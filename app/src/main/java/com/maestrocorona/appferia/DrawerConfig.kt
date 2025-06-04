package com.maestrocorona.appferia // Asegúrate que el paquete sea el correcto

import com.maestrocorona.appferia.R // Importa tu clase R para acceder a los IDs

// 1. Define la estructura de cada ítem del drawer
data class DrawerItem(
    val label: String,      // El texto que se mostrará para el ítem
    val destinationId: Int  // El ID del destino del Fragment en tu nav_graph.xml
    // Podrías añadir un 'icon: ImageVector?' si quisieras iconos más adelante
)

// 2. Crea la lista de todos los ítems que aparecerán en el drawer
val drawerItemsList = listOf(
    DrawerItem(label = "Inicio", destinationId = R.id.mainFragment),
    DrawerItem(label = "Negocios Nave 1", destinationId = R.id.nave1Fragment),
    DrawerItem(label = "Negocios Nave 2", destinationId = R.id.nave2Fragment),
    DrawerItem(label = "Negocios Nave 3", destinationId = R.id.nave3Fragment),
    DrawerItem(label = "Atracciones y Conciertos", destinationId = R.id.atraccionesFragment),
    DrawerItem(label = "Fechas Importantes", destinationId = R.id.fechasImportantesFragment)
    // Puedes añadir más ítems aquí si lo necesitas
)