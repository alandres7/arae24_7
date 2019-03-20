package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.HorarioLineaMetroRepository;
import co.gov.metropol.area247.repository.domain.HorarioLinea;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class HorarioLineaMetroMapper {

	@Autowired
	private HorarioLineaMetroRepository repository;

	@ObjectFactory
	protected HorarioLinea createHorarioLinea(HorarioLineaMetroDTO horarioLineaMetroDTO) {
		if (!Utils.isNull(horarioLineaMetroDTO)) {
			if (!Utils.isNull(horarioLineaMetroDTO.getId())) {
				return repository.findOne(horarioLineaMetroDTO.getId());
			}

			HorarioLinea horarioLinea = new HorarioLinea();

			if (!Utils.isNull(horarioLineaMetroDTO.getLineaDTO())) {
				horarioLinea.setIdLinea(horarioLineaMetroDTO.getLineaDTO().getId());
			}

			return horarioLinea;
		}
		return new HorarioLinea();
	}

	public abstract HorarioLineaMetroDTO toHorarioLineaMetroDTO(HorarioLinea horarioLinea);

	public abstract List<HorarioLineaMetroDTO> mapToHorarioLineaMetroDTO(List<HorarioLinea> horarioLineas);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true) })
	public abstract HorarioLinea toHorarioLinea(HorarioLineaMetroDTO horarioLineaMetroDTO);

}
