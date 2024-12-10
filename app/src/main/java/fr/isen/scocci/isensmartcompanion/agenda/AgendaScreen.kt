package fr.isen.scocci.isensmartcompanion.agenda

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.scocci.isensmartcompanion.EventDetailActivity
import fr.isen.scocci.isensmartcompanion.R
import fr.isen.scocci.isensmartcompanion.save.SharedPreferences
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class Cours(
    val title: String,
    val description: String,
    val time: String
)

data class AgendaEvent(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgendaScreen() {
    // Récupérer les événements notifiés depuis SharedPreferences
    val context = LocalContext.current
    val preferencesHelper = SharedPreferences(context)
    val notifiedEvents = remember { preferencesHelper.getNotifiedEvents() }

    // State pour savoir quel onglet est sélectionné
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80F7A0A0))
            .padding(16.dp)
    ) {
        // Titre "Agenda" en rouge au-dessus des onglets
        Text(
            text = "Agenda",
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Bar de navigation pour sélectionner entre les cours et événements
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Cours") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Événements") }
            )
        }

        // Affichage dynamique basé sur l'onglet sélectionné
        when (selectedTab) {
            0 -> CalendarWithCours()
            1 -> EventList(
                events = notifiedEvents,
                onEventClick = { event ->
                    val intent = Intent(context, EventDetailActivity::class.java).apply {
                        putExtra("eventId", event.id)
                        putExtra("eventTitle", event.title)
                        putExtra("eventDate", event.date)
                        putExtra("eventLocation", event.location)
                        putExtra("eventDescription", event.description)
                        putExtra("eventCategory", event.category)
                    }
                    context.startActivity(intent)
                }
            )
        }
    }
}

// Composant pour afficher le calendrier avec les cours
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarWithCours() {
    val months = listOf(
        "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
        "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    )

    val coursList = listOf(
        Cours("Mathematics 101", "Calculus Basics", "10:00 AM"),
        Cours("Physics 201", "Mechanics Overview", "1:00 PM"),
        Cours("Computer Science 301", "Advanced Programming", "3:00 PM"),
        Cours("Philosophy 101", "Introduction to Ethics", "9:00 AM"),
        Cours("History of Art", "Baroque Art", "11:00 AM")
    )

    // Variable d'état pour afficher les détails du jour
    var showDialog by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf<Int?>(null) }

    // Utilisation de LazyColumn pour rendre les mois scrollables verticalement
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(bottom = 46.dp)
    ) {
        months.forEachIndexed { index, month ->
            item {
                // Affichage du mois en titre
                Text(
                    text = "$month 2025",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Red,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Définir le nombre de jours pour chaque mois
                val daysInMonth = when (index) {
                    0 -> 31  // Janvier
                    1 -> 28  // Février (année non bissextile)
                    2 -> 31  // Mars
                    3 -> 30  // Avril
                    4 -> 31  // Mai
                    5 -> 30  // Juin
                    6 -> 31  // Juillet
                    7 -> 31  // Août
                    8 -> 30  // Septembre
                    9 -> 31  // Octobre
                    10 -> 30 // Novembre
                    11 -> 31 // Décembre
                    else -> 31
                }

                // Générer la grille des jours avec LazyVerticalGrid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp), // Ajout de hauteur maximale pour éviter l'infini
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items((1..daysInMonth).toList()) { day ->
                        DayWithCours(
                            day = day,
                            onClick = {
                                selectedDay = day
                                showDialog = true
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))  // Espacement entre les mois
            }
        }
    }

    // Affiche un dialog si un jour est sélectionné
    if (showDialog && selectedDay != null) {
        selectedDay?.let { day ->
            DayDetailsDialog(
                day = day,
                cours = coursList.shuffled().take((1..2).random()), // Cours aléatoires
                onDismiss = {
                    showDialog = false
                    selectedDay = null
                }
            )
        }
    }
}

// Composant pour afficher un jour (case du calendrier)
@Composable
fun DayWithCours(day: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.red_2)),
        onClick = onClick // Détecte le clic
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "$day",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Composant pour afficher les détails d'un jour dans un dialog
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayDetailsDialog(day: Int, cours: List<Cours>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            val dayName = LocalDate.of(2024, 12, day)
                .dayOfWeek
                .getDisplayName(TextStyle.FULL, Locale.FRENCH)
            Text("Jour $day - $dayName")
        },
        text = {
            Column {
                if (cours.isNotEmpty()) {
                    cours.forEach { coursItem ->
                        Text("- ${coursItem.title} à ${coursItem.time}")
                    }
                } else {
                    Text("Aucun cours pour ce jour.")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer")
            }
        }
    )
}

// Composant pour afficher la liste des événements notifiés
@Composable
fun EventList(events: List<AgendaEvent>, onEventClick: (AgendaEvent) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .padding(bottom = 72.dp)
    ) {
        items(events) { event ->
            EventItem(
                event = event,
                onClick = { onEventClick(event) }
            )
        }
    }
}

// Composant pour afficher chaque item d'événement
@Composable
fun EventItem(
    event: AgendaEvent,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.red_2))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${event.date}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Lieu: ${event.location}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Catégorie: ${event.category}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${event.description}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Aperçu pour vérifier l'interface
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAgendaScreen() {
    AgendaScreen()
}