package com.resphere.android.vista.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.resphere.android.modelo.Organizacion;
import com.resphere.android.vista.R;

public class OrganizacionFragment extends DialogFragment {

	private EditText organizacion;
	private EditText sector;
	private EditText contacto;
	private Button guardar;
	private Button cancelar;
	private Organizacion o;
	
	public OrganizacionFragment(){
		
	}
	
	public interface OrganizacionListener{
		public void OnFinishListener(Organizacion org);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		View view = inflater.inflate(R.layout.organizacion, container);
		getDialog().setTitle("Organizacion");
		organizacion = (EditText)view.findViewById(R.id.editOrganizacion);
		sector = (EditText)view.findViewById(R.id.editOrganizacion);
		contacto = (EditText)view.findViewById(R.id.editContacto);
		guardar = (Button)view.findViewById(R.id.btnGuardarOrgF);
		cancelar = (Button)view.findViewById(R.id.btnCancelarOrg);
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				o = new Organizacion();
				o.setNombre(organizacion.getText().toString());
				o.setSector(sector.getText().toString());
				o.setContacto(contacto.getText().toString());
				
				OrganizacionListener activity = (OrganizacionListener)getActivity();
				activity.OnFinishListener(o);
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
