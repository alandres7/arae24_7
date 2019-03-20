package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.EmpresaRutaDTO;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.FrecuenciaRutaDTO;
import co.gov.metropol.area247.model.HorarioRutaDTO;
import co.gov.metropol.area247.model.OperadorDTO;
import co.gov.metropol.area247.model.OperadorEmpresaDTO;
import co.gov.metropol.area247.model.ParaderoDTO;
import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.RutaGtpcDTO;
import co.gov.metropol.area247.model.RutaTipoSistemaRutaDTO;
import co.gov.metropol.area247.model.TipoModoTransporteDTO;
import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.TipoParaderoDTO;
import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.EmpresaTransporteRepository;
import co.gov.metropol.area247.repository.FrecuenciaRutaMetroRepository;
import co.gov.metropol.area247.repository.HorarioRutaRepository;
import co.gov.metropol.area247.repository.OperadorEmpresaRepository;
import co.gov.metropol.area247.repository.OperadorRepository;
import co.gov.metropol.area247.repository.ParaderoRutaRepository;
import co.gov.metropol.area247.repository.RutaRepository;
import co.gov.metropol.area247.repository.TipoModoTransporteRepository;
import co.gov.metropol.area247.repository.TipoOrientacionRepository;
import co.gov.metropol.area247.repository.TipoParaderoRepository;
import co.gov.metropol.area247.repository.TipoRutaRepository;
import co.gov.metropol.area247.repository.TipoSistemaRutaRepository;
import co.gov.metropol.area247.repository.domain.EmpresaRuta;
import co.gov.metropol.area247.repository.domain.EmpresaTransporte;
import co.gov.metropol.area247.repository.domain.FrecuenciaRuta;
import co.gov.metropol.area247.repository.domain.HorarioRuta;
import co.gov.metropol.area247.repository.domain.Operador;
import co.gov.metropol.area247.repository.domain.OperadorEmpresa;
import co.gov.metropol.area247.repository.domain.Paradero;
import co.gov.metropol.area247.repository.domain.ParaderoRuta;
import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.repository.domain.RutaTipoSistemaRuta;
import co.gov.metropol.area247.repository.domain.TipoModoTransporte;
import co.gov.metropol.area247.repository.domain.TipoOrientacion;
import co.gov.metropol.area247.repository.domain.TipoParadero;
import co.gov.metropol.area247.repository.domain.TipoRuta;
import co.gov.metropol.area247.repository.domain.TipoSistemaRuta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class GtpcMapper {
	
	@Autowired
	private TipoRutaRepository tipoRutaRepository;
	
	@Autowired
	private TipoModoTransporteRepository tipoModoTransporteRepository;
	
	@Autowired
	private TipoSistemaRutaRepository tipoSistemaRutaRepository;
	
	@Autowired 
	private RutaRepository rutaRepository;
	
	@Autowired 
	private TipoParaderoRepository tipoParaderoRepository;
	
	@Autowired 
	private TipoOrientacionRepository tipoOrientacionRepository;
	
	@Autowired 
	private ParaderoRutaRepository paraderoRutaRepository;
	
	@Autowired 
	private FrecuenciaRutaMetroRepository frecuenciaRutaRepository;
	
	@Autowired 
	private HorarioRutaRepository horarioRutaRepository;
	
	@Autowired 
	private OperadorRepository operadorRepository;
	
	@Autowired 
	private EmpresaTransporteRepository empresaTransporteRepository;
	
	@Autowired 
	private OperadorEmpresaRepository operadorEmpresaRepository;

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	
	public abstract List<ParaderoDTO> mapParaderoDTO (List<Paradero> paradero);
	
	public abstract List<RutaGtpcDTO> mapRutaGtpcDTO (List<Ruta> ruta);
	
	
	@ObjectFactory
	protected TipoRuta createItemTipoRuta(TipoRutaDTO tipoRutaDTO){
		if(!Utils.isNull(tipoRutaDTO) && !Utils.isNull(tipoRutaDTO.getId())){
			return tipoRutaRepository.findOne(tipoRutaDTO.getId());
		}
		return new TipoRuta();
	}
	
	public abstract TipoRutaDTO toTipoRutaDTO(TipoRuta tipoRuta);
	
	public abstract TipoRuta toTipoRuta(TipoRutaDTO tipoRutaDTO);
	
	@ObjectFactory
	protected TipoModoTransporte createItemTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO){
		if(!Utils.isNull(tipoModoTransporteDTO) && !Utils.isNull(tipoModoTransporteDTO.getId())){
			return tipoModoTransporteRepository.findOne(tipoModoTransporteDTO.getId());
		}
		return new TipoModoTransporte();
	}
	
	public abstract TipoModoTransporteDTO toTipoModoTransporteDTO(TipoModoTransporte tipoModoTransporte);
	
	public abstract TipoModoTransporte toTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO);
	
	@ObjectFactory
	protected TipoSistemaRuta createItemTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO){
		if(!Utils.isNull(tipoSistemaRutaDTO) && !Utils.isNull(tipoSistemaRutaDTO.getId())){
			return tipoSistemaRutaRepository.findOne(tipoSistemaRutaDTO.getId());
		}
		return new TipoSistemaRuta();
	}
	
	public abstract TipoSistemaRutaDTO toTipoSistemaRutaDTO(TipoSistemaRuta tipoSistemaRuta);
	
	public abstract TipoSistemaRuta toTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO);
	
	public abstract RutaTipoSistemaRutaDTO toRutaTipoSistemaRutaDTO(RutaTipoSistemaRuta rutaTipoSistemaRuta);
	
	public abstract RutaTipoSistemaRuta toRutaTipoSistemaRuta(RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTO);

	@ObjectFactory
	protected Ruta createItemRuta(RutaGtpcDTO rutaGtpcDTO){
		if (!Utils.isNull(rutaGtpcDTO)) {

			Ruta rutaGtpc = null;
			
			if (!Utils.isNull(rutaGtpcDTO.getId())) {
				rutaGtpc = rutaRepository.findOne(rutaGtpcDTO.getId());
			}

			if (Utils.isNull(rutaGtpc)) {
				rutaGtpc = new Ruta();
			}

			if (rutaGtpc != null) {
				rutaGtpc.setCodigo(rutaGtpcDTO.getCodigo());
				rutaGtpc.setDescripcion(rutaGtpcDTO.getDescripcion());
				rutaGtpc.setLongitudRuta(rutaGtpcDTO.getLongitudRuta());
				rutaGtpc.setCoordenadas(rutaGtpcDTO.getCoordenadas());
				rutaGtpc.setPrimerPunto(rutaGtpcDTO.getPrimerPunto());
				rutaGtpc.setUltimoPunto(rutaGtpcDTO.getUltimoPunto());
				rutaGtpc.setRutaActiva(rutaGtpcDTO.getRutaActiva());
				rutaGtpc.setTiempoEstimado(rutaGtpcDTO.getTiempoEstimado());
				rutaGtpc.setValorTarifa(rutaGtpcDTO.getValorTarifa());
				rutaGtpc.setIdModoRuta(rutaGtpcDTO.getIdModoRuta());
				rutaGtpc.setIdTipoRuta(
						Utils.isNull(rutaGtpcDTO.getIdTipoRuta()) ? null : rutaGtpcDTO.getIdTipoRuta());
				//rutaMetro.setIdTipoSistemaRuta(Utils.isNull(rutaMetroDTO.getTipoSistemaRutaDTO()) ? null
						//: rutaMetroDTO.getTipoSistemaRutaDTO().getId());
			}

			return rutaGtpc;
		}

		return new Ruta();
	}
	
	public abstract RutaGtpcDTO toRutaGtpcDTO(Ruta ruta);
	
	public abstract Ruta toRutaGtpc(RutaGtpcDTO rutaGtpcDTO);
	
	@ObjectFactory
	protected TipoParadero createItemTipoParadero(TipoParaderoDTO tipoParaderoDTO){
		if(!Utils.isNull(tipoParaderoDTO) && !Utils.isNull(tipoParaderoDTO.getId())){
			return tipoParaderoRepository.findOne(tipoParaderoDTO.getId());
		}
		return new TipoParadero();
	}
	
	public abstract TipoParaderoDTO toTipoParaderoGtpcDTO(TipoParadero tipoParadero);
	
	public abstract TipoParadero toTipoParaderoGtpc(TipoParaderoDTO tipoParaderoDTO);
	
	public abstract TipoParaderoDTO toTipoParaderoDTO(TipoParadero tipoParadero);
	
	public abstract TipoParadero toTipoParadero(TipoParaderoDTO tipoParaderoDTO);
	
	
	@ObjectFactory
	protected TipoOrientacion createItemTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO){
		if(!Utils.isNull(tipoOrientacionDTO) && !Utils.isNull(tipoOrientacionDTO.getId())){
			return tipoOrientacionRepository.findOne(tipoOrientacionDTO.getId());
		}
		return new TipoOrientacion();
	}
	
	public abstract TipoOrientacionDTO toTipoOrientacionGtpcDTO(TipoOrientacion tipoOrientacion);
	
	public abstract TipoOrientacion toTipoOrientacionGtpc(TipoOrientacionDTO tipoOrientacionDTO);
	
	
	@ObjectFactory
	protected ParaderoRuta createItemParaderoRUta(ParaderoRutaDTO paraderoRutaDTO){
		if(!Utils.isNull(paraderoRutaDTO) && !Utils.isNull(paraderoRutaDTO.getId())){
			return paraderoRutaRepository.findOne(paraderoRutaDTO.getId());
		}
		return new ParaderoRuta();
	}
	
	public abstract ParaderoRutaDTO toParaderoRutaGtpcDTO(ParaderoRuta paraderoRuta);
	
	public abstract ParaderoRuta toParaderoRutaGtpc(ParaderoRutaDTO paraderoRutaDTO);
	
	public abstract List<ParaderoRutaDTO> mapToParaderoRutaDTO (List<ParaderoRuta> paraderoRuta);
	
	public abstract List<TipoOrientacionDTO> mapTipoOrientacionDTO (List<TipoOrientacion> tipoOrientacion);
	
	@ObjectFactory
	protected FrecuenciaRuta createItemFrecuenciaRuta(FrecuenciaRutaDTO frecuenciaRutaDTO){
		if(!Utils.isNull(frecuenciaRutaDTO) && !Utils.isNull(frecuenciaRutaDTO.getId())){
			return frecuenciaRutaRepository.findOne(frecuenciaRutaDTO.getId());
		}
		return new FrecuenciaRuta();
	}
	
	public abstract FrecuenciaRutaDTO toFrecuenciaRutaGtpcDTO(FrecuenciaRuta frecuenciaRuta);
	
	public abstract FrecuenciaRuta toFrecuenciaRutaGtpc(FrecuenciaRutaDTO frecuenciaRutaDTO);
	
	public abstract List<FrecuenciaRutaDTO> mapToFrecuenciaRutaDTO (List<FrecuenciaRuta> frecuenciaRuta);
	
	
	@ObjectFactory
	protected HorarioRuta createItemHorarioRuta(HorarioRutaDTO horarioRutaDTO){
		if(!Utils.isNull(horarioRutaDTO) && !Utils.isNull(horarioRutaDTO.getId())){
			return horarioRutaRepository.findOne(horarioRutaDTO.getId());
		}
		return new HorarioRuta();
	}
	
	public abstract HorarioRutaDTO toHorarioRutaGtpcDTO(HorarioRuta horarioRuta);
	
	public abstract HorarioRuta toHorarioRutaGtpc(HorarioRutaDTO horarioRutaDTO);
	
	public abstract List<HorarioRutaDTO> mapHorariosRutaDTO (List<HorarioRuta> horarioRuta);
	
	@ObjectFactory
	protected Operador createItemOperador(OperadorDTO operadorDTO){
		if(!Utils.isNull(operadorDTO) && !Utils.isNull(operadorDTO.getId())){
			return operadorRepository.findOne(operadorDTO.getId());
		}
		return new Operador();
	}
	
	public abstract OperadorDTO toOperadorDTO(Operador operador);
	
	public abstract Operador toOperador(OperadorDTO operadorDTO);
	
	@ObjectFactory
	protected EmpresaTransporte createItemEmpresatransporte(EmpresaTransporteDTO empresaTransporteDTO){
		if(!Utils.isNull(empresaTransporteDTO) && !Utils.isNull(empresaTransporteDTO.getId())){
			return empresaTransporteRepository.findOne(empresaTransporteDTO.getId());
		}
		return new EmpresaTransporte();
	}
	
	public abstract EmpresaTransporteDTO toEmpresaTransporteDTO(EmpresaTransporte empresaTransporte);
	
	public abstract EmpresaTransporte toEmpresaTransporte(EmpresaTransporteDTO empresaTransporteDTO);
	
	public abstract List<EmpresaTransporteDTO> mapEmpresaTransporteDTO(List<EmpresaTransporte> empresaTransporte);
	
	@ObjectFactory
	protected OperadorEmpresa createItemOperadorEmpresa(OperadorEmpresaDTO operadorEmpresaDTO){
		if(!Utils.isNull(operadorEmpresaDTO) && !Utils.isNull(operadorEmpresaDTO.getId())){
			return operadorEmpresaRepository.findOne(operadorEmpresaDTO.getId());
		}
		return new OperadorEmpresa();
	}
	
	public abstract OperadorEmpresaDTO toOperadorEmpresaDTO(OperadorEmpresa operadorEmpresa);
	
	public abstract OperadorEmpresa toOperadorEmpresa(OperadorEmpresaDTO operadorEmpresaDTO);
	
	@ObjectFactory
	protected EmpresaRuta createItemEmpresaRuta(EmpresaRutaDTO empresaRutaDTO) {
		return new EmpresaRuta();
	}
	
	public abstract List<EmpresaRutaDTO> mapEmpresaRutaDTO(List<EmpresaRuta> empresaRuta);
	
	public abstract EmpresaRuta toEmpresaRuta(EmpresaRutaDTO empresaRutaDTO);
}
