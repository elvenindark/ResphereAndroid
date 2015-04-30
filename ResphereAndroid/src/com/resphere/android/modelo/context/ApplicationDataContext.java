package com.resphere.android.modelo.context;

import android.content.Context;
import android.util.Log;

import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Acceso;
import com.resphere.android.modelo.Accion;
import com.resphere.android.modelo.Canton;
import com.resphere.android.modelo.Comentario;
import com.resphere.android.modelo.Equipo;
import com.resphere.android.modelo.Evaluacion;
import com.resphere.android.modelo.Evento;
import com.resphere.android.modelo.Impacto;
import com.resphere.android.modelo.MedioVida;
import com.resphere.android.modelo.NRecuperacion;
import com.resphere.android.modelo.NUrgente;
import com.resphere.android.modelo.Nrrhh;
import com.resphere.android.modelo.Organizacion;
import com.resphere.android.modelo.Parroquia;
import com.resphere.android.modelo.Poblacion;
import com.resphere.android.modelo.Provincia;
import com.resphere.android.modelo.Respuesta;
import com.resphere.android.modelo.Salud;
import com.resphere.android.modelo.Servicio;
import com.resphere.android.modelo.TipoEvento;
import com.resphere.android.modelo.Tipoacceso;
import com.resphere.android.modelo.Ubicacion;
import com.resphere.android.modelo.Vivienda;

public class ApplicationDataContext extends ObjectContext {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "resphere.db";
	
	public ObjectSet<Ubicacion> ubicacionDAO;
	public ObjectSet<Evento> eventoDAO;
	public ObjectSet<Provincia> provinciaDAO;
	public ObjectSet<Canton> cantonDAO;
	public ObjectSet<Parroquia> parroquiaDAO;
	public ObjectSet<TipoEvento> tipoEventoDAO;
	public ObjectSet<Poblacion> poblacionDAO;
	public ObjectSet<MedioVida> medioVidaDAO;
	public ObjectSet<Vivienda> viviendaDAO;
	public ObjectSet<Servicio> servicioDAO;
	public ObjectSet<Acceso> accesoDAO; 
	public ObjectSet<Tipoacceso> tipoAccesoDAO;
	public ObjectSet<Salud> saludDAO;
	public ObjectSet<Accion> accionDAO;
	public ObjectSet<Organizacion> organizacionDAO;
	public ObjectSet<Impacto> impactoDAO;
	public ObjectSet<NUrgente> nurgenteDAO;
	public ObjectSet<Nrrhh> nrrhhDAO;
	public ObjectSet<NRecuperacion> nrecuperacionDAO;
	public ObjectSet<Comentario> comentarioDAO;
	public ObjectSet<Evaluacion> evaluacionDAO;
	public ObjectSet<Equipo> equipoDAO;
	public ObjectSet<Respuesta> respuestaDAO;
	
	private Context context;
	
	private ApplicationDataContext appDataContext;
	
	public ApplicationDataContext(Context pContext) throws AdaFrameworkException {
		super(pContext, DATABASE_NAME);
		// TODO Auto-generated constructor stub
		if(eventoDAO == null)
			this.eventoDAO = new ObjectSet<Evento>(Evento.class, this);
		if(ubicacionDAO == null)
			this.ubicacionDAO = new ObjectSet<Ubicacion>(Ubicacion.class, this);
		if(provinciaDAO == null)
			this.provinciaDAO = new ObjectSet<Provincia>(Provincia.class, this);
		if(cantonDAO == null)
			this.cantonDAO = new ObjectSet<Canton>(Canton.class, this);
		if(parroquiaDAO == null)
			this.parroquiaDAO = new ObjectSet<Parroquia>(Parroquia.class, this);
		if(tipoEventoDAO == null)
			this.tipoEventoDAO = new ObjectSet<TipoEvento>(TipoEvento.class, this);
		if(poblacionDAO == null)
			this.poblacionDAO = new ObjectSet<Poblacion>(Poblacion.class, this);
		if(medioVidaDAO == null)
			this.medioVidaDAO = new ObjectSet<MedioVida>(MedioVida.class, this);
		if(viviendaDAO == null)
			this.viviendaDAO = new ObjectSet<Vivienda>(Vivienda.class, this);
		if(servicioDAO == null)
			this.servicioDAO = new ObjectSet<Servicio>(Servicio.class, this);
		if(accesoDAO == null)
			this.accesoDAO = new ObjectSet<Acceso>(Acceso.class, this);
		if(tipoAccesoDAO == null)
			this.tipoAccesoDAO = new ObjectSet<Tipoacceso>(Tipoacceso.class, this); 		
		if(saludDAO == null)
			this.saludDAO = new ObjectSet<Salud>(Salud.class, this); 
		if(accionDAO == null)
			this.accionDAO = new ObjectSet<Accion>(Accion.class, this);
		if(organizacionDAO == null)
			this.organizacionDAO = new ObjectSet<Organizacion>(Organizacion.class, this);
		if(impactoDAO == null)
			this.impactoDAO = new ObjectSet<Impacto>(Impacto.class, this);
		if(nurgenteDAO == null)
			this.nurgenteDAO = new ObjectSet<NUrgente>(NUrgente.class, this);
		if(nrrhhDAO == null)
			this.nrrhhDAO = new ObjectSet<Nrrhh>(Nrrhh.class, this);
		if(nrecuperacionDAO == null)
			this.nrecuperacionDAO = new ObjectSet<NRecuperacion>(NRecuperacion.class, this);
		if(comentarioDAO == null)
			this.comentarioDAO = new ObjectSet<Comentario>(Comentario.class, this);
		if(evaluacionDAO == null)
			this.evaluacionDAO = new ObjectSet<Evaluacion>(Evaluacion.class, this);
		if(equipoDAO == null)
			this.equipoDAO = new ObjectSet<Equipo>(Equipo.class, this);
		if(respuestaDAO == null)
			this.respuestaDAO = new ObjectSet<Respuesta>(Respuesta.class, this);
		
		this.context = pContext;
	}
	
