package it.spaarkly.jetweatherforecastapp.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.spaarkly.jetweatherforecastapp.widgets.WeatherAppBar

@Composable
fun SettingScreen(navController: NavHostController, settingsViewModel: SettingsViewModel = hiltViewModel()) {
    var unitToggleState by remember {
        mutableStateOf(false)
    }

    val measurementUnits = listOf("Imperial (F)", "Metric (C)")

    val choiceFromDb = settingsViewModel.unitList.collectAsState().value

    val defaultChoice = if(choiceFromDb.isEmpty()) measurementUnits[1] else choiceFromDb.first().unit


    var choiceState by remember(defaultChoice) {
        mutableStateOf(defaultChoice)
    }

    Scaffold(topBar = {
        WeatherAppBar(navController = navController, title = "Settings",
            icon = Icons.Default.ArrowBack,
        isMainScreen = false) {
            navController.popBackStack()
        }
    }) {paddingValues ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Change Units of Measurement", modifier = Modifier.padding(bottom = 15.dp))

                IconToggleButton(checked = !unitToggleState, onCheckedChange = {newVal ->
                    unitToggleState = !newVal
                    choiceState = if(unitToggleState) {
                        measurementUnits[0] // Switch to Imperial
                    } else {
                        measurementUnits[1] // Switch to Metric
                    }
                },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(choiceState)
                }
                Button(modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xffefbe42)
                    ),
                    onClick = {
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(it.spaarkly.jetweatherforecastapp.model.Unit(choiceState))
                    }) {
                    Text(modifier = Modifier.padding(4.dp), text = "Save",
                    color = Color.White, fontSize = 17.sp)
                }
            }
        }
    }
}
