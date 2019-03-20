package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.CiclorutaDTO;
import co.gov.metropol.area247.model.DareviaZonaDTO;
import co.gov.metropol.area247.model.EstacionEnciclaDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.CicloRutaRepository;
import co.gov.metropol.area247.repository.DareviaZonaRepository;
import co.gov.metropol.area247.repository.EstacionEnciclaRepository;
import co.gov.metropol.area247.repository.ModoEstacionRepository;
import co.gov.metropol.area247.repository.TareviaEstacionEnciclaRepository;
import co.gov.metropol.area247.repository.domain.Cicloruta;
import co.gov.metropol.area247.repository.domain.DareviaZona;
import co.gov.metropol.area247.repository.domain.EstacionEncicla;
import co.gov.metropol.area247.repository.domain.ModoEstacion;
import co.gov.metropol.area247.repository.domain.TareviaEstacionEncicla;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class EstacionEnciclaMapper{
	
	@Autowired
	private EstacionEnciclaRepository estacionEnciclaRepository;
	
	@Autowired
	private DareviaZonaRepository dareviaZonaRepository;
	
	@Autowired
	private TareviaEstacionEnciclaRepository tareviaEstacionEnciclaRepository;
	
	@Autowired
	private CicloRutaRepository cicloRutaRepository;
	
	@Autowired
	private ModoEstacionRepository modoEstacionRepository;
	
	@Autowired
	private ModoEstacionMapper modoEstacionMapper;
		
	@ObjectFactory
	protected EstacionEncicla createItemEncicla(EstacionEnciclaDTO estacionEnciclaDTO){
		if(!Utils.isNull(estacionEnciclaDTO) && !Utils.isNull(estacionEnciclaDTO.getId())){
			return estacionEnciclaRepository.findOne(estacionEnciclaDTO.getId());
		}
		return new EstacionEncicla();
	}
	
	@ObjectFactory
	protected DareviaZona createDareviaZona(DareviaZonaDTO dareviaZonaDTO){
		if(!Utils.isNull(dareviaZonaDTO) && !Utils.isNull(dareviaZonaDTO.getId())){
			return dareviaZonaRepository.findOne(dareviaZonaDTO.getId());
		}
		return new DareviaZona();
	}
	
	@ObjectFactory
	protected TareviaEstacionEncicla createTareviaEstacionEncicla(TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO){
		if(!Utils.isNull(tareviaEstacionEnciclaDTO)){
			
			if (!Utils.isNull(tareviaEstacionEnciclaDTO.getId())) {
				return tareviaEstacionEnciclaRepository.findOne(tareviaEstacionEnciclaDTO.getId());
			}
			
			TareviaEstacionEncicla tareviaEstacionEncicla = new TareviaEstacionEncicla();
			
			if (!Utils.isNull(tareviaEstacionEnciclaDTO.getModoEstacionDTO())) {
				tareviaEstacionEncicla.setIdModoEstacion(tareviaEstacionEnciclaDTO.getModoEstacionDTO().getId());
			}
						
			return tareviaEstacionEncicla;
		}
		
		return new TareviaEstacionEncicla();
	}
	
	@ObjectFactory
	protected TareviaEstacionEnciclaDTO createTareviaEstacionEnciclaDTO(TareviaEstacionEncicla tareviaEstacionEncicla){
		
		TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO = new TareviaEstacionEnciclaDTO();
		
		if (!Utils.isNull(tareviaEstacionEncicla)) {
			
			if (!Utils.isNull(tareviaEstacionEncicla.getIdModoEstacion())) {
				ModoEstacion modoEstacion = modoEstacionRepository.findOne(tareviaEstacionEncicla.getIdModoEstacion());
				tareviaEstacionEnciclaDTO.setModoEstacionDTO(modoEstacionMapper.toModoEstacionDTO(modoEstacion));
			}
			
			if (!Utils.isNull(tareviaEstacionEncicla.getIdEstacionEncicla())) {
				// Falta por implementar
			}
			
		}
		
		return tareviaEstacionEnciclaDTO;		
		
	}
	
	@ObjectFactory
	protected Cicloruta createItemCicloRuta(CiclorutaDTO cicloRutaDTO){
		if(!Utils.isNull(cicloRutaDTO) && !Utils.isNull(cicloRutaDTO.getId())){
			return cicloRutaRepository.findByIdItem(cicloRutaDTO.getIdItem());
		}
		return new Cicloruta();
	}
	
	public abstract EstacionEnciclaDTO toEstacionEnciclaDTO(EstacionEncicla estacionEncicla);
	
	public abstract List<EstacionEnciclaDTO> mapToEstacionEnciclaDTO (List<EstacionEncicla> estacionesEncicla);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract EstacionEncicla toEstacionEncicla(EstacionEnciclaDTO estacionEnciclaDTO);
	
	public abstract DareviaZonaDTO toDareviaZonaDTO(DareviaZona dareviaZona);
	
	public abstract DareviaZona toDareviaZona(DareviaZonaDTO dareviaZonaDTO);

	public abstract TareviaEstacionEnciclaDTO toTareviaEstacionEnciclaDTO(TareviaEstacionEncicla tareviaEstacionEncicla);
	
	public abstract TareviaEstacionEncicla toTareviaEstacionEstacion(TareviaEstacionEnciclaDTO tareviaEstacionEncicla);
	
	public abstract List<TareviaEstacionEnciclaDTO> mapTareviaEstacionEnciclaDTO (List<TareviaEstacionEncicla> tareviaEstacionEncicla);
	
	public abstract List<CiclorutaDTO> mapCicloRutaDTO (List<Cicloruta> cicloRuta);
	
	public abstract CiclorutaDTO toCicloRutaDTO(Cicloruta cicloRuta);
	
	public abstract Cicloruta toCicloRuta(CiclorutaDTO cicloRutaDTO);
}
