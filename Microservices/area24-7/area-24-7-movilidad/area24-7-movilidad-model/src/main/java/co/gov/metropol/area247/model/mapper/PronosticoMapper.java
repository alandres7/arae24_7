package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;

import co.gov.metropol.area247.model.PronosticoDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.services.rest.siata.SiataWSDTO;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class PronosticoMapper {
	
	public abstract PronosticoDTO toPronosticoDTO(SiataWSDTO siataWSDTO);

}
