package com.resphere.android.vista;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Accion;
import com.resphere.android.modelo.Organizacion;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;
import com.resphere.android.vista.adapter.CustomListAccion;
import com.resphere.android.vista.adapter.CustomListOrganizacion;
import com.resphere.android.vista.fragment.AccionFragment;
import com.resphere.android.vista.fragment.AccionFragment.AccionListener;
import com.resphere.android.vista.fragment.OrganizacionFragment;
import com.resphere.android.vista.fragment.OrganizacionFragment.OrganizacionListener;


public class AyudaHumanitariaActivity extends FragmentActivity implements OrganizacionListener, AccionListener{
	
	private Button agregarOrg;
	private Button agregarAccion;
	private Button guardarOrg;
	private Button enviarOrg;
	private Button guardarAccion;
	private Button enviarAccion;
	private ListView listViewOrg;
	private ListView listViewAccion;
	
	private CustomListOrganizacion adapterOrg;
	private CustomListAccion adapterAccion;
	private ArrayList<Organizacion> listaOrg;
	private ArrayList<Accion> listaAccion;
	private ConfiguracionPreferencias preferencias;
	private String identificador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ayuda_humanitaria);
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
		getUI();
		listaOrg = new ArrayList<Organizacion>();
		listaAccion = new ArrayList<Accion>();
		
		agregarOrg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showOrganizacionFragment(v);
			}
			
		});
		
		agregarAccion.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAccionFragment(v);
			}
			
		});
		
		guardarAccion.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(guardarAccion(listaAccion))
					Toast.makeText(getApplicationContext(), "Acciones guardadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Error al guardar acciones", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
		
		guardarOrg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(guardarOrg(listaOrg))
					Toast.makeText(getApplicationContext(), "Organizaciones guardadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Error al guardar organizaciones", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
		
		enviarOrg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(enviarOrg(listaOrg))
					Toast.makeText(getApplicationContext(), "Organizaciones enviadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Error al enviar organizaciones", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
		
		enviarAccion.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(enviarAccion(listaAccion))
					Toast.makeText(getApplicationContext(), "Acciones enviadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Error al enviar acciones", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
	}
	
	public Boolean enviarOrg(ArrayList<Organizacion> lista){
		Class clazz;		
		
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
		
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(), preferencias.getPortPref(), "organizacion", 1, listofobjects);
		enviar.execute(atributos);
		return true;
	}
	
	public Boolean enviarAccion(ArrayList<Accion> lista){
		Class clazz;		
		
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
		
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(), preferencias.getPortPref(), "accion", 1, listofobjects);
		enviar.execute(atributos);
		return true;
	}
	
	public Boolean guardarAccion(ArrayList<Accion> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getAccionDAO().addAll(list);
		try {
			dbManager.getAccionDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean guardarOrg(ArrayList<Organizacion> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getOrganizacionDAO().addAll(list);
		try {
			dbManager.getOrganizacionDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void showAccionFragment(View v){
		AccionFragment dialog = new AccionFragment();
		dialog.show(getSupportFragmentManager(), "Accion");
	}
	
	public void showOrganizacionFragment(View v){
		OrganizacionFragment dialog = new OrganizacionFragment();
		dialog.show(getSupportFragmentManager(), "Organizacion");
	}
	
	public void getUI(){
		agregarOrg = (Button)findViewById(R.id.btnAgregarOrg);
		agregarAccion = (Button)findViewById(R.id.btnAgregarAccion);
		guardarOrg = (Button)findViewById(R.id.btnGuardarOrg);
		guardarAccion = (Button)findViewById(R.id.btnGuardarAccion);
		enviarOrg = (Button)findViewById(R.id.btnEnviarOrg);
		enviarAccion = (Button)findViewById(R.id.btnEnviarAccion);
		listViewOrg = (ListView)findViewById(R.id.listOrg);
		listViewAccion =(ListView)findViewById(R.id.listAccion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ayuda_humanitaria, menu);
		return true;
	}

	@Override
	public void OnFinishListener(Organizacion org) {
		// TODO Auto-generated method stub
		org.setIdevento(identificador);
		org.setIdorganizacion(String.valueOf(System.currentTimeMillis()/1000));
		listaOrg.add(org);
		adapterOrg = new CustomListOrganizacion(AyudaHumanitariaActivity.this, R.id.listOrg, listaOrg);
		listViewOrg.setAdapter(adapterOrg);
	}

	@Override
	public void OnFinishListener(Accion accion) {
		// TODO Auto-generated method stub
		accion.setIdevento(identificador);
		listaAccion.add(accion);
		adapterAccion = new CustomListAccion(AyudaHumanitariaActivity.this, R.id.listAccion, listaAccion);
		listViewAccion.setAdapter(adapterAccion);
	}

}
