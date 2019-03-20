package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.CivicaDTO;
import co.gov.metropol.area247.model.PuntoTarjetaCivicaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.PuntoTarjetaCivicaRepository;
import co.gov.metropol.area247.repository.domain.PuntoTarjetaCivica;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract  class PuntoTarjetaCivicaMapper {

	public abstract List<PuntoTarjetaCivicaDTO> mapPuntoTarjetaCivicaDTO (List<PuntoTarjetaCivica> puntoTarjetaCivica);
	
	@Autowired
	private PuntoTarjetaCivicaRepository puntoTarjetaCivicaRepository;
	
	@ObjectFactory
	protected PuntoTarjetaCivica createItemCivica(CivicaDTO civicaDTO){
		if(!Utils.isNull(civicaDTO) && !Utils.isNull(civicaDTO.getId())){
			return puntoTarjetaCivicaRepository.findOne(civicaDTO.getId());
		}
		return new PuntoTarjetaCivica();
	}
	
	public abstract CivicaDTO tocivicaDTO(PuntoTarjetaCivica puntoTarjetaCivica);
	
	public abstract PuntoTarjetaCivica toPuntoTarjetaCivica(CivicaDTO civicaDTO);
}
