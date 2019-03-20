package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.core.domain.capa.dto.CapaN;

public interface IContenedoraCapaNService {
	
	List<CapaN> getCapaN(NivelCapa nivelCapa, Long idCapaN);
	
}
