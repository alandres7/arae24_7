package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.TipoRutaRepository;
import co.gov.metropol.area247.repository.domain.TipoRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class TipoRutaMapper {

	@Autowired
	private TipoRutaRepository tipoRutaRepository;

	@ObjectFactory
	protected TipoRuta createTipoRuta(TipoRutaDTO tipoRutaDTO) {
		if (!Utils.isNull(tipoRutaDTO) && !Utils.isNull(tipoRutaDTO.getId())) {
			return tipoRutaRepository.findOne(tipoRutaDTO.getId());
		}
		return new TipoRuta();
	}

	public abstract TipoRutaDTO toTipoRutaDTO(TipoRuta entity);

	public abstract List<TipoRutaDTO> mapToTipoRutaDTO(List<TipoRuta> tiposRutas);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract TipoRuta toTipoRuta(TipoRutaDTO dto);

}
