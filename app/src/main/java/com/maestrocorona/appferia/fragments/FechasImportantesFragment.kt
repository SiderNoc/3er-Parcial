package com.maestrocorona.appferia.fragments // Asegúrate que el paquete sea el correcto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.maestrocorona.appferia.R // Importa tu clase R
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme
import com.maestrocorona.appferia.ui.theme.Purple80 // O el color que prefieras para las tarjetas

// 1. Data Class para los Eventos
data class EventoFeria(
    val id: Int, // Un ID único para la key en LazyVerticalGrid
    val nombre: String,
    val fecha: String,
    @DrawableRes val iconoResId: Int? = null // ID de recurso drawable para un icono opcional
)

// 2. Lista de Ejemplo de Eventos de la Feria
// NOTA: Reemplaza R.drawable.ic_... con tus propios drawables o usa null para iconoResId.
val listaEventosFeria = listOf(
    EventoFeria(1, "Inauguración Oficial", "27 de Abril, 2025", R.drawable.ic_event_available), // Ejemplo, necesitarás 'ic_event_available.xml'
    EventoFeria(2, "Elección Flor Tabasco", "28 de Abril, 2025", R.drawable.ic_crown), // Ejemplo, necesitarás 'ic_flower.xml'
    EventoFeria(3, "Desfile de Carros Alegóricos", "30 de Abril, 2025", R.drawable.ic_parade), // Ejemplo, necesitarás 'ic_parade.xml'
    EventoFeria(4, "Foro Ganadero", "02 de Mayo, 2025", R.drawable.ic_editor_choice), // Ejemplo, necesitarás 'ic_livestock_general.xml'
    EventoFeria(5, "Concierto Estelar: Artista Sorpresa", "05 de Mayo, 2025", R.drawable.ic_mic_external_on), // Ejemplo, necesitarás 'ic_music_concert.xml'
    EventoFeria(6, "Show Ecuestre", "07 de Mayo, 2025", R.drawable.ic_horse), // Ejemplo, necesitarás 'ic_horse_riding.xml'
    EventoFeria(7, "Festival Gastronómico", "09 de Mayo, 2025", R.drawable.ic_local_diningxml), // Ejemplo
    EventoFeria(8, "Clausura de la Feria", "12 de Mayo, 2025", R.drawable.ic_firework) // Ejemplo, necesitarás 'ic_fireworks_display.xml'
)

// 3. Composable para la Tarjeta de un Evento
@OptIn(ExperimentalMaterial3Api::class) // Para Card y sus defaults
@Composable
fun EventoCard(evento: EventoFeria, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp), // Pequeño padding alrededor de cada card
        shape = MaterialTheme.shapes.medium, // Esquinas redondeadas estándar
        colors = CardDefaults.cardColors(containerColor = Purple80.copy(alpha = 0.15f)) // Fondo suave para la tarjeta
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp) // Padding interno de la tarjeta
                .fillMaxWidth()
                .defaultMinSize(minHeight = 120.dp), // Altura mínima para que todas se vean similares
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centra el contenido si es corto
        ) {
            if (evento.iconoResId != null) {
                Icon(
                    painter = painterResource(id = evento.iconoResId),
                    contentDescription = evento.nombre, // Descripción para accesibilidad
                    modifier = Modifier
                        .size(40.dp) // Tamaño del icono
                        .padding(bottom = 8.dp),
                    tint = MaterialTheme.colorScheme.primary // Tinta el icono con el color primario del tema
                )
            }
            Text(
                text = evento.nombre,
                style = MaterialTheme.typography.titleSmall, // Estilo para el título del evento
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp),
                maxLines = 2 // Limita a 2 líneas para nombres largos
            )
            Text(
                text = evento.fecha,
                style = MaterialTheme.typography.bodySmall, // Estilo para la fecha
                textAlign = TextAlign.Center
            )
        }
    }
}

// 4. Composable para el Contenido de la Pantalla de Fechas Importantes
@Composable
fun FechasImportantesScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fondo blanco para toda la pantalla del fragmento
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Dos columnas
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 80.dp), // Padding, especialmente abajo para el divisor
            verticalArrangement = Arrangement.spacedBy(8.dp),   // Espacio vertical entre tarjetas
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio horizontal entre tarjetas
        ) {
            items(listaEventosFeria, key = { evento -> evento.id }) { evento ->
                EventoCard(evento = evento)
            }
        }

        // Imagen Decorativa Divisora, alineada al fondo del Box
        Image(
            painter = painterResource(id = R.drawable.divisor),
            contentDescription = "Divisor decorativo",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .aspectRatio(283f / 170f), // Mantiene la proporción de tu imagen 283x170
            contentScale = ContentScale.Fit
        )
    }
}

// 5. Clase del Fragmento
class FechasImportantesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppFeriaTheme {
                    FechasImportantesScreenContent() // Llama al Composable principal
                }
            }
        }
    }
}