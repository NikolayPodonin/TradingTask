package com.podonin.quotes.presentation


import androidx.annotation.ColorRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.SingletonImageLoader
import coil3.compose.AsyncImage
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import com.podonin.common_ui.compose.textSizeResource
import com.podonin.common_ui.compose.toDp
import com.podonin.quotes.presentation.model.QuoteItem
import com.podonin.quotes_impl.R
import com.podonin.common_ui.R as CommonUiR

private const val IMAGE_EXPAND_DURATION = 150
private const val BACKGROUND_CHANGE_DURATION = 300
private const val BACKGROUND_CHANGE_DELAY = 100

@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    viewModel: QuotesViewModel = hiltViewModel()
) {
    val quotes by viewModel.quotesFlow.collectAsState()

    val bigPadding = dimensionResource(CommonUiR.dimen.material_margin_big)
    val statusBarPaddings = WindowInsets.statusBars.asPaddingValues()
    val navBarPaddings = WindowInsets.navigationBars.asPaddingValues()
    LazyColumn(
        contentPadding = PaddingValues(
            top = statusBarPaddings.calculateTopPadding() + bigPadding,
            bottom = navBarPaddings.calculateBottomPadding() + bigPadding
        ),
        modifier = modifier
            .padding(
                horizontal = bigPadding
            )
    ) {
        items(
            quotes,
            key = { it.ticker },
        ) { quoteItem ->
            ItemQuote(quoteItem)
        }
    }
}

@Composable
fun ItemQuote(quote: QuoteItem) {
    val mediumPadding = dimensionResource(CommonUiR.dimen.material_margin_medium)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            var textHeight by remember { mutableIntStateOf(0) }
            Row {
                AnimatedImage(
                    imageUrl = quote.logoUrl,
                    parentHeight = textHeight,
                    contentDescription = quote.ticker
                )

                Text(
                    text = quote.ticker,
                    fontSize = textSizeResource(R.dimen.text_size_big),
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    onTextLayout = { textHeight = it.size.height }
                )
            }
            if (quote.exchangeAndName.isNotBlank()) {
                Text(
                    text = quote.exchangeAndName,
                    color = Color.Gray,
                    fontSize = textSizeResource(R.dimen.text_size_small),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.width(mediumPadding))
        Column(horizontalAlignment = Alignment.End) {
            AnimatedText(quote)
            Text(
                text = quote.priceAndChange,
                fontSize = textSizeResource(R.dimen.text_size_normal)
            )
        }
    }
}

@Composable
private fun AnimatedText(quote: QuoteItem) {
    var actualPriceBefore by remember {
        mutableDoubleStateOf(0.0)
    }

    var actualPriceChanged by remember { mutableStateOf(ActualPriceChanged.None) }
    val backgroundColor = colorResource(actualPriceChanged.backgroundColor)
    val animatedBackgroundColor by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(BACKGROUND_CHANGE_DURATION, BACKGROUND_CHANGE_DELAY),
        finishedListener = {
            actualPriceChanged = ActualPriceChanged.None
        }
    )
    val actualTextColor = if (quote.isPositive) {
        colorResource(R.color.light_green)
    } else {
        colorResource(R.color.light_red)
    }

    val targetTextColor = if (actualPriceChanged != ActualPriceChanged.None) {
        colorResource(R.color.white)
    } else {
        actualTextColor
    }
    val animatedTextColor by animateColorAsState(
        targetValue = targetTextColor,
        animationSpec = tween(BACKGROUND_CHANGE_DURATION, BACKGROUND_CHANGE_DELAY),
        finishedListener = {
            actualPriceChanged = ActualPriceChanged.None
        }
    )

    LaunchedEffect(quote.actualPrice) {
        val actualPrice = quote.actualPrice
        actualPriceChanged = when {
            actualPriceBefore == 0.0 -> {
                actualPriceBefore = actualPrice
                ActualPriceChanged.None
            }

            actualPrice > actualPriceBefore -> {
                ActualPriceChanged.Positive
            }

            actualPrice < actualPriceBefore -> {
                ActualPriceChanged.Negative
            }

            else -> {
                ActualPriceChanged.None
            }
        }

        val isChanged = actualPrice != actualPriceBefore
        if (isChanged) {
            actualPriceBefore = actualPrice
        }

    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = animatedBackgroundColor,
                shape = RoundedCornerShape(dimensionResource(CommonUiR.dimen.material_margin_medium))
            )
            .padding(dimensionResource(CommonUiR.dimen.material_margin_small))
    ) {
        Text(
            text = quote.percent,
            style = TextStyle(
                color = animatedTextColor,
                fontSize = textSizeResource(R.dimen.text_size_big)
            )
        )
    }
}

@Composable
private fun AnimatedImage(
    imageUrl: String,
    parentHeight: Int,
    contentDescription: String? = null,
) {
    val context = LocalContext.current
    val imageLoader = SingletonImageLoader.get(context)
    val isCached by remember {
        mutableStateOf(
            imageLoader.memoryCache?.get(MemoryCache.Key(imageUrl)) != null
        )
    }
    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(imageUrl) {
        val request = ImageRequest.Builder(context)
            .memoryCacheKey(imageUrl)
            .data(imageUrl)
            .build()

        val result = imageLoader.execute(request)
        isLoaded = result.image != null
    }

    val imageSize = parentHeight.toDp()
    val animatedWidth by animateDpAsState(
        targetValue = if (isLoaded) imageSize else 0.dp,
        animationSpec = tween(IMAGE_EXPAND_DURATION)
    )
    val width = if (isCached) imageSize else animatedWidth
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        modifier = Modifier
            .height(imageSize)
            .width(width),
        contentScale = ContentScale.Fit,
    )
    if (isLoaded || isCached) {
        val mediumPadding = dimensionResource(CommonUiR.dimen.material_margin_medium)
        Spacer(modifier = Modifier.width(mediumPadding))
    }
}

private enum class ActualPriceChanged(@ColorRes val backgroundColor: Int) {
    None(android.R.color.transparent),
    Negative(R.color.red),
    Positive(R.color.green)
}