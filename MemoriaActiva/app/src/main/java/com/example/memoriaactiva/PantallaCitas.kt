package com.example.memoriaactiva

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

@Composable
fun PantallaCitas(citasVM: CitasViewModel = viewModel()) {
    val context = LocalContext.current
    val listaCitas by citasVM.listaCitas.collectAsState(initial = emptyList())

    // Estados para el formulario
    var doctor by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("Seleccionar fecha") }
    var hora by remember { mutableStateOf("Seleccionar hora") }

    val calendario = Calendar.getInstance()

    // Selector de Fecha
    val dateDialog = DatePickerDialog(context, { _, y, m, d ->
        fecha = "$d/${m + 1}/$y"
    }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH))

    // Selector de Hora
    val timeDialog = TimePickerDialog(context, { _, h, min ->
        hora = String.format("%02d:%02d", h, min)
    }, calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), true)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("CITAS MÉDICAS", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = doctor, onValueChange = { doctor = it }, label = { Text("Nombre del Doctor") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = especialidad, onValueChange = { especialidad = it }, label = { Text("Especialidad") }, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = fecha, onValueChange = {}, readOnly = true, label = { Text("Fecha") },
                modifier = Modifier.weight(1f).clickable { dateDialog.show() }, enabled = false
            )
            OutlinedTextField(
                value = hora, onValueChange = {}, readOnly = true, label = { Text("Hora") },
                modifier = Modifier.weight(1f).clickable { timeDialog.show() }, enabled = false
            )
        }

        Button(
            onClick = {
                if (doctor.isNotBlank() && fecha != "Seleccionar fecha") {
                    citasVM.agregarCita(doctor, especialidad, fecha, hora, "")
                    doctor = ""; especialidad = ""; fecha = "Seleccionar fecha"; hora = "Seleccionar hora"
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) { Text("AGENDAR CITA") }

        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
            items(listaCitas) { cita ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(cita.doctor, style = MaterialTheme.typography.titleMedium)
                            Text(cita.especialidad, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            Text("📅 ${cita.fecha} - 🕒 ${cita.hora}", style = MaterialTheme.typography.bodyMedium)
                        }
                        IconButton(onClick = { citasVM.eliminarCita(cita) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}