package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.RegularExpressionValidation;
import com.mobandme.ada.annotations.RequiredFieldValidation;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.resphere.android.vista.R;

@Table(name="evento")
public class Evento extends Entity {

	@Databinding(ViewId = R.id.txtFechaEvento)
	@TableField(name ="fecha", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "fecha vacia")
	private String fecha;
	@Databinding(ViewId = R.id.txtHoraEvento)
	@TableField(name ="hora", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "hora vacia")
	private String hora;
	@Databinding(ViewId = R.id.editDescEvento)
	@TableField(name ="descripcion", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(messageResourceId=R.string.error_empty_name)
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |Ò—]{5,50}", message = "Problemas en el formato: 5 a 50 caracteres")
	private String descripcion;
	@Databinding(ViewId = R.id.editEfecSecundarios)
	@TableField(name ="efectos", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(messageResourceId=R.string.error_empty_name)
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |Ò—]{5,50}", message = "Problemas en el formato: 5 a 50 caracteres")
	private String efectos;
	@Databinding(ViewId = R.id.editAmenazas)
	@TableField(name ="amenazas", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(messageResourceId=R.string.error_empty_name)
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |Ò—]{5,50}", message = "Problemas en el formato: 5 a 50 caracteres")
	private String amenazas;
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message="identificador vacio")
	private String idevento;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEfectos() {
		return efectos;
	}
	public void setEfectos(String efectos) {
		this.efectos = efectos;
	}
	public String getAmenazas() {
		return amenazas;
	}
	public void setAmenazas(String amenazas) {
		this.amenazas = amenazas;
	}	
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String nameClass(){
		return "com.resphere.android.modelo.Evento";
	}
}
