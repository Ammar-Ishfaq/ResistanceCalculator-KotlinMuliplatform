package com.ammar.resistorassistant.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.SemanticsProperties.Heading
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.ammar.resistorassistant.MR
import com.ammar.resistorassistant.extension.toCR
import com.ammar.resistorassistant.extension.toComposeColor
import com.ammar.resistorassistant.screens.band.FiveBandResistorCalculator
import com.ammar.resistorassistant.screens.band.FourBandResistorCalculator
import com.ammar.resistorassistant.screens.band.SixBandResistorCalculator
import dev.icerock.moko.resources.compose.stringResource

data object ResistanceCalculator : Screen {
    @Composable
    override fun Content() {
        var selectedScreen by remember { mutableStateOf(ScreenType.HOME) }

        val isHome = selectedScreen == ScreenType.HOME
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                // Title header
                Text(
                    text = if (isHome) "Res Calculate" else "Menu", // Replace with your desired title
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                        .background(color = MR.colors.background_color.toCR())
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
                            HomeScreenContent()
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
fun HomeScreenContent() {
    var selectedBand by remember { mutableStateOf(4) }

    Column {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = stringResource(MR.strings.select_band_type),
                color = Color.Black,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(8.dp)
            )

            BandSelectionMenu { selectedBands ->
                selectedBand = selectedBands
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = stringResource(MR.strings.calculate_resistance),
                color = Color.Black,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(8.dp)
            )
            when (selectedBand) {
                4 -> {
                    FourBandResistorCalculator()
                }
                5->{
                    FiveBandResistorCalculator()
                }
                6->{
                    SixBandResistorCalculator()
                }
            }
        }


    }
}

@Composable
fun BandSelectionMenu(onSelectBand: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedBand by remember { mutableStateOf("4 Bands") }

    val resistorBands = listOf(4, 5, 6)
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp) // Adjust the width as needed
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
//                .clip(CircleShape)
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedBand,
                    modifier = Modifier.padding(4.dp),
                    color = Color.White
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                resistorBands.forEach { band ->
                    DropdownMenuItem(
                        onClick = {
                            selectedBand = "$band Bands"
                            onSelectBand.invoke(band)
                            expanded = false
                        }
                    ) {
                        Text(
                            text = "$band Bands",
                            modifier = Modifier.padding(8.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
