package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="equipo")
public class Equipo extends Entity {
	
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="nombre", datatype = Entity.DATATYPE_STRING)
	private String nombre;
	@TableField(name ="organizacion", datatype = Entity.DATATYPE_STRING)
	private String organizacion;
	@TableField(name ="telefono", datatype = Entity.DATATYPE_STRING)
	private String telefono;
	@TableField(name ="email", datatype = Entity.DATATYPE_STRING)
	private String email;
	@TableField(name ="idtipoblacion", datatype = Entity.DATATYPE_STRING)
	private String idtipopoblacion;
	@TableField(name ="idroltecnico", datatype = Entity.DATATYPE_STRING)
	private String idroltecnico;
	
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getOrganizacion() {
		return organizacion;
	}
	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdtipopoblacion() {
		return idtipopoblacion;
	}
	public void setIdtipopoblacion(String idtipopoblacion) {
		this.idtipopoblacion = idtipopoblacion;
	}
	public String getIdroltecnico() {
		return idroltecnico;
	}
	public void setIdroltecnico(String idroltecnico) {
		this.idroltecnico = idroltecnico;
	}
	
}
