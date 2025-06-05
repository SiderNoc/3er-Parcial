package com.maestrocorona.appferia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maestrocorona.appferia.R
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme
// ... (otros imports que ya tienes)
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape // Para esquinas redondeadas
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider // Para la línea divisoria
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color // Para el color de fondo del círculo de imagen
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maestrocorona.appferia.ui.theme.Purple80 // Usando un color de tu tema
import com.maestrocorona.appferia.ui.theme.PurpleGrey80


// La data class Artista ya la tienes definida en AtraccionesActivity.kt,
// puedes moverla a un archivo común o aquí si solo se usa en esta pantalla.
// Por ahora, la copiamos aquí para que el Fragment sea autocontenido.
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


// Renombramos el Composable original para evitar confusión si mueves Artista
@Composable
fun AtraccionesScreenContent() {
    val artists = listOf(
        Artista("Los Angeles Azules", "Mayo-15", "10:00 PM", R.drawable.angeles_azules_artista),
        Artista("Shakira", "Mayo-15", "09:00 PM", R.drawable.shakira_artista),
        Artista("Robleis", "Mayo-16", "08:00 PM", R.drawable.robleis_artista),
        Artista("Miranda!", "Mayo-17", "10:30 PM", R.drawable.miranda_artista),
        Artista("Pablo Alboran", "Mayo-18", "09:30 PM", R.drawable.pablo_artista),
        Artista("La Sonora Dinamita", "Mayo-19", "11:00 PM", R.drawable.sonora_dinamita_artista),
        Artista("Daddy Yankee", "Mayo-20", "10:00 PM", R.drawable.daddy_yankee_artista)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f), // Para que ocupe el espacio disponible
                contentPadding = PaddingValues(vertical = 8.dp), // Padding arriba y abajo de toda la lista
                verticalArrangement = Arrangement.spacedBy(12.dp) // <--- ¡ESTA LÍNEA ES CLAVE!
            ) {
                items(artists) { artist ->
                    ArtistasRow(artist = artist)
                }
            }


        }
    }
}


@Composable
fun ArtistasRow(artist: Artista) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp), // Padding horizontal para la tarjeta en la lista
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purple_90) // Color de fondo de la tarjeta
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp) // Padding interno de la tarjeta
            //.height(IntrinsicSize.Min) // Podemos quitar esto o ajustarlo.
            // La altura se definirá por el contenido, principalmente la imagen de 120.dp
            // más el padding vertical de la Row (8.dp arriba y 8.dp abajo).
            ,
            verticalAlignment = Alignment.CenterVertically // Centra la imagen y la columna de texto verticalmente
        ) {
            // Tu forma preferida de mostrar la Imagen (Izquierda)
            Image(
                painter = painterResource(id = artist.imageResource),
                contentDescription = "Imagen de ${artist.name}", // Descripción más específica
                modifier = Modifier
                    .size(120.dp) // Tamaño fijo que especificaste
                    .clip(CircleShape), // [cite: 39]
                contentScale = ContentScale.Crop
            )

            // Columna para el Texto (Derecha)
            Column(
                modifier = Modifier
                    .padding(start = 16.dp) // Espacio entre la imagen y el texto
                    .fillMaxHeight() // Intenta que la columna ocupe la altura disponible por la imagen
                    .weight(1f), // Ocupa el espacio restante en la fila si es necesario
                verticalArrangement = Arrangement.Center // Centra el contenido de texto verticalmente en su columna
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
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black.copy(alpha = 0.87f),
                    maxLines = 2, // Para evitar que nombres muy largos descuadren mucho
                    overflow = TextOverflow.Ellipsis // Añade puntos suspensivos si el nombre es muy largo
                )

                Spacer(modifier = Modifier.height(20.dp)) // Un poco menos de espacio aquí

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

                Spacer(modifier = Modifier.height(3.dp))
                HorizontalDivider(thickness = 1.dp,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(vertical = 2.dp))
                Spacer(modifier = Modifier.height(3.dp))

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
@Preview(showBackground = true, name = "Atracciones Screen Preview")
@Composable
fun AtraccionesScreenPreview() {
    AppFeriaTheme {
        AtraccionesScreenContent() // Ya no necesita la lambda onBackPressed
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