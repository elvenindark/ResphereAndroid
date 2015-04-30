package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="poblacion")
public class Poblacion extends Entity {

	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	
	@TableField(name ="idtipopoblacion", datatype = Entity.DATATYPE_STRING)
	private String idtipopoblacion;
	
	@TableField(name ="idtipoafectacion", datatype = Entity.DATATYPE_STRING)
	private String idtipoafectacion;
	
	@TableField(name ="numero", datatype = Entity.DATATYPE_STRING)
	private String numero;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getIdevento() {
		return idevento;
	}

	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}

	public String getIdtipopoblacion() {
		return idtipopoblacion;
	}

	public void setIdtipopoblacion(String idtipopoblacion) {
		this.idtipopoblacion = idtipopoblacion;
	}

	public String getIdtipoafectacion() {
		return idtipoafectacion;
	}

	public void setIdtipoafectacion(String idtipoafectacion) {
		this.idtipoafectacion = idtipoafectacion;
	}
	
}
