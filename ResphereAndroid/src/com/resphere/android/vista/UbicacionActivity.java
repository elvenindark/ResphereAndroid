package com.resphere.android.vista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.resphere.android.modelo.Canton;
import com.resphere.android.modelo.Parroquia;
import com.resphere.android.modelo.Provincia;
import com.resphere.android.modelo.Ubicacion;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsynRespuesta;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.Comunicacion;
import com.resphere.android.util.GPSTask;
import com.resphere.android.util.ItemWidget;
import com.resphere.android.util.LayoutActivity;
import com.resphere.android.util.Posicion;
import com.resphere.android.util.Preferencias;
import com.resphere.android.util.Reflection;
import com.resphere.android.util.UbicacionTask;
//import com.mobandme.ada.Entity;

public class UbicacionActivity extends LayoutActivity implements OnClickListener, AsynRespuesta, ValidationListener{

	private String ip;
	private String port;
	Ubicacion ubicacion;
	
	//@Required(order = 6, message = "No es una sector valido")
	//@TextRule(order =7, minLength = 3, maxLength = 30, message = "Introduce entre 6 a 30 caracteres")
	//@ItemWidget(className = EditText.class, identifier = R.id.txtSector)
	public EditText sector;
	//@Required(order = 8, message = "No es una distancia valida")
	//@TextRule(order =9, minLength = 1, maxLength = 3, message = "Introduce entre 1 a 3 caracteres")
	//@ItemWidget(className = EditText.class, identifier = R.id.txtDistancia)
	public EditText distancia;
	//@Required(order = 10, message = "No es un tiempo valido")
	//@TextRule(order =12, minLength = 1, maxLength = 3, message = "Introduce entre 1 a 3 caracteres")
	//@ItemWidget(className = EditText.class, identifier = R.id.txtTiempo)
	public EditText tiempo;
	//@Required(order = 13, message = "No es un punto valido")
	//@TextRule(order =14, minLength = 3, maxLength = 30, message = "Introduce entre 3 a 30 caracteres")
	//@ItemWidget(className = EditText.class, identifier = R.id.txtReferencia)
	public EditText referencia;
	//@Required(order = 15, message = "No es una canton valida")
	//@TextRule(order =16, minLength = 3, maxLength = 30, message = "Introduce entre 3 a 30 caracteres")
	//@ItemWidget(className = EditText.class, identifier = R.id.txtX)
	public EditText latitud;
	//@Required(order = 17, message = "No es una canton valida")
	//@TextRule(order =18, minLength = 3, maxLength = 20, message = "Introduce entre 6 a 20 caracteres")
	//@ItemWidget(className = EditText.class, identifier = R.id.txtY)
	public EditText longitud;
	//@Required(order = 19, message = "No es una canton valida")
	//@TextRule(order =20, minLength = 3, maxLength = 20, message = "Introduce entre 3 a 20 caracteres")
	//@ItemWidget(className = EditText.class, identifier = R.id.txtAltitud)
	public EditText altitud;
	
	private TextView tipoParroquia;
	
	Button gps;
	Button guardar;
	Button enviar;
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	Button posicion;
	Spinner spProvincia;
	Spinner spCanton;
	Boolean value = null;
	public Validator validator;
	private int estado = 0;
	
	private String codigo = "01";
	private String codigoC = "01";
	
	public String getCodigoC() {
		return codigoC;
	}

	public void setCodigoC(String codigoC) {
		this.codigoC = codigoC;
	}

	ApplicationDataContext dbManager;
	ApplicationDataContext ubicacionDAO;
	
	private String identificador;
	
	private int idProvincia;
	private Spinner spParroquia;
	
	private Boolean esNuevo;
	private Button actualizar;
	private Button send;
	private Preferencias pref;
	
