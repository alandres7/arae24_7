package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 
 * @author ageofuentes
 * <h3>
 * Entidad que mapea la tabla CONTROL.D247SEG_LOGINEXTERNO
 * </h3>
 *
 */
@Entity
@Table(name = "D247SEG_LOGINEXTERNO", schema = "CCONTROL")
public class LoginExterno implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_LOGINEXTERNO_ID", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
    @Column(name = "ID")
	private Long id;
	
	@Column(name = "S_USERNAME")
	private String username;
	
	@Column(name = "S_TOKEN")
	private String token;
	
	@Column(name = "S_NOMBRE")
	private String nombre;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "D_FECHA_ACCESO")
	private Date fechaAcceso;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public Date getFechaAcceso() {
		return fechaAcceso;
	}

	public void setFechaAcceso(Date fechaAcceso) {
		this.fechaAcceso = fechaAcceso;
	}

	public LoginExterno() {}
	
	
	
}
