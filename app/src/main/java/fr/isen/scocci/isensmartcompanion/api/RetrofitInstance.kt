package fr.isen.scocci.isensmartcompanion.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL de base pour les requêtes
            .addConverterFactory(GsonConverterFactory.create()) // Convertisseur Gson
            .build()
            .create(ApiService::class.java)
    }
}