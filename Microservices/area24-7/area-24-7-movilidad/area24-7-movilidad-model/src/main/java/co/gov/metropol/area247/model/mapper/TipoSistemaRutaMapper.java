package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.TipoSistemaRutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.TipoSistemaRutaRepository;
import co.gov.metropol.area247.repository.domain.TipoSistemaRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class TipoSistemaRutaMapper {

	@Autowired
	private TipoSistemaRutaRepository repository;

	@ObjectFactory
	protected TipoSistemaRuta createTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO) {
		
		if (!Utils.isNull(tipoSistemaRutaDTO)) {
			
			TipoSistemaRuta tipoSistemaRuta = null;
			
			if (!Utils.isNull(tipoSistemaRutaDTO.getId())) {
				tipoSistemaRuta = repository.findOne(tipoSistemaRutaDTO.getId());
			}
			
			if (Utils.isNull(tipoSistemaRuta)) {
				tipoSistemaRuta = new TipoSistemaRuta();
			}
			
			if (tipoSistemaRuta != null) {
				tipoSistemaRuta.setNombre(tipoSistemaRutaDTO.getNombre());
				tipoSistemaRuta.setDescripcion(tipoSistemaRutaDTO.getDescripcion());
				tipoSistemaRuta.setFuenteDatos(tipoSistemaRutaDTO.getFuenteDatos());
				tipoSistemaRuta.setIdItem(tipoSistemaRutaDTO.getIdItem());
			}
		
			return tipoSistemaRuta;
		}
		
		return new TipoSistemaRuta();
	}

	public abstract TipoSistemaRutaDTO toTipoSistemaRutaDTO(TipoSistemaRuta entity);
	
	public abstract List<TipoSistemaRutaDTO> mapToTipoSistemaRutaDTO(List<TipoSistemaRuta> entites);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract TipoSistemaRuta toTipoSistemaRuta(TipoSistemaRutaDTO dto);

}
