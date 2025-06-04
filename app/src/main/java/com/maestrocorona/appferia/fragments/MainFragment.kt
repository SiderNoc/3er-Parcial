package com.maestrocorona.appferia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme


// ... otros imports como Fragment, ComposeView, NavController ...
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
// Importante: Asegúrate que MaterialTheme esté disponible, usualmente a través de tu AppFeriaTheme
// import com.maestrocorona.appferia.ui.theme.AppFeriaTheme // Ya lo tienes en setContent

import androidx.compose.foundation.layout.Box // Necesitarás este import
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme // Necesitarás este import
import androidx.compose.material3.Text
import com.maestrocorona.appferia.R

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

// Coloca estas funciones Composable en MainFragment.kt,
// fuera de la clase MainFragment, o en un nuevo archivo .kt en el mismo paquete.

@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        Text(
            text = "¡Bienvenido a la AppFeria Tabasco 2025!",
            style = MaterialTheme.typography.headlineMedium, // Un estilo de texto prominente
            textAlign = TextAlign.Center // Centra el texto en sí mismo
        )
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