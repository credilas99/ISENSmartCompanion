package fr.isen.scocci.isensmartcompanion

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.scocci.isensmartcompanion.composants.AgendaScreen
import fr.isen.scocci.isensmartcompanion.composants.EventScreen
import fr.isen.scocci.isensmartcompanion.composants.HistoryScreen
import fr.isen.scocci.isensmartcompanion.composants.MainScreen
import fr.isen.scocci.isensmartcompanion.room.InteractionDatabase
import fr.isen.scocci.isensmartcompanion.save.createNotificationChannel
import fr.isen.scocci.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

class MainActivity : ComponentActivity() {

    // Declare permission launcher variable
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Appel à createNotificationChannel pour créer le canal de notification
        createNotificationChannel(applicationContext)

        // Initialize the permission launcher
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Permission accordée, afficher la notification
                    Toast.makeText(
                        this,
                        "Permission accordée pour les notifications",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Permission refusée, informer l'utilisateur
                    Toast.makeText(
                        this,
                        "Permission refusée pour les notifications",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        // Étape 1 : Initialisation de la base de données et du DAO
        val database =
            InteractionDatabase.getDatabase(applicationContext) // Méthode pour obtenir la DB
        val interactionDao = database.interactionDao() // Récupération de l'instance DAO

        // Appel à setContent
        setContent {
            val mainTab = TabBarItem(
                title = "main",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            )
            val eventsTab = TabBarItem(
                title = "events",
                selectedIcon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications
            )
            val agendaTab = TabBarItem(
                title = "agenda",
                selectedIcon = Icons.Filled.Event,
                unselectedIcon = Icons.Outlined.Event
            )
            val historyTab = TabBarItem(
                title = "history",
                selectedIcon = Icons.Filled.History,
                unselectedIcon = Icons.Outlined.History
            )
            val tabBarItems = listOf(mainTab, eventsTab, agendaTab, historyTab)
            val navController = rememberNavController()

            ISENSmartCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
                        NavHost(navController = navController, startDestination = mainTab.title) {
                            composable(mainTab.title) {
                                MainScreen()
                            }
                            composable(eventsTab.title) {
                                EventScreen()
                            }
                            composable(agendaTab.title) {
                                AgendaScreen()
                            }
                            composable(historyTab.title) {
                                // Utilisation du DAO dans HistoryScreen
                                HistoryScreen(interactionDao = interactionDao)
                            }
                        }
                    }
                }
            }
        }

        // Request permission when necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasNotificationPermission(this)) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Les versions antérieures à Android 13 n'ont pas besoin de cette permission
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
