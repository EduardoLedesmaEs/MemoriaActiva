package com.example.memoriaactiva

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class TareaItemUI(
    val nombre: String,
    val icono: ImageVector,
    inicialMarcada: Boolean = false
) {
    var estaMarcada by mutableStateOf(inicialMarcada)
}

@Composable
fun PantallaSalida() {
    // 2. ACTUALIZAMOS EL NOMBRE DE LA CLASE EN LA LISTA
    val listaTareas = remember {
        mutableStateListOf(
            TareaItemUI("¿Llevas las llaves?", Icons.Default.Lock),
            TareaItemUI("¿Llevas el móvil?", Icons.Default.Phone),
            TareaItemUI("¿Llevas la cartera?", Icons.Default.ShoppingCart)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFDAD6)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color(0xFFBA1A1A),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Aviso",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF410002),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Parece que estás saliendo de casa...",
                        color = Color(0xFF410002)
                    )
                }
            }
        }

        Text(
            text = "Antes de salir",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF003366)
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaTareas) { tarea ->
                ItemTarea(tarea)
            }
        }
    }
}

// 3. ACTUALIZAMOS EL PARÁMETRO DE LA FUNCIÓN
@Composable
fun ItemTarea(tarea: TareaItemUI) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = tarea.icono,
                    contentDescription = null,
                    tint = if (tarea.estaMarcada) Color.Gray else Color(0xFF003366)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = tarea.nombre,
                    fontSize = 18.sp,
                    color = if (tarea.estaMarcada) Color.Gray else Color.Black
                )
            }
            Checkbox(
                checked = tarea.estaMarcada,
                onCheckedChange = { tarea.estaMarcada = it }
            )
        }
    }
}