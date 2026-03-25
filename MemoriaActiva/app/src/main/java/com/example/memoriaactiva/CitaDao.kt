package com.example.memoriaactiva

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {
    @Query("SELECT * FROM tabla_citas ORDER BY fecha ASC, hora ASC")
    fun obtenerTodas(): Flow<List<Cita>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cita: Cita)

    @Delete
    suspend fun eliminar(cita: Cita)
}