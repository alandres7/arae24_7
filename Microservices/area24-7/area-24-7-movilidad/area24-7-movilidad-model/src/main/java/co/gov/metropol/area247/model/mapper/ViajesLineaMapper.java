package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.ViajesLineaRepository;
import co.gov.metropol.area247.repository.domain.ViajesLinea;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class ViajesLineaMapper {

	@Autowired
	private ViajesLineaRepository viajesLineaRepository;

	@ObjectFactory
	protected ViajesLinea createLineaMetro(InfoViajesLineaDTO viajesLineaDTO) {

		if (!Utils.isNull(viajesLineaDTO)) {

			ViajesLinea viajesLinea = null;
			
			if (!Utils.isNull(viajesLineaDTO.getId())) {
				viajesLinea = viajesLineaRepository.findOne(viajesLineaDTO.getId());
			}

			if (Utils.isNull(viajesLinea)) {
				viajesLinea = new ViajesLinea();
			}

			if (viajesLinea != null) {
				
				viajesLinea.setNumSalidas(viajesLineaDTO.getNumSalidas());
				viajesLinea.setHoraInicio(viajesLineaDTO.getHoraInicio());
				viajesLinea.setHoraFin(viajesLineaDTO.getHoraFin());
				viajesLinea.setFrecuencia(viajesLineaDTO.getFrecuencia());
				viajesLinea.setIdaVuelta(viajesLineaDTO.isIdaVuelta());
				viajesLinea.setTiempoVuelta(viajesLineaDTO.getTiempoVuelta());
				viajesLinea.setIdLinea(Utils.isNull(viajesLineaDTO.getLineaDTO()) ? null : viajesLineaDTO.getLineaDTO().getId());
			}

			return viajesLinea;
		}
		return new ViajesLinea();
	}

	@ObjectFactory
	protected InfoViajesLineaDTO createInfoViajesLineaDTO(ViajesLinea viajesLinea) {

		InfoViajesLineaDTO viajesLineaDTO = new InfoViajesLineaDTO();

		if (!Utils.isNull(viajesLinea) && !Utils.isNull(viajesLinea.getIdLinea())) { 
				viajesLineaDTO.setLineaDTO(new LineaMetroDTO().withId(viajesLinea.getIdLinea()));
		}

		return viajesLineaDTO;
	}

	public abstract InfoViajesLineaDTO toInfoViajesLineaDTO(ViajesLinea entity);

	public abstract List<InfoViajesLineaDTO> mapToInfoViajesLineaDTO(List<ViajesLinea> entities);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract ViajesLinea toViajesLinea(InfoViajesLineaDTO dto);

}
