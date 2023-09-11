package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samara.ui_kit.R
import theme.Dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FileComponent(
    title: String,
    modifier: Modifier = Modifier,
    size: String = "",
    changedDate: String = "",
    isDir: Boolean = false,
    isChecked: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(80.dp)
    ) {

        Row(modifier = Modifier
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
            .padding(Dimens.mediumPadding)
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(end = Dimens.standardPadding),
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(
                        id =
                        if (isDir) R.drawable.folder
                        else R.drawable.file
                    ),
                    contentDescription = "Localized description"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = Dimens.enlargedPadding)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = Dimens.StandardTextSize,
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = Dimens.smallPadding)
                )

                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(bottom = Dimens.smallPadding)
                ) {
                    Text(
                        text = size,
                        fontSize = Dimens.SmallTextSize,
                        modifier = Modifier.padding(end = Dimens.smallPadding)
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)

                    )

                    Text(
                        text = changedDate,
                        fontSize = Dimens.SmallTextSize,
                        modifier = Modifier.padding(start = Dimens.smallPadding)
                    )
                }
            }

            Column(
                modifier =
                Modifier
                    .padding(start = Dimens.standardPadding)
                    .alpha(if (isChecked) 1.0f else 0.0f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = "Localized description"
                )

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FileComponentPreview() {
    FileComponent(
        "Test long fdssdfczxczxcslafdssdfczxczxcslafdssdfczxczxcslafdssdfczxczxcsla",
        Modifier.fillMaxWidth(),
        "200.1 MB",
        "2022-01-23",
        true, true
    )
}
