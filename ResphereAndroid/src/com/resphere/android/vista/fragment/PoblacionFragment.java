package com.resphere.android.vista.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.resphere.android.modelo.TipoPoblacion;
import com.resphere.android.vista.R;

public class PoblacionFragment extends DialogFragment {

	private Button guardar;
	private Button cancelar;
	private EditText hombres;
	private EditText mujeres;
	private EditText ninos;
	private EditText ninas;
	private EditText personas;
	private EditText hogares;
	private TipoPoblacion tipopoblacion;
	private int position;
	private int size;
	
	public interface PoblacionListener{		
		public void onFinishPoblacion(TipoPoblacion tipopoblacion, int position);
	}
	
	public PoblacionFragment(int position, int size){
		this.position = position;
		this.size = size;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		View view = inflater.inflate(R.layout.poblacion, container);
		guardar = (Button)view.findViewById(R.id.btnGuardarPoblacion);
		cancelar = (Button)view.findViewById(R.id.btnCancelarPoblacion);
		hombres = (EditText)view.findViewById(R.id.editHombres);
		mujeres = (EditText)view.findViewById(R.id.editMujeres);
		ninos = (EditText)view.findViewById(R.id.editNinos);
		ninas = (EditText)view.findViewById(R.id.editNinas);
		personas = (EditText)view.findViewById(R.id.editPersonas);
		hogares = (EditText)view.findViewById(R.id.editHogares);
		
		if(position > size-1){
			ninos.setVisibility(view.GONE);
			ninas.setVisibility(view.GONE);
			personas.setVisibility(view.GONE);
			ninos.setText("0");
			ninas.setText("0");
			personas.setText("0");
			Log.w("poblacion fragment: ", String.valueOf(position));
			//10 mujeres afectadas
			if(position == 8 || position == 9){
				hombres.setVisibility(view.INVISIBLE);
				mujeres.setVisibility(view.INVISIBLE);
				hombres.setText("0");
				mujeres.setText("0");
			}			
			if(position == 10){
				hombres.setVisibility(view.INVISIBLE);
				hombres.setText("0");
				hogares.setVisibility(view.INVISIBLE);
				hogares.setText("0");				
			}
		}		
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String hombre, mujer, nino, nina, persona, hogar;
				hombre = hombres.getText().toString();
				mujer = mujeres.getText().toString();
				nino = ninos.getText().toString();
				nina = ninas.getText().toString();
				persona = personas.getText().toString();
				hogar = hogares.getText().toString();
				tipopoblacion = new TipoPoblacion();
				tipopoblacion.setHombres(hombre);
				tipopoblacion.setHogares(hogar);
				tipopoblacion.setMujeres(mujer);
				tipopoblacion.setNinas(nina);
				tipopoblacion.setNinos(nino);
				tipopoblacion.setPersonas(persona);
				PoblacionListener listener = (PoblacionListener)getActivity();
				//listener.onFinishPoblacion(hombre, mujer, nino, nina, persona, hogar);
				listener.onFinishPoblacion(tipopoblacion, position);
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
