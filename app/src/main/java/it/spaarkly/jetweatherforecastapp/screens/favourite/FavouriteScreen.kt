package it.spaarkly.jetweatherforecastapp.screens.favourite

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.spaarkly.jetweatherforecastapp.model.Favourite
import it.spaarkly.jetweatherforecastapp.widgets.WeatherAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteScreen(
    navController: NavHostController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Favourite Cities",
            isMainScreen = false,
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {
        it
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val list = favouriteViewModel.favList.collectAsState().value
                LazyColumn {
                    items(items = list) { fav: Favourite ->
                        key(fav.city) {
                            //  **IMPORTANT! Declare dismiss state here to assign individual items a Dismiss State!
                            val dismissState = rememberDismissState(initialValue = DismissValue.Default)

                            SwipeToDismiss(
                                state = dismissState,
                                background = {
                                    SwipeToDismissBackground(dismissState)
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            ) {
                                CityRow(
                                    fav,
                                    navController = navController,
                                    favouriteViewModel = favouriteViewModel,
                                    dismissState = dismissState,
                                )
                            }
                        }

                    }
                }

            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun SwipeToDismissBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> MaterialTheme.colors.background
            else -> Color.Red
        }
    )
    val alignment = Alignment.CenterEnd
    val icon = Icons.Default.Delete

    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = "Delete Icon",
            modifier = Modifier.scale(scale)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CityRow(
    favourite: Favourite,
    navController: NavHostController,
    favouriteViewModel: FavouriteViewModel,
    dismissState: DismissState,
) {
    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        favouriteViewModel.deleteFavourite(favourite)
        Toast.makeText(
            LocalContext.current,
            "Favourite City: ${favourite.city} has been deleted",
            Toast.LENGTH_SHORT
        ).show()
    }

    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {

            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color = Color(0xffB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(favourite.city, modifier = Modifier
                .padding(horizontal = 4.dp)
                .width(200.dp),
                textAlign = TextAlign.Start, overflow = TextOverflow.Ellipsis)

            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape, color = Color(0xffd1e3e1)
            ) {
                Text(favourite.country, modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)
            }

            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete",
                tint = Color.Red.copy(0.3f), modifier = Modifier.clickable {
                    favouriteViewModel.deleteFavourite(favourite)
                })
        }
    }
}
