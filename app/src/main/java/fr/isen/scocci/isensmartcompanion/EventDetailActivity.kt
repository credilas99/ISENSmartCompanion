package fr.isen.scocci.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupération des extras de l'intent
        val eventTitle = intent.getStringExtra("eventTitle") ?: "Événement inconnu"
        val eventDate = intent.getStringExtra("eventDate") ?: "Date inconnue"
        val eventDescription =
            intent.getStringExtra("eventDescription") ?: "Description non disponible"

        setContent {
            // Affichage de l'interface composable
            EventDetailScreen(eventTitle, eventDate, eventDescription)
        }
    }
}

@Composable
fun EventDetailScreen(title: String, date: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche le titre de l'événement
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp) // Correction : Utilisation de sp pour la taille de la police
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les éléments

        // Affiche la date de l'événement
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp) // Correction : Utilisation de sp pour la taille de la police
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacement entre les éléments

        // Affiche la description de l'événement
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp) // Correction : Utilisation de sp pour la taille de la police
        )
    }
}
