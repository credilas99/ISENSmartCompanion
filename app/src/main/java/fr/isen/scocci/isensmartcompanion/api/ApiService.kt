package fr.isen.scocci.isensmartcompanion.api

import fr.isen.scocci.isensmartcompanion.Event
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("events.json")
    fun getEvents(): Call<List<Event>> // Requête GET pour obtenir la liste d'événements
}
