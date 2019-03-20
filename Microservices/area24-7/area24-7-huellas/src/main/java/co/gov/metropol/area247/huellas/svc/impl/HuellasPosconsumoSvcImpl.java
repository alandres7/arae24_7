package co.gov.metropol.area247.huellas.svc.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.util.logger.Logger;
import co.gov.metropol.area247.core.gateway.MarkersProvider;
import co.gov.metropol.area247.core.gateway.posconsumo.dto.PosconsumoPoint;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.huellas.dao.IHuellasPosconsumoDao;
import co.gov.metropol.area247.huellas.entity.Posconsumo;
import co.gov.metropol.area247.huellas.jdbc.dao.IHuellasPosconsumoDaoJDBC;
import co.gov.metropol.area247.huellas.svc.IHuellasPosconsumoSvc;

@Service
public class HuellasPosconsumoSvcImpl implements IHuellasPosconsumoSvc {
	
	public static final Logger LOGGER = Logger.getInstance(HuellasPosconsumoSvcImpl.class);
	
	@Autowired
	MarkersProvider markersProvider;
	
	@Autowired
	IContenedoraCapaService capaSvc;
	
	 @Autowired
	 IContenedoraMarcadorService marcadorSvc;
	 
	 @Autowired
	 IHuellasPosconsumoDao posconsumoDao;
	 
	 @Autowired
	 IHuellasPosconsumoDaoJDBC posconsumoDaoJDBC;
	 	
	private Marcador  marcadorAux;
	
	@Override
	public boolean updatePuntosPosconsumo(Long idCapa) {
		try {
			Capa capa = capaSvc.capaGetById(idCapa);
			List<PosconsumoPoint> puntosPosconsumo = markersProvider.getPayload(capa.getUrlRecurso());
			if(!capa.getCategorias().isEmpty()) {
				capa.getCategorias()
					.forEach(
						categoria->{
							if(categoria.getMarcadores()!=null && !categoria.getMarcadores().isEmpty()) {
								List<Marcador> marcadoresCapa = categoria.getMarcadores();
								capa.setMarcadores(new ArrayList<>());
								marcadorSvc.eliminarMarcadores(marcadoresCapa);
							}
							puntosPosconsumo
							.stream()
							.filter(posconsumo->categoria.getNombre().equalsIgnoreCase(posconsumo.getCategoria_residuo()))
							.collect(Collectors.toList())
							.forEach(puntoPosconsumo->{
								createMarcador(puntoPosconsumo, categoria);
								createPosconsumo(puntoPosconsumo);
							});
						});
			}
			return true;
		}catch (Exception e) {
			LOGGER.error("Operación updatePuntosPosconsumo(Long idCapa): ", e);
		}
		return false;
		
	}
	
	@Override
	public String getDetailXIdMarcador(long idMarcador) throws Exception {
		try {
			return posconsumoDaoJDBC.getDetailXMarker(idMarcador);
		} catch (Exception e) {
			LOGGER.error("Operación getDetailXIdMarcador(long idMarcador): ", e);
			throw new Exception("Operación getDetailXIdMarcador(long idMarcador):", e);
		}
		
	}
	
	private void createMarcador(PosconsumoPoint puntoPosconsumo, Categoria categoria) {
		marcadorAux = new Marcador();
		marcadorAux.setNombre(puntoPosconsumo.getNombre_punto_de_recolecci_n());
		marcadorAux.setDescripcion(puntoPosconsumo.getNombre_residuo());
		marcadorAux.setCategoria(categoria);
		marcadorAux.setPoligono(Boolean.FALSE);
		marcadorAux.setCoordenadaPunto(
				GeometryUtil.obtenerPunto(puntoPosconsumo.getLatitud().getLatitude(), puntoPosconsumo.getLatitud().getLongitude()));
		marcadorSvc.marcadorCrear(marcadorAux);
	}
	
	private void createPosconsumo(PosconsumoPoint puntoPosconsumo) {
		Posconsumo posconsumo = new Posconsumo();
		posconsumo.setMarcador(marcadorAux);
		posconsumo.setNombre(puntoPosconsumo.getNombre_punto_de_recolecci_n());
		posconsumo.setResiduo(puntoPosconsumo.getNombre_residuo());
		posconsumo.setTipoResiduo(puntoPosconsumo.getTipo_residuo());
		posconsumo.setEmail(puntoPosconsumo.getCorreo_electr_nico());
		posconsumo.setDireccion(puntoPosconsumo.getDirecci_n_punto_de_recolecci_n());
		posconsumo.setCiudad(puntoPosconsumo.getCiudad());
		posconsumo.setDepartamento(puntoPosconsumo.getDepartamento());
		posconsumo.setPais(puntoPosconsumo.getPa_s());
		posconsumo.setContacto(puntoPosconsumo.getPersona_contacto());
		posconsumo.setHorario(puntoPosconsumo.getHorario());
		posconsumo.setPrograma(puntoPosconsumo.getNombre_programa_posconsumo());
		posconsumoDao.save(posconsumo);
	}
	
}
