package com.example.memoriaactiva

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_medicamentos")
data class Medicamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val hora: String,
    val tomado: Boolean = false
)