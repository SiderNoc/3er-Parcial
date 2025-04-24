package com.maestrocorona.appferia

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.colorResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(
                onNavigateToSecondActivity = {
                    // Iniciamos la segunda actividad cuando se presione el botón
                    startActivity(Intent(this, Activity2::class.java))
                },
                onNavigateToDetalleNave = {
                    // Iniciamos la DetalleNaveActivity cuando se presione el botón "Ver más"
                    startActivity(Intent(this, DetalleNaveActivity::class.java))
                },
                onNavigateToAtracciones = {
                    startActivity(Intent(this, AtraccionesActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun MainScreen(onNavigateToSecondActivity: () -> Unit, onNavigateToDetalleNave: () -> Unit, onNavigateToAtracciones: () -> Unit) {
    // Pantalla principal que contiene todos los elementos
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
            // Lista de negocios con sus imágenes
            BusinessItem(text = "Negocios de la Nave 1", imageResource = R.drawable.logo_rest, onNavigate = onNavigateToDetalleNave)
            BusinessItem(text = "Negocios de la Nave 2", imageResource = R.drawable.imagen1, onNavigate = onNavigateToDetalleNave)
            BusinessItem(text = "Negocios de la Nave 3", imageResource = R.drawable.imagen2, onNavigate = onNavigateToDetalleNave)
            BusinessItem(text = "Atracciones y Conciertos", imageResource = R.drawable.imagen3, onNavigate = onNavigateToAtracciones)

            // Botón para navegar a la segunda actividad
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
    // Componente reutilizable para mostrar negocio con imagen
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purple_80) // Fondo de la Card
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Imagen del negocio
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Imagen del negocio $text",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
            )
            // Texto del negocio
            Text(
                text = text,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = Bold,
                    color = colorResource(id = R.color.purple_40)
                )
            )
            // Boton ver mas
            FilledTonalButton(onClick = onNavigate, modifier = Modifier.padding(end = 8.dp)) {
                Text("Ver más")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    MainScreen(onNavigateToSecondActivity = { /* Simular navegación */ }, onNavigateToDetalleNave = { /* Simular navegación */ }, onNavigateToAtracciones = { /* Simular navegación */ })
}