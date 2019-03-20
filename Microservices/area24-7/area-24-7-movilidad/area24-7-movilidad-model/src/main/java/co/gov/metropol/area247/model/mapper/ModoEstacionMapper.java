package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.ModoEstacionDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.ModoEstacionRepository;
import co.gov.metropol.area247.repository.domain.ModoEstacion;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class ModoEstacionMapper {

	@Autowired
	private ModoEstacionRepository repository;

	@ObjectFactory
	protected ModoEstacion createModoEstacion(ModoEstacionDTO modoEstacionDTO) {
		if (!Utils.isNull(modoEstacionDTO) && !Utils.isNull(modoEstacionDTO.getId())) {
			return repository.findOne(modoEstacionDTO.getId());
		}
		return new ModoEstacion();
	}

	public abstract ModoEstacionDTO toModoEstacionDTO(ModoEstacion modoEstacion);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract ModoEstacion toModoEstacion(ModoEstacionDTO dto);
}
