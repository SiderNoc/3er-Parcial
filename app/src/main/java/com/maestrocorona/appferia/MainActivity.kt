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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState // Para el estado seleccionado del drawer
import androidx.navigation.fragment.NavHostFragment
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme
import kotlinx.coroutines.launch
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
import androidx.compose.material3.CenterAlignedTopAppBar // ¡NUEVO IMPORT!
import androidx.compose.material3.ExperimentalMaterial3Api // Ya deberías tenerlo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight // ¡NUEVO IMPORT para Negrita!

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Importa Box, Column, etc.
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.currentBackStackEntryAsState
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme
import kotlinx.coroutines.launch
class MainActivity : FragmentActivity() {

    // Ya no necesitamos esta propiedad de clase para el NavController usado en Compose.
    // private var navController: NavController? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFeriaTheme {
                // --- INICIO DE CAMBIOS IMPORTANTES ---
                // 1. Creamos un estado para el NavController dentro de Compose
                var navControllerState by remember { mutableStateOf<NavController?>(null) }
                // --- FIN DE CAMBIOS IMPORTANTES ---

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // Observamos la entrada actual de la pila de navegación USANDO navControllerState
                val currentNavBackStackEntryAsState: State<NavBackStackEntry?>? =
                    navControllerState?.currentBackStackEntryAsState() // Usamos navControllerState
                val navBackStackEntry: NavBackStackEntry? = currentNavBackStackEntryAsState?.value

                val currentRouteId: Int? = navBackStackEntry?.destination?.id
                val currentScreenLabel =
                    navBackStackEntry?.destination?.label?.toString() ?: getString(R.string.app_name)

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawerContent(
                            currentRouteId = currentRouteId,
                            onNavigateToRoute = { destinationId ->
                                scope.launch {
                                    drawerState.close()
                                }
                                // Usamos navControllerState para navegar
                                if (navControllerState?.currentDestination?.id != destinationId) {
                                    navControllerState?.navigate(destinationId)
                                }
                            }
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = currentScreenLabel, // Texto de la TopBar
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 30.sp,
                                        color = colorResource(id=R.color.yellow)
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                        }
                                    }) {
                                        Icon(
                                            Icons.Filled.Menu,
                                            contentDescription = "Abrir menú de navegación",
                                            modifier = Modifier.size(45.dp)
                                        )
                                    }
                                }
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
                                // --- INICIO DE CAMBIOS IMPORTANTES ---
                                // 2. Actualizamos el ESTADO del NavController
                                if (navControllerState == null) { // Solo asigna si aún es null para evitar reasignaciones innecesarias
                                    navControllerState = navHostFragmentInstance.navController
                                }
                                // --- FIN DE CAMBIOS IMPORTANTES ---
                            }
                        )
                    }
                }
            }
        }
    }
}


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
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color =(colorResource(id = R.color.white)) // Texto en blanco
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
                            selectedContainerColor = colorResource(id = R.color.light_purple),

                            // Opcional: Ajustar colores de texto e iconos si el contraste no es bueno
                            // con los nuevos fondos. Por ejemplo, si el texto seleccionado necesita ser más oscuro:
                            selectedTextColor = colorResource(id = R.color.white), // O un color de tu MaterialTheme
                            selectedIconColor = colorResource(id= R.color.white), // O un color de tu MaterialTheme
                            // unselectedTextColor = MaterialTheme.colorScheme.onSurface, // Color de texto por defecto sobre surface
                            // unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant // Color de icono por defecto

                        )
                    )
                }
            }
        }
    }
}