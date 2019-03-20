package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.EmpresaRutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.EmpresaRutaRepository;
import co.gov.metropol.area247.repository.domain.EmpresaRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class EmpresaRutaMapper {

	@Autowired
	private EmpresaRutaRepository repository;
	
	
	@ObjectFactory
	protected EmpresaRuta createEmpresaRuta(EmpresaRutaDTO dto) {
		if (!Utils.isNull(dto) && !Utils.isNull(dto.getId())) {
			return repository.findOne(dto.getId());
		}
		return new EmpresaRuta(); 
	}
	
	public abstract EmpresaRutaDTO toEmpresaRutaDTO(EmpresaRuta entity);
	
	public abstract List<EmpresaRutaDTO> mapToEmpresaRutaDTO(List<EmpresaRuta> entities);
	
	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract EmpresaRuta toEmpresaRuta(EmpresaRutaDTO dto);
}
