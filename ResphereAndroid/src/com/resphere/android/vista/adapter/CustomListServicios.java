package com.resphere.android.vista.adapter;

import java.util.ArrayList;

import com.resphere.android.modelo.Servicio;
import com.resphere.android.vista.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListServicios extends ArrayAdapter<Servicio>{

	private ArrayList<Servicio> list;
	private Context activity;
	
	public CustomListServicios(Context context, int textViewResourceId, ArrayList<Servicio> list){
		super(context, textViewResourceId, list);
		this.list = list;
		this.activity = context;
	}
	
	private static class ViewHolder{
		TextView aplica;
		TextView funciona;
		TextView tipodano;	
		TextView observacion;		
	}
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater vi = LayoutInflater.from(activity);
        v = vi.inflate(R.layout.servicioitem, null, true);
        
        ViewHolder holder = new ViewHolder();
        holder.aplica = (TextView)v.findViewById(R.id.txtAplicaServ);
        holder.funciona = (TextView)v.findViewById(R.id.txtFuncionaServ);
        holder.tipodano = (TextView)v.findViewById(R.id.txtTipoDanoServ);
        holder.observacion = (TextView)v.findViewById(R.id.txtObservacionServ);
        v.setTag(holder);
        Servicio custom = list.get(position);
        holder.aplica.setText(custom.getSiaplica());
        holder.funciona.setText(custom.getSifunciona());
        holder.observacion.setText(custom.getObservacion());
        holder.tipodano.setText(custom.getIdtipodano());
        return v;
	}
}
