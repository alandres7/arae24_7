package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.TipoLineaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.LineaMetroRepository;
import co.gov.metropol.area247.repository.domain.LineaMetro;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class LineaMetroMapper {

	@Autowired
	private LineaMetroRepository lineaMetroRepository;

	@ObjectFactory
	protected LineaMetro createLineaMetro(LineaMetroDTO lineaMetroDTO) {

		if (!Utils.isNull(lineaMetroDTO)) {

			LineaMetro lineaMetro = null;
			
			if (!Utils.isNull(lineaMetroDTO.getId())) {
				lineaMetro = lineaMetroRepository.findOne(lineaMetroDTO.getId());
			}

			if (Utils.isNull(lineaMetro)) {
				lineaMetro = new LineaMetro();
			}

			if (lineaMetro != null) {
				
				lineaMetro.setCodigo(lineaMetroDTO.getCodigo());
				lineaMetro.setDescripcion(lineaMetroDTO.getDescripcion());
				lineaMetro.setLongitud(lineaMetroDTO.getLongitud());
				lineaMetro.setCoordenadas(lineaMetroDTO.getCoordenadas());
				lineaMetro.setPrimerPunto(lineaMetroDTO.getPrimerPunto());
				lineaMetro.setUltimoPunto(lineaMetroDTO.getUltimoPunto());
				lineaMetro.setActivo(lineaMetroDTO.getActivo());
				lineaMetro.setTiempoEstimado(lineaMetroDTO.getTiempoEstimado());
				lineaMetro.setValorTarifa(lineaMetroDTO.getValorTarifa());
				lineaMetro.setIdModoLinea(lineaMetroDTO.getModoLinea());
				lineaMetro.setIdTipoLinea(
						Utils.isNull(lineaMetroDTO.getTipoLinea()) ? null : lineaMetroDTO.getTipoLinea().getId());
			}

			return lineaMetro;
		}
		return new LineaMetro();
	}

	@ObjectFactory
	protected LineaMetroDTO createLineaMetroDTO(LineaMetro lineaMetro) {

		LineaMetroDTO lineaMetroDTO = new LineaMetroDTO();

		if (!Utils.isNull(lineaMetro)) {

			if (!Utils.isNull(lineaMetro.getIdModoLinea())) { 
				lineaMetroDTO.setModoLinea(lineaMetro.getIdModoLinea());
			}

			if (!Utils.isNull(lineaMetro.getIdTipoLinea())) {
				TipoLineaDTO tipoLineaDTO = new TipoLineaDTO();
				tipoLineaDTO.setId(lineaMetro.getIdTipoLinea());
				lineaMetroDTO.setTipoLinea(tipoLineaDTO);
			}

		}

		return lineaMetroDTO;
	}

	public abstract LineaMetroDTO toLineaMetroDTO(LineaMetro entity);

	public abstract List<LineaMetroDTO> mapToLineaMetroDTO(List<LineaMetro> lineasMetro);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract LineaMetro toLineaMetro(LineaMetroDTO dto);

}
