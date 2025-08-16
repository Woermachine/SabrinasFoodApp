package com.stephenwoerner.sabrinafoodapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LocationErrorText() {
    Box (Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Location Error Occurred",
            color = Color.Red,
            style = TextStyle(
                color = Color.Red,
                fontWeight = FontWeight.W900,
                fontSize = 25.sp,
            ),
        )
    }
}

@Preview
@Composable
fun LocationErrorText_Preview() {
    LocationErrorText()
}