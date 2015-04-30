package com.resphere.android.vista.fragment;

import com.resphere.android.modelo.Accion;
import com.resphere.android.vista.R;
import com.resphere.android.vista.fragment.FechaPickerFragment.EditFechaListener;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccionFragment extends DialogFragment implements EditFechaListener{
	
	private TextView date;
	private Button fecha;
	private Button guardar;
	private Button cancelar;
	private EditText descripcion;
	private EditText organizacion;
	private EditText hogares;
	private EditText personas;
	private Accion a;
	private View view;
	
	public AccionFragment(){
		
	}
	
	public interface AccionListener{
		public void OnFinishListener(Accion accion);
	}
	
	public interface FechaListener{
		public void OnFinishListener(String date);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		view = inflater.inflate(R.layout.accion, container);
		getDialog().setTitle("Acciones");
		
		getIU(view);
		
		fecha.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("before call", date.getText().toString() + ":" + date.toString());
				showDateFragment(v);
			}
			
		});
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				a = new Accion();
				a.setFecha(date.getText().toString());
				a.setDescripcion(descripcion.getText().toString());
				a.setOrganizacion(organizacion.getText().toString());
				a.setHogares(hogares.getText().toString());
				a.setPersonas(personas.getText().toString());
				
				AccionListener activity = (AccionListener)getActivity();
				activity.OnFinishListener(a);
				
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
	
	public void getIU(View view){
		fecha = (Button)view.findViewById(R.id.btnFechaAccion);
		date = (TextView)view.findViewById(R.id.textFechaAccion);
		descripcion = (EditText)view.findViewById(R.id.editDescripcionAccion);
		organizacion = (EditText)view.findViewById(R.id.editOrganizacionAccion);
		hogares = (EditText)view.findViewById(R.id.editHogaresAccion);
		personas = (EditText)view.findViewById(R.id.editPersonasAccion);
		guardar = (Button)view.findViewById(R.id.btnGuardarAccionF);
		cancelar = (Button)view.findViewById(R.id.btnCancelarAccion);
	}
	
	public void showDateFragment(View v){
		FechaPickerFragment dateDialog = new FechaPickerFragment("Accion");
		dateDialog.show(getFragmentManager(), "Fecha");
	}

	@Override
	public void onFinishFechaDialog(String fecha, String hora) {		
		if(fecha == null && hora == null)
			Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
		else{			
			date.setText(fecha + " " + hora);
			Log.d("after", date.toString());
			Log.d("error", fecha+" "+hora);
		}
	}
	
	public static AccionFragment newInstance() {
	    AccionFragment f = new AccionFragment();
	    return f;
	}
	
}
