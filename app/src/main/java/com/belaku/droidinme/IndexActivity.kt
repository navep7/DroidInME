package com.belaku.droidinme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belaku.droidinme.ContentActivity.Companion.makeToast
import com.belaku.droidinme.ui.theme.DroidInMETheme
import kotlinx.coroutines.launch


lateinit var appContx: Context

class IndexActivity : ComponentActivity() {


    // ui.theme/Theme.kt
    @Composable
    fun MyAppTheme(
        //   darkTheme: Boolean = isSystemInDarkTheme(), // Detect system dark mode
        content: @Composable () -> Unit
    ) {
        isDark = remember { mutableStateOf(false) }
        val colors = if (isDark.value) DarkColorScheme else LightColorScheme

        MaterialTheme(
            colorScheme = colors,
            typography = Typography(), // Your defined typography
            shapes = Shapes(),       // Your defined shapes
            content = content
        )
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (listTopics.size == 0)
            getTopics()

        setContent {

            MyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = topBarPrimaryColor, // Set your desired background color here
                                    titleContentColor = topBarSecondaryColor,
                                    actionIconContentColor = topBarSecondaryColor,
                                    navigationIconContentColor = topBarSecondaryColor// Optional: Adjust title color for contrast
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
                                        isDark.value = !isDark.value
                                        if (topBarPrimaryColor == Color.Black) {
                                            topBarPrimaryColor = Color.White
                                            topBarSecondaryColor = Color.Black
                                        } else {
                                            topBarPrimaryColor = Color.Black
                                            topBarSecondaryColor = Color.White
                                        }



                                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                    }) {
                                        Icon(Icons.Filled.Face, contentDescription = "Settings")
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
                                Modifier
                                    .padding(innerPadding)
                                    .clickable {
                                        //    ContentActivity.makeToast("DarkTheme impl H3r3")
                                        //    ThemeToggleScreen()
                                    },
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                            appContx = applicationContext

                            for (i in 0 until listTopics.size)
                                Greeting(listTopics[i].name)


                        }

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
        listTopics.add(
            Topic(
                "2. Activities, Fragments - Lifecycle",
                "2. Activities, Fragments - Lifecycle"
            )
        )
        listTopics.add(
            Topic(
                "3. Architecture Patterns \n\t\t\t\t MVC, MVP, MVVM, MVI",
                "Earlier 10 years back, GodActivity : simple, but devolved into a nightmare of tangled code, difficulty in testing and maintenance, and poor scalability as projects grew \n \t The need for Separation of Concerns and logical code structuring led us toward architectural patterns. The primary goal has always been for each part of the system to have a clear responsibility, act independently, and be easily testable and modifiable. This not only elevates code quality but also allows larger teams to work in parallel with minimal interference. " +
                        "\n \t These three patterns are approaches to organizing application code with the aim of separating UI from business logic, improving testability, and enhancing maintainability. " +
                        "\n\n 1. MVC " +
                        "\n\t Model: Responsible for holding data and business logic. It operates unaware of the UI and notifies the Controller of changes. (Example: POJO/POKO classes for data, data storage/retrieval logic). " +
                        "\n\t View: Responsible for displaying the user interface and receiving user input. It is entirely passive and forwards inputs to the Controller. (Example: XML layouts, Activity or Fragment classes that only manage UI). " +
                        "\n\t Controller: Acts as an intermediary between the Model and View. It receives user input from the View, updates the Model, and then selects the View to display the new state. (Example: In Android, Activity or Fragment often play the Controller role, which can lead to the \"God Activity\" problem). " +
                        "\n\n\t 2. MVP " +
                        "\n\t Model: Similar to MVC, responsible for data and business logic. " +
                        "\n\t View: An interface implemented by the Activity or Fragment. It is entirely passive, displays the UI, and notifies the Presenter of user actions. No business logic resides in the View. " +
                        "\n\t Presenter: The intermediary between the Model and View. It contains UI presentation logic, interacts with the Model, and \"commands\" the View (via its interface) what to display. It is independent of the Android Framework." +
                        "\n\n\t 3. MVVM " +
                        "\n\t Model: Similar to previous patterns, responsible for data and business logic. " +
                        "\n\t View: Responsible for displaying the UI and receiving user input. It communicates with the ViewModel via Data Binding or Observers. The View has no business logic and merely observes changes from the ViewModel. " +
                        "\n\t ViewModel: Prepares data and presentation logic for the View. It notifies the View of changes via Observables (like LiveData or StateFlow) without directly manipulating the View. It interacts with the Model (often through a Repository). Key features include being lifecycle-aware and retaining data across configuration changes." +
                        "\n\n\n\t The main purpose is Separation of Concerns. This means: " +
                        "\n\t 1.Better Testability: By separating business logic from UI, each part can be tested independently and more easily." +
                        "\n\t 2.Higher Maintainability: Changes in one part have minimal impact on other parts, and finding/fixing bugs becomes simpler." +
                        "\n\t 3.Scalability: The project is easier to manage as features grow and teams expand." +
                        "\n\t 4.Increased Readability: Structured code is more understandable for new and existing developers." +
                        "\n\n\t MVC vs MVP , and why is MVP better for testability?" +
                        "\n\t MVC: The Controller directly interacts with the View and Model. The View might also interact directly with or observe the Model. This direct coupling often makes the Controller heavily dependent on the Android SDK (due to UI management), making it difficult to test." +
                        "\n\t MVP: The Presenter (unlike the Controller) has no direct knowledge of the actual View (the Activity or Fragment class); instead, it works only with the View's interface. The View is completely passive. This decoupling allows the Presenter to be a pure POJO/POKO class with no Android Framework dependencies. Therefore, the Presenter can be easily unit-tested without needing to run an emulator or a real device." +
                        "\n\n\t Communication between components in each pattern ~" +
                        "\n\t MVC: " +
                        "\n\t View to Controller (via Listeners/Callbacks)" +
                        "\n\t Controller to View (via direct methods)" +
                        "\n\t Controller to Model (via direct methods)" +
                        "\n\t Model to Controller (via Observers/Callbacks)" +
                        "\n\t View to Model (directly, for reading data)" +
                        "\n\n\t MVP:" +
                        "\n\t View to Presenter (via direct methods on Presenter interface)" +
                        "\n\t Presenter to View (via direct methods on View interface)" +
                        "\n\t Presenter to Model (via direct methods)" +
                        "\n\t Model to Presenter (via Callbacks/Observers)" +
                        "\n\n\t MVVM:" +
                        "\n\t View to ViewModel (via Data Binding or method calls)" +
                        "\n\t ViewModel to View (via Observables like LiveData/ StateFlow that the View observes)" +
                        "\n\t ViewModel to Model (via Repository)" +
                        "\n\t Model to Repository (via Callbacks/Flows)" +
                        "\n\n\t how does ViewModel in MVVM help solve the Configuration Changes problem?" +
                        "\n\t 1.Lifecycle-aware: Unlike Activity or Fragment, a ViewModel survives configuration changes (e.g., screen rotations). When an Activity or Fragment is destroyed and recreated due to a configuration change, the same ViewModel instance is re-attached to the new Activity/ Fragment." +
                        "\n\t 2.Data Persistence Across Configuration Changes: This feature means any data held within the ViewModel (typically in LiveData or StateFlow) is not lost during screen rotation. The new Activity/Fragment can immediately observe the existing data and update its UI, eliminating the need to reload data or write complex logic using onSaveInstanceState() and onRestoreInstanceState(). This significantly reduces the complexity of UI state management and provides a smoother user experience." +
                        "\n\n\t Data Binding in MVVM ~ " +
                        "\n\t Data Binding is a library within Android Jetpack that allows you to directly link UI components in your layout to data sources in your ViewModel (or other classes)." +
                        "\n\t Functionality: Instead of using findViewById() to locate views in your Activity or Fragment and then manually setText() or setImageDrawable() data onto them, Data Binding performs this declaratively in your XML Layout." +
                        "\n\t Advantages ~" +
                        "\n\t\t Less Code: Reduces boilerplate code needed for UI updates." +
                        "\n\t\t Increased Readability: The connection between UI and data is clearly specified in the XML file." +
                        "\n\t\t Fewer Bugs: Decreases runtime errors caused by findViewById()." +
                        "\n\t\t Improved Testability: UI logic dependent on data is moved from Activity/Fragment to ViewModel." +
                        "\n\n\t "
            )
        )
        listTopics.add(
            Topic(
                "4. Kotlin Coroutines & Concurrency",
                "Asynchronous Programming ? ..." +
                        "\nResponsive User Interface (UI) : " +
                        "\n\t All UI-related operations must execute on a specific thread known as the \"Main Thread\" or \"UI Thread.\" " +
                        "\n\t If long-running operations (like network requests, database operations, or heavy file processing) are executed on this thread, the UI will freeze, making the application unresponsive to user interactions. " +
                        "\n\t Asynchronous programming offloads these operations to background threads, keeping the Main Thread free." +
                        "\n Increased Efficiency : " +
                        "\n\t By performing multiple operations concurrently (using concurrency techniques), system resources (such as CPU and network) can be utilized more effectively. Instead of the application waiting for one operation to complete, it can initiate another or attend to other tasks." +
                        "\n Better Resource Management : " +
                        "\n\tAsynchronous operations allow system resources to be allocated and deallocated optimally without blocking each other. For example, an asynchronous network request doesn't block the thread, allowing it to perform other tasks until the network response arrives." +
                        "\n ANR ~ If the Main Thread is blocked for too long (5 seconds), the Android OS detects that the application is \"not responding\" and displays an ANR dialog to the user, asking if they want to close the app. This provides a very poor user experience and can lead to app uninstallation." +
                        "\n UI Freezing ~ Even if an operation isn't long enough to cause an ANR (e.g., 1-2 seconds), the UI will freeze during this period. Animations stop, buttons appear unresponsive, and the app doesn't react to user input. This makes the application feel slow and unprofessional." +
                        "\n Reduced Performance ~ The Main Thread is a single- threaded resource. If an operation blocks it, no other work (even small UI updates) can be done until that operation completes." +
                        "\n\n Coroutine ?.. suspend keyword.." +
                        "\n\t simplifies thread management. It can be thought of as a \"lightweight thread\" or a \"suspendable subroutine.\"" +
                        "\n\n\t Lightweight: Coroutines are much lighter than operating system threads. Creating, managing, and switching between them incurs less overhead. Thousands of coroutines can run on a single thread." +
                        "\n\t Suspendable: The most important feature of coroutines is their ability to be suspended and resumed. A coroutine can be paused at a certain point (suspend) without blocking its underlying thread, and then resumed from that exact point when the result is ready." +
                        "\n\n\t suspend is a modifier placed at the beginning of a function definition (e.g., suspend fun fetchData()). This keyword signals to the Kotlin compiler and the programmer that:\n" +
                        "\n\t 1.This function can potentially be long-running: Meaning it might need to \"wait\" for its work to complete (e.g., for a network response, or reading from disk)." +
                        "\n\t 2.This function is suspendable without blocking the thread: When a suspend function is called, it can pause (suspend) the execution of the current coroutine, and the thread on which the coroutine is running is freed up to do other work. As soon as the result is ready (e.g., a network response is received), the coroutine's execution is resumed from the exact point it was paused." +
                        "\n\t 3.It can only be called from within a coroutine or another suspend function: This is a compiler restriction that ensures suspend functions are always executed within an appropriate CoroutineContext and prevents accidental thread blocking." +
                        "\n\n suspend vs blocking ~" +
                        "\n\t Concept :" +
                        "\n\t\t suspend - The current coroutine is temporarily paused, but its underlying thread is freed to do other work." +
                        "\n\t\t blocking - The thread on which the operation is running is completely halted and blocked until the operation finishes." +
                        "\n\n\t Thread Efficiency :" +
                        "\n\t\t suspend - Very high. One thread can manage thousands of suspended coroutines." +
                        "\n\t\t blocking - Very low. A blocked thread cannot do any other work until the operation completes." +
                        "\n\n\t Resource Management :" +
                        "\n\t\t suspend - Efficient. Frees up resources (like CPU)." +
                        "\n\t\t blocking - Inefficient. Keeps thread resources idle in a waiting state." +
                        "\n\n\t UI Responsiveness :" +
                        "\n\t\t suspend - Does not block the Main Thread, so the UI remains responsive." +
                        "\n\t\t blocking - Blocks the Main Thread, leading to UI freezes and ANRs." +
                        "\n\n\t Example :" +
                        "\n\t\t suspend - Asynchronous network request, asynchronous database operation, delay() in a coroutine." +
                        "\n\t\t blocking - Thread.sleep(), Socket.accept() in traditional mode, blocking I/O." +
                        "\n\n\t Implementation :" +
                        "\n\t\t suspend - Requires suspend fun and a Coroutine Dispatcher." +
                        "\n\t\t blocking - Via traditional threading APIs or blocking I/O operations." +
                        "\n\n\t Architect-Level Insight :" +
                        "\n\t\t  suspend leverages the Continuation-Passing Style (CPS) in the compiler. Instead of a function \"returning\" a result, it accepts a \"Continuation\" (i.e., the rest of the program after this function completes) as a parameter. When the result is ready, it invokes the Continuation. This mechanism allows the thread to be freed." +
                        "\n\n\t Dispatchers (Main, IO, Default)" +
                        "\n\t\t Dispatchers (Coroutine Dispatcher) determine which thread or thread pool a coroutine runs on. They play a key role in separating concerns and managing concurrency." +
                        "\n\t Dispatchers.Main :" +
                        "\n\t\t For all User Interface (UI) related operations. This Dispatcher ensures the coroutine runs on the Android Main Thread." +
                        "\n\t Dispatchers.IO :" +
                        "\n\t\t For Input/Output (I/O) operations that involve waiting for external resources and are not CPU- bound. This Dispatcher uses a thread pool with a large number of threads optimized for I/O operations (like file reading/writing, database operations, network requests). Threads in this pool can block without significantly impacting overall performance, as their purpose is to wait for I/O." +
                        "\n\t Dispatchers.Default:" +
                        "\n\t\t For CPU-bound (computationally intensive) operations that do not involve waiting for I/O. This Dispatcher uses a thread pool with a limited number of threads (typically equal to the number of CPU cores) to avoid excessive context switching overhead." +
                        "\n\t Architect-Level Insight: Dispatchers.Default is typically implemented using a ForkJoinPool for the JVM, which is optimized for CPU-bound tasks." +
                        "\n\n\t Error handling in Coroutines?" +
                        "\n\t\t try-catch block : For handling errors that occur within a specific section of a coroutine's code and that you expect to \"catch\" and manage (e.g., display an error message to the user, retry an operation)." +
                        "\n\t\t CoroutineExceptionHandler : For handling unhandled exceptions that occur within a hierarchy of coroutines (especially those started with launch) and have not been managed automatically. A CoroutineExceptionHandler is attached to the CoroutineContext of a root coroutine launched with launch (or other Job constructors that actively propagate the Job)." +
                        " " +
                        ""
            )
        )
        listTopics.add(
            Topic(
                "5. Kotlin Flow & Reactive Programming",
                "5. Kotlin Flow & Reactive Programming"
            )
        )
        listTopics.add(
            Topic(
                "6. Jetpack Compose : Fundamentals ",
                "6. Jetpack Compose : Fundamentals "
            )
        )
        listTopics.add(
            Topic(
                "7. Jetpack Components \n\t\t\t\t ViewModel, LiveData, Room",
                "7. Jetpack Components \n\t\t\t\t ViewModel, LiveData, Room"
            )
        )
        listTopics.add(
            Topic(
                "8. Dependency Injection \n\t\t\t\t Dagger & Hilt",
                "8. Dependency Injection \n\t\t\t\t Dagger & Hilt"
            )
        )
        listTopics.add(Topic("9. Testing Strategies", "9. Testing Strategies"))
        listTopics.add(
            Topic(
                "10. I. Network Layer \n\t\t\t\t Retrofit, OkHttp, Security",
                "10. I. Network Layer \n\t\t\t\t Retrofit, OkHttp, Security"
            )
        )
        listTopics.add(
            Topic(
                "11. II. Network Layer \n\t\t\t\t Retrofit, OkHttp, Security",
                "10. II. Network Layer \n\t\t\t\t Retrofit, OkHttp, Security"
            )
        )
        listTopics.add(Topic("12, I. Designing a  Robust & Scalable Data layer", ""))
        listTopics.add(Topic("13, II. Designing a  Robust & Scalable Data layer", ""))
    }

    companion object {
        var topBarSecondaryColor = Color.White
        var topBarPrimaryColor = Color.Black

        val DarkColorScheme = darkColorScheme(
            primary = Color(0xFFBB86FC),
            onPrimary = Color.Black,
            surface = Color(0xFF121212),
            onSurface = Color.White,
            // Define other colors for dark theme
        )

        val LightColorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            onPrimary = Color.White,
            surface = Color.White,
            onSurface = Color.Black,
            // Define other colors for light theme
        )
        lateinit var isDark: MutableState<Boolean>
        val listTopics: ArrayList<Topic> = ArrayList()
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
    /*  Text(
          text = text,

          fontWeight = FontWeight.Bold,
          fontSize = 25.sp
      )*/
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
        }, fontSize = 20.sp // Example font size
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