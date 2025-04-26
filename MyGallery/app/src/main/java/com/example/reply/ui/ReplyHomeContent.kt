package com.example.reply.ui

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reply.R
import com.example.reply.data.Email
import com.example.reply.data.local.LocalAccountsDataProvider

@Composable
fun ReplyListOnlyContent(
    replyUiState: ReplyUiState,
    onEmailCardPressed: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    val emails = replyUiState.currentMailboxEmails

    LazyColumn(
        modifier = modifier,
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.email_list_item_vertical_spacing)
        )
    ) {
        item {
            ReplyHomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.topbar_padding_vertical))
            )
        }
        items(emails, key = { email -> email.id }) { email ->
            ReplyEmailListItem(
                email = email,
                selected = false,
                onCardClick = {
                    onEmailCardPressed(email)
                }
            )
        }
    }
}

@Composable
fun ReplyListAndDetailContent(
    replyUiState: ReplyUiState,
    onEmailCardPressed: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    val emails = replyUiState.currentMailboxEmails
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LazyColumn(
            contentPadding = WindowInsets.statusBars.asPaddingValues(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(R.dimen.email_list_only_horizontal_padding)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.email_list_item_vertical_spacing)
            )
        ) {
            items(emails, key = { email -> email.id }) { email ->
                ReplyEmailListItem(
                    email = email,
                    selected = replyUiState.currentSelectedEmail.id == email.id,
                    onCardClick = {
                        onEmailCardPressed(email)
                    },
                )
            }
        }
        val activity = LocalContext.current as Activity
        ReplyDetailsScreen(
            replyUiState = replyUiState,
            modifier = Modifier
                .weight(1f)
                .padding(end = dimensionResource(R.dimen.email_list_only_horizontal_padding)),
            onBackPressed = { activity.finish() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyEmailListItem(
    email: Email,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.email_list_item_inner_padding))
        ) {
            ReplyEmailItemHeader(
                email,
                Modifier.fillMaxWidth()
            )

        }
    }
}

@Composable
private fun ReplyEmailItemHeader(email: Email, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        ReplyProfileImage(
            drawableResource = email.sender.avatar,
            description = stringResource(email.sender.firstName) + " "
                    + stringResource(email.sender.lastName),
            modifier = Modifier.size(dimensionResource(R.dimen.email_header_profile_size))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(R.dimen.email_header_content_padding_horizontal),
                    vertical = dimensionResource(R.dimen.email_header_content_padding_vertical)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(email.sender.firstName),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = stringResource(email.createdAt),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun ReplyProfileImage(
    @DrawableRes drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(drawableResource),
            contentDescription = description,
        )
    }
}

@Composable
fun ReplyLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = "MyGallery",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
private fun ReplyHomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ReplyLogo(
            modifier = Modifier
                .size(dimensionResource(R.dimen.topbar_logo_size))
                .padding(start = dimensionResource(R.dimen.topbar_logo_padding_start))
        )
        ReplyProfileImage(
            drawableResource = LocalAccountsDataProvider.defaultAccount.avatar,
            description = stringResource(R.string.profile),
            modifier = Modifier
                .padding(end = dimensionResource(R.dimen.topbar_profile_image_padding_end))
                .size(dimensionResource(R.dimen.topbar_profile_image_size))
        )
    }
}
