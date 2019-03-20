package co.gov.metropol.area247.centrocontrol.model;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase encargada de almacenar los diversos objetos que usara el centro de
 * control
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Objeto{
	

	private Long id;	

	private String nombre;
	
	private String descripcion;
	
	private TipoObjeto tipoObjeto;
	
	private Boolean activo;
	
	
	private Boolean puedeAdicionar;

	private Boolean puedeBorrar;

	private Boolean puedeEditar;

	private Boolean puedeImprimir;
	
	private Boolean puedeConsultar;
	
	
	private Boolean auditarAdicion;

	private Boolean auditarBorrar;

	private Boolean auditarConsulta;

	private Boolean auditarEdicion;

	private Boolean auditarImprimir;
	
    
    private Boolean puedeAuditarAdicion;

    private Boolean puedeAuditarEdicion;

    private Boolean puedeAuditarBorrar;

    private Boolean puedeAuditarImprimir;

    private Boolean puedeAuditarConsulta;
    
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public TipoObjeto getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(TipoObjeto tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getPuedeAdicionar() {
		return puedeAdicionar;
	}

	public void setPuedeAdicionar(Boolean puedeAdicionar) {
		this.puedeAdicionar = puedeAdicionar;
	}

	public Boolean getPuedeBorrar() {
		return puedeBorrar;
	}

	public void setPuedeBorrar(Boolean puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}

	public Boolean getPuedeEditar() {
		return puedeEditar;
	}

	public void setPuedeEditar(Boolean puedeEditar) {
		this.puedeEditar = puedeEditar;
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

	public Boolean getAuditarAdicion() {
		return auditarAdicion;
	}

	public void setAuditarAdicion(Boolean auditarAdicion) {
		this.auditarAdicion = auditarAdicion;
	}

	public Boolean getAuditarBorrar() {
		return auditarBorrar;
	}

	public void setAuditarBorrar(Boolean auditarBorrar) {
		this.auditarBorrar = auditarBorrar;
	}

	public Boolean getAuditarConsulta() {
		return auditarConsulta;
	}

	public void setAuditarConsulta(Boolean auditarConsulta) {
		this.auditarConsulta = auditarConsulta;
	}

	public Boolean getAuditarEdicion() {
		return auditarEdicion;
	}

	public void setAuditarEdicion(Boolean auditarEdicion) {
		this.auditarEdicion = auditarEdicion;
	}

	public Boolean getAuditarImprimir() {
		return auditarImprimir;
	}

	public void setAuditarImprimir(Boolean auditarImprimir) {
		this.auditarImprimir = auditarImprimir;
	}

	public Boolean getPuedeAuditarAdicion() {
		return puedeAuditarAdicion;
	}

	public void setPuedeAuditarAdicion(Boolean puedeAuditarAdicion) {
		this.puedeAuditarAdicion = puedeAuditarAdicion;
	}

	public Boolean getPuedeAuditarEdicion() {
		return puedeAuditarEdicion;
	}

	public void setPuedeAuditarEdicion(Boolean puedeAuditarEdicion) {
		this.puedeAuditarEdicion = puedeAuditarEdicion;
	}

	public Boolean getPuedeAuditarBorrar() {
		return puedeAuditarBorrar;
	}

	public void setPuedeAuditarBorrar(Boolean puedeAuditarBorrar) {
		this.puedeAuditarBorrar = puedeAuditarBorrar;
	}

	public Boolean getPuedeAuditarImprimir() {
		return puedeAuditarImprimir;
	}

	public void setPuedeAuditarImprimir(Boolean puedeAuditarImprimir) {
		this.puedeAuditarImprimir = puedeAuditarImprimir;
	}

	public Boolean getPuedeAuditarConsulta() {
		return puedeAuditarConsulta;
	}

	public void setPuedeAuditarConsulta(Boolean puedeAuditarConsulta) {
		this.puedeAuditarConsulta = puedeAuditarConsulta;
	}
	
}