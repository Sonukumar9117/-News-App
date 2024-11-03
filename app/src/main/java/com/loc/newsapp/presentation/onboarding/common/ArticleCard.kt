package com.loc.newsapp.presentation.onboarding.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.model.Source
import com.loc.newsapp.presentation.onboarding.Dimens.ArticleCardSize
import com.loc.newsapp.presentation.onboarding.Dimens.ExtraSmallPadding
import com.loc.newsapp.presentation.onboarding.Dimens.ExtraSmallPadding2
import com.loc.newsapp.presentation.onboarding.Dimens.SmallIconSize
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 2.dp, horizontal = 2.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(1.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            error = painterResource(id = R.drawable.ic_time),
            placeholder = painterResource(id = R.drawable.ic_time)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(ArticleCardSize)
                .weight(1f)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.text_title)
                )

                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )

                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                Text(
                    text = article.publishedAt,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.text_title)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    NewsAppTheme {
        ArticleCard(
            article = Article(
                author = "Author Name",
                content = "Content of the article",
                description = "Description",
                publishedAt = "2023-11-03",
                source = Source(id = "", name = "News Source"),
                title = "Sample Title of the News Article for Preview",
                url = "",
                urlToImage = ""
            ),
            onClick = {}
        )
    }
}
