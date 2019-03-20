package co.gov.metropol.area247.batch.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.VehiculoRuntMapper;
import co.gov.metropol.area247.repository.VehiculoRuntRepository;

public abstract class AbstractRuntItemProcessor {
	
	@Autowired
	protected VehiculoRuntMapper mapper;
	
	@Autowired
	protected VehiculoRuntRepository repository;

}
