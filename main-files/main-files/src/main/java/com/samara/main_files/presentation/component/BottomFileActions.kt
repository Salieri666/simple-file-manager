package com.samara.main_files.presentation.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
    modifier: Modifier = Modifier
) {
    Column {
        Divider(thickness = 1.dp, color = Color.Black)

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            BottomFileAction(R.drawable.move, R.string.move)
            BottomFileAction(R.drawable.delete, R.string.delete)
            BottomFileAction(R.drawable.text_field, R.string.rename)
            BottomFileAction(R.drawable.detail, R.string.details)
        }
    }

}

@Composable
private fun BottomFileAction(@DrawableRes iconId: Int, @StringRes iconTextId: Int) {
    Column(
        modifier = Modifier.padding(Dimens.mediumPadding),
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
