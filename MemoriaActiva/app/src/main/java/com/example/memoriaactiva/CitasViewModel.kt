package com.example.memoriaactiva

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CitasViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).citaDao()

    // citas en tiempo real
    val listaCitas: Flow<List<Cita>> = dao.obtenerTodas()

    fun agregarCita(doctor: String, especialidad: String, fecha: String, hora: String, notas: String) {
        viewModelScope.launch {
            val nuevaCita = Cita(
                doctor = doctor,
                especialidad = especialidad,
                fecha = fecha,
                hora = hora,
                notas = notas
            )
            dao.insertar(nuevaCita)
        }
    }

    fun eliminarCita(cita: Cita) {
        viewModelScope.launch {
            dao.eliminar(cita)
        }
    }
}