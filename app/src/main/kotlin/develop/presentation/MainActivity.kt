package develop.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import develop.presentation.screen.CalendarScreen
import develop.presentation.screen.DetailTaskScreen
import develop.presentation.screen.TaskListScreen
import develop.ui.theme.KalendaryTheme
import develop.util.CALENDAR_SCREEN_PATH
import develop.util.DETAIL_TASK_SCREEN_PATH
import develop.util.TASK_ID_KEY
import develop.util.TASK_LIST_SCREEN_PATH

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalendaryTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Calendar.route) {
                    addScreen(Screen.Calendar) { CalendarScreen(navController = navController) }
                    addScreen(Screen.TaskList) { TaskListScreen(navController = navController) }
                    addScreen(Screen.DetailTask) { DetailTaskScreen(navController = navController) } }
            }
        }
    }

    private fun NavGraphBuilder.addScreen(
        screen: Screen,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        composable(screen.route, arguments = screen.arguments) { backStackEntry ->
            content(backStackEntry)
        }
    }
}

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>) {
    data object Calendar : Screen(CALENDAR_SCREEN_PATH, emptyList())
    data object TaskList : Screen(TASK_LIST_SCREEN_PATH, emptyList())
    data object DetailTask : Screen("$DETAIL_TASK_SCREEN_PATH/{$TASK_ID_KEY}",
        listOf(
            navArgument(TASK_ID_KEY) { type = NavType.StringType }
        )
    )
}