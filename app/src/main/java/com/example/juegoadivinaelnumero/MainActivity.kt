package com.example.juegoadivinaelnumero

// Importaciones necesarias para el funcionamiento de la aplicación
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

// Clase principal que extiende AppCompatActivity para usar componentes de la interfaz de usuario de Android
class MainActivity : AppCompatActivity() {

    // Declaración de variables para almacenar el número secreto y la cantidad de intentos
    private var numeroSecreto = 0
    private var intentos = 0

    // Método que se ejecuta cuando la actividad se crea
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Configura el diseño desde el archivo XML

        // Obtiene referencias a los elementos del diseño utilizando sus IDs
        val txtNumero = findViewById<EditText>(R.id.txt_Numero) // Campo de texto para ingresar un número
        val btnAdivinar = findViewById<Button>(R.id.btn_Adivinar) // Botón para intentar adivinar
        val tvSuerte = findViewById<TextView>(R.id.tv_Suerte) // Texto para mostrar mensajes de retroalimentación

        // Si hay un estado guardado, restaura el número secreto y los intentos
        if (savedInstanceState != null) {
            numeroSecreto = savedInstanceState.getInt("numeroSecreto")
            intentos = savedInstanceState.getInt("intentos")
        } else {
            iniciarJuego() // Inicia un nuevo juego
        }

        // Define el comportamiento del botón cuando se hace clic
        btnAdivinar.setOnClickListener {
            val input = txtNumero.text.toString() // Obtiene el texto ingresado

            // Verifica si el campo está vacío
            if (input.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un número.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Detiene la ejecución si no hay entrada
            }

            // Convierte el texto ingresado a un número entero y verifica si es válido
            val numeroIngresado = input.toIntOrNull()
            if (numeroIngresado == null || numeroIngresado !in 1..100) {
                Toast.makeText(this, "Ingresa un número válido entre 1 y 100.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Detiene la ejecución si el número no es válido
            }

            // Incrementa el contador de intentos
            intentos++

            // Compara el número ingresado con el número secreto
            when {
                numeroIngresado < numeroSecreto -> {
                    // Si el número ingresado es menor, muestra un mensaje indicando que es mayor
                    tvSuerte.text = "El número es mayor 😯. ¡Inténtalo de nuevo! 👻"
                }
                numeroIngresado > numeroSecreto -> {
                    // Si el número ingresado es mayor, muestra un mensaje indicando que es menor
                    tvSuerte.text = "El número es menor 👀. ¡Inténtalo de nuevo! 😉"
                }
                else -> {
                    // Si el número ingresado es correcto, felicita al usuario y reinicia el juego
                    tvSuerte.text = "¡Felicidades! 🥳🤩 Adivinaste en $intentos intentos.🍀"
                    Toast.makeText(this, "¡Nuevo juego iniciado!", Toast.LENGTH_SHORT).show()
                    iniciarJuego() // Reinicia el juego
                }
            }

            // Limpia el campo de texto para el próximo intento
            txtNumero.text.clear()
        }
    }

    // Método para inicializar un nuevo juego
    private fun iniciarJuego() {
        numeroSecreto = Random.nextInt(1, 101) // Genera un número aleatorio entre 1 y 100
        intentos = 0 // Reinicia el contador de intentos
    }

    // Método que guarda el estado de la actividad para restaurarlo si es necesario
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("numeroSecreto", numeroSecreto) // Guarda el número secreto
        outState.putInt("intentos", intentos) // Guarda la cantidad de intentos
    }
}
