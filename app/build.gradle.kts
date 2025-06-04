plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.maestrocorona.appferia"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.maestrocorona.appferia"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // --- NUEVAS DEPENDENCIAS PARA NAVIGATION COMPONENT ---
    val navVersion = "2.7.7" // Puedes usar la última versión estable
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    // Para Compose con Navigation (opcional pero recomendado si usas NavController en Composables directamente)
    implementation("androidx.navigation:navigation-compose:$navVersion")
    // --- FIN DE NUEVAS DEPENDENCIAS ---

        // ... tus dependencias existentes ...
        implementation(libs.androidx.core.ktx)
        // ... navigation, compose, etc. ...

        // --- NUEVAS DEPENDENCIAS PARA THECATAPI ---
        // Retrofit (para llamadas a la API)
        implementation("com.squareup.retrofit2:retrofit:2.9.0") // O la última versión estable
        implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Convertidor Gson para JSON

        // Coil (para cargar imágenes en Compose)
        implementation("io.coil-kt:coil-compose:2.6.0") // O la última versión estable
        // --- FIN DE NUEVAS DEPENDENCIAS ---

        // Lifecycle (para ViewModel y viewModelScope)
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0") // O la última versión estable
        // Fragment KTX (para el delegado by viewModels())
        implementation("androidx.fragment:fragment-ktx:1.8.7") // O la última versión estable
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.0") // O la última versión estable

}