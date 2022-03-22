package com.applicnation.eggnation.feature_eggnation.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.applicnation.eggnation.R

@Composable
fun PrizeInfoCard(
    prizeTitle: String,
    prizeDesc: String,
    prizeType: String,
    prizeClaimed: Boolean,
    onDismissCard: () -> Unit,
    showClaimButton: Boolean,
    onClaimButtonClick: () -> Unit = {},
    modifier: Modifier,
) {
    val image = when (prizeType) {
        "earbuds" -> { R.drawable.egg }
        "phone" -> { R.drawable.egg_four }
        "tablet" -> { R.drawable.egg_three }
        "laptop" -> { R.drawable.egg }
        "tee" -> { R.drawable.egg_two }
        "hoodie" -> {R.drawable.egg}
        "cash" -> { R.drawable.egg_four }
        else -> { R.drawable.egg_three }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "exit",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, top = 4.dp)
                .clickable { onDismissCard() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = image),
            contentDescription = "prize Image",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            style = MaterialTheme.typography.h3,
            text = prizeTitle
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (showClaimButton) {
            Button(
                enabled = !prizeClaimed,
                onClick = { onClaimButtonClick() })
            {
                Text(text = "Claim")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        LazyColumn {
            item {
                Text(
                    style = MaterialTheme.typography.body1,
                    text = prizeDesc
                )
            }
        }
    }
}