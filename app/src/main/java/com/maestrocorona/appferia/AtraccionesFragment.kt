package com.maestrocorona.appferia

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme

// La data class Artista ya la tienes definida en AtraccionesActivity.kt,
// puedes moverla a un archivo común o aquí si solo se usa en esta pantalla.
// Por ahora, la copiamos aquí para que el Fragment sea autocontenido.
data class Artista(val name: String, val date: String, val imageResource: Int) // Definida en el archivo original [cite: 29]

class AtraccionesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppFeriaTheme {
                    // Pasamos una lambda para que el Composable pueda invocar la navegación hacia atrás
                    AtraccionesScreenContent(onBackPressed = {
                        findNavController().popBackStack() // Navega hacia atrás en la pila de Fragments
                    })
                }
            }
        }
    }
}

// Renombramos el Composable original para evitar confusión si mueves Artista
@Composable
fun AtraccionesScreenContent(onBackPressed: () -> Unit) {
    val artists = listOf(
        Artista("Los Angeles Azules", "Mayo-15", R.drawable.angeles_azules_artista),
        Artista("Shakira", "Mayo-15", R.drawable.shakira_artista),
        Artista("Robleis", "Mayo-16", R.drawable.robleis_artista),
        Artista("Miranda!", "Mayo-17", R.drawable.miranda_artista),
        Artista("Pablo Alboran", "Mayo-18", R.drawable.pablo_artista),
        Artista("La Sonora Dinamita", "Mayo-19", R.drawable.sonora_dinamita_artista),
        Artista("Daddy Yankee", "Mayo-20", R.drawable.daddy_yankee_artista)
    ) // [cite: 29]

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
            Text(
                text = "Atracciones y Conciertos",
                style = TextStyle(
                    color = colorResource(id = R.color.purple_40),
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start),
                textAlign = TextAlign.Left
            ) // [cite: 31, 32, 33]
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(artists) { artist ->
                    ArtistasRow(artist = artist)
                }
            } // [cite: 34]
            Button(onClick = onBackPressed) { // El onClick ahora usa la lambda proporcionada
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    ) // [cite: 35, 36]
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Atrás")
                }
            }
        }
    }
}

// ArtistasRow Composable (copiado de tu AtraccionesActivity.kt)
// Asegúrate de que la data class Artista esté accesible (definida arriba o en un archivo común)
@Composable
fun ArtistasRow(artist: Artista) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // [cite: 38]
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = artist.imageResource),
                contentDescription = "Artistas",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape), // [cite: 39]
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f) // [cite: 40]
            ) {
                Text(
                    text = artist.name,
                    fontSize = 20.sp, // [cite: 41]
                    fontWeight = FontWeight.SemiBold)
            }
            Text(text = artist.date,
                modifier = Modifier.padding(end = 10.dp),
                style = TextStyle(
                    fontSize = 16.sp, // [cite: 42]
                    fontStyle = FontStyle.Italic
                )
            )
        }
    }
}