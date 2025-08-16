import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephenwoerner.sabrinafoodapp.data.Action

// VisibilitySwitchState enum remains the same
enum class IncludeHideSwitchState {
    REQUIRED, NOTALLOWED, ALLOWED;

    fun toAction(): Action {
        return when (this) {
            REQUIRED -> Action.Include
            NOTALLOWED -> Action.Exclude
            ALLOWED -> Action.Optional
        }
    }
}

@Composable
fun IncludeHideSwitch(
    modifier: Modifier = Modifier, // Allow external modifiers
    currentState: IncludeHideSwitchState,
    onStateChange: (IncludeHideSwitchState) -> Unit,
    includeText: String = "Include",
    hideText: String = "Hide",
    includeIcon: ImageVector = Icons.Filled.Check,
    hideIcon: ImageVector = Icons.Filled.Close,
    height: Dp = 40.dp, // Keep height as a direct parameter
    cornerRadiusPercent: Int = 50, // Use percentage for dynamic corner radius
    activeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    inactiveBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    activeContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    inactiveContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    iconSize: Dp = 18.dp,
    spacingBetweenIconAndText: Dp = 4.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val animationSpec = tween<Dp>(durationMillis = 250)

    // Use BoxWithConstraints to get the available width
    BoxWithConstraints(
        modifier = modifier // Apply the external modifier here
            .height(height) // Set height directly
            .clip(RoundedCornerShape(percent = cornerRadiusPercent)) // Dynamic corner radius
            .background(inactiveBackgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(percent = cornerRadiusPercent))
    ) {
        // maxWidth is available from BoxWithConstraintsScope (it's in Dp)
        val optionWidth = maxWidth / 2

        val selectedBackgroundOffsetX by animateDpAsState(
            targetValue = if (currentState == IncludeHideSwitchState.REQUIRED) 0.dp else optionWidth,
            animationSpec = animationSpec,
            label = "Selected Background Offset"
        )

        // Selected state background (moves)
        Box(
            modifier = Modifier
                .offset(x = selectedBackgroundOffsetX)
                .width(optionWidth) // Use half of the available width
                .fillMaxHeight()    // Fill the height of the parent BoxWithConstraints
                .background(activeBackgroundColor, RoundedCornerShape(percent = cornerRadiusPercent))
        )

        // Text and Icon elements
        Row(
            modifier = Modifier.fillMaxSize(), // Row fills the BoxWithConstraints
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "Include" Section
            SwitchOption(
                modifier = Modifier.width(optionWidth), // Explicitly set width for each option
                text = includeText,
                icon = includeIcon,
                isSelected = currentState == IncludeHideSwitchState.REQUIRED,
                activeColor = activeContentColor,
                inactiveColor = inactiveContentColor,
                iconSize = iconSize,
                spacingBetweenIconAndText = spacingBetweenIconAndText,
                onClick = { onStateChange(IncludeHideSwitchState.REQUIRED) }
            )

            // "Hide" Section
            SwitchOption(
                modifier = Modifier.width(optionWidth), // Explicitly set width for each option
                text = hideText,
                icon = hideIcon,
                isSelected = currentState == IncludeHideSwitchState.NOTALLOWED,
                activeColor = activeContentColor,
                inactiveColor = inactiveContentColor,
                iconSize = iconSize,
                spacingBetweenIconAndText = spacingBetweenIconAndText,
                onClick = { onStateChange(IncludeHideSwitchState.NOTALLOWED) }
            )
        }
    }
}

// SwitchOption remains largely the same, but ensure it respects the width passed to it
@Composable
private fun SwitchOption(
    modifier: Modifier = Modifier, // This modifier will now include .width(optionWidth)
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    iconSize: Dp,
    spacingBetweenIconAndText: Dp,
    onClick: () -> Unit
) {
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) activeColor else inactiveColor,
        animationSpec = tween(250),
        label = "Content Color"
    )

    Row(
        modifier = modifier // Apply the passed modifier (which includes width)
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = contentColor,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(spacingBetweenIconAndText))
        Text(
            text = text,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            maxLines = 1 // Ensure text doesn't wrap if space is tight
        )
    }
}


@Preview(showBackground = true, name = "IncludeHideSwitch FillMaxWidth")
@Composable
fun IncludeHideSwitchFillWidthPreview() {
    var switchState by remember { mutableStateOf(IncludeHideSwitchState.REQUIRED) }
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Current State: ${switchState.name}")
                Spacer(Modifier.height(16.dp))
                IncludeHideSwitch(
                    modifier = Modifier.fillMaxWidth(), // <--- HERE
                    currentState = switchState,
                    onStateChange = { newState -> switchState = newState }
                )
                Spacer(Modifier.height(16.dp))
                Text("With fixed width for comparison:")
                IncludeHideSwitch(
                    modifier = Modifier.width(220.dp), // Fixed width
                    currentState = switchState,
                    onStateChange = { newState -> switchState = newState }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "IncludeHideSwitch FillMaxWidth")
@Composable
fun IncludeHideSwitchFillPreview() {
    var switchState by remember { mutableStateOf(IncludeHideSwitchState.REQUIRED) }
    MaterialTheme {
        Box(modifier = Modifier.size(500.dp)) {
            IncludeHideSwitch(
                modifier = Modifier.fillMaxWidth().padding(10.dp), // <--- HERE
                currentState = switchState,
                onStateChange = { newState -> switchState = newState }
            )
        }
    }
}
