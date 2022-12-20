package it.spaarkly.jetweatherforecastapp.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import it.spaarkly.jetweatherforecastapp.R
import it.spaarkly.jetweatherforecastapp.model.WeatherItem
import it.spaarkly.jetweatherforecastapp.util.formatDate
import it.spaarkly.jetweatherforecastapp.util.formatDateTime
import it.spaarkly.jetweatherforecastapp.util.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather.first().icon}.png"
    Surface(modifier = Modifier
        .padding(3.dp)
        .padding(horizontal = 8.dp)
        .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp),),
        color = Color.White) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                formatDate(weather.dt).split(",").first(),
                modifier = Modifier.padding(start = 5.dp), color = MaterialTheme.colors.background)

            WeatherStateImage(imageUrl = imageUrl)

            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xffffc400)
            ) {
                Text(weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)
            }

            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold),) {
                    append(formatDecimals(weather.temp.max) + "°")
                }

                withStyle(
                    SpanStyle(color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold),) {
                    append(formatDecimals(weather.temp.min) + "°")
                }

            })

        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.wind),
        contentDescription = stringResource(R.string.app_name),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .clip(CircleShape)
            .size(80.dp),
        filterQuality = FilterQuality.High,

        )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    val speed = if(isImperial) {
        "mph"
    } else {
        "m/s"
    }
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp))
            Text("${weather.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp))
            Text("${weather.pressure} psi", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "Wind icon",
                modifier = Modifier.size(20.dp))
            Text("${weather.speed}$speed",
                style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun SunRiseSunSet(weather : WeatherItem) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {


        Row {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(formatDateTime(weather.sunrise))
        }

        Row {
            Text(formatDateTime(weather.sunset))
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(30.dp)
            )
        }

    }
}
