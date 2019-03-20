package co.gov.metropol.area247.centrocontrol.model.dto;

public class NodoArbolDto{
		
	private Long id;	
	private String nombre;	
	private String descripcion;	
    private String alias;
	private String instrucciones;
	private String instruccionesDetalladas;
	private int orden;
	private String formatoMedia;
	private Long idMultimediaImagen;
	private String urlMediaImagen;
	private String urlMediaVideoAudio;
	private String urlMediaSeleccionada;
	private Long idPadre;		
	private Long idAutoridadCompetente;
	
	private Long idIconoVigiaPen;
	private String urIconoVigiaPen;	
	private Long idIconoVigiaApr;
	private String urIconoVigiaApr;	
	private Long idIconoVigiaRec;
	private String urIconoVigiaRec;	
	private Long idIconoVigiaWin;
	private String urIconoVigiaWin;
	
	private boolean flagHijosDropdown;	
	private boolean tieneHijos;	
	
	
	public NodoArbolDto(Long id, String nombre, String descripcion, String alias,String instrucciones, 
			String instruccionesDetalladas, int orden, Long idMultimedia, Long idPadre, 
			boolean flagHijosDropdown, String urlMediaVideoAudio,String formatoMedia, 
			Long idAutoridadCompetente) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.alias = alias;
		this.instrucciones = instrucciones;
		this.instruccionesDetalladas = instruccionesDetalladas;
		this.orden = orden;												
		this.idPadre = idPadre;		
		this.flagHijosDropdown = flagHijosDropdown;
		this.formatoMedia = formatoMedia;
		this.urlMediaVideoAudio = urlMediaVideoAudio;
		this.idMultimediaImagen = idMultimedia;
		this.idAutoridadCompetente = idAutoridadCompetente;		
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

	public int getOrden() {
		return orden;
	}
	
	public void setOrden(int orden) {
		this.orden = orden;
	}
				
	public String getFormatoMedia() {
		return formatoMedia;
	}


	public void setFormatoMedia(String formatoMedia) {
		this.formatoMedia = formatoMedia;
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
	
	public String getUrlMediaSeleccionada() {
		return urlMediaSeleccionada;
	}

	public void setUrlMediaSeleccionada(String urlMediaSeleccionada) {
		this.urlMediaSeleccionada = urlMediaSeleccionada;
	}

	public Long getIdPadre() {
		return idPadre;
	}
	
	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
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

	public boolean isTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}
	
	
	
}
