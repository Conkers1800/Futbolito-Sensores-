package com.conkers.futbolito.futbolitoo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class ViewModelTest:ViewModel(){
    var x by mutableStateOf(0f)
    var y by mutableStateOf(0f)
    var z by mutableStateOf(0f)
    var xAcelerometro by mutableStateOf(0f)
    var yAcelerometro by mutableStateOf(0f)
    var zAcelerometro by mutableStateOf(0f)
    var posicion by mutableStateOf(Offset(0f,0f))
    var mueve by mutableStateOf(true)
    var maxheigth by mutableStateOf(0f)
    var maxwidth by mutableStateOf(0f)
    var estadoBalon by mutableStateOf("Zona de cancha")
    var colorEstado by mutableStateOf(Color(0,0,0))

    fun actualizarX(float: Float){
        x = float
    }
    fun actualizarY(float: Float){
        y = float
    }
    fun actualizarZ(float: Float){
        z = float
    }

    fun actualizarxAcelerometro(float: Float){
        xAcelerometro = float
    }
    fun actualizaryAcelerometro(float: Float){
        yAcelerometro = float
    }
    fun actualizarzAcelerometro(float: Float){
        zAcelerometro = float
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ViewModelTest()
            }
        }
    }
}