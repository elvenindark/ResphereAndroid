package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.Arrays;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Salud;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;
import com.resphere.android.vista.adapter.CustomListSalud;
import com.resphere.android.vista.fragment.SAlimentariaFragment;
import com.resphere.android.vista.fragment.SAlimentariaFragment.SAlimentariaListener;
import com.resphere.android.vista.fragment.SaludFragment;
import com.resphere.android.vista.fragment.SaludFragment.SaludListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AfectacionSaludActivity extends FragmentActivity implements SAlimentariaListener, SaludListener{
	
	String[] menuSalud;
	private String identificador;
	private String[] salud;
	private String[] alimentacion;
	private ListView list;
	private Button spinnerSalud;
	private Button spinnerAlimentacion;
	private Button guardar;
	private Button enviar;
	private Salud[] arraySalud;
	private ArrayList<Salud> listSalud;
	private CustomListSalud adapter;
	private ArrayList<Salud> listaSalud;
	private ConfiguracionPreferencias preferencias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afectacion_salud);
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
		salud = getResources().getStringArray(R.array.salud);
		alimentacion = getResources().getStringArray(R.array.alimentacion);
		guardar = (Button)findViewById(R.id.btnGuardarSalud);
		enviar = (Button)findViewById(R.id.btnEnviarSalud);
		
		
		arraySalud = new Salud[salud.length + alimentacion.length];
		for(int i = 0; i < arraySalud.length; i++)
			arraySalud[i] = new Salud();
		
		list = (ListView)findViewById(R.id.listSalud);
		spinnerSalud = (Button)findViewById(R.id.spinnerSalud);
		spinnerAlimentacion = (Button)findViewById(R.id.spinnerAlimentacion);	
		
		final ArrayAdapter<String> adapterSalud = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, salud);
		adapterSalud.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	
		final ArrayAdapter<String> adapterAlimentacion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alimentacion);
		adapterAlimentacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  
		spinnerSalud.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AfectacionSaludActivity.this)
				  .setTitle("Salud y nutrición")
				  .setAdapter(adapterSalud, new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	
			    	showSaludFragment(v, which);
			    	spinnerSalud.setText(adapterSalud.getItem(which));
				    dialog.dismiss();
				    }
				  }).create().show();
			}
			
		});
		
		spinnerAlimentacion.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AfectacionSaludActivity.this)
				  .setTitle("Seguridad alimentaria")
				  .setAdapter(adapterAlimentacion, new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog, int which) {

				      // TODO: user specific action
			    	showSAlimentariaFragment(v, which);
			    	spinnerAlimentacion.setText(adapterAlimentacion.getItem(which));
				    dialog.dismiss();
				    }
				  }).create().show();
			}
			
		});
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(listaSalud))
					Toast.makeText(getApplicationContext(), "Salud guardada correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar salud", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(enviarDatos(listaSalud))
					Toast.makeText(getApplicationContext(), "Salud enviada correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar salud", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
	}
	
	public Boolean enviarDatos(ArrayList<Salud> lista){
		Class clazz;		
		
		guardarDatos(lista);
		
		ArrayList<ArrayList<String>> listofobjects = new ArrayList<ArrayList<String>>();		
		ArrayList<String> atributos = new ArrayList<String>();					
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < lista.size(); i++){
			clazz = lista.get(i).getClass();
			Reflection clase = new Reflection(clazz);			
			values = (ArrayList) clase.returnDatos(lista.get(i));		
			atributos = (ArrayList) clase.returnFields().clone();
			listofobjects.add(values);
		}
		Log.d("size on poblacionimpactada", " "+atributos.size());
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(), preferencias.getPortPref(), "salud", 1, listofobjects);
		enviar.execute(atributos);
		return true;
	}
	
	public Boolean guardarDatos(ArrayList<Salud> lista){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getSaludDAO().addAll(lista);
		try {
			dbManager.getSaludDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getDatos(){
		listaSalud = new ArrayList<Salud>();
		for(int i = 0; i< listSalud.size(); i++){
			if(listSalud.get(i) != null && listSalud.get(i).getSifunciona()!=null){
				listSalud.get(i).setIdevento(identificador);
				listaSalud.add(listSalud.get(i));
				//Log.d("lista salud", listaSalud.get(i).getIdevento());
			}
		}
	}
	
	private void showSAlimentariaFragment(View v, int position){
		Log.d("before fragment", alimentacion[position]);
		SAlimentariaFragment salimentariaDialog = new SAlimentariaFragment(position, alimentacion[position]);
		salimentariaDialog.show(getSupportFragmentManager(), "salimentaria");
	}
	
	private void showSaludFragment(View v, int position){
		SaludFragment saludDialog = new SaludFragment(position, salud[position]);
		saludDialog.show(getSupportFragmentManager(), "salud");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_afectacion_salud, menu);
		return true;
	}

	@Override
	public void onFinishSalud(Salud salud, int position) {
		// TODO Auto-generated method stub
		if(salud != null){
			arraySalud[position] = salud;
			listSalud = new ArrayList<Salud>(Arrays.asList(arraySalud));
			
			if(listSalud != null){
				adapter = new CustomListSalud(AfectacionSaludActivity.this, R.id.listSalud, listSalud);
				list.setAdapter(adapter);
			}
		}
	}

	@Override
	public void onFinishSAlimentaria(Salud salud, int position) {
		// TODO Auto-generated method stub
		if(salud != null){
			salud.setIdtiposalud(String.valueOf(position + this.salud.length));
			arraySalud[position+this.salud.length] = salud;
			listSalud = new ArrayList<Salud>(Arrays.asList(arraySalud));
			
			if(listSalud != null){
				adapter = new CustomListSalud(AfectacionSaludActivity.this, R.id.listSalud, listSalud);
				list.setAdapter(adapter);
			}
		}
	}

}
