package fr.isen.scocci.isensmartcompanion.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Locale


@Entity(tableName = "interactions")
data class Interaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val response: String,
    val date: Long = System.currentTimeMillis()
)

// Fonction pour formater la date
fun Interaction.getFormattedDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return dateFormat.format(date) // Date au format : jour/mois/année heure:minute:seconde
}
