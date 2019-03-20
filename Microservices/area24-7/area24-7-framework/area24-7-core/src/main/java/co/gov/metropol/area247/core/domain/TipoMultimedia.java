package co.gov.metropol.area247.core.domain;

import java.io.Serializable;

public class TipoMultimedia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String tipo;

	private String subtipo;

	private String renderizador;

	public TipoMultimedia() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	public String getRenderizador() {
		return renderizador;
	}

	public void setRenderizador(String renderizador) {
		this.renderizador = renderizador;
	}

}
