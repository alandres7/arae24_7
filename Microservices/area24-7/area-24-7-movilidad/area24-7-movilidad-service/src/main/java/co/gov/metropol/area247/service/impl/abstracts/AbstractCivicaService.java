package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.PuntoTarjetaCivicaMapper;
import co.gov.metropol.area247.repository.PuntoTarjetaCivicaRepository;

public abstract class AbstractCivicaService {
	
	@Autowired
	protected PuntoTarjetaCivicaMapper mapper;
	
	@Autowired
	protected PuntoTarjetaCivicaRepository civicaRepository;
}
