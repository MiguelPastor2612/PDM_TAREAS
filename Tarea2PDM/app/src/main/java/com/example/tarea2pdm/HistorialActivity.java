package com.example.tarea2pdm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class HistorialActivity extends Activity {

    private DatabaseHelper dbHelper;
    private TextView textViewHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        dbHelper = new DatabaseHelper(this);
        textViewHistorial = findViewById(R.id.textViewHistorial);

        mostrarHistorial();
    }

    private void mostrarHistorial() {
        List<String> pedidos = dbHelper.obtenerTodosPedidos();

        if (pedidos.isEmpty()) {
            textViewHistorial.setText("No hay pedidos registrados.");
            Toast.makeText(this, "No hay pedidos en el historial", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder historial = new StringBuilder();
            historial.append("=== HISTORIAL DE PEDIDOS ===\n\n");

            for (String pedido : pedidos) {
                historial.append(pedido).append("\n");
            }

            textViewHistorial.setText(historial.toString());
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}