package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="acceso")
public class Respuesta extends Entity {
	@TableField(name ="idrespuesta", datatype = Entity.DATATYPE_INTEGER)
	private int idrespuesta;
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idindicadorclave", datatype = Entity.DATATYPE_STRING)
	private String idindicadorclave;
	@TableField(name ="observacion", datatype = Entity.DATATYPE_STRING)
	private String observacion;
	@TableField(name ="idsectorhumanitario", datatype = Entity.DATATYPE_INTEGER)
	private int idsectorhumanitario;
	public int getIdrespuesta() {
		return idrespuesta;
	}
	public void setIdrespuesta(int idrespuesta) {
		this.idrespuesta = idrespuesta;
	}
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getIdindicadorclave() {
		return idindicadorclave;
	}
	public void setIdindicadorclave(String idindicadorclave) {
		this.idindicadorclave = idindicadorclave;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public int getIdsectorhumanitario() {
		return idsectorhumanitario;
	}
	public void setIdsectorhumanitario(int idsectorhumanitario) {
		this.idsectorhumanitario = idsectorhumanitario;
	}
	
}
