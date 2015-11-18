package com.dam.jesus.agenda1eva;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminBD extends SQLiteOpenHelper {

    public AdminBD(Context context, String name, CursorFactory factory,
                   int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contactos(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, apellido text, via text, direccion text, localidad text,correo text)");
        db.execSQL("create table telefonos(id INTEGER, telefono text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("drop table if exist contactos");
        db.execSQL("drop table if exist telefonos");
        db.execSQL("create table contactos(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, apellido text, via text, direccion text, localidad text,correo text)");
        db.execSQL("create table telefonos(id INTEGER, telefono text)");

    }

}
