package co.gov.metropol.area247.centrocontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_VEHICULO_MARCA", schema = "CCONTROL")
public class TipoMarcaVehiculo {
	
    @Id
    @Column(name = "TIPOID")
    private Long id;
    
    private String nombre;

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

}
