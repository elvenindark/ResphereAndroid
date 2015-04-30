package com.resphere.android.util;

import java.util.prefs.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;

public class Preferencias {
	
	public static final String LOGIN = "login";

	public static final String PASSWORD = "password";

	public static final String SERVER = "server";
	
	public static final String HOST = "host";
	
	public static final String PORT = "port";
	
	public static final String PORTL = "portl";
	
	public static final String FIRST_USE = "first_use";
	
	public static final String UBICACION = "ubicacion"; 
	
	public static final String PROVINCIA = "provincias.txt";
	
	public static final String CANTON = "cantones.txt";
	
	public static final String PARROQUIA = "parroquias.txt";
	
	public static final String INTERNET = "internet";
	
	private static SharedPreferences _preferences;
	
	public static String getLogin() {	
		if(_preferences.contains(LOGIN))
			return _preferences.getString(LOGIN, "");
		else
			return null;
	}
	
	public static String getPassword() {
		if(_preferences.contains(PASSWORD))
			return _preferences.getString(PASSWORD, "");
		else
			return null;
	}
	
	public static String getServer() {
		if(_preferences.contains(SERVER))
			return _preferences.getString(SERVER, "");
		else
			return null;
	}
	
	public static String getHost(){
		if(_preferences.contains(HOST))
			return _preferences.getString(HOST, "");
		else
			return null;
	}
	
	public static String getPort(){
		if(_preferences.contains(PORT))
			return _preferences.getString(PORT, "");
		else
			return null;
	}
	
	public static String getPortL(){
		if(_preferences.contains(PORTL))
			return _preferences.getString(PORTL, "");
		else
			return null;
	}
	
	public static Boolean getFirstUse(){
		try
		{
			if(_preferences.contains(FIRST_USE))
				if(_preferences.getBoolean(FIRST_USE, true))
					return true;
				else	
					return false;
			else
				return true;
		}
		catch(Exception e){
			return true;
		}
	}
	
	public static Boolean getInternetAllow(){
		try	{
			if(_preferences.contains(INTERNET))
				if(_preferences.getBoolean(INTERNET, true))
					return true;
				else
					return false;
			else
				return true;
		}catch(Exception e){
			return true;
		}
	}
	
	public static Boolean getLoadUbicacion(){
		try
		{
			if(_preferences.contains(UBICACION))
				if(_preferences.getBoolean(UBICACION, true))
					return true;
				else
					return false;
			else
				return true;
		}
		catch(Exception e){
			return true;
		}
	}
	
	public static void init(Context context) {
		_preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static Session getSession() {
		return new SessionImpl(getServer(), getLogin(), getPassword());
		//return new SessionImpl(getServer(), new BasicAuthentication(getLogin(), getPassword()));
	}
	
	public void setServer(String server){
		Editor editor = _preferences.edit();
		editor.putString(SERVER, server);
		editor.commit();
	}
	
	public void setLogin(String login){
		Editor editor = _preferences.edit();
		editor.putString(LOGIN, login);
		editor.commit();
	}
	
	public void setPassword(String password){
		Editor editor = _preferences.edit();
		editor.putString(PASSWORD, password);
		editor.commit();
	}
	
	public void setHost(String host){
		Editor editor = _preferences.edit();
		editor.putString(HOST, host);
		editor.commit();
	}
	
	public void setPort(String port){
		Editor editor = _preferences.edit();
		editor.putString(PORT, port);
		editor.commit();
	}
	
	public void setPortL(String portl){
		Editor editor = _preferences.edit();
		editor.putString(PORTL, portl);
		editor.commit();
	}

	public void setFirstUse(){
		Editor editor = _preferences.edit();
		editor.putBoolean(FIRST_USE, false).commit();
	}
	
	public void setInternetAllow(Boolean flag){
		Editor editor = _preferences.edit();
		editor.putBoolean(INTERNET, flag).commit();
	}
	
	public void setLoadUbicacion(){
		Editor editor = _preferences.edit();
		editor.putBoolean(UBICACION, false).commit();
	}
	
	public String getProvincia(){
		return PROVINCIA;
	}
	
	public String getParroquia(){
		return PARROQUIA;
	}
	
	public String getCanton(){
		return CANTON;
	}
}
