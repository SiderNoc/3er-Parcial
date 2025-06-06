package com.maestrocorona.appferia.api // o tu paquete.network

import com.maestrocorona.appferia.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCatApiService {

    // Endpoint para buscar imágenes de gatos
    // Documentación de TheCatAPI: https://developers.thecatapi.com/view-account/ylX4blBYv روד勅силаChVB
    @GET("v1/images/search")
    suspend fun getRandomCatImages(
        @Query("limit") limit: Int = 10, // Cuántas imágenes queremos
        @Query("mime_types") mimeTypes: String = "jpg,png" // Tipos de imagen
    ): List<CatImage> // Esperamos una lista de objetos CatImage
}