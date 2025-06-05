package com.maestrocorona.appferia.fragments // Asegúrate que el paquete sea el correcto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.maestrocorona.appferia.R
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme

// Data Class para los Eventos
data class EventoFeria(
    val id: Int, // Un ID único para la key en LazyVerticalGrid
    val nombre: String,
    val fecha: String,
    @DrawableRes val iconoResId: Int? = null // ID de recurso drawable para un icono opcional
)

// Lista de Eventos de la Feria

val listaEventosFeria = listOf(
    EventoFeria(1, "Inauguración Oficial", "27 de Abril, 2025", R.drawable.ic_event_available), // Ejemplo, necesitarás 'ic_event_available.xml'
    EventoFeria(2, "Elección Flor Tabasco", "28 de Abril, 2025", R.drawable.ic_crown), // Ejemplo, necesitarás 'ic_flower.xml'
    EventoFeria(3, "Desfile de Carros Alegóricos", "30 de Abril, 2025", R.drawable.ic_parade), // Ejemplo, necesitarás 'ic_parade.xml'
    EventoFeria(4, "Foro Ganadero", "02 de Mayo, 2025", R.drawable.ic_editor_choice), // Ejemplo, necesitarás 'ic_livestock_general.xml'
    EventoFeria(5, "Concierto Estelar: Artista Sorpresa", "05 de Mayo, 2025", R.drawable.ic_mic_external_on), // Ejemplo, necesitarás 'ic_music_concert.xml'
    EventoFeria(6, "Show Ecuestre", "07 de Mayo, 2025", R.drawable.ic_horse), // Ejemplo, necesitarás 'ic_horse_riding.xml'
    EventoFeria(7, "Festival Gastronómico", "09 de Mayo, 2025", R.drawable.ic_local_dining), // Ejemplo
    EventoFeria(8, "Clausura de la Feria", "12 de Mayo, 2025", R.drawable.ic_firework) // Ejemplo, necesitarás 'ic_fireworks_display.xml'
)

// Composable para la Tarjeta de un Evento
@Composable
fun EventoCard(evento: EventoFeria, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp), // Pequeño padding alrededor de cada card
        shape = MaterialTheme.shapes.medium, // Esquinas redondeadas estándar
        colors = CardDefaults.cardColors(
            colorResource(id = R.color.light_green)// Fondo suave para la tarjeta
        )
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
                    tint = colorResource(id = R.color.dark_green)
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

// Composable para el Contenido de la Pantalla de Fechas Importantes
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

// Clase del Fragmento
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