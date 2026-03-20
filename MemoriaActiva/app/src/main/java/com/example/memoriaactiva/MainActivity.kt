package com.example.memoriaactiva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Rutas {
    const val INICIO = "inicio"
    const val MEDICAMENTOS = "medicamentos"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Rutas.INICIO) {
                    composable(Rutas.INICIO) {
                        PantallaInicio(onNavegarAMedicamentos = {
                            navController.navigate(Rutas.MEDICAMENTOS)
                        })
                    }
                    composable(Rutas.MEDICAMENTOS) {
                        PantallaMedicamentos()
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(onNavegarAMedicamentos: () -> Unit) {
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

        // Botón 1: Medicamentos
        BotonGigante("TOMAR MEDICAMENTOS", Color(0xFF336699)) {
            onNavegarAMedicamentos()
        }

        // Botón 2: Citas
        BotonGigante("CITAS MÉDICAS", Color(0xFF2E8B57)) {}

        // Botón 3: Checklist
        BotonGigante("¿SALES DE CASA?", Color(0xFFD2691E)) {}

        // Botón 4: Agua
        BotonGigante("BEBER AGUA", Color(0xFF4682B4)) {}
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        PantallaInicio(onNavegarAMedicamentos = {})
    }
}