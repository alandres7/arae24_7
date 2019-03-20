package co.gov.metropol.area247.entorno.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;

@Entity
@Table(name = "T247ENT_MEDICION", schema = "CONTENEDOR")
public class Medicion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_MEDICION_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
	
	@Column(name = "ID_CAPA")
	private Long idCapa;
	
	@Column(name = "S_NOMBRE")
	private String nombre;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Column(name = "S_COLOR")
	private String color;
	
	@Column(name = "S_SIGNIFICADO")
	private String significado;
	
	@Column(name = "S_RECOMENDACION")
	private String recomendacion;
	
	@Column(name = "N_ESCALA_INCIAL")
	private float escalaInicial;
	
	@Column(name = "N_ESCALA_FINAL")
	private float escalaFinal;
	
	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icono icono;
	
	@OneToOne
	@JoinColumn(name = "ID_VENTANA_INFORMACION", referencedColumnName = "ID")
	private VentanaInformacion ventanaInformacion;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSignificado() {
		return significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}

	public String getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	public double getEscalaInicial() {
		return escalaInicial;
	}

	public void setEscalaInicial(float escalaInicial) {
		this.escalaInicial = escalaInicial;
	}

	public double getEscalaFinal() {
		return escalaFinal;
	}

	public void setEscalaFinal(float escalaFinal) {
		this.escalaFinal = escalaFinal;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public VentanaInformacion getVentanaInformacion() {
		return ventanaInformacion;
	}

	public void setVentanaInformacion(VentanaInformacion ventanaInformacion) {
		this.ventanaInformacion = ventanaInformacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(Long idCapa) {
		this.idCapa = idCapa;
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
	
}
