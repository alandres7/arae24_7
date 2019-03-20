package co.gov.metropol.area247.model;

import java.util.List;

public class MetroDTO {

	private String codigo;
	private String descripcion;
	private List<RutaMetroDTO> rutasDTO;
	private List<LineaMetroDTO> lineasDTO;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<RutaMetroDTO> getRutasDTO() {
		return rutasDTO;
	}

	public void setRutasDTO(List<RutaMetroDTO> rutasDTO) {
		this.rutasDTO = rutasDTO;
	}

	public List<LineaMetroDTO> getLineasDTO() {
		return lineasDTO;
	}

	public void setLineasDTO(List<LineaMetroDTO> lineasDTO) {
		this.lineasDTO = lineasDTO;
	}

}
