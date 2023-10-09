package com.samara.main_files.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.samara.main_files.R
import theme.Dimens


@Composable
fun DetailDialog(
    counts: Int,
    size: String,
    modifier: Modifier = Modifier,
    name: String = "",
    lastChanged: String = "",
    onConfirmation: () -> Unit = {},
) {
    Dialog(onDismissRequest  = { onConfirmation() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(Dimens.enlargedPadding),
            shape = RoundedCornerShape(Dimens.enlargedPadding),
        ) {
            Column(modifier = Modifier.padding(Dimens.enlargedPadding)) {
                Text(text = stringResource(id = R.string.detailTitle), fontWeight = FontWeight.Bold)

                Text(text = if (counts == 1) name else stringResource(id = R.string.numberOfElements, counts),
                    Modifier.padding(vertical = Dimens.enlargedPadding))

                Text(text = "Size: $size",
                    Modifier.padding(top = Dimens.standardPadding))

                if (counts == 1) {
                    Text(
                        text = "Last changes: $lastChanged",
                        Modifier.padding(top = Dimens.standardPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailDialogPreview() {
    DetailDialog(
        2, "23Mb", name = "Test name", lastChanged = "2023-01-12"
    )
}