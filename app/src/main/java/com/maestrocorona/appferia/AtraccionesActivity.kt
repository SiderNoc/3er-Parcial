package com.maestrocorona.appferia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

class AtraccionesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtraccionesScreen(onBackPressed = { finish() })
        }
    }
}

data class Artista(val name: String, val date: String, val imageResource: Int)

@Composable
fun AtraccionesScreen(onBackPressed: () -> Unit) {
    val artists = listOf(
        Artista("Los Angeles Azules", "Mayo-15", R.drawable.angeles_azules_artista),
        Artista("Shakira", "Mayo-15", R.drawable.shakira_artista),
        Artista("Robleis", "Mayo-16", R.drawable.robleis_artista),
        Artista("Miranda!", "Mayo-17", R.drawable.miranda_artista),
        Artista("Pablo Alboran", "Mayo-18", R.drawable.pablo_artista),
        Artista("La Sonora Dinamita", "Mayo-19", R.drawable.sonora_dinamita_artista),
        Artista("Daddy Yankee", "Mayo-20", R.drawable.daddy_yankee_artista)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centramos los elementos horizontalmente dentro de la Column
        ) {
            Text(
                text = "Atracciones y Conciertos",
                style = TextStyle(
                    color = colorResource(id = R.color.purple_40),
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp,
                        top = 16.dp)
                    .fillMaxWidth() // Ocupamos el ancho máximo disponible
                    .wrapContentWidth(Alignment.Start), // Alineamos el contenido del Text a la izquierda
                textAlign = TextAlign.Left
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(artists) { artist ->
                    ArtistasRow(artist = artist)
                }
            }
            Button(onClick = onBackPressed) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Atrás")
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
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = artist.imageResource),
                contentDescription = "Artistas",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(text = artist.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold)
            }
            Text(text = artist.date,
                modifier = Modifier.padding(end = 10.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AtraccionesPreview() {
    AtraccionesScreen(onBackPressed = { /* Do nothing in preview */ })
}