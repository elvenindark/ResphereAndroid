package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="accion")
public class Accion extends Entity {

	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="fecha", datatype = Entity.DATATYPE_STRING)
	private String fecha;
	@TableField(name ="descripcion", datatype = Entity.DATATYPE_STRING)
	private String descripcion;
	@TableField(name ="organizacion", datatype = Entity.DATATYPE_STRING)
	private String organizacion;
	@TableField(name ="hogares", datatype = Entity.DATATYPE_STRING)
	private String hogares;
	@TableField(name ="personas", datatype = Entity.DATATYPE_STRING)
	private String personas;
	
	public Accion(){
		
	}

	public String getIdevento() {
		return idevento;
	}

	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}

	public String getHogares() {
		return hogares;
	}

	public void setHogares(String hogares) {
		this.hogares = hogares;
	}

	public String getPersonas() {
		return personas;
	}

	public void setPersonas(String personas) {
		this.personas = personas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
