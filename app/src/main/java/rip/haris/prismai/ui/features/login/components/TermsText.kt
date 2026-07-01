package rip.haris.prismai.ui.features.login.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun TermsText(modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        ) {
            append("By continuing, you agree to PrismAI's ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Consumer Terms")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        ) {
            append(" and ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Usage Policy")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        ) {
            append(", and acknowledge their ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        ) {
            append(".")
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center
        ),
        onClick = { }
    )
}
