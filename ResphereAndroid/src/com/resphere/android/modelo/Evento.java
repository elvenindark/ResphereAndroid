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
	
	public static int Fecha_Evento = R.id.txtFechaEvento;

	@Databinding(ViewId = 0x7f090050)	
	@TableField(name ="fecha", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "fecha vacia")
	private String fecha;	
	public static int Hora_Evento = R.id.txtHoraEvento;	
	@Databinding(ViewId = 0x7f090051)
	@TableField(name ="hora", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "hora vacia")
	private String hora;
	public static int Desc_Evento = R.id.editDescEvento;	
	public static int Error_empty_name = R.string.error_empty_name;			
	@Databinding(ViewId = 0x7f090056)
	@TableField(name ="descripcion", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(messageResourceId=0x7f05003b)
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |,|.|Ò—]{5,100}", message = "Problemas en el formato: 5 a 100 caracteres ")
	private String descripcion;
	public static int efectos_secundarios = R.id.editEfecSecundarios;
	@Databinding(ViewId = 0x7f090057)
	@TableField(name ="efectos", datatype = Entity.DATATYPE_STRING, required = true)
	//@RequiredFieldValidation(messageResourceId=R.string.error_empty_name)
	@RequiredFieldValidation(messageResourceId=0x7f05003b)
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |,|.|Ò—]{5,100}", message = "Problemas en el formato: 5 a 100 caracteres")
	private String efectos;
	public static int amenazas_evento = R.id.editAmenazas;
	@Databinding(ViewId = 0x7f090058)
	@TableField(name ="amenazas", datatype = Entity.DATATYPE_STRING, required = true)
	//@RequiredFieldValidation(messageResourceId=R.string.error_empty_name)
	@RequiredFieldValidation(messageResourceId=0x7f05003b)
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |,|.|Ò—]{5,100}", message = "Problemas en el formato: 5 a 100 caracteres")
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
