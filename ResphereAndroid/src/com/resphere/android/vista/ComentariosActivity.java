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
import com.resphere.android.modelo.Comentario;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;

public class ComentariosActivity extends Activity {

	private Button guardar;
	private Button enviar;
	private EditText comentario;
	
	private Comentario item;
	private String identificador;
	private ConfiguracionPreferencias preferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comentarios);
		
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
		
		guardar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(guardarDatos(item))
					Toast.makeText(getApplicationContext(), "Comentario guardado correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al guardar comentario", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
		
		enviar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if(enviarDatos(item))
					Toast.makeText(getApplicationContext(), "Comentario enviado correctamente", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Problemas al enviar comentario", Toast.LENGTH_SHORT).show();
				finish();
			}			
		});
	}
	
	public Boolean enviarDatos(Comentario com){
		
		Class clazz;
		
		clazz = com.getClass();
		Reflection clase = new Reflection(clazz);		
				
		ArrayList<String> atributos = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		//obtener
		values = (ArrayList) clase.returnDatos(com);		
		atributos = (ArrayList) clase.returnFields().clone();				
		
		Log.d("ipport", preferencias.getIpPref() + " " + preferencias.getPortPref());
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(),preferencias.getPortPref(),"comentario");
		enviar.execute(atributos, values);	
		return true;
	}
	
	public Boolean guardarDatos(Comentario com){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getComentarioDAO().add(com);
		try {
			dbManager.getComentarioDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getDatos(){
		item = new Comentario();
		item.setComentario(comentario.getText().toString());
		item.setIdevento(identificador);
	}
	
	public void getIU(){
		comentario = (EditText)findViewById(R.id.editComentario);
		guardar = (Button)findViewById(R.id.btnGuardarCom);
		enviar = (Button)findViewById(R.id.btnEnviarCom);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_comentarios, menu);
		return true;
	}

}
