package co.gov.metropol.area247.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.core.repository.AplicacionRepository;
import co.gov.metropol.area247.core.repository.CapaRepository;
import co.gov.metropol.area247.core.service.AplicacionService;
import co.gov.metropol.area247.core.service.ex.ServiceException;

@Service
public class AplicacionServiceImpl implements AplicacionService {

	private static Logger LOGGER = LoggerFactory.getLogger(AplicacionServiceImpl.class);

	@Autowired
	private AplicacionRepository aplicacionRepository;

	@Autowired
	private CapaRepository capaRepository;

	@Override
	public Aplicacion obtenerAplicaciomPorId(Long idAplicacion) throws ServiceException {
		try {
			return aplicacionRepository.buscarPorId(idAplicacion);
		} catch (Exception e) {
			LOGGER.error("Error en el servicio al intentar obtener las aplicaciones por id--{}{}", idAplicacion, e);
			throw new ServiceException("Error en el servicio al intentar obtener las aplicaciones por id --{}{}", e);
		}
	}
		
	
	@Override
	public List<Aplicacion> obtenerCapasPorTipoCapasOAplicacion(String tipoCapas, Long idAplicacion)
			throws ServiceException {
		try {
			List<Capa> capas = capaRepository.obtenerAplicacionYcapasPorTipoCapas(tipoCapas, idAplicacion);
			
			if(idAplicacion!=null) {
			    if(idAplicacion==3L) {	
				    List<Capa> capasAuxiliar = machetazoCapasDeAvistamientos(capas);
				    capas.clear();
				    capas.addAll(capasAuxiliar);
			    }
			}
						
			List<Aplicacion> aplicaciones = capas.stream().map(Capa::getAplicacion)
					.filter(distinctByKey(apl -> apl.getId())).collect(Collectors.toList());

			aplicaciones.forEach(app -> {
				app.setCapas(
						capas.stream().filter(c -> app.getId().longValue() == c.getAplicacion().getId().longValue())
								.collect(Collectors.toList()));
			});
			return aplicaciones;
		} catch (Exception e) {
			LOGGER.error(
					"Error en el servicio de aplicaciones al intentar obtener las aplicaciones con sus tipos de capas --{}{}",
					tipoCapas, e);
			throw new ServiceException(
					"Error en el servicio de aplicaciones al intentar obtener las aplicaciones con sus tipos de capas --{}{}",
					e);
		}
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
		
	public List<Capa> machetazoCapasDeAvistamientos(List<Capa> capas){
		List<Capa> capasAuxiliar = new ArrayList<Capa>();
		for (Capa capa : capas) {
			if(capa.getId()==223L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (Capa capa : capas) {
			if(capa.getId()==4L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (Capa capa : capas) {
			if(capa.getId()==224L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (Capa capa : capas) {
			if(capa.getId()==211L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (Capa capa : capas) {
			if( (capa.getId()!=223L) && (capa.getId()!=4L) &&
				(capa.getId()!=224L) && (capa.getId()!=211L) ) {
				capasAuxiliar.add(capa);
			}			
		}
		return capasAuxiliar;
	}

}
