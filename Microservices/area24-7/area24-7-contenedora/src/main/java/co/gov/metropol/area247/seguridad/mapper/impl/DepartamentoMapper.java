package co.gov.metropol.area247.seguridad.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.seguridad.mapper.ISeguridadMapper;
import co.gov.metropol.area247.seguridad.model.Departamento;
import co.gov.metropol.area247.seguridad.model.dto.DepartamentoDto;

/**
 * 
 * @author ageofuentes
 *
 */
@Component("departamentoMapper")
public class DepartamentoMapper implements ISeguridadMapper<Departamento, DepartamentoDto> {

	@Override
	public Departamento modelToEntity(DepartamentoDto model) {
		Departamento entity = new Departamento();
		ModelMapper mapper = new ModelMapper();
		entity = mapper.map(model, Departamento.class);
		return entity;
	}

	@Override
	public DepartamentoDto entityToModel(Departamento entity) {
		DepartamentoDto model = new DepartamentoDto();
		ModelMapper mapper = new ModelMapper();
		model = mapper.map(entity, DepartamentoDto.class);
		return model;
	}

}
