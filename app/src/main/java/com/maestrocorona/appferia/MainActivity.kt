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

// Asumimos que DrawerConfig.kt está en el mismo paquete y define:
// data class DrawerItem(val label: String, val destinationId: Int)
// val drawerItemsList: List<DrawerItem>

class MainActivity : FragmentActivity() {

    private var navController: NavController? = null

    @OptIn(ExperimentalMaterial3Api::class) // Necesario para TopAppBar, ModalNavigationDrawer, etc.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFeriaTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // --- INICIO DE LA CORRECCIÓN ---
                // 1. Obtenemos el State<NavBackStackEntry?> de forma segura.
                //    Si navController es null, currentNavBackStackEntryAsState será null.
                val currentNavBackStackEntryAsState: State<NavBackStackEntry?>? = navController?.currentBackStackEntryAsState()

                // 2. Obtenemos el valor (NavBackStackEntry?) del State, también de forma segura.
                //    Si currentNavBackStackEntryAsState es null, navBackStackEntry será null.
                val navBackStackEntry: NavBackStackEntry? = currentNavBackStackEntryAsState?.value

                // 3. Obtenemos el ID de la ruta actual.
                //    Si navBackStackEntry es null, currentRouteId será null.
                val currentRouteId: Int? = navBackStackEntry?.destination?.id
                // --- FIN DE LA CORRECCIÓN ---

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawerContent(
                            currentRouteId = currentRouteId,
                            onNavigateToRoute = { destinationId ->
                                // Cerramos el drawer ANTES de navegar
                                scope.launch {
                                    drawerState.close()
                                }
                                // Evitamos navegar al mismo destino si ya estamos en él
                                if (navController?.currentDestination?.id != destinationId) {
                                    navController?.navigate(destinationId)
                                }
                            }
                            // onCloseDrawer ya no es necesario como parámetro separado si onNavigateToRoute lo maneja
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text("Feria Tabasco") },
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
                            )
                        }
                    ) { innerPadding ->
                        AndroidView(
                            factory = { context ->
                                // Usamos el nombre completamente calificado para FragmentContainerView
                                androidx.fragment.app.FragmentContainerView(context).apply {
                                    id = R.id.nav_host_fragment_container // ID definido en res/values/ids.xml
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding), // Aplicamos el padding del Scaffold
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
                                // Asignamos el navController si aún no lo hemos hecho
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

@OptIn(ExperimentalMaterial3Api::class) // Para ModalDrawerSheet y NavigationDrawerItem
@Composable
fun AppDrawerContent(
    currentRouteId: Int?,
    onNavigateToRoute: (Int) -> Unit
    // onCloseDrawer no es necesario si onNavigateToRoute ya cierra el drawer
) {
    ModalDrawerSheet { // Contenedor estilizado para el contenido del drawer
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Text(
                "Menú Feria Tabasco",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
            )

            // drawerItemsList se toma de tu archivo DrawerConfig.kt
            drawerItemsList.forEach { item ->
                NavigationDrawerItem(
                    label = { Text(item.label) },
                    selected = item.destinationId == currentRouteId,
                    onClick = {
                        onNavigateToRoute(item.destinationId)
                        // No es necesario llamar a onCloseDrawer aquí si la lambda
                        // onNavigateToRoute en MainActivity ya lo hace.
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding) // Padding estándar para ítems
                )
            }
        }
    }
}