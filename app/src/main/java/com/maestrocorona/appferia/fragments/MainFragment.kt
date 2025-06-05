package com.maestrocorona.appferia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme
import androidx.compose.runtime.LaunchedEffect // Para efectos que se ejecutan en corutinas
import kotlinx.coroutines.delay // Para la pausa de 5 segundos
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box // Necesitarás este import
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme // Necesitarás este import
import androidx.compose.material3.Text
import com.maestrocorona.appferia.R
import androidx.compose.foundation.ExperimentalFoundationApi // Para Pager
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager // El componente Pager
import androidx.compose.foundation.pager.rememberPagerState // Estado para el Pager
import androidx.compose.ui.AbsoluteAlignment



class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppFeriaTheme {
                    WelcomeScreen()
                    /*
                    MainScreen(
                        onNavigateToSecondActivity = {
                            // Navegar a FechasImportantesFragment
                            findNavController().navigate(R.id.action_mainFragment_to_fechasImportantesFragment)
                        },
                        onNavigateToDetalleNave = { naveName, _ -> // imageResource ya no es necesario aquí
                            // porque cada NaveXFragment tiene su propia imagen.
                            when (naveName) {
                                "Negocios de la Nave 1" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_nave1Fragment)
                                }
                                "Negocios de la Nave 2" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_nave2Fragment)
                                }
                                "Negocios de la Nave 3" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_nave3Fragment)
                                }
                                else -> {
                                    // Opcional: manejar un caso por defecto o loggear un error si el nombre no coincide
                                    println("Error: Nombre de nave no reconocido - $naveName")
                                }
                            }
                        },
                        onNavigateToAtracciones = {
                            // Navegar a AtraccionesFragment
                            findNavController().navigate(R.id.action_mainFragment_to_atraccionesFragment)
                        }
                    )*/
                }
            }
        }
    }
}


@Composable
fun WelcomeScreen() {
    val carouselImageIds = listOf(
        R.drawable.imagen_nave_3,
        R.drawable.sonora_dinamita_artista,
        R.drawable.imagen_conciertos,
        R.drawable.angeles_azules_artista
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Carrusel de Fotos en la parte superior
        PhotoCarousel(
            imageIds = carouselImageIds,
            modifier = Modifier.fillMaxWidth()
        )

        // Columna interna para el título y el texto de bienvenida
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(), // Hacemos que esta columna también ocupe el ancho
            horizontalAlignment = AbsoluteAlignment.Left // Alineación para el contenido interno
        ){
            Image(
                painter = painterResource(id = R.drawable.titulo),
                contentDescription = "Logo Feria Tabasco", // Un poco más descriptivo
                modifier = Modifier.fillMaxWidth(), // El logo ocupa el ancho de esta columna interna
                alignment = AbsoluteAlignment.TopLeft // Alineación del painter si es más pequeño que el Image Composable
            )
            Spacer(modifier = Modifier.height(16.dp)) // Un poco menos de espacio

            Text(
                text = "¡Bienvenido a la Feria Tabasco!",
                style = MaterialTheme.typography.headlineMedium, // Usa el estilo del tema
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(horizontal = 1.dp), // Padding ya es pequeño
                fontSize = 16.sp // El estilo headlineMedium ya define un tamaño, esto lo podría sobreescribir
                // Considera quitar fontSize si headlineMedium ya es adecuado.
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espacio después del texto de bienvenida
        }

        // Spacer para empujar el decorador hacia abajo
        Spacer(modifier = Modifier.weight(1f))

        // Imagen Decorativa Divisora en la parte inferior
        Image(
            painter = painterResource(id = R.drawable.divisor), // Tu imagen 283x170
            contentDescription = "Divisor decorativo",
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho de la pantalla
                .aspectRatio(283f / 170f), // Mantiene la proporción original de tu imagen
            contentScale = ContentScale.Fit // Asegura que toda la imagen sea visible, ajustada al ancho
        )
        // Opcional: si quieres un pequeño margen en la parte inferior de la pantalla, después del divisor:
        // Spacer(modifier = Modifier.height(8.dp))
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoCarousel(imageIds: List<Int>, modifier: Modifier = Modifier) {
    // Si no hay imágenes o solo hay una, no necesitamos el Pager ni el auto-scroll.
    // Simplemente mostramos la única imagen si existe, o nada.
    if (imageIds.size <= 1) {
        if (imageIds.isNotEmpty()) {
            Image(
                painter = painterResource(id = imageIds[0]),
                contentDescription = "Imagen del carrusel 1", // Descripción para la única imagen
                contentScale = ContentScale.Crop,
                modifier = modifier // Aplicamos el modifier general
                    .fillMaxWidth()
                    .height(220.dp) // Mantenemos una altura consistente
            )
        }
        // Si no hay imágenes (imageIds.isEmpty()), no se muestra nada desde esta función.
        // Los indicadores tampoco se mostrarán si pageCount no es > 1.
        return
    }

    val pagerState = rememberPagerState(pageCount = { imageIds.size })

    // Efecto para el auto-desplazamiento
    LaunchedEffect(key1 = pagerState.pageCount) { // Se relanza si cambia el número de páginas
        while (true) { // Bucle infinito para el auto-desplazamiento
            delay(5000L) // Espera 5 segundos (5000 milisegundos)
            // Calcula la siguiente página, asegurando que vuelva al inicio (loop)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            try {
                // Anima el desplazamiento a la siguiente página
                pagerState.animateScrollToPage(nextPage)
            } catch (e: Exception) {
                // Opcional: Manejar o registrar la excepción si algo sale mal durante la animación
                // (ej. si el composable se desmonta mientras la animación está en curso)
                println("Error al animar scroll del Pager: ${e.message}")
                break // Salir del bucle en caso de error para no insistir
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp) // Altura del carrusel
        ) { pageIndex ->
            Image(
                painter = painterResource(id = imageIds[pageIndex]),
                contentDescription = "Imagen del carrusel ${pageIndex + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Indicadores de página (puntos) - se muestran si hay más de una página
        if (pagerState.pageCount > 1) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    }
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }
        }
    }
}

