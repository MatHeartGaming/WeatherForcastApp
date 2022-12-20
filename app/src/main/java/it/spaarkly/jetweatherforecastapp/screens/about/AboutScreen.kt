package it.spaarkly.jetweatherforecastapp.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import it.spaarkly.jetweatherforecastapp.R
import it.spaarkly.jetweatherforecastapp.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,
            title = "About",
        isMainScreen = false,
        icon = Icons.Default.ArrowBack,
        onButtonClicked = {
            navController.popBackStack()
        })
    }) {it
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.about_app),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold)

                Text(text = stringResource(R.string.developed_by),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold)

                Text(text = stringResource(id = R.string.api_used),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Light)


            }
        }
    }
}