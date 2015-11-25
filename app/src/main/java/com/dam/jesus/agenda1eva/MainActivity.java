package com.dam.jesus.agenda1eva;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contacto> contactos = new ArrayList<>();
    private ListView ListVContactos;
    private int requestCode1=1;
    private EditText et1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarPersonas();
        AdaptadorContactos adaptador = new AdaptadorContactos(this,contactos);
        ListVContactos = (ListView) findViewById(R.id.listViewContactos);
        ListVContactos.setAdapter(adaptador);
        ListVContactos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                modificaActividad(arg2);
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
        ListVContactos = (ListView) findViewById(R.id.listViewContactos);
        ListVContactos.setAdapter(adaptador);
        ListVContactos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                modificaActividad(arg2);
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

    private void modificaActividad(int index) {
        Intent i = new Intent(this,actualizaContacto.class);
        i.putExtra("contacto", contactos.get(index));
        startActivity(i);
    }
    private void darAlta(){
        Intent i=new Intent(this,Alta.class);
        startActivityForResult(i, requestCode1);
    }
    public void buscar(View view){
        String nombre,apellido,via,direccion,localidad,correo;
        int id;
        DataBase admin=new DataBase(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getReadableDatabase();
        String nombreBusqueda=et1.getText().toString();
        if(nombreBusqueda.length()>0){
            contactos.clear();
            Cursor rs = bd.rawQuery("select * from contactos where nombre=?", new String[]{nombreBusqueda});
            if(rs.moveToFirst()){
                do{
                    id=rs.getInt(0);
                    nombre=rs.getString(1);
                    apellido=rs.getString(2);
                    via=rs.getString(3);
                    direccion=rs.getString(4);
                    localidad=rs.getString(5);
                    correo=rs.getString(6);
                    contactos.add(new Contacto(id,nombre,apellido,via,direccion,localidad,correo));

                }while(rs.moveToNext());

            }
            bd.close();
            AdaptadorContactos adaptador = new AdaptadorContactos(this,contactos);
            ListVContactos = (ListView) findViewById(R.id.listViewContactos);
            ListVContactos.setAdapter(adaptador);
            ListVContactos.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    modificaActividad(arg2);
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
}