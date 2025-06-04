package com.maestrocorona.appferia.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maestrocorona.appferia.model.CatImage
import com.maestrocorona.appferia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException // Para manejar excepciones de red

class CatGalleryViewModel : ViewModel() {

    private val _catImages = MutableStateFlow<List<CatImage>>(emptyList())
    val catImages: StateFlow<List<CatImage>> = _catImages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Referencia al servicio de la API desde nuestro RetrofitInstance
    private val apiService = RetrofitInstance.api

    init {
        fetchCatImages() // Cargar imágenes cuando se crea el ViewModel
    }

    fun fetchCatImages(limit: Int = 20) { // Pedimos 20 imágenes por defecto
        _isLoading.value = true
        _errorMessage.value = null // Limpiamos errores previos
        viewModelScope.launch {
            try {
                val images = apiService.getRandomCatImages(limit = limit)
                _catImages.value = images
            } catch (e: IOException) { // Error de red (ej: sin conexión)
                _errorMessage.value = "Error de red: ${e.localizedMessage}"
                _catImages.value = emptyList() // Limpiamos imágenes en caso de error
            } catch (e: Exception) { // Otros errores (ej: problema con el JSON, API)
                _errorMessage.value = "Error al obtener imágenes: ${e.localizedMessage}"
                _catImages.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Función para limpiar el mensaje de error una vez mostrado
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}