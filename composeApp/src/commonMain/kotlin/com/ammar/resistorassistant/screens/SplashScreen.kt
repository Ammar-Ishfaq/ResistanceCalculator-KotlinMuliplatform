package com.ammar.resistorassistant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ammar.resistorassistant.MR
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay

data object SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val navigateToListScreen by remember { mutableStateOf(false) }

        // Display SplashScreen
        SplashScreen()

        // Delay for 5 seconds and navigate to ListScreen
        LaunchedEffect(key1 = navigateToListScreen) {
            delay(5000) // 5000 milliseconds = 5 seconds
            navigator.push(ResistanceCalculator)
        }
    }
}


@Composable
fun SplashScreen() {
    val backgroundColor = Color(0xFFEAEAEA) // Light gray background color
    val textColor = Color(0xFF333333) // Dark text color
    val imageColor = Color(0xFF007ACC) // Blue image color

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            // Your app logo or splash image
            Image(
                painter = painterResource(MR.images.ic_resistor), // Replace with your image resource
                contentDescription = null, // Set a meaningful description if needed
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .background(imageColor)
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            // Your splash screen text
            Text(
                text = stringResource(MR.strings.splash_screen_text),
                style = MaterialTheme.typography.h5.copy(color = textColor),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
    }
}

//@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
