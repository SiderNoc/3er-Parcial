package com.maestrocorona.appferia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetalleNaveActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtiene los datos del intent
        val naveName = intent.getStringExtra("NAVE_NAME") ?: "Default Name"
        val imageResource = intent.getIntExtra("IMAGE_RESOURCE", R.drawable.logo_rest)
        setContent {
            // Muestra el contenido de la pantalla
            DetalleNaveScreen(naveName, imageResource)
        }
    }
}

@Composable
fun DetalleNaveScreen(naveName: String, imageResource: Int) {
    val context = LocalContext.current
    // Determina el texto de Lorem Ipsum según el nombre de la nave
    val loremIpsumText = when (naveName) {
        "Negocios de la Nave 1" -> context.getString(R.string.lorem_ipsum_1)
        "Negocios de la Nave 2" -> context.getString(R.string.lorem_ipsum_2)
        "Negocios de la Nave 3" -> context.getString(R.string.lorem_ipsum_3)
        else -> context.getString(R.string.lorem_ipsum_default)
    }

    // Organizar los elementos en una columna
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen rectangular con bordes redondeados
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Imagen de $naveName",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Titulo
        Text(
            text = naveName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Texto de Lorem ipsum
        Text(
            text = loremIpsumText,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleNavePreview() {
    DetalleNaveScreen(naveName = "Negocios de la Nave 3", imageResource = R.drawable.imagen_nave_3)
}