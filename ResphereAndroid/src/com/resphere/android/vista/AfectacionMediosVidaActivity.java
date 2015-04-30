package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.Arrays;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.MedioVida;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.Reflection;
import com.resphere.android.vista.adapter.CustomListMediosVida;
import com.resphere.android.vista.fragment.MediosVidaFragment;
import com.resphere.android.vista.fragment.MediosVidaFragment.MediosVidaListener;

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

public class AfectacionMediosVidaActivity extends FragmentActivity implements MediosVidaListener{
	
	private String[] afectacionMedios;
	private String identificador;
	private ListView list;
	private Button spinnerCustom;
	private Button guardar;
	private Button enviar;
	private MedioVida[] arrayMediovida;
	//private Tipo[] arrayTipomediovida;
	private ArrayList<MedioVida> listMedioVidaAux;
	private ArrayList<MedioVida> listMedioVida;
	private CustomListMediosVida adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afectacion_medios_vida);
		
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
		
		afectacionMedios = getResources().getStringArray(R.array.afectacionmediosvida);
		list = (ListView)findViewById(R.id.listMediosVida);
		spinnerCustom = (Button)findViewById(R.id.spinnerCustomMV);
		guardar = (Button)findViewById(R.id.btnGuardarMediosV);
		enviar = (Button)findViewById(R.id.btnEnviarMediosV);
		arrayMediovida = new MedioVida[afectacionMedios.length];
		for(int i = 0; i < afectacionMedios.length; i++){
			arrayMediovida[i] = new MedioVida();
		}
		
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, afectacionMedios);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerCustom.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AfectacionMediosVidaActivity.this)
				  .setTitle("Seleccione tipo de afectacion")
				  .setAdapter(dataAdapter, new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	showMediosFragment(v, which);
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
				setListTask(listMedioVida);
				Toast.makeText(getApplicationContext(), "MedioVida enviados", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(listMedioVida))
					Toast.makeText(getApplicationContext(), "Medios de Vida guardados correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Error al guardar Medios de Vida", Toast.LENGTH_SHORT).show();
				finish();
			}
			
		});
	}
	
	public Boolean guardarDatos(ArrayList<MedioVida> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getMedioVidaDAO().addAll(list);
		try {
			dbManager.getMedioVidaDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	public void showMediosFragment(View v, int position){
		MediosVidaFragment mediosvida = new MediosVidaFragment(position);
		mediosvida.show(getSupportFragmentManager(), "MediosVida");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_afectacion_medios_vida, menu);
		return true;
	}

	public void getDatos(){
		listMedioVida = new ArrayList<MedioVida>();
		for(int i = 0; i < arrayMediovida.length; i++){
			if(arrayMediovida[i].getIdtipomediovida()!=null){				
				listMedioVida.add(arrayMediovida[i]);
			}			
		}		
	}
	
	public void setListTask(ArrayList<MedioVida> listaMedioVida){
		Class clazz;
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		String ip = prefe.getString("ip", "");
		String port = prefe.getString("port", "");
		ArrayList<ArrayList<String>> listofobjects = new ArrayList<ArrayList<String>>();		
		ArrayList<String> atributos = new ArrayList<String>();					
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < listaMedioVida.size(); i++){
			clazz = listaMedioVida.get(i).getClass();
			Reflection clase = new Reflection(clazz);			
			values = (ArrayList) clase.returnDatos(listaMedioVida.get(i));		
			atributos = (ArrayList) clase.returnFields().clone();
			listofobjects.add(values);
		}
		Log.d("size on mediovida", " "+atributos.size());
		AsyncTaskSend enviar = new AsyncTaskSend(ip, port, "mediovida", 1, listofobjects);
		enviar.execute(atributos);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onFinishMediosVida(MedioVida mediovida, int position) {
		// TODO Auto-generated method stub
		
		if(mediovida!=null){
			arrayMediovida[position].setHombres(mediovida.getHombres());
			arrayMediovida[position].setMujeres(mediovida.getMujeres());
			arrayMediovida[position].setSiaplica(mediovida.getSiaplica());
			arrayMediovida[position].setIdtipodano(mediovida.getIdtipodano());
			arrayMediovida[position].setObservacion(mediovida.getObservacion());
			arrayMediovida[position].setIdevento(identificador);
			arrayMediovida[position].setIdtipomediovida(String.valueOf(position));
			
			listMedioVidaAux = new ArrayList<MedioVida>(Arrays.asList(arrayMediovida));
			if(listMedioVidaAux == null){
				Toast.makeText(getApplicationContext(), "datos no registrados", Toast.LENGTH_SHORT).show();
				return;
			}else{
				adapter = new CustomListMediosVida(AfectacionMediosVidaActivity.this,R.id.listMediosVida, listMedioVidaAux);
				list.setAdapter(adapter);
				
			}
		}
	}

}
