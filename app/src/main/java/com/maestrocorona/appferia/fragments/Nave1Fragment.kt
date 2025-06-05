package com.maestrocorona.appferia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.maestrocorona.appferia.R
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme

class Nave1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppFeriaTheme {
                    DetalleNave1Screen() // Composable específico para Nave 1
                }
            }
        }
    }
}

@Composable
fun DetalleNave1Screen() { // O como se llame tu Composable para el detalle de la nave
    val context = LocalContext.current
    val naveName = "Negocios de la Nave 1"
    val imageResource = R.drawable.imagen_nave_1
    val loremIpsumText = context.getString(R.string.lorem_ipsum_1)

    // Usamos un Box como contenedor raíz para poder alinear el divisor al fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)) // Asegura fondo blanco si no viene del tema
    ) {
        // Columna para el contenido principal que puede ser scrollable
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa todo el espacio del Box
                .verticalScroll(rememberScrollState()) // Permite el scroll si el contenido es largo
                // Padding inferior para que el último texto no quede OCULTO detrás del divisor
                // La cantidad de padding debe ser al menos la altura de tu divisor.
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 60.dp) // Ajusta el bottom padding
        ) {
            // Imagen rectangular de la nave
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

            // Título de la Nave
            Text(
                text = naveName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Texto descriptivo (Lorem Ipsum)
            Text(
                text = loremIpsumText,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify
            )
        }

        // Imagen Decorativa Divisora, alineada al fondo del Box
        Image(
            painter = painterResource(id = R.drawable.divisor),
            contentDescription = "Divisor decorativo",
            modifier = Modifier
                .align(Alignment.BottomCenter) // ¡CLAVE! Alinea esta imagen al fondo y centro del Box
                .fillMaxWidth()
                .aspectRatio(283f / 170f), // Mantiene la proporción de tu imagen de 283x170
            contentScale = ContentScale.Fit
        )
    }
}