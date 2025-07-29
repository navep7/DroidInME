package com.belaku.droidinme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belaku.droidinme.ui.theme.DroidInMETheme
import kotlinx.coroutines.launch
import java.util.Locale


class ContentActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    private lateinit var desc: String
    private var tts: TextToSpeech? = null


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US) // Set your desired language
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle language not supported
            }
        } else {
            // Handle initialization failure
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }

    fun chunkText(longText: String, maxChunkSize: Int = 3000): List<String> {
        val chunks = mutableListOf<String>()
        var currentIndex = 0
        while (currentIndex < longText.length) {
            var endIndex = minOf(currentIndex + maxChunkSize, longText.length)
            if (endIndex < longText.length) {
                // Try to find a sentence ending to split at
                val lastSentenceEnd = longText.lastIndexOfAny(charArrayOf('.', '!', '?'), endIndex)
                if (lastSentenceEnd > currentIndex) {
                    endIndex = lastSentenceEnd + 1
                }
            }
            chunks.add(longText.substring(currentIndex, endIndex))
            currentIndex = endIndex
        }
        return chunks
    }

    fun speakText(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "") // QUEUE_FLUSH or QUEUE_ADD
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        tts = TextToSpeech(this, this)
        setContent {
            DroidInMETheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black, // Set your desired background color here
                                titleContentColor = Color.White,
                                actionIconContentColor = Color.White,
                                navigationIconContentColor = Color.White// Optional: Adjust title color for contrast
                            ),
                            title = {
                                intent.getStringExtra("day")?.let {
                                    Text(
                                        text = IndexActivity.listTopics[it.subSequence(0, 1)
                                            .toString().toInt() - 1].name,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2, // Restricts the text to a single line
                                        fontSize = 21.sp
                                    )
                                }
                            },

                            /*
                            * intent.getStringExtra("day")?.let {
                            Text(
                                text = IndexActivity.listTopics[it.subSequence(0, 1).toString()
                                    .toInt() - 1].desc,*/
                            actions = {
                                IconButton(onClick = {
                                    chunkText(desc)
                                }) {
                                    Icon(Icons.Filled.PlayArrow, contentDescription = "Settings")
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.ArrowBack, "backIcon")
                                }
                            },

                            )
                    }, modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(25.dp, 25.dp, 0.dp, 0.dp)
                    ) {
                        intent.getStringExtra("day")?.let {
                            desc = IndexActivity.listTopics[it.subSequence(0, 1).toString()
                                .toInt() - 1].desc
                          /*  Text(
                                text = desc,
                                Modifier.padding(innerPadding),
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )*/

                            RecipeReaderScreen()
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun RecipeReaderScreen() {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        // Remember the manager across recompositions
        val ttsManager = remember { TextToSpeechManager(context) }
        // Clean up the manager when the composable leaves the screen
        DisposableEffect(lifecycleOwner) {
            onDispose {
                ttsManager.shutdown()
            }
        }
        DisposableEffect(lifecycleOwner) {
            // When the Composable enters the composition, add the observer.
            lifecycleOwner.lifecycle.addObserver(ttsManager)
            // When the Composable leaves the composition, remove the observer.
            // This is also where the manager's onDestroy will be called,
            // which in turn calls shutdown(), cleaning up all resources.
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(ttsManager)
            }
        }
        val recipeText = "First, preheat the oven to 180 degrees Celsius. " +
                "While it heats, whisk together the flour, sugar, and cocoa powder in a large bowl. " +
                "Slowly mix in the eggs and milk until the batter is smooth. " +
                "Finally, pour the batter into a greased baking pan and bake for 30 minutes. Enjoy your delicious cake!"
        // Collect states from our manager to drive the UI
        val ttsState by ttsManager.state.collectAsState()
        val highlight by ttsManager.highlight.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        // Create the annotated string for highlighting text
        val annotatedText = buildAnnotatedString {
            append(recipeText)
            highlight?.let { (start, end) ->
                if (start in recipeText.indices && end <= recipeText.length && start < end) {
                    addStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.ExtraBold,
                            background = Color(0xFFFFF59D) // A pleasant yellow
                        ),
                        start = start,
                        end = end
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 125.dp, 5.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // The main Play/Pause/Resume Button
                Button(
                    onClick = {
                        when (ttsState) {
                            TtsState.Idle -> {
                                coroutineScope.launch { ttsManager.speak(desc) }
                            }
                            TtsState.Paused -> {
                                ttsManager.resume()
                            }
                            TtsState.Speaking -> {
                                ttsManager.pause()
                            }
                        }
                    },
                    modifier = Modifier.height(48.dp)
                ) /*{
                    val iconDrawable = when(ttsState) {
                        TtsState.Idle -> Icons.Filled.PlayArrow
                        TtsState.Speaking -> Icons.Filled.List
                        TtsState.Paused -> Icons.Filled.Send
                    }
                    IconButton(onClick = {}) {
                        Icon(iconDrawable, "backIcon")
                    }
                }*/
                {
                    val buttonText = when(ttsState) {
                        TtsState.Idle -> "Read"
                        TtsState.Speaking -> "Pause"
                        TtsState.Paused -> "Resume"
                    }
                    Text(buttonText)
                }
                Spacer(modifier = Modifier.width(16.dp))
                // The Stop Button
                Button(
                    onClick = { ttsManager.stop() },
                    enabled = ttsState != TtsState.Idle,
                    modifier = Modifier.height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White
                    )
                ) {
                    Text("Stop")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = desc,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }


        }
    }

    companion object {
        fun makeToast(s: String) {
            Toast.makeText(appContx, s, Toast.LENGTH_SHORT).show()
        }
    }
}

