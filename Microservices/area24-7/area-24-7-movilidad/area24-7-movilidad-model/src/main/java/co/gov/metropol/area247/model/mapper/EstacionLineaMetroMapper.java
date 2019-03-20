package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.ModoEstacionDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.EstacionLineaMetroRepository;
import co.gov.metropol.area247.repository.domain.EstacionMetro;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class EstacionLineaMetroMapper {

	@Autowired
	private EstacionLineaMetroRepository estacionLineaMetroRepository;

	@ObjectFactory
	protected EstacionMetro createEstacionMetro(EstacionLineaMetroDTO estacionLineaMetroDTO) {

		if (!Utils.isNull(estacionLineaMetroDTO)) {
			EstacionMetro estacionMetro = new EstacionMetro();

			if (!Utils.isNull(estacionLineaMetroDTO.getId())
					&& estacionLineaMetroRepository.exists(estacionLineaMetroDTO.getId())) {
				estacionMetro = estacionLineaMetroRepository.findOne(estacionLineaMetroDTO.getId());
			}

			estacionMetro.setCodigo(estacionLineaMetroDTO.getCodigo());
			estacionMetro.setDescripcion(estacionLineaMetroDTO.getDescripcion());
			estacionMetro.setLatitud(estacionLineaMetroDTO.getLatitud());
			estacionMetro.setLongitud(estacionLineaMetroDTO.getLongitud());
			estacionMetro.setActivo(estacionLineaMetroDTO.getActivo());
			estacionMetro.setIdModoEstacion(Utils.isNull(estacionLineaMetroDTO.getModoEstacionDTO()) ? null
					: estacionLineaMetroDTO.getModoEstacionDTO());
			estacionMetro.setModoLinea(estacionLineaMetroDTO.getTipoEstacion());
			estacionMetro.setIdLinea(Utils.isNull(estacionLineaMetroDTO.getLineaDTO()) ? null
					: estacionLineaMetroDTO.getLineaDTO().getId());

			return estacionMetro;
		}
		
		return new EstacionMetro();
	}

	@ObjectFactory
	protected EstacionLineaMetroDTO createEstacionLineaMetroDTO(EstacionMetro estacionMetro) {

		EstacionLineaMetroDTO estacionLineaMetroDTO = new EstacionLineaMetroDTO();

		if (!Utils.isNull(estacionMetro)) {

			if (!Utils.isNull(estacionMetro.getIdModoEstacion())) {
				estacionLineaMetroDTO.setModoEstacionDTO(estacionMetro.getIdModoEstacion());
			}

			if (!Utils.isNull(estacionMetro.getIdLinea())) {
				LineaMetroDTO lineaMetroDTO = new LineaMetroDTO();
				lineaMetroDTO.setId(estacionMetro.getIdLinea());
				estacionLineaMetroDTO.setLineaDTO(lineaMetroDTO);
			}

		}
		
		return estacionLineaMetroDTO;
	}
	
	public abstract EstacionLineaMetroDTO toEstacionLineaMetroDTO(EstacionMetro estacionMetro);

	public abstract List<EstacionLineaMetroDTO> mapToEstacionLineaMetroDTO(List<EstacionMetro> estacionesMetro);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true) })
	public abstract EstacionMetro toEstacionMetro(EstacionLineaMetroDTO estacionLineaMetroDTO);

}
