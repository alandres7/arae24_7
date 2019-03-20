package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.EstructuraDeArchivoMapper;
import co.gov.metropol.area247.repository.EstructuraDeArchivoRespository;

public abstract class AbstractEstructuraDeArchivoService {
	
	@Autowired
	protected EstructuraDeArchivoMapper mapper;
	
	@Autowired
	protected EstructuraDeArchivoRespository repository;

}
