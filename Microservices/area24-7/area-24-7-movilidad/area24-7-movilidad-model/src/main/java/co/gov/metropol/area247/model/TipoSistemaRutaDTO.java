package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class TipoSistemaRutaDTO extends AbstractDTO{
	
	private String nombre;
	private String descripcion;
	private Long idItem;
	private int fuenteDatos;
	
	
	public TipoSistemaRutaDTO() {
		this.nombre = "";
		this.descripcion = "";
		this.idItem = (long) 0;
		this.fuenteDatos = 0;
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
	@Override
	public TipoSistemaRutaDTO withId(Long id) {
		super.setId(id);
		return this;
	}
	@Override
	public TipoSistemaRutaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (this.getClass() != obj.getClass()) {
		    return false;
		}
		
		TipoSistemaRutaDTO dto = (TipoSistemaRutaDTO) obj;
		
		return (this.getFuenteDatos() == dto.getFuenteDatos() && this.getNombre().equals(dto.getNombre()));
	}
	
	@Override
	public int hashCode() {
		return this.getNombre().hashCode();
	}
}
