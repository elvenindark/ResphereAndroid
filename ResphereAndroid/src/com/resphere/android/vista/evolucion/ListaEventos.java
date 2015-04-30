package com.resphere.android.vista.evolucion;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Evento;
import com.resphere.android.modelo.TipoEvento;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.vista.EventoActivity;
import com.resphere.android.vista.InicioActivity;
import com.resphere.android.vista.PrincipalActivity;
import com.resphere.android.vista.R;

public class ListaEventos extends Activity {

	private ListView lista;
	private Button botonActualizar;
	private List<Evento> eventos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_eventos);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		lista = (ListView)findViewById(R.id.listEventos);
		botonActualizar = (Button)findViewById(R.id.btnActualizarEventos);
		if(actualizarEventos())
			Toast.makeText(getApplicationContext(), "Cargando", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "No se encuentran datos", Toast.LENGTH_SHORT).show();
		
		botonActualizar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {							
				Toast.makeText(getApplicationContext(), "Cargando datos del servidor", Toast.LENGTH_SHORT).show();
			}			
		});
		
		lista.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				Intent intent1 = new Intent(ListaEventos.this, PrincipalActivity.class);
         		intent1.putExtra("identificador", eventos.get(arg2).getIdevento()); 
         		startActivity(intent1);
			}			
		});
			
	
	}
	
	public Boolean actualizarEventos(){
		
		String wherePattern = "idevento NOT LIKE ?";
		String wherePatternte = "idevento LIKE ?";
		
		ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());
		try {
			eventos = dbManager.eventoDAO.search("evento", false, null, wherePattern, 
					new String[]{"00"}, null, null, null, null, null);			
			if(eventos!=null){
				List<String> eventosString = new ArrayList<String>();
				for(int i = 0; i < eventos.size(); i++){
					List<TipoEvento> tipoEventos = dbManager.tipoEventoDAO.search("tipoevento", false, null, wherePatternte, 
							new String[]{eventos.get(i).getIdevento()}, null, null, null, null, null);
					if(tipoEventos!=null){
						String seteventos = "";
						for(int j = 0; j < tipoEventos.size(); j++)
							seteventos += tipoEventos.get(j).getEvento()+" "; 
						eventosString.add(seteventos+eventos.get(i).getFecha()+" "
							    +eventos.get(i).getHora());
					}
					else{
						eventosString.add(eventos.get(i).getIdevento()+";"+eventos.get(i).getFecha()+";"
							    +eventos.get(i).getHora());								
					}					
						
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventosString);
				lista.setAdapter(adapter);
			}
			else
				return false;
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lista_eventos, menu);
		return true;
	}

}
