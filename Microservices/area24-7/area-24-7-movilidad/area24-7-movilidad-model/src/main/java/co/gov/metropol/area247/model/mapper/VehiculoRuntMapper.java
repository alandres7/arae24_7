package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.RuntDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.VehiculoRuntRepository;
import co.gov.metropol.area247.repository.domain.VehiculosRunt;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class VehiculoRuntMapper {
	
	@Autowired
	private VehiculoRuntRepository vehiculoRuntRepository;
	
	@ObjectFactory
	protected VehiculosRunt createArchivo(RuntDTO runtModel) {
		if (!Utils.isNull(runtModel) && !Utils.isNull(runtModel.getId())) {
			return vehiculoRuntRepository.findOne(runtModel.getId());
		}
		return new VehiculosRunt();
	}
	
	public abstract RuntDTO toRuntDTO(VehiculosRunt vehiculosRunt);
	
	public abstract List<RuntDTO> mapToRuntDTO (List<VehiculosRunt> vehiculosRunt);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract VehiculosRunt toVehiculosRunt(RuntDTO runtDTO);

}
