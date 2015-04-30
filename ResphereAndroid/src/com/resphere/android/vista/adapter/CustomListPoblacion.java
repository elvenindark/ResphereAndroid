package com.resphere.android.vista.adapter;

import java.util.ArrayList;

import com.resphere.android.modelo.TipoPoblacion;
import com.resphere.android.vista.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListPoblacion extends ArrayAdapter<TipoPoblacion> {

	private ArrayList<TipoPoblacion> list;
	private Context activity;
	public CustomListPoblacion(Context context, int textViewResourceId, 
			ArrayList<TipoPoblacion> list){
		super(context, textViewResourceId, list);
		this.list = list;
		this.activity = context;
		//Log.d("customlist", "creando el customlist" + list.toString());
	}
	
	private static class ViewHolder{
		TextView hombres;
		TextView mujeres;
		TextView ninos;
		TextView ninas;
		TextView personas;
		TextView hogares;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;       
        ViewHolder holder;       
        LayoutInflater vi = LayoutInflater.from(activity);
        v = vi.inflate(R.layout.poblacionitem, null, true);        	
        Log.d("customlist", "before layout inflater" + v.toString());
        holder = new ViewHolder();
        holder.hombres = (TextView) v.findViewById(R.id.txtHombres);
        holder.mujeres = (TextView) v.findViewById(R.id.txtMujeres);
        holder.ninos = (TextView) v.findViewById(R.id.txtNinos);
        holder.ninas = (TextView) v.findViewById(R.id.txtNinas);
        holder.personas = (TextView) v.findViewById(R.id.txtPersonas);
        holder.hogares = (TextView) v.findViewById(R.id.txtHogares);
        v.setTag(holder);   
        TipoPoblacion custom = list.get(position);    
        holder.hombres.setText(custom.getHombres());
        holder.mujeres.setText(custom.getMujeres());
        holder.ninos.setText(custom.getNinos());
        holder.ninas.setText(custom.getNinas());
        holder.personas.setText(custom.getPersonas());
        holder.hogares.setText(custom.getHogares());          

        return v;
    }
}
