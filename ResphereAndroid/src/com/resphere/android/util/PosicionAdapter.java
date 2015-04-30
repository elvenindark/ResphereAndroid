package com.resphere.android.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resphere.android.vista.R;

public class PosicionAdapter extends ArrayAdapter<Posicion>{
	
	Context context;
	private int resource; 
	private TextView latitud;
	private TextView longitud;
	private TextView altitud;

	public PosicionAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		this.resource = resource;
		this.context = context;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Posicion posicion = new Posicion();
        posicion = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lista, null);         
            latitud = (TextView) convertView.findViewById(R.id.latitud);
            longitud = (TextView) convertView.findViewById(R.id.longitud);
            altitud = (TextView) convertView.findViewById(R.id.provincia);            
        } else            
 
        latitud.setText(posicion.getLatitud());
        longitud.setText(posicion.getLongitud());
        altitud.setText(posicion.getAltitud());
 
        return convertView;
       
    }

}
