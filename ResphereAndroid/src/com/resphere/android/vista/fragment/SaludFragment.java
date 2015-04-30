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

public class SaludFragment extends DialogFragment{

	public interface SaludListener{
		public void onFinishSalud(Salud salud, int position);
	}

	private String msg;
	private int position;
	private Salud s;
	
	private RadioGroup salud;
	private Button guardar;
	private Button cancelar;
	private RadioGroup tipoSalud;	
	private EditText observaciones;
	private RadioButton tipo;
	private TextView titulo;
	
	public SaludFragment(int position, String msg){
		this.position = position;
		this.msg = msg;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		final View view = inflater.inflate(R.layout.salud, container);
		
		getDatosUI(view);
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				guardarDatos();
				SaludListener listener = (SaludListener)getActivity();
				listener.onFinishSalud(s, position);
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
		s.setSifunciona(tipo.getText().toString());		
		s.setObservaciones(observaciones.getText().toString());
		s.setIdtiposalud(String.valueOf(position));
	}
	
	public void getDatosUI(View view){
		guardar = (Button)view.findViewById(R.id.btnGuardarSaludF);
		cancelar = (Button)view.findViewById(R.id.btnCancelarSaludF);
		observaciones = (EditText)view.findViewById(R.id.editObservacionSalud);
		tipoSalud = (RadioGroup)view.findViewById(R.id.radioSalud);
		titulo = (TextView)view.findViewById(R.id.textTitleSalud);
		titulo.setText(msg);
		int buttonCheckedId = tipoSalud.getCheckedRadioButtonId();
		if(buttonCheckedId  == -1){
			if((RadioButton)view.findViewById(tipoSalud.getCheckedRadioButtonId()) == null)
				tipo = (RadioButton)view.findViewById(tipoSalud.getCheckedRadioButtonId());						
		}else{
			//Toast.makeText(getActivity(), "id: " + buttonCheckedId, Toast.LENGTH_SHORT).show();
			tipo = (RadioButton)view.findViewById(tipoSalud.getCheckedRadioButtonId());
		}	
	}
}
