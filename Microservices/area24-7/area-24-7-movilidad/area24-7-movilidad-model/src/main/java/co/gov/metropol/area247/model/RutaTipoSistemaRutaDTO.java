 package co.gov.metropol.area247.model;

public class RutaTipoSistemaRutaDTO {
	
	private Long idTipoSistemaRuta;

	private Long idRuta;
	
	private String activo;
	
	public RutaTipoSistemaRutaDTO() {
		this.activo = "-"; 
	}

	public Long getIdTipoSistemaRuta() {
		return idTipoSistemaRuta;
	}

	public void setIdTipoSistemaRuta(Long idTipoSistemaRuta) {
		this.idTipoSistemaRuta = idTipoSistemaRuta;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		RutaTipoSistemaRutaDTO dto = (RutaTipoSistemaRutaDTO) obj;
		
		return (this.getIdRuta().equals(dto.getIdRuta())
				&& this.getIdTipoSistemaRuta().equals(dto.getIdTipoSistemaRuta()));
	}
	
	@Override
	public int hashCode() {
		return this.idRuta.hashCode();
	}
}
