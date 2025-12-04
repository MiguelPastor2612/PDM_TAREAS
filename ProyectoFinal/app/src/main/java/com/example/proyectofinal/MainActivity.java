package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Configurar la Toolbar (Action Bar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Configurar el Navigation Drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        androidx.appcompat.app.ActionBarDrawerToggle toggle = new androidx.appcompat.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

                    // CheckBoxes (Platos)
                    CheckBox checkBoxPizza = findViewById(R.id.checkBoxPizza);
                    boolean pizza = checkBoxPizza != null && checkBoxPizza.isChecked();
                    CheckBox checkBoxPasta = findViewById(R.id.checkBoxPasta);
                    boolean pasta = checkBoxPasta != null && checkBoxPasta.isChecked();
                    CheckBox checkBoxEnsalada = findViewById(R.id.checkBoxEnsalada);
                    boolean ensalada = checkBoxEnsalada != null && checkBoxEnsalada.isChecked();

                    // RadioButtons (Bebida)
                    String bebida = "Sin Bebida";
                    RadioGroup radioGroupBebida = findViewById(R.id.radioGroupBebida);
                    if (radioGroupBebida != null) {
                        int selectedId = radioGroupBebida.getCheckedRadioButtonId();
                        if (selectedId != -1) {
                            RadioButton selectedRadioButton = findViewById(selectedId);
                            bebida = selectedRadioButton.getText().toString();
                        }
                    }

                    // 2. Crear Intent y añadir Extras (datos a enviar)
                    Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                    // Usamos las constantes de SecondaryActivity para las claves
                    intent.putExtra(SecondaryActivity.EXTRA_NOMBRE_CLIENTE, nombre);
                    intent.putExtra(SecondaryActivity.EXTRA_DISH_PIZZA, pizza);
                    intent.putExtra(SecondaryActivity.EXTRA_DISH_PASTA, pasta);
                    intent.putExtra(SecondaryActivity.EXTRA_DISH_SALAD, ensalada);
                    intent.putExtra(SecondaryActivity.EXTRA_DRINK, bebida);

                    // 3. Iniciar la segunda actividad
                    startActivity(intent);
                }
            });
        }
    }

    // El método onActivityResult ha sido eliminado ya que ya no se usa startActivityForResult.

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Lógica para el Action Bar Menu (tres puntos)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú; esto añade ítems a la Action Bar si está presente.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Configuración del Action Bar seleccionada", Toast.LENGTH_SHORT).show();
            // Aquí se podría iniciar la actividad de Configuración
            return true;
        }
        if (id == R.id.action_about) {
            Toast.makeText(this, "Acerca de seleccionado", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Lógica para el Navigation Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Ya estamos en MainActivity, no hacemos nada o cerramos el drawer
            Toast.makeText(this, "Inicio seleccionado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_view_orders) {
            startActivity(new Intent(this, ViewOrdersActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}