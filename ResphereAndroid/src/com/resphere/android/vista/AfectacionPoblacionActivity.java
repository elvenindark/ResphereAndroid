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
import android.widget.Spinner;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Poblacion;
import com.resphere.android.modelo.TipoPoblacion;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.Comunicacion;
import com.resphere.android.util.Preferencias;
import com.resphere.android.util.Reflection;
import com.resphere.android.vista.adapter.CustomListPoblacion;
import com.resphere.android.vista.fragment.PoblacionFragment;
import com.resphere.android.vista.fragment.PoblacionFragment.PoblacionListener;

public class AfectacionPoblacionActivity extends FragmentActivity implements PoblacionListener{

	private String[] poblacionAfectada;
	private String[] poblacionne;	
	private ListView list;
	private Button guardar;
	private Button enviar;
	private Button spinnerCustom;
	private CustomListPoblacion adapter;
	private ArrayList<TipoPoblacion> tipoPoblacion = new ArrayList<TipoPoblacion>();
	private TipoPoblacion[] arrayTipoPoblacion;
	ArrayList<Poblacion> listaPoblacion;
	private boolean[] isFilled;
	private String identificador;
	private String ip;
	private String port;
	private Button spinnerCustomNe;
	private Preferencias pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poblacion_impactada);
		
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
		
		poblacionAfectada = getResources().getStringArray(R.array.poblacionafectada);
		poblacionne = getResources().getStringArray(R.array.poblacionne);
		//spinner = (Spinner)findViewById(R.id.spinnerPoblacion);
		list = (ListView)findViewById(R.id.listTipoPoblacion);
		spinnerCustom = (Button)findViewById(R.id.spinnerCustom);
		spinnerCustomNe = (Button)findViewById(R.id.spinnerCustomNe);
		guardar = (Button)findViewById(R.id.btnGuardarPoblaciones);
		enviar = (Button)findViewById(R.id.btnEnviarPoblaciones);
		
		pref = new Preferencias();
		pref.init(this);
		ip = pref.getHost();
		port = pref.getPort();
		
		isFilled = new boolean[poblacionAfectada.length];
		arrayTipoPoblacion = new TipoPoblacion[poblacionAfectada.length+poblacionne.length];
		for(int i = 0; i < poblacionAfectada.length+poblacionne.length; i++){
			arrayTipoPoblacion[i] = new TipoPoblacion();
		}
		
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, poblacionAfectada);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //spinner.setAdapter(dataAdapter);		
		final ArrayAdapter<String> dataAdapterNe = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, poblacionne);
		dataAdapterNe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
		spinnerCustom.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AfectacionPoblacionActivity.this)
				  .setTitle("Seleccione tipo de afectacion")
				  .setAdapter(dataAdapter, new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog, int which) {

				      // TODO: user specific action
			    	showPoblacionFragment(v, which);
			    	spinnerCustom.setText(dataAdapter.getItem(which));
				    dialog.dismiss();
				    }
				  }).create().show();
			}	    	
	    });
	    	  
		spinnerCustomNe.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AfectacionPoblacionActivity.this)
				.setTitle("Poblacion con necesidades especiales")
				.setAdapter(dataAdapterNe, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						showPoblacionFragment(v, which + poblacionAfectada.length);
				    	spinnerCustomNe.setText(dataAdapterNe.getItem(which));
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
				if(guardarDatos(listaPoblacion))
					Toast.makeText(getApplicationContext(), "Poblacion guardada correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar poblacion", Toast.LENGTH_SHORT).show();
				finish();
			}
	    	
	    });
	    
	    enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(listaPoblacion)){
					if(sendListPoblacion(listaPoblacion)){
						Toast.makeText(getApplicationContext(), "Poblacion guardada correctamente", Toast.LENGTH_SHORT).show();
					}else
						Toast.makeText(getApplicationContext(), "Problemas al guardar poblacion", Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), "Poblacion enviada correctamente", Toast.LENGTH_SHORT).show();
					finish();
				}					
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar poblacion", Toast.LENGTH_SHORT).show();				
			}
	    	
	    });
	}
	
	public Boolean guardarDatos(ArrayList<Poblacion> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getPoblacionDAO().addAll(list);
		try {
			dbManager.getPoblacionDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getDatos(){
		listaPoblacion = new ArrayList<Poblacion>();
		if(tipoPoblacion.size()>0)
			for(int i = 0; i < tipoPoblacion.size(); i++){
				if(tipoPoblacion.get(i).getHogares()==null && tipoPoblacion.get(i).getHombres()==null
						&& tipoPoblacion.get(i).getMujeres()==null && tipoPoblacion.get(i).getNinas()==null
						&& tipoPoblacion.get(i).getNinos()==null && tipoPoblacion.get(i).getPersonas()==null){
					Log.d("lista poblacion", "no se han registrado datos en " + String.valueOf(i));					
				}else{					
					Poblacion poblacion1 = new Poblacion();
					poblacion1.setIdevento(identificador);
					poblacion1.setIdtipoafectacion(String.valueOf(i));
					poblacion1.setIdtipopoblacion("1");
					poblacion1.setNumero(tipoPoblacion.get(i).getHombres());					
					listaPoblacion.add(poblacion1);		
					
					Poblacion poblacion2 = new Poblacion();
					poblacion2.setIdevento(identificador);
					poblacion2.setIdtipoafectacion(String.valueOf(i));
					poblacion2.setIdtipopoblacion("2");
					poblacion2.setNumero(tipoPoblacion.get(i).getMujeres());
					listaPoblacion.add(poblacion2);		
					
					Poblacion poblacion3 = new Poblacion();
					poblacion3.setIdevento(identificador);
					poblacion3.setIdtipoafectacion(String.valueOf(i));
					poblacion3.setIdtipopoblacion("3");
					poblacion3.setNumero(tipoPoblacion.get(i).getNinos());
					listaPoblacion.add(poblacion3);	
					
					Poblacion poblacion4 = new Poblacion();
					poblacion4.setIdevento(identificador);
					poblacion4.setIdtipoafectacion(String.valueOf(i));
					poblacion4.setIdtipopoblacion("4");
					poblacion4.setNumero(tipoPoblacion.get(i).getNinas());
					listaPoblacion.add(poblacion4);		
					
					Poblacion poblacion5 = new Poblacion();
					poblacion5.setIdevento(identificador);
					poblacion5.setIdtipoafectacion(String.valueOf(i));
					poblacion5.setIdtipopoblacion("5");
					poblacion5.setNumero(tipoPoblacion.get(i).getPersonas());
					listaPoblacion.add(poblacion5);
					
					Poblacion poblacion6 = new Poblacion();
					poblacion6.setIdevento(identificador);
					poblacion6.setIdtipoafectacion(String.valueOf(i));
					poblacion6.setIdtipopoblacion("6");
					poblacion6.setNumero(tipoPoblacion.get(i).getHogares());
					listaPoblacion.add(poblacion6);						
				}
			}
		else
			Toast.makeText(getApplicationContext(), "no se han registrado datos", Toast.LENGTH_SHORT).show();
		
	}
	
	public void setListTask(ArrayList<Poblacion> listPoblacion){
		Class clazz;
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		ip = prefe.getString("ip", "");
		port = prefe.getString("port", "");
		ArrayList<ArrayList<String>> listofobjects = new ArrayList<ArrayList<String>>();		
		ArrayList<String> atributos = new ArrayList<String>();					
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < listPoblacion.size(); i++){
			clazz = listPoblacion.get(i).getClass();
			Reflection clase = new Reflection(clazz);			
			values = (ArrayList) clase.returnDatos(listPoblacion.get(i));		
			atributos = (ArrayList) clase.returnFields().clone();
			listofobjects.add(values);
		}
		Log.d("size on poblacionimpactada", " "+atributos.size());
		AsyncTaskSend enviar = new AsyncTaskSend(ip, port, "poblacion", 1, listofobjects);
		enviar.execute(atributos);
	}
	
	public Boolean sendListPoblacion(ArrayList<Poblacion> listPoblacion){
		Comunicacion<Poblacion> mensaje = new Comunicacion<Poblacion>(Poblacion.class, "poblacion", ip, port);
		if(mensaje.enviarListaObjetos(listPoblacion))
			return true;
		else
			return false;
	}
	
	public String[] getStringArray() {
        return poblacionAfectada;
    }

	private void showPoblacionFragment(View v, int position){
		PoblacionFragment poblacionDialog = new PoblacionFragment(position, poblacionAfectada.length);
		poblacionDialog.show(getSupportFragmentManager(), "poblacion");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_poblacion_impactada, menu);
		return true;
	}


	@Override
	public void onFinishPoblacion(TipoPoblacion tipopoblacion, int position) {
		// TODO Auto-generated method stub
		
		//tipoPoblacion.add(tipopoblacion);
		if(tipopoblacion!= null){
			arrayTipoPoblacion[position].setHombres(tipopoblacion.getHombres()); 
			arrayTipoPoblacion[position].setMujeres(tipopoblacion.getMujeres());
			arrayTipoPoblacion[position].setNinos(tipopoblacion.getNinos());
			arrayTipoPoblacion[position].setNinas(tipopoblacion.getNinas());
			arrayTipoPoblacion[position].setHogares(tipopoblacion.getHogares());
			arrayTipoPoblacion[position].setPersonas(tipopoblacion.getPersonas());
			
			tipoPoblacion = new ArrayList<TipoPoblacion>(Arrays.asList(arrayTipoPoblacion));
			
			if(tipoPoblacion == null){
				Toast.makeText(getApplicationContext(), "datos no registrados", Toast.LENGTH_SHORT).show();
				return;
			}else{			
				adapter = new CustomListPoblacion(AfectacionPoblacionActivity.this, R.id.listTipoPoblacion, tipoPoblacion);
				list.setAdapter(adapter);			
			}
		}else{
			Toast.makeText(getApplicationContext(), "objeto null", Toast.LENGTH_SHORT).show();
		}
	}

}
