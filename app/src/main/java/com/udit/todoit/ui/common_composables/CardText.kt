package com.udit.todoit.ui.common_composables

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text

@Composable
fun CardText(
    text: String,
    textModifier: Modifier = Modifier,
//    textUnit: TextUnit = TextUnit(value = 10f, type = TextUnitType.Sp),
    cardModifier: Modifier = Modifier,
    onClickAction: () -> Unit = { Log.d("Card Text", "noClick") }
) {
    Card(
        modifier = cardModifier,
        onClick = {
            onClickAction()
        }
    ) {
        Text(
            text = text,
            modifier = textModifier
                .padding(vertical = 0.dp, horizontal = 10.dp),
        )
    }
}

@Composable
fun CardTextWithText(
    textComposable: @Composable () -> Unit,
    cardColors: CardColors = CardColors(
        contentColor = Color.Transparent,
        containerColor = Color.Transparent,
        disabledContentColor = Color.Red,
        disabledContainerColor = Color.Red
    ),
    cardModifier: Modifier = Modifier,
    onClickAction: () -> Unit = { Log.d("Card Text With Text Style", "noClick") }
) {
    Card(
        modifier = cardModifier,
        colors = cardColors,
        onClick = {
            onClickAction()
        }
    ) {
        textComposable()
    }
}
