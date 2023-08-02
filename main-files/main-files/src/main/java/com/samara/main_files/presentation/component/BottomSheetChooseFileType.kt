package com.samara.main_files.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.samara.main_files.presentation.mappers.FileExtensions
import theme.Dimens

@Composable
fun BottomSheetChooseFileType(
    elements: List<FileExtensions>,
    modifier: Modifier = Modifier,
    onClick: (FileExtensions) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(elements) {item ->
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.largePadding),
                modifier = Modifier
                    .clickable { }
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)) {
/*                Icon(
                    Icons.Rounded.ShoppingCart, contentDescription = null
                )*/
                Box(modifier = Modifier.fillMaxWidth().clickable {
                    onClick(item)}
                    ) {
                    Text(
                        stringResource(id = item.nameRes))
                }

            }
        }
    }
}