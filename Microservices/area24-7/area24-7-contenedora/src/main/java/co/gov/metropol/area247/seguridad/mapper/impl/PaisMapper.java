package co.gov.metropol.area247.seguridad.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.seguridad.mapper.ISeguridadMapper;
import co.gov.metropol.area247.seguridad.model.Pais;
import co.gov.metropol.area247.seguridad.model.dto.PaisDto;

/**
 * 
 * @author ageofuentes
 *
 */
@Component("paisMapper")
public class PaisMapper implements ISeguridadMapper<Pais, PaisDto> {

	@Override
	public Pais modelToEntity(PaisDto model) {
		Pais entity = new Pais();
		ModelMapper mapper = new ModelMapper();
		entity = mapper.map(model, Pais.class);
		return entity;
	}

	@Override
	public PaisDto entityToModel(Pais entity) {
		PaisDto model = new PaisDto();
		ModelMapper mapper = new ModelMapper();
		model = mapper.map(entity, PaisDto.class);
		return model;
	}
	
}
