package fr.isen.scocci.isensmartcompanion.save

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("event_prefs", Context.MODE_PRIVATE)

    // Vérifie si un événement est notifié
    fun isEventNotified(eventId: String): Boolean {
        return preferences.getBoolean(eventId, false)
    }

    // Définit l'état de notification pour un événement
    fun setEventNotification(eventId: String, isNotified: Boolean) {
        preferences.edit().putBoolean(eventId, isNotified).apply()
    }

}
