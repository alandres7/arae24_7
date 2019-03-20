package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;
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

@Entity
@Table(name = "D247CON_CATEGORIA", schema = "CONTENEDOR")
public class Categoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_CATEGORIA_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(name = "S_NOMBRE")
	private String nombre;

	@Size(max = 2000)
	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@OneToMany(mappedBy="categoria", cascade=CascadeType.ALL)
	private List<Marcador> marcadores;

	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID", nullable = true)
	private Icono icono;

	@OneToOne
	@JoinColumn(name = "ID_ICONO_MARCADOR", referencedColumnName = "ID", nullable = true)
	private Icono iconoMarcador;

	@Column(name = "S_URL_RECURSO", unique = true)
	private String urlRecurso;
	
	@Column(name = "S_ALIAS_NOMBRE")
	private String aliasNombre;

	@Column(name = "S_ALIAS_MUNICIPIO")
	private String aliasMunicipio;

	@Column(name = "S_ALIAS_DESCRIPCION")
	private String aliasDescripcion;
	
	@Column(name = "S_ALIAS_TIPO")
	private String aliasTipo;
	
	@Column(name = "S_ALIAS_DIRECCION")
	private String aliasDireccion;
	
	@Column(name = "S_ALIAS_IMAGEN")
	private String aliasImagen;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CATEGORIA", referencedColumnName = "ID")
	private TipoCapa tipoCategoria;
	
	@Column(name = "N_POLIGONO")
	private boolean poligono;
	
	@Column(name = "N_FICHA_CARACTERIZACION")
	private boolean fichaCaracterizacion;
	
	@Column(name = "S_FICHA_CARACTERIZACION")
	private String respuestaFichaCarac;
	
	@Column(name = "N_MSG_ORDENAMIENTO")
	private boolean msgOrdenamiento;
	
	@Column(name = "N_AREA_PROTEGIDA")
	private boolean areaProtegida;
	
	@Column(name = "N_SUELO_PROTECCION")
	private boolean sueloProteccion;
	
	@Column(name = "N_HISTORIA_AVISTAMIENTO")
	private Boolean tieneHistoria;
	
	@ManyToOne
	@JoinColumn(name = "ID_CAPA", referencedColumnName = "ID")
	private Capa capa;	
	
	

	public TipoCapa getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(TipoCapa tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public String getAliasTipo() {
		return aliasTipo;
	}

	public void setAliasTipo(String aliasTipo) {
		this.aliasTipo = aliasTipo;
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

	public List<Marcador> getMarcadores() {
		return marcadores;
	}

	public void setMarcadores(List<Marcador> marcadores) {
		this.marcadores = marcadores;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Icono getIconoMarcador() {
		return iconoMarcador;
	}

	public void setIconoMarcador(Icono iconoMarcador) {
		this.iconoMarcador = iconoMarcador;
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

	public Capa getCapa() {
		return capa;
	}

	public void setCapa(Capa capa) {
		this.capa = capa;
	}

	public boolean isFichaCaracterizacion() {
		return fichaCaracterizacion;
	}

	public void setFichaCaracterizacion(boolean fichaCaracterizacion) {
		this.fichaCaracterizacion = fichaCaracterizacion;
	}

	public String getRespuestaFichaCarac() {
		return respuestaFichaCarac;
	}

	public void setRespuestaFichaCarac(String respuestaFichaCarac) {
		this.respuestaFichaCarac = respuestaFichaCarac;
	}

	public boolean isPoligono() {
		return poligono;
	}

	public void setPoligono(boolean poligono) {
		this.poligono = poligono;
	}

	public boolean isMsgOrdenamiento() {
		return msgOrdenamiento;
	}

	public void setMsgOrdenamiento(boolean msgOrdenamiento) {
		this.msgOrdenamiento = msgOrdenamiento;
	}

	public boolean isAreaProtegida() {
		return areaProtegida;
	}

	public void setAreaProtegida(boolean areaProtegida) {
		this.areaProtegida = areaProtegida;
	}

	public boolean isSueloProteccion() {
		return sueloProteccion;
	}

	public void setSueloProteccion(boolean sueloProteccion) {
		this.sueloProteccion = sueloProteccion;
	}

	public Boolean getTieneHistoria() {
		return tieneHistoria;
	}

	public void setTieneHistoria(Boolean tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}
}