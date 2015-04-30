package com.resphere.android.vista.fragment;

import com.resphere.android.vista.R;
import com.resphere.android.modelo.MedioVida;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MediosVidaFragment extends DialogFragment {

	private Button guardar;
	private Button cancelar;
	private EditText hombres;
	private EditText mujeres;
	private RadioGroup tipodanos;
	private CheckBox aplica;
	private EditText observaciones;
	
	private MedioVida mediovida;
	private int position;
	private RadioButton tipo;
	
	public interface MediosVidaListener{		
		public void onFinishMediosVida(MedioVida tipomediovia, int position);
	}
	
	public MediosVidaFragment(int position){
		this.position = position;
	}
	
	public void getDatosUI(View view){
		guardar = (Button)view.findViewById(R.id.btnGuardarMV);
		cancelar = (Button)view.findViewById(R.id.btnCancelarMV);
		hombres = (EditText)view.findViewById(R.id.editHombresMV);
		mujeres = (EditText)view.findViewById(R.id.editMujeresMV);
		tipodanos = (RadioGroup)view.findViewById(R.id.radioGroupMV);
		aplica = (CheckBox)view.findViewById(R.id.chkAplicaMV);
		observaciones = (EditText)view.findViewById(R.id.editObservacionesMV);	
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
		final View view = inflater.inflate(R.layout.mediovida, container);
		
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
				String hombre, mujer, dano = null, siaplica, observacion;
				hombre = hombres.getText().toString();
				mujer = mujeres.getText().toString();				
				mediovida = new MedioVida();
				if(tipo.getText().toString().equalsIgnoreCase("Sin daño"))
					dano = "0";
				else if(tipo.getText().toString().equalsIgnoreCase("Daño parcial"))
					dano = "1";
				else if(tipo.getText().toString().equalsIgnoreCase("Daño total"))
					dano = "2";
				//dano = tipo.getText().toString();				
				if(aplica.isChecked())
					siaplica = aplica.getText().toString();
				else
					siaplica = "No aplica";
				observacion = observaciones.getText().toString();				
				mediovida.setHombres(hombre);				
				mediovida.setMujeres(mujer);				
				mediovida.setSiaplica(siaplica);
				mediovida.setIdtipodano(dano);
				mediovida.setObservacion(observacion);
				MediosVidaListener listener = (MediosVidaListener)getActivity();
				listener.onFinishMediosVida(mediovida, position);
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
