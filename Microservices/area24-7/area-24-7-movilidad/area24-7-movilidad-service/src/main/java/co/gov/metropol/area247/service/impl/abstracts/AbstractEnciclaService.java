package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.EstacionEnciclaMapper;
import co.gov.metropol.area247.repository.CicloRutaRepository;
import co.gov.metropol.area247.repository.DareviaZonaRepository;
import co.gov.metropol.area247.repository.EstacionEnciclaRepository;
import co.gov.metropol.area247.repository.TareviaEstacionEnciclaRepository;


public abstract class AbstractEnciclaService {
	
	@Autowired
	protected EstacionEnciclaMapper mapper;
	
	@Autowired
	protected EstacionEnciclaRepository repository;
	
	@Autowired
	protected DareviaZonaRepository zonaRepository;
	
	@Autowired
	protected TareviaEstacionEnciclaRepository EstacionRepository;
	
	@Autowired
	protected  CicloRutaRepository cicloRutaRepository;

}
