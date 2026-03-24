package com.example.memoriaactiva

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicamentosViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.medicamentoDao()

    private val _listaMedicamentos = MutableStateFlow<List<Medicamento>>(emptyList())
    val listaMedicamentos: StateFlow<List<Medicamento>> = _listaMedicamentos.asStateFlow()

    init {
        viewModelScope.launch {
            dao.obtenerTodos().collect {
                _listaMedicamentos.value = it
            }
        }
    }

    fun agregarMedicamento(nombre: String, hora: String) {
        viewModelScope.launch {
            val nuevo = Medicamento(
                nombre = nombre,
                hora = hora
            )
            dao.insertar(nuevo)
        }
    }

    fun eliminarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            dao.eliminar(medicamento)
        }
    }

    fun cambiarEstado(medicamento: Medicamento, tomado: Boolean) {
        viewModelScope.launch {
            dao.actualizar(medicamento.copy(tomado = tomado))
        }
    }
}