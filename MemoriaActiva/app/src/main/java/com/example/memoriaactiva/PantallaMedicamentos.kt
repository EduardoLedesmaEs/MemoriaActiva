package com.example.memoriaactiva


import android.R
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

// Estructura de datos
data class Medicamento(
    val id: Int,
    val nombre: String,
    val hora: String,
    val tomado: Boolean = false
)

@Composable
fun PantallaMedicamentos() {
    val context = LocalContext.current

    // Lista persistente en la sesión
    val listaMedicamentos = remember { mutableStateListOf<Medicamento>() }

    // VARIABLES CON 'BY' (Necesitan import androidx.compose.runtime.*)
    var nombre by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("Seleccionar hora") }

    // Lógica del Reloj
    val calendario = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            hora = String.format("%02d:%02d", hour, minute)
        },
        calendario.get(Calendar.HOUR_OF_DAY),
        calendario.get(Calendar.MINUTE),
        true
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("MIS MEDICAMENTOS", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de la medicina") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de hora que abre el reloj
        OutlinedTextField(
            value = hora,
            onValueChange = { },
            label = { Text("Hora de consumo") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() },
            enabled = false, // Evita que salga el teclado
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )

        Button(
            onClick = {
                if (nombre.isNotBlank() && hora != "Seleccionar hora") {
                    listaMedicamentos.add(Medicamento(listaMedicamentos.size + 1, nombre, hora))
                    nombre = ""
                    hora = "Seleccionar hora"
                }
            },
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth()
        ) {
            Text("AGREGAR MEDICAMENTO")
        }
        val pendientes = listaMedicamentos.filter { !it.tomado }
        val completadas = listaMedicamentos.filter { it.tomado }
        Divider()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Sección: POR HACER
            item {
                Text("POR HACER", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            }

            items(pendientes, key = { it.id }) { med ->
                TarjetaMedicamento(med, listaMedicamentos)
            }

            // Sección: COMPLETADAS
            if (completadas.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("COMPLETADAS", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
                }

                items(completadas, key = { it.id }) { med ->
                    TarjetaMedicamento(med, listaMedicamentos)
                }
            }
        }
    }
}
// Función auxiliar para no repetir código de la tarjeta
@Composable
fun TarjetaMedicamento(med: Medicamento, listaCompleta: SnapshotStateList<Medicamento>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = med.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (med.tomado) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (med.tomado) Color.Gray else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Hora: ${med.hora}",
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (med.tomado) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (med.tomado) Color.Gray else MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(onClick = { listaCompleta.remove(med) }) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }

            Checkbox(
                checked = med.tomado,
                onCheckedChange = { valor ->
                    val index = listaCompleta.indexOf(med)
                    if (index != -1) {
                        listaCompleta[index] = med.copy(tomado = valor)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50),
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )
        }
    }
}