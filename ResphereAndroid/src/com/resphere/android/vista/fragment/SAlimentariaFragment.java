package com.resphere.android.vista.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.resphere.android.modelo.Salud;
import com.resphere.android.vista.R;

public class SAlimentariaFragment extends DialogFragment{
	
	public interface SAlimentariaListener{
		public void onFinishSAlimentaria(Salud salud, int position);
	}

	private String msg;
	private int position;
	private Salud s;
	
	private Button guardar;
	private Button cancelar;
	private EditText porcentaje;
	private EditText observacion;
	private RadioGroup salimentaria;
	private RadioButton tipo;
	private TextView titulo;
	
	public SAlimentariaFragment(int position, String msg){
		this.position = position;
		this.msg = msg;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		final View view = inflater.inflate(R.layout.salimentaria, container);
		
		getDatosUI(view);
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				guardarDatos();
				SAlimentariaListener listener = (SAlimentariaListener)getActivity();
				listener.onFinishSAlimentaria(s, position);
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
	
	public void guardarDatos(){
		s = new Salud();
		s.setIdtiposalud(String.valueOf(position));
		s.setObservaciones(observacion.getText().toString());
		s.setPorcentaje(porcentaje.getText().toString());
		s.setSifunciona(tipo.getText().toString());
	}
	
	public void getDatosUI(View view){
		guardar = (Button)view.findViewById(R.id.btnGuardarSA);
		cancelar = (Button)view.findViewById(R.id.btnCancelarSA);
		porcentaje = (EditText)view.findViewById(R.id.editPorcentajeSA);
		observacion = (EditText)view.findViewById(R.id.editObservacionSA);
		salimentaria = (RadioGroup)view.findViewById(R.id.radioGroupSA);
		titulo = (TextView)view.findViewById(R.id.textTituloSA);
		titulo.setText(msg);		
		int buttonCheckedId = salimentaria.getCheckedRadioButtonId();
		if(buttonCheckedId  == -1){
			if((RadioButton)view.findViewById(salimentaria.getCheckedRadioButtonId()) == null)
				tipo = (RadioButton)view.findViewById(salimentaria.getCheckedRadioButtonId());						
		}else{
			//Toast.makeText(getActivity(), "id: " + buttonCheckedId, Toast.LENGTH_SHORT).show();
			tipo = (RadioButton)view.findViewById(salimentaria.getCheckedRadioButtonId());
		}	
	}
}
