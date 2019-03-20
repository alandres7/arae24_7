package co.gov.metropol.area247.services.rest.gtpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.InfoRutaOperadorEmpresasDTO;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;

public class InfoRutaOperadorEmpresasWSDTO {
	
	@JsonProperty(value = "tipoSistemaRuta")
	private Long tipoSistemaRuta;
	
	@JsonProperty(value = "nombreSistemaRuta")
	private String nombreSistemaRuta;
	
	@JsonProperty(value = "idOperador")
	private Long idOperador;
	
	@JsonProperty(value = "operadorActivo")
	private String operadorActivo;
	
	@JsonProperty(value = "idEmpresa")
	private Long idEmpresa;
	
	@JsonProperty(value = "empresaActivo")
	private String empresaActivo;
	
	@JsonProperty(value = "idPersona")
	private Long idPersona;
	
	@JsonProperty(value = "nombrePersona")
	private String nombrePersona;
	
	public InfoRutaOperadorEmpresasDTO getInfoRutaOperadorEmpresasDTO(){
		InfoRutaOperadorEmpresasDTO infoRutaOperadorEmpresasDTO = new InfoRutaOperadorEmpresasDTO();
		infoRutaOperadorEmpresasDTO.setTipoSistemaRuta(this.tipoSistemaRuta);
		infoRutaOperadorEmpresasDTO.setNombreSistemaRuta(this.nombreSistemaRuta);
		infoRutaOperadorEmpresasDTO.setIdOperador(this.idOperador);
		infoRutaOperadorEmpresasDTO.setOperadorActivo(this.operadorActivo);
		infoRutaOperadorEmpresasDTO.setIdEmpresa(this.idEmpresa);
		infoRutaOperadorEmpresasDTO.setEmpresaActivo(this.empresaActivo);
		infoRutaOperadorEmpresasDTO.setIdPersona(this.idPersona);
		infoRutaOperadorEmpresasDTO.setNombrePersona(this.nombrePersona);
		return infoRutaOperadorEmpresasDTO;
	}
	
	public TipoSistemaRutaDTO getTipoSistemaRutaDTO() {
		TipoSistemaRutaDTO tipoSistemaRutaDTO = new TipoSistemaRutaDTO();
		tipoSistemaRutaDTO.setIdItem(this.tipoSistemaRuta);
		tipoSistemaRutaDTO.setFuenteDatos(2);
		tipoSistemaRutaDTO.setNombre(this.nombreSistemaRuta);
		tipoSistemaRutaDTO.setDescripcion("-");
		return tipoSistemaRutaDTO;
	}

	public Long getTipoSistemaRuta() {
		return tipoSistemaRuta;
	}

	public void setTipoSistemaRuta(Long tipoSistemaRuta) {
		this.tipoSistemaRuta = tipoSistemaRuta;
	}

	public String getNombreSistemaRuta() {
		return nombreSistemaRuta;
	}

	public void setNombreSistemaRuta(String nombreSistemaRuta) {
		this.nombreSistemaRuta = nombreSistemaRuta;
	}

	public Long getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Long idOperador) {
		this.idOperador = idOperador;
	}

	public String getOperadorActivo() {
		return operadorActivo;
	}

	public void setOperadorActivo(String operadorActivo) {
		this.operadorActivo = operadorActivo;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getEmpresaActivo() {
		return empresaActivo;
	}

	public void setEmpresaActivo(String empresaActivo) {
		this.empresaActivo = empresaActivo;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	
	
	
}
