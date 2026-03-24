package com.example.memoriaactiva

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar
import java.util.Locale

@Composable
fun PantallaMedicamentos(miViewModel: MedicamentosViewModel = viewModel()) {
    val context = LocalContext.current

    // Estados locales para el formulario
    var nombreInput by remember { mutableStateOf("") }
    var horaInput by remember { mutableStateOf("Seleccionar hora") }

    // Observamos el StateFlow del ViewModel
    val listaMedicamentos by miViewModel.listaMedicamentos.collectAsState()

    // Lógica del Reloj
    val calendario = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            horaInput = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        },
        calendario.get(Calendar.HOUR_OF_DAY),
        calendario.get(Calendar.MINUTE),
        true
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("MIS MEDICAMENTOS", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombreInput,
            onValueChange = { nombreInput = it },
            label = { Text("Nombre de la medicina") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = horaInput,
            onValueChange = { },
            label = { Text("Hora de consumo") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() },
            readOnly = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Button(
            onClick = {
                if (nombreInput.isNotBlank() && horaInput != "Seleccionar hora") {
                    miViewModel.agregarMedicamento(nombreInput, horaInput)
                    nombreInput = ""
                    horaInput = "Seleccionar hora"
                }
            },
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth()
        ) {
            Text("AGREGAR MEDICAMENTO")
        }

        // Filtramos la lista ya colectada
        val pendientes = listaMedicamentos.filter { !it.tomado }
        val completadas = listaMedicamentos.filter { it.tomado }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Sección Pendientes
            item {
                Text("POR HACER", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }

            items(pendientes, key = { it.id }) { med ->
                TarjetaMedicamento(
                    med = med,
                    onEliminar = { miViewModel.eliminarMedicamento(it) },
                    onCambiarEstado = { m, v -> miViewModel.cambiarEstado(m, v) }
                )
            }

            // Sección Completadas
            if (completadas.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("COMPLETADAS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                items(completadas, key = { it.id }) { med ->
                    TarjetaMedicamento(
                        med = med,
                        onEliminar = { miViewModel.eliminarMedicamento(it) },
                        onCambiarEstado = { m, v -> miViewModel.cambiarEstado(m, v) }
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaMedicamento(
    med: Medicamento,
    onEliminar: (Medicamento) -> Unit,
    onCambiarEstado: (Medicamento, Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = med.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (med.tomado) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (med.tomado) Color.Gray else Color.Unspecified
                )
                Text(
                    text = "Hora: ${med.hora}",
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (med.tomado) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (med.tomado) Color.Gray else Color.Unspecified
                )
            }
            IconButton(onClick = { onEliminar(med) }) {
                Icon(Icons.Default.Delete, "Borrar", tint = MaterialTheme.colorScheme.error)
            }
            Checkbox(
                checked = med.tomado,
                onCheckedChange = { onCambiarEstado(med, it) },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4CAF50))
            )
        }
    }
}