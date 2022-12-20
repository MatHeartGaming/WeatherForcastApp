package it.spaarkly.jetweatherforecastapp.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.spaarkly.jetweatherforecastapp.navigation.WeatherScreens
import it.spaarkly.jetweatherforecastapp.widgets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,
            icon = Icons.Default.ArrowBack,
            title = "Search",
            isMainScreen = false,
            onButtonClicked = {
                navController.popBackStack()
            })
    }) { it ->
        it
        Surface() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)) {searchText ->
                    navController.navigate(WeatherScreens.MainScreen.name + "/$searchText") {
                        popUpTo(WeatherScreens.SplashScreen.name) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit = {}) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.isNotBlank()
    }
    Column(modifier = modifier) {
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Search",
            onAction = KeyboardActions {
                if(!valid) return@KeyboardActions
                onSearch(searchQueryState.value)
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = placeholder)},
        singleLine = true,
        maxLines = 1,
        enabled = true,
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.onBackground,
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)

    )

}
