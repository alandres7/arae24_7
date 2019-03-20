package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "D247CON_SUBCATEGORIA", schema = "CONTENEDOR")
public class Subcategoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	  @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_SUBCATEGORIA_ID", allocationSize=1)
	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	  private Long id;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "S_NOMBRE")
	private String nombre;
	
	@Size(max = 2000)
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "D247CON_SUBCATEGORIA_MARCADOR",
			joinColumns = @JoinColumn(name = "ID_SUBCATEGORIA"),
			inverseJoinColumns = @JoinColumn(name = "ID_MARCADOR"))
	@SequenceGenerator(name="SEQ_GEN_2", sequenceName="SEQ_SUBCATEGORIA_MARCADOR_ID", allocationSize=1)
	@CollectionId(columns = @Column(name="ID"), type= @Type(type="long"), generator = "SEQ_GEN_2" )
	private List<Marcador> marcadores;
	
	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icono icono;

	@OneToOne
	@JoinColumn(name = "ID_ICONO_MARCADOR", referencedColumnName = "ID")
	private Icono iconoMarcador;
	
	@Column(name = "S_URL_RECURSO", unique = true)
	private String urlRecurso;
	
	@Column(name = "S_ALIAS_NOMBRE")
	private String aliasNombre;

	@Column(name = "S_ALIAS_NOMBRE_ITEM")
	private String aliasNombreItem;

	@Column(name = "S_ALIAS_DESCRIPCION_ITEM")
	private String aliasDescripcionItem;

	@Column(name = "S_ALIAS_ID")
	private String aliasId;
	
	@JsonIgnore
	@ManyToOne
	@JoinTable(name = "D247CON_CATEGORIA_SUBCATEGORIA", joinColumns = @JoinColumn(name = "ID_SUBCATEGORIA", referencedColumnName = "ID")
		, inverseJoinColumns = @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID"))
	@SequenceGenerator(name = "SEQ_GEN_3", sequenceName = "SEQ_CATEGORIA_SUBCATEGORIA_ID", allocationSize = 1)
	//@CollectionId(columns = @Column(name = "ID"), type = @Type(type = "long"), generator = "SEQ_GEN_3")
	Categoria categoria;
	
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

	public String getAliasNombreItem() {
		return aliasNombreItem;
	}

	public void setAliasNombreItem(String aliasNombreItem) {
		this.aliasNombreItem = aliasNombreItem;
	}

	public String getAliasDescripcionItem() {
		return aliasDescripcionItem;
	}

	public void setAliasDescripcionItem(String aliasDescripcionItem) {
		this.aliasDescripcionItem = aliasDescripcionItem;
	}

	public String getAliasId() {
		return aliasId;
	}

	public void setAliasId(String aliasId) {
		this.aliasId = aliasId;
	}
	
//	public Categoria getCategoria() {
//		return categoria;
//	}
//
//	public void setCategoria(Categoria categoria) {
//		this.categoria = categoria;
//	}
	
}
