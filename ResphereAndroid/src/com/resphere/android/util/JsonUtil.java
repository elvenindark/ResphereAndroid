package com.resphere.android.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;

public abstract class JsonUtil<T> {

	private Class<T> entityClass;
	private T[] array;
	private Reflection r;
	private ArrayList<String> atributos;
	
	public JsonUtil(Class<T> entityClass){
		this.entityClass = entityClass;
	}
	
	public List<T> JsonArrayToList(String entrada){
		Gson gson = new Gson();
		array=(T[])Array.newInstance(entityClass, 0);
		T[] arrayEntities = (T[]) gson.fromJson(entrada, array.getClass());
		List<T> list = Arrays.asList(arrayEntities);
		return list;
	}
	
	public T JsonToEntity(String entrada){
		Gson g = new Gson();
		return g.fromJson(entrada, entityClass);
	}
}
