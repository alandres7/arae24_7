package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class NodoArbol {
	
	private Long  id;     
    private String nombre; 
    private String descripcion;
    private String alias;
	private String instrucciones;
	private String instruccionesDetalladas;
    private String orden;
    private String formatoMedia;
    private Long idMultimediaImagen;
    private String urlMediaImagen;	
    private String urlMediaVideoAudio;
    private Long  idPadre; 
    private Boolean tieneHijos;
    private Long  idAutoridadCompetente; 
    
    private Long idIconoVigiaPen;
	private String urIconoVigiaPen;	
	private Long idIconoVigiaApr;
	private String urIconoVigiaApr;	
	private Long idIconoVigiaRec;
	private String urIconoVigiaRec;	
	private Long idIconoVigiaWin;
	private String urIconoVigiaWin;
    
	private boolean flagHijosDropdown;	
	
	
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

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}	
						
	public Long getIdMultimediaImagen() {
		return idMultimediaImagen;
	}

	public void setIdMultimediaImagen(Long idMultimediaImagen) {
		this.idMultimediaImagen = idMultimediaImagen;
	}

	public String getUrlMediaImagen() {
		return urlMediaImagen;
	}

	public void setUrlMediaImagen(String urlMediaImagen) {
		this.urlMediaImagen = urlMediaImagen;
	}

	public String getUrlMediaVideoAudio() {
		return urlMediaVideoAudio;
	}

	public void setUrlMediaVideoAudio(String urlMediaVideoAudio) {
		this.urlMediaVideoAudio = urlMediaVideoAudio;
	}

	public Long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}

	public Boolean getTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(Boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}

	public Long getIdAutoridadCompetente() {
		return idAutoridadCompetente;
	}

	public void setIdAutoridadCompetente(Long idAutoridadCompetente) {
		this.idAutoridadCompetente = idAutoridadCompetente;
	}
		
	public Long getIdIconoVigiaPen() {
		return idIconoVigiaPen;
	}

	public void setIdIconoVigiaPen(Long idIconoVigiaPen) {
		this.idIconoVigiaPen = idIconoVigiaPen;
	}

	public String getUrIconoVigiaPen() {
		return urIconoVigiaPen;
	}

	public void setUrIconoVigiaPen(String urIconoVigiaPen) {
		this.urIconoVigiaPen = urIconoVigiaPen;
	}

	public Long getIdIconoVigiaApr() {
		return idIconoVigiaApr;
	}

	public void setIdIconoVigiaApr(Long idIconoVigiaApr) {
		this.idIconoVigiaApr = idIconoVigiaApr;
	}

	public String getUrIconoVigiaApr() {
		return urIconoVigiaApr;
	}

	public void setUrIconoVigiaApr(String urIconoVigiaApr) {
		this.urIconoVigiaApr = urIconoVigiaApr;
	}

	public Long getIdIconoVigiaRec() {
		return idIconoVigiaRec;
	}

	public void setIdIconoVigiaRec(Long idIconoVigiaRec) {
		this.idIconoVigiaRec = idIconoVigiaRec;
	}

	public String getUrIconoVigiaRec() {
		return urIconoVigiaRec;
	}

	public void setUrIconoVigiaRec(String urIconoVigiaRec) {
		this.urIconoVigiaRec = urIconoVigiaRec;
	}

	public Long getIdIconoVigiaWin() {
		return idIconoVigiaWin;
	}

	public void setIdIconoVigiaWin(Long idIconoVigiaWin) {
		this.idIconoVigiaWin = idIconoVigiaWin;
	}

	public String getUrIconoVigiaWin() {
		return urIconoVigiaWin;
	}

	public void setUrIconoVigiaWin(String urIconoVigiaWin) {
		this.urIconoVigiaWin = urIconoVigiaWin;
	}

	public boolean isFlagHijosDropdown() {
		return flagHijosDropdown;
	}

	public void setFlagHijosDropdown(boolean flagHijosDropdown) {
		this.flagHijosDropdown = flagHijosDropdown;
	}

	public String getFormatoMedia() {
		return formatoMedia;
	}

	public void setFormatoMedia(String formatoMedia) {
		this.formatoMedia = formatoMedia;
	}
									
}