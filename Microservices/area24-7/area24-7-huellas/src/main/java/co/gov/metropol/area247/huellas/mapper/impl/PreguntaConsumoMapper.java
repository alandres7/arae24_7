package co.gov.metropol.area247.huellas.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.huellas.entity.PreguntaConsumo;
import co.gov.metropol.area247.huellas.mapper.IHuellasMapper;
import co.gov.metropol.area247.huellas.model.PreguntaConsumoDto;

@Component("preguntaConsumoMapper")
public class PreguntaConsumoMapper implements IHuellasMapper<PreguntaConsumo, PreguntaConsumoDto> {

	@Override
	public PreguntaConsumo modelToEntity(PreguntaConsumoDto model) {
		PreguntaConsumo entity = new PreguntaConsumo();
		ModelMapper modelMapper = new ModelMapper();
		entity = modelMapper.map(model,PreguntaConsumo.class);
		return entity;
	}

	@Override
	public PreguntaConsumoDto entityToModel(PreguntaConsumo entity) {
		PreguntaConsumoDto model = new PreguntaConsumoDto();
		ModelMapper modelMapper = new ModelMapper();
		model = modelMapper.map(entity, PreguntaConsumoDto.class);
		return model;
	}

	@Override
	public PreguntaConsumoDto entityToModelDiscriminated(PreguntaConsumo entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
