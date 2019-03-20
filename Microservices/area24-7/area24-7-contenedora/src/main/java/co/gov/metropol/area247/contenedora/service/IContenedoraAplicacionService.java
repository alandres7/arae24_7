package co.gov.metropol.area247.contenedora.service;

import java.util.List;
//import org.json.JSONObject;

import co.gov.metropol.area247.contenedora.model.Aplicacion;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDto;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDtoSinListas;
import co.gov.metropol.area247.contenedora.model.dto.App;

public interface IContenedoraAplicacionService {
	
	Aplicacion aplicacionObtenerPorNombre(String nombre);
	
	Aplicacion aplicacionObtenerPorId(Long idAplicacion);
	
	boolean aplicacionActualizar(Aplicacion aplicacion);
	
	List<Aplicacion> aplicacionObtenerTodas();
	
	List<AplicacionDto> aplicacionDtoObtenerTodas(boolean contenedora);
	
	List<AplicacionDto> obtenerMenu();
	
	AplicacionDto aplicacionDtoObtenerMenu(Long aplicacionId);
	
	AplicacionDto aplicacionDtoObtenerPreferenciasUsuario(Long aplicacionId);
		
	AplicacionDto obtenerAplicacionDtoPorIdCategoria(Long idCategoria);
		
	AplicacionDtoSinListas aplicacionDtoSinListasObtenerPorId(Long idAplicacion) throws Exception;
	
	List<App> getApps() throws Exception;
	
	List<String> aplicacionDtoObtenerNombres() throws Exception;
	
	String getDefaultAppsPreferences() throws Exception;
}
