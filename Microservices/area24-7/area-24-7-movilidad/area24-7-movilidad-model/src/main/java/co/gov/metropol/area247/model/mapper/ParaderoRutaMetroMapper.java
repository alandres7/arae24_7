package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.TipoParaderoDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.ParaderoRutaRepository;
import co.gov.metropol.area247.repository.domain.ParaderoRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class ParaderoRutaMetroMapper {

	@Autowired
	private ParaderoRutaRepository paraderoRutaRepository;

	@ObjectFactory
	protected ParaderoRuta createParaderoRuta(ParaderoRutaMetroDTO paraderoRutaMetroDTO) {
		if (!Utils.isNull(paraderoRutaMetroDTO)) {
			
			ParaderoRuta paraderoRuta = null;
			
			if (!Utils.isNull(paraderoRutaMetroDTO.getId())) {
				paraderoRuta = paraderoRutaRepository.findOne(paraderoRutaMetroDTO.getId());
			}

			if (Utils.isNull(paraderoRuta)) {
				paraderoRuta = new ParaderoRuta();
			}

			if (paraderoRuta != null) {
				paraderoRuta.setCodigo(paraderoRutaMetroDTO.getCodigo());
				paraderoRuta.setDescripcion(paraderoRutaMetroDTO.getDescripcion());
				paraderoRuta.setLatitud(paraderoRutaMetroDTO.getLatitud());
				paraderoRuta.setLongitud(paraderoRutaMetroDTO.getLongitud());
				paraderoRuta.setFuenteDatos(paraderoRutaMetroDTO.getFuenteDatos());
				paraderoRuta.setActivo(paraderoRutaMetroDTO.getActivo());
				paraderoRuta.setIdTipoParadero(Utils.isNull(paraderoRutaMetroDTO.getTipoParaderoDTO()) ? null : paraderoRutaMetroDTO.getTipoParaderoDTO().getId());
				paraderoRuta.setIdTipoOrientacion(Utils.isNull(paraderoRutaMetroDTO.getTipoOrientacionDTO()) ? null :paraderoRutaMetroDTO.getTipoOrientacionDTO().getId());
				paraderoRuta.setIdRuta(Utils.isNull(paraderoRutaMetroDTO.getRutaDTO()) ? null : paraderoRutaMetroDTO.getRutaDTO().getId());
			}

			return paraderoRuta;

		}
		return new ParaderoRuta();
	}

	@ObjectFactory
	protected ParaderoRutaMetroDTO createParaderoRUtaMetroDTO(ParaderoRuta entidad) {

		ParaderoRutaMetroDTO dto = new ParaderoRutaMetroDTO();

		if (!Utils.isNull(entidad)) {
			
			dto.setActivo(entidad.getActivo());

			if (!Utils.isNull(entidad.getIdRuta())) {
				RutaMetroDTO rutaMetroDTO = new RutaMetroDTO();
				rutaMetroDTO.setId(entidad.getIdRuta());
				dto.setRutaDTO(rutaMetroDTO);
			}

			if (!Utils.isNull(entidad.getIdTipoParadero())) {
				TipoParaderoDTO tipoParaderoDTO = new TipoParaderoDTO();
				tipoParaderoDTO.setId(entidad.getIdTipoParadero());
				dto.setTipoParaderoDTO(tipoParaderoDTO);
			}

			if (!Utils.isNull(entidad.getIdTipoOrientacion())) {
				TipoOrientacionDTO tipoOrientacionDTO = new TipoOrientacionDTO();
				tipoOrientacionDTO.setId(entidad.getIdTipoOrientacion());
				dto.setTipoOrientacionDTO(tipoOrientacionDTO);
			}
			
		}

		return dto;

	}

	public abstract ParaderoRutaMetroDTO toParaderoRutaMetroDTO(ParaderoRuta paraderoRuta);

	public abstract List<ParaderoRutaMetroDTO> mapToParaderoRutaMetroDTO(List<ParaderoRuta> paraderosRuta);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true) })
	public abstract ParaderoRuta toParaderoRuta(ParaderoRutaMetroDTO paraderoRutaMetroDTO);

}
