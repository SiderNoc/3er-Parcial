package com.maestrocorona.appferia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maestrocorona.appferia.ui.theme.AppFeriaTheme

class FechasImportantesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppFeriaTheme {
                    FechasImportantesScreenContent(onBackPressed = {
                        findNavController().popBackStack()
                    })
                }
            }
        }
    }
}

// Renombramos el Composable original para mayor claridad
@Composable
fun FechasImportantesScreenContent(onBackPressed: () -> Unit) {
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
            Text("Restaurante 1") // [cite: 45]
            Text("Restaurante 2") // [cite: 45]
            Text("Restaurante 3") // [cite: 45]

            Button(
                onClick = onBackPressed, // El onClick ahora usa la lambda
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Atrás") // Cambiamos el texto del botón para que sea más genérico "Atrás"
            }
        }
    }
}