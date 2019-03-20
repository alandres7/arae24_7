package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.HorarioRutaMetroDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.HorarioRutaRepository;
import co.gov.metropol.area247.repository.domain.HorarioRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class HorarioRutaMetroMapper {

	@Autowired
	private HorarioRutaRepository repository;

	@ObjectFactory
	protected HorarioRuta createHorarioRuta(HorarioRutaMetroDTO horarioRutaDTO) {
		if (!Utils.isNull(horarioRutaDTO)) { 
			if (!Utils.isNull(horarioRutaDTO.getId())) {
				return repository.findOne(horarioRutaDTO.getId());
			}
			
			HorarioRuta horarioRuta = new HorarioRuta();
			
			if (!Utils.isNull(horarioRutaDTO.getRutaDTO())) {
				horarioRuta.setIdRuta(horarioRutaDTO.getRutaDTO().getId());
			}
			
			return horarioRuta;
		}
		return new HorarioRuta();
	}

	public abstract HorarioRutaMetroDTO toHorarioRutaMetroDTO(HorarioRuta horarioRuta);

	public abstract List<HorarioRutaMetroDTO> mapToHorarioRutaMetroDTO(List<HorarioRuta> horariosRuta);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract HorarioRuta toHorarioRuta(HorarioRutaMetroDTO dto);

}
