package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.TipoOrientacionRepository;
import co.gov.metropol.area247.repository.domain.TipoOrientacion;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class TipoOrientacionMapper {

	@Autowired
	private TipoOrientacionRepository repository;

	@ObjectFactory
	protected TipoOrientacion createTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO) {
		if (!Utils.isNull(tipoOrientacionDTO) && !Utils.isNull(tipoOrientacionDTO.getId())) {
			return repository.findOne(tipoOrientacionDTO.getId());
		}
		return new TipoOrientacion();
	}

	public abstract TipoOrientacionDTO toTipoOrientacionDTO(TipoOrientacion entity);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract TipoOrientacion toTipoOrientacion(TipoOrientacionDTO dto);

}
