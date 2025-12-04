package com.example.tarea2pdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE_CLIENTE = "com.example.tarea2pdm.NOMBRE_CLIENTE";
    public static final String EXTRA_PEDIDO_RESUMEN = "com.example.tarea2pdm.PEDIDO_RESUMEN";
    public static final String EXTRA_EMAIL = "com.example.tarea2pdm.EMAIL";
    public static final String EXTRA_DIRECCION = "com.example.tarea2pdm.DIRECCION";
    public static final String EXTRA_NEWSLETTER = "com.example.tarea2pdm.NEWSLETTER";

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);  // Mismo menú
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String nombreCliente = intent.getStringExtra(EXTRA_NOMBRE_CLIENTE);
        String resumenPedido = intent.getStringExtra(EXTRA_PEDIDO_RESUMEN);
        String email = intent.getStringExtra(EXTRA_EMAIL);
        String direccion = intent.getStringExtra(EXTRA_DIRECCION);
        boolean newsletter = intent.getBooleanExtra(EXTRA_NEWSLETTER, false);

        // GUARDAR EN LA BASE DE DATOS
        long id = dbHelper.insertarPedido(nombreCliente, resumenPedido, email, direccion, newsletter);

        if (id != -1) {
            Log.d("DATABASE", "Pedido guardado con ID: " + id);
            Toast.makeText(this, "Pedido guardado en la base de datos", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("DATABASE", "Error al guardar el pedido");
            Toast.makeText(this, "Error al guardar el pedido", Toast.LENGTH_SHORT).show();
        }

        // Mostrar información
        TextView textViewResumenPedido = findViewById(R.id.textViewResumenPedido);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewDireccion = findViewById(R.id.textViewDireccion);
        TextView textViewNewsletter = findViewById(R.id.textViewNewsletter);

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

        // Configurar botón de Finalizar
        Button buttonFinalizar = findViewById(R.id.buttonFinalizar);
        if (buttonFinalizar != null) {
            buttonFinalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}