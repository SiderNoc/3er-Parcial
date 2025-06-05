package com.maestrocorona.appferia // O tu paquete principal

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Importa Box, Column, fillMaxSize, padding, height, etc.
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.* // Importa todos los componentes de Material 3
import androidx.compose.runtime.* // Para remember, mutableStateOf, getValue, by
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Para Color.Transparent y Color.White (si se usa directamente)
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource // Para cargar colores desde R.color
import androidx.compose.ui.res.painterResource // Para cargar drawables desde R.drawable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState // Para observar el estado de navegación
import androidx.navigation.fragment.NavHostFragment
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme // Tu tema de Compose
import kotlinx.coroutines.launch // Para lanzar corutinas para el drawer

// Asumimos que DrawerConfig.kt está en el mismo paquete y define:
// data class DrawerItem(val label: String, val destinationId: Int, val iconResId: Int)
// val drawerItemsList: List<DrawerItem>
// Y que R.color.dark_purple, R.color.white, R.color.purple_80, R.drawable.header_menu existen.

class MainActivity : FragmentActivity() {

    // Ya no necesitamos una propiedad de clase para el NavController aquí,
    // lo manejaremos como un estado dentro de setContent.

    @OptIn(ExperimentalMaterial3Api::class) // Necesario para varios componentes de Material 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Para UI de borde a borde si está configurado

