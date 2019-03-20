package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.FrecuenciaLineaMetroRepository;
import co.gov.metropol.area247.repository.LineaMetroRepository;
import co.gov.metropol.area247.repository.domain.FrecuenciaLinea;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class FrecuenciaLineaMetroMapper {

	@Autowired
	private FrecuenciaLineaMetroRepository frecuenciaLineaMetroRepository;
	
	@Autowired
	private LineaMetroRepository lineaMetroRepository;
	
	@Autowired
	private LineaMetroMapper lineaMetroMapper;

	@ObjectFactory
	protected FrecuenciaLinea createFrecuenciaLinea(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO) {
		if (!Utils.isNull(frecuenciaLineaMetroDTO)) {
			
			if (!Utils.isNull(frecuenciaLineaMetroDTO.getId())) {
				return frecuenciaLineaMetroRepository.findOne(frecuenciaLineaMetroDTO.getId());
			}

			FrecuenciaLinea frecuencia = new FrecuenciaLinea();

			if (!Utils.isNull(frecuenciaLineaMetroDTO.getLineaDTO())) {
				frecuencia.setIdLinea(frecuenciaLineaMetroDTO.getLineaDTO().getId());
			}

			return frecuencia;
		}
		return new FrecuenciaLinea();
	}
	
	@ObjectFactory
	protected FrecuenciaLineaMetroDTO createFrecuenciaLineaMetroDTO(FrecuenciaLinea frecuenciaLinea) {
		
		FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO = new FrecuenciaLineaMetroDTO();
		
		if (!Utils.isNull(frecuenciaLinea) && !Utils.isNull(frecuenciaLinea.getIdLinea())) {
			LineaMetroDTO lineaMetroDTO = new LineaMetroDTO();
			lineaMetroDTO.setId(frecuenciaLinea.getIdLinea());
			frecuenciaLineaMetroDTO.setLineaDTO(lineaMetroDTO);
		}
		
		return frecuenciaLineaMetroDTO;
		
	}

	public abstract FrecuenciaLineaMetroDTO toFrecuenciaLineaMetroDTO(FrecuenciaLinea frecuenciaLinea);

	public abstract List<FrecuenciaLineaMetroDTO> mapToFrecuenciaLineaMetroDTO(List<FrecuenciaLinea> frecuenciasLineas);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true) })
	public abstract FrecuenciaLinea toFrecuenciaLinea(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO);

}
