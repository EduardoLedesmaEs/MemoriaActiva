package com.example.memoriaactiva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

object Rutas {
    const val INICIO = "inicio"
    const val MEDICAMENTOS = "medicamentos"
    const val CITAS = "citas"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val rutaActual = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (rutaActual != Rutas.INICIO) {
                            BarraNavegacionInferior(navController)
                        }
                    }
                ) { paddingInterno ->
                    NavHost(
                        navController = navController,
                        startDestination = Rutas.INICIO,
                        modifier = Modifier.padding(paddingInterno)
                    ) {
                        composable(Rutas.INICIO) {
                            PantallaInicio(
                                onNavegarAMedicamentos = { navController.navigate(Rutas.MEDICAMENTOS) },
                                onNavegarACitas = { navController.navigate(Rutas.CITAS) }
                            )
                        }
                        composable(Rutas.MEDICAMENTOS) { PantallaMedicamentos() }
                        composable(Rutas.CITAS) { PantallaCitas() }
                        composable("agua") { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Próximamente: Agua") } }
                        composable("salida") { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Próximamente: Salida") } }
                    }
                }
            }
        }
    }
}

@Composable
fun BarraNavegacionInferior(navController: androidx.navigation.NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, "Inicio") },
            label = { Text("Inicio") },
            selected = currentRoute == Rutas.INICIO,
            onClick = { 
                navController.navigate(Rutas.INICIO) { 
                    popUpTo(Rutas.INICIO) { inclusive = true } 
                } 
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, "Meds") },
            label = { Text("Meds") },
            selected = currentRoute == Rutas.MEDICAMENTOS,
            onClick = { navController.navigate(Rutas.MEDICAMENTOS) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DateRange, "Citas") },
            label = { Text("Citas") },
            selected = currentRoute == Rutas.CITAS,
            onClick = { navController.navigate(Rutas.CITAS) }
        )
    }
}

@Composable
fun PantallaInicio(onNavegarAMedicamentos: () -> Unit, onNavegarACitas: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
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
        BotonGigante("¿SALES DE CASA?", Color(0xFFD2691E)) { /* Próximamente */ }
        BotonGigante("BEBER AGUA", Color(0xFF4682B4)) { /* Próximamente */ }
    }
}

@Composable
fun BotonGigante(texto: String, colorFondo: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo)
    ) {
        Text(text = texto, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}