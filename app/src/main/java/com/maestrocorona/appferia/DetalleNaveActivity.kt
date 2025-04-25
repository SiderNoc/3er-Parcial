package com.maestrocorona.appferia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetalleNaveActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get data from the intent
        val businessName = intent.getStringExtra("BUSINESS_NAME") ?: "Default Name"
        val imageResource = intent.getIntExtra("IMAGE_RESOURCE", R.drawable.logo_rest)
        setContent {
            // Display the detail screen
            DetalleNaveScreen(businessName, imageResource)
        }
    }
}

@Composable
fun DetalleNaveScreen(businessName: String, imageResource: Int) {
    val context = LocalContext.current
    // Determine the correct lorem ipsum text based on the business name
    val loremIpsumText = when (businessName) {
        "Negocios de la Nave 1" -> context.getString(R.string.lorem_ipsum_1)
        "Negocios de la Nave 2" -> context.getString(R.string.lorem_ipsum_2)
        "Negocios de la Nave 3" -> context.getString(R.string.lorem_ipsum_3)
        else -> context.getString(R.string.lorem_ipsum_default)
    }

    // Detail screen layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Rectangular image
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Imagen",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = businessName,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(id = R.color.purple_40)
        )
        Spacer(modifier = Modifier.height(18.dp))
        // Lorem ipsum text
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
    DetalleNaveScreen(businessName = "Negocios de la Nave 2", imageResource = R.drawable.imagen_nave_3)
}