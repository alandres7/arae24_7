package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import co.gov.metropol.area247.model.HorarioRutaDTO;
import co.gov.metropol.area247.repository.domain.HorarioRuta;

public abstract class HorarioMapper {

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract HorarioRuta toHorario(HorarioRutaDTO horario);
	
}
