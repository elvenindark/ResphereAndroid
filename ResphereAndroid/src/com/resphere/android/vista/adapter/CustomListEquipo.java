package com.resphere.android.vista.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resphere.android.modelo.Equipo;
import com.resphere.android.vista.R;

public class CustomListEquipo extends ArrayAdapter<Equipo>{

	private ArrayList<Equipo> list;
	private Context activity;
	
	public CustomListEquipo(Context context, int textViewResourceId, 
			ArrayList<Equipo> list){
		super(context, textViewResourceId, list);
		this.list = list;
		this.activity = context;
	}
	
	private static class ViewHolder{
		TextView nombre;
		TextView tipo;
		TextView organizacion;
		TextView telefono;
		TextView email;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;       
        ViewHolder holder;       
        LayoutInflater vi = LayoutInflater.from(activity);
        v = vi.inflate(R.layout.equipoitem, null, true);
        holder = new ViewHolder();
        holder.nombre = (TextView)v.findViewById(R.id.textNombreEq);
        holder.tipo = (TextView)v.findViewById(R.id.textTipoEq);
        holder.organizacion = (TextView)v.findViewById(R.id.textOrganizacionEq);
        holder.telefono = (TextView)v.findViewById(R.id.textTelefonoEq);
        holder.email = (TextView)v.findViewById(R.id.textEmailEq);
        v.setTag(holder);
        
        Equipo item = list.get(position);
        holder.nombre.setText(item.getNombre());
        holder.telefono.setText(item.getTelefono());
        holder.organizacion.setText(item.getOrganizacion());
        holder.tipo.setText(item.getIdtipopoblacion());
        holder.email.setText(item.getEmail());
        
        return v;
	}
}
