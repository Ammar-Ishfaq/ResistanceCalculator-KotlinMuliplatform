package com.ammar.resistorassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.graphics.parseColor
import dev.icerock.moko.graphics.Color as MokoColor
data object ResistanceCalculator : Screen {
    @Composable
    override fun Content() {
        ResistanceCalculator()
        Text("hello" +
                "")
    }
}

@Composable
fun BandSelectionDropdown(onBandCountSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedBandCount by remember { mutableStateOf(4) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown for selecting the number of bands
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(8.dp)
        ) {
            listOf(4, 5, 6).forEach { bandCount ->
                DropdownMenuItem(
                    onClick = {
                        selectedBandCount = bandCount
                        onBandCountSelected(bandCount)
                        expanded = false
                    }
                ) {
                    Text("Number of Bands: $bandCount")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ColorSelectionDropdown(
    selectedBandCount: Int,
    onColorSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf<String?>(null) }

    // Actual resistor colors
    val resistorColors = listOf(
        "#000000", // Black
        "#8B4513", // Brown
        "#FF0000", // Red
        "#FFA500", // Orange
        "#FFFF00", // Yellow
        "#008000", // Green
        "#0000FF", // Blue
        "#800080", // Violet
        "#808080", // Gray
        "#FFFFFF"  // White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown for selecting resistor color
        Box(
            modifier = Modifier
                .background(Color.Black)
                .clickable { expanded = !expanded }
                .padding(8.dp)
        ) {
            Text(
                text = selectedColor?.let { "Selected Color: $it" } ?: "Select Resistor Color",
                color = Color.White
            )
            Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null, tint = Color.White)
        }

        // Dropdown content based on the selected band count
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(8.dp)
        ) {
            resistorColors.take(selectedBandCount).forEach { color ->
                DropdownMenuItem(
                    onClick = {
                        selectedColor = color
                        onColorSelected(color)
                        expanded = false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(convertStringToComposeColor(color))
                            .size(24.dp)
                            .padding(4.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ResistanceCalculator() {
    var selectedColor by remember { mutableStateOf<String?>(null) }
    var selectedBandCount by remember { mutableStateOf(4) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BandSelectionDropdown {
            selectedBandCount = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        ColorSelectionDropdown(
            selectedBandCount = selectedBandCount,
            onColorSelected = {
                selectedColor = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ResistanceColorIndicator(selectedColor)
    }
}

@Composable
fun ResistanceColorIndicator(color: String?) {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = color?.let { "Selected Color: $it" } ?: "No Color Selected",
            color = Color.White
        )
    }
}

@Composable
fun convertStringToComposeColor(hexColor: String): Color {
    val mokoColor = dev.icerock.moko.graphics.Color.parseColor(hexColor)
    return convertToComposeColor(mokoColor)
}

@Composable
fun convertToComposeColor(mokoColor: MokoColor): Color {
    return Color(
        red = mokoColor.red / 255f,
        green = mokoColor.green / 255f,
        blue = mokoColor.blue / 255f,
        alpha = mokoColor.alpha / 255f
    )
}