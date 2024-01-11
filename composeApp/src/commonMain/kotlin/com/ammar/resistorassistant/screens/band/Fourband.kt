package com.ammar.resistorassistant.screens.band

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ammar.resistorassistant.extension.toComposeColor
import kotlin.math.pow

@Composable
fun FourBandResistorCalculator() {
    var band1 by remember { mutableStateOf("#8B4513") } // Brown
    var band2 by remember { mutableStateOf("#000000") } // Black
    var multiplier by remember { mutableStateOf("#FF0000") } // Red
    var tolerance by remember { mutableStateOf("#808080") } // Grey
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
        ResistorBandInput("Multiplier", multiplier, { multiplier = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Tolerance", tolerance, { tolerance = it })

        Spacer(modifier = Modifier.height(16.dp))
        if (resistanceCalculation.isNotEmpty()) Text(resistanceCalculation)
        Button(
            onClick = {
                val result = calculateResistorValue(band1, band2, multiplier, tolerance)
                print("Ohm Resistance => $result")
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

@Composable
fun ResistorBandInput(
    label: String,
    selectedColor: String,
    onColorSelected: (String) -> Unit,
) {
    var isError by remember { mutableStateOf(false) }
    val density = LocalDensity.current.density

    // Actual resistor colors in hexadecimal format
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

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .clip(
                    RoundedCornerShape(
                        topStart = CornerSize(16.dp * density),
                        topEnd = CornerSize(16.dp * density),
                        bottomStart = CornerSize(4.dp * density),
                        bottomEnd = CornerSize(4.dp * density)
                    )
                )
                .background(if (isError) Color(0xFFFFCCCC.toInt()) else Color.Transparent)
                .padding(top = 8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(selectedColor.toComposeColor())
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = getColorName(selectedColor), color = Color.Black)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            resistorColors.forEach { color ->
                DropdownMenuItem(
                    onClick = {
                        onColorSelected(color)
                        expanded = false
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(color.toComposeColor())
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = getColorName(color), color = Color.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun getColorName(hexCode: String): String {
    val colorNames = mapOf(
        "#000000" to "Black",
        "#8B4513" to "Brown",
        "#FF0000" to "Red",
        "#FFA500" to "Orange",
        "#FFFF00" to "Yellow",
        "#008000" to "Green",
        "#0000FF" to "Blue",
        "#800080" to "Violet",
        "#808080" to "Gray",
        "#FFFFFF" to "White"
    )
    return colorNames[hexCode] ?: "Unknown"
}

fun calculateResistorValue(band1: String, band2: String, multiplier: String, tolerance: String): String {
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
    val multiplierValue = 10.0.pow(colorCode[multiplier]?.toDouble() ?: return "Invalid color: $multiplier")

    // Calculate resistance value
    val resistance = (digit1 * 10 + digit2) * multiplierValue

    if (tolerance.isNotEmpty()) {
        // If tolerance band is provided, include tolerance
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
        return "Resistance: $resistance ohms, Tolerance: ±$toleranceValue%"
    } else {
        return "Resistance: $resistance ohms"
    }
}
