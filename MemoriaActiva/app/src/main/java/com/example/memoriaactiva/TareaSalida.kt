package com.example.memoriaactiva

data class TareaSalida(
    val nombre: String,
    val icono: androidx.compose.ui.graphics.vector.ImageVector,
    var estaMarcada: Boolean = false
)