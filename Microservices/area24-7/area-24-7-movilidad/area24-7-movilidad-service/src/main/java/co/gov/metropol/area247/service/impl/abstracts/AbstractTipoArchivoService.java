package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.TipoArchivoMapper;
import co.gov.metropol.area247.repository.TipoArchivoRepository;

public abstract class AbstractTipoArchivoService {
	
	@Autowired
	protected TipoArchivoMapper mapper;
	
	@Autowired
	protected TipoArchivoRepository repository;

}
