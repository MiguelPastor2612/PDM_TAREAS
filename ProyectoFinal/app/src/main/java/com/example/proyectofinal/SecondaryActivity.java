package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SecondaryActivity extends AppCompatActivity {

    // Constantes para las claves de los Intents (Extras)
    public static final String EXTRA_NOMBRE_CLIENTE = "com.example.proyectofinal.NOMBRE_CLIENTE";
    public static final String EXTRA_DISH_PIZZA = "com.example.proyectofinal.DISH_PIZZA";
    public static final String EXTRA_DISH_PASTA = "com.example.proyectofinal.DISH_PASTA";
    public static final String EXTRA_DISH_SALAD = "com.example.proyectofinal.DISH_SALAD";
    public static final String EXTRA_DRINK = "com.example.proyectofinal.DRINK";

    // Variables para almacenar los datos recibidos de MainActivity
    private String nombreCliente;
    private boolean pizza;
    private boolean pasta;
    private boolean ensalada;
    private String bebida;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        // 1. Lógica de Recepción de Datos (Extras) de MainActivity
        Intent intent = getIntent();
        nombreCliente = intent.getStringExtra(EXTRA_NOMBRE_CLIENTE);
        pizza = intent.getBooleanExtra(EXTRA_DISH_PIZZA, false);
        pasta = intent.getBooleanExtra(EXTRA_DISH_PASTA, false);
        ensalada = intent.getBooleanExtra(EXTRA_DISH_SALAD, false);
        bebida = intent.getStringExtra(EXTRA_DRINK);

        // 2. Configurar el botón para enviar los datos a SummaryActivity
        Button buttonGuardar = findViewById(R.id.buttonGuardar);
        if (buttonGuardar != null) {
            buttonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Recolectar datos de contacto
                    EditText editTextEmail = findViewById(R.id.editTextEmail);
                    EditText editTextDireccion = findViewById(R.id.editTextDireccion);
                    CheckBox checkBoxNewsletter = findViewById(R.id.checkBoxNewsletter);

                    String email = editTextEmail != null ? editTextEmail.getText().toString() : "";
                    String direccion = editTextDireccion != null ? editTextDireccion.getText().toString() : "";
                    boolean newsletter = checkBoxNewsletter != null && checkBoxNewsletter.isChecked();

                    if (email.trim().isEmpty() || direccion.trim().isEmpty()) {
                        Toast.makeText(SecondaryActivity.this, "Por favor, completa el email y la dirección.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Crear Intent para SummaryActivity y añadir TODOS los Extras
                    Intent summaryIntent = new Intent(SecondaryActivity.this, SummaryActivity.class);

                    // Datos del Pedido (recibidos de MainActivity)
                    summaryIntent.putExtra(SummaryActivity.EXTRA_NOMBRE_CLIENTE, nombreCliente);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_DISH_PIZZA, pizza);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_DISH_PASTA, pasta);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_DISH_SALAD, ensalada);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_DRINK, bebida);

                    // Datos de Contacto (recolectados en esta actividad)
                    summaryIntent.putExtra(SummaryActivity.EXTRA_EMAIL, email);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_DIRECCION, direccion);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_NEWSLETTER, newsletter);

                    // Iniciar la actividad de resumen
                    startActivity(summaryIntent);

                    // Opcional: Finalizar esta actividad para que el usuario no pueda volver atrás
                    // finish();
                }
            });
        }
    }
}