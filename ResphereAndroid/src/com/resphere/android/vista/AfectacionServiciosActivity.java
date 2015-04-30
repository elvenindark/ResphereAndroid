package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.Arrays;

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

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Servicio;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.Reflection;
import com.resphere.android.vista.adapter.CustomListServicios;
import com.resphere.android.vista.fragment.ServiciosFragment;
import com.resphere.android.vista.fragment.ServiciosFragment.ServiciosListener;

public class AfectacionServiciosActivity extends FragmentActivity implements ServiciosListener {
	
	private String[] servicios;
	private String identificador;
	private Servicio arrayServicios[];
	private ListView list;
	private Button spinnerCustom;
	private Button guardar;
	private Button enviar;
	private ArrayList<Servicio> listServiciosAux;
	private CustomListServicios adapter;
	private ArrayList<Servicio> listServicios;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afectacion_servicios);
		
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
				
		servicios = getResources().getStringArray(R.array.servicios);
		list = (ListView)findViewById(R.id.listServicios);
		spinnerCustom = (Button)findViewById(R.id.spinnerCustomServicios);
		guardar = (Button)findViewById(R.id.btnGuardarServicios);
		enviar = (Button)findViewById(R.id.btnEnviarServicios);
		arrayServicios = new Servicio[servicios.length];
		for(int i = 0; i < servicios.length; i++){
			arrayServicios[i] = new Servicio();
		}
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, servicios);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerCustom.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AfectacionServiciosActivity.this)
				 .setTitle("Seleccione tipo de afectacion")
				  .setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {

				    // TODO: user specific action
			    	showServiciosFragment(v, which);
			    	spinnerCustom.setText(dataAdapter.getItem(which));
				    dialog.dismiss();
				    }
				  }).create().show();
			}			
		});	
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getDatos();
				setListTask(listServicios);
				Toast.makeText(getApplicationContext(), "Afectacion servicios enviados", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(listServicios))
					Toast.makeText(getApplicationContext(), "Servicios guardados correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar Servicios", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
	}
	
	public Boolean guardarDatos(ArrayList<Servicio> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getServicioDAO().addAll(list);
		try {
			dbManager.getServicioDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void getDatos(){
		listServicios = new ArrayList<Servicio>();
		for(int i = 0; i < arrayServicios.length; i++){
			if(arrayServicios[i].getIdtiposervicio()!=null)
				listServicios.add(arrayServicios[i]);
		}
	}
	
	public void setListTask(ArrayList<Servicio> listaServicio){
		Class clazz;
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		String ip = prefe.getString("ip", "");
		String port = prefe.getString("port", "");
		ArrayList<ArrayList<String>> listofobjects = new ArrayList<ArrayList<String>>();		
		ArrayList<String> atributos = new ArrayList<String>();					
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < listaServicio.size(); i++){
			clazz = listaServicio.get(i).getClass();
			Reflection clase = new Reflection(clazz);			
			values = (ArrayList) clase.returnDatos(listaServicio.get(i));		
			atributos = (ArrayList) clase.returnFields().clone();
			listofobjects.add(values);
		}
		Log.d("size on servicio", " "+atributos.size());
		AsyncTaskSend enviar = new AsyncTaskSend(ip, port, "servicio", 1, listofobjects);
		enviar.execute(atributos);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//calling fragment to display service IU
	public void showServiciosFragment(View v, int position){
		ServiciosFragment servicio = new ServiciosFragment(position);
		servicio.show(getSupportFragmentManager(), "Servicio");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_afectacion_servicios, menu);
		return true;
	}

	public void onFinishServicios(Servicio servicio, int position){
		if(servicio != null){
			arrayServicios[position].setIdtipodano(servicio.getIdtipodano());
			arrayServicios[position].setObservacion(servicio.getObservacion());
			arrayServicios[position].setSiaplica(servicio.getSiaplica());
			arrayServicios[position].setSifunciona(servicio.getSifunciona());
			arrayServicios[position].setIdtiposervicio(String.valueOf(position));
			arrayServicios[position].setIdevento(identificador);
			listServiciosAux = new ArrayList<Servicio>(Arrays.asList(arrayServicios));
			if(listServiciosAux == null){
				Toast.makeText(getApplicationContext(), "datos no registrados", Toast.LENGTH_SHORT).show();
				return;
			}else{
				//Toast.makeText(getApplicationContext(), arrayServicios[position].getObservacion(), Toast.LENGTH_SHORT).show();
				adapter = new CustomListServicios(AfectacionServiciosActivity.this, R.id.listServicios, listServiciosAux);
				list.setAdapter(adapter);
			}
		}
	}
}
