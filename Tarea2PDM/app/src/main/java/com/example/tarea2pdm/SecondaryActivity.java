// SecondaryActivity.java
package com.example.tarea2pdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondaryActivity extends AppCompatActivity {  // Cambiar a AppCompatActivity

    public static final String EXTRA_PEDIDO_RESUMEN = "com.example.tarea2pdm.PEDIDO_RESUMEN";
    public static final String EXTRA_NOMBRE_CLIENTE = "com.example.tarea2pdm.NOMBRE_CLIENTE";

    private String resumenPedido;
    private String nombreCliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        // Recibir datos
        Intent intent = getIntent();
        nombreCliente = intent.getStringExtra(EXTRA_NOMBRE_CLIENTE);
        resumenPedido = intent.getStringExtra(EXTRA_PEDIDO_RESUMEN);

        setupGuardarListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);  // Mismo menú
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cancelar) {
            Toast.makeText(this, "Pedido cancelado desde Secondary", Toast.LENGTH_SHORT).show();
            finish();  // Regresar a MainActivity
            return true;
        } else if (id == R.id.action_ver_historial) {
            Intent intent = new Intent(SecondaryActivity.this, HistorialActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupGuardarListener() {
        Button buttonGuardar = findViewById(R.id.buttonGuardar);
        if (buttonGuardar != null) {
            buttonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

                    Intent summaryIntent = new Intent(SecondaryActivity.this, SummaryActivity.class);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_NOMBRE_CLIENTE, nombreCliente);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_PEDIDO_RESUMEN, resumenPedido);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_EMAIL, email);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_DIRECCION, direccion);
                    summaryIntent.putExtra(SummaryActivity.EXTRA_NEWSLETTER, newsletter);

                    startActivity(summaryIntent);
                }
            });
        }
    }
}