package com.example.tarea2pdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mostrar la interfaz de pedidos (activity_main.xml)
        setContentView(R.layout.activity_main);

        setupPedidoListener();
    }

    private void setupPedidoListener() {
        Button buttonEnviar = findViewById(R.id.buttonEnviar);
        if (buttonEnviar != null) {
            buttonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1. Recolectar datos del pedido
                    EditText editTextNombre = findViewById(R.id.editTextNombre);
                    String nombre = editTextNombre != null ? editTextNombre.getText().toString() : "";

                    if (nombre.trim().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Por favor, introduce tu nombre.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    StringBuilder resumenPedido = new StringBuilder();
                    resumenPedido.append("Platos: ");

                    // CheckBoxes
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

                    // RadioButtons (Bebida)
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

                    // 2. Crear Intent y añadir Extras (datos a enviar)
                    Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                    // Usamos las constantes de SecondaryActivity para las claves
                    intent.putExtra(SecondaryActivity.EXTRA_NOMBRE_CLIENTE, nombre);
                    intent.putExtra(SecondaryActivity.EXTRA_PEDIDO_RESUMEN, resumenPedido.toString());

                    // 3. Iniciar la segunda actividad (ya no esperamos resultado)
                    startActivity(intent);
                }
            });
        }
    }

    // El método onActivityResult ha sido eliminado ya que ya no se usa startActivityForResult.
}