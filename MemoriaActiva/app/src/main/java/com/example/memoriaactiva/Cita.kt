package com.example.memoriaactiva

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val doctor: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val notas: String = ""
)