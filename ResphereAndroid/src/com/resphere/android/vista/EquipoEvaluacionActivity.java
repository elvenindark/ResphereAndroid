package com.resphere.android.vista;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Equipo;
import com.resphere.android.modelo.Evaluacion;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;
import com.resphere.android.vista.adapter.CustomListEquipo;
import com.resphere.android.vista.fragment.EquipoFragment;
import com.resphere.android.vista.fragment.EquipoFragment.EntrevistadoListener;
import com.resphere.android.vista.fragment.EquipoFragment.EquipoListener;
import com.resphere.android.vista.fragment.FechaPickerFragment;
import com.resphere.android.vista.fragment.FechaPickerFragment.EditFechaListener;

@SuppressLint("NewApi")
public class EquipoEvaluacionActivity extends FragmentActivity implements EquipoListener, EntrevistadoListener, EditFechaListener{

	private TextView fecha;
	private Button fechaEv;
	private Button guardar;
	private Button enviar;
	private Button agregarEq;
	private Button agregarEnt;
	private ListView listViewEquipo;
	private ListView listViewEntrevistado;
	private ArrayList<Equipo> listEquipo;
	private ArrayList<Equipo> listEntrevistado;
	
	private CustomListEquipo adapterEq;
	private CustomListEquipo adapterEnt;
	private ConfiguracionPreferencias preferencias;
	private String identificador;
	private Evaluacion eval;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipo_evaluacion);
		
		preferencias = new ConfiguracionPreferencias(this);
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
		
		getIU();
		listEquipo = new ArrayList<Equipo>();
		listEntrevistado = new ArrayList<Equipo>();
		
		agregarEq.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showEquipoFragment(v, 0);
			}			
		});
		
		agregarEnt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showEquipoFragment(v, 1);
			}			
		});
		
		fechaEv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDateFragment();
			}			
		});
		
		guardar.setVisibility(android.view.View.INVISIBLE);
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFecha();
				if(guardarFecha(eval))
					Toast.makeText(getApplicationContext(), "Fecha guardada correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar la fecha", Toast.LENGTH_SHORT).show();
				if(guardarEquipo(listEquipo))
					Toast.makeText(getApplicationContext(), "Equipo guardado correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar el equipo", Toast.LENGTH_SHORT).show();
				if(guardarEquipo(listEntrevistado))
					Toast.makeText(getApplicationContext(), "Entrevistados guardados correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar los entrevistados", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFecha();
				guardarFecha(eval);
				if(enviarFecha(eval))
					Toast.makeText(getApplicationContext(), "Fecha enviada correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar la fecha", Toast.LENGTH_SHORT).show();
				guardarEquipo(listEquipo);
				if(enviarEquipo(listEquipo))
					Toast.makeText(getApplicationContext(), "Equipo enviado correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar el equipo", Toast.LENGTH_SHORT).show();
				guardarEquipo(listEntrevistado);
				if(enviarEquipo(listEntrevistado))
					Toast.makeText(getApplicationContext(), "Entrevistados enviados correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar los entrevistados", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
	}
	
	public Boolean enviarFecha(Evaluacion e){
		Class clazz;
		
		clazz = e.getClass();
		Reflection clase = new Reflection(clazz);		
				
		ArrayList<String> atributos = new ArrayList<String>();	
		ArrayList<String> values = new ArrayList<String>();
		
		values = (ArrayList) clase.returnDatos(e);		
		atributos = (ArrayList) clase.returnFields().clone();				
		
		Log.d("ipport", preferencias.getIpPref() + " " + preferencias.getPortPref());
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(),preferencias.getPortPref(),"evaluacion");
		enviar.execute(atributos, values);
		return true;
	}
	
	public Boolean enviarEquipo(ArrayList<Equipo> lista){
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
		
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(), preferencias.getPortPref(), "equipo", 1, listofobjects);
		enviar.execute(atributos);
		return true;
	}
	
	public Boolean guardarEquipo(ArrayList<Equipo> list){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getEquipoDAO().addAll(list);
		try {
			dbManager.getEquipoDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean guardarFecha(Evaluacion e){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getEvaluacionDAO().add(e);
		try {
			dbManager.getEvaluacionDAO().save();
		} catch (AdaFrameworkException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getFecha(){
		eval = new Evaluacion();
		eval.setIdevento(identificador);
		eval.setIdevaluacion(String.valueOf(System.currentTimeMillis()/1000));
		eval.setFecha(fecha.getText().toString());
	}

	public void showDateFragment(){
		FechaPickerFragment dialog = new FechaPickerFragment();
		dialog.show(getSupportFragmentManager(), "Equipo");
	}
	
	public void showEquipoFragment(View v, int flag){
		EquipoFragment dialog = new EquipoFragment(flag);
		dialog.show(getSupportFragmentManager(), "Equipo");
	}

	public void getIU(){
		fecha = (TextView)findViewById(R.id.textFechaEq);
		fechaEv = (Button)findViewById(R.id.btnFechaEq);
		guardar = (Button)findViewById(R.id.btnGuardarEq);
		enviar = (Button)findViewById(R.id.btnEnviarEq);
		agregarEq = (Button)findViewById(R.id.btnEquipoEq);
		agregarEnt = (Button)findViewById(R.id.btnEntrevistadoEq);
		listViewEquipo = (ListView)findViewById(R.id.listEquipo);
		listViewEntrevistado = (ListView)findViewById(R.id.listEntrevistado);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_equipo_evaluacion, menu);
		return true;
	}


	@Override
	public void OnFinishListener(Equipo equipo) {
		// TODO Auto-generated method stub
		equipo.setIdevento(identificador);
		listEquipo.add(equipo);
		adapterEq = new CustomListEquipo(EquipoEvaluacionActivity.this, R.id.listEquipo, listEquipo);
		listViewEquipo.setAdapter(adapterEq);
	}

	@Override
	public void OnFinishEListener(Equipo equipo) {
		// TODO Auto-generated method stub
		equipo.setIdevento(identificador);
		listEntrevistado.add(equipo);
		adapterEnt = new CustomListEquipo(EquipoEvaluacionActivity.this, R.id.listEntrevistado, listEntrevistado);
		listViewEntrevistado.setAdapter(adapterEnt);
	}

	@Override
	public void onFinishFechaDialog(String fecha, String hora) {
		// TODO Auto-generated method stub
		this.fecha.setText(fecha + " " + hora);
	}
	
}
