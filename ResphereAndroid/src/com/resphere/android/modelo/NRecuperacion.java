package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="nrecuperacion")
public class NRecuperacion extends Entity {
	
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idtiposector", datatype = Entity.DATATYPE_STRING)
	private String idtiposector;
	@TableField(name ="aplica", datatype = Entity.DATATYPE_STRING)
	private String aplica;
	@TableField(name ="observacion", datatype = Entity.DATATYPE_STRING)
	private String observacion;
	
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getIdtiposector() {
		return idtiposector;
	}
	public void setIdtiposector(String idtiposector) {
		this.idtiposector = idtiposector;
	}
	public String getAplica() {
		return aplica;
	}
	public void setAplica(String aplica) {
		this.aplica = aplica;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}	
	
}
