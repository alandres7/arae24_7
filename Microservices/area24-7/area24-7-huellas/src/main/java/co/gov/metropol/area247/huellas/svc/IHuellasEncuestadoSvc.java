package co.gov.metropol.area247.huellas.svc;

import co.gov.metropol.area247.huellas.model.EncuestadoDto;

public interface IHuellasEncuestadoSvc {
	
	public abstract EncuestadoDto registro(EncuestadoDto dto);
	
	public abstract EncuestadoDto getEncuestado(Long dto); 
	
}
