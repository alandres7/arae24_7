package co.gov.metropol.area247.centrocontrol.model;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "T247CCN_AUTORIDAD_COMPETENTE", schema = "CCONTROL")
public class AutoridadCompetente implements Persistable<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_AUTORIDAD_COMPETENTE_ID", allocationSize=1)
	private Long id;
	
	@NotNull
	@Column(name="S_NOMBRE")
	private String nombre;
	
	@Column(name="S_DIRECCION")
	private String direccion;
	
	@Column(name = "S_TELEFONO")
	private String telefono;
	
	@Column(name = "S_MUNICIPIO")
	private String municipio;
	
	@Column(name = "S_CORREO_ELECTRONICO")
	private String correo;
	
	@Column(name = "S_HORARIO_ATENCION")
	private String horario;
	
//	@JsonIgnore
//	@OneToMany(mappedBy="autoridadCompetente")
//	private List<NodoArbol> nodoArbol;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return id==null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public List<NodoArbol> getNodoArbol() {
//		return nodoArbol;
//	}
//
//	public void setNodoArbol(List<NodoArbol> nodoArbol) {
//		this.nodoArbol = nodoArbol;
//	}

	

	

	
}
