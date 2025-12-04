package com.example.tarea2pdm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurante.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de pedidos
    public static final String TABLE_PEDIDOS = "pedidos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PEDIDO = "pedido";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DIRECCION = "direccion";
    public static final String COLUMN_NEWSLETTER = "newsletter";
    public static final String COLUMN_FECHA = "fecha";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PEDIDOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_PEDIDO + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_DIRECCION + " TEXT, " +
                    COLUMN_NEWSLETTER + " INTEGER, " +
                    COLUMN_FECHA + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDOS);
        onCreate(db);
    }

    // Método para insertar un pedido
    public long insertarPedido(String nombre, String pedido, String email,
                               String direccion, boolean newsletter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_PEDIDO, pedido);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_DIRECCION, direccion);
        values.put(COLUMN_NEWSLETTER, newsletter ? 1 : 0);

        long id = db.insert(TABLE_PEDIDOS, null, values);
        db.close();
        return id;
    }

    // Método para obtener todos los pedidos
    public List<String> obtenerTodosPedidos() {
        List<String> pedidos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PEDIDOS + " ORDER BY " + COLUMN_FECHA + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String pedido = "ID: " + cursor.getInt(0) +
                        "\nCliente: " + cursor.getString(1) +
                        "\nPedido: " + cursor.getString(2) +
                        "\nEmail: " + cursor.getString(3) +
                        "\nDirección: " + cursor.getString(4) +
                        "\nNewsletter: " + (cursor.getInt(5) == 1 ? "Sí" : "No") +
                        "\nFecha: " + cursor.getString(6) +
                        "\n-------------------";
                pedidos.add(pedido);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pedidos;
    }

    // Método para verificar si la tabla tiene datos
    public boolean tieneDatos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PEDIDOS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }
}