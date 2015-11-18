package com.dam.jesus.agenda1eva;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Alta extends AppCompatActivity {

    private EditText et1,et2,et3,et4,et5,et6;
    private Spinner sp;
    private String nombre,apellido,telefono,via,direccion,localidad,correo;
    private int id;
    private String[]vias={"Calle","Avenida","Plaza"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dar_alta);
        et1=(EditText)findViewById(R.id.etda1);
        et2=(EditText)findViewById(R.id.etda2);
        et3=(EditText)findViewById(R.id.etda3);
        et4=(EditText)findViewById(R.id.etda4);
        et5=(EditText)findViewById(R.id.etda5);
        et6=(EditText)findViewById(R.id.etda6);
        sp=(Spinner)findViewById(R.id.sp1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,vias);
        sp.setAdapter(adapter);
    }

    public void alta(View view){

        Intent i=new Intent();
        boolean salir=false;
        if(et1.getText().length()>0 && et3.getText().length()>0){

            AdminBD admin=new AdminBD(this, "administracion", null, 1);
            SQLiteDatabase bd=admin.getWritableDatabase();

            nombre=et1.getText().toString();
            apellido=et2.getText().toString();
            telefono=et3.getText().toString();
            via=sp.getSelectedItem().toString();
            direccion=et4.getText().toString();
            localidad=et5.getText().toString();
            correo=et6.getText().toString();

            ContentValues registro=new ContentValues();
            registro.put("nombre",nombre );
            registro.put("apellido",apellido );
            registro.put("via",via );
            registro.put("direccion",direccion );
            registro.put("localidad",localidad );
            registro.put("correo",correo );
            int cant=(int) bd.insert("contactos", null, registro);
            Cursor fila = bd.rawQuery("SELECT last_insert_rowid()", null);
            if(fila.moveToFirst()){
                id=fila.getInt(0);

            }
            aniadirTel(id,telefono);
            bd.close();
            if(cant>0){
                Toast.makeText(this, R.string.ContIntro, Toast.LENGTH_SHORT).show();
                salir=true;
            }
            else{
                Toast.makeText(this, getString(R.string.Error), Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(this, getString(R.string.Exnointroducido), Toast.LENGTH_LONG).show();
        }
        if(salir){
            setResult(RESULT_OK,i);
            finish();
        }
    }

    private void aniadirTel(int id, String telefono) {
        AdminBD admin=new AdminBD(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("id",id );
        registro.put("telefono",telefono );
        bd.insert("telefonos", null, registro);
        bd.close();
    }

}
