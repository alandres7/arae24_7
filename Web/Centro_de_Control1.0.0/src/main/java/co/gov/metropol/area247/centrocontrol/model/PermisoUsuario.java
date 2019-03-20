package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PermisoUsuario{

	private Long id;
	private Long idUsuario;
	private Long idObjeto;
	
	private Boolean puedeAdicionar;
	private Boolean puedeEditar;
	private Boolean puedeBorrar;
	private Boolean puedeImprimir;
	private Boolean puedeConsultar;
	
	
	
	
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

	public Boolean getPuedeAdicionar() {
		return puedeAdicionar;
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

}