package co.gov.metropol.area247.core.domain;

import java.io.Serializable;

public class Multimedia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String ruta;

	private String nombre;

	private TipoMultimedia tipoMultimedia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	// public Long getIdAplicacion() {
	// return idAplicacion;
	// }
	//
	// public void setIdAplicacion(Long idAplicacion) {
	// this.idAplicacion = idAplicacion;
	// }
	//
	// public Long getIdEvento() {
	// return idEvento;
	// }

	// public void setIdEvento(Long idEvento) {
	// this.idEvento = idEvento;
	// }

	public TipoMultimedia getTipoMultimedia() {
		return tipoMultimedia;
	}

	public void setTipoMultimedia(TipoMultimedia tipoMultimedia) {
		this.tipoMultimedia = tipoMultimedia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
