package com.example.tarea2pdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondaryActivity extends Activity {

    // Constantes para las claves de los Intents (Extras)
    public static final String EXTRA_PEDIDO_RESUMEN = "com.example.tarea2pdm.PEDIDO_RESUMEN";
    public static final String EXTRA_NOMBRE_CLIENTE = "com.example.tarea2pdm.NOMBRE_CLIENTE";

    // Variables para almacenar los datos recibidos de MainActivity
    private String resumenPedido;
    private String nombreCliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        // 1. Lógica de Recepción de Datos (Extras) de MainActivity
        Intent intent = getIntent();
        nombreCliente = intent.getStringExtra(EXTRA_NOMBRE_CLIENTE);
        resumenPedido = intent.getStringExtra(EXTRA_PEDIDO_RESUMEN);

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
                    summaryIntent.putExtra(SummaryActivity.EXTRA_PEDIDO_RESUMEN, resumenPedido);

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