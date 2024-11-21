package fr.isen.scocci.isensmartcompanion

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


// Fonction principale
@Composable
fun MainScreen() {
    var userInput by remember { mutableStateOf("") } // Stocke la question posée par l'utilisateur

    // Liste contenant des paires (question, réponse) pour l'historique
    val questionsAndResponses = remember { mutableStateListOf<Pair<String, String>>() }

    // Contexte nécessaire pour afficher un Toast
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80F7A0A0)) // Remplacez avec une couleur de fond spécifique
            .padding(16.dp)
    ) {
        // Contenu principal (image et texte)
        Spacer(modifier = Modifier.height(30.dp))

        // Image en haut de l'écran
        Image(
            painter = painterResource(id = R.drawable.isen_brest_1), // Remplacez avec votre image dans res/drawable
            contentDescription = "Description de l'image",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            alignment = Alignment.Center
        )

        // Texte centré "Smart Companion"
        Text(
            text = "Smart Companion",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center // Centre le texte
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section défilable uniquement pour l'historique des questions et réponses
        Box(
            modifier = Modifier
                .weight(1f) // Cette section occupera tout l'espace disponible avant la Row du bas
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()) // Permet de défiler uniquement cette section
            ) {
                if (questionsAndResponses.isNotEmpty()) {
                    for ((question, historyResponse) in questionsAndResponses) {
                        // Affichage de la question (à droite)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.End // Aligner à droite
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFFE0E0E0),
                                        shape = MaterialTheme.shapes.medium
                                    ) // Couleur de fond pour la bulle de la question
                                    .padding(8.dp)
                                    .widthIn(min = 120.dp) // Largeur minimale
                            ) {
                                Text(
                                    text = question,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }
                        }

                        // Affichage de la réponse (à gauche)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Start // Aligner à gauche
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFFB0E0A8),
                                        shape = MaterialTheme.shapes.medium
                                    ) // Couleur de fond pour la bulle de réponse
                                    .padding(8.dp)
                                    .widthIn(min = 120.dp) // Largeur minimale
                            ) {
                                Text(
                                    text = historyResponse,  // Utilisation de `historyResponse` pour la réponse dans l'historique
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacement avant la ligne de saisie

        // Champ de saisie et bouton
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp), // Espacement par rapport aux bords
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Champ de saisie pour poser une question
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Posez une question ?") },
                modifier = Modifier
                    .weight(1f) // Prend toute la place disponible
                    .padding(end = 6.dp) // Espace entre le champ et le bouton
            )

            // Bouton "Envoyer" aligné à droite
            Button(
                onClick = {
                    if (userInput.isNotBlank()) {
                        // Ajouter la question à l'historique et la réponse associée
                        val newResponse =
                            "Réponse : Merci de votre question"  // Remplacez par la logique de réponse réelle
                        questionsAndResponses.add(
                            Pair(
                                "Question : $userInput",
                                newResponse
                            )
                        )  // Retirer les guillemets autour de la question

                        // Afficher un Toast
                        Toast.makeText(context, "Question envoyée", Toast.LENGTH_SHORT).show()

                        // Réinitialise le champ de saisie
                        userInput = ""
                    } else {
                        // Si le champ est vide
                        Toast.makeText(context, "Veuillez entrer une question.", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.red_1) // Accès à la couleur via colorResource
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Envoyer")
            }
        }
    }
}