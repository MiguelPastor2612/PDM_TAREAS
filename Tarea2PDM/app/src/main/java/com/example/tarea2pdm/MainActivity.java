package com.example.tarea2pdm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;  // Cambiar de Activity a AppCompatActivity

public class MainActivity extends AppCompatActivity {  // Cambiar aquí

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPedidoListener();
    }

    // 1. Crear el menú del Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // 2. Manejar clics en el menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cancelar) {
            Log.d("MENU_ACTION", "Cancelar Pedido");
            Toast.makeText(this, "Pedido cancelado", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_reenviar) {
            Log.d("MENU_ACTION", "Reenviar Pedido");
            Toast.makeText(this, "Pedido reenviado", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_modificar) {
            Log.d("MENU_ACTION", "Modificar Pedido");
            Toast.makeText(this, "Modificar pedido", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_ver_historial) {
            Log.d("MENU_ACTION", "Ver Historial de Pedidos");
            Toast.makeText(this, "Mostrando historial", Toast.LENGTH_SHORT).show();
            // Abrir actividad de historial
            Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupPedidoListener() {
        // Tu código existente...
        Button buttonEnviar = findViewById(R.id.buttonEnviar);
        if (buttonEnviar != null) {
            buttonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editTextNombre = findViewById(R.id.editTextNombre);
                    String nombre = editTextNombre != null ? editTextNombre.getText().toString() : "";

                    if (nombre.trim().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Por favor, introduce tu nombre.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    StringBuilder resumenPedido = new StringBuilder();
                    resumenPedido.append("Platos: ");

                    CheckBox checkBoxPizza = findViewById(R.id.checkBoxPizza);
                    if (checkBoxPizza != null && checkBoxPizza.isChecked()) {
                        resumenPedido.append("Pizza, ");
                    }
                    CheckBox checkBoxPasta = findViewById(R.id.checkBoxPasta);
                    if (checkBoxPasta != null && checkBoxPasta.isChecked()) {
                        resumenPedido.append("Pasta, ");
                    }
                    CheckBox checkBoxEnsalada = findViewById(R.id.checkBoxEnsalada);
                    if (checkBoxEnsalada != null && checkBoxEnsalada.isChecked()) {
                        resumenPedido.append("Ensalada, ");
                    }

                    RadioGroup radioGroupBebida = findViewById(R.id.radioGroupBebida);
                    if (radioGroupBebida != null) {
                        int selectedId = radioGroupBebida.getCheckedRadioButtonId();
                        if (selectedId != -1) {
                            RadioButton selectedRadioButton = findViewById(selectedId);
                            resumenPedido.append("Bebida: ").append(selectedRadioButton.getText().toString());
                        } else {
                            resumenPedido.append("Sin Bebida");
                        }
                    }

                    Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                    intent.putExtra(SecondaryActivity.EXTRA_NOMBRE_CLIENTE, nombre);
                    intent.putExtra(SecondaryActivity.EXTRA_PEDIDO_RESUMEN, resumenPedido.toString());
                    startActivity(intent);
                }
            });
        }
    }
}