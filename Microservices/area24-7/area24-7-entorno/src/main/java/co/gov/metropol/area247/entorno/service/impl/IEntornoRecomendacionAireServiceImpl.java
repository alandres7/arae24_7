package co.gov.metropol.area247.entorno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.entorno.dao.IEntornoRecomendacionAireRepository;
import co.gov.metropol.area247.entorno.model.RecomenaireEstacion;
import co.gov.metropol.area247.entorno.model.RecomendacionAire;
import co.gov.metropol.area247.entorno.service.IEntornoRecomenaireEstacionService;
import co.gov.metropol.area247.entorno.service.IEntornoRecomendacionAireService;

@Service
public class IEntornoRecomendacionAireServiceImpl implements IEntornoRecomendacionAireService {

	@Autowired
	IEntornoRecomendacionAireRepository recomendacionDao;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
	IEntornoRecomenaireEstacionService recomenaireEstacionService;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	

	@Override
	public boolean recomendacionCrear(RecomendacionAire recomendacion) {
		try {
			recomendacionDao.save(recomendacion);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean recomendacionActualizar(RecomendacionAire recomendacion) {
		return recomendacionCrear(recomendacion);
	}
	
	@Override
	public List<RecomendacionAire> recomendacionObtenerTodas() {
		try {
			List<RecomendacionAire> listRecomendaciones = (List<RecomendacionAire>) recomendacionDao.findAll();
			
			if (listRecomendaciones != null) {
				for (RecomendacionAire recomendacion : listRecomendaciones) {
					if (recomendacion.getIcono() == null) {
						recomendacion.setIcono(new Icono());
					}else {
						recomendacion.getIcono().setRutaLogo(urlIconos + recomendacion.getIcono().getId());
					}
				}				
			}
			
			return listRecomendaciones;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public RecomendacionAire recomendacionPorCodigo(String codigo) {
		try {
			RecomendacionAire recomendacion = recomendacionDao.findByCodigo(codigo).get(0);
			if (recomendacion.getIcono() == null) {
				recomendacion.setIcono(new Icono());
			}else {
				recomendacion.getIcono().setRutaLogo(urlIconos + recomendacion.getIcono().getId());
			}
			return recomendacion;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public RecomendacionAire recomendacionPorId(Long idRecomendacion) {
		try {
			RecomendacionAire recomendacion = recomendacionDao.findOne(idRecomendacion);
			if (recomendacion.getIcono() == null) {
				recomendacion.setIcono(new Icono());
			}else {
				recomendacion.getIcono().setRutaLogo(urlIconos + recomendacion.getIcono().getId());
			}
			return recomendacion;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<RecomendacionAire> recomendacionesPorIdEstacion(Long idEstacion){
		List<RecomenaireEstacion> listRecomenaireEstacion 
		    = recomenaireEstacionService.recomenaireEstacionObtenerPorEstacion(idEstacion);
		
		List<RecomendacionAire> listRecomendaciones = new ArrayList<RecomendacionAire>();
		for (RecomenaireEstacion recomenaireEstacion : listRecomenaireEstacion) { 
			RecomendacionAire recomendacion = recomendacionDao.findOne(recomenaireEstacion.getIdRecomendacion());
			if (recomendacion.getIcono() == null) {
				recomendacion.setIcono(new Icono());
			}else {
				recomendacion.getIcono().setRutaLogo(urlIconos + recomendacion.getIcono().getId());
			}
			listRecomendaciones.add(recomendacion);
		}
		return listRecomendaciones;
	}

	@Override
	public boolean recomendacionEliminar(String codigo) {
		try {
			RecomendacionAire recomendacion = recomendacionDao.findByCodigo(codigo).get(0);
			if (recomendacion != null) {
				iconoService.iconoEliminar(recomendacion.getIcono().getId());
				recomendacionDao.delete(recomendacion);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}



}
