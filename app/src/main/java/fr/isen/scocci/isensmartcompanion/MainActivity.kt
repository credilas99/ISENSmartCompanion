package fr.isen.scocci.isensmartcompanion

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.scocci.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val MainTab = TabBarItem(
                title = "main",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            )
            val EventsTab = TabBarItem(
                title = "events",
                selectedIcon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications
            )
            val AgendaTab = TabBarItem(
                title = "agenda",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            )
            val HistoryTab = TabBarItem(
                title = "history",
                selectedIcon = Icons.Filled.MoreVert,
                unselectedIcon = Icons.Outlined.MoreVert
            )
            val tabBarItems = listOf(MainTab, EventsTab, AgendaTab, HistoryTab)
            val navController = rememberNavController()

            ISENSmartCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
                        NavHost(navController = navController, startDestination = MainTab.title) {
                            composable(MainTab.title) {
                                MainScreen()
                            }
                            composable(EventsTab.title) {
                                EventScreen()
                            }
                            composable(AgendaTab.title) {
                                Text(AgendaTab.title)
                            }
                            composable(HistoryTab.title) {
                                Text(HistoryTab.title)
                            }
                        }
                    }
                }
            }
        }
    }
}


// Aperçu pour vérifier l'interface
@Preview(showBackground = true)
@Composable
fun PreviewFullScreenLayout() {
    ISENSmartCompanionTheme {
        MainScreen()
    }
}