//CONTENIDO ORIGINAL DE MAINSCREEN DE MAINACTIVITY SE QUEDA POR SI LO OCUPO DESPUES
@Composable
fun MainScreen(
    onNavigateToSecondActivity: () -> Unit,
    onNavigateToDetalleNave: (String, Int) -> Unit,
    onNavigateToAtracciones: () -> Unit
) {
    // Contenido original de MainScreen de tu MainActivity
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "FERIA TABASCO\n2025",
                style = TextStyle(
                    color = colorResource(id = R.color.purple_40), // Asegúrate que R.color.purple_40 existe
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 40.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start),
                textAlign = TextAlign.Left
            )
            BusinessItem(
                text = "Negocios de la Nave 1",
                imageResource = R.drawable.imagen_nave_1, // Asegúrate que R.drawable.imagen_nave_1 existe
                onNavigate = { onNavigateToDetalleNave("Negocios de la Nave 1", R.drawable.imagen_nave_1) }
            )
            BusinessItem(
                text = "Negocios de la Nave 2",
                imageResource = R.drawable.imagen_nave_2, // Asegúrate que R.drawable.imagen_nave_2 existe
                onNavigate = { onNavigateToDetalleNave("Negocios de la Nave 2", R.drawable.imagen_nave_2) }
            )
            BusinessItem(
                text = "Negocios de la Nave 3",
                imageResource = R.drawable.imagen_nave_3, // Asegúrate que R.drawable.imagen_nave_3 existe
                onNavigate = { onNavigateToDetalleNave("Negocios de la Nave 3", R.drawable.imagen_nave_3) }
            )
            BusinessItem(
                text = "Atracciones y Conciertos",
                imageResource = R.drawable.imagen_conciertos, // Asegúrate que R.drawable.imagen_conciertos existe
                onNavigate = { onNavigateToAtracciones() }
            )
            Button(
                onClick = onNavigateToSecondActivity,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Fechas importantes")
            }
        }
    }
}

@Composable
fun BusinessItem(text: String, imageResource: Int, onNavigate: () -> Unit) {
    // Contenido original de BusinessItem de tu MainActivity
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purple_80) // Asegúrate que R.color.purple_80 existe
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Imagen de $text",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = text,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.purple_40)
                )
            )
            FilledTonalButton(onClick = onNavigate,
                modifier = Modifier.padding(end = 8.dp)) {
                Text("Ver más")
            }
        }
    }
}