package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="monitoreo")
public class Monitoreo extends Entity{

	@TableField(name ="idrespuesta", datatype = Entity.DATATYPE_INTEGER)
	private int idrespuesta;
	@TableField(name ="observacion", datatype = Entity.DATATYPE_STRING)
	private String observacion;
	@TableField(name ="cumple", datatype = Entity.DATATYPE_STRING)
	private String cumple;
	
	public int getIdrespuesta() {
		return idrespuesta;
	}
	public void setIdrespuesta(int idrespuesta) {
		this.idrespuesta = idrespuesta;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getCumple() {
		return cumple;
	}
	public void setCumple(String cumple) {
		this.cumple = cumple;
	}
	
}
