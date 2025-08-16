package com.stephenwoerner.sabrinafoodapp.ui

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check // Example
import androidx.compose.material.icons.filled.Close // Example
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephenwoerner.sabrinafoodapp.data.Action

// Define your three states (replace with your actual state names)
enum class TriStateSwitchState {
    STATE_ONE, // e.g., Include
    STATE_TWO, // e.g., Hide
    STATE_THREE; // e.g., Neutral / Optional / Off

    fun toAction(): Action {
        return when (this) {
            STATE_ONE -> Action.Include
            STATE_TWO -> Action.Exclude
            STATE_THREE -> Action.Optional
        }
    }
}

@Composable
fun TriStateCustomSwitch(
    modifier: Modifier = Modifier,
    currentState: TriStateSwitchState,
    onStateChange: (TriStateSwitchState) -> Unit,
    stateOneText: String = "Include",
    stateTwoText: String = "Exclude",
    stateThreeText: String = "Optional",
    stateOneIcon: ImageVector = Icons.Filled.Star,
    stateTwoIcon: ImageVector = Icons.Filled.Close,
    stateThreeIcon: ImageVector = Icons.Filled.Add,
    height: Dp = 40.dp,
    cornerRadiusPercent: Int = 50,
    activeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    inactiveBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    activeContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    inactiveContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    iconSize: Dp = 18.dp,
    spacingBetweenIconAndText: Dp = 4.dp
) {
    val animationSpec = tween<Dp>(durationMillis = 300) // Slightly longer for 3 states can feel smoother

    BoxWithConstraints(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(percent = cornerRadiusPercent))
            .background(inactiveBackgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(percent = cornerRadiusPercent))
    ) {
        val optionWidth = maxWidth / 3 // Divide by 3 for three options

        val selectedBackgroundOffsetX by animateDpAsState(
            targetValue = when (currentState) {
                TriStateSwitchState.STATE_ONE -> 0.dp
                TriStateSwitchState.STATE_TWO -> optionWidth
                TriStateSwitchState.STATE_THREE -> optionWidth * 2
            },
            animationSpec = animationSpec,
            label = "Selected Background Offset"
        )

        // Selected state background (moves)
        Box(
            modifier = Modifier
                .offset(x = selectedBackgroundOffsetX)
                .width(optionWidth)
                .fillMaxHeight()
                .background(
                    activeBackgroundColor,
                    RoundedCornerShape(percent = cornerRadiusPercent)
                )
        )

        // Text and Icon elements
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // State One Section
            SwitchOptionInternal(
                modifier = Modifier.width(optionWidth),
                text = stateOneText,
                icon = stateOneIcon,
                isSelected = currentState == TriStateSwitchState.STATE_ONE,
                activeColor = activeContentColor,
                inactiveColor = inactiveContentColor,
                iconSize = iconSize,
                spacingBetweenIconAndText = spacingBetweenIconAndText,
                onClick = { onStateChange(TriStateSwitchState.STATE_ONE) }
            )

            // State Two Section
            SwitchOptionInternal(
                modifier = Modifier.width(optionWidth),
                text = stateTwoText,
                icon = stateTwoIcon,
                isSelected = currentState == TriStateSwitchState.STATE_TWO,
                activeColor = activeContentColor,
                inactiveColor = inactiveContentColor,
                iconSize = iconSize,
                spacingBetweenIconAndText = spacingBetweenIconAndText,
                onClick = { onStateChange(TriStateSwitchState.STATE_TWO) }
            )

            // State Three Section
            SwitchOptionInternal(
                modifier = Modifier.width(optionWidth),
                text = stateThreeText,
                icon = stateThreeIcon,
                isSelected = currentState == TriStateSwitchState.STATE_THREE,
                activeColor = activeContentColor,
                inactiveColor = inactiveContentColor,
                iconSize = iconSize,
                spacingBetweenIconAndText = spacingBetweenIconAndText,
                onClick = { onStateChange(TriStateSwitchState.STATE_THREE) }
            )
        }
    }
}

@Composable
private fun SwitchOptionInternal( // Renamed to avoid conflict if original is still used
    modifier: Modifier = Modifier,
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
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // No ripple effect
                onClick = onClick
            )
            .padding(horizontal = 6.dp), // Adjusted padding slightly for potentially tighter space
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text, // Content description for accessibility
            tint = contentColor,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(spacingBetweenIconAndText))
        Text(
            text = text,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 13.sp, // Slightly smaller font if text is long for 3 options
            maxLines = 1,
            overflow = TextOverflow.Ellipsis // Added Ellipsis for overflow
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun SwitchOptionInternalPreviewSelected() {
    MaterialTheme {
        SwitchOptionInternal(
            modifier = Modifier.width(120.dp), // Example width
            text = "Include",
            icon = Icons.Filled.Check,
            isSelected = true,
            activeColor = MaterialTheme.colorScheme.onPrimary,
            inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant,
            iconSize = 18.dp,
            spacingBetweenIconAndText = 4.dp,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun SwitchOptionInternalPreviewNotSelected() {
    MaterialTheme {
        SwitchOptionInternal(
            modifier = Modifier.width(120.dp), // Example width
            text = "Hide",
            icon = Icons.Filled.Check, // Using same icon for simplicity in preview
            isSelected = false,
            activeColor = MaterialTheme.colorScheme.onPrimary,
            inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant,
            iconSize = 18.dp,
            spacingBetweenIconAndText = 4.dp,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun SwitchOptionInternalPreviewLongText() {
    MaterialTheme {
        SwitchOptionInternal(
            modifier = Modifier.width(100.dp), // Example width, potentially tight
            text = "VeryLongOptionText",
            icon = Icons.Filled.Check,
            isSelected = true,
            activeColor = MaterialTheme.colorScheme.onPrimary,
            inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant,
            iconSize = 18.dp,
            spacingBetweenIconAndText = 2.dp, // Reduced spacing for long text
            onClick = {}
        )
    }
}


// --- Previews for TriStateCustomSwitch ---

@Preview(showBackground = true, name = "State One Selected", widthDp = 300)
@Composable
fun TriStateCustomSwitchPreviewStateOne() {
    MaterialTheme {
        Surface { // Surface provides a background color from the theme
            var currentState by remember { mutableStateOf(TriStateSwitchState.STATE_ONE) }
            TriStateCustomSwitch(
                modifier = Modifier.padding(16.dp),
                currentState = currentState,
                onStateChange = { newState -> currentState = newState }
            )
        }
    }
}

@Preview(showBackground = true, name = "State Two Selected", widthDp = 300)
@Composable
fun TriStateCustomSwitchPreviewStateTwo() {
    MaterialTheme {
        Surface {
            var currentState by remember { mutableStateOf(TriStateSwitchState.STATE_TWO) }
            TriStateCustomSwitch(
                modifier = Modifier.padding(16.dp),
                currentState = currentState,
                onStateChange = { newState -> currentState = newState },
                stateOneText = "Yes",
                stateTwoText = "No",
                stateThreeText = "Maybe",
            )
        }
    }
}

@Preview(showBackground = true, name = "State Three Selected", widthDp = 300)
@Composable
fun TriStateCustomSwitchPreviewStateThree() {
    MaterialTheme {
        Surface {
            var currentState by remember { mutableStateOf(TriStateSwitchState.STATE_THREE) }
            TriStateCustomSwitch(
                modifier = Modifier.padding(16.dp),
                currentState = currentState,
                onStateChange = { newState -> currentState = newState },
                stateOneText = "Required",
                stateTwoText = "Allowed",
                stateThreeText = "Not Allowed",
            )
        }
    }
}
