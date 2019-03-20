package co.gov.metropol.area247.entorno.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;
import co.gov.metropol.area247.entorno.dao.IEntornoMedicionRepository;
import co.gov.metropol.area247.entorno.model.Capas;
import co.gov.metropol.area247.entorno.model.Medicion;
import co.gov.metropol.area247.entorno.model.dto.MedicionDto;
import co.gov.metropol.area247.entorno.service.IEntornoMedicionService;

@Service
public class IEntornoMedicionServiceImpl implements IEntornoMedicionService {

	@Value("${iconos.server.url}")
	String urlIconos;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IEntornoMedicionServiceImpl.class);
	
	@Autowired
	IEntornoMedicionRepository medicionDao;
	
	@Autowired
	IContenedoraVentanaInformacionService ventanaInformacionService;
	
	@Override
	public boolean medicionCrear(Medicion medicion) {
		try{
			medicionDao.save(medicion);
			LOGGER.info("Medicion creada con id:"+medicion.getId());
			return true;			
		}catch(Exception e) {
			LOGGER.error("No se pudo crear la medicion. Causa: "+e);
			return false;
		}
	}

	@Override
	public boolean medicionActualizar(Medicion medicion) {
		try{
			medicionDao.save(medicion);
			return true;
		}catch(Exception e) {
			LOGGER.error("No se pudo actualizar la medicion: "+medicion.getId()+" causa:"+e);
			return false;
		}
	}
	

	@Override
	public boolean medicionEliminar(Long idMedicion) {
		try{
			medicionDao.delete(idMedicion);
			return true;
		}catch(Exception e) {
			LOGGER.error("No se pudo borrar la medicion: "+idMedicion+" causa:"+e);
			return false;
		}
	}
	

	@Override
	public List<Medicion> medicionObtenerTodas() {
		return (List<Medicion>) medicionDao.findAll();
	}
	

	@Override
	public Medicion medicionObtenerPorId(Long idMedicion) {
		Medicion medicion = medicionDao.findOne(idMedicion); 
		if(medicion==null){
			LOGGER.info("No se encuentra una medición con id " + idMedicion);
		}
		return medicion;
	}
	
	@Override
	public MedicionDto medicionDtoObtenerPorId(Long idMedicion) {
		MedicionDto medicion = medicionDao.medicionDtoObtenerPorId(idMedicion); 
		if(medicion==null){
			LOGGER.info("No se encuentra una medición con id " + idMedicion);
		}else {
			if (medicion.getIcono() == null) {
			    medicion.setIcono(new Icono());
		    } else {
			    medicion.getIcono().setRutaLogo(urlIconos + medicion.getIcono().getId());
		    }
		}
		return medicion;
	}
	

	@Override
	public List<Medicion> medicionObtenerPorIdCapa(Long idCapa) {
		return medicionDao.findByIdCapa(idCapa);
	}
	
	
	@Override
	public List<MedicionDto> medicionDtoObtenerPorIdCapa(Long idCapa) {		
		List<MedicionDto> listaMediciones = medicionDao.medicionDtoObtenerPorIdCapa(idCapa);
		if (listaMediciones.isEmpty()) {
			LOGGER.info("No se encuentran mediciones por id de capa: " + idCapa);
		}else {
			for (MedicionDto medicionDto : listaMediciones) {    
				if (medicionDto.getIcono() == null) {
				    medicionDto.setIcono(new Icono());
			    } else {
				    medicionDto.getIcono().setRutaLogo(urlIconos + medicionDto.getIcono().getId());
			    }
			}
		}	
		return listaMediciones;
	}
	
	
	@Override
	public Long asignarVentanaInformacion(Medicion medicion) {		
		boolean capaValida = false;
		VentanaInformacion ventanaInformacion = new VentanaInformacion();
		for (Capas capaIterador : Capas.values()) {
			if (capaIterador.getValor() == Math.toIntExact(medicion.getIdCapa())) {
				capaValida = true;
				if(capaIterador.equals(Capas.AIRE) || capaIterador.equals(Capas.AGUA) || capaIterador.equals(Capas.CLIMA)) {
					//ventanaInformacion.setTitulo("Significado");
					//ventanaInformacion.setSubtitulo("Recomendación");
					ventanaInformacion.setColor(medicion.getColor());
					ventanaInformacion.setIcono(medicion.getIcono());
					ventanaInformacion.setNombre(medicion.getNombre());
					ventanaInformacion.setDescripcion(medicion.getSignificado());
					ventanaInformacion.setDescripcion2(medicion.getRecomendacion());
				}
				//TODO
			}
		}
		if(capaValida && ventanaInformacionService.ventanaInformacionCrear(ventanaInformacion)!=null) {
			return ventanaInformacion.getId();
		}else {
				return null;	
		}		
	}
	
	
	public boolean verificarSolapamientoIntervalos(Long idMedicion, Long idCapa, float escalaInicial, float escalaFinal) {
		Capas tipoCapaEntorno = Capas.obtenerPorId(idCapa.intValue());		
		if(!tipoCapaEntorno.equals(Capas.CLIMA)) {
		    List<Medicion> medicionesCapa = medicionDao.findByIdCapa(idCapa);		
		    for(Medicion medicion: medicionesCapa) {	
			    if(medicion.getId() != idMedicion) {
			        if((medicion.getEscalaInicial()<= escalaInicial) && (medicion.getEscalaFinal()>= escalaInicial)) {
			  	        return true;
			        }
			        if((medicion.getEscalaInicial()<= escalaFinal) && (medicion.getEscalaFinal()>=escalaFinal)) {
				        return true;
			        }
			        if((medicion.getEscalaInicial()<= escalaInicial) && (medicion.getEscalaFinal()>=escalaFinal)) {
				        return true;
			        }
		    	}
	    	}
		    return false;
		}else {
			return false;
		}
	}
	

	@Override
	public boolean medicionActualizarVentana(Medicion medicion) {
		VentanaInformacion ventanaInformacion = medicion.getVentanaInformacion();
			if(ventanaInformacion!=null) {
			ventanaInformacion.setColor(medicion.getColor());
			ventanaInformacion.setIcono(medicion.getIcono());
			ventanaInformacion.setNombre(medicion.getNombre());
			ventanaInformacion.setDescripcion(medicion.getSignificado());
			ventanaInformacion.setDescripcion2(medicion.getRecomendacion());
			return ventanaInformacionService.ventanaInformacionActualizar(ventanaInformacion);
		}
		LOGGER.error("La medición no tiene ventana de "
				+ "información; id de la medición: "+medicion.getId());
		return false;	
	}
	
	@Override
	public List<String> obtenerNombresMedicion(){		
		return medicionDao.obtenerNombresMedicion();
	}
}
