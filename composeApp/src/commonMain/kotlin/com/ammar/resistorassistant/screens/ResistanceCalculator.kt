package com.ammar.resistorassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.ammar.resistorassistant.MR
import com.ammar.resistorassistant.extension.toCR
import com.ammar.resistorassistant.extension.toComposeColor
import dev.icerock.moko.graphics.parseColor
import dev.icerock.moko.graphics.Color as MokoColor

data object ResistanceCalculator : Screen {
    @Composable
    override fun Content() {
        var selectedScreen by remember { mutableStateOf<ScreenType>(ScreenType.HOME) }

        val isHome= selectedScreen == ScreenType.HOME
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                // Title header
                Text(
                    text = if(isHome)"Resistance Calculate" else "Menu", // Replace with your desired title
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(16.dp)
                )

                // Black box with padding
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Takes up remaining space
                        .padding(16.dp)
                ) {
                    // Content for the box (e.g., screen-specific content)
                    when (selectedScreen) {
                        ScreenType.HOME -> {
                            Text("HomeScreen Content")
                        }
                        ScreenType.MORE -> {
                            Text("MoreScreen Content")
                        }
                    }
                }

                BottomNavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(MR.colors.background_color.toCR()),
                    selectedScreenType = mutableStateOf(selectedScreen),

                    ) {
                    selectedScreen = it
                }
            }

        }
    }
}


@Composable
fun BottomNavigationBar(
    modifier: Modifier,
    selectedScreenType: MutableState<ScreenType>,
    onScreenSelected: (ScreenType) -> Unit
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, ScreenType.HOME),
        BottomNavItem("More", Icons.Default.Menu, ScreenType.MORE)
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            Spacer(modifier = Modifier.weight(1f))
            BottomNavItemButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(if (item.screenType == selectedScreenType.value) 1f else 0.6f)
                    .clickable {
                        selectedScreenType.value = item.screenType
                        onScreenSelected.invoke(item.screenType)
                    }
                    .padding(8.dp),
                item = item,
            )
            if (index == items.lastIndex) {
                Spacer(modifier = Modifier.weight(1f)) // Add Spacer with weight
            }
        }

    }
}

@Composable
fun BottomNavItemButton(modifier: Modifier, item: BottomNavItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = MR.colors.text_color.toCR()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.caption,
            color = MR.colors.text_color.toCR()
        )
    }
}

data class BottomNavItem(val title: String, val icon: ImageVector, val screenType: ScreenType)
enum class ScreenType {
    HOME,
    MORE
}

@Composable
fun ColorSelectionDropdown(
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
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown for selecting resistor color
        Row(
            modifier = Modifier
                .background(Color.Black)
                .clickable { expanded = !expanded }
                .padding(8.dp)
        ) {
            Text(
                text = selectedColor?.let { "Selected Color: $it" } ?: "Select Resistor Color",
                color = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = null,
                tint = Color.White
            )
        }

        // Dropdown content based on the selected band count
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(8.dp)
        ) {
            resistorColors.forEach { color ->
                DropdownMenuItem(
                    onClick = {
                        selectedColor = color
                        onColorSelected(color)
                        expanded = false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(color.toComposeColor())
                            .size(24.dp)
                            .padding(4.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }

    }
}

@Composable
fun ResistanceCalculator(selectedBandCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        for (i in 0..selectedBandCount) {
            ColorSelectionDropdown(
                onColorSelected = {
                }
            )
        }

//        Spacer(modifier = Modifier.height(16.dp))
//
//        ResistanceColorIndicator(selectedColor)
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

