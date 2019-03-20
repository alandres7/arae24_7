package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraMensajeRepository;
import co.gov.metropol.area247.contenedora.model.Mensaje;
import co.gov.metropol.area247.contenedora.model.dto.MensajeDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraMensajeService;
import co.gov.metropol.area247.contenedora.dao.IContenedoraAplicacionRepository;
import co.gov.metropol.area247.contenedora.model.Aplicacion;

@Service
public class IContenedoraMensajeServiceImpl implements IContenedoraMensajeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraMensajeServiceImpl.class);
	
	@Autowired
	IContenedoraMensajeRepository mensajeDao;
	
	@Autowired
	IContenedoraAplicacionRepository aplicacionDao;
	
	@Override
	public List<MensajeDto> mensajeDtoObtenerTodos()throws Exception {
		List<MensajeDto> mensajes = mensajeDao.mensajeDtoObtenerTodos();
		if(mensajes.isEmpty()){
			LOGGER.info("No se encuentran mensajes");
		}else {
			for (MensajeDto mensaje : mensajes) {
				if(mensaje.getIdAplicacion() != null) {
				    Aplicacion aplicacion = aplicacionDao.findOne(mensaje.getIdAplicacion());
				    mensaje.setNombreAplicacion(aplicacion.getNombre());
				}				
			}
		}
		return mensajes;
	}

	@Override
	public MensajeDto mensajeDtoObtenerPorId(Long idMensaje)throws Exception {
		MensajeDto mensaje = mensajeDao.mensajeDtoPorId(idMensaje);		
		if(mensaje == null){
			LOGGER.info(String.format("No se encuentra el mensaje con id: %s",idMensaje));
		}else {
			if(mensaje.getIdAplicacion() != null) {
			    Aplicacion aplicacion = aplicacionDao.findOne(mensaje.getIdAplicacion());
			    mensaje.setNombreAplicacion(aplicacion.getNombre());
			}
		}
		return mensaje;
	}

	@Override
	public boolean mensajeGuardar(Mensaje mensaje)throws Exception {
	    try{
			mensajeDao.save(mensaje);
			return true;
		}catch(Exception e){
			LOGGER.error("Error creando el mensaje: "+e);
			return false;
		}			
	}
	

	@Override
	public boolean mensajeBorrar(Long idMensaje)throws Exception {
		try{
			mensajeDao.delete(idMensaje);
			return true;
		}catch(Exception e){
			LOGGER.error("Error borrando el mensaje: "+e);
			return false;
		}
	}
	
	
	@Override
	public Mensaje mensajeObtenerPorNombreIdentificador(String nombreIdentificador) throws Exception {
		Mensaje mensaje = mensajeDao.findByNombreIdentificador(nombreIdentificador);
		if(mensaje == null){
			LOGGER.info(String.format("No se encuentra el mensaje"));
		}
		return mensaje;
	}
	
	@Override
	public String crearRespuestaJson(String nombreIdentificador,String textoRemplazo,String indiceRemplazo) {			
		try {
			Mensaje mensaje = mensajeObtenerPorNombreIdentificador(nombreIdentificador);			
			if(mensaje!=null) {			
			    String descripcion = mensaje.getDescripcion();
			    String titulo = mensaje.getTitulo();
			    
			    if( (!textoRemplazo.equals("")) && (!indiceRemplazo.equals("")) ) {
				    descripcion = descripcion.replace(indiceRemplazo,textoRemplazo);
			    }				
			    return "{\"descripcion\":\""+descripcion+"\",\"titulo\":\""+titulo+"\"}";
			}
			else {
				return "{\"descripcion\":\"Hubo un inconveniente en la generación de la respuesta\",\"titulo\":\"Mensaje\"}";
			}
		} 
		catch (Exception e) {
			return "{\"descripcion\":\"Hubo un inconveniente en la generación de la respuesta\",\"titulo\":\"Mensaje\"}";
		}					
	}
}

