package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.EstructuraDeArchivoDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.EstructuraDeArchivoRespository;
import co.gov.metropol.area247.repository.domain.EstructuraDeArchivo;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class, uses={TipoArchivoMapper.class})
public abstract class EstructuraDeArchivoMapper{
	
	@Autowired
	private EstructuraDeArchivoRespository estructuraDeArchivoRespository;

	@ObjectFactory
	protected EstructuraDeArchivo createEstructuraDeArchivo(EstructuraDeArchivoDTO fileStructureModel) {
		if (!Utils.isNull(fileStructureModel) && !Utils.isNull(fileStructureModel.getId())) {
			return estructuraDeArchivoRespository.findOne(fileStructureModel.getId());
		}
		return new EstructuraDeArchivo();
	}
	
	public abstract EstructuraDeArchivoDTO toEstructuraDeArchivoDTO(EstructuraDeArchivo estructuraDeArchivo);
	
	public abstract List<EstructuraDeArchivoDTO> mapToEstructuraDeArchivoDTO (List<EstructuraDeArchivo> estructurasDeArchivo);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract EstructuraDeArchivo toEstructuraDeArchivo(EstructuraDeArchivoDTO estructuraDeArchivoDTO);

}
