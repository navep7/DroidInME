package com.belaku.droidinme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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





    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        getTopics()
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
                                IconButton(onClick = {

                                }) {
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
                            Modifier.padding(innerPadding).clickable {
                                ContentActivity.makeToast("DarkTheme impl H3r3")
                            //    ThemeToggleScreen()
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        appContx = applicationContext

                        for (i in 0 until listTopics.size) 
                            Greeting(listTopics[i].name)

                      //  ThemeToggleScreen()

                    }

                }
            }
        }
    }





    private fun getTopics() {


        /*arrayListOf(
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

        )*/

        listTopics.add(Topic("1. Fundamentals", "Fundamentals"))
        listTopics.add(Topic("2. Activities, Fragments - Lifecycle", "2. Activities, Fragments - Lifecycle"))
        listTopics.add(Topic("3. Architecture Patterns \\n\\t\\t\\t\\t MVC, MVP, MVVM, MVI", "Earlier 10 years back, GodActivity : simple, but devolved into a nightmare of tangled code, difficulty in testing and maintenance, and poor scalability as projects grew \n \t The need for Separation of Concerns and logical code structuring led us toward architectural patterns. The primary goal has always been for each part of the system to have a clear responsibility, act independently, and be easily testable and modifiable. This not only elevates code quality but also allows larger teams to work in parallel with minimal interference. \n \t These three patterns are approaches to organizing application code with the aim of separating UI from business logic, improving testability, and enhancing maintainability. \n\n 1. MVC \n\t Model: Responsible for holding data and business logic. It operates unaware of the UI and notifies the Controller of changes. (Example: POJO/POKO classes for data, data storage/retrieval logic). \n\t View: Responsible for displaying the user interface and receiving user input. It is entirely passive and forwards inputs to the Controller. (Example: XML layouts, Activity or Fragment classes that only manage UI). \n\t Controller: Acts as an intermediary between the Model and View. It receives user input from the View, updates the Model, and then selects the View to display the new state. (Example: In Android, Activity or Fragment often play the Controller role, which can lead to the \"God Activity\" problem). \n\n\t 2. MVP \n\t Model: Similar to MVC, responsible for data and business logic. \n\t View: An interface implemented by the Activity or Fragment. It is entirely passive, displays the UI, and notifies the Presenter of user actions. No business logic resides in the View. \n\t Presenter: The intermediary between the Model and View. It contains UI presentation logic, interacts with the Model, and \"commands\" the View (via its interface) what to display. It is independent of the Android Framework.\n\n\t 3. MVVM \n\t Model: Similar to previous patterns, responsible for data and business logic. \n\t View: Responsible for displaying the UI and receiving user input. It communicates with the ViewModel via Data Binding or Observers. The View has no business logic and merely observes changes from the ViewModel. \n\t ViewModel: Prepares data and presentation logic for the View. It notifies the View of changes via Observables (like LiveData or StateFlow) without directly manipulating the View. It interacts with the Model (often through a Repository). Key features include being lifecycle-aware and retaining data across configuration changes."))
        listTopics.add(Topic("4. Kotlin Coroutines & Concurrency", "4. Kotlin Coroutines & Concurrency"))
        listTopics.add(Topic("5. Kotlin Flow & Reactive Programming", "5. Kotlin Flow & Reactive Programming"))
        listTopics.add(Topic("6. Jetpack Compose : Fundamentals ", "6. Jetpack Compose : Fundamentals "))
        listTopics.add(Topic("7. Jetpack Components \\n\\t\\t\\t\\t ViewModel, LiveData, Room", "7. Jetpack Components \\n\\t\\t\\t\\t ViewModel, LiveData, Room"))
        listTopics.add(Topic("8. Dependency Injection \\n\\t\\t\\t\\t Dagger & Hilt", "8. Dependency Injection \\n\\t\\t\\t\\t Dagger & Hilt"))
        listTopics.add(Topic("9. Testing Strategies", "9. Testing Strategies"))
        listTopics.add(Topic("10. I. Network Layer \\n\\t\\t\\t\\t Retrofit, OkHttp, Security", "10. I. Network Layer \\n\\t\\t\\t\\t Retrofit, OkHttp, Security"))
        listTopics.add(Topic("11. II. Network Layer \\n\\t\\t\\t\\t Retrofit, OkHttp, Security", "10. II. Network Layer \\n\\t\\t\\t\\t Retrofit, OkHttp, Security"))

    }

    companion object {
        var listTopics: ArrayList<Topic> = ArrayList()
    }
}
/*

@Composable
private fun ThemeToggleScreen() {
    isDarkThemeEnabled = remember { mutableStateOf(false) }

    MyAppTheme(darkTheme = isDarkThemeEnabled.value) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { isDarkThemeEnabled.value = !isDarkThemeEnabled.value }) {
                Text(text = if (isDarkThemeEnabled.value) {
                    "Switch to Light Mode"
                } else {
                    "Switch to Dark Mode"
                })
            }
            // Your other UI content here, which will inherit the theme
        //    Text(text = "Hello Compose!", style = MaterialTheme.typography.headlineLarge)
        }
    }
}




private val DarkColorPalette = darkColorScheme(
    primary = Color.LightGray,
    secondary = Color.DarkGray,
    // ... define other dark colors
)

private val LightColorPalette = lightColorScheme(
    primary = Color.DarkGray,
    secondary = Color.LightGray,
    // ... define other light colors
)


@Composable
fun MyAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
*/



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