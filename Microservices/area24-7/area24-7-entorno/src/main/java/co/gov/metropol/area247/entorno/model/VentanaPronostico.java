package co.gov.metropol.area247.entorno.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="D247ENT_TIPO_VENTANA", schema="CONTENEDOR")
public class VentanaPronostico {
	@Id
	@Column(name="ID")
	private long id;
	
	@Column(name="N_CODIGO_VENTANA")
	private long codigoVentana;
	
	@Column(name="S_NOMBRE")
	private String nombre;
	
	@Column(name="S_DESCRIPCION")
	private String descripcion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodigoVentana() {
		return codigoVentana;
	}

	public void setCodigoVentana(long codigoVentana) {
		this.codigoVentana = codigoVentana;
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

	/**
	 * @param id
	 * @param codigoVentana
	 * @param nombre
	 * @param descripcion
	 */
	public VentanaPronostico(long id, long codigoVentana, String nombre, String descripcion) {
		this.id = id;
		this.codigoVentana = codigoVentana;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	/**
	 * 
	 */
	public VentanaPronostico() {
		// TODO Auto-generated constructor stub
	}
	
}
