package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import theme.Dimens

@Composable
fun FileComponent(
    title: String,
    modifier: Modifier = Modifier,
    isDir: Boolean = false,
    onClick: (Boolean) -> Unit = {}
) {
    Row(modifier = modifier
        .clickable { onClick(isDir) }
        .padding(Dimens.enlargedPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = Dimens.MediumHeadlineSize)

        if (isDir) {
            Text(text = "dir", fontSize = Dimens.MediumHeadlineSize)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FileComponentPreview() {
    FileComponent("Test", Modifier.fillMaxWidth(), true)
}