package co.gov.metropol.area247.contenedora.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "D247CON_CAPA", schema = "CONTENEDOR")
public class Capa implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_CAPA_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long id;

	@JsonProperty
	@ApiModelProperty(notes = "Nombre identificador único de la capa", required = true)
	@NotNull
	@Size(max = 100)
	@Column(name = "S_NOMBRE", unique = true)
	private String nombre;

	@JsonProperty
	@ApiModelProperty(notes = "Descripción de en qué consiste la capa", required = false)
	@Size(max = 2000)
	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@JsonProperty
	@ApiModelProperty(notes = "Establece si la capa se encuentra activa para visualización", required = true)
	@NotNull
	@Column(name = "N_ACTIVO")
	private boolean activo;

	@JsonProperty
	@ApiModelProperty(notes = "Establece si la capa se mostrará por defecto luego del ingreso a la aplicación", required = true)
	@NotNull
	@Column(name = "N_FAVORITO")
	private boolean favorito;

	@ApiModelProperty(notes = "Describe la fecha de la última actualización realizada", required = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "D_ULTIMA_ACTUALIZACION")
	private Date ultimaActualizacion;

	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icono icono;

	@OneToOne
	@JoinColumn(name = "ID_ICONO_MARCADOR", referencedColumnName = "ID")
	private Icono iconoMarcador;

	@OneToMany(mappedBy = "capa", cascade = CascadeType.ALL)
	private List<Marcador> marcadores;

	@OneToMany(mappedBy = "capa", cascade = CascadeType.ALL)
	private List<Categoria> categorias;

	@Column(name = "S_URL_RECURSO", unique = true)
	private String urlRecurso;

	@Column(name = "S_ALIAS_NOMBRE")
	private String aliasNombre;

	@Column(name = "S_ALIAS_MUNICIPIO")
	private String aliasMunicipio;

	@Column(name = "S_ALIAS_DESCRIPCION")
	private String aliasDescripcion;

	@Column(name = "S_ALIAS_CATEGORIA")
	private String aliasCategoria;
	
	@Column(name = "S_ALIAS_DIRECCION")
	private String aliasDireccion;
	
	@Column(name = "S_ALIAS_IMAGEN")
	private String aliasImagen;
	
	@Column(name = "N_HISTORIA_AVISTAMIENTO")
	private Boolean contieneHistoria;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CAPA", referencedColumnName = "ID")
	private TipoCapa tipoCapa;

	@ManyToOne
	@JoinColumn(name = "ID_APLICACION")
	@JsonIgnore
	private Aplicacion aplicacion;
	
	@Column(name="N_POLIGONO")
	private boolean poligono; 
	
	@Column(name = "N_FICHA_CARACTERIZACION")
	private boolean fichaCaracterizacion;
	
	@Column(name="S_URL_RECURSO_ALTERNO")
	private String urlRecursoAlterno;

	public Capa() {

	}

	public Capa(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

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

	public String getUrlRecurso() {
		return urlRecurso;
	}

	public void setUrlRecurso(String urlRecurso) {
		this.urlRecurso = urlRecurso;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public List<Marcador> getMarcadores() {
		return marcadores;
	}

	public void setMarcadores(List<Marcador> marcadores) {
		this.marcadores = marcadores;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Icono getIconoMarcador() {
		return iconoMarcador;
	}

	public void setIconoMarcador(Icono iconoMarcador) {
		this.iconoMarcador = iconoMarcador;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getAliasNombre() {
		return aliasNombre;
	}

	public void setAliasNombre(String aliasNombre) {
		this.aliasNombre = aliasNombre;
	}

	public String getAliasMunicipio() {
		return aliasMunicipio;
	}

	public void setAliasMunicipio(String aliasMunicipio) {
		this.aliasMunicipio = aliasMunicipio;
	}

	public String getAliasDescripcion() {
		return aliasDescripcion;
	}

	public void setAliasDescripcion(String aliasDescripcion) {
		this.aliasDescripcion = aliasDescripcion;
	}

	public String getAliasCategoria() {
		return aliasCategoria;
	}

	public void setAliasCategoria(String aliasCategoria) {
		this.aliasCategoria = aliasCategoria;
	}
		
	public String getAliasDireccion() {
		return aliasDireccion;
	}

	public void setAliasDireccion(String aliasDireccion) {
		this.aliasDireccion = aliasDireccion;
	}

	public String getAliasImagen() {
		return aliasImagen;
	}

	public void setAliasImagen(String aliasImagen) {
		this.aliasImagen = aliasImagen;
	}

	public TipoCapa getTipoCapa() {
		return tipoCapa;
	}

	public void setTipoCapa(TipoCapa tipoCapa) {
		this.tipoCapa = tipoCapa;
	}
	
	public boolean isPoligono() {
		return poligono;
	}

	public void setPoligono(boolean poligono) {
		this.poligono = poligono;
	}

	public boolean isFichaCaracterizacion() {
		return fichaCaracterizacion;
	}

	public void setFichaCaracterizacion(boolean fichaCaracterizacion) {
		this.fichaCaracterizacion = fichaCaracterizacion;
	}

	public Boolean getContieneHistoria() {
		return contieneHistoria;
	}

	public void setContieneHistoria(Boolean contieneHistoria) {
		this.contieneHistoria = contieneHistoria;
	}

	public String getUrlRecursoAlterno() {
		return urlRecursoAlterno;
	}

	public void setUrlRecursoAlterno(String urlRecursoAlterno) {
		this.urlRecursoAlterno = urlRecursoAlterno;
	}

}
