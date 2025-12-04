package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ViewOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        // Opcional: Habilitar el botón de retroceso en la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}