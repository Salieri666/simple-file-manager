package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import theme.Dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FileComponent(
    title: String,
    modifier: Modifier = Modifier,
    isDir: Boolean = false,
    isChecked: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Row(modifier = modifier
        .combinedClickable(
            onClick = { onClick() },
            onLongClick = { onLongClick() }
        )
        .padding(Dimens.enlargedPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = Dimens.MediumHeadlineSize)

        Row(
            modifier = Modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            if (isDir) {
                Text(
                    text = "dir", fontSize = Dimens.MediumHeadlineSize
                )
            }

            if (isChecked) {
                Box(modifier = Modifier.padding(start = Dimens.standardPadding)) {
                    Icon(
                        Icons.Rounded.Check,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FileComponentPreview() {
    FileComponent("Test", Modifier.fillMaxWidth(), true, true)
}