package com.conkers.futbolitoo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conkers.futbolito.R
import com.jesse.futbolitoo.navegacion.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InicioScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inicio") })
        }
    ) {
        InicioBodyContent(navController)
    }
}

@Composable
fun InicioBodyContent(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate(route = AppScreen.JuegoScreen.route)
                },
                modifier = Modifier.size(200.dp, 70.dp) // Tamaño del botón ajustado
            ) {
                Text(text = "Jugar", fontSize = 24.sp)
            }
        }
    }
}