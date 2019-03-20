package co.gov.metropol.area247.services.rest.gtpc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.HorarioRutaDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class HorarioGtpcWSDTO {

	@JsonProperty(value = "idHorario")
	private Long idHorario;
	
	@JsonProperty(value = "horaInicioOperacion")
	private String horaInicioOperacion;
	
	@JsonProperty(value = "horaFinOperacion")
	private String horaFinOperacion;
	
	@JsonProperty(value = "horaInicioPicoAm")
	private String horaInicioPicoAm;
	
	@JsonProperty(value = "horaFinPicoAm")
	private String horaFinPicoAm;
	
	@JsonProperty(value = "horaInicioPicoPm")
	private String horaInicioPicoPm;
	
	@JsonProperty(value = "horaFinPicoPm")
	private String horaFinPicoPm;
	
	@JsonProperty(value = "idTipoEstadoRegistro")
	private Long idTipoEstadoRegistro;
	
	@JsonProperty(value = "descEstadoRegistro")
	private String descEstadoRegistro;
	
	@JsonProperty(value = "activo")
	private String activo;
	
	@JsonProperty(value = "idRuta")
	private Long idRuta;

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}

	public String getHoraInicioOperacion() {
		return horaInicioOperacion;
	}

	public void setHoraInicioOperacion(String horaInicioOperacion) {
		this.horaInicioOperacion = horaInicioOperacion;
	}

	public String getHoraFinOperacion() {
		return horaFinOperacion;
	}

	public void setHoraFinOperacion(String horaFinOperacion) {
		this.horaFinOperacion = horaFinOperacion;
	}

	public String getHoraInicioPicoAm() {
		return horaInicioPicoAm;
	}

	public void setHoraInicioPicoAm(String horaInicioPicoAm) {
		this.horaInicioPicoAm = horaInicioPicoAm;
	}

	public String getHoraFinPicoAm() {
		return horaFinPicoAm;
	}

	public void setHoraFinPicoAm(String horaFinPicoAm) {
		this.horaFinPicoAm = horaFinPicoAm;
	}

	public String getHoraInicioPicoPm() {
		return horaInicioPicoPm;
	}

	public void setHoraInicioPicoPm(String horaInicioPicoPm) {
		this.horaInicioPicoPm = horaInicioPicoPm;
	}

	public String getHoraFinPicoPm() {
		return horaFinPicoPm;
	}

	public void setHoraFinPicoPm(String horaFinPicoPm) {
		this.horaFinPicoPm = horaFinPicoPm;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}
	
	public Long getIdTipoEstadoRegistro() {
		return idTipoEstadoRegistro;
	}

	public void setIdTipoEstadoRegistro(Long idTipoEstadoRegistro) {
		this.idTipoEstadoRegistro = idTipoEstadoRegistro;
	}

	public String getDescEstadoRegistro() {
		return descEstadoRegistro;
	}

	public void setDescEstadoRegistro(String descEstadoRegistro) {
		this.descEstadoRegistro = descEstadoRegistro;
	}

	public HorarioRutaDTO getHorarioRuta() {
		HorarioRutaDTO horarioRuta = new HorarioRutaDTO();
		horarioRuta.setIdItem(this.idHorario);
		horarioRuta.setFuenteDatos(2);
		horarioRuta.setHoraInicioOperacion(obtenerHora(this.horaInicioOperacion));
		horarioRuta.setHoraFinOperacion(obtenerHora(this.horaFinOperacion));
		horarioRuta.setHoraInicioPicoAm(obtenerHora(this.horaInicioPicoAm));
		horarioRuta.setHoraFinPicoAm(obtenerHora(this.horaFinPicoAm));
		horarioRuta.setHoraInicioPicoPm(obtenerHora(this.horaInicioPicoPm));
		horarioRuta.setHoraFinPicoPm(obtenerHora(this.horaFinPicoPm));
		horarioRuta.setActivo(this.activo);
		horarioRuta.setIdRuta(this.idRuta);
		return horarioRuta;
	}
	
	private Date stringToDate(String dateString) {
		Date date = null;
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
    
    // TODO eliminar metodo cuando el WS de GTPC devuelva los horarios en formato HH:mm:ss
    @Deprecated
    private static String obtenerHora(String fecha) {
    	if (fecha != null && !fecha.isEmpty()) {
    		String[] partesFecha = fecha.split(" ");
    		if (partesFecha != null && partesFecha.length > 1) {
    			return partesFecha[3];
    		} else {
    			return fecha;
    		}
    	}
    	return "";
    }
}
