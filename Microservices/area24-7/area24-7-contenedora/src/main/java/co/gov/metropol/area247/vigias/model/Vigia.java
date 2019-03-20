package co.gov.metropol.area247.vigias.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.model.Multimedia;

@Entity
@Table (name = "T247VIG_REPORTE_VIGIA", schema="VIGIAS")
public class Vigia implements  Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_REPORTE_VIGIA_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@Column(name = "D_FECHA_REPORTE")
	private Date fechaReporte;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "S_DIRECCION")
	private String direccion;
		
	@Column(name = "S_ESTADO_REPORTE")
	private String estado;
		
	@Column(name = "S_ACTIVO")
	private boolean activo;
		
	@Column(name = "S_RADICADO_SIM")
	private String radicadoSim;
	
	@OneToOne
	@JoinColumn(name = "ID_MULTIMEDIA", referencedColumnName = "ID")
	private Multimedia multimedia;
	
	@NotNull
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@OneToOne
	@JoinColumn(name = "ID_MARCADOR", referencedColumnName = "ID")
	private Marcador marcador;
	
	@Column(name = "ID_NODO_ARBOL")
	private Long idNodoArbol;
	
	@Column(name = "S_RECORRIDO_ARBOL")
	private String recorridoArbol;	
	
	@Column(name = "ID_ICONO_VENTANA")
	private Long idIconoVentana;
	
	@OneToMany(mappedBy = "vigia", cascade = CascadeType.ALL)
	private List<Multimedia> multimediaList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(Date fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getRadicadoSim() {
		return radicadoSim;
	}

	public void setRadicadoSim(String radicadoSim) {
		this.radicadoSim = radicadoSim;
	}

	public Multimedia getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(Multimedia multimedia) {
		this.multimedia = multimedia;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Marcador getMarcador() {
		return marcador;
	}

	public void setMarcador(Marcador marcador) {
		this.marcador = marcador;
	}

	public Long getIdNodoArbol() {
		return idNodoArbol;
	}

	public void setIdNodoArbol(Long idNodoArbol) {
		this.idNodoArbol = idNodoArbol;
	}

	public String getRecorridoArbol() {
		return recorridoArbol;
	}

	public void setRecorridoArbol(String recorridoArbol) {
		this.recorridoArbol = recorridoArbol;
	}

	public Long getIdIconoVentana() {
		return idIconoVentana;
	}

	public void setIdIconoVentana(Long idIconoVentana) {
		this.idIconoVentana = idIconoVentana;
	}
		
}
