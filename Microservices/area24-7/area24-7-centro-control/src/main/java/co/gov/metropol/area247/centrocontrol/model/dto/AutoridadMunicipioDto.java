package co.gov.metropol.area247.centrocontrol.model.dto;


public class AutoridadMunicipioDto{
		
	private Long id;
    private Long idNodoArbol;
    private String municipio;
    private Long idAutoridadCompetente;

      
	public AutoridadMunicipioDto(Long id, Long idNodoArbol, String municipio, Long idAutoridadCompetente) {
		super();
		this.id = id;
		this.idNodoArbol = idNodoArbol;
		this.municipio = municipio;
		this.idAutoridadCompetente = idAutoridadCompetente;
	}
	

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdNodoArbol() {
		return idNodoArbol;
	}
	
	public void setIdNodoArbol(Long idNodoArbol) {
		this.idNodoArbol = idNodoArbol;
	}
	
	public String getMunicipio() {
		return municipio;
	}
	
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public Long getIdAutoridadCompetente() {
		return idAutoridadCompetente;
	}
	
	public void setIdAutoridadCompetente(Long idAutoridadCompetente) {
		this.idAutoridadCompetente = idAutoridadCompetente;
	}

}
