package co.gov.metropol.area247.huellas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.gov.metropol.area247.contenedora.model.Marcador;

@Entity
@Table(name="D247HUE_POSCONSUMO", schema="HUELLAS")
public class Posconsumo {
	
	@Id
	@NotNull
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_POSCONSUMO_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ID_MARCADOR")
	private Marcador marcador;
	
	@Column(name="S_NOMBRE")
	private String nombre;
	
	@Column(name="S_DIRECCION")
	private String direccion;
	
	@Column(name="S_EMAIL")
	private String email;
	
	@Column(name="S_CONTACTO")
	private String contacto;
	
	@Column(name="S_HORARIO")
	private String horario;
	
	@Column(name="S_RESIDUO")
	private String residuo;
	
	@Column(name="S_TIPO_RESIDUO")
	private String tipoResiduo;
	
	@Column(name="S_PAIS")
	private String pais;
	
	@Column(name="S_DEPARTAMENTO")
	private String departamento;
	
	@Column(name="S_CIUDAD")
	private String ciudad;
	
	@Column(name="S_PROGRAMA")
	private String programa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Marcador getMarcador() {
		return marcador;
	}

	public void setMarcador(Marcador marcador) {
		this.marcador = marcador;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getResiduo() {
		return residuo;
	}

	public void setResiduo(String residuo) {
		this.residuo = residuo;
	}

	public String getTipoResiduo() {
		return tipoResiduo;
	}

	public void setTipoResiduo(String tipoResiduo) {
		this.tipoResiduo = tipoResiduo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	/**
	 * @param id
	 * @param marcador
	 * @param nombre
	 * @param direccion
	 * @param email
	 * @param contacto
	 * @param horario
	 * @param residuo
	 * @param tipoResiduo
	 * @param pais
	 * @param departamento
	 * @param ciudad
	 * @param programa
	 */
	public Posconsumo(Long id, Marcador marcador, String nombre, String direccion, String email, String contacto,
			String horario, String residuo, String tipoResiduo, String pais, String departamento, String ciudad,
			String programa) {
		this.id = id;
		this.marcador = marcador;
		this.nombre = nombre;
		this.direccion = direccion;
		this.email = email;
		this.contacto = contacto;
		this.horario = horario;
		this.residuo = residuo;
		this.tipoResiduo = tipoResiduo;
		this.pais = pais;
		this.departamento = departamento;
		this.ciudad = ciudad;
		this.programa = programa;
	}

	/**
	 * 
	 */
	public Posconsumo() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
