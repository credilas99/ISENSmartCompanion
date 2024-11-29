package fr.isen.scocci.isensmartcompanion.save

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import fr.isen.scocci.isensmartcompanion.R
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Récupérer les données de notification (titre, message) depuis les arguments
        val title = inputData.getString("title") ?: "Notification"
        val message = inputData.getString("message") ?: "Un événement vous attend !"

        // Créer et afficher la notification
        showNotification(title, message)
        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "event_notifications"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Créer un canal de notification pour les versions Android O et supérieures
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notifications d'événements",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Assurez-vous d'avoir un icône dans `res/drawable`
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

fun scheduleNotification(context: Context, title: String, message: String) {
    val notificationData = Data.Builder()
        .putString("title", title)
        .putString("message", message)
        .build()

    val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInputData(notificationData)
        .build()

    WorkManager.getInstance(context).enqueue(notificationRequest)
}
