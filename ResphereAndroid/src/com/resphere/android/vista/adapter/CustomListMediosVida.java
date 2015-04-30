package com.resphere.android.vista.adapter;

import java.util.ArrayList;
import java.util.List;

import com.resphere.android.modelo.MedioVida;
import com.resphere.android.vista.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListMediosVida  extends ArrayAdapter<MedioVida>{

	private ArrayList<MedioVida> list;
	private Context activity;

	public CustomListMediosVida(Context context, int textViewResourceId, 
			ArrayList<MedioVida> list) {
		super(context, textViewResourceId, list);
		// TODO Auto-generated constructor stub		
		this.list = list;
		this.activity = context;
	}

	private static class ViewHolder{
		TextView hombres;
		TextView mujeres;
		TextView tipodano;
		TextView aplica;
		TextView observaciones;		
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;       
        ViewHolder holder;       
        LayoutInflater vi = LayoutInflater.from(activity);
        v = vi.inflate(R.layout.mediovidaitem, null, true);        	
        //Log.d("customlist", "before layout inflater" + v.toString());
        holder = new ViewHolder();
        holder.hombres = (TextView) v.findViewById(R.id.txtHombresMV);
        holder.mujeres = (TextView) v.findViewById(R.id.txtMujeresMV);
        holder.tipodano = (TextView) v.findViewById(R.id.txtDanoMV);
        holder.aplica = (TextView) v.findViewById(R.id.txtAplicaMV);
        holder.observaciones = (TextView) v.findViewById(R.id.txtObservacionMV);        
        v.setTag(holder);   
        MedioVida custom = list.get(position);    
        holder.hombres.setText(custom.getHombres());
        holder.mujeres.setText(custom.getMujeres());
        holder.tipodano.setText(custom.getIdtipodano());
        holder.aplica.setText(custom.getSiaplica());
        holder.observaciones.setText(custom.getObservacion());        

        return v;
    }
}
