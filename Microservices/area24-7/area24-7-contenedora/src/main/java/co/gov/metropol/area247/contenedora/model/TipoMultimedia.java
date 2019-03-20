package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T247CON_TIPO_MULTIMEDIA", schema = "CONTENEDOR")
public class TipoMultimedia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_TIPO_MULTIMEDIA_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long id;

	@NotNull
	@Column(name = "S_NOMBRE_TIPO")
	private String tipo;

	@NotNull
	@Column(name = "S_NOMBRE_SUBTIPO")
	private String subtipo;

	@Column(name = "S_RENDERIZADOR")
	private String renderizador;

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
