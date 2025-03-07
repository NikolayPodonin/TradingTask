package com.podonin.quotes.presentation.ui

import androidx.annotation.ColorRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.Image
import coil3.SingletonImageLoader
import coil3.compose.AsyncImage
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.podonin.common_ui.compose.textSizeResource
import com.podonin.common_ui.compose.toDp
import com.podonin.quotes.presentation.ui.model.QuoteItem
import com.podonin.quotes_impl.R
import com.podonin.common_ui.R as CommonUiR

private const val BACKGROUND_CHANGE_DURATION = 300
private const val BACKGROUND_CHANGE_DELAY = 100

@Composable
fun ListStateScreen(
    quotes: List<QuoteItem>,
    modifier: Modifier = Modifier
) {

    val bigPadding = dimensionResource(CommonUiR.dimen.material_margin_big)
    val statusBarPaddings = WindowInsets.statusBars.asPaddingValues()
    val navBarPaddings = WindowInsets.navigationBars.asPaddingValues()
    LazyColumn(
        contentPadding = PaddingValues(
            top = statusBarPaddings.calculateTopPadding() + bigPadding,
            bottom = navBarPaddings.calculateBottomPadding() + bigPadding
        ),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            quotes,
            key = { it.ticker },
        ) { quoteItem ->
            ItemQuote(
                quoteItem,
                isLast = quotes.last() == quoteItem
            )
        }
    }
}

@Composable
fun ItemQuote(
    quote: QuoteItem,
    isLast: Boolean = false
) {
    val bigPadding = dimensionResource(CommonUiR.dimen.material_margin_big)
    val mediumPadding = dimensionResource(CommonUiR.dimen.material_margin_medium)
    val smallPadding = dimensionResource(CommonUiR.dimen.material_margin_small)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (header, exchange, percent, price, arrow, divider) = createRefs()
        var textHeight by remember { mutableIntStateOf(0) }
        val isExchangeEmpty by rememberUpdatedState(quote.exchangeAndName.isBlank())
        Row(
            modifier = Modifier
                .padding(start = bigPadding, top = mediumPadding)
                .constrainAs(header) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    if (!isExchangeEmpty) {
                        bottom.linkTo(exchange.top)
                    } else {
                        bottom.linkTo(parent.bottom)
                    }
                }
        ) {
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
                    .fillMaxWidth()
                    .padding(start = smallPadding),
                onTextLayout = { textHeight = it.size.height }
            )
        }

        if (!isExchangeEmpty) {
            Text(
                text = quote.exchangeAndName,
                color = Color.Gray,
                fontSize = textSizeResource(R.dimen.text_size_small),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = smallPadding, bottom = mediumPadding, start = bigPadding)
                    .constrainAs(exchange) {
                        top.linkTo(price.top)
                        bottom.linkTo(price.bottom)
                        start.linkTo(header.start)
                        end.linkTo(price.start)
                        width = Dimension.fillToConstraints
                    }
            )
        }
        AnimatedText(
            isPositive = quote.isPositive,
            actualPrice = quote.actualPrice,
            percent = quote.percent,
            modifier = Modifier
                .padding(top = mediumPadding)
                .constrainAs(percent) {
                    top.linkTo(parent.top)
                    end.linkTo(arrow.start)
                }
        )
        Text(
            text = quote.priceAndChange,
            fontSize = textSizeResource(R.dimen.text_size_normal),
            modifier = Modifier
                .padding(top = smallPadding, bottom = mediumPadding)
                .constrainAs(price) {
                    top.linkTo(percent.bottom)
                    end.linkTo(arrow.start)
                }
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward),
            tint = Color.Gray,
            contentDescription = null,
            modifier = Modifier
                .height(dimensionResource(R.dimen.arrow_size))
                .padding(start = mediumPadding, end = bigPadding)
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )
        if (!isLast) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = bigPadding + smallPadding)
                    .constrainAs(divider) {
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Composable
private fun AnimatedText(
    isPositive: Boolean,
    actualPrice: Double,
    percent: String,
    modifier: Modifier = Modifier
) {
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
    val actualTextColor = if (isPositive) {
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

    LaunchedEffect(actualPrice) {
        val actualPrice = actualPrice
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
        modifier = modifier
            .wrapContentSize()
            .background(
                color = animatedBackgroundColor,
                shape = RoundedCornerShape(dimensionResource(CommonUiR.dimen.material_margin_medium))
            )
            .padding(dimensionResource(CommonUiR.dimen.material_margin_small))
    ) {
        Text(
            text = percent,
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
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val context = LocalContext.current
    val imageLoader by remember { mutableStateOf(SingletonImageLoader.get(context)) }
    val isCached by remember {
        mutableStateOf(
            imageLoader.memoryCache?.get(
                MemoryCache.Key(imageUrl)
            )?.image.isCorrect()
        )
    }
    var isLoaded by remember { mutableStateOf(isCached) }

    LaunchedEffect(imageUrl) {
        val request = ImageRequest.Builder(context)
            .memoryCacheKey(imageUrl)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .data(imageUrl)
            .build()

        val result = imageLoader.execute(request)
        isLoaded = result.image.isCorrect()
    }

    AnimatedVisibility(
        isLoaded,
        enter = expandHorizontally()
    ) {
        AsyncImage(
            model = imageUrl,
            imageLoader = imageLoader,
            contentDescription = contentDescription,
            modifier = modifier.size(parentHeight.toDp())
        )
    }

}

private fun Image?.isCorrect() = this != null && width > 1 && height > 1

private enum class ActualPriceChanged(@ColorRes val backgroundColor: Int) {
    None(android.R.color.transparent),
    Negative(R.color.red),
    Positive(R.color.green)
}