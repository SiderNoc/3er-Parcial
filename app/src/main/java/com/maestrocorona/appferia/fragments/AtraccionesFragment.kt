package com.maestrocorona.appferia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.maestrocorona.appferia.R
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme

//Clase para los artistas
data class Artista(
    val name: String,
    val date: String,
    val time: String,
    val imageResource: Int)

class AtraccionesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppFeriaTheme {
                    AtraccionesScreenContent()
                }
            }
        }
    }
}

@Composable
fun AtraccionesScreenContent() {
    // Mantenemos la lista de artistas aquí o la obtenemos de un ViewModel
    val artists = listOf(
        Artista("Los Angeles Azules", "Mayo-15", "10:00 PM", R.drawable.angeles_azules_artista),
        Artista("Shakira", "Mayo-15", "09:00 PM", R.drawable.shakira_artista),
        Artista("Robleis", "Mayo-16", "08:00 PM", R.drawable.robleis_artista),
        Artista("Miranda!", "Mayo-17", "10:30 PM", R.drawable.miranda_artista),
        Artista("Pablo Alboran", "Mayo-18", "09:30 PM", R.drawable.pablo_artista),
        Artista("La Sonora Dinamita", "Mayo-19", "11:00 PM", R.drawable.sonora_dinamita_artista),
        Artista("Daddy Yankee", "Mayo-20", "10:00 PM", R.drawable.daddy_yankee_artista)
    )

    Box( // Usamos Box como contenedor raíz para anclar el decorador
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)) // Asegura fondo blanco si no viene del tema
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa el espacio del Box
                // Padding: horizontal general, top para espacio con TopAppBar,
                // bottom para dejar espacio para el decorador fijo.
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 48.dp), // Ajusta el bottom padding (altura_decorador + margen)
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(), // La LazyColumn ocupa el ancho disponible
                // El verticalArrangement ya añade espacio entre las tarjetas
                verticalArrangement = Arrangement.spacedBy(20.dp), // El espaciado que estableciste
                // contentPadding para el LazyColumn puede ser horizontal si las tarjetas no lo tienen
                contentPadding = PaddingValues(horizontal = 0.dp) // O un valor pequeño si es necesario
            ) {
                items(artists, key = { it.name }) { artist -> // Usar una key estable
                    ArtistasRow(artist = artist)
                }
            }

        }

        // Imagen Decorativa Divisora, alineada al fondo del Box
        Image(
            painter = painterResource(id = R.drawable.divisor),
            contentDescription = "Divisor decorativo",
            modifier = Modifier
                .align(Alignment.BottomCenter) // Alinea esta imagen al fondo y centro del Box
                .fillMaxWidth()
                .height(24.dp), // Altura fija para el decorador, igual que en WelcomeScreen
            contentScale = ContentScale.Crop // Crop para llenar el espacio, o Fit/FillBounds
        )
    }
}


@Composable
fun ArtistasRow(artist: Artista) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.light_blue)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = artist.imageResource),
                contentDescription = "Imagen de ${artist.name}",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Columna para el Texto (Derecha)
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Artista",
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.grey),
                    fontSize = 15.sp,
                )
                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.black),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top // Alinea las columnas de Fecha y Hora a la parte superior
                ) {
                    // Columna para Fecha
                    Column(
                        modifier = Modifier.weight(1f) // Ocupa la mitad del espacio disponible
                    ) {
                        Text(
                            text = "Fecha:",
                            style = MaterialTheme.typography.labelMedium,
                            color = colorResource(id = R.color.grey),
                        )
                        Text(
                            text = artist.date,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(id = R.color.black)
                        )
                    }

                    // Espaciador opcional antes del divisor
                    Spacer(modifier = Modifier.width(8.dp))

                    // Divisor Vertical
                    VerticalDivider(
                        modifier = Modifier
                            .height(32.dp) // Altura del divisor
                            .width(1.dp),
                        color = colorResource(id = R.color.light_grey) // Color del divisor
                    )

                    // Espaciador opcional después del divisor
                    Spacer(modifier = Modifier.width(8.dp))

                    // Columna para Hora
                    Column(
                        modifier = Modifier.weight(1f) // Ocupa la otra mitad del espacio
                    ) {
                        Text(
                            text = "Hora:",
                            style = MaterialTheme.typography.labelMedium,
                            color = colorResource(id = R.color.grey)
                        )
                        Text(
                            text = artist.time,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, name = "Atracciones Screen Preview")
@Composable
fun AtraccionesScreenPreview() {
    AppFeriaTheme {
        AtraccionesScreenContent()
    }
}

@Preview(showBackground = true, name = "Artista Row Preview")
@Composable
fun ArtistaRowPreview() {
    AppFeriaTheme {
        ArtistasRow(
            artist = Artista("Nombre Artista Ejemplo", "Fecha Ejemplo", time = "4:00", R.drawable.angeles_azules_artista)
        )
    }
}