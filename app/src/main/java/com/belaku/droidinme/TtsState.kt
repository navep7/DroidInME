package com.belaku.droidinme

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

sealed class TtsState {
    data object Idle : TtsState()
    data object Speaking : TtsState()
    data object Paused : TtsState()
}

private data class TtsSession(
    val text: String,
    val chunks: List<String>,
    val chunkOffsets: List<Int>,
    var currentChunkIndex: Int = 0,
    var lastSpokenTextEndIndex: Int = 0,
    var resumeOffsetInChunk: Int = 0
)
class TextToSpeechManager(private val context: Context): DefaultLifecycleObserver {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var tts: TextToSpeech? = null
    private var currentSession: TtsSession? = null
    private val _state = MutableStateFlow<TtsState>(TtsState.Idle)
    val state: StateFlow<TtsState> = _state
    private val _highlight = MutableStateFlow<Pair<Int, Int>?>(null)
    val highlight: StateFlow<Pair<Int, Int>?> = _highlight
    suspend fun speak(text: String) {
        initialize()
        stop()
        val chunks = splitTextIntoChunks(text)
        val offsets = chunks.runningFold(0) { acc, chunk -> acc + chunk.length }.dropLast(1)
        currentSession = TtsSession(text, chunks, offsets)
        _state.value = TtsState.Speaking
        speakCurrentChunk()
    }
    fun pause() {
        if (_state.value == TtsState.Speaking) {
            tts?.stop()
            _state.value = TtsState.Paused
        }
    }
    fun resume() {
        if (_state.value == TtsState.Paused) {
            _state.value = TtsState.Speaking
            speakCurrentChunk(fromPause = true)
        }
    }
    fun stop() {
        tts?.stop()
        currentSession = null
        _state.value = TtsState.Idle
        _highlight.value = null
    }
    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
        coroutineScope.cancel()
    }
    private fun speakCurrentChunk(fromPause: Boolean = false) {
        val session = currentSession ?: return
        val ttsInstance = tts ?: return
        val chunkIndex = session.currentChunkIndex
        if (chunkIndex >= session.chunks.size) {
            stop()
            return
        }
        val chunk = session.chunks[chunkIndex]
        val textToSpeak: String
        if (fromPause) {
            val resumeIndex = session.lastSpokenTextEndIndex - session.chunkOffsets[chunkIndex]
            session.resumeOffsetInChunk = if (resumeIndex > 0) resumeIndex else 0
            textToSpeak = if (session.resumeOffsetInChunk < chunk.length) {
                chunk.substring(session.resumeOffsetInChunk)
            } else {
                session.currentChunkIndex++
                speakCurrentChunk()
                return
            }
        } else {
            session.resumeOffsetInChunk = 0
            textToSpeak = chunk
        }
        ttsInstance.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "chunk_${chunkIndex}")
    }
    private suspend fun initialize() {
        if (tts != null) return
        tts = suspendCancellableCoroutine { continuation ->
            var ttsInstance: TextToSpeech? = null
            val listener = TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    ttsInstance?.let {
                        it.setOnUtteranceProgressListener(createProgressListener())
                        if (continuation.isActive) {
                            continuation.resume(it)
                        }
                    }
                } else {
                    if (continuation.isActive) {
                        continuation.resumeWithException(Exception("Init failed."))
                    }
                }
            }
            ttsInstance = TextToSpeech(context, listener)
            continuation.invokeOnCancellation {
                ttsInstance.shutdown()
            }
        }
    }
    private fun createProgressListener() = object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {}
        override fun onError(utteranceId: String?) { stop() }
        override fun onDone(utteranceId: String?) {
            if (_state.value == TtsState.Speaking) {
                currentSession?.let {
                    it.currentChunkIndex++
                    speakCurrentChunk()
                }
            }
        }
        override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
            val session = currentSession ?: return
            val chunkOffset = session.chunkOffsets[session.currentChunkIndex]
            val resumeOffset = session.resumeOffsetInChunk
            val absoluteStart = chunkOffset + resumeOffset + start
            val absoluteEnd = chunkOffset + resumeOffset + end
            session.lastSpokenTextEndIndex = absoluteEnd
            coroutineScope.launch {
                _highlight.emit(Pair(absoluteStart, absoluteEnd))
            }
        }
    }
    companion object {
        private const val TTS_CHUNK_SIZE_LIMIT = 3000
        private fun splitTextIntoChunks(text: String): List<String> {
            if (text.length <= TTS_CHUNK_SIZE_LIMIT) {
                return listOf(text)
            }
            val chunks = mutableListOf<String>()
            var remainingText = text
            while (remainingText.isNotEmpty()) {
                if (remainingText.length <= TTS_CHUNK_SIZE_LIMIT) {
                    chunks.add(remainingText)
                    break
                }
                val potentialChunk = remainingText.substring(0, TTS_CHUNK_SIZE_LIMIT)
                var splitIndex = potentialChunk.lastIndexOfAny(charArrayOf('.', '!', '?'))
                if (splitIndex == -1) {
                    splitIndex = potentialChunk.lastIndexOf(' ')
                }
                if (splitIndex == -1) {
                    splitIndex = TTS_CHUNK_SIZE_LIMIT - 1
                }
                chunks.add(remainingText.substring(0, splitIndex + 1))
                remainingText = remainingText.substring(splitIndex + 1)
            }
            return chunks
        }
    }
    override fun onPause(owner: LifecycleOwner) {
        if (_state.value == TtsState.Speaking) {
            pause()
        }
    }
    override fun onDestroy(owner: LifecycleOwner) {
        shutdown()
    }
}