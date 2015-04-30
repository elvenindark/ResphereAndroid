package com.resphere.android.vista.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resphere.android.modelo.Salud;
import com.resphere.android.vista.R;

public class CustomListSalud extends ArrayAdapter<Salud>{

	private ArrayList<Salud> lista;
	private Context context;
	
	public CustomListSalud(Context context, int textViewResourceId, ArrayList<Salud> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.lista = objects;
		this.context = context;
	}
	
	private static class ViewHolder{
		TextView funciona;
		TextView porcentaje;		
		TextView observaciones;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;       
        ViewHolder holder;       
        LayoutInflater vi = LayoutInflater.from(context);
        v = vi.inflate(R.layout.saluditem, null, true);        
        holder = new ViewHolder();
        holder.funciona = (TextView) v.findViewById(R.id.txtFuncionaSalud);
        holder.porcentaje = (TextView) v.findViewById(R.id.txtPorcentajeSalud);
        holder.observaciones = (TextView) v.findViewById(R.id.txtObservacionesSalud);
        v.setTag(holder);
        Salud item = lista.get(position);
        holder.funciona.setText(item.getSifunciona());
        holder.porcentaje.setText(item.getPorcentaje());
        holder.observaciones.setText(item.getObservaciones());
        
        return v;
	}
	

}
