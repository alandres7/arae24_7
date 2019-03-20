package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import co.gov.metropol.area247.model.RutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.domain.Ruta;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class RutaMapper {

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract Ruta toRuta(RutaDTO ruta);
	
}
