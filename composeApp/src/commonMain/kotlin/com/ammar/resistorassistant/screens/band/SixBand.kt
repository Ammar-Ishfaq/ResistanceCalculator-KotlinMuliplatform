package com.ammar.resistorassistant.screens.band

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@Composable
fun SixBandResistorCalculator() {
    var band1 by remember { mutableStateOf("#8B4513") } // Brown
    var band2 by remember { mutableStateOf("#000000") } // Black
    var band3 by remember { mutableStateOf("#FF0000") } // Red
    var multiplier by remember { mutableStateOf("#808080") } // Grey
    var tolerance by remember { mutableStateOf("#FFFF00") } // Yellow
    var temperatureCoefficient by remember { mutableStateOf("#00FFFF") } // Cyan
    var resistanceCalculation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ResistorBandInput("Band 1", band1, { band1 = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Band 2", band2, { band2 = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Band 3", band3, { band3 = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Multiplier", multiplier, { multiplier = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Tolerance", tolerance, { tolerance = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Temp. Coefficient", temperatureCoefficient, { temperatureCoefficient = it })

        Spacer(modifier = Modifier.height(16.dp))
        if (resistanceCalculation.isNotEmpty()) Text(resistanceCalculation)
        Button(
            onClick = {
                val result = calculateSixBandResistorValue(band1, band2, band3, multiplier, tolerance, temperatureCoefficient)
                println("Ohm Resistance => $result")
                resistanceCalculation = result
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Calculate Resistance")
        }
    }
}

fun calculateSixBandResistorValue(band1: String, band2: String, band3: String, multiplier: String, tolerance: String, temperatureCoefficient: String): String {
    // Define the color code mapping
    val colorCode = mapOf(
        "#000000" to 0, // Black
        "#8B4513" to 1, // Brown
        "#FF0000" to 2, // Red
        "#FFA500" to 3, // Orange
        "#FFFF00" to 4, // Yellow
        "#008000" to 5, // Green
        "#0000FF" to 6, // Blue
        "#800080" to 7, // Violet
        "#808080" to 8, // Gray
        "#FFFFFF" to 9  // White
    )

    // Extract digit values from color bands
    val digit1 = colorCode[band1] ?: return "Invalid color: $band1"
    val digit2 = colorCode[band2] ?: return "Invalid color: $band2"
    val digit3 = colorCode[band3] ?: return "Invalid color: $band3"
    val multiplierValue = 10.0.pow(colorCode[multiplier]?.toDouble() ?: return "Invalid color: $multiplier")

    // Calculate resistance value
    val resistance = (digit1 * 100 + digit2 * 10 + digit3) * multiplierValue

    if (tolerance.isNotEmpty() && temperatureCoefficient.isNotEmpty()) {
        // If tolerance and temperature coefficient bands are provided, include them
        val toleranceValues = mapOf(
            "#8B4513" to 1,   // Brown
            "#FF0000" to 2,   // Red
            "#008000" to 0.5, // Green
            "#FFFF00" to 0.25,// Yellow
            "#800080" to 0.1, // Violet
            "#808080" to 0.05,// Gray
            "#FFD700" to 5,   // Gold
            "#C0C0C0" to 10   // Silver
        )
        val toleranceValue = toleranceValues[tolerance.toLowerCase()] ?: return "Invalid color: $tolerance"
        return "Resistance: $resistance ohms, Tolerance: ±$toleranceValue%, Temp. Coefficient: $temperatureCoefficient ppm/°C"
    } else {
        return "Resistance: $resistance ohms"
    }
}
