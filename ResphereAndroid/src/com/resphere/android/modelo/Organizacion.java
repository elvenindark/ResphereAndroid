package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="organizacion")
public class Organizacion extends Entity {
	
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idorganizacion", datatype = Entity.DATATYPE_STRING)
	private String idorganizacion;
	@TableField(name ="nombre", datatype = Entity.DATATYPE_STRING)
	private String nombre;
	@TableField(name ="sector", datatype = Entity.DATATYPE_STRING)
	private String sector;
	@TableField(name ="contacto", datatype = Entity.DATATYPE_STRING)
	private String contacto;
	
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getIdorganizacion() {
		return idorganizacion;
	}
	public void setIdorganizacion(String idorganizacion) {
		this.idorganizacion = idorganizacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}	
	
}
