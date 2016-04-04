package com.resphere.android.vista;

import com.resphere.android.modelo.Respuesta;
import com.resphere.android.util.Comunicacion;
import com.resphere.android.util.Preferencias;
import com.resphere.server.model.Monitoreorespuesta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MonitoreoRespuestaDetalle extends Activity {

	private String indicador;
	private int idrespuesta;
	private TextView txtIndicador;
	private Button btnEnviarRespuesta;
	private Button btnGuardarRepuesta;
	private EditText editObservaciones;
	private String ip;
	private String port;
	private Preferencias pref;
	private Monitoreorespuesta monitoreo;
	private RadioGroup radioRespuesta;
	private RadioButton radioButton;
	private Respuesta respuesta;
	private String idevento;
	private String idindicador;
	private String observacion;
	private TextView txtObservaciones;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoreo_respuesta_detalle);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			indicador = (String) extras.get("indicador");
			idrespuesta = (Integer) extras.get("idrespuesta");
			idevento = (String) extras.get("idevento");
			idindicador = (String) extras.get("idindicador");
			observacion = (String) extras.get("observacion");
		}
		
		init();
		txtIndicador.setText(indicador);
		txtObservaciones.setText(observacion);
		pref = new Preferencias();
		pref.init(this);
		ip = pref.getHost();
		port = pref.getPort();
		btnGuardarRepuesta.setVisibility(View.GONE);
		btnEnviarRespuesta.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getMonitoreo();
				if(sendMonitoreo(monitoreo)){
					Log.d("Monitoreo observacion", monitoreo.getObservacion());
					Toast.makeText(getApplicationContext(), "Se envió información", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "No se actualizó/registró información", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
	}
	
	public void init(){
		txtIndicador = (TextView)this.findViewById(R.id.textIndicadorM);
		btnEnviarRespuesta = (Button)this.findViewById(R.id.btnEnviarMonitoreo);
		btnGuardarRepuesta = (Button)this.findViewById(R.id.btnGuardarMonitoreo);
		editObservaciones = (EditText)this.findViewById(R.id.editObservacionMonitoreo);
		radioRespuesta = (RadioGroup)this.findViewById(R.id.radioGroupMonitoreo);
		txtObservaciones = (TextView)this.findViewById(R.id.txtObservacionM);
	}
	
	private Boolean saveRespuesta(Respuesta e){
		return false;
	}
	
	private Boolean sendMonitoreo(Monitoreorespuesta e){
		Comunicacion<Monitoreorespuesta> mensaje = new Comunicacion<Monitoreorespuesta>(Monitoreorespuesta.class, "monitoreorespuesta", ip, port);
		if(mensaje.enviarObjeto(e))
			return true;
		else
			return false;
	}
	
	private Boolean getMonitoreo(){
		monitoreo = new Monitoreorespuesta();		
		
		if( editObservaciones.getText().toString()!=null){
			int selected = radioRespuesta.getCheckedRadioButtonId();
			radioButton = (RadioButton)findViewById(selected);
			monitoreo.setIdrespuesta(Integer.valueOf(idrespuesta).intValue());
			monitoreo.setIdrespuesta(idrespuesta);
			monitoreo.setCumple(radioButton.getText().toString());
			monitoreo.setObservacion(editObservaciones.getText().toString());			
			
			return true;
		}else
			return false;
	}

}
