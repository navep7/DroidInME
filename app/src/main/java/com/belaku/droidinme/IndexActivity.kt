package com.belaku.droidinme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belaku.droidinme.ui.theme.DroidInMETheme

lateinit var appContx: Context

class IndexActivity : ComponentActivity() {


    private val listTopics: ArrayList<String> = arrayListOf(
        "1. Fundamentals",
        "2. Activities, Fragments - Lifecycle",
        "3. Architecture Patterns \n\t\t\t\t MVC, MVP, MVVM, MVI",
        "4. Kotlin Coroutines & Concurrency",
        "5. Kotlin Flow & Reactive Programming",
        "6. Jetpack Compose : Fundamentals ",
        "7. Jetpack Components \n\t\t\t\t ViewModel, LiveData, Room",
        "8. Dependency Injection \n\t\t\t\t Dagger & Hilt",
        "9. Testing Strategies",
        "10. I. Network Layer \n\t\t\t\t Retrofit, OkHttp, Security",
        "11. II. Network Layer \n\t\t\t\t Retrofit, OkHttp, Security"

        )


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                                Text(
                                    text = "DroidInME",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 35.sp
                                )
                            },
                            actions = {
                                IconButton(onClick = { /* Handle settings icon click */ }) {
                                    Icon(Icons.Filled.Settings, contentDescription = "Settings")
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Home, "backIcon")
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

                        Text(
                            text = "30 Days of..,",
                            Modifier.padding(innerPadding),
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        appContx = applicationContext

                        for (i in 0 until listTopics.size) 
                            Greeting(listTopics[i])
                        

                    }

                }
            }
        }
    }
}


@Composable
fun DottedUnderlinedText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    underlineColor: Color = Color.Gray,
    underlineThickness: Dp = 1.dp,
    dotLength: Dp = 25.dp,
    gapLength: Dp = 5.dp
) {
    Text(
        text = text, modifier = modifier.drawBehind {
            val strokeWidthPx = underlineThickness.toPx()
            val dotLengthPx = dotLength.toPx()
            val gapLengthPx = gapLength.toPx()

            val textBottom = size.height - strokeWidthPx / 2f // Adjust for line thickness

            drawLine(
                color = underlineColor,
                start = Offset(-100f, textBottom),
                end = Offset(1000f, textBottom),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round, // Makes the dots round
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(dotLengthPx, gapLengthPx), phase = 0f
                )
            )
        }, color = color, fontSize = 20.sp // Example font size
    )
}

@Composable
fun Greeting(name: String) {
    Box(
        modifier = Modifier
            .clickable {
                // This lambda will be executed when the Box is clicked
                val intent = Intent(appContx, ContentActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this flag
                }
                intent.putExtra("day", name)
                appContx.startActivity(intent)
                // You can add any logic here, e.g., navigate, update state, show a Toast
            }
            .fillMaxWidth()
            .padding(5.dp, 15.dp, 0.dp, 0.dp)

    ) {
        DottedUnderlinedText(text = name)
    }

}