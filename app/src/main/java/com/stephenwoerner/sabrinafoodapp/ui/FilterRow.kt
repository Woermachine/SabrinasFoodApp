package com.stephenwoerner.sabrinafoodapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephenwoerner.sabrinafoodapp.R
import com.stephenwoerner.sabrinafoodapp.data.*
import com.stephenwoerner.sabrinafoodapp.ui.theme.DarkGreen
import com.stephenwoerner.sabrinafoodapp.ui.theme.LightGreen
import com.stephenwoerner.sabrinafoodapp.ui.theme.LightRed

@Composable
fun HorizontalChipsWithStickyAdd(
    modifier: Modifier = Modifier,
    chipItems: List<Filter>, // List of strings to display in chips
    onAddClick: () -> Unit,
    onChipClick: (Filter) -> Unit = {} // Optional: handle chip clicks
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Horizontal Scrolling Chips
        LazyRow(
            modifier = Modifier.weight(1f), // Takes up remaining horizontal space
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Spacing between chips
            contentPadding = PaddingValues(end = 8.dp) // Padding at the end of the list
        ) {
            item { Spacer(modifier = Modifier.width(8.dp)) }
            items(chipItems) { filter ->
                AssistChip(
                    onClick = { onChipClick(filter) },
                    label = { Text(filter.tag.title) },
                    leadingIcon = {
                        Icon(
                            imageVector = when (filter.action) {
                                Action.Include -> Icons.Filled.Star
                                Action.Exclude -> Icons.Filled.Close
                                Action.Optional -> Icons.Filled.Add
                            },
                            contentDescription = filter.tag.title,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors().copy(
                        containerColor = when (filter.action) {
                            Action.Include -> DarkGreen
                            Action.Optional -> LightGreen
                            Action.Exclude -> LightRed
                        }
                    )
                )
            }
        }
        IconButton(
            onClick = onAddClick,
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(start = 8.dp, end = 8.dp)
            ,
        ) {
            Image(
                painter = painterResource(R.drawable.filter),
                contentDescription = "Add Item",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000000)
@Composable
fun HorizontalChipsWithStickyAddPreview() {
    val items by remember {
        mutableStateOf(
            listOf(
                Filter(tag = Tag.Italian, action = Action.Include),
                Filter(tag = Tag.Japanese, action = Action.Include),
                Filter(tag = Tag.American, action = Action.Include),
                Filter(tag = Tag.Mexican, action = Action.Exclude),
                Filter(tag = Tag.Sushi, action = Action.Exclude),
                Filter(tag = Tag.Salad, action = Action.Include),
                Filter(tag = Tag.Wine, action = Action.Exclude),
                Filter(tag = Tag.Beer, action = Action.Exclude),
                Filter(tag = Tag.Brunch, action = Action.Include),
            )
        )
    }

    MaterialTheme {
        Column {
            HorizontalChipsWithStickyAdd(
                chipItems = items,
                onAddClick = {
                    println("Add button clicked")
                },
                onChipClick = { chipText ->
                    println("$chipText clicked")
                }
            )
        }
    }
}