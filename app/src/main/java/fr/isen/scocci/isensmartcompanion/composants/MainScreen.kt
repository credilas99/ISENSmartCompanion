package fr.isen.scocci.isensmartcompanion.composants

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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.isen.scocci.isensmartcompanion.R
import fr.isen.scocci.isensmartcompanion.ia.generateText
import fr.isen.scocci.isensmartcompanion.room.Interaction
import fr.isen.scocci.isensmartcompanion.room.InteractionDatabase
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    var userInput by remember { mutableStateOf("") } // Stocke la question posée par l'utilisateur
    val questionsAndResponses = remember { mutableStateListOf<Pair<String, String>>() } // Historique
    val context = LocalContext.current // Contexte nécessaire pour afficher un Toast
    val coroutineScope = rememberCoroutineScope() // Pour exécuter des tâches suspendues

    // FocusRequester pour le TextField
    val focusRequester = remember { FocusRequester() }

    val database = InteractionDatabase.getDatabase(context)
    val interactionDao = database.interactionDao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80F7A0A0))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.isen_brest_1),
            contentDescription = "Description de l'image",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            alignment = Alignment.Center
        )

        Text(
            text = "Smart Companion",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()) // Scroll pour l'historique
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Afficher l'historique sous forme de conversation
                if (questionsAndResponses.isNotEmpty()) {
                    for ((question, response) in questionsAndResponses) {
                        // Affichage de la question de l'utilisateur (à gauche)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFFE0E0E0),
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .padding(8.dp)
                                    .widthIn(min = 120.dp)
                            ) {
                                Text(
                                    text = question,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }
                        }

                        // Affichage de la réponse de Gemini (à droite)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFFB0E0A8),
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .padding(8.dp)
                                    .widthIn(min = 120.dp)
                            ) {
                                Text(
                                    text = response,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ajustement de la Row avec le TextField et Button pour tenir compte de l'IME (clavier)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding() // Ajoute un padding pour le clavier
                .padding(bottom = 100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Posez une question ?") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp)
                    .focusRequester(focusRequester) // Assure le focus
            )

            Button(
                onClick = {
                    if (userInput.isNotBlank()) {
                        coroutineScope.launch {
                            val geminiResponse = getGeminiResponse(userInput, context)
                            geminiResponse?.let { response ->
                                // Ajouter la question et la réponse à l'historique
                                questionsAndResponses.add(Pair(userInput, response))

                                // Insérer dans la base de données
                                val interaction = Interaction(question = userInput, response = response)
                                interactionDao.insert(interaction)
                            }
                            userInput = "" // Réinitialiser le champ de saisie après envoi
                        }
                    } else {
                        Toast.makeText(context, "Veuillez entrer une question.", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.red_1)
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Envoyer")
            }
        }

        // Demande le focus au démarrage de l'écran
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

// Fonction dédiée pour obtenir la réponse de Gemini
suspend fun getGeminiResponse(userInput: String, context: android.content.Context): String? {
    return try {
        generateText(userInput) // Appel de la fonction pour générer la réponse
    } catch (e: Exception) {
        Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
        null
    }
}
