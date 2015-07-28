package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.mobandme.ada.validators.ValidationResult;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.resphere.android.modelo.Evento;
import com.resphere.android.modelo.TipoEvento;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsynRespuesta;
import com.resphere.android.util.AsyncTaskSend.OnSendListener;
import com.resphere.android.util.Comunicacion;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.ItemWidget;
import com.resphere.android.util.Posicion;
import com.resphere.android.util.Preferencias;
import com.resphere.android.vista.fragment.EventoListFragment;
import com.resphere.android.vista.fragment.EventoListFragment.ListEventoListener;
import com.resphere.android.vista.fragment.FechaPickerFragment;
import com.resphere.android.vista.fragment.FechaPickerFragment.EditFechaListener;

public class EventoActivity extends FragmentActivity implements EditFechaListener, ListEventoListener, 
OnSendListener, ValidationListener, AsynRespuesta{

	private Button botonFecha;
	private Button botonEventos;
	private Button botonGuardar;
	private Button botonEnviar;
	private EditText fechaTxt;
	private ListView listview;
	private ArrayAdapter<String> adapter;
	private EditText descripcion;
	private EditText efectos;
	private EditText amenazas;
	private Evento evento;
	public Validator validator;
	private String fecha;
	private String hora;
	private String identificador;
	private ArrayList<TipoEvento> listaEventos;
	private String[] listEventos;
	private Boolean esNuevo;
	private ConfiguracionPreferencias preferencias;
	private Preferencias pref;
	private int estado;
	private String ip;
	private String port;
	private Button botonSend;
	private TextView fechaEvento;
	private TextView horaEvento;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fecha_evento);
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
		botonFecha = (Button)findViewById(R.id.btnFechaEvento);
		botonEventos = (Button)findViewById(R.id.btnEvento);
		botonGuardar = (Button)findViewById(R.id.btnGuardarEvento);
		botonEnviar = (Button)findViewById(R.id.btnEnviarEvento);
		
		botonSend = (Button)findViewById(R.id.btnSendEvento);
		fechaTxt = (EditText)findViewById(R.id.editFechaEvento);
		fechaEvento = (TextView)findViewById(R.id.txtFechaEvento);
		horaEvento = (TextView)findViewById(R.id.txtHoraEvento);
		listview = (ListView)findViewById(R.id.listEventosSeleccionados);
	
		pref = new Preferencias();
		pref.init(this);
		ip = pref.getHost();
		port = pref.getPort();
		
		if(cargarEvento()){
			Toast.makeText(this, "Cargando", Toast.LENGTH_SHORT).show();
			esNuevo = false;
		}
		else{
			Toast.makeText(this, "Nuevo", Toast.LENGTH_SHORT).show();
			esNuevo = true;
		}		
		
		botonFecha.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showFechaDialog(arg0);
			}			
		});
		
		botonEventos.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showEventoDialog(v);
			}
			
		});		
		
		botonSend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(guardarEvento()){
					if(evento!=null)
						if(sendEvento(evento)){
							Toast.makeText(getApplicationContext(), "Enviando evento", Toast.LENGTH_SHORT).show();
							if(listaEventos!=null)
								if(sendListaEventos(listaEventos))
									Toast.makeText(getApplicationContext(), "Enviando lista de eventos", Toast.LENGTH_SHORT).show();
								else
									Toast.makeText(getApplicationContext(), "No se envio información", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(getApplicationContext(), "Lista de eventos null", Toast.LENGTH_SHORT).show();
							//finish();
						}else
							Toast.makeText(getApplicationContext(), "No se envió información", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getApplicationContext(), "No se actualizó/registró información", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	private Boolean guardarEvento(){		
		evento = new Evento();
		if(esNuevo){			
			ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());			
			try {
				evento.setIdevento(identificador);
				evento.bind(EventoActivity.this, DataBinder.BINDING_UI_TO_ENTITY);				
				if(evento.validate(getApplicationContext())){							
					evento.setStatus(Entity.STATUS_NEW);
					dbManager.eventoDAO.add(evento);	
					dbManager.eventoDAO.save();
					setResult(RESULT_OK);
					Log.d("metodo guardarEvento", "despues de la validacion");
					listaEventos = new ArrayList<TipoEvento>();						
					if(listview.getCount() > 0){
						for(int j = 0; j < listEventos.length; j++){
							TipoEvento tipoevento = new TipoEvento();
							tipoevento.setEvento(listEventos[j]);
							tipoevento.setIdevento(identificador);
							listaEventos.add(tipoevento);
							Log.d("Lista de eventos", tipoevento.getEvento());
						}
						dbManager.tipoEventoDAO.addAll(listaEventos);
						dbManager.tipoEventoDAO.save();						
						setResult(RESULT_OK);
						//pref.setEvento();
						pref.setNuevo(false);
					}else{
						Toast.makeText(getApplicationContext(), "Agregue evento(s)", Toast.LENGTH_SHORT).show();
						return false;
					}					
					Toast.makeText(getApplicationContext(), "Registrando información", Toast.LENGTH_SHORT).show();										
				}else{
					List<ValidationResult> lista = evento.getValidationResult();
					 StringBuilder sb = new StringBuilder(getString(R.string.errors));
				        for (ValidationResult vr : lista) {
				            sb.append("\n");
				            sb.append(vr.getMessage());
				        }
				        Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
				}
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
				return false;
			}			
			return true;
		}else{	
			try{			
				List<Evento> eventos;
				List<TipoEvento> tipoEventos;
				String wherePattern = "idevento LIKE ?";				
				ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());
				eventos = dbManager.eventoDAO.search("evento", false, null, wherePattern, 
						new String[]{identificador}, null, null, null, null, null);
				if(eventos!=null){				
					eventos.get(0).bind(EventoActivity.this, DataBinder.BINDING_UI_TO_ENTITY);
					if(eventos.get(0).validate(getApplicationContext())){
						eventos.get(0).setStatus(Entity.STATUS_UPDATED);
						dbManager.eventoDAO.add(eventos.get(0));	
						dbManager.eventoDAO.save();
						setResult(RESULT_OK);
						
						tipoEventos = dbManager.tipoEventoDAO.search("tipoevento", false, null, wherePattern, 
								new String[]{eventos.get(0).getIdevento()}, null, null, null, null, null);				
						if(tipoEventos!=null)
							for(int i = 0; i < tipoEventos.size(); i++){
								tipoEventos.get(i).setStatus(Entity.STATUS_DELETED);
								dbManager.tipoEventoDAO.add(tipoEventos.get(i));
								dbManager.tipoEventoDAO.save();
								setResult(RESULT_OK);
							}					
						if(listview.getCount()>0){
							listaEventos = new ArrayList<TipoEvento>();							
							for(int j = 0; j < listview.getCount(); j++){
								TipoEvento tipo = new TipoEvento();
								tipo.setIdevento(identificador);
								tipo.setEvento((String) listview.getItemAtPosition(j));
								listaEventos.add(tipo);
							}						
							dbManager.tipoEventoDAO.addAll(listaEventos);
							dbManager.tipoEventoDAO.save();	
							setResult(RESULT_OK);
							//pref.setEvento();
							pref.setNuevo(false);
						}else{
							Toast.makeText(getApplicationContext(), "Agregue evento(s)", Toast.LENGTH_SHORT).show();
							return false;
						}							
					}else{
						List<ValidationResult> lista = eventos.get(0).getValidationResult();
						StringBuilder sb = new StringBuilder(getString(R.string.errors));
			            for (ValidationResult vr : lista) {
			                sb.append("\n");
			                sb.append(vr.getMessage());
			            }
			            Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
			            return false;
					}
					Toast.makeText(getApplicationContext(), "Actualizando información", Toast.LENGTH_SHORT).show();
				}
			} catch (AdaFrameworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
	
	private Boolean cargarEvento(){						
		
		ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());		
		try {
			String wherePattern = "idevento LIKE ?";
			List<Evento> eventos;
			eventos = dbManager.eventoDAO.search("evento", false, null, wherePattern, 
					new String[]{identificador}, null, null, null, null, null);		
			if(eventos!=null){						
				eventos.get(0).bind(EventoActivity.this);
				List<TipoEvento> tipoEventos = dbManager.tipoEventoDAO.search("tipoevento", false, null, wherePattern, 
						new String[]{eventos.get(0).getIdevento()}, null, null, null, null, null);
				List<String> tipoEventosString = new ArrayList<String>();
				
				if(tipoEventos!=null){
					for(int i = 0; i < tipoEventos.size(); i++)
						tipoEventosString.add(tipoEventos.get(i).getEvento());
					ArrayAdapter<String> adapterTipoEventos = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tipoEventosString);
					listview.setAdapter(adapterTipoEventos);
				}
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
	
	private Boolean sendEvento(Evento e){
		Comunicacion<Evento> mensaje = new Comunicacion<Evento>(Evento.class, "evento", ip, port);
		if(mensaje.enviarObjeto(e))
			return true;
		else 
			return false;
	}
	
	private Boolean sendListaEventos(ArrayList<TipoEvento> lista){
		Comunicacion<TipoEvento> mensaje = new Comunicacion<TipoEvento>(TipoEvento.class,"tipoevento", ip, port);
		if(mensaje.enviarListaObjetos(lista))
			return true;
		else
			return false;
	}
	
	public Boolean guardarDatos(Evento e){
		ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());
		dbManager.getEventoDAO().add(e);
		try {
			dbManager.getEventoDAO().save();
		} catch (AdaFrameworkException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean guardarDatos(List<TipoEvento> list){
		ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());
		dbManager.getTipoEventoDAO().addAll(list);
		try {
			dbManager.tipoEventoDAO.save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void showFechaDialog(View arg0) {
		// TODO Auto-generated method stub
		FechaPickerFragment dateDialog = new FechaPickerFragment();
		dateDialog.show(getSupportFragmentManager(), "Fecha");
	}

	private void showEventoDialog(View v){
		EventoListFragment eventoDialog = new EventoListFragment();
		eventoDialog.show(getSupportFragmentManager(), "evento");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fecha_evento, menu);
		return true;
	}

	@Override
	public void onFinishFechaDialog(String fecha, String hora) {
		// TODO Auto-generated method stub
		this.fecha = fecha;
		this.hora = hora;
		fechaTxt.setText(fecha+" "+hora);
		fechaEvento.setText(fecha);
		horaEvento.setText(hora);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
	}

	@Override
	public void onFinishEventoList(String[] lista) {					
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
		listview.setAdapter(adapter);
		listEventos = new String[lista.length];
		for(int i = 0; i < lista.length; i ++){
			listEventos[i] = lista[i];
		}
	}	
	
	@Override
	public void preValidation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		if(estado == 2){
			//setDataTask(evento);
			if(sendEvento(evento))
				Toast.makeText(this, "Evento creado", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this, "Problemas al enviar el evento", Toast.LENGTH_LONG).show();
			//setListTask(listaEventos);
			if(sendListaEventos(listaEventos))
					preferencias.setEventoPref("enviado");
			super.finish();
		}
		else if(estado == 1){
			
			if(guardarDatos(evento)){
				if(guardarDatos(listaEventos)){
					Toast.makeText(this, "Evento creado", Toast.LENGTH_SHORT).show();
					preferencias.setEventoPref("guardado");			
					super.finish();
				}
			}else{
				Toast.makeText(this, "Problemas al guardar el evento", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onFailure(View failedView, Rule<?> failedRule) {
		// TODO Auto-generated method stub
		if (failedView instanceof TextView) {
		       TextView view = (TextView) failedView;
		       view.requestFocus();
			   view.setError(failedRule.getFailureMessage());        
			   Toast.makeText(this, failedRule.getFailureMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onValidationCancelled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void response(Boolean respuesta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resul(Boolean value) {
		// TODO Auto-generated method stub
		/*if(value){
			Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
		}*/
	}

	@Override
	public void resul(Posicion value) {
		// TODO Auto-generated method stub
		
	}
}
