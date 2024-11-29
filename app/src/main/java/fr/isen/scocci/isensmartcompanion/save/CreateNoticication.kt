package fr.isen.scocci.isensmartcompanion.save

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

// Créer le canal de notification
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "event_notifications" // ID du canal
        val channelName = "Événements" // Nom du canal
        val channelDescription = "Notifications pour les événements" // Description du canal
        val importance = NotificationManager.IMPORTANCE_HIGH // Niveau de priorité des notifications

        // Crée le canal de notification
        val channel = NotificationChannel(channelId, channelName, importance)
        channel.description = channelDescription

        // Enregistrer le canal auprès du système
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