        setContent {
            AppFeriaTheme { // Aplicamos el tema general de la aplicación

                // --- ESTADOS PRINCIPALES PARA LA UI ---
                // Estado para manejar el NavController. Se inicializa como null y se actualiza
                // cuando el NavHostFragment está listo. Usar mutableStateOf asegura la recomposición.
                var navControllerState by remember { mutableStateOf<NavController?>(null) }

                // Estado para controlar si el Navigation Drawer está abierto o cerrado.
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                // Scope de corutina para lanzar acciones asíncronas como abrir/cerrar el drawer.
                val scope = rememberCoroutineScope()

                // --- LÓGICA DE NAVEGACIÓN Y TÍTULO ---
                // Observamos la entrada actual en la pila de navegación del NavController.
                // Esto es un State, por lo que la UI se recompondrá cuando cambie.
                // Usamos 'navControllerState?' para evitar errores si aún es null.
                val currentNavBackStackEntryAsState: State<NavBackStackEntry?>? =
                    navControllerState?.currentBackStackEntryAsState()
                val navBackStackEntry: NavBackStackEntry? = currentNavBackStackEntryAsState?.value

                // Obtenemos el ID de la ruta actual para marcar el ítem seleccionado en el drawer.
                val currentRouteId: Int? = navBackStackEntry?.destination?.id

                // Obtenemos el 'label' del destino actual (definido en nav_graph.xml) para el título de la TopAppBar.
                // Si no hay label, usamos el nombre de la app como default.
                val currentScreenLabel =
                    navBackStackEntry?.destination?.label?.toString()
                        ?: getString(R.string.app_name) // app_name debe estar en strings.xml

                // --- ESTRUCTURA PRINCIPAL DE LA UI ---
                ModalNavigationDrawer(
                    drawerState = drawerState, // Estado para controlar apertura/cierre
                    drawerContent = {
                        // Contenido del menú desplegable
                        AppDrawerContent(
                            currentRouteId = currentRouteId, // Para saber qué ítem resaltar
                            onNavigateToRoute = { destinationId ->
                                // Lambda para manejar la navegación desde el drawer
                                scope.launch {
                                    drawerState.close() // Cerramos el drawer
                                }
                                // Navegamos solo si el destino es diferente al actual
                                if (navControllerState?.currentDestination?.id != destinationId) {
                                    navControllerState?.navigate(destinationId)
                                }
                            }
                        )
                    }
                ) {
                    // Contenido principal de la pantalla (lo que se ve cuando el drawer está cerrado)
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = currentScreenLabel, // Título dinámico
                                        fontWeight = FontWeight.ExtraBold, // En negrita
                                        fontSize = 30.sp, // Tamaño de fuente
                                        textAlign = TextAlign.Center, // Alineación del texto

                                    )
                                },
                                navigationIcon = {
                                    // Botón para el icono de hamburguesa que abre/cierra el drawer
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu, // Icono de hamburguesa estándar
                                            contentDescription = "Abrir menú de navegación",
                                            modifier = Modifier.size(40.dp) // Tamaño del icono ajustado
                                            // El color del icono se define en 'colors' abajo
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = colorResource(id = R.color.dark_purple), // Color de fondo de la TopAppBar
                                    titleContentColor = colorResource(id = R.color.white), // Color del texto del título
                                    navigationIconContentColor = colorResource(id = R.color.white) // Color del icono de hamburguesa
                                )
                            )
                        }
                    ) { innerPadding -> // Padding proporcionado por Scaffold para el contenido (ej. para no solaparse con TopAppBar)
                        // Aquí es donde se alojan los Fragments
                        AndroidView(
                            factory = { context ->
                                // Creamos el FragmentContainerView que usará NavHostFragment
                                androidx.fragment.app.FragmentContainerView(context).apply {
                                    id = R.id.nav_host_fragment_container // ID para el NavHostFragment
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding), // Aplicamos el padding del Scaffold
                            update = { fragmentContainerView ->
                                // Este bloque se llama cuando la vista necesita ser actualizada.
                                // Aquí obtenemos o creamos el NavHostFragment y el NavController.
                                val fragmentManager = supportFragmentManager
                                val existingFragment = fragmentManager.findFragmentById(fragmentContainerView.id)

                                val navHostFragmentInstance = if (existingFragment != null) {
                                    existingFragment as NavHostFragment
                                } else {
                                    // Creamos el NavHostFragment con nuestro grafo de navegación
                                    NavHostFragment.create(R.navigation.nav_graph).also {
                                        fragmentManager.beginTransaction()
                                            .replace(fragmentContainerView.id, it)
                                            .setPrimaryNavigationFragment(it) // Importante para la navegación de sistema (botón atrás)
                                            .commitNow() // commitNow para asegurar que esté disponible inmediatamente
                                    }
                                }

                                // Asignamos el NavController al estado de Compose la primera vez que está disponible.
                                // Esto disparará la recomposición para que el título y el drawer usen el NavController.
                                if (navControllerState == null) {
                                    navControllerState = navHostFragmentInstance.navController
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

// Composable para el contenido del Navigation Drawer
@OptIn(ExperimentalMaterial3Api::class) // Para ModalDrawerSheet y NavigationDrawerItem
@Composable
fun AppDrawerContent(
    currentRouteId: Int?, // Para saber qué ítem está seleccionado
    onNavigateToRoute: (Int) -> Unit // Lambda para ejecutar la navegación
) {
    ModalDrawerSheet(
        drawerContainerColor = colorResource(id = R.color.white) // Fondo blanco para el drawer
    ) {
        Column(modifier = Modifier.fillMaxSize()) { // Columna para organizar el contenido del drawer
            // 1. Imagen de Cabecera
            Image(
                painter = painterResource(id = R.drawable.header_menu), // Tu imagen de cabecera
                contentDescription = "Cabecera del Menú",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp), // Altura que definiste para la cabecera
                contentScale = ContentScale.Crop // Modo de escalado
            )

            // 2. Título/Saludo dentro del Drawer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp) // Altura que definiste
                    .background(colorResource(id = R.color.dark_purple)), // Fondo morado oscuro
                contentAlignment = Alignment.CenterStart, // Alineación del texto
            ) {
                Text(
                    text = "Bienvenid@!", // Tu texto de saludo
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = colorResource(id = R.color.white), // Color blanco para el texto
                        fontWeight = FontWeight.Bold // En negrita
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp) // Padding para el texto

                )
            }

            // 3. Lista de Ítems de Navegación
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp) // Padding vertical para la lista de ítems
                    .weight(1f) // Para que ocupe el espacio restante si el contenido es largo y necesita scroll
            ) {
                // drawerItemsList viene de tu archivo DrawerConfig.kt
                drawerItemsList.forEach { item ->
                    val isSelected = item.destinationId == currentRouteId

                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconResId), // Icono desde drawable
                                contentDescription = item.label // Descripción para accesibilidad
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                // --- CAMBIO: Aplicamos negrita condicionalmente ---
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            onNavigateToRoute(item.destinationId) // Acción al hacer clic
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding), // Padding estándar
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent, // Fondo transparente para no seleccionados
                            selectedContainerColor = colorResource(id = R.color.light_purple), // Color para el ítem seleccionado
                            selectedTextColor = colorResource(id = R.color.white),
                            selectedIconColor = colorResource(id = R.color.white), // Hace el icono blanco cuando está seleccionado
                            // Puedes ajustar selectedTextColor, unselectedTextColor, etc., aquí si es necesario
                        )
                    )
                }
            }
        }
    }
}