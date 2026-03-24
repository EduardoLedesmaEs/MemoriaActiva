package com.example.memoriaactiva

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicamentoDao {
    @Query("SELECT * FROM tabla_medicamentos")
    fun obtenerTodos(): Flow<List<Medicamento>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(medicamento: Medicamento)

    @Delete
    suspend fun eliminar(medicamento: Medicamento)

    @Update
    suspend fun actualizar(medicamento: Medicamento)
}