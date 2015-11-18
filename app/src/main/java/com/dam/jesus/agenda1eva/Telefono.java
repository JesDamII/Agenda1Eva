package com.dam.jesus.agenda1eva;

import android.os.Parcel;
import android.os.Parcelable;


public class Telefono implements Parcelable {
	private int id;
	private String telefono;
	
	public static final Creator<Telefono> CREATOR =
			new Creator<Telefono>() {

				@Override
				public Telefono createFromParcel(Parcel source) {
					return new Telefono(source);
				}

				@Override
				public Telefono[] newArray(int size) {
					return new Telefono[size];
				}
			};
	
	public Telefono() {
		this.id=0;
		this.telefono = "";
	}
	
	public Telefono(int id, String telefono) {
		super();
		this.id = 0;
		this.telefono = telefono;
	}

	public Telefono(Parcel source) {
		// TODO Auto-generated constructor stub
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeInt(id);
		out.writeString(telefono);
		
	}
	
	public void readFromParcel(Parcel in){
		id=in.readInt();
		telefono=in.readString();
		
	}

}
