package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PermisoUsuarioVista{

	private Long id;
	private Long idUsuario;
	private Long idObjeto;
	private String nombreObjeto;
	private String tipoObjeto;
	
	private Boolean puedeAdicionar;
	private Boolean puedeEditar;
	private Boolean puedeBorrar;
	private Boolean puedeImprimir;
	private Boolean puedeConsultar;
	
	private Boolean checkAdicionar;
	private Boolean checkEditar;
	private Boolean checkBorrar;
	private Boolean checkImprimir;
	private Boolean checkConsultar;
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Long getIdObjeto() {
		return idObjeto;
	}
	
	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}
		
	public String getNombreObjeto() {
		return nombreObjeto;
	}

	public void setNombreObjeto(String nombreObjeto) {
		this.nombreObjeto = nombreObjeto;
	}

	public Boolean getPuedeAdicionar() {
		return puedeAdicionar;
	}	
	
	public String getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public void setPuedeAdicionar(Boolean puedeAdicionar) {
		this.puedeAdicionar = puedeAdicionar;
	}
	
	public Boolean getPuedeEditar() {
		return puedeEditar;
	}
	public void setPuedeEditar(Boolean puedeEditar) {
		this.puedeEditar = puedeEditar;
	}
	
	public Boolean getPuedeBorrar() {
		return puedeBorrar;
	}
	
	public void setPuedeBorrar(Boolean puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}
	
	public Boolean getPuedeImprimir() {
		return puedeImprimir;
	}
	
	public void setPuedeImprimir(Boolean puedeImprimir) {
		this.puedeImprimir = puedeImprimir;
	}
	
	public Boolean getPuedeConsultar() {
		return puedeConsultar;
	}
	
	public void setPuedeConsultar(Boolean puedeConsultar) {
		this.puedeConsultar = puedeConsultar;
	}

	
	
	public Boolean getCheckAdicionar() {
		return checkAdicionar;
	}

	public void setCheckAdicionar(Boolean checkAdicionar) {
		this.checkAdicionar = checkAdicionar;
	}

	public Boolean getCheckEditar() {
		return checkEditar;
	}

	public void setCheckEditar(Boolean checkEditar) {
		this.checkEditar = checkEditar;
	}

	public Boolean getCheckBorrar() {
		return checkBorrar;
	}

	public void setCheckBorrar(Boolean checkBorrar) {
		this.checkBorrar = checkBorrar;
	}

	public Boolean getCheckImprimir() {
		return checkImprimir;
	}

	public void setCheckImprimir(Boolean checkImprimir) {
		this.checkImprimir = checkImprimir;
	}

	public Boolean getCheckConsultar() {
		return checkConsultar;
	}

	public void setCheckConsultar(Boolean checkConsultar) {
		this.checkConsultar = checkConsultar;
	}

}