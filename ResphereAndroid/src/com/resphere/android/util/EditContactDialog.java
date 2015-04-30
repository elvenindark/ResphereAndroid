package com.resphere.android.util;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.resphere.android.vista.R;

public class EditContactDialog extends DialogFragment {
	
	private EditText nombre;
	private EditText organizacion;
	private RadioGroup sexo;
	private EditText telefono;
	private EditText email;
	private Button guardar;
	private Button cancelar;
	
	public EditContactDialog(){
		
	}
	
	public interface EditNameDialogListener {
        void onFinishEditDialog(String nombre, String organizacion, String sexo, 
        		String telefono, String email);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		View view = inflater.inflate(R.layout.contacto, container);
		nombre = (EditText)view.findViewById(R.id.nombreEE);
		organizacion = (EditText)view.findViewById(R.id.organizacionEE);
		sexo = (RadioGroup)view.findViewById(R.id.radioGroupEE);
		telefono = (EditText)view.findViewById(R.id.telefonoEE);
		email = (EditText)view.findViewById(R.id.emailEE);
		getDialog().setTitle("Informacion de contacto");
		guardar = (Button)view.findViewById(R.id.btnGuardarContacto);
		cancelar = (Button)view.findViewById(R.id.btnCancelarContacto);
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditNameDialogListener activity = (EditNameDialogListener)getActivity();
				activity.onFinishEditDialog(nombre.getText().toString(), organizacion.getText().toString(), 
						"sexo", telefono.getText().toString(), email.getText().toString());
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
