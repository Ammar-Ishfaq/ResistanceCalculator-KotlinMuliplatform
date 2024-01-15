package com.ammar.resistorassistant.screens.band

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ammar.resistorassistant.MR
import com.ammar.resistorassistant.extension.toCR

@Composable
fun RoundedCornerBar(
    options: List<String>,
    selectedOption: MutableState<String>,
    onOptionSelected: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .background(MR.colors.white.toCR(), shape = RoundedCornerShape(30.dp))
            .border(
                width = 2.dp, // Adjust the width of the border as needed
                color = MR.colors.blue.toCR(),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        options.forEachIndexed { index, option ->
            BarOption(
                text = option,
                isSelected = option == selectedOption.value,
                onOptionSelected = {
                    selectedOption.value = option
                    onOptionSelected(option)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = if (index < options.size - 1) 8.dp else 0.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
        }
    }
}


@Composable
fun BarOption(
    text: String,
    isSelected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clickable {
                onOptionSelected()
            }
//            .shadow(elevation = if (isSelected) 20.dp else 0.dp) // Increased elevation
            .padding(8.dp),
        color = if (isSelected) MR.colors.blue.toCR() else MR.colors.white.toCR(),
        contentColor = contentColorFor(MR.colors.blue.toCR()),
        shape = RoundedCornerShape(30.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.body1.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                letterSpacing = 0.25.sp,
                color = if (isSelected) MR.colors.white.toCR() else MR.colors.blue.toCR()
            ),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BandSelectionScreen(onOptionSelected: (Int) -> Unit) {
    var selectedOption by remember { mutableStateOf("4 Band") }
    val optionsList = listOf("4 Band", "5 Band", "6 Band")
    RoundedCornerBar(
        options = optionsList,
        selectedOption = mutableStateOf(selectedOption),
        onOptionSelected = {
            selectedOption = it
            // Handle the selection change here
            onOptionSelected.invoke(optionsList.indexOf(it))
        }
    )
}
