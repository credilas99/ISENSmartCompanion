package fr.isen.scocci.isensmartcompanion.save

import android.content.Context
import fr.isen.scocci.isensmartcompanion.agenda.AgendaEvent
import fr.isen.scocci.isensmartcompanion.composants.Event

class SharedPreferences(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Sauvegarder un événement notifié avec tous ses détails
    fun setEventNotification(event: Event, isNotified: Boolean) {
        val editor = sharedPreferences.edit()
        if (isNotified) {
            editor.putString("event_${event.id}_title", event.title)
            editor.putString("event_${event.id}_description", event.description)
            editor.putString("event_${event.id}_date", event.date)
            editor.putString("event_${event.id}_location", event.location)
            editor.putString("event_${event.id}_category", event.category)
            editor.putBoolean("event_${event.id}_notified", true)
        } else {
            editor.remove("event_${event.id}_title")
            editor.remove("event_${event.id}_description")
            editor.remove("event_${event.id}_date")
            editor.remove("event_${event.id}_location")
            editor.remove("event_${event.id}_category")
            editor.remove("event_${event.id}_notified")
        }
        editor.apply()
    }

    // Vérifier si un événement est notifié
    fun isEventNotified(eventId: String): Boolean {
        return sharedPreferences.getBoolean("event_${eventId}_notified", false)
    }

    // Récupérer tous les événements notifiés
    fun getNotifiedEvents(): List<AgendaEvent> {
        val notifiedEvents = mutableListOf<AgendaEvent>()

        sharedPreferences.all.keys.forEach { key ->
            if (key.startsWith("event_") && key.endsWith("_notified")) {
                val eventId = key.replace("_notified", "")
                val title = sharedPreferences.getString("${eventId}_title", "")
                val description = sharedPreferences.getString("${eventId}_description", "")
                val date = sharedPreferences.getString("${eventId}_date", "")
                val location = sharedPreferences.getString("${eventId}_location", "")
                val category = sharedPreferences.getString("${eventId}_category", "")
                val id = sharedPreferences.getString("${eventId}_id", "")

                if (title != null) {
                    if (title.isNotEmpty()) {
                        notifiedEvents.add(
                            AgendaEvent(
                                id = id ?: "",
                                title = title,
                                description = description ?: "",
                                date = date ?: "",
                                location = location ?: "",
                                category = category ?: ""
                            )
                        )
                    }
                }
            }
        }

        return notifiedEvents
    }
}

