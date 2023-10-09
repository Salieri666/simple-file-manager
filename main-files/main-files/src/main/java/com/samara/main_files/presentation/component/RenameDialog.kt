package com.samara.main_files.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.samara.main_files.R
import theme.Dimens


@Composable
fun RenameDialog(
    title: String,
    modifier: Modifier = Modifier,
    onConfirm: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(Dimens.enlargedPadding),
            shape = RoundedCornerShape(Dimens.enlargedPadding),
        ) {
            Column(modifier = Modifier.padding(Dimens.enlargedPadding)) {
                Text(
                    text = stringResource(id = R.string.renameTitle),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = Dimens.enlargedPadding)
                )

                var titleState by remember {
                    mutableStateOf(title)
                }
                OutlinedTextField(value = titleState, onValueChange = { titleState = it }, singleLine = true)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = Dimens.standardPadding),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }

                    TextButton(onClick = { onConfirm(
                        titleState
                    ) }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RenameDialogPreview() {
    RenameDialog(
        "test title lognsjdfbsdbfssdfsdfsdfsfkdsjfhsjdhfksfdhb"
    )
}