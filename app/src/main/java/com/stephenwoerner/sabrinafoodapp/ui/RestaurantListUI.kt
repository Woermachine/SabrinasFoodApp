package com.stephenwoerner.sabrinafoodapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stephenwoerner.sabrinafoodapp.MainViewModel
import com.stephenwoerner.sabrinafoodapp.data.*
import com.stephenwoerner.sabrinafoodapp.data.Restaurant

@Composable
fun RestaurantListUI(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
) {
    val restaurants by mainViewModel.restaurants.collectAsStateWithLifecycle()
    val filters by mainViewModel.filters.collectAsStateWithLifecycle()
    val showLocationError by mainViewModel.showLocationError.collectAsStateWithLifecycle()
    val hasLocationPermissions by mainViewModel.hasLocationPermissions.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }

    RestaurantsList(
        modifier = modifier,
        restaurants = restaurants,
        filters = filters,
        showLocationError = showLocationError,
        showLocationRequester = !hasLocationPermissions,
        addClick = {
            showDialog = true
        },
        onChipClick = {
            mainViewModel.removeFilter(it)
        }
    )

    if (showDialog) {
        FilterDialog(
            allTagGroups = listOf(
                TagGroup("Ethnicity", Tag.entries.filter { it.type == Type.Ethnicity }.sorted()),
                TagGroup("Food Items", Tag.entries.filter { it.type == Type.FoodItem }.sorted()),
                TagGroup("Other", Tag.entries.filter { it.type == Type.Other }.sorted()),
            ),
            onDismissRequest = {
                showDialog = false
            },
            onApplyFilters = {
                mainViewModel.addFilters(it)
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RestaurantsList(
    modifier: Modifier = Modifier,
    filters: List<Filter>,
    restaurants: List<Restaurant>,
    showLocationRequester: Boolean,
    showLocationError: Boolean,
    addClick: () -> Unit = {},
    onChipClick: (Filter) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        stickyHeader {
            Text(
                modifier = Modifier
                    .background(Color(0xFFF15C8C))
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Sabrina's Restaurant App",
                textAlign = TextAlign.Center,
                color = Color.White,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.W700
                )
            )
        }
        stickyHeader {
            HorizontalChipsWithStickyAdd(
                chipItems = filters,
                onAddClick = addClick,
                onChipClick = onChipClick,
            )
        }
        if (showLocationRequester) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    LocationPermissionRequester(Modifier.align(Alignment.Center))
                }
            }
        }
        if (showLocationError) {
            item {
                LocationErrorText()
            }
        }
        items(restaurants) { restaurant ->
            RestaurantItem(restaurant)
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Restaurant, onClick: () -> Unit = {}) {
    Row(modifier = Modifier.clickable(onClick = onClick)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = restaurant.title,
        )
    }
    restaurant.title
}

@Preview
@Composable
fun RestaurantItem_Preview() {
    RestaurantItem(restaurant = Restaurant.LakeHouse)
}

@Preview(showBackground = true)
@Composable
fun RestaurantsList_Preview() {
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
                Filter(tag = Tag.Brunch, action = Action.Include)
            )
        )
    }
    RestaurantsList(
        restaurants = Restaurant.entries.sortedBy { it.title }.take(20),
        filters = items,
        showLocationError = false,
        showLocationRequester = false,
    )
}

@Preview(showBackground = true)
@Composable
fun RestaurantsListError_Preview() {
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
                Filter(tag = Tag.Brunch, action = Action.Include)
            )
        )
    }
    RestaurantsList(
        restaurants = Restaurant.entries.sortedBy { it.title }.take(20),
        filters = items,
        showLocationError = true,
        showLocationRequester = false,
    )
}

@Preview(showBackground = true)
@Composable
fun RestaurantsListRequester_Preview() {
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
                Filter(tag = Tag.Brunch, action = Action.Include)
            )
        )
    }
    RestaurantsList(
        restaurants = Restaurant.entries.sortedBy { it.title }.take(20),
        filters = items,
        showLocationError = false,
        showLocationRequester = true,
    )
}