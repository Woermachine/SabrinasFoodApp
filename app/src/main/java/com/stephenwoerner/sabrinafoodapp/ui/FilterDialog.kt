package com.stephenwoerner.sabrinafoodapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.stephenwoerner.sabrinafoodapp.data.Filter
import com.stephenwoerner.sabrinafoodapp.data.Tag
import com.stephenwoerner.sabrinafoodapp.data.Type


data class FilterCriteria(
    val tags: Set<Tag> = emptySet(),
    val action: TriStateSwitchState = TriStateSwitchState.STATE_ONE
) {
    fun toList(): List<Filter> = tags.map { Filter(action = action.toAction(), tag = it) }.toList()
}

data class TagGroup(
    val title: String,
    val tags: List<Tag>
)

@Composable
fun FilterDialog(
    allTagGroups: List<TagGroup>,
    onDismissRequest: () -> Unit,
    onApplyFilters: (FilterCriteria) -> Unit
) {
    // Internal state for the dialog
    var currentState by remember { mutableStateOf(TriStateSwitchState.STATE_ONE) }
    var selectedTagsState by remember { mutableStateOf(setOf<Tag>()) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                item {
                    TriStateCustomSwitch(
                        modifier = Modifier.fillMaxWidth(),
                        currentState = currentState,
                        onStateChange = {
                            currentState = it
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    FilterSection(
                        allTagGroups = allTagGroups,
                        selectedTags = selectedTagsState,
                        onTagSelectionChanged = { tag, isSelected ->
                            selectedTagsState = if (isSelected) {
                                HashSet(selectedTagsState + tag)
                            } else {
                                HashSet(selectedTagsState - tag)
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onApplyFilters(
                    FilterCriteria(
                        tags = selectedTagsState,
                        action = currentState
                    )
                )
                onDismissRequest()
            }) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false), // Allow wider dialog
        modifier = Modifier.fillMaxWidth(0.95f) // Adjust width as needed
    )
}

@Composable
fun FilterSection(
    allTagGroups: List<TagGroup>,
    selectedTags: Set<Tag>,
    onTagSelectionChanged: (Tag, Boolean) -> Unit
) {
    Column {
        allTagGroups.forEach { tagGroup ->
            ExpandableTagList(
                tagGroup = tagGroup,
                selectedTags = selectedTags,
                onTagClick = { tag ->
                    val isCurrentlySelected = selectedTags.contains(tag)
                    onTagSelectionChanged(tag, !isCurrentlySelected)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandableTagList(
    tagGroup: TagGroup,
    selectedTags: Set<Tag>,
    onTagClick: (Tag) -> Unit,
    initiallyExpanded: Boolean = false
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(tagGroup.title, style = MaterialTheme.typography.titleSmall)
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse ${tagGroup.title}" else "Expand ${tagGroup.title}"
            )
        }

        AnimatedVisibility(visible = expanded) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Renamed from verticalAlignment for FlowRow
            ) {
                tagGroup.tags.forEach { tag ->
                    val isSelected = selectedTags.contains(tag)
                    FilterChip(
                        selected = isSelected,
                        onClick = { onTagClick(tag) },
                        label = { Text(tag.title) },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Selected",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.outline))
    }
}

@Preview
@Composable
fun FilterBuilderDialogPreview() {
    val ethnicity = Tag.entries.filter { it.type == Type.Ethnicity }.sorted()
    val foodItems = Tag.entries.filter { it.type == Type.FoodItem }.sorted()
    val other = Tag.entries.filter { it.type == Type.Other }.sorted()
    FilterDialog(
        allTagGroups = listOf(
            TagGroup("Ethnicity", ethnicity),
            TagGroup("Food Items", foodItems),
            TagGroup("Other", other),
        ),
        onDismissRequest = { },
        onApplyFilters = { },
    )
}

@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun ExpandableTagListPreview() {
    val ethnicity = Tag.entries.filter { it.type == Type.Ethnicity }.sorted()

    val selectedTags by remember { mutableStateOf(setOf(ethnicity[0])) }
    ExpandableTagList(
        tagGroup = TagGroup(
            title = "Test Group",
            tags = ethnicity,
        ),
        selectedTags = selectedTags,
        onTagClick = {
            if (selectedTags.contains(it)) {
                selectedTags - setOf(it)
            } else {
                selectedTags + setOf(it)
            }
        }
    )
}

@Preview(showBackground = true, name = "Filter Section Preview")
@Composable
fun FilterSectionPreview() {
    val ethnicity = Tag.entries.filter { it.type == Type.Ethnicity }.sorted()
    val foodItems = Tag.entries.filter { it.type == Type.FoodItem }.sorted()
    val other = Tag.entries.filter { it.type == Type.Other }.sorted()

    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            FilterSection(
                allTagGroups = listOf(
                    TagGroup("Ethnicity", ethnicity),
                    TagGroup("Food Items", foodItems),
                    TagGroup("Other", other),
                ),
                selectedTags = mutableSetOf(foodItems[0]),
                onTagSelectionChanged = { tag, isSelected ->
                    println("Tag '${tag.title}' selection changed to: $isSelected")
                }
            )
        }
    }
}