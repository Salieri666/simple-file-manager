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
import com.samara.main_files.R
import theme.Dimens

@Composable
fun BottomFileActions(
    modifier: Modifier = Modifier,
    onClickItem: (BottomFileActionType) -> Unit = {}
) {
    Column {
        Divider(thickness = 1.dp, color = Color.Black)

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            BottomFileAction(R.drawable.move, R.string.move, onClick = { onClickItem(BottomFileActionType.MOVE) })
            BottomFileAction(R.drawable.delete, R.string.delete, onClick = { onClickItem(BottomFileActionType.DELETE) })
            BottomFileAction(R.drawable.text_field, R.string.rename, onClick = { onClickItem(BottomFileActionType.RENAME) })
            BottomFileAction(R.drawable.detail, R.string.details, onClick = { onClickItem(BottomFileActionType.DETAIL) })
        }
    }

}

@Composable
private fun BottomFileAction(
    @DrawableRes iconId: Int,
    @StringRes iconTextId: Int,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(Dimens.mediumPadding)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(
                id = iconId
            ),
            contentDescription = stringResource(id = iconTextId)
        )
        Text(text = stringResource(id = iconTextId))
    }
}

@Preview(showBackground = true)
@Composable
fun BottomFileActionsPreview() {
    BottomFileActions(modifier = Modifier.fillMaxWidth())
}

enum class BottomFileActionType {
    MOVE, DELETE, RENAME, DETAIL
}
