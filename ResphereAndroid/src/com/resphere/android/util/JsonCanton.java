package com.resphere.android.util;

import java.util.List;

import com.resphere.android.modelo.Canton;


public class JsonCanton extends JsonUtil<Canton> {
	
	public JsonCanton(Class<Canton> entityClass){
		super (entityClass);
	}
	
	public Canton getCanton(String entrada){
		return super.JsonToEntity(entrada);
	}
	
	public List<Canton> getCantones(String entrada){
		return super.JsonArrayToList(entrada);
	}

}
