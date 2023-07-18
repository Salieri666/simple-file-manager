package theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val Typography = Typography(
    bodyLarge = UiTypography.bodyLarge,
    /*titleLarge = UiTypography.titleLarge,
    labelSmall = UiTypography.labelSmall*/
)

object UiTypography {
    val bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.LargeBodySize,
        lineHeight = Dimens.MediumHeadlineSize,
        letterSpacing = Dimens.LetterSpacing
    )
    val titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.MediumHeadlineSize,
        lineHeight = Dimens.LargeBodyHeight,
        letterSpacing = Dimens.zeroText
    )
    val labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = Dimens.SmallTextSize,
        lineHeight = Dimens.LargeBodySize,
        letterSpacing = Dimens.LetterSpacing
    )
}