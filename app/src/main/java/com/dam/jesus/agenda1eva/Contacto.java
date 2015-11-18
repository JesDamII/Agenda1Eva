package com.dam.jesus.agenda1eva;

import android.os.Parcel;
import android.os.Parcelable;

public class Contacto implements Parcelable {
	private int id;
	private String nombre;
	private String apellido;
	private String via;
	private String direccion;
	private String localidad;
	private String correo;
	
	public static final Creator<Contacto> CREATOR =
			new Creator<Contacto>() {

				@Override
				public Contacto createFromParcel(Parcel source) {
					return new Contacto(source);
				}

				@Override
				public Contacto[] newArray(int size) {
					return new Contacto[size];
				}
			};
	
	public Contacto(){
		id=0;
		nombre="";
		apellido="";
		via="";
		direccion="";
		localidad="";
		correo="";
	}

	public Contacto(int id,String nombre, String apellido, String via, String direccion,String localidad, String correo) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.via=via;
		this.direccion = direccion;
		this.localidad=localidad;
		this.correo=correo;
	}
	

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocalidad() {
		return localidad;
	}

	public String getCorreo() {
		return correo;
	}

	public Contacto(Parcel parcel){
		readFromParcel(parcel);
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeInt(id);
		out.writeString(nombre);
		out.writeString(apellido);
		out.writeString(via);
		out.writeString(direccion);
		out.writeString(localidad);
		out.writeString(correo);
		
	}
	
	public void readFromParcel(Parcel in){
		id=in.readInt();
		nombre=in.readString();
		apellido=in.readString();
		via=in.readString();
		direccion=in.readString();
		localidad=in.readString();
		correo=in.readString();
	}

}
