package co.gov.metropol.area247.centrocontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D247SEG_ROL", schema = "CONTENEDOR")
public class Rol {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "S_NOMBRE")
    private String nombre;
    
    @Column(name = "S_DESCRIPCION")
    private String descripcion;
    
    @Column(name = "N_ACTIVO")
    private boolean activo;

    
   
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "FormatoCarga [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
    
}
