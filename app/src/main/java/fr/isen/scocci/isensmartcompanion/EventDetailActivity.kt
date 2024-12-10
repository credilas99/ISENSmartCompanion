package fr.isen.scocci.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupération des extras de l'intent
        val eventId = intent.getStringExtra("eventId") ?: "ID inconnu"
        val eventTitle = intent.getStringExtra("eventTitle") ?: "Événement inconnu"
        val eventDate = intent.getStringExtra("eventDate") ?: "Date inconnue"
        val eventDescription =
            intent.getStringExtra("eventDescription") ?: "Description non disponible"
        val eventLocation = intent.getStringExtra("eventLocation") ?: "Lieu non spécifié"
        val eventCategory = intent.getStringExtra("eventCategory") ?: "Catégorie inconnue"

        setContent {
            EventDetailScreen(
                id = eventId,
                title = eventTitle,
                date = eventDate,
                description = eventDescription,
                location = eventLocation,
                category = eventCategory
            )
        }
    }
}

@Composable
fun EventDetailScreen(
    id: String,
    title: String,
    date: String,
    description: String,
    location: String,
    category: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.red_2))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Titre de l'événement
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 28.sp,
                color = colorResource(id = R.color.red_1)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Ligne séparatrice
        Spacer(modifier = Modifier.height(8.dp))

        // Date
        Text(
            text = "📅 Date : $date",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Lieu
        Text(
            text = "📍 Lieu : $location",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Catégorie
        Text(
            text = "📂 Catégorie : $category",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Description
        Text(
            text = "Description :",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Ligne séparatrice
        Spacer(modifier = Modifier.height(16.dp))

        // ID de l'événement
        Text(
            text = "ID de l'événement : $id",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
