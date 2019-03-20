package co.gov.metropol.area247.seguridad.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.seguridad.mapper.ISeguridadMapper;
import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.model.dto.MunicipioDto;

@Component("municipioMapper")
public class MunicipioMapper implements ISeguridadMapper<Municipio, MunicipioDto> {

	@Override
	public Municipio modelToEntity(MunicipioDto model) {
		Municipio entity = new Municipio();
		ModelMapper mapper = new ModelMapper();
		entity = mapper.map(model, Municipio.class);
		return entity;
	}

	@Override
	public MunicipioDto entityToModel(Municipio entity) {
		MunicipioDto model = new MunicipioDto();
		ModelMapper mapper = new ModelMapper();
		model = mapper.map(entity, MunicipioDto.class);
		return model;
	}

}
