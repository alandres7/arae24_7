package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.TipoLineaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.TipoLineaRepository;
import co.gov.metropol.area247.repository.domain.TipoLinea;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class TipoLineaMapper {

	@Autowired
	private TipoLineaRepository tipoLineaRepository;

	@ObjectFactory
	protected TipoLinea createTipoLinea(TipoLineaDTO tipoLineaDTO) {
		if (!Utils.isNull(tipoLineaDTO) && !Utils.isNull(tipoLineaDTO.getId())) {
			return tipoLineaRepository.findOne(tipoLineaDTO.getId());
		}
		return new TipoLinea();
	}

	public abstract TipoLineaDTO toTipoLineaDTO(TipoLinea entity);

	public abstract List<TipoLineaDTO> mapToTipoLineaDTO(List<TipoLinea> tiposLineas);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract TipoLinea toTipoLinea(TipoLineaDTO dto);

}
