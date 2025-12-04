package com.example.proyectofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes de la Base de Datos
    private static final String DATABASE_NAME = "RestaurantOrders.db";
    private static final int DATABASE_VERSION = 1;

    // Constantes de la Tabla de Pedidos
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CLIENT_NAME = "client_name";
    public static final String COLUMN_DISH_PIZZA = "dish_pizza";
    public static final String COLUMN_DISH_PASTA = "dish_pasta";
    public static final String COLUMN_DISH_SALAD = "dish_salad";
    public static final String COLUMN_DRINK = "drink";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_NEWSLETTER = "newsletter";
    public static final String COLUMN_ORDER_DATE = "order_date";

    // Sentencia SQL para crear la tabla
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ORDERS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_CLIENT_NAME + " text not null, "
            + COLUMN_DISH_PIZZA + " integer not null, " // 0=false, 1=true
            + COLUMN_DISH_PASTA + " integer not null, " // 0=false, 1=true
            + COLUMN_DISH_SALAD + " integer not null, " // 0=false, 1=true
            + COLUMN_DRINK + " text, "
            + COLUMN_EMAIL + " text, "
            + COLUMN_ADDRESS + " text, "
            + COLUMN_NEWSLETTER + " integer, " // 0=false, 1=true
            + COLUMN_ORDER_DATE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En una aplicación real, aquí se manejaría la migración de datos.
        // Para este ejemplo, simplemente eliminamos y recreamos la tabla.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }
}
