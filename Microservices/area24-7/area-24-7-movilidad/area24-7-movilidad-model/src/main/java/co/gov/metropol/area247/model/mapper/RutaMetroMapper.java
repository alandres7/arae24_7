package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.RutaMetroRepository;
import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class RutaMetroMapper {

	@Autowired
	private RutaMetroRepository rutaMetroRepository;
	
	@ObjectFactory
	protected Ruta createRuta(RutaMetroDTO rutaMetroDTO) {
		
		if (!Utils.isNull(rutaMetroDTO)) {

			Ruta rutaMetro = null;
			
			if (!Utils.isNull(rutaMetroDTO.getId())) {
				rutaMetro = rutaMetroRepository.findOne(rutaMetroDTO.getId());
			}

			if (Utils.isNull(rutaMetro)) {
				rutaMetro = new Ruta();
			}

			if (rutaMetro != null) {
				rutaMetro.setCodigo(rutaMetroDTO.getCodigo());
				rutaMetro.setDescripcion(rutaMetroDTO.getDescripcion());
				rutaMetro.setLongitudRuta(rutaMetroDTO.getLongitudRuta());
				rutaMetro.setCoordenadas(rutaMetroDTO.getCoordenadas());
				rutaMetro.setPrimerPunto(rutaMetroDTO.getPrimerPunto());
				rutaMetro.setUltimoPunto(rutaMetroDTO.getUltimoPunto());
				rutaMetro.setRutaActiva(rutaMetroDTO.getRutaActiva());
				rutaMetro.setTiempoEstimado(rutaMetroDTO.getTiempoEstimado());
				rutaMetro.setValorTarifa(rutaMetroDTO.getValorTarifa());
				rutaMetro.setIdModoRuta(rutaMetroDTO.getModoRutaDTO());
				rutaMetro.setIdTipoRuta(
						Utils.isNull(rutaMetroDTO.getTipoRutaDTO()) ? null : rutaMetroDTO.getTipoRutaDTO().getId());
				//rutaMetro.setIdTipoSistemaRuta(Utils.isNull(rutaMetroDTO.getTipoSistemaRutaDTO()) ? null
						//: rutaMetroDTO.getTipoSistemaRutaDTO().getId());
			}

			return rutaMetro;
		}

		return new Ruta();
	}

	@ObjectFactory
	protected RutaMetroDTO createRutaMetroDTO(Ruta ruta) {

		RutaMetroDTO rutaMetroDTO = new RutaMetroDTO();

		if (!Utils.isNull(ruta)) {
			
			if (!Utils.isNull(ruta.getIdModoRuta())) {
				rutaMetroDTO.setModoRutaDTO(ruta.getIdModoRuta());
			}
			
			if (!Utils.isNull(ruta.getIdTipoRuta())) {
				TipoRutaDTO tipoRutaDTO = new TipoRutaDTO();
				tipoRutaDTO.setId(ruta.getIdTipoRuta());
				rutaMetroDTO.setTipoRutaDTO(tipoRutaDTO);
			}
			
			/*if (!Utils.isNull(ruta.getIdTipoSistemaRuta())) {
				TipoSistemaRutaDTO tipoSistemaRutaDTO = new TipoSistemaRutaDTO();
				tipoSistemaRutaDTO.setId(ruta.getIdTipoSistemaRuta());
				rutaMetroDTO.setTipoSistemaRutaDTO(tipoSistemaRutaDTO);
			}*/
		}

		return rutaMetroDTO;
	}

	public abstract RutaMetroDTO toRutaMetroDTO(Ruta entity);

	public abstract List<RutaMetroDTO> mapToRutaMetroDTO(List<Ruta> rutasMetro);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract Ruta toRuta(RutaMetroDTO dto);

}
