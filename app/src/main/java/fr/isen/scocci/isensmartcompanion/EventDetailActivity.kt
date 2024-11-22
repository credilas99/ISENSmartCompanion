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
        val eventId = intent.getStringExtra("eventId") ?: "ID inconnu" // Changement ici
        val eventTitle = intent.getStringExtra("eventTitle") ?: "Événement inconnu"
        val eventDate = intent.getStringExtra("eventDate") ?: "Date inconnue"
        val eventDescription = intent.getStringExtra("eventDescription") ?: "Description non disponible"
        val eventLocation = intent.getStringExtra("eventLocation") ?: "Lieu non spécifié"
        val eventCategory = intent.getStringExtra("eventCategory") ?: "Catégorie inconnue"

        setContent {
            // Affichage de l'interface composable
            EventDetailScreen(
                eventId, // Le paramètre eventId est maintenant un String
                eventTitle,
                eventDate,
                eventDescription,
                eventLocation,
                eventCategory
            )
        }
    }
}

@Composable
fun EventDetailScreen(
    id: String, // Le type de id est maintenant String
    title: String,
    date: String,
    description: String,
    location: String,
    category: String
) {
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
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les éléments

        // Affiche la date de l'événement
        Text(
            text = "Date : $date",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les éléments

        // Affiche le lieu de l'événement
        Text(
            text = "Lieu : $location",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les éléments

        // Affiche la catégorie de l'événement
        Text(
            text = "Catégorie : $category",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacement entre les éléments

        // Affiche la description de l'événement
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp)
        )

        // Afficher l'ID de l'événement
        Spacer(modifier = Modifier.height(16.dp)) // Espacement entre les éléments
        Text(
            text = "ID de l'événement : $id", // Affiche l'ID de l'événement
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
        )
    }
}
