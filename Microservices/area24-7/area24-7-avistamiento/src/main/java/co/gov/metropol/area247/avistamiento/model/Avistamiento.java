package co.gov.metropol.area247.avistamiento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.contenedora.model.Marcador;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table (name = "T247AVI_AVISTAMIENTO", schema="AVISTAM")
public class Avistamiento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="AVISTAM.SEQ_AVISTAMIENTO_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@NotNull
	@Column(name = "ID_USUARIO")
	Long idUsuario;

	@Column(name = "S_NOMBRE_COMUN")
	String nombreComun;
	
	@Column(name = "S_DESCRIPCION")
	String descripcion;
	
	@Column(name = "S_NOMBRE_CIENTIFICO")
	String nombreCientifico;
	
	@Column(name = "S_TIPO_AVISTAMIENTO")
	String tipoAvistamiento;
	
	@Column(name = "S_TIPO_ESPECIE")
	String tipoEspecie;
	
	@Column(name = "N_ESTADO")
	int estado;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Column(name = "D_PUBLICACION")
	private Date fechaCreacion;
    
    @OneToOne
    @JoinColumn(name = "ID_MARCADOR", referencedColumnName = "ID")
    private Marcador marcador;   
    
    @JsonProperty
	@ApiModelProperty(notes = "Establece si el avistamiento tendra historias o no", required = true)
	@NotNull
	@Column(name = "TIENE_HISTORIA")
	private boolean tieneHistoria;
    
	@Column(name = "ID_ESPECIE")
	Long idEspecie;
	
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreComun() {
		return nombreComun;
	}

	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreCientifico() {
		return nombreCientifico;
	}

	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getTipoAvistamiento() {
		return tipoAvistamiento;
	}

	public void setTipoAvistamiento(String tipoAvistamiento) {
		this.tipoAvistamiento = tipoAvistamiento;
	}

	public String getTipoEspecie() {
		return tipoEspecie;
	}

	public void setTipoEspecie(String tipoEspecie) {
		this.tipoEspecie = tipoEspecie;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isTieneHistoria() {
		return tieneHistoria;
	}

	public void setTieneHistoria(boolean tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}

	public Marcador getMarcador() {
		return marcador;
	}

	public void setMarcador(Marcador marcador) {
		this.marcador = marcador;
	}

	public Long getIdEspecie() {
		return idEspecie;
	}

	public void setIdEspecie(Long idEspecie) {
		this.idEspecie = idEspecie;
	}
													
}
