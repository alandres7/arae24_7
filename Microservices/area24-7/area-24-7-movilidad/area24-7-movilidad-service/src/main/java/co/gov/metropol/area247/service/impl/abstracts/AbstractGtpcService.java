package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.GtpcMapper;
import co.gov.metropol.area247.repository.EmpresaRutaRepository;
import co.gov.metropol.area247.repository.EmpresaTransporteRepository;
import co.gov.metropol.area247.repository.FrecuenciaRutaRepository;
import co.gov.metropol.area247.repository.HorarioRutaRepository;
import co.gov.metropol.area247.repository.OperadorEmpresaRepository;
import co.gov.metropol.area247.repository.OperadorRepository;
import co.gov.metropol.area247.repository.ParaderoRutaRepository;
import co.gov.metropol.area247.repository.RutaRepository;
import co.gov.metropol.area247.repository.RutaTipoSistemaRutaRepository;
import co.gov.metropol.area247.repository.TipoModoTransporteRepository;
import co.gov.metropol.area247.repository.TipoOrientacionRepository;
import co.gov.metropol.area247.repository.TipoParaderoRepository;
import co.gov.metropol.area247.repository.TipoRutaRepository;
import co.gov.metropol.area247.repository.TipoSistemaRutaRepository;
import co.gov.metropol.area247.service.IEmpresaTransporteService;
import co.gov.metropol.area247.service.IParaderoRutaService;
import co.gov.metropol.area247.service.IViajesRutaService;

public abstract class AbstractGtpcService {
	@Autowired
	protected GtpcMapper mapper;
	
	@Autowired
	protected TipoRutaRepository tipoRutaRepository;
	
	@Autowired
	protected TipoModoTransporteRepository tipoModoTransporteRepository;
	
	@Autowired
	protected TipoSistemaRutaRepository tipoSistemaRutaRepostory;
	
	@Autowired
	protected RutaRepository rutaRepository;
	
	@Autowired
	protected TipoParaderoRepository tipoParaderoRepository;
	
	@Autowired
	protected TipoOrientacionRepository tipoOrientacionRepository;
	
	@Autowired
	protected ParaderoRutaRepository paraderoRutaRepository;
	
	@Autowired
	protected FrecuenciaRutaRepository frecuenciaRutaRepository;
	
	@Autowired
	protected HorarioRutaRepository horarioRutaRepository;
	
	@Autowired
	protected OperadorRepository operadorRepository;
	
	@Autowired
	protected EmpresaTransporteRepository empresaTransporteRepository;
	
	@Autowired
	protected OperadorEmpresaRepository operadorEmpresaRepository;
	
	@Autowired
	protected EmpresaRutaRepository empresaRutaRepository;
	
	@Autowired
	protected RutaTipoSistemaRutaRepository rutaTipoSistemaRutaRepository;
	
	@Autowired
	protected IViajesRutaService viajesRutaService;
	
	@Autowired
	protected IParaderoRutaService paraderoRutaService;
	
	@Autowired
	protected IEmpresaTransporteService empresaTransporteService;
}
