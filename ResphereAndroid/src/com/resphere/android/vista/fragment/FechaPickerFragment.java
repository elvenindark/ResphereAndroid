package com.resphere.android.vista.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.resphere.android.vista.R;

public class FechaPickerFragment extends DialogFragment {

	private Button guardar;
	private Button cancelar;
	private DatePicker fecha;
	private TimePicker tiempo;
	private Activity a = null;
	private EditFechaListener fl;
	
	private int dia;
	private int mes;
	private int anio;
	private int hora;
	private int minuto;
	private String tag = null;
	
	public FechaPickerFragment(){
		
	}
	
	/*
	 * Este constructor debe enviar como tag, el nombre de la fragment que lanza este fragment
	 */
	public FechaPickerFragment(String tag){
		//this.a = activity;
		this.tag = tag;
	}
	
	public interface EditFechaListener{
		public void onFinishFechaDialog(String fecha, String hora);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		View view = inflater.inflate(R.layout.fecha, container);
		fecha = (DatePicker)view.findViewById(R.id.datePickerDialog);
		tiempo = (TimePicker)view.findViewById(R.id.timePickerDialog);
				
		getDialog().setTitle("Fecha");
		guardar = (Button)view.findViewById(R.id.btnGuardarFecha);
		cancelar = (Button)view.findViewById(R.id.btnCancelarFecha);
		
		if(tag==null)		
			guardar.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					EditFechaListener activity = (EditFechaListener)getActivity();
					anio = fecha.getYear();
					mes = fecha.getMonth()+1;
					dia = fecha.getDayOfMonth();
					hora = tiempo.getCurrentHour();
					minuto = tiempo.getCurrentMinute();				
					
					activity.onFinishFechaDialog(anio+"/"+mes+"/"+dia,hora+":"+minuto+":00");
					//activity.onFinishFechaDialog("fecha");
					getDialog().dismiss();
				}			
			});
		else{
			guardar.setOnClickListener(new OnClickListener(){
				
				@Override
				public void onClick(View v) {
					anio = fecha.getYear();
					mes = fecha.getMonth()+1;
					dia = fecha.getDayOfMonth();
					hora = tiempo.getCurrentHour();
					minuto = tiempo.getCurrentMinute();	
					fl = (EditFechaListener)getFragmentManager().findFragmentByTag(tag);					
					fl.onFinishFechaDialog(anio+"/"+mes+"/"+dia,hora+":"+minuto+":00");					
					getDialog().dismiss();
					
				}			
			});
		}
		
		cancelar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDialog().dismiss();
			}
			
		});
		
		return view;
	}

	/*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);        
        try {
            fl = (EditFechaListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement EditFechaListener");
        }
    }
	*/
}
