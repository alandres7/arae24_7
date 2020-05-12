package co.gov.metropol.area247.covid19.entity;

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
public class Layer implements Serializable {
	
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
	@Column(name = "S_NOMBRE")
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
	private Boolean activo;

	@JsonProperty
	@ApiModelProperty(notes = "Establece si la capa se mostrará por defecto luego del ingreso a la aplicación", required = true)
	@Column(name = "N_FAVORITO")
	private Boolean favorito;

	@ApiModelProperty(notes = "Describe la fecha de la última actualización realizada", required = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "D_ULTIMA_ACTUALIZACION")
	private Date ultimaActualizacion;

	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icon icono;

	@OneToOne
	@JoinColumn(name = "ID_ICONO_MARCADOR", referencedColumnName = "ID")
	private Icon iconoMarcador;

	@OneToMany(mappedBy = "capa", cascade = {CascadeType.ALL})
	private List<Marker> marcadores;

	@OneToMany(mappedBy = "capa", cascade = CascadeType.ALL)
	private List<Category> categorias;

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
	
	@Column(name = "S_ALIAS_IMAGEN2")
	private String aliasImagen2;
	
	@Column(name = "N_HISTORIA_AVISTAMIENTO")
	private Boolean contieneHistoria;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CAPA", referencedColumnName = "ID")
	private LayerType tipoCapa;

	@ManyToOne
	@JoinColumn(name = "ID_APLICACION")
	@JsonIgnore
	private App aplicacion;
	
	@Column(name="N_POLIGONO")
	private Boolean poligono; 
	
	@Column(name = "S_COLOR_POLIGONO")
	private String colorPoligono;
	
	@Column(name = "N_FICHA_CARACTERIZACION")
	private Boolean fichaCaracterizacion;
	
	@Column(name="S_URL_RECURSO_ALTERNO")
	private String urlRecursoAlterno;
	
	
	@Column(name = "ID_ARBOL_DECISION")
	private Long idArbolDecision;
	
	@Column(name = "N_ORDEN")
	private Integer orden;
	
	@Column(name = "N_FLG_PINTAR_SOLO_BORDE")
	private Boolean pintarSoloBorde;

	public Icon getIcono() {
		return icono;
	}

	public void setIcono(Icon icono) {
		this.icono = icono;
	}

	public Icon getIconoMarcador() {
		return iconoMarcador;
	}

	public void setIconoMarcador(Icon iconoMarcador) {
		this.iconoMarcador = iconoMarcador;
	}

	public List<Marker> getMarcadores() {
		return marcadores;
	}

	public void setMarcadores(List<Marker> marcadores) {
		this.marcadores = marcadores;
	}

	public List<Category> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Category> categorias) {
		this.categorias = categorias;
	}

	public LayerType getTipoCapa() {
		return tipoCapa;
	}

	public void setTipoCapa(LayerType tipoCapa) {
		this.tipoCapa = tipoCapa;
	}

	public App getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(App aplicacion) {
		this.aplicacion = aplicacion;
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getFavorito() {
		return favorito;
	}

	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getUrlRecurso() {
		return urlRecurso;
	}

	public void setUrlRecurso(String urlRecurso) {
		this.urlRecurso = urlRecurso;
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

	public String getAliasImagen2() {
		return aliasImagen2;
	}

	public void setAliasImagen2(String aliasImagen2) {
		this.aliasImagen2 = aliasImagen2;
	}

	public Boolean getContieneHistoria() {
		return contieneHistoria;
	}

	public void setContieneHistoria(Boolean contieneHistoria) {
		this.contieneHistoria = contieneHistoria;
	}

	public Boolean getPoligono() {
		return poligono;
	}

	public void setPoligono(Boolean poligono) {
		this.poligono = poligono;
	}

	public String getColorPoligono() {
		return colorPoligono;
	}

	public void setColorPoligono(String colorPoligono) {
		this.colorPoligono = colorPoligono;
	}

	public Boolean getFichaCaracterizacion() {
		return fichaCaracterizacion;
	}

	public void setFichaCaracterizacion(Boolean fichaCaracterizacion) {
		this.fichaCaracterizacion = fichaCaracterizacion;
	}

	public String getUrlRecursoAlterno() {
		return urlRecursoAlterno;
	}

	public void setUrlRecursoAlterno(String urlRecursoAlterno) {
		this.urlRecursoAlterno = urlRecursoAlterno;
	}

	public Long getIdArbolDecision() {
		return idArbolDecision;
	}

	public void setIdArbolDecision(Long idArbolDecision) {
		this.idArbolDecision = idArbolDecision;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Boolean getPintarSoloBorde() {
		return pintarSoloBorde;
	}

	public void setPintarSoloBorde(Boolean pintarSoloBorde) {
		this.pintarSoloBorde = pintarSoloBorde;
	}
	
	

}
