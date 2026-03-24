package com.example.memoriaactiva

data class Medicamento(
    val id: Int,
    val nombre: String,
    val hora: String,
    val tomado: Boolean = false
)