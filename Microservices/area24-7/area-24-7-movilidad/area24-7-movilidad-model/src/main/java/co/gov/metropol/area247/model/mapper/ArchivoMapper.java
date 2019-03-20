package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.ArchivoDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.ArchivoRepository;
import co.gov.metropol.area247.repository.domain.Archivo;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class, uses={TipoArchivoMapper.class})
public abstract class ArchivoMapper{
	
	@Autowired
	private ArchivoRepository archivoRepository;

	@ObjectFactory
	protected Archivo createArchivo(ArchivoDTO archivoModel) {
		if (!Utils.isNull(archivoModel) && !Utils.isNull(archivoModel.getId())) {
			return archivoRepository.findOne(archivoModel.getId());
		}
		return new Archivo();
	}
	
	public abstract ArchivoDTO toArchivoDTO(Archivo archivo);
	
	public abstract List<ArchivoDTO> mapToArchivoDTO (List<Archivo> archivos);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract Archivo toArchivo(ArchivoDTO archivoDTO);


}
