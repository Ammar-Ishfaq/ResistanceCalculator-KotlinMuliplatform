package com.ammar.resistorassistant.screens.band

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.ammar.resistorassistant.extension.toComposeColor
import com.ammar.resistorassistant.extension.toUnComposeColor
import kotlin.math.pow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FourBandResistorCalculator() {
    var band1 by remember { mutableStateOf("#8B4513") } // Brown
    var band2 by remember { mutableStateOf("#000000") } // Black
    var band3 by remember { mutableStateOf("#FF0000") } // Red
    var band4 by remember { mutableStateOf("#FFD700") } // Gold

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ResistorView(band1, band2, band3, band4, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { /* Handle resistor click if needed */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ResistorBandInput("Band 1", band1, { band1 = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Band 2", band2, { band2 = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Band 3", band3, { band3 = it })
        Spacer(modifier = Modifier.height(8.dp))
        ResistorBandInput("Band 4", band4, { band4 = it })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Do something with the resistor values
                // For example, you can calculate the resistance and tolerance
                val result = calculateResistorValue(band1, band2, band3, band4)
                // TODO: Handle the calculated result
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
fun ResistorView(
    band1: String,
    band2: String,
    band3: String,
    band4: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
        }
        Canvas(Modifier.fillMaxSize(), {
            this.drawResistor(band1, band2, band3, band4)
        })

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
        Box(
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
                .padding(16.dp)
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

fun DrawScope.drawResistor(band1: String, band2: String, band3: String, band4: String) {
    val strokeWidth = 8f
    val cornerRadius = CornerRadius(4.0f)
    val resistorHeight = size.height / 2
    val resistorWidth = size.width

    // Draw resistor body
    drawRoundRect(
        color = Color.Gray,
        size = size.copy(height = resistorHeight),
        cornerRadius = cornerRadius,
        style = Stroke(strokeWidth)
    )

    // Draw color bands
    val bandWidth = size.width / 10
    drawRoundRect(
        color = band1.toUnComposeColor(),
        size = Size(bandWidth, resistorHeight),
        topLeft = Offset(0f, 0f),
        style = Fill
    )

    drawRoundRect(
        color = band2.toUnComposeColor(),
        size = Size(bandWidth, resistorHeight),
        topLeft = Offset(bandWidth, 0f),
        style = Fill
    )

    drawRoundRect(
        color = band3.toUnComposeColor(),
        size = Size(bandWidth, resistorHeight),
        topLeft = Offset(bandWidth * 2, 0f),
        style = Fill
    )

    drawRoundRect(
        color = band4.toUnComposeColor(),
        size = Size(bandWidth, resistorHeight),
        topLeft = Offset(bandWidth * 3, 0f),
        style = Fill
    )
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

fun calculateResistorValue(band1: String, band2: String, band3: String, band4: String): String {
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
    val multiplier =
        10.0.pow(colorCode[band3]?.toDouble() ?: return "Invalid color: $band3")

    // Calculate resistance value
    val resistance = (digit1 * 10 + digit2) * multiplier

    if (band4.isNotEmpty()) {
        // If tolerance band is provided, include tolerance
        val toleranceValues = mapOf(
            "#8B4513" to 1, // Brown
            "#FF0000" to 2, // Red
            "#008000" to 0.5, // Green
            "#FFFF00" to 0.25, // Yellow
            "#800080" to 0.1, // Violet
            "#808080" to 0.05, // Gray
            "#FFD700" to 5, // Gold
            "#C0C0C0" to 10  // Silver
        )
        val tolerance = toleranceValues[band4] ?: return "Invalid color: $band4"
        return "Resistance: $resistance ohms, Tolerance: ±$tolerance%"
    } else {
        return "Resistance: $resistance ohms"
    }
}
