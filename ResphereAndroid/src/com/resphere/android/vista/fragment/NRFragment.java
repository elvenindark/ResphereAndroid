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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.resphere.android.modelo.NRecuperacion;
import com.resphere.android.vista.R;

public class NRFragment extends DialogFragment {

	private int position;
	private String msg;
	
	private Button guardar;
	private Button cancelar;
	private EditText observaciones;
	private RadioGroup radioNR;
	private RadioButton checked;
	private TextView title;
	private NRecuperacion nr;
	
	public interface NRListener{
		public void OnFinishNRFragment(NRecuperacion n, int pos);
	}
	
	public NRFragment(int position, String msg){
		this.position = position;
		this.msg = msg;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		final View view = inflater.inflate(R.layout.necesidadesrecuperacion, container);
		getIU(view);
		getDialog().setTitle(msg);
		radioNR.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				checked = (RadioButton)view.findViewById(arg1);
				}			
		});
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
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
		guardar = (Button)view.findViewById(R.id.btnGuardarNRF);
		cancelar = (Button)view.findViewById(R.id.btnCancelarNR);
		observaciones = (EditText)view.findViewById(R.id.editObservacionesNR);
		radioNR = (RadioGroup)view.findViewById(R.id.radioNR);
		title = (TextView)view.findViewById(R.id.txtSectorNR);
		title.setText(msg);
		int buttonCheckedId = radioNR.getCheckedRadioButtonId();
		if(buttonCheckedId  == -1){
			if((RadioButton)view.findViewById(radioNR.getCheckedRadioButtonId()) == null)
				checked = (RadioButton)view.findViewById(radioNR.getCheckedRadioButtonId());						
		}else{
			//Toast.makeText(getActivity(), "id: " + buttonCheckedId, Toast.LENGTH_SHORT).show();
			checked = (RadioButton)view.findViewById(radioNR.getCheckedRadioButtonId());
		}		
	}
	
	public void getDatos(){
		nr = new NRecuperacion();
		nr.setAplica(checked.getText().toString());
		nr.setObservacion(observaciones.getText().toString());
		nr.setIdtiposector(String.valueOf(position));
		
		NRListener listener = (NRListener)getActivity();
		listener.OnFinishNRFragment(nr, position);
	}
}
