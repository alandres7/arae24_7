package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.TipoParaderoDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.TipoParaderoRepository;
import co.gov.metropol.area247.repository.domain.TipoParadero;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class TipoParaderoMapper {

	@Autowired
	private TipoParaderoRepository repository;

	@ObjectFactory
	protected TipoParadero createTipoParadero(TipoParaderoDTO tipoParaderoDTO) {
		if (!Utils.isNull(tipoParaderoDTO) && !Utils.isNull(tipoParaderoDTO.getId())) {
			return repository.findOne(tipoParaderoDTO.getId());
		}
		return new TipoParadero();
	}

	public abstract TipoParaderoDTO toTipoParaderoDTO(TipoParadero entity);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract TipoParadero toTipoParadero(TipoParaderoDTO dto);

}
