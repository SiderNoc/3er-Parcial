package com.maestrocorona.appferia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button // Para el botón de reintentar/limpiar error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue // Para collectAsState o collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // Para el delegado by viewModels()
import androidx.lifecycle.compose.collectAsStateWithLifecycle // Para observar StateFlows
import coil.compose.AsyncImage // Para cargar imágenes con Coil
import coil.request.ImageRequest
import com.maestrocorona.appferia.model.CatImage
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme
import com.maestrocorona.appferia.viewmodel.CatGalleryViewModel

class CatGalleryFragment : Fragment() {

    // Obtenemos una instancia del ViewModel usando el delegado by viewModels()
    private val viewModel: CatGalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppFeriaTheme {
                    // Observamos los estados del ViewModel
                    val catImages by viewModel.catImages.collectAsStateWithLifecycle()
                    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

                    CatGalleryScreenContent(
                        isLoading = isLoading,
                        catImages = catImages,
                        errorMessage = errorMessage,
                        onRetry = { viewModel.fetchCatImages() }, // Lambda para reintentar
                        onClearError = { viewModel.clearErrorMessage() } // Lambda para limpiar error
                    )
                }
            }
        }
    }
}

// Este es el nuevo Composable que mostrará la UI de la galería
@Composable
fun CatGalleryScreenContent(
    isLoading: Boolean,
    catImages: List<CatImage>,
    errorMessage: String?,
    onRetry: () -> Unit,
    onClearError: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading && catImages.isEmpty()) {
            // Muestra el indicador de carga solo si estamos cargando y no hay imágenes previas
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            // Muestra el mensaje de error
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(onClick = {
                    onClearError() // Limpia el error
                    onRetry()      // E intenta cargar de nuevo
                }) {
                    Text("Reintentar")
                }
            }
        } else if (catImages.isEmpty() && !isLoading) {
            // No hay imágenes y no estamos cargando (podría ser después de un error o si la API no devuelve nada)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No se encontraron gatitos. ¿Intentar de nuevo?", textAlign = TextAlign.Center)
                Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Buscar Gatitos")
                }
            }
        } else {
            // Muestra la cuadrícula de imágenes de gatos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Muestra 2 columnas
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(catImages, key = { catImage -> catImage.id }) { catImage ->
                    CatImageItem(catImage)
                }
            }
        }
    }
}

@Composable
fun CatImageItem(catImage: CatImage) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(catImage.url)
            .crossfade(true) // Efecto de fundido suave al cargar
            .build(),
        contentDescription = "Imagen de un gato (ID: ${catImage.id})",
        contentScale = ContentScale.Crop, // Escala la imagen para llenar el espacio, recortando si es necesario
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Hace que cada ítem sea cuadrado
    )
}