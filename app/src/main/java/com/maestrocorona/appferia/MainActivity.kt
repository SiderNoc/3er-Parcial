package com.maestrocorona.appferia

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.* // Incluye ExperimentalMaterial3Api para los componentes
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp // Para el padding en AppDrawerContent
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
// import androidx.fragment.app.FragmentContainerView // No es necesario si se usa el nombre completo calificado
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState // Para el estado seleccionado del drawer
import androidx.navigation.fragment.NavHostFragment
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry

import androidx.compose.foundation.Image // Necesario para el Composable Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale // Para controlar cómo se escala la imagen
import androidx.compose.ui.res.painterResource // Para cargar la imagen desde drawable

import androidx.compose.ui.res.colorResource
// Asumimos que DrawerConfig.kt está en el mismo paquete y define:
// data class DrawerItem(val label: String, val destinationId: Int)
// val drawerItemsList: List<DrawerItem>


import androidx.activity.compose.setContent
// ... otros imports ...
import androidx.compose.material3.CenterAlignedTopAppBar // ¡NUEVO IMPORT!
import androidx.compose.material3.ExperimentalMaterial3Api // Ya deberías tenerlo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults // Opcional, para colores

import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.getValue

import androidx.compose.ui.text.font.FontWeight // ¡NUEVO IMPORT para Negrita!

import androidx.navigation.compose.currentBackStackEntryAsState
// ... el resto de tus imports (AndroidView, NavHostFragment, AppFeriaTheme, etc.) ...
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme // Asegúrate que esté
import kotlinx.coroutines.launch


import androidx.compose.ui.res.colorResource // Para
class MainActivity : FragmentActivity() {

    private var navController: NavController? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFeriaTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // Observamos la entrada actual de la pila de navegación
                val currentNavBackStackEntryAsState: State<NavBackStackEntry?>? =
                    navController?.currentBackStackEntryAsState()
                val navBackStackEntry: NavBackStackEntry? = currentNavBackStackEntryAsState?.value

                // Obtenemos el ID de la ruta actual para la selección del drawer
                val currentRouteId: Int? = navBackStackEntry?.destination?.id

                // Obtenemos el LABEL del destino actual para el título de la TopAppBar
                // Usamos el label definido en nav_graph.xml. Si es null, ponemos un título por defecto.
                val currentScreenLabel =
                    navBackStackEntry?.destination?.label?.toString() ?: getString(R.string.app_name) // Título por defecto desde strings.xml

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawerContent(
                            currentRouteId = currentRouteId,
                            onNavigateToRoute = { destinationId ->
                                scope.launch {
                                    drawerState.close()
                                }
                                if (navController?.currentDestination?.id != destinationId) {
                                    navController?.navigate(destinationId)
                                }
                            }
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CenterAlignedTopAppBar( // <-- CAMBIADO A CenterAlignedTopAppBar
                                title = {
                                    Text(
                                        text = currentScreenLabel,
                                        fontWeight = FontWeight.Bold // Letras en Negrita
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            } else {
                                                drawerState.close()
                                            }
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Abrir menú de navegación"
                                        )
                                    }
                                }
                                // Opcional: Puedes personalizar los colores de la CenterAlignedTopAppBar
                                // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                //    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                //    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                // )
                            )
                        }
                    ) { innerPadding ->
                        AndroidView(
                            factory = { context ->
                                androidx.fragment.app.FragmentContainerView(context).apply {
                                    id = R.id.nav_host_fragment_container
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            update = { fragmentContainerView ->
                                val fragmentManager = supportFragmentManager
                                val existingFragment = fragmentManager.findFragmentById(fragmentContainerView.id)
                                val navHostFragmentInstance = if (existingFragment != null) {
                                    existingFragment as NavHostFragment
                                } else {
                                    NavHostFragment.create(R.navigation.nav_graph).also {
                                        fragmentManager.beginTransaction()
                                            .replace(fragmentContainerView.id, it)
                                            .setPrimaryNavigationFragment(it)
                                            .commitNow()
                                    }
                                }
                                if (this@MainActivity.navController == null) {
                                    this@MainActivity.navController = navHostFragmentInstance.navController
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    currentRouteId: Int?,
    onNavigateToRoute: (Int) -> Unit
) {
    ModalDrawerSheet(
        // Mueve la asignación del color aquí
        drawerContainerColor = colorResource(id = R.color.white)
    ) {
        // Usaremos una Column para apilar la imagen y luego los ítems
        // drawerContainerColor = colorResource(id = R.color.white) // Elimina esta línea

        Column(modifier = Modifier.fillMaxSize()) { // fillMaxSize para que la Column ocupe el drawer
            // 1. Imagen de Cabecera
            Image(
                painter = painterResource(id = R.drawable.header_menu), // Imagen de cabecera desde drawable
                contentDescription = "Cabecera del Menú", // Descripción para accesibilidad
                modifier = Modifier
                    .fillMaxWidth() // La imagen ocupará todo el ancho del drawer
                    .height(250.dp), // Define una altura para la cabecera, ajústala a tu gusto
                contentScale = ContentScale.Crop // O ContentScale.Fit, ContentScale.FillBounds, etc.
                // Crop: Recorta la imagen para llenar el espacio manteniendo la proporción.
                // Fit: Ajusta la imagen dentro del espacio, puede dejar barras.
            )

            // 2. Título del Menú (el que ya tenías)

            Box(
                modifier = Modifier
                    .fillMaxWidth() // El Box ocupa todo el ancho
                    .height(70.dp) // Altura fija
                    .background(colorResource(id = R.color.dark_purple)) // Fondo negro para esta sección del título
                    .padding(horizontal = 16.dp, vertical = 20.dp), // Padding para el contenido DENTRO del Box negro
                contentAlignment = Alignment.CenterStart // Alinea el texto al inicio (izquierda) por defecto
            ) {
                Text(
                    text = "Bienvenid@!",
                    // Usamos .copy() para cambiar solo el color del estilo existente
                    style = MaterialTheme.typography.titleLarge.copy(colorResource(id = R.color.white)), // Texto en blanco
                )
            }

            // 3. Lista de Ítems de Navegación (la que ya tenías)
            Column(modifier = Modifier.padding(vertical = 12.dp).weight(1f)) { // Añadimos weight para que ocupe el espacio restante y permita scroll si es necesario
                drawerItemsList.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconResId), // <-- CAMBIO AQUÍ
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) },
                        selected = item.destinationId == currentRouteId,
                        onClick = {
                            onNavigateToRoute(item.destinationId)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            // 1. Hacer transparente el fondo del ítem NO seleccionado
                            unselectedContainerColor = Color.Transparent,

                            // 2. Usar tu color de colors.xml para el fondo del ítem SELECCIONADO
                            selectedContainerColor = colorResource(id = R.color.purple_80)

                            // Opcional: Ajustar colores de texto e iconos si el contraste no es bueno
                            // con los nuevos fondos. Por ejemplo, si el texto seleccionado necesita ser más oscuro:
                            // selectedTextColor = Color.Black, // O un color de tu MaterialTheme
                            // selectedIconColor = Color.Black, // O un color de tu MaterialTheme
                            // unselectedTextColor = MaterialTheme.colorScheme.onSurface, // Color de texto por defecto sobre surface
                            // unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant // Color de icono por defecto

                        )
                    )
                }
            }
        }
    }
}