package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.ParaderoRutaRepository;
import co.gov.metropol.area247.repository.domain.ParaderoRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class ParaderoRutaMapper {

	@Autowired
	private ParaderoRutaRepository paraderoRutaRepository;

	@ObjectFactory
	protected ParaderoRuta createParaderoRuta(ParaderoRutaDTO paraderoRutaDTO) {
		if (!Utils.isNull(paraderoRutaDTO) && !Utils.isNull(paraderoRutaDTO.getId())) {
			return paraderoRutaRepository.findOne(paraderoRutaDTO.getId());
		}
		return new ParaderoRuta();
	}

	public abstract ParaderoRutaDTO toParaderoRutaDTO(ParaderoRuta paraderoRuta);

	public abstract List<ParaderoRutaDTO> mapToParaderoRutaDTO(List<ParaderoRuta> paraderosRuta);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true) })
	public abstract ParaderoRuta toParaderoRuta(ParaderoRutaDTO paraderoRutaDTO);

}
