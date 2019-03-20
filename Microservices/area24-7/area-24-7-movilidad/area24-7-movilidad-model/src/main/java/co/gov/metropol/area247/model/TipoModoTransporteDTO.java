package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;

public class TipoModoTransporteDTO extends AbstractDTO {

	private String nombre;
	private String descripcion;
	private Long idItem;
	private int fuenteDatos;

	public TipoModoTransporteDTO() {
		super();
		this.nombre = "";
		this.descripcion = "";
		this.idItem = (long) 0;
		this.fuenteDatos = 0;
	}

	public TipoModoTransporteDTO(ModoRecorrido modo) {
		this.id = Long.valueOf(modo.indice());
		this.nombre = modo.name();
		this.descripcion = modo.toString().replace("_", " ");
	}

	@Override
	public TipoModoTransporteDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoModoTransporteDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoModoTransporteDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

}