	@Override
    protected int getLayout(){return R.layout.activity_ubicacion_geografica;}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_informacion_general);
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
		
		validator =  new Validator(this);
		validator.setValidationListener(this);		
		//Obtenemos la ip from datos.xml
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		ip = prefe.getString("ip", "");
		port = prefe.getString("port", "");
		pref = new Preferencias();
		pref.init(this);
		
		gps = (Button)findViewById(R.id.btnGPS);		
		send = (Button)findViewById(R.id.btnEnviarUbicacion);			
		spProvincia = (Spinner)findViewById(R.id.spinnerProvincia);
		spCanton = (Spinner)findViewById(R.id.spinnerCanton);
		spParroquia = (Spinner)findViewById(R.id.spinnerParroquia);
		tipoParroquia = (TextView)findViewById(R.id.textTipoParroquia);
		latitud = (EditText)findViewById(R.id.txtX);
		longitud = (EditText)findViewById(R.id.txtY);
		altitud = (EditText)findViewById(R.id.txtAltitud);
		
		dbManager = DataContext.getInstance(this);
		String wherePattern = "idprovincia NOT LIKE ?";
		ubicacion = new Ubicacion();
		
		if(cargarUbicacion()){
			esNuevo = false;
			Toast.makeText(this, "Actualizando información", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "Nuevo registro", Toast.LENGTH_SHORT).show();
			esNuevo = true;
		}		
		
		try {
			List<Provincia> provincias = dbManager.provinciaDAO.search("provincia", false, null, wherePattern, new String[]{"00"}, null, null, null, null, null);			
			List<String> provinciasArray = new ArrayList<String>();			
			for(int i = 0; i < provincias.size(); i++){
				provinciasArray.add(provincias.get(i).getProvincia());				
			}			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinciasArray);		    
		    spProvincia.setAdapter(dataAdapter);
		    if(!esNuevo)
		    	spProvincia.setSelection(provinciasArray.indexOf(ubicacion.getProvincia()));
		    spProvincia.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					idProvincia = spProvincia.getSelectedItemPosition() + 1;
					if(idProvincia<10){
						setCodigo('0'+String.valueOf(idProvincia));
					}else{
						setCodigo(String.valueOf(idProvincia));
					}
					Log.d("Id Provincia seleccionada", getCodigo());
					String wherePatternC = "idprovincia = ?";						
					List<Canton> cantones;
					try {
						cantones = dbManager.cantonDAO.search("canton", false, null, wherePatternC, new String[]{getCodigo()}, null, null, null, null, null);
						List<String> cantonesArray = new ArrayList<String>();						
						for(int i = 0; i < cantones.size(); i++){
							cantonesArray.add(cantones.get(i).getCanton());
						}
						ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(UbicacionActivity.this, android.R.layout.simple_spinner_item, cantonesArray);						
						spCanton.setAdapter(dataAdapter);		
						if(!esNuevo)
							spCanton.setSelection(cantonesArray.indexOf(ubicacion.getCanton()));
						spCanton.setOnItemSelectedListener(new OnItemSelectedListener(){	
							@Override
							public void onItemSelected(AdapterView<?> arg0,	View arg1, int arg2, long arg3) {
								idProvincia = spCanton.getSelectedItemPosition() + 1;
								if(idProvincia<10)
									setCodigoC('0'+String.valueOf(idProvincia));
								else
									setCodigoC(String.valueOf(idProvincia));								
								Log.d("Id Provincia seleccionada", getCodigo());
								Log.d("Id Canton seleccionado", getCodigoC());
								ubicacion.setCanton((String) spCanton.getItemAtPosition(arg2));								
								String wherePatternC = "idprovincia = ?";
								String wherePatternP = "idcanton = ?";								
								final List<Parroquia> parroquias;
								try {
									parroquias = dbManager.parroquiaDAO.search("parroquia", false, null, wherePatternC +" AND "+ wherePatternP, new String[]{getCodigo(), getCodigoC()}, null, null, null, null, null);
									List<String> parroquiasArray = new ArrayList<String>();
									
									for(int i = 0; i < parroquias.size(); i++){
										parroquiasArray.add(parroquias.get(i).getParroquia());
									}
										ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(UbicacionActivity.this, android.R.layout.simple_spinner_item, parroquiasArray);											
										spParroquia.setAdapter(dataAdapter);	
										if(!esNuevo)
											spParroquia.setSelection(parroquiasArray.indexOf(ubicacion.getParroquia()));
										spParroquia.setOnItemSelectedListener(new OnItemSelectedListener(){

											@Override
											public void onItemSelected(	AdapterView<?> arg0,
													View arg1, int arg2, long arg3) {
												String parroquia = null;
												parroquia = (String)spParroquia.getItemAtPosition(arg2);
												ubicacion.setParroquia(parroquia);
												for(Parroquia item : parroquias){
													if(item.getParroquia().equals(parroquia)){
														if(Integer.valueOf(item.getIdparroquia())<50){
															tipoParroquia.setText("urbana");
														}else{
															tipoParroquia.setText("rural");
														}
													}
												}
											}

											@Override
											public void onNothingSelected(
													AdapterView<?> arg0) {												
											}
											
										});
									}catch (AdaFrameworkException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
						}
						@Override
						public void onNothingSelected(AdapterView<?> arg0) {									
							}								
						});						
						
					} catch (AdaFrameworkException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub					
				}		    	
		    });
					
		} catch (AdaFrameworkException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ubicacionDAO = DataContext.getInstance(this);		
		
		//guardar.setOnClickListener(this);	
		gps.setOnClickListener(this);
		//enviar.setOnClickListener(this);		
			
		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(saveUbicacion()){
					Toast.makeText(getApplicationContext(), "Registrando información", Toast.LENGTH_SHORT).show();
					if(ubicacion!=null){
						if(sendUbicacion(ubicacion))
							Toast.makeText(getApplicationContext(), "Enviando información", Toast.LENGTH_SHORT).show();
						else
							Toast.makeText(getApplicationContext(), "Problemas al enviar información, intente mas tarde", Toast.LENGTH_SHORT).show();
					}else
						Toast.makeText(getApplicationContext(), "Ubicacion null", Toast.LENGTH_SHORT).show();
				}else
					Toast.makeText(getApplicationContext(), "Problemas con la información", Toast.LENGTH_SHORT).show();				
			}			
		});
	}
	
	private Boolean sendUbicacion(Ubicacion u){
		Comunicacion<Ubicacion> mensaje = new Comunicacion<Ubicacion>(Ubicacion.class,"ubicacion", ip, port);
		if(mensaje.enviarObjeto(u))
			return true;
		else
			return false;
	}
	
	private Boolean saveUbicacion(){
		if(esNuevo){
			ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());
			
			try {
				ubicacion.setIdubicacion(String.valueOf(System.currentTimeMillis()/1000));
				ubicacion.setIdevento(identificador);
				ubicacion.setProvincia((String) spProvincia.getSelectedItem());
				ubicacion.setCanton((String) spCanton.getSelectedItem());
				ubicacion.setParroquia((String) spParroquia.getSelectedItem());
				ubicacion.setLatitud(latitud.getText().toString());
				ubicacion.setLongitud(longitud.getText().toString());
				ubicacion.setAltitud(altitud.getText().toString());
				Log.d("Provincia: ", ubicacion.getProvincia());
				ubicacion.bind(UbicacionActivity.this, DataBinder.BINDING_UI_TO_ENTITY);				
				if(ubicacion.validate(getApplicationContext())){
					ubicacion.setStatus(Entity.STATUS_NEW);
					dbManager.ubicacionDAO.add(ubicacion);
					dbManager.ubicacionDAO.save();
					setResult(RESULT_OK);					
				}else{
					List<ValidationResult> lista = ubicacion.getValidationResult();
					 StringBuilder sb = new StringBuilder(getString(R.string.errors));
				        for (ValidationResult vr : lista) {
				            sb.append("\n");
				            sb.append(vr.getMessage());
				        }
				        Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
				        return false;
				}				
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
				return false;
			}				
		}else{
			List<Ubicacion> ubicaciones;
			String wherePattern = "idevento LIKE ?";
			ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());
			try {
				ubicaciones = dbManager.ubicacionDAO.search("ubicacion", false, null, wherePattern, 
						new String[]{identificador}, null, null, null, null, null);
				if(ubicaciones!=null){				
					ubicaciones.get(0).bind(UbicacionActivity.this, DataBinder.BINDING_UI_TO_ENTITY);
					if(ubicaciones.get(0).validate(getApplicationContext())){
						ubicacion = ubicaciones.get(0);
						ubicaciones.get(0).setStatus(Entity.STATUS_UPDATED);
						dbManager.ubicacionDAO.add(ubicaciones.get(0));
						dbManager.ubicacionDAO.save();
						setResult(RESULT_OK);
					}else{
						List<ValidationResult> lista = ubicacion.getValidationResult();
						StringBuilder sb = new StringBuilder(getString(R.string.errors));
			            for (ValidationResult vr : lista) {
			                sb.append("\n");
			                sb.append(vr.getMessage());
			            }
			            Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
			            return false;
					}
				}
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	private Boolean cargarUbicacion(){
		ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());		
		try {
			String wherePattern = "idevento LIKE ?";
			List<Ubicacion> ubicaciones;
			ubicaciones = dbManager.ubicacionDAO.search("ubicacion", false, null, wherePattern, 
					new String[]{identificador}, null, null, null, null, null);		
			if(ubicaciones!=null){	
				ubicacion = ubicaciones.get(0);
				ubicaciones.get(0).bind(UbicacionActivity.this);
				Toast.makeText(getApplicationContext(), ubicaciones.get(0).getParroquia(), Toast.LENGTH_SHORT).show();
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
		getMenuInflater().inflate(R.menu.activity_informacion_general, menu);
		return true;
	}
	
	public Boolean getDatos(){
		ubicacion = new Ubicacion();
		ubicacion.setProvincia(spProvincia.getSelectedItem().toString());		
		ubicacion.setCanton(spCanton.getSelectedItem().toString());
		ubicacion.setParroquia(spParroquia.getSelectedItem().toString());
		ubicacion.setTipo(tipoParroquia.getText().toString());
		ubicacion.setSector(sector.getText().toString());
		ubicacion.setDistancia(distancia.getText().toString());
		ubicacion.setTiempo(tiempo.getText().toString());
		ubicacion.setReferencia(referencia.getText().toString());
		//ubicacion.setLatitud(latitud.getText().toString());
		//ubicacion.setLongitud(longitud.getText().toString());
		//ubicacion.setAltitud(altitud.getText().toString());
		ubicacion.setIdubicacion(String.valueOf(System.currentTimeMillis()/1000));
		ubicacion.setIdevento(identificador);
		return true;
		//Toast.makeText(this, ubicacion.getProvincia(), Toast.LENGTH_SHORT).show();
	}
	
	public void enviarUbicacion(){			
			if(ip == null || ip == ""){
				Log.e("ip null","null");
				return;
			}
			else{
				Log.e("ip before send",ip);
				UbicacionTask rest = new UbicacionTask(ip,port);
				rest.execute(ubicacion);
				rest.response = this;
				Toast.makeText(this, "Ubicacion enviada correctamente", Toast.LENGTH_SHORT).show();
				finish();
			}
	}
	
	//Se envia el objeto para obtener los atributos, metodos y valores del objeto enviado
	//mediante reflection con el nombre de la clase
	private void setDataTask(Ubicacion ub){
		Class clazz;
		//clazz = Class.forName("com.resphere.android.modelo.Evento");
		
		clazz = ub.getClass();
		Reflection clase = new Reflection(clazz);
		
		SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE);
		ip = prefe.getString("ip", "");
		port = prefe.getString("port", "");
				
		ArrayList<String> atributos = new ArrayList<String>();
		ArrayList<String> getters = new ArrayList<String>();		
		ArrayList<String> values = new ArrayList<String>();
		
		//obtener
		values = (ArrayList) clase.returnDatos(ub);		
		atributos = (ArrayList) clase.returnFields().clone();
		getters = (ArrayList) clase.returnGetters().clone();				
		
		AsyncTaskSend enviar = new AsyncTaskSend(ip,port,"ubicacion");
		enviar.execute(atributos, values);
		
	}
	
	public void guardarUbicacion(){		
							
			try {					
				
				ubicacionDAO.getUbicacionDAO().add(ubicacion);
				ubicacionDAO.getUbicacionDAO().save();
				Toast.makeText(this, "Ubicacion guardada correctamente", Toast.LENGTH_SHORT).show();
				finish();
			
			} catch (AdaFrameworkException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0 == enviar){
			estado = 2;
			if(getDatos())	
				validator.validate();				
		}
		if(arg0 == gps){			
			GPSTask gpst = new GPSTask(this);
			gpst.execute();
			gpst.response = this;
		}
		if(arg0 == guardar){
			estado = 1;
			if(getDatos())
				validator.validate();						
		}
		if(arg0 == posicion){
			estado = 3;
			//Insertar llamada al fragment
			validator.validate();						
		}
	}
	

	@Override
	public void resul(Boolean respuesta) {
		// TODO Auto-generated method stub
		value = respuesta;
	}

	@Override
	public void resul(Posicion value) {
		// TODO Auto-generated method stub
			if(value != null){
				//ApplicationDataContext dbManager = DataContext.getInstance(getApplicationContext());
				ubicacion.setLatitud(value.getLatitud());
				ubicacion.setLongitud(value.getLongitud());
				ubicacion.setAltitud(value.getAltitud());
				//ubicacion.bind(UbicacionActivity.this, DataBinder.BINDING_UI_TO_ENTITY);
				latitud.setText(value.getLatitud());
				longitud.setText(value.getLongitud());
				altitud.setText(value.getAltitud());
			}
			else{				
				
				Toast.makeText(getApplicationContext(), "posicion null", Toast.LENGTH_SHORT).show();
			}	
	}

	@Override
	public void preValidation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Validado correctamente", Toast.LENGTH_LONG).show();
		switch(estado){
		case 1: guardarUbicacion();
		break;
		case 2: setDataTask(ubicacion);
			//enviarUbicacion();
			Toast.makeText(this, "Ubicacion enviada correctamente", Toast.LENGTH_SHORT).show();
			finish();
		break;
		default: break;
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
	public void finish() {
	 
	  super.finish();
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();	    
	}
	
}
