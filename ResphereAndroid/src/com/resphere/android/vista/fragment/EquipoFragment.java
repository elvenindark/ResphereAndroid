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

import com.resphere.android.modelo.Equipo;
import com.resphere.android.vista.R;

public class EquipoFragment extends DialogFragment {
	
	private EditText nombre;
	private RadioGroup tipo;
	private CheckBox rol;
	private EditText organizacion;
	private EditText telefono;
	private EditText email;
	private Button guardar;
	private Button cancelar;
	private RadioButton checked;
	private View view;
	
	private int flag = 0;
	private Equipo e;
	
	public interface EquipoListener{
		void OnFinishListener(Equipo equipo);
	}
	
	public interface EntrevistadoListener{
		void OnFinishEListener(Equipo equipo);
	}
	
	public EquipoFragment(int flag){
		this.flag = flag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		view = inflater.inflate(R.layout.equipo, container);
		if(flag == 0)
			getDialog().setTitle("Equipo de Evaluación");
		else
			getDialog().setTitle("Entrevistados");
		
		getIU(view);			
		e = new Equipo();
		tipo.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				checked = (RadioButton)view.findViewById(tipo.getCheckedRadioButtonId());
				Log.w("equipo tipo", checked.getText().toString());
			}
			
		});		
			
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(flag == 0){					
					EquipoListener listener = (EquipoListener)getActivity();
					listener.OnFinishListener(e);					
				}else{
					EntrevistadoListener listener = (EntrevistadoListener)getActivity();
					listener.OnFinishEListener(e);
				}
				
				getDialog().dismiss();
			}
			
		});
		
		cancelar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			
				getDialog().dismiss();
			}
			
		});
		
		return view;
	}

	public void getDatos(){
		
		e.setNombre(nombre.getText().toString());
		if(flag == 0){
			if(rol.isChecked())
				e.setIdroltecnico(String.valueOf(0));
			else
				e.setIdroltecnico(String.valueOf(1));
			e.setEmail(email.getText().toString());			
		}
		else{
			e.setIdroltecnico(String.valueOf(5));
			e.setEmail("");
		}
		if(checked.getText().toString().equalsIgnoreCase("M"))
			e.setIdtipopoblacion(String.valueOf(1));
		else
			e.setIdtipopoblacion(String.valueOf(2));
		e.setTelefono(telefono.getText().toString());
		e.setOrganizacion(organizacion.getText().toString());
		Log.w("equipo ", e.getIdroltecnico() + "," +e.getIdtipopoblacion());
	}
	
	public void getIU(View v){
		nombre = (EditText)v.findViewById(R.id.editNombreEqF);
		tipo = (RadioGroup)v.findViewById(R.id.radioTipoPobEqF);
		rol = (CheckBox)v.findViewById(R.id.checkRolEqF);
		organizacion = (EditText)v.findViewById(R.id.editOrganizacionEqF);
		telefono = (EditText)v.findViewById(R.id.editTelefonoEqF);
		email = (EditText)v.findViewById(R.id.editEmailEqF);
		guardar = (Button)v.findViewById(R.id.btnGuardarEqF);
		cancelar = (Button)v.findViewById(R.id.btnCancelarEqF);
		int buttonCheckedId = tipo.getCheckedRadioButtonId();
		if(buttonCheckedId  != -1){
			if((RadioButton)v.findViewById(tipo.getCheckedRadioButtonId()) == null)
				checked = (RadioButton)v.findViewById(tipo.getCheckedRadioButtonId());
			else
				checked = (RadioButton)v.findViewById(tipo.getCheckedRadioButtonId());
		}
		if(flag == 1){
			rol.setEnabled(false);
			email.setEnabled(false);	
		}
	}
}
