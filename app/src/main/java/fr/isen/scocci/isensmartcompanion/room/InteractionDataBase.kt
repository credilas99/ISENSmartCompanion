package fr.isen.scocci.isensmartcompanion.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Interaction::class], version = 1, exportSchema = false)
abstract class InteractionDatabase : RoomDatabase() {

    abstract fun interactionDao(): InteractionDao

    companion object {
        @Volatile
        private var INSTANCE: InteractionDatabase? = null

        fun getDatabase(context: Context): InteractionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InteractionDatabase::class.java,
                    "interaction_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

