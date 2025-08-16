package com.stephenwoerner.sabrinafoodapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stephenwoerner.sabrinafoodapp.data.Action
import com.stephenwoerner.sabrinafoodapp.data.Filter
import com.stephenwoerner.sabrinafoodapp.data.LatLng
import com.stephenwoerner.sabrinafoodapp.data.Restaurant
import com.stephenwoerner.sabrinafoodapp.service.LocationService
import com.stephenwoerner.sabrinafoodapp.service.LocationService.Companion.haversineDistance
import com.stephenwoerner.sabrinafoodapp.ui.FilterCriteria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private fun List<Restaurant>.sortByDistance(location: LatLng): List<Restaurant> = this.sortedByDescending { haversineDistance(location, it.location) }

class MainViewModel(private val app: Application): AndroidViewModel(app) {
    val filters = MutableStateFlow<List<Filter>>(emptyList())
    val restaurants : MutableStateFlow<List<Restaurant>> = MutableStateFlow(Restaurant.entries)
    val hasLocationPermissions: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showLocationError: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val locationService = LocationService(app)

    private val myLocation: MutableStateFlow<LatLng> =
        MutableStateFlow(LatLng(39.649680, -79.897005))

    init {
        setupFilters()
        setupLocationListener()
        setupHasLocation()
    }

    private fun setupFilters() = viewModelScope.launch {
        combine(filters, myLocation) { activeFilters, myLocation ->
            var entries = Restaurant.entries.toList()

            val requiredFilters = activeFilters.filter { it.action == Action.Include || it.action == Action.Exclude }
            val optional = activeFilters.filter { it.action == Action.Optional }

            requiredFilters.forEach {
                entries = entries.filter(it.toPredicate)
            }

            if (optional.isNotEmpty()) {
                entries = entries.filter { entry ->
                    // Check if the entry matches ANY of the optional filter predicates
                    optional.any { optionalFilter ->
                        optionalFilter.toPredicate(entry)
                    }
                }
            }

            Pair(entries, myLocation)
        }.map { (entries, myLocation) ->
            entries.sortByDistance(myLocation)
        }.collectLatest { listOfRestaurants ->
            restaurants.emit(listOfRestaurants)
        }
    }

    private fun setupHasLocation() = viewModelScope.launch {
        hasLocationPermissions.emit(LocationService.hasLocationPermission(app))
    }

    private fun setupLocationListener() = viewModelScope.launch {
        hasLocationPermissions.collectLatest { hasPermissions -> if (hasPermissions) {
            locationService.getLastKnownLocation { result ->
                viewModelScope.launch {
                    if (result.isSuccess) {
                        showLocationError.emit(false)
                        myLocation.emit(
                            result.getOrThrow().let { location ->
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                            }

                        )
                    }
                    if (result.isFailure) {
                        showLocationError.emit(true)
                    }
                }
            }
        }}
    }

    fun addFilters(newFilters: FilterCriteria) = viewModelScope.launch {
        filters.update {
            it + newFilters.toList()
        }
    }

    fun removeFilter(filter: Filter) = viewModelScope.launch {
        filters.update {
            it - filter
        }
    }


}