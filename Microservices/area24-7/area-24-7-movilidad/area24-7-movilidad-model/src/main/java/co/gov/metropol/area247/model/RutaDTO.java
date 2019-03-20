package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.repository.domain.support.enums.RutaType;
import co.gov.metropol.area247.util.web.Coordenada;

public class RutaDTO {
	
	@Loggable
	private Long id;
	
	@Loggable
	private String nombre;
	
	@Loggable
	private String descripcion;
	
	@Loggable
	private Double latitud;
	
	@Loggable
	private Double longitud;
	
	@Loggable
	private RutaType tipoViaje;
	
	@Loggable
	private Double distacia;
	
	public RutaDTO() {
		super();
	}
	
	public RutaDTO(EstacionEnciclaDTO estacionEnciclaDTO, Coordenada coordenada) {
		Coordenada coordenadaEncicla = new Coordenada(estacionEnciclaDTO.getLatitud(), estacionEnciclaDTO.getLongitud());
		this.id = estacionEnciclaDTO.getId();
		this.nombre = estacionEnciclaDTO.getNombre();
		this.descripcion = estacionEnciclaDTO.getDescripcion();
		this.latitud = estacionEnciclaDTO.getLatitud();
		this.longitud = estacionEnciclaDTO.getLongitud();
		this.tipoViaje = RutaType.CICLA;
		this.distacia = Coordenada.calcularDistancia(coordenada, coordenadaEncicla);
	}
	
	public RutaDTO(TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO, Coordenada coordenada) {
		Coordenada coordenadaEncicla = new Coordenada(tareviaEstacionEnciclaDTO.getLatitudEstacionEncila(), tareviaEstacionEnciclaDTO.getLongitudEstacionEncila());
		this.id = tareviaEstacionEnciclaDTO.getId();
		this.nombre = tareviaEstacionEnciclaDTO.getNombreEstacionEncicla();
		this.descripcion = tareviaEstacionEnciclaDTO.getDescripcionEstacionEncicla();
		this.latitud = tareviaEstacionEnciclaDTO.getLatitudEstacionEncila();
		this.longitud = tareviaEstacionEnciclaDTO.getLongitudEstacionEncila();
		this.tipoViaje = RutaType.CICLA;
		this.distacia = Coordenada.calcularDistancia(coordenada, coordenadaEncicla);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public RutaType getTipoViaje() {
		return tipoViaje;
	}

	public void setTipoViaje(RutaType tipoViaje) {
		this.tipoViaje = tipoViaje;
	}

	public Double getDistacia() {
		return distacia;
	}

	public void setDistacia(Double distacia) {
		this.distacia = distacia;
	}

}
