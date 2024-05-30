package com.conkers.futbolitoo.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conkers.futbolito.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JuegoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Futbolito") })
        }
    ) {
        JuegoBodyContent()
    }
}

@Composable
fun rememberAccelerometerSensorValueAsState(): State<Triple<Float, Float, Float>> {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val sensorValue = remember { mutableStateOf(Triple(0f, 0f, 0f)) }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                sensorValue.value = Triple(event.values[0], event.values[1], event.values[2])
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return sensorValue
}

fun isAccelerometerSensorAvailable(context: Context): Boolean {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null
}

@Composable
fun AccelerometerDemo() {
    var topScore by remember { mutableStateOf(0) }
    var bottomScore by remember { mutableStateOf(0) }

    val context = LocalContext.current

    if (isAccelerometerSensorAvailable(context)) {
        val sensorValue by rememberAccelerometerSensorValueAsState()
        val (x, y, z) = sensorValue

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "",
                fontSize = 24.sp,
                modifier = Modifier.padding(0.dp, 32.dp, 16.dp, 16.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append("Equipo rojo: ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append(topScore.toString())
                    }
                    append(" Equipo Azul: ")
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append(bottomScore.toString())
                    }
                }
            )

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val width = constraints.maxWidth.toFloat()
                val height = constraints.maxHeight.toFloat()
                var center by remember { mutableStateOf(Offset(width / 2, height / 2)) }
                val orientation = LocalConfiguration.current.orientation
                val radius = with(LocalDensity.current) { 10.dp.toPx() }

                center = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val offset = Offset(
                        x = (center.x - x).coerceIn(radius, width - radius),
                        y = (center.y + y).coerceIn(radius, height - radius),
                    )
                    offset
                } else {
                    Offset(
                        x = (center.x + y).coerceIn(radius, width - radius),
                        y = (center.y + x).coerceIn(radius, height - radius),
                    )
                }

                val middleX = width / 2
                when {
                    (center.y - radius <= 0 && center.x >= middleX - radius && center.x <= middleX + radius) -> {
                        topScore += 1
                        if (topScore >= 10) {
                            topScore = 0
                            bottomScore = 0
                        }
                        center = Offset(width / 2, height / 2)
                    }
                    (center.y + radius >= height && center.x >= middleX - radius && center.x <= middleX + radius) -> {
                        bottomScore += 1
                        if (bottomScore >= 10) {
                            topScore = 0
                            bottomScore = 0
                        }
                        center = Offset(width / 2, height / 2)
                    }
                }

                val image = ImageBitmap.imageResource(id = R.drawable.cancha)

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawImage(
                        image = image,
                        dstSize = IntSize(width.toInt(), height.toInt())
                    )

                    drawCircle(
                        color = Color.White,
                        radius = radius,
                        center = center,
                    )
                }
            }
        }
    } else {
        Text(
            text = "Sensor de acelerómetro no disponible",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun JuegoBodyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AccelerometerDemo()
    }
}