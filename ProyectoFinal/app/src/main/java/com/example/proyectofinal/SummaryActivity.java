package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class SummaryActivity extends AppCompatActivity {

    // Constantes para las claves de los Intents (Extras)
    public static final String EXTRA_NOMBRE_CLIENTE = "com.example.proyectofinal.NOMBRE_CLIENTE";
    public static final String EXTRA_DISH_PIZZA = "com.example.proyectofinal.DISH_PIZZA";
    public static final String EXTRA_DISH_PASTA = "com.example.proyectofinal.DISH_PASTA";
    public static final String EXTRA_DISH_SALAD = "com.example.proyectofinal.DISH_SALAD";
    public static final String EXTRA_DRINK = "com.example.proyectofinal.DRINK";
    public static final String EXTRA_EMAIL = "com.example.proyectofinal.EMAIL";
    public static final String EXTRA_DIRECCION = "com.example.proyectofinal.DIRECCION";
    public static final String EXTRA_NEWSLETTER = "com.example.proyectofinal.NEWSLETTER";

    // Clave antigua que ya no se usa, pero se mantiene por si acaso
    // public static final String EXTRA_PEDIDO_RESUMEN = "com.example.tarea2pdm.PEDIDO_RESUMEN";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Obtener el Intent que inició esta actividad
        Intent intent = getIntent();
        final String nombreCliente = intent.getStringExtra(EXTRA_NOMBRE_CLIENTE);
        final boolean pizza = intent.getBooleanExtra(EXTRA_DISH_PIZZA, false);
        final boolean pasta = intent.getBooleanExtra(EXTRA_DISH_PASTA, false);
        final boolean ensalada = intent.getBooleanExtra(EXTRA_DISH_SALAD, false);
        final String bebida = intent.getStringExtra(EXTRA_DRINK);
        final String email = intent.getStringExtra(EXTRA_EMAIL);
        final String direccion = intent.getStringExtra(EXTRA_DIRECCION);
        final boolean newsletter = intent.getBooleanExtra(EXTRA_NEWSLETTER, false);

        // Reconstruir el resumen del pedido a partir de los datos individuales
        StringBuilder resumenPedido = new StringBuilder("Platos: ");
        if (pizza) resumenPedido.append("Pizza, ");
        if (pasta) resumenPedido.append("Pasta, ");
        if (ensalada) resumenPedido.append("Ensalada, ");
        if (resumenPedido.toString().endsWith(", ")) {
            resumenPedido.setLength(resumenPedido.length() - 2); // Eliminar la última coma y espacio
        } else {
            resumenPedido.append("Ninguno");
        }
        resumenPedido.append(". Bebida: ").append(bebida != null ? bebida : "Ninguna");

        // Referencias a los TextViews
        TextView textViewResumenPedido = findViewById(R.id.textViewResumenPedido);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewDireccion = findViewById(R.id.textViewDireccion);
        TextView textViewNewsletter = findViewById(R.id.textViewNewsletter);

        // Mostrar la información
        if (textViewResumenPedido != null) {
            textViewResumenPedido.setText("Cliente: " + nombreCliente + "\n" + resumenPedido.toString());
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

        // 3. Lógica de Guardado en Base de Datos
        final Pedido pedidoFinal = new Pedido();
        pedidoFinal.setNombreCliente(nombreCliente);
        pedidoFinal.setPizza(pizza);
        pedidoFinal.setPasta(pasta);
        pedidoFinal.setEnsalada(ensalada);
        pedidoFinal.setBebida(bebida);
        pedidoFinal.setEmail(email);
        pedidoFinal.setDireccion(direccion);
        pedidoFinal.setNewsletter(newsletter);

        // Configurar el botón de Finalizar
        Button buttonFinalizar = findViewById(R.id.buttonFinalizar);
        if (buttonFinalizar != null) {
            buttonFinalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Guardar el pedido en la base de datos
                    PedidoDataSource dataSource = new PedidoDataSource(SummaryActivity.this);
                    try {
                        dataSource.open();
                        long id = dataSource.guardarPedido(pedidoFinal);
                        dataSource.close();

                        if (id != -1) {
                            Toast.makeText(SummaryActivity.this, "Pedido #" + id + " guardado con éxito.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SummaryActivity.this, "Error al guardar el pedido.", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("SummaryActivity", "Error al acceder a la base de datos: " + e.getMessage());
                        Toast.makeText(SummaryActivity.this, "Error de DB: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    // Finalizar todas las actividades y volver a la pantalla de inicio (o simplemente cerrar)
                    finish();
                }
            });
        }
    }
}
