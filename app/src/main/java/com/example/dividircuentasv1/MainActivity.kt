package com.example.dividircuentasv1

import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dividircuentasv1.ui.theme.DividirCuentasV1Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DividirCuentasV1Theme {
                var cantidad by remember { mutableStateOf("") }
                var comensales by remember { mutableStateOf("") }
                var sliderPosition by remember { mutableFloatStateOf(0f) }
                var resultado by remember { mutableStateOf("") }
                var total by remember { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .padding(16.dp)
                    )
                    {
                        TextField(
                            value = cantidad,
                            onValueChange = { cantidad = it },
                            label = { Text("Cantidad") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = comensales,
                            onValueChange = { comensales = it },
                            label = { Text("Comensales") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        var checked by remember { mutableStateOf(true) }
                        Text(
                            text = "Redondear propina "
                        )
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Valoracion "
                        )
                        if (!checked) {
                            Slider(
                                value = sliderPosition,
                                onValueChange = {},
                                enabled = false,
                                valueRange = 0f..5f,
                                steps = 4

                            )
                        }else{
                            Slider(
                                value = sliderPosition,
                                onValueChange = { sliderPosition = it },
                                valueRange = 0f..5f,
                                steps = 4
                            )
                        }

                        Text(text = sliderPosition.toInt().toString())

                        Spacer(modifier = Modifier.height(16.dp))

                        FilledTonalButton(onClick = {
                            resultado = dividirCuenta(
                                cantidad = cantidad,
                                comensales = comensales,
                                sliderPosition = sliderPosition,
                                propina = checked
                            )
                            total = totalCuenta(
                                cantidad = cantidad,
                                sliderPosition = sliderPosition,
                                propina = checked
                            )

                        }) {
                            Text("Calcular")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (resultado.isNotEmpty()) {
                            Text(
                                text = "Total: $total",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = "Cada uno: $resultado",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
        }
    }


    fun dividirCuenta(
        cantidad: String,
        comensales: String,
        sliderPosition: Float,
        propina: Boolean
    ): String {

        val total = cantidad.toFloatOrNull()
        val personas = comensales.toIntOrNull()

        if (total == null || personas == null || personas == 0) {
            return "Valores inválidos"
        }

        val porcentajePropina = sliderPosition * 0.05f
        val totalConPropina : Float = total + (total * porcentajePropina)
        val resultadoPorPersona : Float = totalConPropina / personas
        val resultadoPorPersonaSinPropina : Float = total / personas
        return if (propina) {
            "%.2f".format(resultadoPorPersona)
        } else {
            "%.2f".format(resultadoPorPersonaSinPropina)
        }
    }

    fun totalCuenta(
        cantidad: String,
        sliderPosition: Float,
        propina: Boolean
    ): String {

        val total = cantidad.toFloatOrNull()

        if (total == null ) {
            return "Valores inválidos"
        }

        val porcentajePropina : Float= sliderPosition * 0.05f
        val totalConPropina : Float = total + (total * porcentajePropina)

        return if (propina) {
            "%.2f".format(totalConPropina)
        } else {
            "%.2f".format(total)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        DividirCuentasV1Theme {
        }
    }
}