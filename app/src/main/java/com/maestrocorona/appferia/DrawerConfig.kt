package com.maestrocorona.appferia

data class DrawerItem(
    val label: String,
    val destinationId: Int,
    val iconResId: Int
)

val drawerItemsList = listOf(

    DrawerItem(
        label = "Inicio",
        destinationId = R.id.mainFragment,
        iconResId = R.drawable.ic_local_florist
    ),
    DrawerItem(
        label = "Negocios Nave 1",
        destinationId = R.id.nave1Fragment,
        iconResId = R.drawable.ic_store
    ),
    DrawerItem(
        label = "Negocios Nave 2",
        destinationId = R.id.nave2Fragment,
        iconResId = R.drawable.ic_fastfood
    ),
    DrawerItem(
        label = "Negocios Nave 3",
        destinationId = R.id.nave3Fragment,
        iconResId = R.drawable.ic_checkroom // Ejemplo
    ),
    DrawerItem(
        label = "Atracciones y Conciertos",
        destinationId = R.id.atraccionesFragment,
        iconResId = R.drawable.ic_star
    ),
    DrawerItem(
        label = "Fechas Importantes",
        destinationId = R.id.fechasImportantesFragment,
        iconResId = R.drawable.ic_event // Ejemplo
    ),
    DrawerItem(
        label = "Galería de Gatitos",
        destinationId = R.id.catGalleryFragment,
        iconResId = R.drawable.ic_photo_library // Ejemplo
    )
)