package com.samara.main_files.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.samara.main_files.R
import theme.Dimens

@Composable
fun TopBarComponent(
    titleString: String,
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        ConstraintLayout(modifier =  Modifier.fillMaxWidth()) {
            val (cancelIcon, title) = createRefs()

           Box(
                modifier = Modifier
                    .constrainAs(cancelIcon) {
                        top.linkTo(parent.top, margin = Dimens.enlargedPadding)
                        start.linkTo(parent.start, margin = Dimens.enlargedPadding)
                    }.padding(bottom = Dimens.enlargedPadding)
                    .clickable {
                        onCancelClick()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = stringResource(
                        id = R.string.cancel
                    )
                )
            }

            Text(text = titleString, modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(cancelIcon.top)
                bottom.linkTo(cancelIcon.bottom)
                end.linkTo(parent.end)
            }.padding(bottom = Dimens.enlargedPadding))
        }

        Divider(thickness = 1.dp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarComponentPreview() {
    TopBarComponent(modifier = Modifier.fillMaxWidth(), titleString = "Test title")
}
