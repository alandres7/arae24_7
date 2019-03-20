package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.InfoViajesRutaDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.ViajesRutaRepository;
import co.gov.metropol.area247.repository.domain.ViajesRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class ViajesRutaMapper {

	@Autowired
	private ViajesRutaRepository viajesRutaRepository;

	@ObjectFactory
	protected ViajesRuta createLineaMetro(InfoViajesRutaDTO viajesRutaDTO) {

		if (!Utils.isNull(viajesRutaDTO)) {

			ViajesRuta viajesRuta = null;
			
			if (!Utils.isNull(viajesRutaDTO.getId())) {
				viajesRuta = viajesRutaRepository.findOne(viajesRutaDTO.getId());
			}

			if (Utils.isNull(viajesRuta)) {
				viajesRuta = new ViajesRuta();
			}

			if (viajesRuta != null) {
				
				viajesRuta.setNumSalidas(viajesRutaDTO.getNumSalidas());
				viajesRuta.setHoraInicio(viajesRutaDTO.getHoraInicio());
				viajesRuta.setHoraFin(viajesRutaDTO.getHoraFin());
				viajesRuta.setFrecuencia(viajesRutaDTO.getFrecuencia());
				viajesRuta.setIdaVuelta(viajesRutaDTO.isIdaVuelta());
				viajesRuta.setTiempoVuelta(viajesRutaDTO.getTiempoVuelta());
				viajesRuta.setIdRuta(Utils.isNull(viajesRutaDTO.getRutaDTO()) ? null : viajesRutaDTO.getRutaDTO().getId());
			}

			return viajesRuta;
		}
		return new ViajesRuta();
	}

	@ObjectFactory
	protected InfoViajesRutaDTO createInfoViajesRutaDTO(ViajesRuta viajesRuta) {

		InfoViajesRutaDTO viajesRutaDTO = new InfoViajesRutaDTO();

		if (!Utils.isNull(viajesRuta) && !Utils.isNull(viajesRuta.getIdRuta())) { 
				viajesRutaDTO.setRutaDTO(new RutaMetroDTO().withId(viajesRuta.getIdRuta()));
		}

		return viajesRutaDTO;
	}

	public abstract InfoViajesRutaDTO toInfoViajesRutaDTO(ViajesRuta entity);

	public abstract List<InfoViajesRutaDTO> mapToInfoViajesRutaDTO(List<ViajesRuta> entities);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract ViajesRuta toViajesRuta(InfoViajesRutaDTO dto);

}
