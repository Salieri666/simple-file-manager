package com.samara.main_files.presentation.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samara.main_files.presentation.screens.files.BottomFileAction
import com.samara.main_files.presentation.screens.files.BottomFileActionType
import com.samara.main_files.presentation.screens.files.getBottomFileActionsList
import theme.Dimens

@Composable
fun BottomFileActions(
    bottomActions: List<BottomFileAction>,
    modifier: Modifier = Modifier,
    onClickItem: (BottomFileActionType) -> Unit = {}
) {
    Column {
        Divider(thickness = 1.dp, color = Color.Black)

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            bottomActions.forEach {
                BottomFileAction(it.icon, it.title, it.isActive, onClick = { onClickItem(it.type) })
            }
        }
    }

}

@Composable
private fun BottomFileAction(
    @DrawableRes iconId: Int,
    @StringRes iconTextId: Int,
    isActive: Boolean,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(Dimens.mediumPadding)
            .clickable(enabled = isActive) {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(
                id = iconId
            ),
            contentDescription = stringResource(id = iconTextId),
            tint = if (isActive) Color.Black else Color.Gray
        )
        Text(text = stringResource(id = iconTextId))
    }
}

@Preview(showBackground = true)
@Composable
fun BottomFileActionsPreview() {
    BottomFileActions(bottomActions = getBottomFileActionsList(), modifier = Modifier.fillMaxWidth())
}