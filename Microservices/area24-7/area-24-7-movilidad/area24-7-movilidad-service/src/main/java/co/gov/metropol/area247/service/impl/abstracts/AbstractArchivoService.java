package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.ArchivoMapper;
import co.gov.metropol.area247.repository.ArchivoRepository;

public abstract class AbstractArchivoService {
	
	@Autowired
	protected ArchivoMapper mapper;
	
	@Autowired
	protected ArchivoRepository repository;

}
