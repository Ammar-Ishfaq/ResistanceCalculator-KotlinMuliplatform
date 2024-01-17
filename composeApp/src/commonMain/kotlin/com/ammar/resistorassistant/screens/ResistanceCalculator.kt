package com.ammar.resistorassistant.screens

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ammar.resistorassistant.MR
import com.ammar.resistorassistant.extension.toCR
import com.ammar.resistorassistant.screens.band.BandSelectionScreen
import com.ammar.resistorassistant.screens.band.FiveBandResistorCalculator
import com.ammar.resistorassistant.screens.band.FourBandResistorCalculator
import com.ammar.resistorassistant.screens.band.SixBandResistorCalculator
import com.ammar.resistorassistant.screens.detail.DetailScreen
import com.ammar.resistorassistant.screens.list.ListScreenModel
import com.ammar.resistorassistant.screens.list.ObjectGrid
import androidx.compose.ui.graphics.Color

data object ResistanceCalculator : Screen {
    @Composable
    override fun Content() {
        val screenModel: ListScreenModel = getScreenModel()
        val navigator = LocalNavigator.currentOrThrow
        val objects by screenModel.objects.collectAsState()

        var selectedScreen by remember { mutableStateOf(ScreenType.HOME) }


        val isHome = selectedScreen == ScreenType.HOME
        Box(modifier = Modifier.fillMaxSize().background(MR.colors.black.toCR())) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MR.colors.blue.toCR())
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = if (isHome) "Resistance Color Calculator" else "Guide To Calculate", // Replace with your desired title
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = MR.colors.white.toCR()
                        )
                        Box(modifier = Modifier.height(30.dp))
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(MR.colors.white.toCR())
                        .padding(16.dp)
                ) {
                    when (selectedScreen) {
                        ScreenType.HOME -> {

                            HomeScreenContent()

                        }

                        ScreenType.GUIDE -> {

                            AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
                                if (objectsAvailable) {
                                    ObjectGrid(
                                        objects = objects,
                                        onObjectClick = { objectId ->
                                            navigator.push(DetailScreen(objectId))
                                        }
                                    )
                                } else {
                                    EmptyScreenContent(Modifier.fillMaxSize())
                                }
                            }
                        }
                    }
                }

                BottomNavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(MR.colors.white.toCR()),
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
        BottomNavItem("More", Icons.Default.Info, ScreenType.GUIDE)
    )
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MR.colors.gray.toCR()) // Set opacity to 20%
                .alpha(0.1f)
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
            tint = MR.colors.blue.toCR()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.caption,
            color = MR.colors.blue.toCR()
        )
    }
}

data class BottomNavItem(val title: String, val icon: ImageVector, val screenType: ScreenType)
enum class ScreenType {
    HOME,
    GUIDE
}

@Composable
fun HomeScreenContent() {
    var selectedBand by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BandSelectionScreen { selectedBands ->
            selectedBand = selectedBands
        }


        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (selectedBand) {
                    0 -> FourBandResistorCalculator()
                    1 -> FiveBandResistorCalculator()
                    2 -> SixBandResistorCalculator()
                }
            }
        }
    }
}

