package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="tipoevento")
public class TipoEvento extends Entity{
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING, required = true)
	private String idevento;
	@TableField(name ="tipoevento", datatype = Entity.DATATYPE_STRING, required = true)
	private String evento;
	
	public String getIdevento() {
		return idevento;
	}

	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public TipoEvento(){
		
	}

}
