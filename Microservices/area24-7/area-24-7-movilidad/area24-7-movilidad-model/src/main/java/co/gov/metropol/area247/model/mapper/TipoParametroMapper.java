package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;

import co.gov.metropol.area247.model.TipoParametroDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.domain.TipoParametro;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class TipoParametroMapper {

	public abstract TipoParametroDTO toTipoParametroDTO(TipoParametro entity);

}
