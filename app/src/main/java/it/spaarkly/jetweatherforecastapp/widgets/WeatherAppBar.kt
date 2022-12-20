package it.spaarkly.jetweatherforecastapp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import it.spaarkly.jetweatherforecastapp.R
import it.spaarkly.jetweatherforecastapp.model.Favourite
import it.spaarkly.jetweatherforecastapp.navigation.WeatherScreens
import it.spaarkly.jetweatherforecastapp.screens.favourite.FavouriteViewModel

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClick: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {

    val showDialog = remember {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClick() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Icon",
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked()
                    })
            }

            if(isMainScreen) {

                Icon(imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "",
                modifier = Modifier.scale(0.9f)
                    .clickable {
                        val splittedTitle = title.split(",")
                        val fav = Favourite(city = splittedTitle[0], country = splittedTitle[1])
                        favouriteViewModel.insertFavourite(favourite = fav)
                },
                tint = Color.Red.copy(alpha = 0.6f))
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = elevation
    )
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded = remember {
        mutableStateOf(true)
    }
    val items = listOf("About", "Favourites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp),
    ) {
        DropdownMenu(
            expanded = expanded.value, onDismissRequest = {
                hideDialog(expanded = expanded, showDialog = showDialog)
            },
            modifier = Modifier
                .width(140.dp)
                .background(MaterialTheme.colors.background)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    hideDialog(expanded = expanded, showDialog = showDialog)
                    navController.navigate(
                        when (text) {
                            "About" -> WeatherScreens.AboutScreen.name
                            "Favourites" -> WeatherScreens.FavouriteScreen.name
                            else -> WeatherScreens.SettingsScreen.name
                        }
                    )
                }) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favourites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings

                        }, contentDescription = "",
                        tint = Color.LightGray
                    )
                    Text(text, fontWeight = FontWeight.W300)
                }
            }
        }
    }
}

private fun hideDialog(
    expanded: MutableState<Boolean>,
    showDialog: MutableState<Boolean>
) {
    expanded.value = false
    showDialog.value = false
}