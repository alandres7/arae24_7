package co.gov.metropol.area247.contenedora.service;

import java.util.List;
import co.gov.metropol.area247.contenedora.model.Mensaje;
import co.gov.metropol.area247.contenedora.model.dto.MensajeDto;

public interface IContenedoraMensajeService {

	List<MensajeDto> mensajeDtoObtenerTodos() throws Exception;

	MensajeDto mensajeDtoObtenerPorId(Long idMensaje) throws Exception;

	boolean mensajeGuardar(Mensaje mensaje)throws Exception;

	boolean mensajeBorrar(Long idMensaje)throws Exception;
		
	Mensaje mensajeObtenerPorNombreIdentificador(String nombreIdentificador) throws Exception;
	
	String crearRespuestaJson(String nombreIdentificador,String textoRemplazo,String indiceRemplazo);
}
