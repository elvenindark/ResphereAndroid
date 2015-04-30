package com.resphere.android.vista.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resphere.android.modelo.Accion;
import com.resphere.android.vista.R;

public class CustomListAccion extends ArrayAdapter<Accion>{
	
	private ArrayList<Accion> list;
	private Context activity;
	
	public CustomListAccion(Context context, int textViewResourceId, 
			ArrayList<Accion> list){
		super(context, textViewResourceId, list);
		this.list = list;
		this.activity = context;
	}
	
	private static class ViewHolder{
		TextView fecha;
		TextView descripcion;
		TextView organizacion;
		TextView hogares;
		TextView personas;		
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;       
        ViewHolder holder;       
        LayoutInflater vi = LayoutInflater.from(activity);
        v = vi.inflate(R.layout.accionitem, null, true);        	
        //Log.d("customlist", "before layout inflater" + v.toString());
        holder = new ViewHolder();
        holder.fecha = (TextView)v.findViewById(R.id.textFechaAccion);
        holder.descripcion = (TextView)v.findViewById(R.id.textDescripcionAccion);
        holder.organizacion = (TextView)v.findViewById(R.id.textOrganizacionAccion);
        holder.hogares = (TextView)v.findViewById(R.id.textHogarAccion);
        holder.personas = (TextView)v.findViewById(R.id.textPersonaAccion);
        v.setTag(holder);
        
        Accion item = list.get(position);
        holder.fecha.setText(item.getFecha());
        holder.descripcion.setText(item.getDescripcion());
        holder.organizacion.setText(item.getOrganizacion());
        holder.hogares.setText(item.getHogares());
        holder.personas.setText(item.getPersonas());
        
        return v;
	}
	
	
}
