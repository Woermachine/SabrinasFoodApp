package com.stephenwoerner.sabrinafoodapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun LocationPermissionRequester(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {},
) {
    val context = LocalContext.current
    var showRationaleDialog by remember { mutableStateOf(false) }

    // Prepare the permission request launcher
    val requestMultiplePermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val fineLocationGranted = permissionsMap[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissionsMap[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            onPermissionGranted()
        } else {
            // Check if we should show a rationale if permissions were denied.
            // This is a bit tricky with the new contract as it doesn't directly give this info
            // for subsequent denials without "Don't ask again".
            // A common pattern is to assume if not granted, it's denied.
            // For "Don't ask again", you'd typically guide the user to settings.
            onPermissionDenied()
        }
    }

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    Column (modifier = modifier) {
        Button(onClick = {
            var allPermissionsGranted = true
            for (permission in permissionsToRequest) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }

            if (allPermissionsGranted) {
                onPermissionGranted()
            } else {
                // Determine if we should show a rationale.
                // This part is more relevant if you're handling the rationale UI yourself
                // before launching the system dialog.
                var shouldShowRationale = false
                for (permission in permissionsToRequest) {
                    // Note: ActivityCompat.shouldShowRequestPermissionRationale might not work as expected
                    // for the first time or after "Don't ask again".
                    // It's generally better to explain upfront why you need the permission.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context as android.app.Activity, permission)) {
                        shouldShowRationale = true
                        break
                    }
                }

                if (shouldShowRationale) {
                    // You can show your own custom rationale dialog here
                    // For this example, let's assume a simple state toggle
                    showRationaleDialog = true
                } else {
                    // Directly launch the permission request
                    requestMultiplePermissionsLauncher.launch(permissionsToRequest)
                }
            }
        }) {
            Text("Request Location Permissions")
        }

        if (showRationaleDialog) {
            // Example of a simple rationale (replace with your actual UI)
            AlertDialog(
                onDismissRequest = { showRationaleDialog = false },
                title = { Text("Location Permission Needed") },
                text = { Text("This app needs location access to provide [your feature description]. Please grant the permission.") },
                confirmButton = {
                    Button(onClick = {
                        showRationaleDialog = false
                        requestMultiplePermissionsLauncher.launch(permissionsToRequest)
                    }) {
                        Text("Continue")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showRationaleDialog = false
                        onPermissionDenied() // User dismissed rationale
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

// Example Usage in another Composable:
@Composable
fun MyScreenWithLocation() {
    var locationStatus by remember { mutableStateOf("Permissions not yet requested.") }

    LocationPermissionRequester(
        onPermissionGranted = {
            locationStatus = "Location permissions GRANTED!"
            // TODO: Proceed to get location
            println(locationStatus)
        },
        onPermissionDenied = {
            locationStatus = "Location permissions DENIED."
            // TODO: Explain why the feature is unavailable or guide to settings
            println(locationStatus)
        }
    )
    Text(locationStatus)
}

@Preview
@Composable
fun LocationPermissionRequester_Preview() {
    LocationPermissionRequester()
}

@Preview
@Composable
fun MyScreenWithLocation_Preview() {
    MyScreenWithLocation()
}