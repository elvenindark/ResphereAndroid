package com.resphere.android.vista;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Nrrhh;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;

public class NecesidadesRRHHActivity extends Activity {

	private CheckBox check0;
	private CheckBox check1;
	private CheckBox check2;
	private CheckBox check3;
	private Button guardar;
	private Button enviar;
	private EditText text0;
	private EditText text1;
	private EditText text2;
	private EditText text3;
	
	private ConfiguracionPreferencias preferencias;
	private Nrrhh recurso;
	private ArrayList<Nrrhh> lista;
	private String identificador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_necesidades_rrhh);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
		    return;
		}
		// Get data via the key
		identificador = extras.getString("identificador");
		if (identificador != null) {
		  // do something with the data
			//Toast.makeText(this, identificador, Toast.LENGTH_SHORT).show();
		} 
		
		preferencias = new ConfiguracionPreferencias(this);
		
		getIU();
		check0.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
					text0.setEnabled(true);
				else
					text0.setEnabled(false);			
			}			
		});
		check1.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
					text1.setEnabled(true);
				else
					text1.setEnabled(false);			
			}			
		});
		check2.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
					text2.setEnabled(true);
				else
					text2.setEnabled(false);			
			}			
		});
		check3.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
					text3.setEnabled(true);
				else
					text3.setEnabled(false);			
			}			
		});
		
		guardar.setVisibility(android.view.View.INVISIBLE);
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(lista))
					Toast.makeText(getApplicationContext(), "Se ha guardado necesidades correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar necesidades", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				guardarDatos(lista);
				if(enviarDatos(lista))
					Toast.makeText(getApplicationContext(), "Se ha enviado necesidades correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar necesidades", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
	}
	
	public Boolean enviarDatos(ArrayList<Nrrhh> list){
		
		Class clazz;
		ArrayList<ArrayList<String>> listofobjects = new ArrayList<ArrayList<String>>();		
		ArrayList<String> atributos = new ArrayList<String>();					
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++){
			clazz = list.get(i).getClass();
			Reflection clase = new Reflection(clazz);			
			values = (ArrayList) clase.returnDatos(list.get(i));		
			atributos = (ArrayList) clase.returnFields().clone();
			listofobjects.add(values);
		}
		
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(), preferencias.getPortPref(), "nrrhh", 1, listofobjects);
		enviar.execute(atributos);
		
		return true;
	}
	
	public Boolean guardarDatos(ArrayList<Nrrhh> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getNrrhhDAO().addAll(list);
		try {
			dbManager.getNrrhhDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getDatos(){
		lista = new ArrayList<Nrrhh>();
		Nrrhh item = new Nrrhh();
		if(check0.isChecked()){
			item.setAplica("SI");
			item.setIdevento(identificador);
			item.setIdtiporrhh(String.valueOf(0));
			item.setRequerimiento(text0.getText().toString());
			lista.add(item);
		}
		if(check1.isChecked()){
			item.setAplica("SI");
			item.setIdevento(identificador);
			item.setIdtiporrhh(String.valueOf(1));
			item.setRequerimiento(text1.getText().toString());
			lista.add(item);
		}
		if(check2.isChecked()){
			item.setAplica("SI");
			item.setIdevento(identificador);
			item.setIdtiporrhh(String.valueOf(2));
			item.setRequerimiento(text2.getText().toString());
			lista.add(item);
		}
		if(check3.isChecked()){
			item.setAplica("SI");
			item.setIdevento(identificador);
			item.setIdtiporrhh(String.valueOf(3));
			item.setRequerimiento(text3.getText().toString());
			lista.add(item);
		}
		
	}
	
	public void getIU(){
		check0 = (CheckBox)findViewById(R.id.checkBusquedaNRH);
		check1 = (CheckBox)findViewById(R.id.checkSaludNRH);
		check2 = (CheckBox)findViewById(R.id.checkSeguridadNRH);
		check3 = (CheckBox)findViewById(R.id.checkIngNRH);
		guardar = (Button)findViewById(R.id.btnGuardarNRH);
		enviar = (Button)findViewById(R.id.btnEnviarNRH);
		text0 = (EditText)findViewById(R.id.editBusquedaNRH);
		text0.setEnabled(false);
		//text0.setVisibility(EditText.INVISIBLE);		
		text1 = (EditText)findViewById(R.id.editSaludNRH);
		text1.setEnabled(false);
		//text1.setVisibility(EditText.INVISIBLE);
		text2 = (EditText)findViewById(R.id.editSeguridadNRH);
		text2.setEnabled(false);
		//text2.setVisibility(EditText.INVISIBLE);
		text3 = (EditText)findViewById(R.id.editIngNRH);
		text3.setEnabled(false);
		//text3.setVisibility(EditText.INVISIBLE);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_necesidades_rrhh, menu);
		return true;
	}

}
