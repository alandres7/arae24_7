package co.gov.metropol.area247.contenedora.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.contenedora.mapper.IContenedoraMapper;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.dto.IconoDto;

@Component("iconoMapper")
public class IconoMapper implements IContenedoraMapper<Icono, IconoDto> {

		@Override
		public Icono modelToEntity(IconoDto model) {
			Icono entity = new Icono();
			ModelMapper mapper = new ModelMapper();
			entity = mapper.map(model, Icono.class);
			return entity;
		}

		@Override
		public IconoDto entityToModel(Icono entity) {
			IconoDto model = new IconoDto();
			ModelMapper mapper = new ModelMapper();
			if(entity != null) {
				model = mapper.map(entity, IconoDto.class);
			}
			return model;
		}

}
