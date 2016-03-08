package com.resphere.android.vista;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.NUrgente;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;

public class NecesidadesUrgentesActivity extends Activity {

	private EditText hogarAlb;
	private EditText hogarAli;
	private EditText hogarAgua;
	private EditText specAlb;
	private EditText specAli;
	private EditText specAgua;
	private Button guardar;
	private Button enviar;
	
	private ArrayList<NUrgente> list;
	private NUrgente item;
	private String identificador;
	private ConfiguracionPreferencias preferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_necesidades_respuesta);
		
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
		
		guardar.setVisibility(android.view.View.INVISIBLE);
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(list))
					Toast.makeText(getApplicationContext(), "Necesidades guardadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar Necesidades", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				guardarDatos(list);
				if(enviarDatos(list))
					Toast.makeText(getApplicationContext(), "Necesidades enviadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar Necesidades", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
		
	}
	
	public Boolean enviarDatos(ArrayList<NUrgente> list){
		
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
		
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(), preferencias.getPortPref(), "nurgente", 1, listofobjects);
		enviar.execute(atributos);
		
		return true;
	}
	
	public Boolean guardarDatos(ArrayList<NUrgente> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getNUrgenteDAO().addAll(list);
		try {
			dbManager.getNUrgenteDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	
	
	public void getDatos(){
		list = new ArrayList<NUrgente>();
		item = new NUrgente();
		item.setIdevento(identificador);
		item.setHogar(hogarAlb.getText().toString());
		item.setEspecificacion(specAlb.getText().toString());
		item.setIdtiponurgente(String.valueOf(0));
		list.add(item);
		item.setHogar(hogarAli.getText().toString());
		item.setIdevento(identificador);
		item.setEspecificacion(specAli.getText().toString());
		item.setIdtiponurgente(String.valueOf(1));
		list.add(item);
		item.setHogar(hogarAgua.getText().toString());
		item.setIdevento(identificador);
		item.setEspecificacion(specAgua.getText().toString());
		item.setIdtiponurgente(String.valueOf(2));
		list.add(item);
	}
	
	public void getIU(){
		hogarAlb = (EditText)findViewById(R.id.editHogarNU1);
		hogarAli = (EditText)findViewById(R.id.editHogarNU2);
		hogarAgua = (EditText)findViewById(R.id.editHogarNU3);
		specAli = (EditText)findViewById(R.id.editEspecificacionNU1);
		specAlb = (EditText)findViewById(R.id.editEspecificacionNU2);
		specAgua = (EditText)findViewById(R.id.editEspecificacionNU3);
		guardar = (Button)findViewById(R.id.btnGuardarNU);
		enviar = (Button)findViewById(R.id.btnEnviarNU);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_necesidades_respuesta, menu);
		return true;
	}

}
