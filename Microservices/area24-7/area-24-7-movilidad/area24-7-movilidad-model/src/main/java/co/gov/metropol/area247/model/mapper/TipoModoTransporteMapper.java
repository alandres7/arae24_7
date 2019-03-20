package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.TipoModoTransporteDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.TipoModoTransporteRepository;
import co.gov.metropol.area247.repository.domain.TipoModoTransporte;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class TipoModoTransporteMapper {

	@Autowired
	private TipoModoTransporteRepository repository;

	@ObjectFactory
	protected TipoModoTransporte createTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO) {
		if (!Utils.isNull(tipoModoTransporteDTO) && !Utils.isNull(tipoModoTransporteDTO.getId())) {
			return repository.findOne(tipoModoTransporteDTO.getId());
		}
		return new TipoModoTransporte();
	}

	public abstract TipoModoTransporteDTO toTipoModoTransporteDTO(TipoModoTransporte entity);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract TipoModoTransporte toTipoModoTransporte(TipoModoTransporteDTO dto);

}
