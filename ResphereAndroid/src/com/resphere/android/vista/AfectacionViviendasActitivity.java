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
import android.widget.EditText;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Vivienda;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.Reflection;

public class AfectacionViviendasActitivity extends Activity {
	
	private String identificador;
	private String ip;
	private String port;
	private EditText sinDano;
	private EditText temporalNH;
	private EditText danoPH;
	private EditText danoTNH;
	private EditText totalV;
	private EditText observaciones;
	private Button guardar;
	private Button enviar;
	private Vivienda vivienda; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afectacion_viviendas);
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
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		ip = prefe.getString("ip", "");
		port = prefe.getString("port", "");
		
		sinDano = (EditText)findViewById(R.id.txtSinDanoV);
		temporalNH = (EditText)findViewById(R.id.txtTemporalV);
		danoPH = (EditText)findViewById(R.id.txtDanoParcialV);
		danoTNH = (EditText)findViewById(R.id.txtDanoTotalNV);
		totalV = (EditText)findViewById(R.id.txtTotalV);
		observaciones = (EditText)findViewById(R.id.txtObservacionesV);
		
		guardar = (Button)findViewById(R.id.btnGuardarV);
		enviar = (Button)findViewById(R.id.btnEnviarV);
		
		guardar.setVisibility(android.view.View.INVISIBLE);
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(vivienda))
					Toast.makeText(getApplicationContext(), "Viviendas guardadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar Viviendas", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				guardarDatos(vivienda);
				sendTask(vivienda);				
				Toast.makeText(getApplicationContext(), "Daños en vivienda enviados", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
	}

	public Boolean guardarDatos(Vivienda v){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getViviendaDAO().add(v);
		try {
			dbManager.getViviendaDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getDatos(){
		vivienda = new Vivienda();
		vivienda.setIdevento(identificador);
		vivienda.setSindano(sinDano.getText().toString());
		vivienda.setDanoparcialh(danoPH.getText().toString());
		vivienda.setTemporalnh(temporalNH.getText().toString());
		vivienda.setDanototalnh(danoTNH.getText().toString());
		vivienda.setTotalv(totalV.getText().toString());
		vivienda.setObservacion(observaciones.getText().toString());
	}
	
	public void sendTask(Vivienda vivi){
		Class clazz;
		//clazz = Class.forName("com.resphere.android.modelo.Evento");
		
		clazz = vivi.getClass();
		Reflection clase = new Reflection(clazz);
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		ip = prefe.getString("ip", "");
		port = prefe.getString("port", "");
				
		ArrayList<String> atributos = new ArrayList<String>();
		ArrayList<String> getters = new ArrayList<String>();		
		ArrayList<String> values = new ArrayList<String>();
		
		//obtener
		values = (ArrayList) clase.returnDatos(vivi);		
		atributos = (ArrayList) clase.returnFields().clone();
		getters = (ArrayList) clase.returnGetters().clone();				
		
		AsyncTaskSend enviar = new AsyncTaskSend(ip,port,"vivienda");
		enviar.execute(atributos, values);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_afectacion_viviendas, menu);
		return true;
	}

}
