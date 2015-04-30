package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="servicio")
public class Servicio extends Entity {
	@TableField(name ="sifunciona", datatype = Entity.DATATYPE_STRING)
	private String sifunciona;
	@TableField(name ="siaplica", datatype = Entity.DATATYPE_STRING)
	private String siaplica;
	@TableField(name ="observacion", datatype = Entity.DATATYPE_STRING)
	private String observacion;
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idtipodano", datatype = Entity.DATATYPE_STRING)
	private String idtipodano;
	@TableField(name ="idtiposervicio", datatype = Entity.DATATYPE_STRING)
	private String idtiposervicio;
	public String getSifunciona() {
		return sifunciona;
	}
	public void setSifunciona(String sifunciona) {
		this.sifunciona = sifunciona;
	}
	public String getSiaplica() {
		return siaplica;
	}
	public void setSiaplica(String siaplica) {
		this.siaplica = siaplica;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getIdtipodano() {
		return idtipodano;
	}
	public void setIdtipodano(String idtipodano) {
		this.idtipodano = idtipodano;
	}
	public String getIdtiposervicio() {
		return idtiposervicio;
	}
	public void setIdtiposervicio(String idtiposervicio) {
		this.idtiposervicio = idtiposervicio;
	}	
}
