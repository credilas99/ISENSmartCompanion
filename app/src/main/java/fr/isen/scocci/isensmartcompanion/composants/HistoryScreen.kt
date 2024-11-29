package fr.isen.scocci.isensmartcompanion.composants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.scocci.isensmartcompanion.R
import fr.isen.scocci.isensmartcompanion.room.InteractionDao
import kotlinx.coroutines.launch
import fr.isen.scocci.isensmartcompanion.room.getFormattedDate  // Assurez-vous d'importer la fonction de formatage

@Composable
fun HistoryScreen(interactionDao: InteractionDao) {
    val interactions by interactionDao.getAllInteractions().collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope() // Définit le scope pour les actions asynchrones

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0x80F7A0A0))
        .padding(16.dp)) {

        // Titre de la page d'historique
        Text(
            text = "Historique des interactions",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 24.sp),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Affichage de l'historique dans une LazyColumn
        LazyColumn(
            modifier = Modifier.weight(1f) // Prend tout l'espace disponible
        ) {
            items(interactions) { interaction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    // Column pour afficher la question, réponse et la date
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // Limite la largeur de la colonne
                            .padding(12.dp)
                            .background(colorResource(id = R.color.red_2), shape = MaterialTheme.shapes.medium) // Applique la couleur red_2 comme fond
                    ) {
                        Text(
                            text = "Question : ${interaction.question}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Réponse : ${interaction.response}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        // Affichage de la date formatée
                        Text(
                            text = "Date : ${interaction.getFormattedDate()}",  // Appel de la fonction getFormattedDate()
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    // Bouton de suppression avec icône
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                interactionDao.deleteById(interaction.id)
                            }
                        },
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer l'interaction")
                    }
                }
            }
        }

        // Bouton pour effacer tout l'historique
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    interactionDao.deleteAll()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFf44336)) // Couleur rouge
        ) {
            Text(text = "Effacer l'historique", color = Color.White)
        }
    }
}
