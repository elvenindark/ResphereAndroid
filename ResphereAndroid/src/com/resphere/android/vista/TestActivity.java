package com.resphere.android.vista;

import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Preferencias;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class TestActivity extends Activity {

	private WebView webView;
	private Button button;
	private Preferencias preferencias;
	private ConfiguracionPreferencias pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		preferencias = new Preferencias();
		pref = new ConfiguracionPreferencias(this);
		preferencias.init(getBaseContext());
		if(!pref.isIpPortPref()){
			Toast.makeText(getApplicationContext(), "Preparando para usar por primera vez", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(TestActivity.this, ConfiguracionActivity.class);	 		
			startActivity(intent);
		}
		
		webView = (WebView) findViewById(R.id.webViewRegistro);
		button = (Button)findViewById(R.id.btnLoginActivity);
		webView.getSettings().setJavaScriptEnabled(true);		
		webView.loadUrl("http://"+pref.getIpPref()+":8000/web/guest/welcome?p_p_id=58&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&saveLastPath=false&_58_struts_action=%2Flogin%2Fcreate_account");
		button.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent14 = new Intent(TestActivity.this, LoginActivity.class);            		
         		startActivity(intent14);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}

}
