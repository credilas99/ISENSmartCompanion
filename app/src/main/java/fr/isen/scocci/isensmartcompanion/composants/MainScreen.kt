package fr.isen.scocci.isensmartcompanion

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.isen.scocci.isensmartcompanion.ia.generateText  // Importez la fonction generateText
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    var userInput by remember { mutableStateOf("") } // Stocke la question posée par l'utilisateur
    val questionsAndResponses = remember { mutableStateListOf<Pair<String, String>>() } // Historique
    val context = LocalContext.current // Contexte nécessaire pour afficher un Toast
    val coroutineScope = rememberCoroutineScope() // Pour exécuter des tâches suspendues

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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                if (questionsAndResponses.isNotEmpty()) {
                    for ((question, response) in questionsAndResponses) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.End
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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Start
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
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
            )

            Button(
                onClick = {
                    if (userInput.isNotBlank()) {
                        coroutineScope.launch {
                            try {
                                // Utilisation de la fonction generateText pour obtenir la réponse de Gemini
                                val geminiResponse = generateText(userInput)
                                questionsAndResponses.add(
                                    Pair("Question : $userInput", "Réponse : $geminiResponse")
                                )
                            } catch (e: Exception) {
                                Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                            } finally {
                                userInput = "" // Réinitialiser le champ de saisie après envoi
                            }
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
    }
}

