package co.gov.metropol.area247.entorno.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.core.gateway.MarkersProvider;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataAgua;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataAire;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataClima;
import co.gov.metropol.area247.entorno.model.Capas;
import co.gov.metropol.area247.entorno.service.IEntornoSiataSvc;

@Deprecated
@Service
public class IEntornoSiataSvcImpl implements IEntornoSiataSvc {
	
	@Autowired
	MarkersProvider markersProvider;
	
	@Autowired
	IContenedoraCapaService capaService;
	
	@Override
	public List<?> getStationsData(Long idCapa) throws Exception {
		Capas capa = Capas.obtenerPorId(idCapa.intValue());
		String urlRecurso = capaService.capaGetById(idCapa).getUrlRecurso();
		switch (capa) {
		case AIRE:
			List<?> stationsDataAireList = markersProvider.getStationsDataDirect(urlRecurso, new StationsDataAire());
			stationsDataAireList.forEach(System.out::println);
			return stationsDataAireList;
		case AGUA:
			return markersProvider.getStationsDataDirect(urlRecurso, new StationsDataAgua());
		case CLIMA:
			return markersProvider.getStationsDataDirect(urlRecurso, new StationsDataClima());
		default:
			return new ArrayList<>();
		}
	}
	
}
