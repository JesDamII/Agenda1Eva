package com.dam.jesus.agenda1eva;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MostrarActividad extends Activity {
	private EditText et1,et2,et3,et4,et5,et6;
	private ListView lv1;
	private int id,pvia;
	private Spinner sp;
	private String nombreNew,via,apellido,telefono,direccion,localidad,correo,telefonoOld="";
	private ArrayList<String> telefonos = new ArrayList<String>();
	private String[]vias={"Calle","Avenida","Plaza"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificar);
		lv1=(ListView)findViewById(R.id.lvTel);
		et1 = (EditText) findViewById(R.id.etm1);
		et2 = (EditText) findViewById(R.id.etm2);
		et3 = (EditText) findViewById(R.id.etm3);
		et4 = (EditText) findViewById(R.id.etm4);
		et5 = (EditText) findViewById(R.id.etm5);
		et6 = (EditText) findViewById(R.id.etm6);
		Contacto contac=getIntent().getParcelableExtra("contacto");
		id=contac.getId();
		cargarTelefonos(id);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,telefonos);
		lv1.setAdapter(adapter);
		lv1.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				et3.setText(telefonos.get(arg2));
				telefonoOld=telefonos.get(arg2);
				
			}
			
			
        	
        }); 
		sp=(Spinner)findViewById(R.id.spm1);
		ArrayAdapter<String>adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,vias);
		sp.setAdapter(adapter2);
		via=contac.getVia();
		for(int i=0;i<vias.length;i++){
			if(via.equalsIgnoreCase(vias[i])){
				pvia=i;
			}
		}
		et1.setText(contac.getNombre());
		et2.setText(contac.getApellido());
		et4.setText(contac.getDireccion());
		et5.setText(contac.getLocalidad());
		et6.setText(contac.getCorreo());
		sp.setSelection(pvia);
		
	}
	
	private void cargarTelefonos(int id) {
		String tele;
    	DataBase admin=new DataBase(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getWritableDatabase();
    	Cursor fila = bd.rawQuery("select telefono from telefonos where id="+id+" order by telefono", null);
    	if(fila.moveToFirst()){
    		do{
	    		tele=fila.getString(0);
	    		telefonos.add(tele);
	    		
    		}while(fila.moveToNext());
    	
    	}
    	
    	bd.close();
		
	}
	public void addTel(View view){
		DataBase admin=new DataBase(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        telefono= et3.getText().toString();
        if(telefono.length()>0){
        	ContentValues registro=new ContentValues();
	        registro.put("id",id );
	        registro.put("telefono",telefono );
	        int cant = (int) bd.insert("telefonos", null, registro);
	        if(cant==1){
	        	Toast.makeText(this, getString(R.string.TelAni), Toast.LENGTH_LONG).show();
	    	}
        }
        bd.close();
        telefonos.clear();
        cargarTelefonos(id);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,telefonos);
		lv1.setAdapter(adapter);
	}
	public void addTele(){
		DataBase admin=new DataBase(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        telefono= et3.getText().toString();
        ContentValues registro=new ContentValues();
        registro.put("id",id );
        registro.put("telefono",telefono );
        bd.insert("telefonos", null, registro);
        bd.close();
	}

	public void volver(View view){
		finish();
	}
	
	public void modificar(View view){
		Intent i=new Intent();	
		if(et1.getText().length()>0){
	        DataBase admin=new DataBase(this, "administracion", null, 1);
	        SQLiteDatabase bd=admin.getWritableDatabase();
	    	nombreNew = et1.getText().toString();
	    	apellido= et2.getText().toString();
	    	telefono= et3.getText().toString();
	    	via=sp.getSelectedItem().toString();
	    	direccion = et4.getText().toString();
	    	localidad= et5.getText().toString();
	    	correo= et6.getText().toString();
	    	ContentValues registro=new ContentValues();
	        registro.put("nombre",nombreNew );
	        registro.put("apellido",apellido );
	        registro.put("via",via ); 
	        registro.put("direccion",direccion );  
	        registro.put("localidad",localidad );  
	        registro.put("correo",correo );  
	        int cant = bd.update("contactos", registro, "id="+id, null);
	        if(telefono.length()>0 && telefonoOld.length()>0){
	        	ContentValues registro2=new ContentValues();
	        	registro2.put("id", id);
		        registro2.put("telefono",telefono );
	        	bd.update("telefonos", registro2, "telefono="+telefonoOld, null);
	        }
	        if(telefono.length()>0 && telefonoOld.length()==0){
	        	addTele();
	        }
	    	bd.close();
	    	et1.setText("");
			et2.setText("");
			et3.setText("");
			et4.setText("");
			et5.setText("");
			et6.setText("");
	    	if(cant==1){
	    		Toast.makeText(this, getString(R.string.ContMod), Toast.LENGTH_LONG).show();
	    	}
	    	else{
	    		Toast.makeText(this, getString(R.string.Error), Toast.LENGTH_LONG).show();
	    	}
    	}
		setResult(RESULT_OK,i);
		finish();
	}
	 public void baja(View view){

	    	AlertDialog.Builder dialogo = new AlertDialog.Builder(this);  
	        dialogo.setTitle(R.string.Advertencia);  
	        dialogo.setMessage(R.string.Eliminar);            
	        dialogo.setCancelable(false);  
	     
	        dialogo.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {  
	         
	        	public void onClick(DialogInterface dialogo1, int id) {  
	                aceptarCon();
	            }  
	        });  
	        dialogo.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialogo1, int id) {  
	                cancelar();
	            }  
	        });            
	        dialogo.show();  
	 }
	 public void aceptarCon(){
			Intent i=new Intent();	
			DataBase admin=new DataBase(this, "administracion", null, 1);
	        SQLiteDatabase bd=admin.getWritableDatabase();
	    	int cant = bd.delete("contactos", "id="+id, null);
	    	bd.delete("telefonos", "id="+id, null);
	    	bd.close();
	    	if(cant==1){
	        	Toast.makeText(this, getString(R.string.ContBaj), Toast.LENGTH_LONG).show();
	    	}
	    	setResult(RESULT_OK,i);
			finish();
		   }
		 
	 public void delTel(View view){
		 if(telefonoOld.length()>0 && telefonoOld.equals(et3.getText().toString())){
		 AlertDialog.Builder dialogo = new AlertDialog.Builder(this);  
	        dialogo.setTitle(R.string.Advertencia);  
	        dialogo.setMessage(R.string.Eliminar);            
	        dialogo.setCancelable(false);  
	     
	        dialogo.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {  
	         
	        	public void onClick(DialogInterface dialogo1, int id) {  
	                aceptarTel();
	            }  
	        });  
	        dialogo.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialogo1, int id) {  
	                cancelar();
	            }  
	        });            
	        dialogo.show();  
		 }
	 }
	 public void aceptarTel(){
		 DataBase admin=new DataBase(this, "administracion", null, 1);
	        SQLiteDatabase bd=admin.getWritableDatabase();
	        telefono= et3.getText().toString();
	        int cant= bd.delete("telefonos", "telefono="+telefonoOld+" and id="+id, null);
	        if(cant==1){
	        	Toast.makeText(this, getString(R.string.TelBaj), Toast.LENGTH_LONG).show();
	    	}
	        bd.close();
	        telefonos.clear();
	        cargarTelefonos(id);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,telefonos);
			lv1.setAdapter(adapter);
	 }
	 public void cancelar() {
	    	
	    }
	 
}