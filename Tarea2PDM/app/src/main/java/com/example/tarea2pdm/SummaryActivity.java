package com.example.tarea2pdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends Activity {

    // Constantes para las claves de los Intents (Extras)
    public static final String EXTRA_NOMBRE_CLIENTE = "com.example.tarea2pdm.NOMBRE_CLIENTE";
    public static final String EXTRA_PEDIDO_RESUMEN = "com.example.tarea2pdm.PEDIDO_RESUMEN";
    public static final String EXTRA_EMAIL = "com.example.tarea2pdm.EMAIL";
    public static final String EXTRA_DIRECCION = "com.example.tarea2pdm.DIRECCION";
    public static final String EXTRA_NEWSLETTER = "com.example.tarea2pdm.NEWSLETTER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Obtener el Intent que inició esta actividad
        Intent intent = getIntent();
        String nombreCliente = intent.getStringExtra(EXTRA_NOMBRE_CLIENTE);
        String resumenPedido = intent.getStringExtra(EXTRA_PEDIDO_RESUMEN);
        String email = intent.getStringExtra(EXTRA_EMAIL);
        String direccion = intent.getStringExtra(EXTRA_DIRECCION);
        boolean newsletter = intent.getBooleanExtra(EXTRA_NEWSLETTER, false);

        // Referencias a los TextViews
        TextView textViewResumenPedido = findViewById(R.id.textViewResumenPedido);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewDireccion = findViewById(R.id.textViewDireccion);
        TextView textViewNewsletter = findViewById(R.id.textViewNewsletter);

        // Mostrar la información
        if (textViewResumenPedido != null) {
            textViewResumenPedido.setText("Cliente: " + nombreCliente + "\n" + resumenPedido);
        }
        if (textViewEmail != null) {
            textViewEmail.setText("Email: " + email);
        }
        if (textViewDireccion != null) {
            textViewDireccion.setText("Dirección: " + direccion);
        }
        if (textViewNewsletter != null) {
            String suscripcion = newsletter ? "Sí, suscrito" : "No suscrito";
            textViewNewsletter.setText("Suscripción a Newsletter: " + suscripcion);
        }

        // Configurar el botón de Finalizar
        Button buttonFinalizar = findViewById(R.id.buttonFinalizar);
        if (buttonFinalizar != null) {
            buttonFinalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Finalizar todas las actividades y volver a la pantalla de inicio (o simplemente cerrar)
                    // Para simplificar, simplemente cerraremos esta actividad.
                    finish();
                }
            });
        }
    }
}