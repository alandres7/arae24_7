package co.gov.metropol.area247.huellas.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.huellas.entity.Consumo;
import co.gov.metropol.area247.huellas.mapper.IHuellasMapper;
import co.gov.metropol.area247.huellas.model.ConsumoDto;

@Component("encuestaMapper")
public class ConsumoMapper implements IHuellasMapper<Consumo, ConsumoDto> {

	@Override
	public Consumo modelToEntity(ConsumoDto model) {
		Consumo entity = new Consumo();
		ModelMapper modelMapper = new ModelMapper();
		entity = modelMapper.map(model,Consumo.class);
		return entity;
	}

	@Override
	public ConsumoDto entityToModel(Consumo entity) {
		ConsumoDto model = new ConsumoDto();
		ModelMapper modelMapper = new ModelMapper();
		model = modelMapper.map(entity, ConsumoDto.class);
		return model;
	}

	@Override
	public ConsumoDto entityToModelDiscriminated(Consumo entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
