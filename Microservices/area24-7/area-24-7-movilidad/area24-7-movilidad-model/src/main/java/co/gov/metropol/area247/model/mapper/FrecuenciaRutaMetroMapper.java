package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.FrecuenciaRutaMetroDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.FrecuenciaRutaMetroRepository;
import co.gov.metropol.area247.repository.domain.FrecuenciaRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class FrecuenciaRutaMetroMapper {

	@Autowired
	private FrecuenciaRutaMetroRepository frecuenciaRutaMetroRepository;

	@ObjectFactory
	protected FrecuenciaRuta createFrecuenciaRuta(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO) {
		if (!Utils.isNull(frecuenciaRutaMetroDTO)) {
			
			if (!Utils.isNull(frecuenciaRutaMetroDTO.getId())) {
				return frecuenciaRutaMetroRepository.findOne(frecuenciaRutaMetroDTO.getId());
			}

			FrecuenciaRuta frecuencia = new FrecuenciaRuta();

			if (!Utils.isNull(frecuenciaRutaMetroDTO.getRutaMetroDTO())) {
				frecuencia.setIdRuta(frecuenciaRutaMetroDTO.getRutaMetroDTO().getId());
			}

			return frecuencia;
		}
		return new FrecuenciaRuta();
	}

	public abstract FrecuenciaRutaMetroDTO toFrecuenciaRutaMetroDTO(FrecuenciaRuta frecuenciaRuta);

	public abstract List<FrecuenciaRutaMetroDTO> mapToFrecuenciaRutaMetroDTO(List<FrecuenciaRuta> frecuenciasRutas);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true) })
	public abstract FrecuenciaRuta toFrecuenciaRuta(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO);

}
