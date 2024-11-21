package fr.isen.scocci.isensmartcompanion

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Liste d'événements fictifs
data class Event(val title: String, val date: String, val description: String)

val eventList = listOf(
    Event(
        "Soirée BDE",
        "25 Nov 2024",
        "Une soirée organisée par le BDE avec de nombreuses animations."
    ),
    Event(
        "Gala de l'ISEN",
        "10 Déc 2024",
        "Un gala prestigieux pour les étudiants et leurs familles."
    ),
    Event(
        "Journée de Cohésion",
        "15 Jan 2025",
        "Activités en plein air pour renforcer l'esprit d'équipe."
    ),
    Event(
        "Hackathon ISEN",
        "20 Fév 2025",
        "Un événement de codage intense pour les développeurs en herbe."
    ),
    Event(
        "Conférence sur l'IA",
        "5 Mars 2025",
        "Exploration des dernières tendances en intelligence artificielle."
    )
)

@Composable
fun EventScreen() {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espacement entre les éléments
    ) {
        items(eventList) { event ->
            EventItem(event) {
                // Action au clic sur un événement : navigation vers l'écran de détails
                val intent = Intent(context, EventDetailActivity::class.java).apply {
                    putExtra("eventTitle", event.title)
                    putExtra("eventDate", event.date)
                    putExtra("eventDescription", event.description)
                }
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = event.date,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            )
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                maxLines = 2 // Limite le texte à 2 lignes pour éviter l'encombrement
            )
        }
    }
}
