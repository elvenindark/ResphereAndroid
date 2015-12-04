package com.resphere.android.vista;


import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.group.GroupService;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Preferencias;
import com.resphere.android.vista.servicio.UbicacionDAO;


public class ConfiguracionActivity extends Activity {

	private EditText ip;
	private EditText port;	
	private ConfiguracionPreferencias preferencias;
	private EditText login;
	private EditText pass;
	private Preferencias pref;
	protected int progressStatus;
	protected ProgressDialog progressGR;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        
        preferencias = new ConfiguracionPreferencias(this);
        pref = new Preferencias();
        pref.init(getBaseContext());
        progressStatus = 0;
        if(pref.getLoadUbicacion()){
        	setProgress(this);
        	Log.d("ubicacion", "cargando ubicaciones on create");
        	loadUbicacion(getBaseContext());
        }else{
        	Log.d("ubicacion", "ubicacion getted");
        }
        
        ip = (EditText)findViewById(R.id.editIP);
        port = (EditText)findViewById(R.id.editPort);
        Button guardarIPPort = (Button)findViewById(R.id.btnGuardarIPPort);      
                   
        ip.setText(preferencias.getIpPref());
        port.setText(preferencias.getPortPref()); 
       
        if(!preferencias.isIpPortPref())
        	Toast.makeText(getApplicationContext(), "Ingrese IP y puerto validos (ip o host)", Toast.LENGTH_SHORT).show();
        else{
	        ip.setText(preferencias.getIpPref());
	        port.setText(preferencias.getPortPref());
        }
        
        guardarIPPort.setOnClickListener(new OnClickListener(){	
			@Override
			public void onClick(View arg0) {
				if(!ip.getText().equals("") && !port.getText().equals("")){
					preferencias.setIpPuertoPref(ip.getText().toString(), port.getText().toString());
					pref.setHost(ip.getText().toString());
					pref.setPort(port.getText().toString());
					pref.setFirstUse();
					Toast.makeText(getApplicationContext(), "IP y Puerto guardados correctamente", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "problemas con IP y/o Puerto", Toast.LENGTH_SHORT).show();
				}
		        //finish();
			}        	
        });
        
       	      
	        
	        /*IntentFilter filter = new IntentFilter();
	        filter.addAction(UbicacionService.ACTION_PROGRESO);        
	        filter.addAction(UbicacionService.ACTION_FIN);
	        ProgressReceiver rcv = new ProgressReceiver();
	        registerReceiver(rcv, filter);  */     
        
    }
    
    public void setProgress(Context context){
    	Log.d("ubicacion", "cargando dialogo");
		progressGR = new ProgressDialog(context);
		progressGR.setCancelable(true);
		progressGR.setMessage("Cargando ubicaciones");
		progressGR.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressGR.setProgress(0);
		progressGR.setMax(100);
		progressGR.show();
	}
    
    public void loadUbicacion(final Context context){
 
    	Log.d("ubicacion", "antes del hilo");
     	new Thread(new Runnable() {		
			public void run() {
				// TODO Auto-generated method stub
				Log.d("ubicacion", "entrando en hilo");
				progressStatus = loadUbicaciones(context);
				if(progressStatus == -1)
					  Log.d("progressStatus", "no hay datos de comunicacion");
				  else if (progressStatus == 100)
					  Log.d("getting Resumen respuestas", "completado");				  				  
				  progressGR.dismiss();
			}    		
    	}).start();
    }
    
    private int loadUbicaciones(Context context){
    	Log.d("ubicacion", "obteniendo el servicio");
    	UbicacionDAO ubicacion = new UbicacionDAO(context);
    	if(ubicacion.loadUbicacion()){
    		pref.setLoadUbicacion();
    		return 100;
    	}else
    		return -1;
    }

    protected Boolean checkLogin() {
    	if(preferencias.isIpPortPref())
    		if(login.getText().toString()!=null && !login.getText().toString().equals(""))
    			if(pass.getText().toString()!=null && !pass.getText().toString().equals(""))
    				return true;
    			else
    				return false;
    		else
    			return false;
    	else
    		return false;
		
	}

	protected Boolean getLogin(Context context) {
		// TODO Auto-generated method stub
		String server = "http://" + ip.getText().toString() + ":" + port.getText().toString();
		//String login = "";
		//String password = "";
		Log.d("host ", server);    	
		pref.init(context);
		pref.setServer(server);
		pref.setLogin(login.getText().toString());
		pref.setPassword(pass.getText().toString());
		Session session = Preferencias.getSession();
		if(session!=null&&!session.getUsername().equals("")){
			try {
				Long groupid = getGuestGroupId(session);
				Log.d("group id", String.valueOf(groupid));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			Log.d("username", session.getUsername());
			return true;
		}			
		return false;
	}
	
	protected long getGuestGroupId(Session session) throws Exception {
		long groupId = -1;

		GroupService groupService = new GroupService(session);
		
		JSONArray groups = groupService.getUserSites();
		
		for (int i = 0; i < groups.length(); i++) {
			JSONObject group = groups.getJSONObject(i);

			String name = group.getString("name");
			
			if (!name.equals("Guest")) {
				continue;
			}

			groupId = group.getLong("groupId");
		}
		
		if (groupId == -1) {
			throw new Exception("Couldn't find Guest group.");
		}
		
		return groupId;
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_configuracion, menu);
        return true;
    }
    
    /*public class ProgressReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(ConfiguracionActivity.class.getName(),"ProgressReceiver onReceive");
			if(intent.getAction().equals(UbicacionService.ACTION_PROGRESO)) {
				int prog = intent.getIntExtra("progreso", 0);
				pbarProgreso.setProgress(prog);
			}
			else if(intent.getAction().equals(UbicacionService.ACTION_FIN)) {
				Toast.makeText(ConfiguracionActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
				preferencias.setUbicacionesPref("cargado");	    
				//finish();
			}
		}
	}*/
    
}
