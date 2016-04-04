package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="evaluacion")
public class Evaluacion extends Entity {
	
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="fecha", datatype = Entity.DATATYPE_STRING)
	private String fecha;
	@TableField(name ="idevaluacion", datatype = Entity.DATATYPE_STRING)
	private String idevalucion;

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

	public String getIdevalucion() {
		return idevalucion;
	}

	public void setIdevalucion(String idevalucion) {
		this.idevalucion = idevalucion;
	}
	
	
}
