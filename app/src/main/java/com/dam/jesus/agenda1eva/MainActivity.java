package com.dam.jesus.agenda1eva;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ArrayList<Contacto> contactos = new ArrayList<>();
    private ListView lvContacto;
    private int requestCode1=1;
    private EditText et1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarPersonas();
        AdaptadorContactos adaptador = new AdaptadorContactos(this,contactos);
        lvContacto = (ListView) findViewById(R.id.listViewContactos);
        lvContacto.setAdapter(adaptador);
        lvContacto.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                lanzarActividad(arg2);
            }



        });
        et1=(EditText)findViewById(R.id.et1);
    }
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        contactos.clear();
        cargarPersonas();
        AdaptadorContactos adaptador = new AdaptadorContactos(this,contactos);
        lvContacto = (ListView) findViewById(R.id.listViewContactos);
        lvContacto.setAdapter(adaptador);
        lvContacto.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                lanzarActividad(arg2);
            }



        });
        et1=(EditText)findViewById(R.id.et1);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemalta:
                darAlta();
                break;
            case R.id.itemacercade:
                Toast.makeText(this, "Realizado por Jesús Pinero Gómez", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemsalir:
                System.exit(0);

        }
        return true;
    }

    private void cargarPersonas(){
        String nombre,apellido,via,direccion,localidad,correo;
        int id;
        DataBase admin=new DataBase(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getReadableDatabase();
        Cursor fila = bd.rawQuery("select * from contactos order by upper(nombre)", null);
        if(fila.moveToFirst()){
            do{
                id=fila.getInt(0);
                nombre=fila.getString(1);
                apellido=fila.getString(2);
                via=fila.getString(3);
                direccion=fila.getString(4);
                localidad=fila.getString(5);
                correo=fila.getString(6);
                contactos.add(new Contacto(id,nombre,apellido,via,direccion,localidad,correo));

            }while(fila.moveToNext());

        }
        bd.close();
    }

    private void lanzarActividad(int index) {
        Intent i = new Intent(this,MostrarActividad.class);
        i.putExtra("contacto", contactos.get(index));
        startActivity(i);
    }
    private void darAlta(){
        Intent i=new Intent(this,AltaContacto.class);
        startActivityForResult(i, requestCode1);
    }
    public void buscar(View view){
        String nombre,apellido,via,direccion,localidad,correo;
        int id;
        DataBase admin=new DataBase(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getReadableDatabase();
        String nombreB=et1.getText().toString();
        if(nombreB.length()>0){
            contactos.clear();
            Cursor fila = bd.rawQuery("select * from contactos where nombre=?", new String[]{nombreB});
            if(fila.moveToFirst()){
                do{
                    id=fila.getInt(0);
                    nombre=fila.getString(1);
                    apellido=fila.getString(2);
                    via=fila.getString(3);
                    direccion=fila.getString(4);
                    localidad=fila.getString(5);
                    correo=fila.getString(6);
                    contactos.add(new Contacto(id,nombre,apellido,via,direccion,localidad,correo));

                }while(fila.moveToNext());

            }
            bd.close();
            AdaptadorContactos adaptador = new AdaptadorContactos(this,contactos);
            lvContacto = (ListView) findViewById(R.id.listViewContactos);
            lvContacto.setAdapter(adaptador);
            lvContacto.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    lanzarActividad(arg2);
                }



            });
            et1=(EditText)findViewById(R.id.et1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void exit(View view){
        System.exit(0);
    }
    public void Alta(View view){
        Intent i=new Intent(this,AltaContacto.class);
        startActivityForResult(i, requestCode1);
    }
}