package com.example.tarea2pdm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Variable para controlar qué layout se muestra
    private boolean isPedidosLayout = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Llamar al método para configurar el layout inicial
        setupLayout();
    }

    private void setupLayout() {
        if (isPedidosLayout) {
            // Mostrar la interfaz de pedidos (activity_main.xml)
            setContentView(R.layout.activity_main);

            // Configurar el botón de la interfaz de pedidos
            Button buttonEnviar = findViewById(R.id.buttonEnviar);
            if (buttonEnviar != null) {
                buttonEnviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Pedido Enviado. Cambiando a formulario de contacto...", Toast.LENGTH_LONG).show();
                        // Cambiar al otro layout
                        isPedidosLayout = false;
                        setupLayout();
                    }
                });
            }
        } else {
            // Mostrar la interfaz de contacto/compras (activity_secondary.xml)
            setContentView(R.layout.activity_secondary);

            // Configurar el botón de la interfaz de contacto
            Button buttonGuardar = findViewById(R.id.buttonGuardar);
            if (buttonGuardar != null) {
                buttonGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Información Guardada. Volviendo a pedidos...", Toast.LENGTH_LONG).show();
                        // Cambiar al otro layout
                        isPedidosLayout = true;
                        setupLayout();
                    }
                });
            }
        }
    }
}