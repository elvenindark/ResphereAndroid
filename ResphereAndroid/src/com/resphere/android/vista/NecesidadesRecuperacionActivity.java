package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.NRecuperacion;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;
import com.resphere.android.vista.adapter.CustomListNRecuperacion;
import com.resphere.android.vista.fragment.NRFragment;
import com.resphere.android.vista.fragment.NRFragment.NRListener;

public class NecesidadesRecuperacionActivity extends FragmentActivity implements NRListener{

	private String[] necesidadesRecuperacion;
	private NRecuperacion[] arrayNR;
	private ArrayList<NRecuperacion> listaNR;
	
	private Button guardar;
	private Button enviar;
	private ListView listViewNR;
	private Button spinnerNR;
	private String identificador;
	private CustomListNRecuperacion adapter;
	private ArrayList<NRecuperacion> listaNRAux;
	
	private ConfiguracionPreferencias preferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_necesidades_recuperacion);
		
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
		necesidadesRecuperacion = getResources().getStringArray(R.array.recuperacionTemprana);
		getIU();
		
		arrayNR = new NRecuperacion[necesidadesRecuperacion.length];
		for(int i = 0; i < necesidadesRecuperacion.length; i++){
			arrayNR[i] = new NRecuperacion();
		}
		
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, necesidadesRecuperacion);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerNR.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View view) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(NecesidadesRecuperacionActivity.this)
				.setTitle("Seleccione el Sector")
				.setAdapter(dataAdapter, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 
						showNRFragment(view, arg1, dataAdapter.getItem(arg1));
						spinnerNR.setText(dataAdapter.getItem(arg1));
						arg0.dismiss();
					}
					
				})
				.create().show();
			}			
		});
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(listaNR))
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
				if(enviarDatos(listaNR))
					Toast.makeText(getApplicationContext(), "Necesidades enviadas correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar Necesidades", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
	}
	
	public Boolean enviarDatos(ArrayList<NRecuperacion> list){
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
		
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(), preferencias.getPortPref(), "nrecuperacion", 1, listofobjects);
		enviar.execute(atributos);
		
		return true;
	}
	
	public Boolean guardarDatos(ArrayList<NRecuperacion> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getNrecuperacionDAO().addAll(list);
		try {
			dbManager.getNrecuperacionDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getDatos(){
		listaNR = new ArrayList<NRecuperacion>();
		for(int i = 0; i < arrayNR.length; i++){
			if(arrayNR[i].getIdtiposector() != null)
				listaNR.add(arrayNR[i]);
		}
	}
	
	public void showNRFragment(View v, int position, String msg){
		NRFragment nr = new NRFragment(position, msg);
		nr.show(getSupportFragmentManager(), "NR");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_necesidades_recuperacion,
				menu);
		return true;
	}
	
	public void getIU(){
		guardar = (Button)findViewById(R.id.btnGuardarNR);
		enviar = (Button)findViewById(R.id.btnEnviarNR);
		listViewNR = (ListView)findViewById(R.id.listNR);
		spinnerNR = (Button)findViewById(R.id.spinnerCustomNR);
	}

	@Override
	public void OnFinishNRFragment(NRecuperacion n, int pos) {
		// TODO Auto-generated method stub
		if(n!=null){
			n.setIdevento(identificador);
			arrayNR[pos] = n;
			listaNRAux = new ArrayList<NRecuperacion>(Arrays.asList(arrayNR));
			if(listaNRAux == null)
			{
				Toast.makeText(getApplicationContext(), "datos no registrados", Toast.LENGTH_SHORT).show();
				return;
			}else{
				adapter = new CustomListNRecuperacion(NecesidadesRecuperacionActivity.this, R.id.listNR, listaNRAux);
				listViewNR.setAdapter(adapter);
			}
		}
	}

}
