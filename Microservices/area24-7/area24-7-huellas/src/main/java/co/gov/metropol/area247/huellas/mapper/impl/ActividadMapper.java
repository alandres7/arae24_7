package co.gov.metropol.area247.huellas.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.huellas.entity.Actividad;
import co.gov.metropol.area247.huellas.mapper.IHuellasMapper;
import co.gov.metropol.area247.huellas.model.ActividadDto;

@Component("actividadMapper")
public class ActividadMapper implements IHuellasMapper<Actividad, ActividadDto> {
	
	private ModelMapper modelMapper;
	
	@Override
	public Actividad modelToEntity(ActividadDto model) {
		modelMapper = new ModelMapper();
		return modelMapper.map(model, Actividad.class);
	}

	@Override
	public ActividadDto entityToModel(Actividad entity) {
		modelMapper = new ModelMapper();
		return modelMapper.map(entity, ActividadDto.class);
	}

	@Override
	public ActividadDto entityToModelDiscriminated(Actividad entity) {
		return null;
	}

}
