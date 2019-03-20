package co.gov.metropol.area247.centrocontrol.dto;

import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.centrocontrol.model.FormatoCarga;

public class CargaArchivoDto {
	
	private Long id;
	
	private String tabla;
	
	private MultipartFile archivo;
	
	private FormatoCarga tipoArchivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public MultipartFile getArchivo() {
		return archivo;
	}

	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}

	public FormatoCarga getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(FormatoCarga tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	@Override
	public String toString() {
		return "CargaArchivoDTO [id=" + id + ", tabla=" + tabla + ", archivo=" + archivo + ", tipoArchivo="
				+ tipoArchivo + "]";
	}

}
