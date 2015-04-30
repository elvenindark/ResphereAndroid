package com.resphere.android.util;

import java.util.List;

import com.resphere.android.modelo.Provincia;

public class JsonProvincia extends JsonUtil<Provincia> {

	public JsonProvincia(Class<Provincia> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	public Provincia getProvincia(String entrada){
		return super.JsonToEntity(entrada);
	}
	
	public List<Provincia> getProvincias(String entrada){
		return super.JsonArrayToList(entrada);
	}
	
}
