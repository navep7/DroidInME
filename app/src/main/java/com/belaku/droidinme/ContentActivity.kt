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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belaku.droidinme.ui.theme.DroidInMETheme
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
                                IconButton(onClick = { speakText(desc) }) {
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
                            Text(
                                text = desc,
                                Modifier.padding(innerPadding),
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun makeToast(s: String) {
            Toast.makeText(appContx, s, Toast.LENGTH_SHORT).show()
        }
    }
}