	public ApplicationDataContext(Context pContext, String pDatabaseName, int pDatabaseVersion){
		super(pContext);
	}
	
	public ApplicationDataContext(Context pContext, String pDatabaseName){
		super(pContext);
	}

	public ObjectSet<Ubicacion> getUbicacionDAO() {
		return ubicacionDAO;
	}
	
	public ObjectSet<Evento> getEventoDAO() {
		return eventoDAO;
	}
	
	public ObjectSet<Provincia> getProvinciaDAO() {
		return provinciaDAO;
	}
	
	public ObjectSet<Canton> getCantonDAO() {
		return cantonDAO;
	}
	
	public ObjectSet<Parroquia> getParroquiaDAO() {
		return parroquiaDAO;
	}
	
	public ObjectSet<TipoEvento> getTipoEventoDAO(){
		return tipoEventoDAO;
	}
	
	public ObjectSet<Poblacion> getPoblacionDAO(){
		return poblacionDAO;
	}
	
	public ObjectSet<MedioVida> getMedioVidaDAO(){
		return medioVidaDAO;
	}
	
	public ObjectSet<Vivienda> getViviendaDAO(){
		return viviendaDAO;
	}
	
	public ObjectSet<Servicio> getServicioDAO(){
		return servicioDAO;
	}
	
	public ObjectSet<Acceso> getAccesoDAO(){
		return accesoDAO;
	}

	public ObjectSet<Tipoacceso> getTipoAccesoDAO(){
		return tipoAccesoDAO;
	}
	
	public ObjectSet<Salud> getSaludDAO(){
		return saludDAO;
	}
	
	public ObjectSet<Accion> getAccionDAO(){
		return accionDAO;
	}
	
	public ObjectSet<Organizacion> getOrganizacionDAO(){
		return organizacionDAO;
	}
	
	public ObjectSet<Impacto> getImpactoDAO(){
		return impactoDAO;
	}
	
	public ObjectSet<NUrgente> getNUrgenteDAO(){
		return nurgenteDAO;
	}
	
	public ObjectSet<Nrrhh> getNrrhhDAO(){
		return nrrhhDAO;
	}
	
	public ObjectSet<NRecuperacion> getNrecuperacionDAO(){
		return nrecuperacionDAO;
	}
	
	public ObjectSet<Comentario> getComentarioDAO(){
		return comentarioDAO;
	}
	
	public ObjectSet<Evaluacion> getEvaluacionDAO(){
		return evaluacionDAO;
	}
	
	public ObjectSet<Equipo> getEquipoDAO(){
		return equipoDAO;
	}
	
	public ObjectSet<Respuesta> getRespuestaDAO(){
		return respuestaDAO;
	}
	
	private ApplicationDataContext getApplicationDataContext() {
        if (appDataContext == null) {
            try {
                appDataContext = new ApplicationDataContext(context);
            } catch (Exception e) {
                Log.e("Androcode", "Error inicializando ApplicationDataContext: " + e.getMessage());
            }
        }
        return appDataContext;
    }
}
