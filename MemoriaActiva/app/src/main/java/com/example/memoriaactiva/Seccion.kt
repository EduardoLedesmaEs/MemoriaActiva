package com.example.memoriaactiva

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Seccion(val ruta: String, val titulo: String, val icono: ImageVector) {
    object Inicio : Seccion("inicio", "Inicio", Icons.Default.Home)
    object Medicamentos : Seccion("medicamentos", "Meds", Icons.Default.Favorite)
    object Citas : Seccion("citas", "Citas", Icons.Default.DateRange)
    object Salida : Seccion("salida", "Objetos", Icons.Default.CheckCircle)
}