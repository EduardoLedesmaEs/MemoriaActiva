package com.example.memoriaactiva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Rutas {
    const val INICIO = "inicio"
    const val MEDICAMENTOS = "medicamentos"
    const val SALIDA = "salida"
    const val CITAS = "citas"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                // EL SCAFFOLD
                Scaffold(
                    bottomBar = {
                        // Quitamos el IF para que la barra no se destruya al ir al inicio
                        SeccionesNav(navController = navController)
                    }
                ) { innerPadding ->
                    // Aplicamos el padding para que el menú no tape el contenido
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Rutas.INICIO
                        ) {
                            composable(Rutas.INICIO) {
                                PantallaInicio(
                                    onNavegarAMedicamentos = { navController.navigate(Rutas.MEDICAMENTOS) },
                                    onNavegarASalida = { navController.navigate(Rutas.SALIDA) },
                                    onNavegarACitas = { navController.navigate(Rutas.CITAS) }
                                )
                            }

                            composable(Rutas.MEDICAMENTOS) { PantallaMedicamentos() }
                            composable(Rutas.SALIDA) { PantallaSalida() }
                            composable(Rutas.CITAS) { PantallaCitas() }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun PantallaInicio(
    onNavegarAMedicamentos: () -> Unit,
    onNavegarASalida: () -> Unit,
    onNavegarACitas: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "MEMORIA ACTIVA",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF005599),
            modifier = Modifier.padding(vertical = 24.dp)
        )

        BotonGigante("TOMAR MEDICAMENTOS", Color(0xFF336699)) { onNavegarAMedicamentos() }
        BotonGigante("CITAS MÉDICAS", Color(0xFF2E8B57)) { onNavegarACitas() }
        BotonGigante("¿SALES DE CASA?", Color(0xFFD2691E)) { onNavegarASalida() }
        BotonGigante("BEBER AGUA", Color(0xFF4682B4)) { /* Proximamente */ }
    }
}

@Composable
fun BotonGigante(texto: String, colorFondo: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo)
    ) {
        Text(text = texto, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}