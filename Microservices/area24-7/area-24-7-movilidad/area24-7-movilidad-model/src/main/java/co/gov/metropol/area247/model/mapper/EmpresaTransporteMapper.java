package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.EmpresaTransporteRepository;
import co.gov.metropol.area247.repository.domain.EmpresaTransporte;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class EmpresaTransporteMapper {

	@Autowired
	private EmpresaTransporteRepository repository;
	
	
	@ObjectFactory
	protected EmpresaTransporte createEmpresaTransporte(EmpresaTransporteDTO empresaTransporteDTO) {
		if (!Utils.isNull(empresaTransporteDTO) && !Utils.isNull(empresaTransporteDTO.getId())) {
			return repository.findOne(empresaTransporteDTO.getId());
		}
		return new EmpresaTransporte(); 
	}
	
	public abstract EmpresaTransporteDTO toEmpresaTransporteDTO(EmpresaTransporte empresaTransporte);
	
	public abstract List<EmpresaTransporteDTO> mapToEmpresaTransporteDTO(List<EmpresaTransporte> empresasTransporte);
	
	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract EmpresaTransporte toEmpresaTransporte(EmpresaTransporteDTO empresaTransporteDTO);
}
