package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.TipoArchivoDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.TipoArchivoRepository;
import co.gov.metropol.area247.repository.domain.TipoArchivo;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class, uses={EstructuraDeArchivoMapper.class})
public abstract class TipoArchivoMapper{

	@Autowired
	private TipoArchivoRepository tipoArchivoRepository;

	@ObjectFactory
	protected TipoArchivo createTipoArchivo(TipoArchivoDTO loadFileTypeModel) {
		if (!Utils.isNull(loadFileTypeModel) && !Utils.isNull(loadFileTypeModel.getId())) {
			return tipoArchivoRepository.findOne(loadFileTypeModel.getId());
		}
		return new TipoArchivo();
	}
	
	public abstract TipoArchivoDTO toTipoArchivoDTO(TipoArchivo tipoArchivo);
	
	public abstract List<TipoArchivoDTO> mapToTipoArchivoDTO (List<TipoArchivo> tiposArchivo);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract TipoArchivo toTipoArchivo(TipoArchivoDTO tipoArchivoDTO);

}
