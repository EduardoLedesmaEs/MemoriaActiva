package com.example.memoriaactiva

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun SeccionesNav(navController: NavHostController) {

    val itemsMenu = listOf(
        Seccion.Inicio,
        Seccion.Medicamentos,
        Seccion.Citas,
        Seccion.Salida
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        itemsMenu.forEach { seccion ->
            val seleccionado = currentDestination?.hierarchy?.any { it.route == seccion.ruta } == true

            NavigationBarItem(
                icon = { Icon(seccion.icono, contentDescription = seccion.titulo) },
                label = { Text(seccion.titulo) },
                selected = seleccionado,
                onClick = {
                    // Solo navega si no estamos ya en esa pantalla
                    if (currentDestination?.route != seccion.ruta) {
                        navController.navigate(seccion.ruta) {
                            // Limpia el historial para que el botón de Inicio sea la base
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                }
            )
        }
    }
}