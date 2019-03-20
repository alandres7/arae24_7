package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import co.gov.metropol.area247.model.FrecuenciaRutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.domain.Frecuencia;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class FrecuenciaMapper {

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract Frecuencia toFrecuencia(FrecuenciaRutaDTO frecuencia);
	
}
