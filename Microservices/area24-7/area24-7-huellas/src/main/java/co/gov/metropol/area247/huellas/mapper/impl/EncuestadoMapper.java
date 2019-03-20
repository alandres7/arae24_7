package co.gov.metropol.area247.huellas.mapper.impl;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Component;

import co.gov.metropol.area247.huellas.entity.Encuestado;
import co.gov.metropol.area247.huellas.mapper.IHuellasMapper;
import co.gov.metropol.area247.huellas.model.EncuestadoDto;

@Component("encuestadoMapper")
public class EncuestadoMapper implements IHuellasMapper<Encuestado, EncuestadoDto>{
	public Encuestado modelToEntity(EncuestadoDto model) {
		Encuestado entity = new Encuestado();
		ModelMapper modelMapper = new ModelMapper();
		entity = modelMapper.map(model,Encuestado.class);
		return entity;
	}
	
	public EncuestadoDto entityToModel(Encuestado entity) {
		EncuestadoDto model = new EncuestadoDto();
		ModelMapper modelMapper = new ModelMapper();
		model = modelMapper.map(entity, EncuestadoDto.class);
		return model;
	}

	@Override
	public EncuestadoDto entityToModelDiscriminated(Encuestado entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
