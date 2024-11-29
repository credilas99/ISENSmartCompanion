package fr.isen.scocci.isensmartcompanion.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractionDao {
    @Query("SELECT * FROM interactions ORDER BY date DESC")
    fun getAllInteractions(): Flow<List<Interaction>>

    @Insert
    suspend fun insert(interaction: Interaction)

    @Query("DELETE FROM interactions WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM interactions")
    suspend fun deleteAll()
}

