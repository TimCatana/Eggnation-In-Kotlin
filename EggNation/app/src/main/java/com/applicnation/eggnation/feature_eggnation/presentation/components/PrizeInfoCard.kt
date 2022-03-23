package com.applicnation.eggnation.feature_eggnation.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.Card
import com.applicnation.eggnation.ui.theme.SpaceSmall

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
    val image = getPrizeImage(prizeType)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = SpaceSmall)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "prize Image",
                    modifier = Modifier.fillMaxHeight(0.3f)
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
            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onDismissCard
                ) {
                    Text(text = "Dismiss")
                }
            }
        }
    }
}