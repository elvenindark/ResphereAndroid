package com.resphere.android.vista;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Impacto;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.AsyncTaskSend;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Reflection;

public class ImpactoEventoActivity extends Activity {

	private RadioGroup impacto;
	private Button guardar;
	private Button enviar;
	private RadioButton checked;
	private RadioButton nivel1;
	private RadioButton nivel2;
	private RadioButton nivel3;
	private RadioButton nivel4;

	private Impacto imp;
	private String tipo;
	private ConfiguracionPreferencias preferencias;
	private String identificador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impacto_evento);
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		// Get data via the key
		identificador = extras.getString("identificador");
		if (identificador != null) {
			// do something with the data
			// Toast.makeText(this, identificador, Toast.LENGTH_SHORT).show();
		}

		preferencias = new ConfiguracionPreferencias(this);

		getIU();

		guardar.setVisibility(android.view.View.INVISIBLE);

		int buttonCheckedId = impacto.getCheckedRadioButtonId();
		if (buttonCheckedId != -1) {
			if ((RadioButton) findViewById(impacto.getCheckedRadioButtonId()) != null) {
				checked = (RadioButton) findViewById(impacto
						.getCheckedRadioButtonId());
				tipo = checked.getText().toString();
				if (tipo.startsWith("Nivel 1"))
					tipo = "0";
				if (tipo.startsWith("Nivel 2"))
					tipo = "1";
				if (tipo.startsWith("Nivel 3"))
					tipo = "2";
				if (tipo.startsWith("Nivel 4"))
					tipo = "4";
			}
		}

		impacto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int arg1) {
				checked = (RadioButton) group.findViewById(impacto
						.getCheckedRadioButtonId());
				//String tipo;
				tipo = checked.getText().toString();
				switch(arg1){
				case R.id.radNivel1:					
						tipo = "0";
						break;
				case R.id.radNivel2:					
					tipo = "1";
					break;
				case R.id.radNivel3:					
					tipo = "2";
					break;
				case R.id.radNivel4:					
					tipo = "3";
					break;					
				
				}
			}
		});

		guardar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getDatos();
				if (guardarImpacto(imp))
					Toast.makeText(getApplicationContext(),
							"Impacto guardado correctamente",
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(),
							"Problemas al guardar impacto", Toast.LENGTH_SHORT)
							.show();
				finish();
			}

		});

		enviar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDatos();
				if (enviarImpacto(imp))
					Toast.makeText(getApplicationContext(),
							"Impacto enviado correctamente", Toast.LENGTH_SHORT)
							.show();
				else
					Toast.makeText(getApplicationContext(),
							"Problemas al enviar impacto", Toast.LENGTH_SHORT)
							.show();
				finish();
			}

		});

	}

	public Boolean enviarImpacto(Impacto i) {
		Class clazz;
		clazz = i.getClass();
		Reflection clase = new Reflection(clazz);

		guardarImpacto(i);

		ArrayList<String> atributos = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		// obtener
		values = (ArrayList) clase.returnDatos(i);
		atributos = (ArrayList) clase.returnFields().clone();

		Log.d("ipport",
				preferencias.getIpPref() + " " + preferencias.getPortPref());
		AsyncTaskSend enviar = new AsyncTaskSend(preferencias.getIpPref(),
				preferencias.getPortPref(), "impacto");
		enviar.execute(atributos, values);
		return true;
	}

	public Boolean guardarImpacto(Impacto i) {
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getImpactoDAO().add(i);
		try {
			dbManager.getImpactoDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void getDatos() {
		imp = new Impacto();
		imp.setIdevento(identificador);
		imp.setIdtipoimpacto(tipo);
	}

	public void getIU() {
		impacto = (RadioGroup) findViewById(R.id.radioImpacto);
		guardar = (Button) findViewById(R.id.btnGuardarImpacto);
		enviar = (Button) findViewById(R.id.btnEnviarImpacto);
		nivel1 = (RadioButton) findViewById(R.id.radNivel1);
		nivel2 = (RadioButton) findViewById(R.id.radNivel2);
		nivel3 = (RadioButton) findViewById(R.id.radNivel3);
		nivel4 = (RadioButton) findViewById(R.id.radNivel4);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_impacto_evento, menu);
		return true;
	}

}
