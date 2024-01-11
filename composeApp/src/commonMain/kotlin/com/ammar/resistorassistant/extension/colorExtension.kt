package com.ammar.resistorassistant.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ammar.resistorassistant.MR
import dev.icerock.moko.graphics.parseColor
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.compose.colorResource

@Composable
fun ColorResource.toCR(): Color {
    return colorResource(this)
}

@Composable
fun String.toComposeColor(): Color {
    val hexColor = this
    val mokoColor = dev.icerock.moko.graphics.Color.parseColor(hexColor)
    return convertToComposeColor(mokoColor)
}

@Composable
fun convertToComposeColor(mokoColor: dev.icerock.moko.graphics.Color): Color {
    return Color(
        red = mokoColor.red / 255f,
        green = mokoColor.green / 255f,
        blue = mokoColor.blue / 255f,
        alpha = mokoColor.alpha / 255f
    )
}

fun String.toUnComposeColor(): Color {
    val hexColor = this
    val mokoColor = dev.icerock.moko.graphics.Color.parseColor(hexColor)
    return convertToUnComposeColor(mokoColor)
}

fun convertToUnComposeColor(mokoColor: dev.icerock.moko.graphics.Color): Color {
    return Color(
        red = mokoColor.red / 255f,
        green = mokoColor.green / 255f,
        blue = mokoColor.blue / 255f,
        alpha = mokoColor.alpha / 255f
    )
}