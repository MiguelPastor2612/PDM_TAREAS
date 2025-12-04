package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PedidoDataSource {

    private static final String TAG = "PedidoDataSource";
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_CLIENT_NAME,
            DatabaseHelper.COLUMN_DISH_PIZZA,
            DatabaseHelper.COLUMN_DISH_PASTA,
            DatabaseHelper.COLUMN_DISH_SALAD,
            DatabaseHelper.COLUMN_DRINK,
            DatabaseHelper.COLUMN_EMAIL,
            DatabaseHelper.COLUMN_ADDRESS,
            DatabaseHelper.COLUMN_NEWSLETTER,
            DatabaseHelper.COLUMN_ORDER_DATE
    };

    public PedidoDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Guarda un nuevo pedido en la base de datos.
     * @param pedido El objeto Pedido a guardar.
     * @return El ID de la fila insertada, o -1 si ocurrió un error.
     */
    public long guardarPedido(Pedido pedido) {
        ContentValues values = new ContentValues();

        // 1. Datos del Pedido
        values.put(DatabaseHelper.COLUMN_CLIENT_NAME, pedido.getNombreCliente());
        values.put(DatabaseHelper.COLUMN_DISH_PIZZA, pedido.isPizza() ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_DISH_PASTA, pedido.isPasta() ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_DISH_SALAD, pedido.isEnsalada() ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_DRINK, pedido.getBebida());

        // 2. Datos de Contacto
        values.put(DatabaseHelper.COLUMN_EMAIL, pedido.getEmail());
        values.put(DatabaseHelper.COLUMN_ADDRESS, pedido.getDireccion());
        values.put(DatabaseHelper.COLUMN_NEWSLETTER, pedido.isNewsletter() ? 1 : 0);

        // 3. Fecha del Pedido (Formato ISO 8601)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String fechaActual = dateFormat.format(new Date());
        values.put(DatabaseHelper.COLUMN_ORDER_DATE, fechaActual);
        pedido.setFechaPedido(fechaActual); // Actualizar el objeto con la fecha

        // Insertar en la base de datos
        long insertId = database.insert(DatabaseHelper.TABLE_ORDERS, null, values);

        if (insertId != -1) {
            pedido.setId(insertId);
            Log.i(TAG, "Pedido guardado con éxito. ID: " + insertId);
        } else {
            Log.e(TAG, "Error al guardar el pedido en la base de datos.");
        }

        return insertId;
    }

    // Nota: Se podrían añadir métodos para obtener todos los pedidos, eliminar, etc.
    // Por ahora, solo implementamos la función de guardar.
}