package com.resphere.android.util;

import java.util.List;

import com.resphere.android.modelo.Parroquia;

public class JsonParroquia extends JsonUtil<Parroquia> {

	public JsonParroquia(Class<Parroquia> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	public List<Parroquia> getParroquias(String entrada){
		return super.JsonArrayToList(entrada);
	}
	
	public Parroquia getParroquia(String entrada){
		return super.JsonToEntity(entrada);
	}
}
