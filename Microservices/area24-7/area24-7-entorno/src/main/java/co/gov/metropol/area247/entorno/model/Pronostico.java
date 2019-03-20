package co.gov.metropol.area247.entorno.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.gov.metropol.area247.contenedora.model.Icono;

@Entity
@Table(name="T247ENT_PRONOSTICO", schema="CONTENEDOR")
public class Pronostico {
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_PRONOSTICO_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private long id;
	
	@ManyToOne
	@JoinColumn(name="ID_TIPO_VENTANA")
	private VentanaPronostico ventanaPronostico;
	
	@OneToOne
	@JoinColumn(name="ID_ICONO", referencedColumnName="ID")
	private Icono icono;
	
	@Column(name="S_DESCRIPCION")
	private String descripcion;
	
	@Column(name="D_TIEMPO_INICIAL")
	private Date tiempoInicial;
	
	@Column(name="D_TIEMPO_FINAL")
	private Date tiempoFinal;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTACION")
	private Estacion estacion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public VentanaPronostico getVentanaPronostico() {
		return ventanaPronostico;
	}

	public void setVentanaPronostico(VentanaPronostico ventanaPronostico) {
		this.ventanaPronostico = ventanaPronostico;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getTiempoInicial() {
		return tiempoInicial;
	}

	public void setTiempoInicial(Date tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}

	public Date getTiempoFinal() {
		return tiempoFinal;
	}

	public void setTiempoFinal(Date tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}

	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	/**
	 * @param id
	 * @param ventanaPronostico
	 * @param icono
	 * @param descripcion
	 * @param tiempoInicial
	 * @param tiempoFinal
	 * @param estacion
	 */
	public Pronostico(long id, VentanaPronostico ventanaPronostico, Icono icono, String descripcion, Date tiempoInicial,
			Date tiempoFinal, Estacion estacion) {
		this.id = id;
		this.ventanaPronostico = ventanaPronostico;
		this.icono = icono;
		this.descripcion = descripcion;
		this.tiempoInicial = tiempoInicial;
		this.tiempoFinal = tiempoFinal;
		this.estacion = estacion;
	}
	/**
	 * 
	 */
	public Pronostico() {
		// TODO Auto-generated constructor stub
	}
	
}
