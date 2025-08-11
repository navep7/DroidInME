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
                "2. Activity - Lifecycle",
                        " ...series of callback methods that define the various states an Activity transitions through from its creation to its destruction." +
                        "\n\n Core Lifecycle Methods:" +
                        "\n⦿ onCreate(): Called when the Activity is first created. This is where you perform essential one-time initialization, such as setting the layout (setContentView()), initializing views, and binding data." +
                        "\n⦿ onStart(): The Activity becomes visible to the user but is not yet interactive. Use this to register broadcast receivers or start animations that apply when the UI is visible but not in focus." +
                        "\n⦿ onResume(): The Activity is in the foreground, fully interactive, and ready to receive user input. This is where you typically start resource-intensive operations or UI updates that need to run only when the user is actively interacting with the Activity." +
                        "\n⦿ onPause(): The Activity is partially obscured by another Activity (e.g., a transparent Activity, a dialog, or a new Activity appearing on top). You should save any unsaved, persistent data here and stop resource- intensive operations that don't need to run while the Activity is not in full focus." +
                        "\n⦿ onStop(): The Activity is no longer visible to the user (e.g., the user navigated to another app or another Activity completely covers it). Release resources that are not needed while the Activity is not visible to conserve CPU and battery." +
                        "\n⦿ onDestroy(): Called just before the Activity is destroyed. This is the final cleanup step, where you release all remaining resources and unregister listeners to prevent memory leaks." +
                        "\n⦿ onRestart(): Called when an Activity that was previously stopped (i.e., onStop() was called) is being re-displayed to the user." +
                        "\n\n Why is this Critical ?" +
                        "\n⦿ Resource Management: Proper lifecycle management is paramount for efficiently managing resources. Mismanaging resources (e.g., not unregistering heavy listeners or not closing database cursors) leads to memory leaks, ANRs (Application Not Responding) errors, and poor performance, especially in large applications." +
                        "\n⦿ User Experience (UX): A smoothly transitioning app that gracefully handles interruptions (like incoming calls, screen rotations, or switching to other apps) depends entirely on correct lifecycle implementation. Users expect an app to resume exactly where they left off." +
                        "\n⦿ Stability & Reliability: Incorrect lifecycle handling can lead to crashes (e.g., trying to update UI after onStop()), data loss, or inconsistent states, all of which severely impact app reliability." +
                        "\n⦿ Performance: Knowing where to perform expensive operations (e.g., network calls) and where to release resources is critical for app responsiveness and battery conservation" +
                        "\n\n Configuration changes (screen rotation)?" +
                        "\n When a configuration change occurs (most commonly screen rotation, but also keyboard availability, language change, etc.), the Android system, by default, destroys and then recreates the current Activity." +
                        "\n⦿ The Activity's onPause(), onStop(), and onDestroy() methods are called. -> The Activity instance is destroyed." +
                        "\n⦿ A new instance of the Activity is created. The new Activity instance goes through onCreate(), onStart(), and onResume()." +
                        "\n This default behavior is designed to allow the app to automatically adapt to the new configuration (e.g., loading different layouts for landscape vs. portrait, or language- specific resources). The system ensures that all resources corresponding to the old configuration are released, and new ones are loaded." +
                        "\n However, for a large, complex application, this default behavior can be problematic. The destruction and recreation process is expensive, causing a noticeable flicker, losing temporary UI state, and potentially leading to ANRs if data re-loading is slow" +
                        "\n\n How do you preserve data during configuration changes?" +
                        "\n ...several ways, each with its own use cases and trade-offs:" +
                        "\n\n⦿ onSaveInstanceState() and onRestoreInstanceState() (via onCreate()'s Bundle):" +
                        "\n Mechanism: onSaveInstanceState() is called before onStop() when the system might destroy the Activity. You can save primitive data types (and some Parcelable/Serializable objects) into a Bundle. This Bundle is then passed to onCreate() and onRestoreInstanceState() of the new Activity instance." +
                        "\n Use Case: Ideal for saving small amounts of UI state (e.g., current scroll position, text in an EditText)." +
                        "\n Limitation (Architect's Note): This is not designed for persisting large objects or complex data. The Bundle has a size limit, and serialization/deserialization on the main thread can cause performance issues. It also doesn't survive process death initiated by the system (e.g., Low Memory Killer)." +
                        "\n\n⦿ ViewModel - most Recommended way.." +
                        "\n ViewModels are designed to store and manage UI-related data in a lifecycle-conscious way. A ViewModel instance survives configuration changes because it's retained by the Android system across Activity recreations." +
                        "\n\n Handling the configuration change yourself (via android:configChanges in Manifest):" +
                        "\n⦿ By declaring android:configChanges=\"orientation|screenSize|keyboar dHidden\" in your Activity's manifest entry, you tell the system not to recreate the Activity on these specific changes. Instead, the onConfigurationChanged() callback method is invoked." +
                        "\n Only use this for specific scenarios where you have a compelling reason to handle the configuration change manually (e.g., a game that needs to redraw graphics or a video player that seamlessly transitions).\n" +
                        "\n Architect's Warning: avoid using this!" +
                        "\n\n⦿ Persisting data to local storage (Database, SharedPreferences, Files):" +
                        "\n Mechanism: Save data to a persistent storage medium (Room Database, SharedPreferences, internal/external files) before the Activity is destroyed, and reload it when needed.\n" +
                        "\n Use Case: For non-UI-related data that needs to persist across app launches, process deaths, and configuration changes (e.g., user preferences, cached network data).\n" +
                        "\n Architect's Insight: This is for data that needs to outlive the current Activity lifecycle. It's often used in conjunction with ViewModels, where the ViewModel interacts with a data repository that handles persistence." +
                        "\n\n How would you handle memory leaks in Activity lifecycle callbacks?" +
                        "\n Memory leaks are a critical concern in Android, often stemming from improper resource management in Activity lifecycle callbacks. An Activity memory leak occurs when an Activity instance (or its View hierarchy) cannot be garbage collected because another object holds a strong reference to it, even after onDestroy() has been called. This leads to increased memory consumption and eventually OutOfMemoryError." +
                        "\n\n Common Causes of Memory Leaks in Activity Lifecycle:" +
                        "\n⦿ Long-running background tasks: An AsyncTask, Thread, or Coroutine that holds a strong reference to the Activity's Context (or a View) and outlives the Activity.\n" +
                        "\n⦿ Anonymous inner classes: Runnables, Handler callbacks, AsyncTasks, or custom Listeners defined as non-static inner classes implicitly hold a strong reference to their enclosing Activity.\n" +
                        "\n⦿ Registered listeners/receivers: If you register BroadcastReceivers, EventBus subscribers, or other system listeners (e.g., LocationManager, SensorManager) in onResume() or onCreate() but forget to unregister them in onPause() or onDestroy().\n" +
                        "\n⦿ Static references: Storing an Activity instance or its Views in a static field.\n" +
                        "\n⦿ Drawables/Bitmaps: Holding onto large Bitmaps or Drawables (especially if they are tied to a Context).\n" +
                        "\n⦿ WebView: WebViews are notorious for memory leaks and often require specific cleanup." +
                        "\n\n Architectural Strategies & Solutions:" +
                        "\n⦿  Use LifecycleObservers and LifecycleOwners: Attach lifecycle-aware components (like LiveData, ViewModel, LifecycleObservers) to automatically manage subscriptions and resource cleanup based on the Activity's lifecycle state. This is the preferred modern approach." +
                        "\n⦿ Weak References: For Handlers or AsyncTasks, use WeakReference to the Activity's Context if you must hold a reference. This allows the garbage collector to reclaim the Activity if needed.\n" +
                        "\n⦿ Unregister Listeners and Callbacks: Always ensure that anything registered in onCreate() or onResume() is unregistered in onDestroy() or onPause(), respectively." +
                        "\n⦿ Clear Handler Callbacks: Remove any pending Runnables from Handlers in onStop() or onDestroy()." +
                        "\n⦿ Static Inner Classes: For inner classes that need to outlive the Activity (e.g., AsyncTasks), declare them as static and pass a WeakReference to the Activity's context if they need to interact with the UI." +
                        "\n⦿ ViewModel for long-running operations: Delegate long- running operations (like network requests or database operations) to a ViewModel or a separate repository layer. ViewModels survive configuration changes, and their data can be observed by the UI without leaks." +
                        "\n⦿ Profile with Android Studio Profiler (Memory Profiler): Regularly use tools like the Android Studio Memory Profiler to detect memory leaks and analyze heap dumps. Look for Activities that are still in memory after they should have been destroyed.\n" +
                        "\n⦿ LeakCanary: Integrate library, LeakCanary into your debug builds. It automatically detects and reports potential memory leaks in your application, providing stack traces to pinpoint the exact location of the leak." +
                        "\n\n How do you implement proper navigation between Activities with data passing? " +
                        "\n Proper navigation is essential for a smooth user experience. Android offers several ways to navigate between Activities and pass data, with modern approaches emphasizing robustness and simplicity." +
                        "\n⦿ Traditional Approach: Using Intents" +
                        "\n Mechanism: An Intent is an abstract description of an operation to be performed. To navigate to another Activity, you create an explicit Intent specifying the target Activity class and then call startActivity(intent).\n" +
                        "\n Data Passing: Data can be passed via Intent \"extras\" using methods like putExtra() for primitive types, putStringArrayListExtra(), putSerializable(), or putParcelable()." +
                        "\n Architect's Note: While fundamental, manually managing Intent extras for every data type can become verbose and error-prone in large apps." +
                        "\n\n⦿ Modern Approach: Android Jetpack Navigation Component (Recommended)" +
                        "\n Mechanism: The Navigation Component simplifies navigation by using a single Activity (Single Activity Architecture) and managing multiple Fragments within it. Navigation is defined graphically in a navigation graph, making it easier to visualize and manage complex flows." +
                        "\n Data Passing: It uses Safe Args (a Gradle plugin) to generate simple object and builder classes for type-safe argument passing between destinations (Fragments or Activities). This eliminates runtime type-casting errors." +
                        "\n Architect's Insight:" +
                        "\n Single Activity Architecture: Encourages a simpler, more maintainable app structure." +
                        "\n Type Safety: Safe Args drastically reduces bugs related to data passing." +
                        "\n Deep Linking: Simplifies handling deep links, routing them directly to specific destinations in your navigation graph." +
                        "\n Shared Element Transitions: Integrates well with shared element transitions for smoother UI animations." +
                        "\n Back Stack Management: Automatically handles the navigation back stack, reducing complexity." +
                        "\n\n⦿ Data Passing via Shared ViewModel - (for Fragments within the same Activity):" +
                        "\n Mechanism: If you have multiple Fragments within the same Activity and need to share data between them that isn't directly part of navigation arguments, you can use a ViewModel scoped to the parent Activity." +
                        "\n Use Case: Real-time communication between sibling Fragments (e.g., a filter Fragment updating a list Fragment)." +
                        "\n Architect's Insight: Great for transient, interactive state sharing, but not for initial navigation arguments or persistent data that needs to survive process death without SavedStateHandle." +
                        ""
            )
        )
        listTopics.add(
            Topic(
                "3. Fragments & Fragment Lifecycle",
                "inherently tied to its host Activity's lifecycle, establishing a parent-child relationship" +
                        "\n Key Fragment Lifecycle Methods:" +
                        "\n⦿ onAttach(): The Fragment is associated with its Activity (context is available)." +
                        "\n⦿ onCreate(): Non-UI and initial Fragment operations are performed (e.g., ViewModel setup)." +
                        "\n⦿ onCreateView(): The Fragment's UI is created and returned." +
                        "\n⦿ onViewCreated(): The Fragment's View is created and ready for access." +
                        "\n⦿ onStart(): The Fragment becomes visible to the user. onResume(): The Fragment is in the foreground and actively interacting with the user." +
                        "\n⦿ onPause(): The Fragment is losing focus; important data should be saved." +
                        "\n⦿ onStop(): The Fragment is no longer visible. onDestroyView(): The Fragment's View hierarchy is destroyed, and UI resources are released. At this stage, View references should be cleared to prevent memory leaks." +
                        "\n⦿ onDestroy(): The Fragment instance is destroyed, and final non-UI resources are released." +
                        "\n⦿ onDetach(): The Fragment is detached from its Activity." +
                        "\n\n Coordinating Fragment Lifecycle with Activity:" +
                        "\n⦿ The Activity's onCreate() triggers the Fragment's onAttach() and onCreate() methods." +
                        "\n⦿ The Activity's onPause() and onStop() pause or stop the Fragments." +
                        "\n⦿ If the Activity is destroyed, the Fragments follow suit (onDestroy() / onDetach())." +
                        "\n\n When should you use Fragments versus Activities?" +
                        "\n Fragments are modular UI components within an Activity, whereas Activities are considered independent screens. The decision to use a Fragment versus an Activity involves understanding modularity, reusability, and the evolving patterns of Android architecture, particularly the shift towards Single-Activity Architecture." +
                        "\n\n⦿ Modularity: To break down the UI into reusable components (e.g., tabs" +
                        "\n⦿ Responsive Design: To support Multi-Pane Layouts and adapt to different screen sizes and orientations" +
                        "\n⦿ Single-Activity Architecture: In modern applications, to manage internal navigation, simplifies navigation as well." +
                        "\n\n How do Fragments communicate with Activities and other Fragments?" +
                        "\n⦿ Shared ViewModel Approach (Recommended): This is the preferred method for communication between Fragments and their host Activity, especially in a Single- Activity Architecture. ViewModels automatically handle lifecycle management and persist across configuration changes." +
                        "\n Mechanism: Multiple Fragments and Activities can share a single ViewModel instance by using the appropriate scopes (e.g., activityViewModels() or navGraphViewModels()) and communicate data using LiveData or Flow." +
                        "\n⦿ Navigation Component with Safe Args: The Jetpack Navigation Component simplifies navigation and data transfer between destinations, including between Fragments. Safe Args provides type-safe argument passing." +
                        "\n Mechanism: You define navigation paths and required arguments in a Navigation Graph, then use NavController for navigation and data passing." +
                        "\n⦿ Interface-based Communication (Callbacks):" +
                        "\n ..traditional method, The Fragment defines an interface, and the host Activity implements this interface to receive messages from the Fragment." +
                        "\n Mechanism: The Fragment defines an interface Listener, the Activity implements it, and the Fragment obtains this Listener in onAttach() and nullifies it in onDetach() to prevent memory leaks." +
                        "\n Challenges: This method can lead to tight coupling and is challenging to manage in complex scenarios with nested Fragments." +
                        "\n\n FragmentManager ?" +
                        "\n ...allows you to perform operations such as adding, removing, replacing, and finding Fragments, as well as managing the Fragment Back Stack." +
                        "\n⦿ Performing Fragment Transactions: FragmentManager provides an interface to perform atomic transactions (like add(), remove(), replace(), hide(), show(), attach(), detach()). These transactions must be grouped within a FragmentTransaction and then commit()ted." +
                        "\n⦿ Managing the Fragment Back Stack: FragmentManager maintains a Back Stack for Fragments, similar to the Activity Back Stack. This allows the user to navigate back to previous Fragment states using the system back button." +
                        "\n⦿ Retrieving Fragments: FragmentManager can retrieve Fragments by their ID or Tag (findFragmentById(), findFragmentByTag())." +
                        "\n⦿ Lifecycle Coordination: FragmentManager is responsible for coordinating the Fragment lifecycle with its host Activity's lifecycle." +
                        "\n\n Android Jetpack Navigation Component ~" +
                        "\n⦿ Navigation Graph:" +
                        "\n\t - An XML resource that visually and structurally defines all destinations (typically Fragments) in your app and the possible paths between them (Actions)." +
                        "\n\t - Deep Linking: Deep Links can be defined directly through the Navigation Graph, allowing you to navigate to a specific destination in your app via a URL, notification, or widgets." +
                        "\n\n⦿ NavHost:" +
                        "\n An empty container that displays destinations (Fragments) from your Navigation Graph. Typically, NavHostFragment is placed in the main Activity." +
                        "\n\n⦿ NavController:" +
                        "\n An object that manages navigation within a NavHost. It is responsible for swapping between destinations and managing the navigation Back Stack. You can obtain its instance using findNavController()." +
                        "\n\n⦿ Safe Args:" +
                        "\n A Gradle plugin that generates simple classes for type-safe navigation and data passing between destinations. This prevents runtime errors and improves code readability." +

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