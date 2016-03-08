package com.resphere.android.vista;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Acceso;
import com.resphere.android.modelo.Tipoacceso;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.Reflection;

public class AfectacionAccesabilidadActivity extends Activity {

	private String identificador;
	private CheckBox checkTerrestre;
	private CheckBox checkAereo;
	private CheckBox checkFluvial;
	private EditText tipoTransporte;
	private Button guardar;
	private Button enviar;
	private Acceso acceso;
	private Tipoacceso tipoacceso;
	private ArrayList<Tipoacceso> listaAcceso;
	private String ip;
	private String port;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afectacion_accesabilidad);
		
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
		
		checkTerrestre = (CheckBox)findViewById(R.id.chkAccesoTerrestre);
		checkAereo = (CheckBox)findViewById(R.id.chkAccesoAereo);
		checkFluvial = (CheckBox)findViewById(R.id.chkAccesoFluvial);
		tipoTransporte = (EditText)findViewById(R.id.txtTipoTransporte);
		guardar = (Button)findViewById(R.id.btnGuardarAcceso);
		enviar = (Button)findViewById(R.id.btnEnviarAcceso);
		
		guardar.setVisibility(android.view.View.INVISIBLE);
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				guardarDatos(acceso);
				guardarDatos(listaAcceso);
				setDataTask(acceso);				
				setListTask(listaAcceso);
				Toast.makeText(v.getContext(), "Accesabilidad enviado correctamente", Toast.LENGTH_LONG).show();
				finish();
			}			
		});		
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(acceso))
					if(guardarDatos(listaAcceso))
						Toast.makeText(getApplicationContext(), "Accesabilidad guardada correctamente", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(), "Problemas al guardar la lista de acceso", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar accesabilidad", Toast.LENGTH_SHORT).show();
				finish();	
			}
			
		});
	}
	
	public void setDataTask(Acceso ev){
		Class clazz;
		//clazz = Class.forName("com.resphere.android.modelo.Evento");
		
		clazz = ev.getClass();
		Reflection clase = new Reflection(clazz);
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		ip = prefe.getString("ip", "");
		port = prefe.getString("port", "");
				
		ArrayList<String> atributos = new ArrayList<String>();
		ArrayList<String> getters = new ArrayList<String>();		
		ArrayList<String> values = new ArrayList<String>();
		
		//obtener
		values = (ArrayList) clase.returnDatos(ev);		
		atributos = (ArrayList) clase.returnFields().clone();				
		
		AsyncTaskSend enviar = new AsyncTaskSend(ip,port,"acceso");
		enviar.execute(atributos, values);	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setListTask(ArrayList<Tipoacceso> tipoAcceso){
		Class clazz;
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		ip = prefe.getString("ip", "");
		port = prefe.getString("port", "");
		ArrayList<ArrayList<String>> listofobjects = new ArrayList<ArrayList<String>>();		
		ArrayList<String> atributos = new ArrayList<String>();					
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < tipoAcceso.size(); i++){
			clazz = tipoAcceso.get(i).getClass();
			Reflection clase = new Reflection(clazz);			
			values = (ArrayList) clase.returnDatos(tipoAcceso.get(i));		
			atributos = (ArrayList) clase.returnFields().clone();
			listofobjects.add(values);
		}
		AsyncTaskSend enviar = new AsyncTaskSend(ip, port, "tipoacceso", 1, listofobjects);
		enviar.execute(atributos);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean guardarDatos(Acceso acceso){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getAccesoDAO().add(acceso);
		try {
			dbManager.getAccesoDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean guardarDatos(ArrayList<Tipoacceso> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getTipoAccesoDAO().addAll(list);
		try {
			dbManager.getAccesoDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void getDatos(){
		acceso = new Acceso();
		listaAcceso = new ArrayList<Tipoacceso>();
		
		if(checkTerrestre.isChecked()||checkAereo.isChecked()||checkFluvial.isChecked()){
			String terrestre = null, aereo = null, fluvial = null;
			if(checkTerrestre.isChecked()){
				terrestre = checkTerrestre.getText().toString();
				Tipoacceso objeto = new Tipoacceso();
				objeto.setIdevento(identificador);
				objeto.setIdtipoacceso("0");
				objeto.setTipoacceso(terrestre);
				listaAcceso.add(objeto);
			}
			if(checkAereo.isChecked()){
				aereo = checkAereo.getText().toString();
				Tipoacceso objeto = new Tipoacceso();
				objeto.setIdevento(identificador);
				objeto.setIdtipoacceso("1");
				objeto.setTipoacceso(aereo);
				listaAcceso.add(objeto);
			}
			if(checkFluvial.isChecked()){
				fluvial = checkFluvial.getText().toString();
				Tipoacceso objeto = new Tipoacceso();
				objeto.setIdevento(identificador);
				objeto.setIdtipoacceso("2");
				objeto.setTipoacceso(fluvial);
				listaAcceso.add(objeto);
			}
			for(int i = 0; i < listaAcceso.size(); i++)
				Toast.makeText(this, listaAcceso.get(i).getTipoacceso(), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "seleccione alguna opcion", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(tipoTransporte!=null||!tipoTransporte.getText().toString().equals("")){
			acceso.setTransporte(tipoTransporte.getText().toString());
			acceso.setIdevento(identificador);
			Toast.makeText(this, acceso.getIdevento(), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "registre el tipo de transporte", Toast.LENGTH_SHORT).show();
			return;
		}
			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_afectacion_accesabilidad,
				menu);
		return true;
	}

}
