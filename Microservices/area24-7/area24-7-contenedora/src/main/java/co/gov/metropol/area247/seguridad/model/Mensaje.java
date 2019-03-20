package co.gov.metropol.area247.seguridad.model;

import java.io.Serializable;

import javax.persistence.Column;

//@Entity
//@Table(name = "T247SEG_MENSAJE", schema = "CONTENEDOR")
public class Mensaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_CODIGO_TECNICO")
	private int codigo;
	
	@Column(name = "S_MENSAJE_TECNICO")
	private String mensajeTecnico;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "S_MENSAJE_USUARIO")
	private String mensaje;
	
	@Column(name = "S_FUENTE_MENSAJE")
	private String fuenteMensaje;
	
	@Column(name = "S_TIPO_MENSAJE")
	private String tipoMensaje;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}

	public void setMensajeTecnico(String mensajeTecnico) {
		this.mensajeTecnico = mensajeTecnico;
	}

	public String getFuenteMensaje() {
		return fuenteMensaje;
	}

	public void setFuenteMensaje(String fuenteMensaje) {
		this.fuenteMensaje = fuenteMensaje;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
	
}
