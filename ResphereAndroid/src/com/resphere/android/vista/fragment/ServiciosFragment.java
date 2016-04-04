package com.resphere.android.vista.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.resphere.android.modelo.Servicio;
import com.resphere.android.vista.R;

public class ServiciosFragment extends DialogFragment{
	
	
	public interface ServiciosListener{		
		public void onFinishServicios(Servicio servicio, int position);
	}

	private int position;
	private Button guardar;
	private Button cancelar;
	private EditText observacion;
	private RadioGroup tipodanos;
	private CheckBox aplica;
	private CheckBox funciona;
	private RadioButton tipo;
	
	public ServiciosFragment(int position){
		this.position = position;
	}

	public void getDatosUI(View view){
		guardar = (Button)view.findViewById(R.id.btnGuardarServiciosF);
		cancelar = (Button)view.findViewById(R.id.btnCancelarServiciosF);
		observacion = (EditText)view.findViewById(R.id.txtObservacionServicios);
		tipodanos = (RadioGroup)view.findViewById(R.id.radioGroupServicios);
		aplica = (CheckBox)view.findViewById(R.id.chksiaplicaservicios);
		funciona = (CheckBox)view.findViewById(R.id.chksifuncionaservicios);
		int buttonCheckedId = tipodanos.getCheckedRadioButtonId();
		if(buttonCheckedId  == -1){
			if((RadioButton)view.findViewById(tipodanos.getCheckedRadioButtonId()) == null)
				tipo = (RadioButton)view.findViewById(tipodanos.getCheckedRadioButtonId());						
		}else{
			//Toast.makeText(getActivity(), "id: " + buttonCheckedId, Toast.LENGTH_SHORT).show();
			tipo = (RadioButton)view.findViewById(tipodanos.getCheckedRadioButtonId());
		}	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		final View view = inflater.inflate(R.layout.servicio, container);
		getDatosUI(view);
		tipodanos.setOnCheckedChangeListener(new OnCheckedChangeListener(){			

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				tipo = (RadioButton)view.findViewById(arg1);
				}			
		});
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sfunciona, saplica, dano = null, observaciones;
				Servicio servicio = new Servicio();
				
				if(tipo.getText().toString().equalsIgnoreCase("Sin daño"))
					dano = "0";
				else if(tipo.getText().toString().equalsIgnoreCase("Daño parcial"))
					dano = "1";
				else if(tipo.getText().toString().equalsIgnoreCase("Daño total"))
					dano = "2";
				
				if(aplica.isChecked())
					saplica = aplica.getText().toString();
				else
					saplica = "No aplica";
				
				if(funciona.isChecked())
					sfunciona = funciona.getText().toString();
				else
					sfunciona = "No funciona";
				observaciones = observacion.getText().toString();
				Log.w("funciona y aplica", sfunciona + ", " + saplica);
				servicio.setIdtipodano(dano);
				servicio.setObservacion(observaciones);
				servicio.setSiaplica(saplica);
				servicio.setSifunciona(sfunciona);
				ServiciosListener listener = (ServiciosListener)getActivity();
				listener.onFinishServicios(servicio, position);
				getDialog().dismiss();
			}			
		});
		
		cancelar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDialog().dismiss();
			}			
		});
		return view;
	}
	
	
}
