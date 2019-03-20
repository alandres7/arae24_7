package co.gov.metropol.area247.centrocontrol.model;


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
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import co.gov.metropol.area247.contenedora.model.Multimedia;

@Entity
@Table(name = "T247CCN_NODO_ARBOL", schema = "CCONTROL")
public class NodoArbol implements Persistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_NODO_ARBOL_ID", allocationSize=1)
	private Long id;
	
	@Column(name = "S_NOMBRE")
	private String nombre;
	
	@Column(name = "N_ORDEN")
	private int orden;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "ID_PADRE")
	private Long idPadre;
	
	@JoinColumn(name = "ID_MULTIMEDIA")
	private Long idMultimedia;
		
	@Column(name = "ID_ARBOL")
	private Long idArbol;
	
	@Column(name = "ID_AUTORIDAD_COMPETENTE")
	private Long idAutoridadCompetente;
	
	@Column(name = "S_ALIAS")
	private String alias;
	
	@Column(name = "S_INSTRUCCIONES")
	private String instrucciones;
	
	@Column(name = "S_INSTRUCCIONES_DETALLADAS")
	private String instruccionesDetalladas;
	
	@Column(name = "FLAG_HIJOS_DROPDOWN")
	private boolean flagHijosDropdown;
	
	@Column(name = "S_URL_MEDIA")
	private String urlMediaVideoAudio;
	
	@Column(name = "S_FORMATO_MEDIA")
	private String formatoMedia;
	
	

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public boolean isNew() {
		return (getId()==null);
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public Long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMultimedia() {
		return idMultimedia;
	}

	public void setIdMultimedia(Long idMultimedia) {
		this.idMultimedia = idMultimedia;
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

	public Long getIdAutoridadCompetente() {
		return idAutoridadCompetente;
	}

	public void setIdAutoridadCompetente(Long idAutoridadCompetente) {
		this.idAutoridadCompetente = idAutoridadCompetente;
	}

	public Long getIdArbol() {
		return idArbol;
	}

	public void setIdArbol(Long idArbol) {
		this.idArbol = idArbol;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public String getInstruccionesDetalladas() {
		return instruccionesDetalladas;
	}

	public void setInstruccionesDetalladas(String instruccionesDetalladas) {
		this.instruccionesDetalladas = instruccionesDetalladas;
	}

	public boolean isFlagHijosDropdown() {
		return flagHijosDropdown;
	}

	public void setFlagHijosDropdown(boolean flagHijosDropdown) {
		this.flagHijosDropdown = flagHijosDropdown;
	}
	
	public String getUrlMediaVideoAudio() {
		return urlMediaVideoAudio;
	}

	public void setUrlMediaVideoAudio(String urlMediaVideoAudio) {
		this.urlMediaVideoAudio = urlMediaVideoAudio;
	}

	public String getFormatoMedia() {
		return formatoMedia;
	}

	public void setFormatoMedia(String formatoMedia) {
		this.formatoMedia = formatoMedia;
	}
			
}
