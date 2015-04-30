package com.resphere.android.vista.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resphere.android.modelo.NRecuperacion;
import com.resphere.android.vista.R;

public class CustomListNRecuperacion extends ArrayAdapter<NRecuperacion> {

	private Context activity;
	private ArrayList<NRecuperacion> list;
	
	public CustomListNRecuperacion(Context context, int textViewResourceId, 
			ArrayList<NRecuperacion> list){
		super(context, textViewResourceId,list);
		this.activity = context;
		this.list = list;
	}
	
	private static class ViewHolder{
		TextView aplica;
		TextView observacion;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;       
        ViewHolder holder;       
        LayoutInflater vi = LayoutInflater.from(activity);
        v = vi.inflate(R.layout.nritem, null, true);        	
        //Log.d("customlist", "before layout inflater" + v.toString());
        holder = new ViewHolder();
        holder.aplica = (TextView)v.findViewById(R.id.txtAplicaNR);
        holder.observacion = (TextView)v.findViewById(R.id.txtObservacionesNR);
        v.setTag(holder);
        
        NRecuperacion item = list.get(position);
        holder.aplica.setText(item.getAplica());
        holder.observacion.setText(item.getObservacion());
        
        return v;
	}
}
