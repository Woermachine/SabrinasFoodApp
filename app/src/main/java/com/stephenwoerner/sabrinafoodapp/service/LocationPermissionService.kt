package com.stephenwoerner.sabrinafoodapp.service

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.stephenwoerner.sabrinafoodapp.data.LatLng
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class LocationService(context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(onLocationResult: (Result<Location>) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    onLocationResult(Result.success(location))
                } else {
                    // Last location is null. This can happen if location was never previously retrieved
                    // or if location services were recently disabled.
                    // You might want to fall back to getCurrentLocation here.
                    onLocationResult(Result.failure(Exception("Last known location is null.")))
                }
            }
            .addOnFailureListener { exception ->
                onLocationResult(Result.failure(exception))
            }
    }

    companion object {
        private const val EARTH_RADIUS_KM = 6371.0

        /**
         * Calculates the distance in kilometers between two LatLng points using the Haversine formula.
         *
         * @param location1 Latitude and Longitude of the first point in degrees.
         * @param location2 Longitude of the first point in degrees.
         * @return The distance in kilometers.
         */
        fun haversineDistance(location1: LatLng, location2: LatLng): Double {
            val dLat = Math.toRadians(location2.latitude - location1.latitude)
            val dLon = Math.toRadians(location2.longitude - location1.longitude)

            val lat1Rad = Math.toRadians(location1.latitude)
            val lat2Rad = Math.toRadians(location2.latitude)

            val a = sin(dLat / 2).pow(2) +
                    sin(dLon / 2).pow(2) * cos(lat1Rad) * cos(lat2Rad)
            val c = 2 * asin(sqrt(a))

            return EARTH_RADIUS_KM * c
        }

        fun hasLocationPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}