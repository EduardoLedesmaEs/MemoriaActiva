package com.example.memoriaactiva

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MedicamentosViewModel : ViewModel() {

    private val _listaMedicamentos = mutableStateListOf<Medicamento>()
    val listaMedicamentos: List<Medicamento> = _listaMedicamentos

    fun agregarMedicamento(nombre: String, hora: String) {
        val nuevo = Medicamento(
            id = if (_listaMedicamentos.isEmpty()) 1 else _listaMedicamentos.last().id + 1,
            nombre = nombre,
            hora = hora
        )
        _listaMedicamentos.add(nuevo)
    }

    fun eliminarMedicamento(medicamento: Medicamento) {
        _listaMedicamentos.remove(medicamento)
    }

    fun cambiarEstado(medicamento: Medicamento, tomado: Boolean) {
        val index = _listaMedicamentos.indexOf(medicamento)
        if (index != -1) {
            _listaMedicamentos[index] = medicamento.copy(tomado = tomado)
        }
    }
}