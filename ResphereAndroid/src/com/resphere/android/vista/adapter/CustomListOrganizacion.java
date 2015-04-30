package com.resphere.android.vista.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resphere.android.modelo.Organizacion;
import com.resphere.android.vista.R;

public class CustomListOrganizacion extends ArrayAdapter<Organizacion>{
	
	private ArrayList<Organizacion> list; 
	private Context activity;
	public CustomListOrganizacion(Context context, int textViewResourceId, 
			ArrayList<Organizacion> list){
		super(context, textViewResourceId, list);
		this.list = list;
		this.activity = context;
	}
	
	private static class ViewHolder{
		TextView organizacion;
		TextView sector;
		TextView contacto;		
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;       
        ViewHolder holder;       
        LayoutInflater vi = LayoutInflater.from(activity);
        v = vi.inflate(R.layout.organizacionitem, null, true);        	
        //Log.d("customlist", "before layout inflater" + v.toString());
        holder = new ViewHolder();
        holder.contacto = (TextView)v.findViewById(R.id.textContacto);
        holder.organizacion = (TextView)v.findViewById(R.id.textOrganizacion);
        holder.sector = (TextView)v.findViewById(R.id.textSector);
        v.setTag(holder); 
        Organizacion item = list.get(position);
        holder.contacto.setText(item.getContacto());
        holder.organizacion.setText(item.getNombre());
        holder.sector.setText(item.getSector());
        
        return v;
	}
}
