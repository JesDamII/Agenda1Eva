package com.dam.jesus.agenda1eva;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdaptadorContactos extends BaseAdapter {
	private ArrayList<Contacto> contactos;
	private Context contexto;
	
	public AdaptadorContactos(Context context,ArrayList<Contacto> contactos){
		this.contexto=context;
		this.contactos=contactos;
	}

	@Override
	public int getCount() {
		return contactos.size();
	}

	@Override
	public Object getItem(int index) {
		return contactos.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater infla = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = infla.inflate(R.layout.item_contactos, null);
		}
		
		TextView nom = (TextView) item.findViewById(R.id.tvItemCon1);
		nom.setText(contactos.get(index).getNombre());
		return item;
	}

}
