package co.gov.metropol.area247.centrocontrol.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

import co.gov.metropol.area247.centrocontrol.config.FireBaseConfig;
import co.gov.metropol.area247.centrocontrol.jobs.JobTarea;
import co.gov.metropol.area247.centrocontrol.model.ActividadHuella;
import co.gov.metropol.area247.centrocontrol.model.ActividadHuellaPackage;
import co.gov.metropol.area247.centrocontrol.model.ActividadHuellaTransaccion;
import co.gov.metropol.area247.centrocontrol.model.Afectacion;
import co.gov.metropol.area247.centrocontrol.model.Aplicacion;
import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;
import co.gov.metropol.area247.centrocontrol.model.Auditoria;
import co.gov.metropol.area247.centrocontrol.model.AutoridadCompetente;
import co.gov.metropol.area247.centrocontrol.model.Avistamiento;
import co.gov.metropol.area247.centrocontrol.model.Capa;
import co.gov.metropol.area247.centrocontrol.model.CapaSubmenu;
import co.gov.metropol.area247.centrocontrol.model.Categoria;
import co.gov.metropol.area247.centrocontrol.model.CategoriaSubmenu;
import co.gov.metropol.area247.centrocontrol.model.ComentarioAvist;
import co.gov.metropol.area247.centrocontrol.model.ComentarioHisto;
import co.gov.metropol.area247.centrocontrol.model.ComentarioVigia;
import co.gov.metropol.area247.centrocontrol.model.DetalleClima;
import co.gov.metropol.area247.centrocontrol.model.DetalleTiempo;
import co.gov.metropol.area247.centrocontrol.model.Especie;
import co.gov.metropol.area247.centrocontrol.model.EstadisticaAvistamiento;
import co.gov.metropol.area247.centrocontrol.model.EstadisticaReporteAvist;
import co.gov.metropol.area247.centrocontrol.model.EstadisticaVigia;
import co.gov.metropol.area247.centrocontrol.model.Formulario;
import co.gov.metropol.area247.centrocontrol.model.Historia;
import co.gov.metropol.area247.centrocontrol.model.Icono;
import co.gov.metropol.area247.centrocontrol.model.IconoEstado;
import co.gov.metropol.area247.centrocontrol.model.MarcadorAvist;
import co.gov.metropol.area247.centrocontrol.model.MarkerInfo;
import co.gov.metropol.area247.centrocontrol.model.MarkerPackage;
import co.gov.metropol.area247.centrocontrol.model.MarkerPoint;
import co.gov.metropol.area247.centrocontrol.model.MarkerPolygon;
import co.gov.metropol.area247.centrocontrol.model.MarkerPrintPackage;
import co.gov.metropol.area247.centrocontrol.model.Medicion;
import co.gov.metropol.area247.centrocontrol.model.Mensaje;
import co.gov.metropol.area247.centrocontrol.model.MenuItem;
import co.gov.metropol.area247.centrocontrol.model.Multimedia;
import co.gov.metropol.area247.centrocontrol.model.MultipartInputStreamFileResource;
import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.NotificacionPush;
import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.model.OpcionPregunta;
import co.gov.metropol.area247.centrocontrol.model.PermisoRol;
import co.gov.metropol.area247.centrocontrol.model.PermisoRolVista;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuarioVista;
import co.gov.metropol.area247.centrocontrol.model.Point;
import co.gov.metropol.area247.centrocontrol.model.Pregunta;
import co.gov.metropol.area247.centrocontrol.model.PreguntaHuella;
import co.gov.metropol.area247.centrocontrol.model.Recomendacion;
import co.gov.metropol.area247.centrocontrol.model.RecomendacionAire;
import co.gov.metropol.area247.centrocontrol.model.RecursoVigia;
import co.gov.metropol.area247.centrocontrol.model.ReporteVigia;
import co.gov.metropol.area247.centrocontrol.model.RespuestaLogin;
import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.model.TareaProgramada;
import co.gov.metropol.area247.centrocontrol.model.TipoCapa;
import co.gov.metropol.area247.centrocontrol.model.TipoObjeto;
import co.gov.metropol.area247.centrocontrol.model.TipoRespuestaHuella;
import co.gov.metropol.area247.centrocontrol.model.Usuario;
import co.gov.metropol.area247.centrocontrol.model.UsuarioJSON;
import co.gov.metropol.area247.centrocontrol.security.context.CentroControlContextHolder;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadLoginExternoService;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadNotificacionPushService;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadPermisoRolService;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadRolService;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadTareaProgramadaService;

@Component
public class CentroControlExchange {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CentroControlExchange.class); 

	@Autowired
	private ISeguridadRolService servicioRoles;

	@Autowired
	private ISeguridadPermisoRolService servicioPermisoRoles;
	
	@Autowired
	private ISeguridadTareaProgramadaService servicioTareasProgramadas;
	
	@Autowired
	private ISeguridadNotificacionPushService servicioNotificacionesPush;

	@Autowired
	private ISeguridadLoginExternoService loginExternoService;

	@Autowired
	private Environment enviro;

	@Autowired
	private FireBaseConfig database;

	public String getTokenAcceso() {
		//return enviro.getProperty("access.token");
		return loginExternoService.getToken();
	}

	public String getServerEndpoint() {
		return enviro.getProperty("server.endpoints");
	}	


	// _______________Opciones de Firebase____________________________________

	
	public void actualizacionFireBase(){	
		try {
		    DatabaseReference ref = database.firebaseDatabse(); 		
		    DatabaseReference aplicacionRef = ref.child("/");
		    Map<String, Object> cambios = new HashMap<String, Object>(); 		
		    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		    Date date = new Date();	
		    cambios.put("updater",dateFormat.format(date));	
		    aplicacionRef.setValue(cambios);
		}catch (Exception ex) {
			System.out.print("Error: " + ex);
		}
	}


	// _______________Administrar Aplicaciones_________________________________
	
	/**
	 * Método encargado de devolvernos un listado de los menús usados por el centro
	 * de control
	 */
	public List<MenuItem> obtenerMenus() {
		RestTemplate restTemplate = new RestTemplate();	
		String menuResourceUrl = getServerEndpoint() + "/api/aplicacion/obtenerTodas?contenedora=false";
		MenuItem[] listaMenus = null;
		try {
			listaMenus = restTemplate.getForObject(menuResourceUrl, MenuItem[].class);
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		for (MenuItem menuItem : listaMenus) {	
		    List<CapaSubmenu> listSubmenu = new ArrayList<CapaSubmenu>();
		    menuItem.setCapas(listSubmenu);
		    //____________________________________________________________________
		
   			CapaSubmenu capaItem = new CapaSubmenu();
   		    capaItem.setNombre("Administrar Capas");
   		    capaItem.setUrlRef("/getCapasByIdAplicacion/" + menuItem.getId());
   		    listSubmenu.add(capaItem);		    
   		    //____________________________________________________________________
   			
   		    if(menuItem.getId()==3L) {
   		        capaItem = new CapaSubmenu();
   		        capaItem.setNombre("Administrar Avistamientos");
   		        capaItem.setUrlRef("/adminAvistamientos");
   		        listSubmenu.add(capaItem);
   		    }
   		    //____________________________________________________________________
               
   		    if(menuItem.getId()==2L) {
   		        capaItem = new CapaSubmenu();
   		        capaItem.setNombre("Administrar Denuncias");
   		        capaItem.setUrlRef("/adminVigias");
   		        listSubmenu.add(capaItem);
   		    }
               //____________________________________________________________________
               
   		    if(menuItem.getId()==5L) {
   		        capaItem = new CapaSubmenu();
   		        capaItem.setNombre("Recomendaciones aire");
   		        capaItem.setUrlRef("/getRecomendacionesAire");
   		        listSubmenu.add(capaItem);
   		    }
               //____________________________________________________________________
               
   		    if(menuItem.getId()==8L) {
   		        capaItem = new CapaSubmenu();
   		        capaItem.setNombre("Administrar Fotografías");
   		        capaItem.setUrlRef("/adminFotografias");
   		        listSubmenu.add(capaItem);
   		    }
   		    
   		    //____________________________________________________________________
   		    
   		    String idApp = menuItem.getId().toString();
   		    String listaTipoCapa = "2,5,8";  //Mapa,Subcapas,Avistamiento
   			for (CapaSubmenu capaItemws : getCapasByIdAplTipoCapa(idApp,listaTipoCapa)) {
   				capaItemws.setNombre("Capa " + capaItemws.getNombre());
   			    capaItemws.setUrlRef("/inicio/" + capaItemws.getId());
   			    listSubmenu.add(capaItemws);
   			}		
   							
		}
		return Arrays.asList(listaMenus);
	}	
	
	/**
	 * Método encargado de devolvernos un listado de los menús usados por el centro
	 * de control
	 */
	public List<MenuItem> obtenerAplicaciones() {
		RestTemplate restTemplate = new RestTemplate();
		String menuResourceUrl = getServerEndpoint() + "/api/aplicacion/obtenerTodas?contenedora=true";
		MenuItem[] listaMenus = null;
		try {
			listaMenus = restTemplate.getForObject(menuResourceUrl, MenuItem[].class);
		} catch (Exception ex) {
			System.out.print("Error: " + ex);  
		}
		
		List<MenuItem> listMenuItem = Arrays.asList(listaMenus);		
		for (MenuItem menuItem : listMenuItem) {
			Recomendacion recomen = obtenerDetalleRecomendacion(menuItem.getId().toString());
			if(recomen !=null ) {
			    menuItem.setRecomendacion(recomen);
			}else {
				menuItem.setRecomendacion(new Recomendacion());
			}
		}		
		return listMenuItem;
	}

	/**
	 * Método encargado de consultar una aplicacion con base en un identificador
	 */
	public Aplicacion obtenerDetalleAplicacion(String idAplicacion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Aplicacion> entity = new HttpEntity<>(headers);

		String aplicacionResourceUrl = getServerEndpoint() + "/api/aplicacion/nolistas/" + idAplicacion;

		Aplicacion aplicacion = null;
		try {
			HttpEntity<Aplicacion> entityAplicacion 
			    = restTemplate.exchange(aplicacionResourceUrl,HttpMethod.GET,entity,Aplicacion.class);
			aplicacion = entityAplicacion.getBody();
			aplicacion.setRecomendacion(obtenerDetalleRecomendacion(idAplicacion));
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}			
		
		return aplicacion;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de aplicaciones al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	public String updateAplicacion(String id, String nombre, Boolean activo, String codigoColor, String descripcion,
			                       String recomendacion, String idRecomendacion, String radioAccion, MultipartFile icono) {
		String url = getServerEndpoint() + "/api/aplicacion";

		MultipartInputStreamFileResource iconoFile = null;
		try {
			iconoFile = new MultipartInputStreamFileResource(icono.getInputStream(), icono.getOriginalFilename());
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
		}

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("icono", iconoFile);
		params.add("id", id);
		params.add("nombre", nombre);
		params.add("activo", activo);
		params.add("codigoColor", codigoColor);
		params.add("descripcion", descripcion);
		params.add("radioAccion", radioAccion);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			
			actualizacionFireBase(); //XXXXXXXXXXX FIREBASE			
			createUpdateRecomendacion(idRecomendacion,"Bienvenida " + nombre,recomendacion,id);									
		} catch (Exception ex) {
			System.out.println ("Error: " + ex);
			
			ex.printStackTrace();
			LOGGER.error("Error durante el proceso: " + ex.getMessage());
			LOGGER.error("Error firebase: ",ex);
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		createUpdateAuditoria(1L,"La aplicación con id: "+id+" ha sido actualizada",1);
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		return respuesta.getBody();
	}
	

	public String updateRadioAplicacion(String id,String radioAccion,String limInfRadio,String limSupRadio) {
		/*String url = getServerEndpoint() + "/api/aplicacion/actualizarRadio";

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("id", id);
		params.add("radioAccion", radioAccion);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);								
		} catch (Exception ex) {
			System.out.println ("Error: " + ex);
			LOGGER.error("Error durante el proceso: " + ex.getMessage());
		}	
		return respuesta.getBody();*/
		return "Radio Actualizado para aplicacion con id: " + id;
	}	
	
		
	public List<String> obtenerListNombreAplicaciones() {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> listEntity = new HttpEntity<String>(headers);
		String nombresAplicacionesUrl = getServerEndpoint() + "/api/aplicacion/listaNombres";
		String[] listaNombres = null;
		try {
			HttpEntity<String[]> listEntityNombres 
			    = restTemplate.exchange(nombresAplicacionesUrl,HttpMethod.GET,listEntity,String[].class);
			listaNombres = listEntityNombres.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaNombres);
	}	
	
		
	/**
	 * Método encargado de consultar una aplicacion con base en Id de capa
	 */
	public Aplicacion obtenerAplicacionPorIdCapa(String idCapa) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Aplicacion> entity = new HttpEntity<>(headers);
		String aplicacionResourceUrl = getServerEndpoint() + "/api/core/app/findbycapa/" + idCapa;
		Aplicacion aplicacion = null;
		try {
			HttpEntity<Aplicacion> entityAplicacion 
			    = restTemplate.exchange(aplicacionResourceUrl,HttpMethod.GET,entity,Aplicacion.class);
			aplicacion = entityAplicacion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}					
		return aplicacion;
	}

	// _______________Administracion de Capas________________________________

	/** Método encargado de consultar una capa con base en un identificador */
	public Capa obtenerDetalleCapa(String idCapa) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Capa> entity = new HttpEntity<Capa>(headers);

		String capaResourceUrl = getServerEndpoint() + "/api/capa/" + idCapa;
		Capa capa = null;
		try {
			HttpEntity<Capa> entityCapa = restTemplate.exchange(capaResourceUrl, HttpMethod.GET, entity, Capa.class);
			capa = entityCapa.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return capa;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de capas al respectivo servicio web encargado de procesarlas y luego
	 * retornarnos un mensaje de confirmación o rechazo
	 */
	public String createUpdateCapa(String id, String nombre, boolean activo, boolean favorito, 
			String descripcion, MultipartFile iconoMar, MultipartFile icono, String idAplicacionDeCapa, 
			String tipoCapa, String urlRecurso, String aliasNombre, String aliasMunicipio, 
			String aliasDescripcion, String aliasCategoria, String aliasImagen, String aliasDireccion,
			boolean fichaCaracterizacion, boolean poligono, boolean tieneHistoria) {
		String url = getServerEndpoint() + "/api/capa";

		MultipartInputStreamFileResource iconoFile = null;
		MultipartInputStreamFileResource iconoMarFile = null;	
		
		try {
			iconoFile = new MultipartInputStreamFileResource(icono.getInputStream(), icono.getOriginalFilename());
		} catch (Exception ex) {}
		
		try {
			iconoMarFile = new MultipartInputStreamFileResource(iconoMar.getInputStream(),iconoMar.getOriginalFilename());
		} catch (Exception ex) {}

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("icono", iconoFile);
		params.add("iconoMarcador", iconoMarFile);
		params.add("nombre", nombre);
		params.add("descripcion", descripcion);
		params.add("activo", activo);
		params.add("favorito", favorito);
		params.add("tipoCapa", Long.valueOf(tipoCapa));		
		params.add("urlRecurso", urlRecurso);
		params.add("aliasNombre", aliasNombre);
		params.add("aliasMunicipio", aliasMunicipio);
		params.add("aliasDescripcion", aliasDescripcion); 
		params.add("aliasCategoria", aliasCategoria); 
		params.add("aliasImagen", aliasImagen);
		params.add("aliasDireccion", aliasDireccion);
		params.add("fichaCaracterizacion", fichaCaracterizacion); 
		params.add("contieneHistoria",tieneHistoria);  
		params.add("poligono", poligono); 
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (id == null) {
				params.add("idAplicacion", Long.valueOf(idAplicacionDeCapa));
				respuesta = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			} else {
				params.add("id", Long.valueOf(id));
				respuesta = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			}

			actualizacionFireBase(); //XXXXXXXXXXX FIREBASE

		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		String respuestaString = respuesta.getBody();
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(2L,respuestaString,1); 
		}else { 
		    createUpdateAuditoria(2L,respuestaString,2);
		}		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		return respuestaString;
	}

	/**
	 * Método encargado de devolvernos un listado de las capas correspondientes a
	 * una aplicacion
	 */
	public List<Capa> getCapasByIdAplicacion(String idAplicacion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization",getTokenAcceso());
		HttpEntity<Capa[]> listEntity = new HttpEntity<Capa[]>(headers);

		String listaCapasUrl = getServerEndpoint() + "/api/capa/aplicacion/" + idAplicacion;

		Capa[] listaCapas = null;
		List<Capa> listaCapas2 = null;
		try {
			HttpEntity<Capa[]> listEntityCapa = restTemplate.exchange(listaCapasUrl, HttpMethod.GET, listEntity,Capa[].class);
			if (listEntityCapa.getBody() != null) {
				listaCapas = listEntityCapa.getBody();
				for (Capa capa : listaCapas) {					
					capa = capasColocarRutasIconosEstado(capa);
					capa.setIdAplicacion(Long.valueOf(idAplicacion));
				}
				listaCapas2 = Arrays.asList(listaCapas);
			}
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return listaCapas2;

	}
	
	
	/** Método encargado de eliminar una capa con base en un identificador */
	public String deleteCapa(String idCapa) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Categoria> entity = new HttpEntity<Categoria>(headers);

		String deleteCapaUrl = getServerEndpoint() + "/api/capa/" + idCapa;
		HttpEntity<String> respuesta = null;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(deleteCapaUrl,HttpMethod.DELETE,entity,String.class);
			actualizacionFireBase(); //XXXXXXXXXXX FIREBASE
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		createUpdateAuditoria(2L,"La capa con id: "+idCapa+" ha sido eliminada",3); 
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		return respuesta.getBody();
	}
	
	
	public List<String> obtenerListNombreCapas() {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> listEntity = new HttpEntity<String>(headers);

		String nombresCapasUrl = getServerEndpoint() + "/api/capa/nombre";
		String[] listaNombres = null;
		try {
			HttpEntity<String[]> listEntityNombres 
			    = restTemplate.exchange(nombresCapasUrl, HttpMethod.GET, listEntity,String[].class);
			listaNombres = listEntityNombres.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaNombres);
	}	
	
	/**
	 * Método encargado de devolvernos un listado de los tipos de capas usadas en los
	 * aplicativos
	 */
	public List<TipoCapa> obtenerTipoCapasPorAplicacion(String idAplicacion) {
		List<TipoCapa> listTipoCapas = new ArrayList<TipoCapa>();

		if(idAplicacion.equals("1")) { // Recórreme
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa			
		}		
		if(idAplicacion.equals("2")) { //  Cuídame
			listTipoCapas.add(obtenerTipoCapaById("1")); // Arbol
			listTipoCapas.add(obtenerTipoCapaById("4")); // Reporte	
			listTipoCapas.add(obtenerTipoCapaById("9")); // Mis Publicaciones
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa	
		}
		if(idAplicacion.equals("3")) { // Asómbrate
			listTipoCapas.add(obtenerTipoCapaById("1")); // Arbol
			listTipoCapas.add(obtenerTipoCapaById("4")); // Reporte	
			listTipoCapas.add(obtenerTipoCapaById("9")); // Mis Publicaciones
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa	
			listTipoCapas.add(obtenerTipoCapaById("5")); // Subcapas	
			listTipoCapas.add(obtenerTipoCapaById("8")); // Avistamiento	
		}
		if(idAplicacion.equals("4")) { // Conóceme
			listTipoCapas.add(obtenerTipoCapaById("1")); // Arbol
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa	
			listTipoCapas.add(obtenerTipoCapaById("5")); // Subcapas	
		}
		if(idAplicacion.equals("5")) { // Disfrútame
			listTipoCapas.add(obtenerTipoCapaById("1")); // Arbol
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa	
			listTipoCapas.add(obtenerTipoCapaById("5")); // Subcapas	
		}
		if(idAplicacion.equals("6")) { // Mídeme
			listTipoCapas.add(obtenerTipoCapaById("3")); // Encuesta
			listTipoCapas.add(obtenerTipoCapaById("5")); // Subcapas	
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa	
		}
		if(idAplicacion.equals("7")) { // Contenedora
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa	
		}
		if(idAplicacion.equals("8")) { // Fotografía
			listTipoCapas.add(obtenerTipoCapaById("1")); // Arbol
			listTipoCapas.add(obtenerTipoCapaById("4")); // Reporte	
			listTipoCapas.add(obtenerTipoCapaById("9")); // Mis Publicaciones
			listTipoCapas.add(obtenerTipoCapaById("2")); // Mapa	
			listTipoCapas.add(obtenerTipoCapaById("5")); // Subcapas	
			listTipoCapas.add(obtenerTipoCapaById("8")); // Avistamiento	
		}
						
		return 	listTipoCapas;
	}
	
	
	/**
	 * Método encargado de devolvernos un listado de los tipos de capas usadas en los
	 * aplicativos
	 */
	public List<TipoCapa> obtenerTipoCapasParaCategorias(String idAplicacion) {
		List<TipoCapa> listTipoCapasParaCategorias = new ArrayList<TipoCapa>();
		listTipoCapasParaCategorias.add(obtenerTipoCapaById("2")); // Mapa	
		listTipoCapasParaCategorias.add(obtenerTipoCapaById("9")); // Mis Publicaciones
		
		if(idAplicacion.equals("3")) { // Asómbrate	
			listTipoCapasParaCategorias.add(obtenerTipoCapaById("8")); // Avistamiento	
		}
		return 	listTipoCapasParaCategorias;
	}
	
	
	public TipoCapa obtenerTipoCapaById(String idTipoCapa) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<TipoCapa> entity = new HttpEntity<TipoCapa>(headers);

		String tipoCapaResourceUrl = getServerEndpoint() + "/api/tipocapa/" + idTipoCapa;
		HttpEntity<TipoCapa> entityTipoCapa = null;
		try {
			entityTipoCapa = restTemplate.exchange(tipoCapaResourceUrl,HttpMethod.GET,entity,TipoCapa.class);
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return entityTipoCapa.getBody();
	}
	
	/**
	 * Método encargado de devolvernos un listado de las capas correspondientes a
	 * una aplicacion y de un tipo especifico
	 */
	public List<CapaSubmenu> getCapasByIdAplTipoCapa(String idAplicacion, String listaTipoCapa) {		
		RestTemplate restTemplate = new RestTemplate();		
		String menuResourceUrl = getServerEndpoint() + "/api/aplicacion/obtenerPorIdOTipoCapa?tipoCapas=" + listaTipoCapa
				                                     + "&idAplicacion=" + idAplicacion;
		MenuItem[] listaMenus = null;
		try {
			listaMenus = restTemplate.getForObject(menuResourceUrl, MenuItem[].class);
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		List<CapaSubmenu> listCapas = Arrays.asList(listaMenus).get(0).getCapas();		
		for (CapaSubmenu capaSubmenu : listCapas) { 
			capaSubmenu.setCategorias(getCategoriasByIdTipoCateg(""+capaSubmenu.getId(),"8"));  //8=Tipocategoria=Avistamiento
		}
		return 	listCapas;	
	}
	
	
	public List<CapaSubmenu> getCapasforArboles() {			
		List<CapaSubmenu> capasArboles = new ArrayList<CapaSubmenu>();
		
		Aplicacion aplicacion = new Aplicacion();
		
		aplicacion = obtenerDetalleAplicacion("2");  	
		for (CapaSubmenu capaSubmenu : getCapasByIdAplTipoCapa("2","2,4,5")) {   
			capaSubmenu.setNombre(aplicacion.getNombre() + " : " + capaSubmenu.getNombre());
			capasArboles.add(capaSubmenu);
		}
		
		aplicacion = obtenerDetalleAplicacion("3");  
		for (CapaSubmenu capaSubmenu : getCapasByIdAplTipoCapa("3","2,4,5")) {   
			capaSubmenu.setNombre(aplicacion.getNombre() + " : " + capaSubmenu.getNombre());
			capasArboles.add(capaSubmenu);
		}		
		return capasArboles;		
	}
	
	public List<String> obtenerListUrlsCapas() {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> listEntity = new HttpEntity<String>(headers);

		String urlsCapasUrl = getServerEndpoint() + "/api/capa/urls";
		String[] listaUrls = null;
		try {
			HttpEntity<String[]> listEntityUrls 
			    = restTemplate.exchange(urlsCapasUrl, HttpMethod.GET, listEntity,String[].class);
			listaUrls = listEntityUrls.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaUrls);
	}	
	
	// _______________Administracion de Categorias_______________________

	/**
	 * Método encargado de consultar una categoria con base en un identificador
	 */
	public Categoria obtenerCategoria(String idCategoria) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Categoria> entity = new HttpEntity<Categoria>(headers);

		String categoriaResourceUrl = getServerEndpoint() + "/api/categoria/" + idCategoria;
		Categoria categoria = null;
		try {
			HttpEntity<Categoria> entityCategoria 
			    = restTemplate.exchange(categoriaResourceUrl,HttpMethod.GET,entity,Categoria.class);
			categoria = entityCategoria.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		categoria = categoriasColocarRutasIconosEstado(categoria);
		return categoria;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de categorias al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	public String createUpdateCategoria(String id, String nombre, String descripcion, MultipartFile icono,
			MultipartFile iconoMar, String idCapaDeCategoria, String urlRecurso, String aliasNombre,
			String aliasMunicipio, String aliasDescripcion, String aliasImagen, String aliasDireccion,
			String tipoCategoria, boolean fichaCaracterizacion, String respuestaFichaCarac, 
			boolean poligono,boolean msgOrdenamiento, boolean tieneHistoria, MultipartFile iconoPen, 
			MultipartFile iconoApr, MultipartFile iconoRec) {
		String url = getServerEndpoint() + "/api/categoria";

		MultipartInputStreamFileResource iconoFile = null;
		MultipartInputStreamFileResource iconoMarFile = null;		
		MultipartInputStreamFileResource iconoPenFile = null;
		MultipartInputStreamFileResource iconoAprFile = null;
		MultipartInputStreamFileResource iconoRecFile = null;
		
		try {
			iconoFile = new MultipartInputStreamFileResource(icono.getInputStream(), icono.getOriginalFilename());
		} catch (Exception ex) {}
		
		try {
			iconoMarFile = new MultipartInputStreamFileResource(iconoMar.getInputStream(),iconoMar.getOriginalFilename());
		} catch (Exception ex) {}
		
		try {
			iconoPenFile = new MultipartInputStreamFileResource(iconoPen.getInputStream(),iconoPen.getOriginalFilename());
		} catch (Exception ex) {}
		
		try {
			iconoAprFile = new MultipartInputStreamFileResource(iconoApr.getInputStream(),iconoApr.getOriginalFilename());
		} catch (Exception ex) {}
		
		try {
			iconoRecFile = new MultipartInputStreamFileResource(iconoRec.getInputStream(),iconoRec.getOriginalFilename());
		} catch (Exception ex) {}

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("icono", iconoFile);
		params.add("iconoMarcador", iconoMarFile);
		params.add("nombre", nombre);
		params.add("descripcion", descripcion);
		params.add("tipoCategoria",Long.valueOf(tipoCategoria));
		params.add("urlRecurso", urlRecurso);
		params.add("aliasNombre", aliasNombre);
		params.add("aliasMunicipio", aliasMunicipio);
		params.add("aliasDescripcion", aliasDescripcion);  
		params.add("aliasImagen", aliasImagen); 
		params.add("aliasDireccion", aliasDireccion); 
		params.add("fichaCaracterizacion", fichaCaracterizacion); 
		params.add("respuestaFichaCarac", respuestaFichaCarac); 
		params.add("poligono", poligono); 
		params.add("msgOrdenamiento", msgOrdenamiento); 
		params.add("tieneHistoria",tieneHistoria);
		params.add("iconoPen", iconoPenFile);
		params.add("iconoApr", iconoAprFile);
		params.add("iconoRec", iconoRecFile);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		
		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (id == null) {
				params.add("idCapa", Long.valueOf(idCapaDeCategoria));
				respuesta = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			} else {
				params.add("id", Long.valueOf(id));
				respuesta = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			}

			actualizacionFireBase(); //XXXXXXXXXXX FIREBASE

		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		String respuestaString = respuesta.getBody();
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(3L,respuestaString,1); 
		}else { 
		    createUpdateAuditoria(3L,respuestaString,2);
		}				
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX	
		
		return respuestaString;
	}

	/**
	 * Método encargado de devolvernos un listado de las categorias correspondientes
	 * a una capa
	 */
	public List<Categoria> getCategoriasByIdCapa(String idCapa) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Categoria[]> listEntity = new HttpEntity<Categoria[]>(headers);

		String listaCategoriasUrl = getServerEndpoint() + "/api/categoria/capa/" + idCapa;
		Categoria[] listaCategorias = null;
		List<Categoria> listaCategorias2 = null;
		try {
			HttpEntity<Categoria[]> listEntityCategoria 
			    = restTemplate.exchange(listaCategoriasUrl,HttpMethod.GET,listEntity, Categoria[].class);
			if (listEntityCategoria.getBody() != null) {
				listaCategorias = listEntityCategoria.getBody();
				for (Categoria categoria : listaCategorias) {
					categoria = categoriasColocarRutasIconosEstado(categoria);
				}
				listaCategorias2 = Arrays.asList(listaCategorias);
			}
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}

		return listaCategorias2;
	}

	/** Método encargado de eliminar una categoria con base en un identificador */
	public String deleteCategoria(String idCategoria) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Categoria> entity = new HttpEntity<Categoria>(headers);

		String deleteCategoriaUrl = getServerEndpoint() + "/api/categoria/" + idCategoria;
		HttpEntity<String> respuesta = null;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(deleteCategoriaUrl,HttpMethod.DELETE,entity,String.class);
			actualizacionFireBase(); //XXXXXXXXXXX FIREBASE
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		createUpdateAuditoria(3L,"La capa con id: "+idCategoria+" ha sido eliminada",3); 
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuesta.getBody();
	}
	
	public List<String> obtenerListNombreCategorias() {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> listEntity = new HttpEntity<String>(headers);

		String nombresCategoriasUrl = getServerEndpoint() + "/api/categoria/nombre";
		String[] listaNombres = null;
		try {
			HttpEntity<String[]> listEntityNombres 
			    = restTemplate.exchange(nombresCategoriasUrl, HttpMethod.GET, listEntity,String[].class);
			listaNombres = listEntityNombres.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaNombres);
	}	
	
	
	/**
	 * Método encargado de devolvernos un listado de las capas correspondientes a
	 * una aplicacion y de un tipo especifico
	 */
	public List<CategoriaSubmenu> getCategoriasByIdTipoCateg(String idCapa, String listaTipoCateg) {		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<CategoriaSubmenu[]> listEntity = new HttpEntity<CategoriaSubmenu[]>(headers);

		String listaCategUrl = getServerEndpoint() + "/core/category/"+idCapa+"/"+listaTipoCateg;
		try {
			HttpEntity<CategoriaSubmenu[]> listEntityCateg 
			    = restTemplate.exchange(listaCategUrl,HttpMethod.GET,listEntity,CategoriaSubmenu[].class);			
			return Arrays.asList(listEntityCateg.getBody());
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}			
	}
	
	public List<String> obtenerListUrlsCategorias() {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> listEntity = new HttpEntity<String>(headers);

		String urlsCategoriasUrl = getServerEndpoint() + "/api/categoria/urls";
		String[] listaUrls = null;
		try {
			HttpEntity<String[]> listEntityUrls 
			    = restTemplate.exchange(urlsCategoriasUrl, HttpMethod.GET, listEntity,String[].class);
			listaUrls = listEntityUrls.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaUrls);
	}	
	

    // _______________Administrar Roles________________________________

	/** Método encargado de devolvernos un listado de los roles */
	public List<Rol> obtenerRoles() {
		List<Rol> listaRoles = servicioRoles.rolListarTodos();
		return listaRoles;
	}

	/** Método encargado de consultar un rol con base en un identificador */
	public Rol obtenerDetalleRol(String idRol) {
		return servicioRoles.getRolById(Long.valueOf(idRol));

	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de roles al respectivo servicio web encargado de procesarlas y luego
	 * retornarnos un mensaje de confirmación o rechazo
	 */
	public String createUpdateRol(String id, String nombre, String descripcion, String activo) {
		String respuesta = "";
		Rol rol = new Rol();
		    		
		if(id!=null) {
		    if (!id.equals("null")) {
		    	rol.setId(Long.valueOf(id));
		    }
		}		
		rol.setNombre(nombre);
		rol.setDescripcion(descripcion);
		rol.setActivo(Boolean.valueOf(activo));
		Boolean exito = servicioRoles.safeUpdateRol(rol);

		if (exito) {
			if (id == null) {
				respuesta = "Se agregó un nuevo Rol";
			} else {
				respuesta = "Se ha actualizado un Rol";
			}
		} else {
			respuesta = "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(10L,"Un nuevo rol con id: ?? ha sido creado",1); 
		}else { 
		    createUpdateAuditoria(10L,"El rol con id: "+id+" ha sido actualizado",2);
		}
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuesta;		
	}
	
	/** Método encargado de editar los permisos que cada rol tiene sobre cada objeto.*/
	public String updatePermisosRol(List<PermisoRolVista> listPermisosRol) {	
		String respuesta = null;
		for (PermisoRolVista permisoRol : listPermisosRol) {				
			try {				
				PermisoRol permisoRolDB = new PermisoRol();
				permisoRolDB.setId(permisoRol.getId());
				permisoRolDB.setIdObjeto(permisoRol.getIdObjeto());
				permisoRolDB.setIdRol(permisoRol.getIdRol());
				permisoRolDB.setPuedeAdicionar(permisoRol.getPuedeAdicionar());
				permisoRolDB.setPuedeBorrar(permisoRol.getPuedeBorrar());
				permisoRolDB.setPuedeConsultar(permisoRol.getPuedeConsultar());
				permisoRolDB.setPuedeEditar(permisoRol.getPuedeEditar());
				permisoRolDB.setPuedeImprimir(permisoRol.getPuedeImprimir());
								
				Boolean exito = servicioPermisoRoles.safeUpdatePermisoRol(permisoRolDB);
				if (exito) {
				    respuesta = "La información se guardó con éxito";
				} else {
					respuesta = "Error en el proceso";
				}				
			} catch (Exception ex) {
				LOGGER.error("Error durante el proceso: "+ex.getMessage());
				respuesta = "Error en el proceso";
			}
		}		
		return respuesta;
	}	
	
	/** Método encargado de obtener el listado de los permisos que cada rol tiene sobre cada objeto */	
	public List<PermisoRolVista> obtenerPermisosPorRol(String idRol) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		List<PermisoRolVista> listaPermisos = new ArrayList<PermisoRolVista>();
		
		if(idRol!=null) {			
    		HttpEntity<Objeto[]> listEntity = new HttpEntity<Objeto[]>(headers);
    		String listObjetosUrl = getServerEndpoint() + "/api/objeto";
    		Objeto[] listaObjetos = null;
    		try {					
    			HttpEntity<Objeto[]> listEntityObjeto 
    			    = restTemplate.exchange(listObjetosUrl,HttpMethod.GET,listEntity,Objeto[].class);
    			listaObjetos = listEntityObjeto.getBody();
    		} catch (Exception ex) {
    			System.out.print("Error: " + ex);
    			return null;
    		}
    		   		
    		if(listaObjetos!=null) {
    		    for (Objeto objeto : Arrays.asList(listaObjetos)) { 
    		    	Long idObjeto = objeto.getId();
    		    	Long idRol2 = Long.valueOf(idRol);
    		    	    		    	
    			    PermisoRolVista permisoRol = new PermisoRolVista();
    			    permisoRol.setId(null);
    			    permisoRol.setIdObjeto(idObjeto);
    			    permisoRol.setIdRol(idRol2);
    			    permisoRol.setNombreObjeto(objeto.getNombre());
    			    permisoRol.setTipoObjeto(objeto.getTipoObjeto().getNombre()); 
    			    
    			    permisoRol.setCheckAdicionar(objeto.getPuedeAdicionar()); 
			        permisoRol.setCheckBorrar(objeto.getPuedeBorrar());
			        permisoRol.setCheckConsultar(objeto.getPuedeConsultar());
			        permisoRol.setCheckEditar(objeto.getPuedeEditar()); 
			        permisoRol.setCheckImprimir(objeto.getPuedeImprimir());
    		          				
    				try {		
    					PermisoRol permiso = servicioPermisoRoles.permisoRolObtenerPermisoPorRolPorObjeto(idRol2, idObjeto);
   					
    					if(permiso!=null) {
    						permisoRol.setId(permiso.getId());
    						permisoRol.setPuedeAdicionar(permiso.getPuedeAdicionar()); 
    				        permisoRol.setPuedeBorrar(permiso.getPuedeBorrar());
    				        permisoRol.setPuedeConsultar(permiso.getPuedeConsultar());
    				        permisoRol.setPuedeEditar(permiso.getPuedeEditar()); 
    				        permisoRol.setPuedeImprimir(permiso.getPuedeImprimir()); 
    					} else {
    						permisoRol.setPuedeAdicionar(false); 
        					permisoRol.setPuedeBorrar(false);
        					permisoRol.setPuedeConsultar(false);
        					permisoRol.setPuedeEditar(false); 
        					permisoRol.setPuedeImprimir(false);    						
    					}
    				} catch (Exception ex) {   					
    					permisoRol.setPuedeAdicionar(false); 
    					permisoRol.setPuedeBorrar(false);
    					permisoRol.setPuedeConsultar(false);
    					permisoRol.setPuedeEditar(false); 
    					permisoRol.setPuedeImprimir(false); 
    				}    		        		        
    		        listaPermisos.add(permisoRol); 
    		    }
    		} 
		}
		return listaPermisos;
	}

	// ______________Administrar Usuarios___________________________________

	/** Método encargado de devolvernos un listado de los usuarios */
	public List<Usuario> obtenerUsuarios() {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Usuario[]> listEntity = new HttpEntity<Usuario[]>(headers);

		String listaUsuariosUrl = getServerEndpoint() + "/api/usuario";
		Usuario[] listaUsuarios = null;
		try {
			HttpEntity<Usuario[]> listEntityUsuario = restTemplate.exchange(listaUsuariosUrl, HttpMethod.GET,listEntity, Usuario[].class);
			listaUsuarios = listEntityUsuario.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}

		return Arrays.asList(listaUsuarios);
	}

	/**
	 * Método encargado de consultar un usuario con base en un identificador.
	 */
	public Usuario obtenerDetalleUsuario(String idUsuario) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Usuario> entity = new HttpEntity<Usuario>(headers);

		String usuarioUrl = getServerEndpoint() + "/api/usuario/" + idUsuario;
		Usuario usuario = null;
		try {
			HttpEntity<Usuario> entityUsuario = restTemplate.exchange(usuarioUrl, HttpMethod.GET, entity,Usuario.class);
			usuario = entityUsuario.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return usuario;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de usuarios al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	public String createUpdateUsuario(String id, String username, String contrasena, String nombreFuenteRegistro,
			                          Boolean activo, String idRol) {
		HttpEntity<String> respuesta = null;
		String respuestafINAL = null;
		String url = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (id == null) {
				UsuarioJSON usuario = new UsuarioJSON();
				usuario.setUsername(username);
				usuario.setNickname(username);
				usuario.setContrasena(contrasena);
				usuario.setNombreFuenteRegistro(nombreFuenteRegistro);
				url = getServerEndpoint() + "/api/registro";
				respuesta = restTemplate.postForEntity(url, usuario, String.class);
			} else {
				String usuario = "{" + "\"id\":" + Long.valueOf(id) + "," 
			                         + "\"username\":\"" + username + "\"," + "\"nickname\":\"" + username + "\","
						             + "\"activo\":" + activo + "," + "\"idRol\":" + idRol + "}";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Authorization", getTokenAcceso());
				HttpEntity<String> entity = new HttpEntity<String>(usuario, headers);

				url = getServerEndpoint() + "/api/usuario-actualizar-rol";
				respuesta = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
			}
			respuestafINAL = respuesta.getBody();
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			respuestafINAL = "Error en el Proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(9L,"Un nuevo usuario con id: ?? ha sido creado",1); 
		}else { 
		    createUpdateAuditoria(9L,"El usuario con id: "+id+" ha sido actualizado",2);
		}
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuestafINAL;
	}

	/** Método encargado de consultar un usuario con base en su username */
	public Usuario obtenerUsuarioByUsername(String username) {
		RestTemplate restTemplate = new RestTemplate();
		String url = getServerEndpoint() + "/api/usuario-por-username";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getTokenAcceso());
        				
		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("username",username);
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		
		Usuario usuario = null;
		try {
			HttpEntity<Usuario> entityUsuario 			
			    = restTemplate.exchange(url,HttpMethod.POST,requestEntity,Usuario.class);
			usuario = entityUsuario.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}

		return usuario;
	}

	/**
	 * Método encargado de Loguear un usuario y retornarnos su informacion y un
	 * token de acceso
	 */
	public RespuestaLogin loguinUsuario(String username, String contrasena, String fuenteRegistro) {

		UsuarioJSON usuario = new UsuarioJSON();
		usuario.setUsername(username);
		usuario.setNickname(username);
		usuario.setContrasena(contrasena);
		usuario.setNombreFuenteRegistro(fuenteRegistro);

		RestTemplate restTemplate = new RestTemplate();
		String usuarioResourceUrl = getServerEndpoint() + "/api/login-ccontrol";
		RespuestaLogin respuestaLogin = null;

		try {
			HttpEntity<RespuestaLogin> object = restTemplate.postForEntity(usuarioResourceUrl,usuario,RespuestaLogin.class);
			respuestaLogin = object.getBody();
		} catch (Exception ex) {
			System.out.print("Error en proceso de Logueo: " + ex);
			return null;
		}
		return respuestaLogin;
		
	}
	
	
	/**
	 * Método encargado de Loguear un usuario y retornarnos su informacion y un
	 * token de acceso
	 */
	public Boolean validarRegistro(String idUsuario, String token) {
		RestTemplate restTemplate = new RestTemplate();
		String validacionUrl = getServerEndpoint() + "/api/confirmar-registro?user="+idUsuario+"&token="+token;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Boolean> entity = new HttpEntity<Boolean>(headers);
		try {
			HttpEntity<Boolean> entityBoolean = restTemplate.exchange(validacionUrl,HttpMethod.GET,entity,Boolean.class);
			return entityBoolean.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return false;
		}
	}
	

	/**
	 * Método encargado de editar los permisos que cada usuario tiene sobre cada objeto.
	 */
	public String updatePermisosUsuario(List<PermisoUsuarioVista> listPermisosUsuario) {
		String url = getServerEndpoint() + "/api/permisousuario/";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());		
		String respuesta = null;

		for (PermisoUsuarioVista permisoUsuario : listPermisosUsuario) {				
			PermisoUsuario permisoUsuarioDB = new PermisoUsuario();
			permisoUsuarioDB.setId(permisoUsuario.getId());
			permisoUsuarioDB.setIdObjeto(permisoUsuario.getIdObjeto());
			permisoUsuarioDB.setIdUsuario(permisoUsuario.getIdUsuario());
			permisoUsuarioDB.setPuedeAdicionar(permisoUsuario.getPuedeAdicionar());
			permisoUsuarioDB.setPuedeBorrar(permisoUsuario.getPuedeBorrar());
			permisoUsuarioDB.setPuedeConsultar(permisoUsuario.getPuedeConsultar());
			permisoUsuarioDB.setPuedeEditar(permisoUsuario.getPuedeEditar());
			permisoUsuarioDB.setPuedeImprimir(permisoUsuario.getPuedeImprimir());
			
			HttpEntity<PermisoUsuario> requestEntity = new HttpEntity<PermisoUsuario>(permisoUsuarioDB, headers);			
			try {
				RestTemplate restTemplate = new RestTemplate();
				respuesta = restTemplate.postForObject(url, requestEntity, String.class);
			} catch (Exception ex) {
				System.out.println("Error: " + ex);
			}
		}		
		if(respuesta != null) {
		    return "La información se guardó con éxito";
		} else {
			return "Error en el proceso";
		}
	}

	/** Método encargado de obtener el listado de los permisos que cada usuario tiene sobre cada objeto */
    public List<PermisoUsuarioVista> obtenerPermisosPorUsuario(String idUsuario) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		List<PermisoUsuarioVista> listaPermisos = new ArrayList<PermisoUsuarioVista>();
		
		if(idUsuario!=null) {			
    		HttpEntity<Objeto[]> listEntity = new HttpEntity<Objeto[]>(headers);
    		String listObjetosUrl = getServerEndpoint() + "/api/objeto";
    		Objeto[] listaObjetos = null;
    		try { 					
    			HttpEntity<Objeto[]> listEntityObjeto 
    			    = restTemplate.exchange(listObjetosUrl,HttpMethod.GET,listEntity,Objeto[].class);
    			listaObjetos = listEntityObjeto.getBody();
    		} catch (Exception ex) {
    			System.out.print("Error: " + ex);
    			return null;
    		}
    		   		
    		if(listaObjetos!=null) {
    		    for (Objeto objeto : Arrays.asList(listaObjetos)) { 
    		    	Long idObjeto = objeto.getId();
    		    	Long idUsuarioLong = Long.valueOf(idUsuario);
    		    	    		    	
    			    PermisoUsuarioVista permisoUsuario = new PermisoUsuarioVista();
    			    permisoUsuario.setId(null);
    		        permisoUsuario.setIdObjeto(idObjeto);
    		        permisoUsuario.setIdUsuario(idUsuarioLong);
    		        permisoUsuario.setNombreObjeto(objeto.getNombre());
    		        permisoUsuario.setTipoObjeto(objeto.getTipoObjeto().getNombre()); 
    		        
    		        permisoUsuario.setCheckAdicionar(objeto.getPuedeAdicionar()); 
			        permisoUsuario.setCheckBorrar(objeto.getPuedeBorrar());
			        permisoUsuario.setCheckConsultar(objeto.getPuedeConsultar());
			        permisoUsuario.setCheckEditar(objeto.getPuedeEditar()); 
			        permisoUsuario.setCheckImprimir(objeto.getPuedeImprimir());
    		        
    		        String permisosUrl = getServerEndpoint() + "/api/permisousuario/usuario/objeto";   				
    				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(permisosUrl)
    				.queryParam("idUsuario",idUsuarioLong)
    				.queryParam("idObjeto",idObjeto);   				
    				HttpEntity<?> entity = new HttpEntity<>(headers);
    				   				
    				try {
    					HttpEntity<PermisoUsuario> entityPermiso 
    					    = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.GET,entity,PermisoUsuario.class);
    					PermisoUsuario permiso = entityPermiso.getBody();
    					
    					if(permiso!=null) {
    						permisoUsuario.setId(permiso.getId());
    						permisoUsuario.setPuedeAdicionar(permiso.getPuedeAdicionar()); 
    				        permisoUsuario.setPuedeBorrar(permiso.getPuedeBorrar());
    				        permisoUsuario.setPuedeConsultar(permiso.getPuedeConsultar());
    				        permisoUsuario.setPuedeEditar(permiso.getPuedeEditar()); 
    				        permisoUsuario.setPuedeImprimir(permiso.getPuedeImprimir());  
    					}
    				} catch (Exception ex) { 
    					Long idRol = obtenerDetalleUsuario(idUsuario).getIdRol();
						PermisoRol permisoRol = servicioPermisoRoles.permisoRolObtenerPermisoPorRolPorObjeto(idRol,idObjeto);    	   					
    					if(permisoRol!=null) {
    						permisoUsuario.setPuedeAdicionar(permisoRol.getPuedeAdicionar()); 
    				        permisoUsuario.setPuedeBorrar(permisoRol.getPuedeBorrar());
    				        permisoUsuario.setPuedeConsultar(permisoRol.getPuedeConsultar());
    				        permisoUsuario.setPuedeEditar(permisoRol.getPuedeEditar()); 
    				        permisoUsuario.setPuedeImprimir(permisoRol.getPuedeImprimir()); 
    					}
    				}    		        		        
    		        listaPermisos.add(permisoUsuario); 
    		    }
    		} 
		}
		return listaPermisos;
	}	
    
    
	/** Método encargado de obtener el listado de los permisos que cada usuario tiene sobre cada objeto */
    public PermisoUsuarioVista obtenerPermisoObjetoPorUsuario(Long idUsuario,
    		                                                  String idObjetoVista) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());    	
                
        Objeto objeto = obtenerDetalleObjeto(idObjetoVista);
    	Long idObjeto = objeto.getId();
  		    	
	    PermisoUsuarioVista permisoUsuario = new PermisoUsuarioVista();
  		        
        String permisosUrl = getServerEndpoint() + "/api/permisousuario/usuario/objeto";   				
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(permisosUrl)
		.queryParam("idUsuario",idUsuario)
		.queryParam("idObjeto",idObjeto);   				
		HttpEntity<?> entity = new HttpEntity<>(headers);
  				
		try {
			HttpEntity<PermisoUsuario> entityPermiso 
			    = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.GET,entity,PermisoUsuario.class);
			PermisoUsuario permiso = entityPermiso.getBody();
		
			if(permiso!=null) {
				permisoUsuario.setPuedeAdicionar(permiso.getPuedeAdicionar()); 
		        permisoUsuario.setPuedeBorrar(permiso.getPuedeBorrar());
		        permisoUsuario.setPuedeConsultar(permiso.getPuedeConsultar());
		        permisoUsuario.setPuedeEditar(permiso.getPuedeEditar()); 
		        permisoUsuario.setPuedeImprimir(permiso.getPuedeImprimir());  
			}
		} catch (Exception ex) { 
			Long idRol = obtenerDetalleUsuario(idUsuario.toString()).getIdRol();
            PermisoRol permisoRol = servicioPermisoRoles.permisoRolObtenerPermisoPorRolPorObjeto(idRol,idObjeto);    	   					
			if(permisoRol!=null) {
				permisoUsuario.setPuedeAdicionar(permisoRol.getPuedeAdicionar()); 
		        permisoUsuario.setPuedeBorrar(permisoRol.getPuedeBorrar());
		        permisoUsuario.setPuedeConsultar(permisoRol.getPuedeConsultar());
		        permisoUsuario.setPuedeEditar(permisoRol.getPuedeEditar()); 
		        permisoUsuario.setPuedeImprimir(permisoRol.getPuedeImprimir()); 
			}
		}    		        		        
  		return permisoUsuario; 
               
	}	    
	

	// _______________Administrar Objeto____________________________

	/** Método encargado de devolvernos un listado de los objetos */
	public List<Objeto> obtenerObjetos() {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Objeto[]> listEntity = new HttpEntity<Objeto[]>(headers);

		String listaObjetosUrl = getServerEndpoint() + "/api/objeto";
		Objeto[] listaObjetos = null;
		try {
			HttpEntity<Objeto[]> listEntityObjeto 
			    = restTemplate.exchange(listaObjetosUrl,HttpMethod.GET,listEntity,Objeto[].class);
			listaObjetos = listEntityObjeto.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaObjetos);
	}
	

	/** Método encargado de consultar un objeto con base en un identificador */
	public Objeto obtenerDetalleObjeto(String idObjeto) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Objeto> entity = new HttpEntity<Objeto>(headers);

		String objetoUrl = getServerEndpoint() + "/api/objeto/" + idObjeto;
		Objeto objeto = null;
		try {
			HttpEntity<Objeto> entityObjeto = restTemplate.exchange(objetoUrl, HttpMethod.GET, entity, Objeto.class);
			objeto = entityObjeto.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return objeto;
	}
	
	/** Método encargado de editar los permisos que cada rol tiene sobre cada objeto.*/
	public String updateAuditorias(List<Objeto> objetos) {	
		String respuesta = null;
		for (Objeto objeto : objetos) {	
			try {				
				respuesta = createUpdateObjeto(objeto.getId().toString(),objeto.getNombre(),
						objeto.getDescripcion(),null,objeto.getTipoObjeto().getId().toString(),
						objeto.getActivo(),objeto.getPuedeAdicionar(),objeto.getPuedeBorrar(),
						objeto.getPuedeConsultar(),objeto.getPuedeEditar(),objeto.getPuedeImprimir(),
						objeto.getAuditarAdicion(),objeto.getAuditarBorrar(),objeto.getAuditarConsulta(),
						objeto.getAuditarEdicion(),objeto.getAuditarImprimir(),
						objeto.getPuedeAuditarAdicion(),
						objeto.getPuedeAuditarBorrar(),
						objeto.getPuedeAuditarConsulta(),
						objeto.getPuedeAuditarEdicion(),
						objeto.getPuedeAuditarImprimir());			
			} catch (Exception ex) {
				LOGGER.error("Error durante el proceso: "+ex.getMessage());
				return "Error en el proceso";
			}
		}		
		
		return "Las auditorias han sido actualizadas satisfactoriamente";
	}	

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de objetos al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	public String createUpdateObjeto(String id,String nombre,String descripcion,
		String idObjetoEntidad,String idTipoObjeto,boolean activo,
	    boolean puedeAdicionar,boolean puedeBorrar,boolean puedeConsultar,boolean puedeEditar,boolean puedeImprimir,
	    boolean auditarAdicion,boolean auditarBorrar,boolean auditarConsulta,boolean auditarEdicion,boolean auditarImprimir,
	    boolean puedeAuditarAdicion,boolean puedeAuditarBorrar,boolean puedeAuditarConsulta,
	    boolean puedeAuditarEdicion,boolean puedeAuditarImprimir) {
		
		TipoObjeto tipoObjeto = obtenerDetalleTipoObjeto(idTipoObjeto); 
	
		String objeto = "{" + "\"id\":" + id + "," + "\"nombre\":\"" + nombre + "\"," + 
				              "\"idObjetoEntidad\":" + idObjetoEntidad + "," + "\"activo\":" + activo + "," + 
				              "\"puedeAdicionar\":" + puedeAdicionar + "," + "\"puedeBorrar\":" + puedeBorrar + "," + 
				              "\"puedeConsultar\":" + puedeConsultar + "," + "\"puedeEditar\":" + puedeEditar + "," + 
				              "\"puedeImprimir\":" + puedeImprimir + "," + "\"auditarImprimir\":" + auditarImprimir + "," + 
				              "\"auditarAdicion\":" + auditarAdicion + "," + "\"auditarBorrar\":" + auditarBorrar + "," + 
				              "\"auditarConsulta\":" + auditarConsulta + "," + "\"auditarEdicion\":" + auditarEdicion + "," + 				              
                              "\"puedeAuditarAdicion\":" + puedeAuditarAdicion + "," + 
                              "\"puedeAuditarBorrar\":" + puedeAuditarBorrar + "," + 
                              "\"puedeAuditarConsulta\":" + puedeAuditarConsulta + "," + 
                              "\"puedeAuditarEdicion\":" + puedeAuditarEdicion + "," + 
                              "\"puedeAuditarImprimir\":" + puedeAuditarImprimir + "," + 				              
				              "\"descripcion\":\"" + descripcion + "\"," +   
		                      "\"tipoObjeto\":{\"id\":" + idTipoObjeto + "," +
		                         		       "\"descripcion\":\"" + tipoObjeto.getDescripcion() + "\"," +
		                         		       "\"nombre\":\"" + tipoObjeto.getNombre() + "\"}" +
		                      "}";
		System.out.println(objeto);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> entity = new HttpEntity<String>(objeto,headers);
		
		String url = getServerEndpoint() + "/api/objeto";
		HttpEntity<Objeto> respuesta = null;
		try {
			if(id==null) {			
			    respuesta = restTemplate.exchange(url,HttpMethod.POST,entity,Objeto.class);  
			} else {
				respuesta = restTemplate.exchange(url,HttpMethod.PUT,entity,Objeto.class);  
			}			
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
				
		if (respuesta != null) {
			return "Se ha agregado un nuevo objeto";
		}else {
			return "Error en el proceso";
		}

	}
	
	/** Método encargado de consultar un objeto con base en un identificador */
	public TipoObjeto obtenerDetalleTipoObjeto(String idTipoObjeto) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Objeto> entity = new HttpEntity<Objeto>(headers);

		String tipoObjetoUrl = getServerEndpoint() + "/api/tipoobjeto/" + idTipoObjeto;
		TipoObjeto tipoObjeto = null;
		try {
			HttpEntity<TipoObjeto> entityObjeto = restTemplate.exchange(tipoObjetoUrl,HttpMethod.GET,entity,TipoObjeto.class);
			tipoObjeto = entityObjeto.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return tipoObjeto;
	}

	// _______________Administrar Arboles de Decision_____________________________________

	/** Método encargado de devolvernos un listado de los arboles de decision */
	public List<ArbolDecision> obtenerArbolesDecision() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ArbolDecision[]> listEntity = new HttpEntity<ArbolDecision[]>(headers);

		String listaArbolesUrl = getServerEndpoint() + "/api/arbol";
		ArbolDecision[] listaArboles = null;
		try {
			HttpEntity<ArbolDecision[]> listEntityCapa 
			    = restTemplate.exchange(listaArbolesUrl,HttpMethod.GET,listEntity, ArbolDecision[].class);
			listaArboles = listEntityCapa.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
				
		List<ArbolDecision> listaArboles2 = new ArrayList<ArbolDecision>();
		for (ArbolDecision arbol : Arrays.asList(listaArboles)) {		
			String idCapa = arbol.getIdCapa();  			
			String nombreAplicacion = "";	
			String nombreCapa = "";			
			if(idCapa!=null) {
				String idAplicacion = obtenerAplicacionPorIdCapa(idCapa).getId().toString();			
				nombreAplicacion = obtenerDetalleAplicacion(idAplicacion).getNombre();
				nombreCapa = obtenerDetalleCapa(idCapa).getNombre(); 
			}						
			String nombreCategoria = "";			
			if(arbol.getIdCategoria()!=null) {
				nombreCategoria = obtenerCategoria(arbol.getIdCategoria()).getNombre();
			}				
			arbol.setRutaCapaCateg(nombreAplicacion+" / "+nombreCapa+" / "+nombreCategoria);			
		    listaArboles2.add(arbol);
		}		
		return listaArboles2;
	}

	/**
	 * Método encargado de consultar un arbol de decision con base en un identificador
	 */
	public ArbolDecision obtenerDetalleArbol(String idArbol) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ArbolDecision> entity = new HttpEntity<ArbolDecision>(headers);

		String arbolDecisionUrl = getServerEndpoint() + "/api/arbol/" + idArbol;
		ArbolDecision arbol = null;
		try {
			HttpEntity<ArbolDecision> entityUsuario 
			    = restTemplate.exchange(arbolDecisionUrl,HttpMethod.GET,entity,ArbolDecision.class);
			arbol = entityUsuario.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		return arbol;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de arboles de decision al respectivo servicio web encargado de
	 * procesarlos y luego retornarnos un mensaje de confirmación o rechazo
	 */
	public String createUpdateArbol(String id, String nombre, String descripcion, String idCapa, 
			                        String idCategoria, Boolean activo) {		
		RestTemplate restTemplate = new RestTemplate();		
		String arbolUrl = getServerEndpoint() + "/api/arbol?nombre=" + nombre + "&descripcion=" + descripcion +
				                                "&activo=" + activo + "&idCapa=" + idCapa;
		if(idCategoria!=null) {
			arbolUrl = arbolUrl + "&idCategoria=" + idCategoria;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);  
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
	    = new HttpEntity<LinkedMultiValueMap<String, Object>>(headers);
        
	    HttpEntity<String> respuesta = null;
	    try {
	    	if(id==null) {
		        respuesta = restTemplate.exchange(arbolUrl,HttpMethod.POST,requestEntity,String.class);
	    	}else {
	    		arbolUrl = arbolUrl + "&idArbol=" + id;
	    		respuesta = restTemplate.exchange(arbolUrl,HttpMethod.PUT,requestEntity,String.class);
	    	}
	    } catch (Exception ex) {
	    	System.out.print("Error: " + ex);
	    	return null;
	    }
	    
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(4L,"Un nuevo árbol de decisión con id: ?? ha sido creado",1); 
		}else { 
		    createUpdateAuditoria(4L,"El árbol de decisión con id: "+id+" ha sido actualizado",2);
		}		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
	    
		return respuesta.getBody();
	}

	/**
	 * Método encargado de eliminar un arbol de decision con base en un identificador
	 */
	public String deleteArbol(String idArbol) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ArbolDecision> entity = new HttpEntity<ArbolDecision>(headers);

		String arbolUrl = getServerEndpoint() + "/api/arbol/" + idArbol;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(arbolUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		createUpdateAuditoria(4L,"El árbol de decisión con id: "+idArbol+" ha sido eliminado",3); 
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		return respuesta.getBody();
	}

	// ___________Administrar Nodos de Arboles de Decision__________________________

	/**
	 * Método encargado de devolvernos un listado de nodos raiz de un arbol de decisión
	 */
	public List<NodoArbol> obtenerNodosRaizArbol(String idArbol) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<NodoArbol[]> listEntity = new HttpEntity<NodoArbol[]>(headers);

		String listaCapasUrl = getServerEndpoint() + "/api/nodo-arbol/raiz/arbol/" + idArbol;
		NodoArbol[] listaNodos = null;
		try {
			HttpEntity<NodoArbol[]> listEntityCapa 
			    = restTemplate.exchange(listaCapasUrl, HttpMethod.GET, listEntity,NodoArbol[].class);
			listaNodos = listEntityCapa.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}

		return Arrays.asList(listaNodos);
	}

	/** Método encargado de consultar un nodo con base en un identificador */
	public NodoArbol obtenerDetalleNodo(String idNodo) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<NodoArbol> entity = new HttpEntity<NodoArbol>(headers);

		String nodoUrl = getServerEndpoint() + "/api/nodo-arbol/" + idNodo;
		NodoArbol nodo = null;
		try {
			HttpEntity<NodoArbol> entityUsuario 
			    = restTemplate.exchange(nodoUrl,HttpMethod.GET,entity,NodoArbol.class);
			nodo = entityUsuario.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		
		return nodo;
	}

	/** Método encargado de actualizar la información de un nodo. */
	public String createUpdateNodoArbol(String idNodo, String nombre, String descripcion, String alias,
			String instrucciones, String instruccionesDetalladas, String orden, String formatoMedia, 
			MultipartFile multimedia, String urlMediaVideoAudio, String idPadre, String idArbol, 
			List<String> rutaNodos, boolean flagHijosDropdown, String pedirMultimedia,
			String idAutoridadCompetente,MultipartFile iconoVigiaApr,MultipartFile iconoVigiaPen,
			MultipartFile iconoVigiaRec,MultipartFile iconoVigiaWin) {
		
		String url = getServerEndpoint() + "/api/nodo-arbol";

		MultipartInputStreamFileResource multimediaFile = null;
		try {
			multimediaFile = new MultipartInputStreamFileResource(multimedia.getInputStream(),multimedia.getOriginalFilename());
		} catch (Exception ex) {}
		
		MultipartInputStreamFileResource iconoVigiaPenFile = null;
		try {
			iconoVigiaPenFile = new MultipartInputStreamFileResource(iconoVigiaPen.getInputStream(),iconoVigiaPen.getOriginalFilename());
		} catch (Exception ex) {}
		
		MultipartInputStreamFileResource iconoVigiaAprFile = null;
		try {
			iconoVigiaAprFile = new MultipartInputStreamFileResource(iconoVigiaApr.getInputStream(),iconoVigiaApr.getOriginalFilename());
		} catch (Exception ex) {}
		
		MultipartInputStreamFileResource iconoVigiaRecFile = null;
		try {
			iconoVigiaRecFile = new MultipartInputStreamFileResource(iconoVigiaRec.getInputStream(),iconoVigiaRec.getOriginalFilename());
		} catch (Exception ex) {}
		
		MultipartInputStreamFileResource iconoVigiaWinFile = null;
		try {
			iconoVigiaWinFile = new MultipartInputStreamFileResource(iconoVigiaWin.getInputStream(),iconoVigiaWin.getOriginalFilename());
		} catch (Exception ex) {}

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();		
		params.add("multimedia", multimediaFile);
		params.add("iconoVigiaPen", iconoVigiaPenFile);
		params.add("iconoVigiaApr", iconoVigiaAprFile);
		params.add("iconoVigiaRec", iconoVigiaRecFile);
		params.add("iconoVigiaWin", iconoVigiaWinFile);
		params.add("nombre", nombre);
		params.add("descripcion", descripcion);
		params.add("alias", alias);
		params.add("instrucciones", instrucciones);
		params.add("instruccionesDetalladas", instruccionesDetalladas);
		params.add("orden", Integer.parseInt(orden));		
		params.add("flagHijosDropdown", flagHijosDropdown);
		
		if (!idAutoridadCompetente.equals("0")) {
		    params.add("idAutoridadCompetente",Long.valueOf(idAutoridadCompetente));
		}
		
		if (pedirMultimedia.equals("none")) {
		    params.add("formatoMedia","Sin Multimedia");
		}else {
			params.add("formatoMedia",formatoMedia);
		}
		
		if (urlMediaVideoAudio!=null) {
		    if (!urlMediaVideoAudio.equals("null")) {
		    	params.add("urlMediaVideoAudio", urlMediaVideoAudio);
	    	}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);

		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (idNodo == null || idNodo.equals("null")) {
				params.add("idArbol", Long.valueOf(idArbol));
				if (!idPadre.equals("null")) {
				    params.add("idPadre", Long.valueOf(idPadre));
				}
				respuesta = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			} else {
				params.add("idNodo", Long.valueOf(idNodo));
				respuesta = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			}
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (idNodo == null) {
			createUpdateAuditoria(8L,"Un nuevo nodo del arbol con id: "+idArbol+" ha sido creado",1); 
		}else { 
		    createUpdateAuditoria(8L,"El nodo con id: "+idNodo+" del arbol con id: "+idArbol+" ha sido actualizado",2);
		}		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		

		return respuesta.getBody();
	}

	/**
	 * Método encargado de devolvernos un listado de los nodos correspondientes a un nodo padre
	 */
	public List<NodoArbol> obtenerNodosByPadre(String idPadre) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<NodoArbol[]> listEntity = new HttpEntity<NodoArbol[]>(headers);

		String listaCapasUrl = getServerEndpoint() + "/api/nodo-arbol/padre/" + idPadre;
		NodoArbol[] listaNodos = null;
		try {
			HttpEntity<NodoArbol[]> listEntityCapa 
			    = restTemplate.exchange(listaCapasUrl,HttpMethod.GET,listEntity,NodoArbol[].class);
			listaNodos = listEntityCapa.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}

		return Arrays.asList(listaNodos);
	}

	/** Método encargado de eliminar un Nodo con base en un identificador */
	public String deleteNodoArbol(String idNodo) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<NodoArbol> entity = new HttpEntity<NodoArbol>(headers);

		String nodoUrl = getServerEndpoint() + "/api/nodo-arbol/" + idNodo;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(nodoUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
        createUpdateAuditoria(8L,"El nodo con id: "+idNodo+" ha sido eliminado",3); 
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuesta.getBody();
	}
	
	
	public List<String> obtenerListOrdenesVista(List<NodoArbol> ListNodos) {
		List<String> ListOrdenesVista = new ArrayList<String>();
		for (NodoArbol nodo : ListNodos) {
		    ListOrdenesVista.add(nodo.getOrden());
	    }
		return ListOrdenesVista;
	}	
	
	
	/**
	 * Método encargado de verificar si un nodo tiene padres o no
	 */
	public Boolean verificarNodoFinal(String idPadre) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);   
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Integer> listEntity = new HttpEntity<Integer>(headers);   
		String listaCapasUrl = getServerEndpoint() + "/api/nodo-arbol/cantidadhijos/" + idPadre;
		try {
			HttpEntity<Integer> listEntityCapa 
			    = restTemplate.exchange(listaCapasUrl,HttpMethod.GET,listEntity,Integer.class);			
			if(listEntityCapa.getBody()==0) {
			    return true;	
			} else {
				return false;
			}	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return true;
		}
	}
	
	

	// ___________________Administracion de Formularios____________________________

	/**
	 * Método encargado de devolvernos un listado de los formularios
	 */
	public List<Formulario> obtenerFormularios() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Formulario[]> listEntity = new HttpEntity<Formulario[]>(headers);

		String listaFormulariosUrl = getServerEndpoint() + "/api/formulario";
		Formulario[] listaFormularios = null;
		try {
			HttpEntity<Formulario[]> listEntityFormulario 
			    = restTemplate.exchange(listaFormulariosUrl, HttpMethod.GET,listEntity, Formulario[].class);
			listaFormularios = listEntityFormulario.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaFormularios);
	}
	

	/** Método encargado de consultar un formulario con base en un identificador */
	public Formulario obtenerFormulario(String idFormulario) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Formulario> entity = new HttpEntity<Formulario>(headers);

		String formularioUrl = getServerEndpoint() + "/api/formulario/" + idFormulario;
		Formulario formulario = null;
		try {
			HttpEntity<Formulario> entityUsuario 
			    = restTemplate.exchange(formularioUrl,HttpMethod.GET,entity,Formulario.class);
			formulario = entityUsuario.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return formulario;
	}
	
	
	/** Método encargado de actualizar la información de un formulario. */
	public String createUpdateFormulario(String id, String nombre, String descripcion,String tipoMultimedia) {
		String respuesta = null;
		String url = getServerEndpoint() + "/api/formulario";

		Formulario formulario = new Formulario();
		if (id!=null) {
		    if (!id.equals("null")) {
			    formulario.setId(new Long(id));
		    }
		}
		formulario.setNombre(nombre);
		formulario.setTipoMultimedia(tipoMultimedia);
		formulario.setDescripcion(descripcion);

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", getTokenAcceso());
			HttpEntity<Formulario> entity = new HttpEntity<Formulario>(formulario, headers);

			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.postForObject(url, entity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(5L,"Un nuevo formulario dinamico con id: ?? ha sido creado",1); 
		}else { 
		    createUpdateAuditoria(5L,"El formulario dinamico con id: "+id+" ha sido actualizado",2);
		}		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		

		if (id!=null) {
		    if (id.equals("null")) {
			    return "Se ha agregado un nuevo Formulario";
		    } else {
			    return "El Formulario ha sido actualizado con exito";
		    }
		}else {
			return "Se ha agregado un nuevo Formulario";
		}
	}
	

	/** Método encargado de eliminar un formulario con base en un identificador */
	public String deleteFormulario(String idFormulario) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Formulario> entity = new HttpEntity<Formulario>(headers);

		String formularioUrl = getServerEndpoint() + "/api/formulario/" + idFormulario;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(formularioUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
        createUpdateAuditoria(5L,"El formulario dinamico con id: "+idFormulario+" ha sido eliminado",3); 
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuesta.getBody();
	}
	

	// _______________Administracion de Preguntas____________________________

	/**
	 * Método encargado de devolvernos un listado de las preguntas usadas en un
	 * formulario
	 */
	public List<Pregunta> obtenerPreguntasbyIdFormulario(String idFormulario) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Pregunta[]> listEntity = new HttpEntity<Pregunta[]>(headers);

		String listaPreguntasUrl = getServerEndpoint() + "/api/pregunta/porformulario/" + idFormulario;
		Pregunta[] listaPreguntas = null;
		try {
			HttpEntity<Pregunta[]> listEntityPregunta 
			    = restTemplate.exchange(listaPreguntasUrl,HttpMethod.GET,listEntity, Pregunta[].class);

			listaPreguntas = listEntityPregunta.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaPreguntas);
	}

	/** Método encargado de consultar una pregunta con base en un identificador */
	public Pregunta obtenerPregunta(String idPregunta) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Pregunta> entity = new HttpEntity<Pregunta>(headers);

		String preguntaUrl = getServerEndpoint() + "/api/pregunta/" + idPregunta;
		Pregunta pregunta = null;
		try {
			HttpEntity<Pregunta> entityPregunta 
			    = restTemplate.exchange(preguntaUrl, HttpMethod.GET, entity,Pregunta.class);
			pregunta = entityPregunta.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return pregunta;
	}

	/** Método encargado de actualizar la información de una pregunta */
	public String createUpdatePregunta(String id, String descripcion, String orden, String tipoPregunta, String idFormulario) {
		String url = getServerEndpoint() + "/api/pregunta/";

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("descripcion", descripcion);
		params.add("IdTipoPregunta", Long.valueOf(tipoPregunta));
		params.add("IdFormulario", Long.valueOf(idFormulario));
		params.add("orden", Integer.valueOf(orden));
		if (id!=null) {
		    if (!id.equals("null")) {
			    params.add("idPregunta", Long.valueOf(id));
		    }
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);

		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso"; 
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(6L,"Una nueva pregunta dinámica con id: ?? ha sido creada",1); 
		}else { 
		    createUpdateAuditoria(6L,"La pregunta dinámica con id: "+id+" ha sido actualizada",2);
		}		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		

		if (id!=null) {
		    if (id.equals("null")) {
			    return "Se ha agregado una nueva Pregunta";
		    } else {
			    return "La pregunta ha sido actualizada con exito";
		    }
		}else {
		    return "Se ha agregado una nueva Pregunta";
	    }
	}

	/** Método encargado de eliminar una Pregunta con base en un identificador */
	public String deletePregunta(String idPregunta) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Pregunta> entity = new HttpEntity<Pregunta>(headers);

		String preguntaUrl = getServerEndpoint() + "/api/pregunta/" + idPregunta;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(preguntaUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
        createUpdateAuditoria(6L,"La pregunta dinámica con id: "+idPregunta+" ha sido eliminada",3); 
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		return respuesta.getBody();
	}
	
	
	public List<Integer> obtenerOrdenesPreguntasByFormulario(String idFormulario) {		
		List<Pregunta> listPreguntas = obtenerPreguntasbyIdFormulario(idFormulario);
		List<Integer> listOrden = new ArrayList<Integer>();
		for (Pregunta preg : listPreguntas) {
			listOrden.add(Integer.parseInt(preg.getOrden()));
	    }
		return listOrden;
	}
	
	
	/**
	 * Método encargado de devolvernos un listado de las opciones de pregunta
	 */
	public List<OpcionPregunta> obtenerOpcionesbyIdPregunta(String idPregunta) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<OpcionPregunta[]> listEntity = new HttpEntity<OpcionPregunta[]>(headers);

		String listaOpcionesUrl = getServerEndpoint() + "/api/opcions-pregunta/" + idPregunta;
		OpcionPregunta[] listaOpciones = null;
		try {
			HttpEntity<OpcionPregunta[]> listEntityOpcion 
			    = restTemplate.exchange(listaOpcionesUrl, HttpMethod.GET,listEntity, OpcionPregunta[].class);
			listaOpciones = listEntityOpcion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaOpciones);
	}

	/**
	 * Método encargado de consultar una opcion de pregunta con base en un
	 * identificador
	 */
	public OpcionPregunta obtenerOpcion(String idOpcion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<OpcionPregunta> entity = new HttpEntity<OpcionPregunta>(headers);

		String opcionUrl = getServerEndpoint() + "/api/opcion-pregunta/" + idOpcion;
		OpcionPregunta opcion = null;
		try {
			HttpEntity<OpcionPregunta> entityOpcion 
			    = restTemplate.exchange(opcionUrl,HttpMethod.GET,entity,OpcionPregunta.class);
			opcion = entityOpcion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return opcion;
	}

	/** Método encargado de actualizar la información de una opción de pregunta. */
	public String createUpdateOpciones(String id, String clave, String valor, String idPregunta) {
		HttpEntity<String> respuesta = null;
		String url = null;
		try {
			RestTemplate restTemplate = new RestTemplate();

			String opcionesPregunta = null;

			opcionesPregunta = "{" + "\"clave\":\"" + clave + "\"," + "\"id\":" + id + "," + "\"preguntaId\":"
					+ idPregunta + "," + "\"valor\":\"" + valor + "\"}";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", getTokenAcceso());
			HttpEntity<String> entity = new HttpEntity<String>(opcionesPregunta, headers);
			url = getServerEndpoint() + "/api/opcion-pregunta";

			if (id != null) {
				respuesta = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
			} else {
				respuesta = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			}
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(7L,"Una nueva opción de pregunta con id: ?? ha sido creada",1); 
		}else { 
		    createUpdateAuditoria(7L,"La opción de pregunta con id: "+id+" ha sido actualizada",2);
		}		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuesta.getBody();
	}

	/** Método encargado de eliminar un Opcion de pregunta con base en un identificador */
	public String deleteOpcion(String idOpcion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<OpcionPregunta> entity = new HttpEntity<OpcionPregunta>(headers);

		String opcionUrl = getServerEndpoint() + "/api/opcion-pregunta" + idOpcion;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(opcionUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
        createUpdateAuditoria(7L,"La opción de pregunta con id: "+idOpcion+" ha sido eliminada",3); 
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuesta.getBody();
	}
	
	
	public List<String> obtenerListClavesVista(List<OpcionPregunta> ListOpciones) {
		List<String> ListClavesVista = new ArrayList<String>();
		for (OpcionPregunta opcion : ListOpciones) {
			ListClavesVista.add(opcion.getClave());
	    }
		return ListClavesVista;
	}

	// _______________Administracion de Mediciones___________________________

	/**
	 * Método encargado de devolvernos un listado de las mediciones correspondientes a una capa
	 */
	public List<Medicion> getMedicionesByIdCapa(String idCapa) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Medicion[]> listEntity = new HttpEntity<Medicion[]>(headers);

		String listaMedicionesUrl = getServerEndpoint() + "/api/medicion/capa/" + idCapa;
		Medicion[] listaMediciones = null;
		try {
			HttpEntity<Medicion[]> listEntityMedicion 
			    = restTemplate.exchange(listaMedicionesUrl,HttpMethod.GET,listEntity, Medicion[].class);
			listaMediciones = listEntityMedicion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}

		for (Medicion medicion : listaMediciones) { 
			medicion.setEscalaInicial(replaceComaByPeriod(medicion.getEscalaInicial()));
			medicion.setEscalaFinal(replaceComaByPeriod(medicion.getEscalaFinal()));
		}
		return Arrays.asList(listaMediciones);
	}
	

	/** Método encargado de consultar una medicion con base en un identificador */
	public Medicion obtenerDetalleMedicion(String idMedicion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Medicion> entity = new HttpEntity<Medicion>(headers);

		String capaResourceUrl = getServerEndpoint() + "/api/medicion/" + idMedicion;
		Medicion medicion = null;
		try {
			HttpEntity<Medicion> entityCapa 
			    = restTemplate.exchange(capaResourceUrl, HttpMethod.GET, entity,Medicion.class);
			medicion = entityCapa.getBody();

			medicion.setEscalaInicial(replaceComaByPeriod(medicion.getEscalaInicial()));
			medicion.setEscalaFinal(replaceComaByPeriod(medicion.getEscalaFinal()));
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return medicion;
	}
	
	
	public String replaceComaByPeriod(String escala) {
		Float escalaNum = Float.parseFloat(escala);
		DecimalFormat df = new DecimalFormat("00.00");
		df.setMaximumFractionDigits(2);
		return df.format(escalaNum).replace(",", ".");		
	}
	

	/**
	 * Método encargado de actualizar la información de una medicion.
	 * 
	 * @throws IOException
	 */
	public String createUpdateMedicion(String id, String nombre, String descripcion, String color, String significado,
			String recomendacion, String escalaInicial, String escalaFinal, String capa, MultipartFile icono)
			throws IOException {
		String url = getServerEndpoint() + "/api/medicion";

		MultipartInputStreamFileResource iconoFile = null;
		try {
			iconoFile = new MultipartInputStreamFileResource(icono.getInputStream(), icono.getOriginalFilename());
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
		}

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("icono", iconoFile);
		params.add("nombre", nombre);
		params.add("descripcion", descripcion);
		params.add("color", color);
		params.add("significado", significado);
		params.add("recomendacion", recomendacion);
		params.add("escalaInicial", Float.valueOf(escalaInicial));
		params.add("escalaFinal", Float.valueOf(escalaFinal));
		params.add("idCapa", Long.valueOf(capa));
		
		if (id != null) {
			params.add("idMedicion", Long.valueOf(id));
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		HttpEntity<String> respuesta = null;

		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (id == null) {
			createUpdateAuditoria(11L,"Una nueva medicion con id: ?? ha sido creada",1); 
		}else { 
		    createUpdateAuditoria(11L,"La medicion con id: "+id+" ha sido actualizada",2);
		}		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX		
		
		return respuesta.getBody();
	}
	
	
	/** Método encargado de eliminar una medicion con base en un identificador */
	public String deleteMedicion(String idMedicion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());
        HttpEntity<Medicion> entity = new HttpEntity<Medicion>(headers);

        String deleteMedicionUrl = getServerEndpoint() + "/api/medicion/" + idMedicion;		
		HttpEntity<String> respuesta = null;

		try {			
			respuesta = restTemplate.exchange(deleteMedicionUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
        createUpdateAuditoria(11L,"La capa con id: "+idMedicion+" ha sido eliminada",3); 
        //XXXXXXXXXXXXXXXXXXXXXXXXXXX AUDITORIA XXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		return respuesta.getBody();
	}
	
	/** Método encargado de validar si un rango de medicion se solapa con otro */
	public Boolean verificarSolapamiento(Long idMedicion, Long idCapa,String escalaInicial,String escalaFinal) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());
        HttpEntity<Medicion> entity = new HttpEntity<Medicion>(headers);

        String verificacionUrl = getServerEndpoint() + "api/medicion/verificarsolapamiento/" + idMedicion + "/" + idCapa +
        		                                       "/" + escalaInicial + "/" + escalaFinal;		
		HttpEntity<Boolean> respuesta = null;

		try {			
			respuesta = restTemplate.exchange(verificacionUrl,HttpMethod.GET,entity,Boolean.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return true;
		}
		return respuesta.getBody();
	}
	
	
	public List<String> obtenerListNombreMediciones() {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> listEntity = new HttpEntity<String>(headers);
		String nombresMediconesUrl = getServerEndpoint() + "/api/medicion/listaNombres";
		String[] listaNombres = null;
		try {
			HttpEntity<String[]> listEntityNombres 
			    = restTemplate.exchange(nombresMediconesUrl,HttpMethod.GET,listEntity,String[].class);
			listaNombres = listEntityNombres.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaNombres);
	}	
	
		
	// __________________Administracion de Autoridades competentes_________________________

	/**
	 * Método encargado de devolvernos un listado de los autoridades
	 */
	public List<AutoridadCompetente> obtenerAutoridades() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<AutoridadCompetente[]> listEntity = new HttpEntity<AutoridadCompetente[]>(headers);

		String listaAutoridadesUrl = getServerEndpoint() + "/api/autoridad";
		AutoridadCompetente[] listaAutoridades = null;
		try {
			HttpEntity<AutoridadCompetente[]> listEntityAutoridad 
			    = restTemplate.exchange(listaAutoridadesUrl,HttpMethod.GET,listEntity,AutoridadCompetente[].class);
			listaAutoridades = listEntityAutoridad.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaAutoridades);
	}
	

	/** Método encargado de consultar una autoridad con base en un identificador */
	public AutoridadCompetente obtenerAutoridad(String idAutoridad) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<AutoridadCompetente> entity = new HttpEntity<AutoridadCompetente>(headers);

		String autoridadUrl = getServerEndpoint() + "/api/autoridad/" + idAutoridad;
		AutoridadCompetente autoridad = null;
		try {
			HttpEntity<AutoridadCompetente> entityAutoridad 
			    = restTemplate.exchange(autoridadUrl,HttpMethod.GET,entity,AutoridadCompetente.class);
			autoridad = entityAutoridad.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return autoridad;
	}
	
	
    /** Método encargado de actualizar la información de una autoridad competente. */
	public String createUpdateAutoridad(String id, String nombre, String direccion, String telefono,
			                            String municipio, String correo, String horario) {
		
		AutoridadCompetente autoridad = new AutoridadCompetente();		
		String url = getServerEndpoint() + "/api/autoridad";
		
		if (id != null) {
			autoridad.setId(Long.valueOf(id));
		}
		autoridad.setNombre(nombre);
		autoridad.setDireccion(direccion);
		autoridad.setTelefono(telefono);
		autoridad.setMunicipio(municipio);
		autoridad.setCorreo(correo);
		autoridad.setHorario(horario);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<AutoridadCompetente> entity = new HttpEntity<AutoridadCompetente>(autoridad, headers);
				
		AutoridadCompetente respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.postForObject(url, entity, AutoridadCompetente.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
			
		if (id == null) {
			return "Se ha agregado una nueva autoridad";
		} else {
			return "La autoridad ha sido actualizada con exito";
		}
	}
	

	/** Método encargado de eliminar una autoridad con base en un identificador */
	public String deleteAutoridad(String idAutoridad) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<AutoridadCompetente> entity = new HttpEntity<AutoridadCompetente>(headers);

		String autoridadUrl = getServerEndpoint() + "/api/autoridad/" + idAutoridad;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(autoridadUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}
		

	// _____________Administracion de Mensajes_________________________________

	/**
	 * Método encargado de devolvernos un listado de los mensajes usados en los
	 * aplicativos
	 */
	public List<Mensaje> obtenerMensajes() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Mensaje[]> listEntity = new HttpEntity<Mensaje[]>(headers);

		String listaMensajesUrl = getServerEndpoint() + "/api/mensaje/";
		Mensaje[] listaMensajes = null;
		try {
			HttpEntity<Mensaje[]> listEntityMensaje 
			    = restTemplate.exchange(listaMensajesUrl,HttpMethod.GET,listEntity,Mensaje[].class);
			listaMensajes = listEntityMensaje.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaMensajes);
	}

	/** Método encargado de consultar un mensaje con base en un identificador */
	public Mensaje obtenerMensaje(String idMensaje) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Mensaje> entity = new HttpEntity<Mensaje>(headers);

		String mensajeUrl = getServerEndpoint() + "/api/mensaje/" + idMensaje;
		Mensaje mensaje = null;
		try {                          
			HttpEntity<Mensaje> entityAutoridad 
			    = restTemplate.exchange(mensajeUrl,HttpMethod.GET,entity,Mensaje.class);
			mensaje = entityAutoridad.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return mensaje;
	}
	
	/** Método encargado de actualizar la información de un mensaje.*/
	public String createUpdateMensaje(String idMensaje, String contenido, 
			                          String titulo, String nombreIdentificador,
			                          String uso, String idAplicacion) {
		String url = getServerEndpoint() + "/api/mensaje/";

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();		
		params.add("contenido",contenido);	
		params.add("titulo",titulo);
		params.add("nombreIdentificador",nombreIdentificador);
		params.add("idAplicacion",Long.valueOf(idAplicacion));
		params.add("uso",uso);		
		
		if ((idMensaje!=null) && (!idMensaje.equals("null"))) {
			params.add("idMensaje", Long.valueOf(idMensaje));
		}		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);

		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}

		return respuesta.getBody();
	}
	
	
	/** Método encargado de eliminar un mensaje con base en un identificador. */
	public String deleteMensaje(String idMensaje) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<AutoridadCompetente> entity = new HttpEntity<AutoridadCompetente>(headers);

		String autoridadUrl = getServerEndpoint() + "/api/mensaje/" + idMensaje;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(autoridadUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}

	/**
	 * Método encargado de realizar el envió de mensajes Push a los celulares
	 */
	public void enviarMensajePush(String recipients, String username, String title, String message) {
        
		// OneSignalAppID es un codigo de identificacion que se crea cuando
		// inscribo mi proyecto en OneSignal
        
		// RestApiKey es un codigo de autorizacion para que el proyecto pueda
		// usar los servicios web de OneSignal.
        
		// https://documentation.onesignal.com/reference#create-notification
		
		try {
			String strJsonBody = "";
			String jsonResponse;
			String destinatario = "Sin destinatario";
            
			URL url = new URL("https://onesignal.com/api/v1/notifications");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
            
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", enviro.getProperty("rest.api.key"));
			con.setRequestMethod("POST");
						
			if(username == null) {
			    strJsonBody = "{" +
	                "\"app_id\": \""+enviro.getProperty("onesignal.app.id")+"\"," +
	                "\"included_segments\": \""+recipients+"\"," +
	                "\"headings\": {\"en\": \""+title+"\"}," +
	                "\"contents\": {\"en\": \""+message+"\"} }";
			    
			    if(recipients.equals("All")) {
			        destinatario = "Todos los usuarios";
			    }else {
			    	destinatario = "Usuarios de " + recipients;
			    }
			}else {			
			    strJsonBody = "{" +
                    "\"app_id\": \""+enviro.getProperty("onesignal.app.id")+"\"," +
                    "\"filters\": [{\"field\": \"tag\", \"key\": \"Personal\", \"relation\": \"=\", \"value\": \""+username+"\"}]," +
                    "\"headings\": {\"en\": \""+title+"\"}," +
                    "\"contents\": {\"en\": \""+message+"\"} }";
			    
			    destinatario = username;
			}
									           
			System.out.println("strJsonBody:\n" + strJsonBody);
            
			byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			
			con.setFixedLengthStreamingMode(sendBytes.length);
            
			OutputStream outputStream = con.getOutputStream(); //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
			outputStream.write(sendBytes); //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

			int httpResponse = con.getResponseCode();
			System.out.println("httpResponse: " + httpResponse);

			if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
				Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
				createUpdateNotificacionPush(null,title,message,destinatario); //XXXXXXXXXXXXXXXXXXXXXXXXXXX
			} else {
				Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
			}
			System.out.println("jsonResponse:\n" + jsonResponse);
		} catch (Throwable t) {
			System.out.println("Error >>> " + t);
			t.printStackTrace();
		}
	}
	
	
	// _____________________Administrar Avistamientos__________________________________________	


	public List<Avistamiento> obtenerAvistamientosPaginadosPorFechaCapa(String idCapa, String idCategoria, String idApp,
		String fechaInicio, String fechaFin, String whereClause, String orderClause, String pageSize, 
		String pageNumber, String estadosList, boolean soloComHis, boolean conComenDeHis) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Avistamiento[]> listEntity = new HttpEntity<Avistamiento[]>(headers); 
		
		String listaReportesUrl = getServerEndpoint() + "/api/avistamiento/paginated/capa-fecha?idCapa=" + idCapa +
				                                        "&fechaDesde=" + fechaInicio + 
				                                        "&fechaHasta=" + fechaFin + 
		                                                "&whereClause=" + whereClause +
		                                                "&orderClause=" + orderClause +
		                                                "&pageSize=" + pageSize +
		                                                "&pageStart=" + pageNumber;
		
		if(!idCategoria.equals("0")) {   listaReportesUrl = listaReportesUrl + "&idCategoria=" + idCategoria;   }
		if(!estadosList.equals(""))  {   listaReportesUrl = listaReportesUrl + "&estadosList=" + estadosList;   }
		if(soloComHis)  {   listaReportesUrl = listaReportesUrl + "&soloComHis=1";   }
		if(conComenDeHis)  {   listaReportesUrl = listaReportesUrl + "&conComenDeHis=1";   }
		
		Avistamiento[] listaAvistamientos = null;
		try {
			HttpEntity<Avistamiento[]> listEntityReporte 
			    = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,Avistamiento[].class);
			listaAvistamientos = listEntityReporte.getBody();
			
			String numColorApp = obtenerDetalleAplicacion(idApp).getCodigoColor().replace("#","");
			for (Avistamiento avistamiento : listaAvistamientos) {
				avistamiento = asignarAtributosByEstadoPaginado(avistamiento);
				avistamiento.setBotonVisualizar("" +					
						"<img src='assets/imagesAdminAvist/visualizar.png' " +
							"style='height: 35px;margin-bottom: -25px; margin-top: -25px;cursor: pointer' " +
	                    	"onclick='visualizarAvistamiento(" + avistamiento.getId() + ",\"" + numColorApp + "\");'>" +
						"</img>" );
				
				if(avistamiento.getTipoEspecie()==null) {
					avistamiento.setTipoEspecie("Sin Especie");
				}
				if(avistamiento.getNombreComun()==null) {
					avistamiento.setNombreComun(" ");
				}
				if(avistamiento.getDescripcion()==null) {
					avistamiento.setDescripcion(" ");
				}
			}
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}				
		return Arrays.asList(listaAvistamientos);
	}	
	

	public Avistamiento asignarAtributosByEstadoPaginado(Avistamiento avistamiento) {
		String id = avistamiento.getId().toString();
		if(("2").equals(avistamiento.getEstadoPublicacion())) {
			avistamiento.setNombreEstado(
		        "<span id='estadoAvist_"+id+"' style='color:#B2B2B2; font-weight: 900;'>Pendiente</span>");
		}else {
			if(("0").equals(avistamiento.getEstadoPublicacion())) {
				avistamiento.setNombreEstado(
				    "<span id='estadoAvist_"+id+"' style='color:#B22D2C; font-weight: 900;'>Rechazado</span>");
			}else {
				if(("1").equals(avistamiento.getEstadoPublicacion())) {
					avistamiento.setNombreEstado(
					    "<span id='estadoAvist_"+id+"' style='color:#559839; font-weight: 900;'>Aprobado</span>");
				}	
			}
		}
		return avistamiento;
	}	
	

	public int cantidadAvistamientosPaginadosPorFechaCapa(String idCapa, String idCategoria,String fechaInicio, String fechaFin, 
			                                              String estadosList, boolean soloComHis, boolean conComenDeHis) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Integer> listEntity = new HttpEntity<Integer>(headers);
		
		String listaReportesUrl = getServerEndpoint() + "/api/avistamiento/cantidad/capa-fecha?idCapa=" + idCapa +
				                                        "&fechaDesde=" + fechaInicio + 
				                                        "&fechaHasta=" + fechaFin;
		
		if(!idCategoria.equals("0")) {   listaReportesUrl = listaReportesUrl + "&idCategoria=" + idCategoria;   }
		if(!estadosList.equals(""))  {   listaReportesUrl = listaReportesUrl + "&estadosList=" + estadosList;   }
		if(soloComHis)  {   listaReportesUrl = listaReportesUrl + "&soloComHis=1";   }
		if(conComenDeHis)  {   listaReportesUrl = listaReportesUrl + "&conComenDeHis=1";   }
		
		Integer cantidadAvistamientos = null;
		try {
			HttpEntity<Integer> listEntityReporte 
			    = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,Integer.class);
			cantidadAvistamientos = listEntityReporte.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return 0;
		}				
		return cantidadAvistamientos;
	}
		
		
	public int cantidadAvisFiltradosPaginadosPorFechaCapa(String idCapa, String idCategoria,String fechaInicio, String fechaFin, 
			                                              String whereClause, String estadosList, 
			                                              boolean soloComHis, boolean conComenDeHis) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Integer> listEntity = new HttpEntity<Integer>(headers);
		
		String listaReportesUrl = getServerEndpoint() + "/api/avistamiento/filtered/cantidad/capa-fecha?idCapa=" + idCapa +
				                                        "&fechaDesde=" + fechaInicio + 
				                                        "&fechaHasta=" + fechaFin +
				                                        "&whereClause=" + whereClause;
		
		if(!idCategoria.equals("0")) {   listaReportesUrl = listaReportesUrl + "&idCategoria=" + idCategoria;   }
		if(!estadosList.equals(""))  {   listaReportesUrl = listaReportesUrl + "&estadosList=" + estadosList;   }
		if(soloComHis)  {   listaReportesUrl = listaReportesUrl + "&soloComHis=1";   }
		if(conComenDeHis)  {   listaReportesUrl = listaReportesUrl + "&conComenDeHis=1";   }
		
		Integer cantidadAvistamientos = null;
		try {
			HttpEntity<Integer> listEntityReporte 
			    = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,Integer.class);
			cantidadAvistamientos = listEntityReporte.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return 0;
		}				
		return cantidadAvistamientos;
	}	
	
	
	/** Método encargado de retornarnos un listado de avistamientos con base en un id de usuario. */
	public List<Avistamiento> obtenerAvistamientosbyIdUsuario(String idUsuario) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Avistamiento[]> listEntity = new HttpEntity<Avistamiento[]>(headers);

		String listaReportesUrl = getServerEndpoint() + "/api/avistamiento/usuario/" + idUsuario;
		Avistamiento[] listaAvistamientos = null;
		try {
			HttpEntity<Avistamiento[]> listEntityReporte 
			    = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,Avistamiento[].class);
			listaAvistamientos = listEntityReporte.getBody();	
			
			for (Avistamiento avistamiento : listaAvistamientos) {
				avistamiento = asignarAtributosByEstado(avistamiento);
			}
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}				
		return Arrays.asList(listaAvistamientos);
	}
	
	
	/** Método encargado de consultar un avistamiento con base en un identificador */
	public Avistamiento obtenerAvistamiento(String idAvistamiento) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Avistamiento> entity = new HttpEntity<Avistamiento>(headers);

		String avistamientoUrl = getServerEndpoint() + "/api/avistamiento/find?idAvistamiento=" + idAvistamiento;
		Avistamiento avistamiento = null;
		try {
			HttpEntity<Avistamiento> entityAvistamiento 
			    = restTemplate.exchange(avistamientoUrl,HttpMethod.GET,entity,Avistamiento.class);
			avistamiento = entityAvistamiento.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		avistamiento = asignarAtributosByEstado(avistamiento);	
		return avistamiento;
	}
	
	/** Método encargado de retornarnos un listado de avistamientos con base en un estado. */
	public List<Avistamiento> obtenerAvistamientosbyEstado(String estado) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Avistamiento[]> listEntity = new HttpEntity<Avistamiento[]>(headers);

		String listaReportesUrl = getServerEndpoint() + "/api/avistamiento/estado/" + estado;
		Avistamiento[] listaAvistamientos = null;
		try {
			HttpEntity<Avistamiento[]> listEntityReporte 
			    = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,Avistamiento[].class);
			listaAvistamientos = listEntityReporte.getBody();	
			
			for (Avistamiento avistamiento : listaAvistamientos) {
				avistamiento = asignarAtributosByEstado(avistamiento);
			}
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}				
		return Arrays.asList(listaAvistamientos);
	}
	
	
	/** Método encargado de colocar a un avistamiento el color y el nombre de su respectivo estado */
	public Avistamiento asignarAtributosByEstado(Avistamiento avistamiento) {
		if(avistamiento.getEstadoPublicacion().equals("2")) {
			avistamiento.setColorEstado("#B2B2B2");	
			avistamiento.setNombreEstado("Pendiente");
		}else {
			if(avistamiento.getEstadoPublicacion().equals("0")) {
				avistamiento.setColorEstado("#B22D2C");
				avistamiento.setNombreEstado("Rechazado");
			}else {
				if(avistamiento.getEstadoPublicacion().equals("1")) {
					avistamiento.setColorEstado("#559839");
					avistamiento.setNombreEstado("Aprobado");
				}	
			}
		}
		return avistamiento;
	}
	
	
	/** Método encargado de retornarnos la cantidad de avistamientos con un estado. */
	/*public int cantidadAvistamientosByEstado(String estado) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Avistamiento[]> listEntity = new HttpEntity<Avistamiento[]>(headers);

		String cantidadUrl = getServerEndpoint() + "/api/avistamiento/cantidad/estado/" + estado;
		Integer cantidad = null;
		try {
			HttpEntity<Integer> entityCantidad = restTemplate.exchange(cantidadUrl,HttpMethod.GET,listEntity,Integer.class);
			cantidad = entityCantidad.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return 0;
		}
		return cantidad;		
	}*/	
	
	
	/** Método encargado de modificar los datos de un avistamiento */
	public String actualizarAvistamiento(String idAvistamiento, String nombreComun, String descripcion, 
			                             String nombreCientifico, String tipoEspecie) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);   
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		String avistamientoUrl = getServerEndpoint() + "/api/avistamiento?idAvistamiento="+idAvistamiento+
		                                               "&nombreComun="+nombreComun;
		
		if((!descripcion.equals("")) && (descripcion!=null)) {
			avistamientoUrl = avistamientoUrl +"&descripcion="+descripcion;
		}
		if((!nombreCientifico.equals("0")) && (nombreCientifico!=null)) {
			avistamientoUrl = avistamientoUrl + "&nombreCientifico=" + nombreCientifico;
		}		
		if((!tipoEspecie.equals("0")) && (tipoEspecie!=null)) {
			avistamientoUrl = avistamientoUrl + "&tipoEspecie=" + tipoEspecie;
		}
		
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(avistamientoUrl,HttpMethod.PUT,requestEntity,String.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}   				
		return respuesta.getBody();
	}
	
	
	/** Método encargado de cambiar el estado de un avistamiento */
	public String cambiarEstadoAvistamiento(String idAvistamiento, String estado) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		String avistamientoUrl = getServerEndpoint() + "/api/avistamiento/estado?id="+idAvistamiento+"&estado="+estado;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(avistamientoUrl,HttpMethod.PUT,requestEntity,String.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		return respuesta.getBody();
	}
		
	/** Método encargado de retornarnos las cantidades de los avistamientos correspondientes a un tipo avistamiento */
	public EstadisticaAvistamiento getCantidadesAvistamientoEstadosbyFechaCapa(String capa, String categoria,String fechaIni,
			                                                       String fechaFin,boolean soloComHis,boolean conComenDeHis){
		EstadisticaAvistamiento estadisticaAvistamiento = new EstadisticaAvistamiento();
		int cantAprobado = getCantidadAvistamientoByEstado(fechaIni,fechaFin,capa,categoria,"1",soloComHis,conComenDeHis);
		estadisticaAvistamiento.setCantidadAprobados(cantAprobado);
		int cantPendiente = getCantidadAvistamientoByEstado(fechaIni,fechaFin,capa,categoria,"2",soloComHis,conComenDeHis);
		estadisticaAvistamiento.setCantidadPendientes(cantPendiente);
		int cantRechazado = getCantidadAvistamientoByEstado(fechaIni,fechaFin,capa,categoria,"0",soloComHis,conComenDeHis);
		estadisticaAvistamiento.setCantidadRechazados(cantRechazado);		
		return estadisticaAvistamiento;		
	}
	
	
	/** Método encargado de cambiar el estado de un avistamiento */
	public int getCantidadAvistamientoByEstado(String fechaIni,String fechaFin, String capa, 
			                                   String categoria, String estado, boolean soloComHis,boolean conComenDeHis) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(headers);

		String avistamientoUrl = getServerEndpoint() + "/api/avistamiento/cantidad/parametros?"
				                                     + "fechaDesde=" + fechaIni + "&fechaHasta=" + fechaFin;
		
		if(capa!=null) {  avistamientoUrl = avistamientoUrl +"&tipoAvistamiento="+capa;  }
		if((categoria!=null) && (!categoria.equals("0"))) {  avistamientoUrl = avistamientoUrl +"&idCategoria="+categoria;  }
		if(estado!=null) {  avistamientoUrl = avistamientoUrl +"&estado="+estado;  }	
		if(soloComHis)  {   avistamientoUrl = avistamientoUrl + "&soloComHis=1";   }
		if(conComenDeHis)  {   avistamientoUrl = avistamientoUrl + "&conComenDeHis=1";   }
		
		HttpEntity<Integer> respuesta = null;
		try {
			respuesta = restTemplate.exchange(avistamientoUrl,HttpMethod.GET,requestEntity,Integer.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return 0;
		}
		return respuesta.getBody();
	}	
	
	
	/** Método encargado de retornar la cantidad total de avistamientos en los graficos estadisticos */
	public int getTotalAvistamientoEstadisticas(String fechaInicio,String fechaFin,
			                                    String filtroCapa,String filtroCateg, boolean soloPendientes) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(headers);

		String avistamientoUrl = getServerEndpoint() + "/api/avistamiento/report/totalAvistamientos/date?"
				                                     + "fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin;				
		if(filtroCapa!=null) {
		    avistamientoUrl = avistamientoUrl +"&filtroCapa="+filtroCapa;
		}
		if(filtroCateg!=null) {
		    avistamientoUrl = avistamientoUrl +"&filtroCateg="+filtroCateg;
		}
		if(soloPendientes) {
		    avistamientoUrl = avistamientoUrl +"&soloPendientes=1";
		}
		
		HttpEntity<Integer> respuesta = null;
		try {
			respuesta = restTemplate.exchange(avistamientoUrl,HttpMethod.GET,requestEntity,Integer.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return 0;
		}
		return respuesta.getBody();
	}		
	
	
	/* Método encargado de obtener el listado de estadisticas por usuario   */
	public List<EstadisticaReporteAvist> getEstadisticasAvistUsuario(String fechaIni,String fechaFin,
			                                                         String filtroCapa,String filtroCateg) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<EstadisticaReporteAvist[]> listEntity = new HttpEntity<EstadisticaReporteAvist[]>(headers);

		String estadisticaUrl = getServerEndpoint() + "/api/avistamiento/report/usuario/rank/date?"
                                                    + "fechaInicio=" + fechaIni + "&fechaFin=" + fechaFin
                                                    + "&filtroCapa=" + filtroCapa + "&filtroCateg=" + filtroCateg;
		EstadisticaReporteAvist[] listaEstadisticas = null;
		try {
			HttpEntity<EstadisticaReporteAvist[]> listEntityReporte
			    = restTemplate.exchange(estadisticaUrl,HttpMethod.GET,listEntity,EstadisticaReporteAvist[].class);
			listaEstadisticas = listEntityReporte.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		return Arrays.asList(listaEstadisticas);
	}	
	
	
	/* Método encargado de obtener el listado de estadisticas por municipio   */
	public List<EstadisticaReporteAvist> getEstadisticasAvistMunicipio(String fechaIni,String fechaFin,
			                                                           String filtroCapa,String filtroCateg) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<EstadisticaReporteAvist[]> listEntity = new HttpEntity<EstadisticaReporteAvist[]>(headers);

		String estadisticaUrl = getServerEndpoint() + "/api/avistamiento/report/municipio/rank/date?"
                                                    + "fechaInicio=" + fechaIni + "&fechaFin=" + fechaFin
		                                            + "&filtroCapa=" + filtroCapa + "&filtroCateg=" + filtroCateg;
		EstadisticaReporteAvist[] listaEstadisticas = null;
		try {
			HttpEntity<EstadisticaReporteAvist[]> listEntityReporte
			    = restTemplate.exchange(estadisticaUrl,HttpMethod.GET,listEntity,EstadisticaReporteAvist[].class);
			listaEstadisticas = listEntityReporte.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}					
		return Arrays.asList(listaEstadisticas);
	}
	
	
	public String getFiltroPorCapasEstadisticas(List<CapaSubmenu> listCapas) {	
		String filtroCapa = "";
		for (CapaSubmenu capa : listCapas) { 
			if(capa.getId()!=211) {
			    filtroCapa = filtroCapa + capa.getId() + ",";
			}
		}
		return 	filtroCapa + "0";	
	}
		
	public String getFiltroPorCategEstadisticas(List<CapaSubmenu> listCapas) {	
		String filtroCategorias = "";
		for (CapaSubmenu capa : listCapas) { 
			if(capa.getId()!=211) {
			    for (CategoriaSubmenu cate : capa.getCategorias()) { 
			        filtroCategorias = filtroCategorias + cate.getIdCategoria() + ",";
			    }
			}
		}
		return 	filtroCategorias + "0";	
	}
	
		
	/** Método encargado de retornarnos un listado de comentarios con base en un id de Avistamiento. */
	public List<ComentarioAvist> obtenerComentariosbyIdAvistamiento(String idAvistamiento) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ComentarioAvist[]> listEntity = new HttpEntity<ComentarioAvist[]>(headers);

		String ComentariosUrl = getServerEndpoint() + "/api/avistamiento/comentario/list/" + idAvistamiento;
		ComentarioAvist[] listaComentarios = null;
		try {
			HttpEntity<ComentarioAvist[]> listEntityReporte
			    = restTemplate.exchange(ComentariosUrl,HttpMethod.GET,listEntity,ComentarioAvist[].class);
			listaComentarios = listEntityReporte.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}				
		
		for (ComentarioAvist comentario : listaComentarios) {
			if(comentario.getEstadoPublicacion() == 2) {
				comentario.setColorEstado("#B2B2B2");	
				comentario.setNombreEstado("PENDIENTE");
			}else {
				if(comentario.getEstadoPublicacion() == 0) {
					comentario.setColorEstado("#B22D2C");
					comentario.setNombreEstado("RECHAZADO");
				}else {
					if(comentario.getEstadoPublicacion() == 1) {
						comentario.setColorEstado("#559839");
						comentario.setNombreEstado("APROBADO");
					}	
				}
			}
		}		
		return Arrays.asList(listaComentarios);
	}	
		
	/** Método encargado de cambiar el estado de un comentario de un avistamiento */
	public String cambiarEstadoComenAvistamiento(String idComentario, String estado) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(headers);

		String comentarioUrl = getServerEndpoint() + "/api/avistamiento/comentario/estado?id="+idComentario+"&estado="+estado;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(comentarioUrl,HttpMethod.PUT,requestEntity,String.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return respuesta.getBody();
	}
	
	/**
	 * Método encargado de crear y actualizar comentarios
	 */
	public String createUpdateComentario(String id, String descripcion, String estado, String idUsuario, String idAvist) {
								
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
				
		String comentario = "{" + "\"id\":" + id + "," + "\"descripcion\":\"" + descripcion + "\","
				                + "\"fechaPublicacion\":\"" + format.format(date) + "\","
	                            + "\"estado\":" + estado + "," + "\"idUsuario\":" + idUsuario + "}";
		HttpEntity<String> respuesta = null;
		String respuestafINAL = null;
		try {
			RestTemplate restTemplate = new RestTemplate();			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", getTokenAcceso());
			HttpEntity<String> entity = new HttpEntity<String>(comentario, headers);
			String url = getServerEndpoint() + "/api/comentario/" + idAvist;
			
			if (id == null) {
				respuesta = restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
			} else {				
				respuesta = restTemplate.exchange(url,HttpMethod.PUT,entity,String.class);
			}
			respuestafINAL = respuesta.getBody();
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			respuestafINAL = "Error en el Proceso";
		}
		return respuestafINAL;
	}
	
	
	// _____________________Administrar Historias__________________________________________	

	/** Método encargado de retornarnos un listado de avistamientos con base en un id de usuario. */
	public List<Historia> obtenerHistoriasbyIdAvistamiento(String idAvistamiento) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Historia[]> listEntity = new HttpEntity<Historia[]>(headers);

		String listaHistoriasUrl = getServerEndpoint() + "/api/avistamiento/historia/list/" + idAvistamiento;
		Historia[] listaHistorias = null;
		try {
			HttpEntity<Historia[]> listEntityHistoria 
			    = restTemplate.exchange(listaHistoriasUrl,HttpMethod.GET,listEntity,Historia[].class);
			listaHistorias = listEntityHistoria.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		
		for (Historia historia : listaHistorias) {
			if(historia.getEstadoPublicacion() == 2) {
				historia.setColorEstado("#B2B2B2");	
				historia.setNombreEstado("PENDIENTE");
			}else {
				if(historia.getEstadoPublicacion() == 0) {
					historia.setColorEstado("#B22D2C");
					historia.setNombreEstado("RECHAZADO");
				}else {
					if(historia.getEstadoPublicacion() == 1) {
						historia.setColorEstado("#559839");
						historia.setNombreEstado("APROBADO");
					}	
				}
			}
		}	
		return Arrays.asList(listaHistorias);
	}
		
	/** Método encargado de cambiar el estado de una historia */
	public String cambiarEstadoHistoria(String idHistoria, String estado) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(headers);

		String historiaUrl = getServerEndpoint() + "/api/avistamiento/historia/estado?id="+idHistoria+"&estado="+estado;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(historiaUrl,HttpMethod.PUT,requestEntity,String.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return respuesta.getBody();
	}
		
	/** Método encargado de retornarnos un listado de comentarios con base en un id de historia. */
	public List<ComentarioHisto> obtenerComentariosbyIdHistoria(String idHistoria) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ComentarioHisto[]> listEntity = new HttpEntity<ComentarioHisto[]>(headers);

		String listaComentariosUrl = getServerEndpoint() + "/api/avistamiento/historia/comentario/list/" + idHistoria;
		ComentarioHisto[] listaComentarios = null;
		try {
			HttpEntity<ComentarioHisto[]> listEntityComentario 
			    = restTemplate.exchange(listaComentariosUrl,HttpMethod.GET,listEntity,ComentarioHisto[].class);
			listaComentarios = listEntityComentario.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		
		for (ComentarioHisto comentario : listaComentarios) {
			if(comentario.getEstadoPublicacion() == 2) {
				comentario.setColorEstado("#B2B2B2");
				comentario.setNombreEstado("PENDIENTE");
			}else {
				if(comentario.getEstadoPublicacion() == 0) {
					comentario.setColorEstado("#B22D2C");
					comentario.setNombreEstado("RECHAZADO");
				}else {
					if(comentario.getEstadoPublicacion() == 1) {
						comentario.setColorEstado("#559839");
						comentario.setNombreEstado("APROBADO");
					}	
				}
			}
		}	
		return Arrays.asList(listaComentarios);
	}	
	
	/** Método encargado de cambiar el estado de un comentario de una historia */
	public String cambiarEstadoComenHistoria(String idComentario, String estado) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(headers);

		String comentarioUrl = getServerEndpoint() + "/api/avistamiento/historia/comentario/estado?id="+idComentario+"&estado="+estado;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(comentarioUrl,HttpMethod.PUT,requestEntity,String.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return respuesta.getBody();
	}
	

	public MarkerPrintPackage obtenerMardadoresListAvistamientos(List<Avistamiento> listAvistamientos) {		
		if(listAvistamientos!=null) {
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", getTokenAcceso());
		    MarcadorAvist markerInfo = null;
		    String marcResourceUrl = "";
		    
		    MarkerPrintPackage paqueteImpresion = new MarkerPrintPackage();
		    List<MarkerPoint> listaMarcadores = new ArrayList<MarkerPoint>();				    
		
		    for (Avistamiento avistamiento : listAvistamientos) {
		    	MarkerPoint markerPoint = new MarkerPoint();
		    	HttpEntity<MarkerInfo> entityMar = new HttpEntity<MarkerInfo>(headers);
		    	marcResourceUrl = getServerEndpoint() + "/api/avistamiento/detalle?idAvistamiento=" + avistamiento.getId();						
		    	try {
		    		HttpEntity<MarcadorAvist> entityMarc 
		    	        = restTemplate.exchange(marcResourceUrl,HttpMethod.GET,entityMar,MarcadorAvist.class);
		    		markerInfo = entityMarc.getBody();		    			
		    		markerPoint.setNombre(avistamiento.getNombreComun());      
		    		markerPoint.setDescripcion(avistamiento.getDescripcion());
		    		markerPoint.setNombreCientifico(avistamiento.getNombreCientifico());
		    		markerPoint.setRutaWebIcono(markerInfo.getImageAvistamiento().getRutaLogo());   
		    		markerPoint.setPoint(markerInfo.getPoint());
		    		markerPoint.setUrlImagen(avistamiento.getRutaMultimedia());
		    		listaMarcadores.add(markerPoint);
		    	} catch (Exception ex) {
		    		System.out.print("Error: " + ex);
		    	}				
		    }		    

	    	paqueteImpresion.setListaMarcadores(listaMarcadores);
		    return paqueteImpresion;		
		}else {
			return null;
	    }
	}		

		
	// _____________________Administrar Vigias__________________________________________	
	
	
	public List<ReporteVigia> obtenerVigiasPaginadosPorParametros(String idCapa, String idCategoria, String idApp,
			String fechaInicio, String fechaFin, String whereClause, String orderClause, String pageSize, 
			String pageNumber, String estadosList, boolean comenPen) {
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", getTokenAcceso());
			HttpEntity<ReporteVigia[]> listEntity = new HttpEntity<ReporteVigia[]>(headers); 
			
			String listaReportesUrl = getServerEndpoint() + "/api/vigia/paginated/parametros?idCapa=" + idCapa +
					                                        "&fechaDesde=" + fechaInicio + 
					                                        "&fechaHasta=" + fechaFin + 
			                                                "&whereClause=" + whereClause +
			                                                "&orderClause=" + orderClause +
			                                                "&pageSize=" + pageSize +
			                                                "&pageStart=" + pageNumber;
			
			if(!idCategoria.equals("0")) {   listaReportesUrl = listaReportesUrl + "&idCategoria=" + idCategoria;   }
			if(!estadosList.equals(""))  {   listaReportesUrl = listaReportesUrl + "&estadosList=" + estadosList;   }
			if(comenPen)  {   listaReportesUrl = listaReportesUrl + "&comenPen=1";   }
			
			ReporteVigia[] listaVigias = null;
			try {
				HttpEntity<ReporteVigia[]> listEntityReporte 
				    = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,ReporteVigia[].class);
				listaVigias = listEntityReporte.getBody();
				
				String numColorApp = obtenerDetalleAplicacion(idApp).getCodigoColor().replace("#","");
				for (ReporteVigia vigia : listaVigias) {
					vigia = asignarAtributosVigiaByEstadoPaginado(vigia);
					vigia.setBotonVisualizar("" +					
							"<img src='assets/imagesAdminVigia/visualizar.png' " +
								"style='height: 35px;margin-bottom: -25px; margin-top: -25px;cursor: pointer' " +
		                    	"onclick='visualizarVigia(" + vigia.getId() + ",\"" + numColorApp + "\");'>" +
							"</img>" );
					
					if(vigia.getDescripcion()==null) {
						vigia.setDescripcion(" ");
					}
				}
			} catch (Exception ex) {
				System.out.print("Error: " + ex);
				return null;
			}				
			return Arrays.asList(listaVigias);
	}
	
	
	public ReporteVigia asignarAtributosVigiaByEstadoPaginado(ReporteVigia vigia) {
		String id = vigia.getId().toString();
		if(("PENDIENTE").equals(vigia.getEstado())) {
			vigia.setNombreEstado(
		        "<span id='estadoVigia_"+id+"' style='color:#B2B2B2; font-weight: 900;'>Pendiente</span>");
		}else {
			if(("RECHAZADO").equals(vigia.getEstado())) {
				vigia.setNombreEstado(
				    "<span id='estadoVigia_"+id+"' style='color:#B22D2C; font-weight: 900;'>Rechazado</span>");
			}else {
				if(("APROBADO").equals(vigia.getEstado())) {
					vigia.setNombreEstado(
					    "<span id='estadoVigia_"+id+"' style='color:#559839; font-weight: 900;'>Aprobado</span>");
				}	
			}
		}
		return vigia;
	}	
	
	
	public MarkerPrintPackage obtenerMardadoresListVigias(List<ReporteVigia> listVigia) {		
		if(listVigia!=null) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", getTokenAcceso());		    
		    MarkerPrintPackage paqueteImpresion = new MarkerPrintPackage();
		    List<MarkerPoint> listaMarcadores = new ArrayList<MarkerPoint>();				    
		
		    for (ReporteVigia vigia : listVigia) {
		    	MarkerPoint markerPoint = new MarkerPoint();  
		    	markerPoint.setDescripcion(vigia.getDescripcion());
		    	markerPoint.setRutaWebIcono(vigia.getRutaIcono());   
		    	markerPoint.getPoint().setLat(Double.parseDouble(vigia.getLatitud()));
		    	markerPoint.getPoint().setLng(Double.parseDouble(vigia.getLongitud()));
		    	markerPoint.setUrlImagen(vigia.getRutaMultimedia());
		    	listaMarcadores.add(markerPoint);			
		    }		    
	    	paqueteImpresion.setListaMarcadores(listaMarcadores);
		    return paqueteImpresion;		
		}else {
			return null;
	    }
	}	
	
	

	public int cantidadVigiasPaginatedPorParametros(String idCapa,String idCategoria,String estadosList,
			String fechaInicio, String fechaFin,boolean comenPen) {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Integer> listEntity = new HttpEntity<Integer>(headers);
		
		String listaReportesUrl = getServerEndpoint() + "/api/vigia/paginated/cantidad/parametros?idCapa=" + idCapa +
				                                        "&fechaDesde=" + fechaInicio + 
				                                        "&fechaHasta=" + fechaFin;
		
		if(!idCategoria.equals("0")) {   listaReportesUrl = listaReportesUrl + "&idCategoria=" + idCategoria;   }
		if(!estadosList.equals(""))  {   listaReportesUrl = listaReportesUrl + "&estadosList=" + estadosList;   }
		if(comenPen)  {   listaReportesUrl = listaReportesUrl + "&comenPen=1";   }
		
		Integer cantidadVigias = null;
		try {
			HttpEntity<Integer> listEntityReporte 
			    = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,Integer.class);
			cantidadVigias = listEntityReporte.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return 0;
		}				
		return cantidadVigias;
	}
	
	
	public int cantidadVigiasPaginatedFiltradosPorParametros(String idCapa, String idCategoria,String fechaInicio, String fechaFin,
			String whereClause, String estadosList,boolean comenPen) {
		
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", getTokenAcceso());
	    HttpEntity<Integer> listEntity = new HttpEntity<Integer>(headers);
	
	    String listaReportesUrl = getServerEndpoint() + "/api/vigia/paginated/cantidad/filtered/parametros?idCapa=" + idCapa +
			                                            "&fechaDesde=" + fechaInicio + 
			                                            "&fechaHasta=" + fechaFin +
			                                            "&whereClause=" + whereClause;
	    
	    if(!idCategoria.equals("0")) {   listaReportesUrl = listaReportesUrl + "&idCategoria=" + idCategoria;   }
	    if(!estadosList.equals(""))  {   listaReportesUrl = listaReportesUrl + "&estadosList=" + estadosList;   }
	    if(comenPen)  {   listaReportesUrl = listaReportesUrl + "&comenPen=1";   }
	
	    Integer cantidadAvistamientos = null;
	    try {
	        HttpEntity<Integer> listEntityReporte 
	            = restTemplate.exchange(listaReportesUrl,HttpMethod.GET,listEntity,Integer.class);
	        cantidadAvistamientos = listEntityReporte.getBody();
	    } catch (Exception ex) {
	    	System.out.print("Error: " + ex);
	        return 0;
	    }				
	    return cantidadAvistamientos;
    }	
	
	
	
	

	/** Método encargado de retornarnos las cantidades de los reportes de vigia corresponidntes a un filtro*/
	public EstadisticaVigia getCantidadesVigiaEstadosbyFechaCapa(String capa, String categoria,String fechaIni,
			                                                     String fechaFin,boolean soloCom){
		EstadisticaVigia estadisticaVigia = new EstadisticaVigia();
		int cantAprobado = getCantidadVigiaByParametros(fechaIni,fechaFin,capa,categoria,"APROBADO",soloCom);
		estadisticaVigia.setCantidadAprobados(cantAprobado);
		int cantPendiente = getCantidadVigiaByParametros(fechaIni,fechaFin,capa,categoria,"PENDIENTE",soloCom);
		estadisticaVigia.setCantidadPendientes(cantPendiente);
		int cantRechazado = getCantidadVigiaByParametros(fechaIni,fechaFin,capa,categoria,"RECHAZADO",soloCom);
		estadisticaVigia.setCantidadRechazados(cantRechazado);		
		return estadisticaVigia;		
	}
	
	
	public int getCantidadVigiaByParametros(String fechaIni,String fechaFin, String capa, 
			                            String categoria, String estado, boolean comenPen) {				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(headers);

		String vigiaUrl = getServerEndpoint() + "/api/vigia/cantidad/parametros?"
				                              + "fechaInicio=" + fechaIni + "&fechaFin=" + fechaFin;
		
		if(capa!=null) {  vigiaUrl = vigiaUrl +"&idCapa="+capa;  }
		if((categoria!=null) && (!categoria.equals("0"))) {  vigiaUrl = vigiaUrl +"&idCategoria="+categoria;  }
		if(estado!=null) {  vigiaUrl= vigiaUrl +"&estado="+estado;  }	
		if(comenPen)  {   vigiaUrl = vigiaUrl + "&comenPen=1";   }
		
		HttpEntity<Integer> respuesta = null;
		try {
			respuesta = restTemplate.exchange(vigiaUrl,HttpMethod.GET,requestEntity,Integer.class);	
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return 0;
		}
		return respuesta.getBody();
	}		
	
	
	
	
	
	
	
	
	

	
	// _____________________Administrar Marcadores___________________________________

	
	public MarkerPrintPackage obtenerMardadoresbyIdCapa(String idCapa) {		
		if(idCapa!=null) {
			
			String idAplicacion = obtenerAplicacionPorIdCapa(idCapa).getId().toString();
			if(idAplicacion.equals("5")) {
				return obtenerMardadoresMiEntorno(idCapa);
			}else{			
		        RestTemplate restTemplate = new RestTemplate();
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);
		        headers.set("Authorization", getTokenAcceso());
		        MarkerPackage markerPackage = null;
		        MarkerInfo markerInfo = null;
		        String marcResourceUrl = "";
		        
		        MarkerPrintPackage paqueteImpresion = new MarkerPrintPackage();
		        List<MarkerPoint> listaMarcadores = new ArrayList<MarkerPoint>();
		        List<MarkerPolygon> listaPoligonos = new ArrayList<MarkerPolygon>();
		        List<Categoria> listCategoria = getCategoriasByIdCapa(idCapa);		
		        
		        if(listCategoria == null) {			
		        	HttpEntity<MarkerPackage> entity = new HttpEntity<MarkerPackage>(headers);
		        	String capaResourceUrl = getServerEndpoint() + "/api/contenedora/markers/CAPA/" + idCapa;						
		        	try {
		        		HttpEntity<MarkerPackage> entityCapa = restTemplate.exchange(capaResourceUrl,HttpMethod.GET,entity,MarkerPackage.class);
		        		markerPackage = entityCapa.getBody();
		        	} catch (Exception ex) {
		        		System.out.print("Error: " + ex);
		        		return null;
		        	}
		        	
		        	for (MarkerPoint markerPoint : markerPackage.getMarkersPoint()) {
		        		HttpEntity<MarkerInfo> entityMar = new HttpEntity<MarkerInfo>(headers);
		        		String idMarker = "" + markerPoint.getIdMarker();
		        		marcResourceUrl = getServerEndpoint() + "/api/contenedora/marker-info/" + idMarker;						
		        		try {
		        			HttpEntity<MarkerInfo> entityMarc = restTemplate.exchange(marcResourceUrl,HttpMethod.GET,entityMar,MarkerInfo.class);
		        			markerInfo = entityMarc.getBody();		    			
		        			markerPoint.setNombre(markerInfo.getNombre());   
		        			markerPoint.setDescripcion(markerInfo.getDescripcion());
		        			markerPoint.setMarkerEntorno(false);
		        			listaMarcadores.add(markerPoint);
		        		} catch (Exception ex) {
		        			System.out.print("Error: " + ex);
		        		}				
		        	}
		        	
		        	for (MarkerPolygon markerPoligono : markerPackage.getMarkersPolygon()) {
		        		List<LatLng> listaLatLng = PolylineEncoding.decode(markerPoligono.getEncodedPolygon());		    			
	    	    		List<Point> listaPuntos = new ArrayList<Point>();
	    	    		for (LatLng latLng : listaLatLng) {
	    	    			Point punto = new Point();
	    	    			punto.setLat(latLng.lat);
	    	    			punto.setLng(latLng.lng);
	    	    			listaPuntos.add(punto);
	    	    		}
			    
	    	    		HttpEntity<MarkerInfo> entityMar = new HttpEntity<MarkerInfo>(headers);
	    	    		String idMarker = "" + markerPoligono.getId();
	    	    		marcResourceUrl = getServerEndpoint() + "/api/contenedora/marker-info/" + idMarker;						
	    	    		try {
	    	    			HttpEntity<MarkerInfo> entityMarc = restTemplate.exchange(marcResourceUrl,HttpMethod.GET,entityMar,MarkerInfo.class);
	    	    			markerInfo = entityMarc.getBody();	    				
	    	    			markerPoligono.setNombre(markerInfo.getNombre());
	    	    			markerPoligono.setDescripcion(markerInfo.getDescripcion());	    				
	    	    			markerPoligono.setListaPuntos(listaPuntos);
	    	    			markerPoligono.setEncodedPolygon(null);
	    	    			markerPoligono.setMarkerEntorno(false);
	    	    			listaPoligonos.add(markerPoligono);
	    	    		} catch (Exception ex) {
	    	    			System.out.print("Error: " + ex);
	    	    		}		    			
	    	    	}
		        	paqueteImpresion.setListaMarcadores(listaMarcadores);
		        	paqueteImpresion.setListaPoligonos(listaPoligonos);
		        	return paqueteImpresion;		
		        } else {
					for (Categoria categoria : listCategoria) {
						if (categoria.getTipoCategoria().getId() == 2L || categoria.getTipoCategoria().getId() == 8L) {
							String idCategoria = categoria.getId().toString();
							HttpEntity<MarkerPackage> entity = new HttpEntity<MarkerPackage>(headers);
							String categoriaResourceUrl = getServerEndpoint() + "/api/contenedora/markers/CATEGORIA/"
									+ idCategoria;
							try {
								HttpEntity<MarkerPackage> entityCapa = restTemplate.exchange(categoriaResourceUrl,
										HttpMethod.GET, entity, MarkerPackage.class);
								markerPackage = entityCapa.getBody();
							} catch (Exception ex) {
								System.out.print("Error: " + ex);
								return null;
							}

							for (MarkerPoint markerPoint : markerPackage.getMarkersPoint()) {
								HttpEntity<MarkerInfo> entityMar = new HttpEntity<MarkerInfo>(headers);
								String idMarker = "" + markerPoint.getIdMarker();
								try {
									if (idAplicacion.equals("6")) {
										marcResourceUrl = getServerEndpoint()
												+ "/api/huellas/posconsumo/detail?idMarcador=" + idMarker;
										HttpEntity<String> entityEst = new HttpEntity<String>(headers);
										HttpEntity<String> entityInfoEst = restTemplate.exchange(marcResourceUrl,
												HttpMethod.GET, entityEst, String.class);
										markerPoint.setDatosEstacion(entityInfoEst.getBody());
										markerPoint.setMarkerEntorno(true);
										listaMarcadores.add(markerPoint);
									} else {
										marcResourceUrl = getServerEndpoint() + "/api/contenedora/marker-info/"
												+ idMarker;
										HttpEntity<MarkerInfo> entityMarc = restTemplate.exchange(marcResourceUrl,
												HttpMethod.GET, entityMar, MarkerInfo.class);
										markerInfo = entityMarc.getBody();
										markerPoint.setNombre(markerInfo.getNombre());
										markerPoint.setDescripcion(markerInfo.getDescripcion());
										markerPoint.setMarkerEntorno(false);
										listaMarcadores.add(markerPoint);
									}
								} catch (Exception ex) {
									System.out.print("Error: " + ex);
								}
							}

							for (MarkerPolygon markerPoligono : markerPackage.getMarkersPolygon()) {
								List<LatLng> listaLatLng = PolylineEncoding.decode(markerPoligono.getEncodedPolygon());
								List<Point> listaPuntos = new ArrayList<Point>();
								for (LatLng latLng : listaLatLng) {
									Point punto = new Point();
									punto.setLat(latLng.lat);
									punto.setLng(latLng.lng);
									listaPuntos.add(punto);
								}

								HttpEntity<MarkerInfo> entityMar = new HttpEntity<MarkerInfo>(headers);
								String idMarker = "" + markerPoligono.getId();
								marcResourceUrl = getServerEndpoint() + "/api/contenedora/marker-info/" + idMarker;
								try {
									HttpEntity<MarkerInfo> entityMarc = restTemplate.exchange(marcResourceUrl,
											HttpMethod.GET, entityMar, MarkerInfo.class);
									markerInfo = entityMarc.getBody();
									markerPoligono.setNombre(markerInfo.getNombre());
									markerPoligono.setDescripcion(markerInfo.getDescripcion());
									markerPoligono.setListaPuntos(listaPuntos);
									markerPoligono.setEncodedPolygon(null);
									markerPoligono.setMarkerEntorno(false);
									listaPoligonos.add(markerPoligono);
								} catch (Exception ex) {
									System.out.print("Error: " + ex);
								}
							}
						}
					}
		        	paqueteImpresion.setListaMarcadores(listaMarcadores);
		        	paqueteImpresion.setListaPoligonos(listaPoligonos);
		        	return paqueteImpresion;		    	
		        }
			}
		}else {
			return null;
	    }
	}	
	
	
	public MarkerPrintPackage obtenerMardadoresMiEntorno(String idCapa) {		
		if(idCapa!=null) {
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", getTokenAcceso());
		    MarkerPackage markerPackage = null;
		    String marcInformacUrl = "";
		    
		    MarkerPrintPackage paqueteImpresion = new MarkerPrintPackage();
		    List<MarkerPoint> listaMarcadores = new ArrayList<MarkerPoint>();
		    List<MarkerPolygon> listaPoligonos = new ArrayList<MarkerPolygon>();	
		    
		    HttpEntity<MarkerPackage> entity = new HttpEntity<MarkerPackage>(headers);
		    String capaResourceUrl = getServerEndpoint() + "/api/contenedora/markers/CAPA/" + idCapa;						
		    try {
		    	HttpEntity<MarkerPackage> entityCapa = restTemplate.exchange(capaResourceUrl,HttpMethod.GET,entity,MarkerPackage.class);
		    	markerPackage = entityCapa.getBody();
		    } catch (Exception ex) {
		    	System.out.print("Error: " + ex);
		    	return null;
		    }
		    
		    for (MarkerPoint markerPoint : markerPackage.getMarkersPoint()) {		    	
		    	String idMarker = "" + markerPoint.getIdMarker();		    			    		
		    	try {
		    		marcInformacUrl = getServerEndpoint() + "/api/entorno/estacion/detalle/find-By-Marker?idMarcador=" + idMarker;	
		    		HttpEntity<String> entityEst = new HttpEntity<String>(headers);
		    		HttpEntity<String> entityInfoEst = restTemplate.exchange(marcInformacUrl,HttpMethod.GET,entityEst,String.class);
		    		markerPoint.setDatosEstacion(entityInfoEst.getBody());	
		    		markerPoint.setMarkerEntorno(true);
		    		listaMarcadores.add(markerPoint);
		    	} catch (Exception ex) {
		    		System.out.print("Error: " + ex);
		    	}				
		    }
		    
		    for (MarkerPolygon markerPoligono : markerPackage.getMarkersPolygon()) {
		    	List<LatLng> listaLatLng = PolylineEncoding.decode(markerPoligono.getEncodedPolygon());		    			
	    		List<Point> listaPuntos = new ArrayList<Point>();
	    		for (LatLng latLng : listaLatLng) {
	    			Point punto = new Point();
	    			punto.setLat(latLng.lat);
	    			punto.setLng(latLng.lng);
	    			listaPuntos.add(punto);
	    		}
				    		
	    		String idMarker = "" + markerPoligono.getId();	    					
	    		try {
	    			marcInformacUrl = getServerEndpoint() + "/api/entorno/clima/detalle/find-By-Marker?idMarcador=" + idMarker;
	    			HttpEntity<DetalleClima> entityClim = new HttpEntity<DetalleClima>(headers);
	    			HttpEntity<DetalleClima> entityInfoClim = restTemplate.exchange(marcInformacUrl,HttpMethod.GET,entityClim,DetalleClima.class);
	    			
	    			DetalleClima  detalleClima = entityInfoClim.getBody();
	    			detalleClima.getTiempoDetails().remove(3);
	    			detalleClima.getTiempoDetails().remove(2);
                    
	    			markerPoligono.setDetalleClima(detalleClima); 	    			
	    			markerPoligono.setListaPuntos(listaPuntos);
	    			markerPoligono.setEncodedPolygon(null);
	    			markerPoligono.setMarkerEntorno(true);
	    			listaPoligonos.add(markerPoligono);
	    		} catch (Exception ex) {
	    			System.out.print("Error: " + ex);
	    		}		    			
	    	}
		    paqueteImpresion.setListaMarcadores(listaMarcadores);
		    paqueteImpresion.setListaPoligonos(listaPoligonos);
		    return paqueteImpresion;				    	
		}else {
			return null;
	    }
	}		
	
	// __________________Administracion de Recursos_________________________

	/**
	 * Método encargado de devolvernos un listado de los recursos
	 */
	public List<RecursoVigia> obtenerRecursos() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<RecursoVigia[]> listEntity = new HttpEntity<RecursoVigia[]>(headers);

		String listaRecursosUrl = getServerEndpoint() + "/api/recurso";
		RecursoVigia[] listaRecursos = null;
		try {
			HttpEntity<RecursoVigia[]> listEntityRecurso 
			    = restTemplate.exchange(listaRecursosUrl,HttpMethod.GET,listEntity,RecursoVigia[].class);
			listaRecursos = listEntityRecurso.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaRecursos);
	}
	

	/** Método encargado de consultar un recurso con base en un identificador */
	public RecursoVigia obtenerRecurso(String idRecurso) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<RecursoVigia> entity = new HttpEntity<RecursoVigia>(headers);

		String recursoUrl = getServerEndpoint() + "/api/recurso/" + idRecurso;
		RecursoVigia recurso = null;
		try {
			HttpEntity<RecursoVigia> entityRecurso 
			    = restTemplate.exchange(recursoUrl,HttpMethod.GET,entity,RecursoVigia.class);
			recurso = entityRecurso.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return recurso;
	}
	
	
    /** Método encargado de actualizar la información de un recurso. */
	public String createUpdateRecurso(String id, String nombre, String descripcion) {
		
		RecursoVigia recurso = new RecursoVigia();		
		String url = getServerEndpoint() + "/api/recurso";
		
		if (id != null) {
			recurso.setId(Long.valueOf(id));
		}
		recurso.setNombre(nombre);
		recurso.setDescripcion(descripcion);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<RecursoVigia> entity = new HttpEntity<RecursoVigia>(recurso,headers);
				
		RecursoVigia respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.postForObject(url,entity,RecursoVigia.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
			
		if (id == null) {
			return "Se ha agregado una nueva autoridad";
		} else {
			return "La autoridad ha sido actualizada con exito";
		}
	}
	

	/** Método encargado de eliminar una autoridad con base en un identificador */
	public String deleteRecurso(String idRecurso) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<RecursoVigia> entity = new HttpEntity<RecursoVigia>(headers);

		String recursoUrl = getServerEndpoint() + "/api/recurso/" + idRecurso;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(recursoUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}

	
	// __________________Administracion de Afectaciones_________________________

	/**
	 * Método encargado de devolvernos un listado de los recursos
	 */
	public List<Afectacion> obtenerAfectaciones() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Afectacion[]> listEntity = new HttpEntity<Afectacion[]>(headers);

		String listaAfectacionesUrl = getServerEndpoint() + "/api/afectacion";
		Afectacion[] listaAfectaciones = null;
		try {
			HttpEntity<Afectacion[]> listEntityAfectacion 
			    = restTemplate.exchange(listaAfectacionesUrl,HttpMethod.GET,listEntity,Afectacion[].class);
			listaAfectaciones = listEntityAfectacion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaAfectaciones);
	}
	

	/** Método encargado de consultar una afectacion con base en un identificador */
	public Afectacion obtenerAfectacion(String idAfectacion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Afectacion> entity = new HttpEntity<Afectacion>(headers);

		String afectacionUrl = getServerEndpoint() + "/api/afectacion/" + idAfectacion;
		Afectacion afectacion = null;
		try {
			HttpEntity<Afectacion> entityAfectacion 
			    = restTemplate.exchange(afectacionUrl,HttpMethod.GET,entity,Afectacion.class);
			afectacion = entityAfectacion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return afectacion;
	}
	
	
    /** Método encargado de actualizar la información de una afectacion. */
	public String createUpdateAfectacion(String id,String nombre,String descripcion,
			                             String recurso,String autoridad) {
		
		Afectacion afectacion = new Afectacion();		
		String url = getServerEndpoint() + "/api/afectacion";
		
		if (id != null) {
			afectacion.setId(Long.valueOf(id));
		}
		afectacion.setNombre(nombre);
		afectacion.setDescripcion(descripcion);
		afectacion.setRecurso(obtenerRecurso(recurso));
		afectacion.setAutoridad(obtenerAutoridad(autoridad));
				
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Afectacion> entity = new HttpEntity<Afectacion>(afectacion,headers);
		
		Afectacion respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.postForObject(url,entity,Afectacion.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
			
		if (id == null) {
			return "Se ha agregado una nueva autoridad";
		} else {
			return "La autoridad ha sido actualizada con exito";
		}
	}
	

	/** Método encargado de eliminar una afectacion con base en un identificador */
	public String deleteAfectacion(String idAfectacion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Afectacion> entity = new HttpEntity<Afectacion>(headers);

		String afectacionUrl = getServerEndpoint() + "/api/afectacion/" + idAfectacion;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(afectacionUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}
	

    // _______________________________Tareas Programadas____________________________________
	
	/*  Método encargado de ejecutar una tarea programada que se ejecutara cada cierto tiempo 
	    la cual permitira actualizar los marcadores correspondientes a una categoria */
	public String programarTarea(String id, String tipoRecurso, LocalDateTime fechaIni, String intervalo, 
			                     String unidad, String nombre, String idAplicacion) throws SchedulerException {		
		if(mostrarBotonesURL(tipoRecurso,id).equals("ejecutar")) {		
		    String cronExpression = "";
		    String seg="0",
		    	   min=""+fechaIni.getMinute(),
		    	   hor=""+fechaIni.getHour(),
		    	   day=""+fechaIni.getDayOfMonth(),
		    	   mon=""+fechaIni.getMonthValue(),
		    	   yea=""+fechaIni.getYear();
		    				
		    switch(unidad) {		
		        case "Minutos":  
		        	min = fechaIni.getMinute() + "/" + intervalo;
                break;
            
		        case "Horas":  
		        	hor = fechaIni.getHour() + "/" + intervalo;
                break;
                
		        case "Dias":  
		        	day = fechaIni.getDayOfMonth() + "/" + intervalo;
                break;
                
		        case "Meses":  
		            mon = fechaIni.getMonthValue() + "/" + intervalo;
                break;
                
		        case "Anios":  
		        	yea = fechaIni.getYear() + "/" + intervalo;
                break;				
		    }
		    
		    cronExpression = seg + " " + min + " " + hor + " " + day + " " + mon + " " + "?" + " " + yea;
            
		    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		    
		    String jobDetailIden = "";
		    String jobDetailGroup = "";
		    String cronTriggIden = "";
		    String cronTriggGroup = "";
		    
		    if(tipoRecurso.equals("CAPA")) {
		    	jobDetailIden = "jobCapa_"+id;
			    jobDetailGroup = "groupCapa_"+id;
			    cronTriggIden = "triggerCapa_"+id;
			    cronTriggGroup = "groupCapa_"+id;	
			}else {
				jobDetailIden = "jobCateg_"+id;
			    jobDetailGroup = "groupCateg_"+id;
			    cronTriggIden = "triggerCateg_"+id;
			    cronTriggGroup = "groupCateg_"+id;	
			}	
		    
		    JobDetail job = JobBuilder.newJob(JobTarea.class)  
		        .withIdentity(jobDetailIden,jobDetailGroup)
		        .usingJobData("idRecurso",id)
		        .usingJobData("tipoRecurso",tipoRecurso)
		        .usingJobData("idAplicacion",idAplicacion)
	            .usingJobData("serverEndpoint",getServerEndpoint()).build();
		    
		    CronTrigger trigger = TriggerBuilder.newTrigger()
		    	.withIdentity(cronTriggIden,cronTriggGroup).startNow()
		    	.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
		    	.build();
            
		    try {
                scheduler.scheduleJob(job,trigger);
                scheduler.start();	
            } catch (Exception e) {
            	LOGGER.error("Error durante el proceso: " + e.getMessage());
            	
            	if(tipoRecurso.equals("CAPA")) {
            		return "Error al programar la tarea para la Capa " + nombre;   
    			}else {	
    				return "Error al programar la tarea para la Categoría " + nombre;   
    			}           	                  
            }
		    
		    String tarea = createUpdateTareaProgramada(null,id,fechaIni,intervalo,unidad,tipoRecurso,idAplicacion);	

		    if(tipoRecurso.equals("1")) {
        		return "Tarea programada para la Capa " + nombre;   
			}else {	
				return "Tarea programada para la Categoría " + nombre;   
			}  
		} else {
			return "Ya existe otro proceso actualizando la misma URL";	
		}
		
		//return "No esta en funcionamiento";
	}	
		
	

	/** Método encargado de correr manualmente una tarea la cual permitira
	    actualizar los marcadores correspondientes a una categoria */	
	public String correrTarea(String tipoRecurso, String idRecurso, String idAplicacion) {	
		String url;	
				
		if(idAplicacion.equals("4")) {
			// Actualizadores de Ordenamiento
		    if(tipoRecurso.equals("CAPA")){
		        url = getServerEndpoint() + "/api/contenedora/load-markers-capa?idCapa=" + idRecurso;
		    }else {
			    url = getServerEndpoint() + "/api/contenedora/load-markers-categoria?idCategoria=" + idRecurso;
		    }
		}else {
			if(idAplicacion.equals("5")){
			    // Actualizadores de Mi Entorno
			    url = getServerEndpoint() + "/api/entorno/estacion/update?idCapa=" + idRecurso;
			}else {
				if(idAplicacion.equals("6")){
				    // Actualizadores de Huellas
				    //url = getServerEndpoint() + "/api/entorno/estacion/update?idCapa=" + idRecurso;
				    url = "";
				}else {
					url = "";
				}
			}
		}
										
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String,Object>>(headers);
		HttpEntity<Boolean> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			respuesta = restTemplate.exchange(url,HttpMethod.PUT,requestEntity,Boolean.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Hubo un error en el proceso";
		}
		if(respuesta.getBody()) {
			return "Los marcadores han sido actualizados";
		}else {
			return "No se pudieron actualizar los marcadores";
		}	
				
		//return "No esta en funcionamiento";
	}	
	
	
    /** Método encargado de actualizar la información de una tarea programada. */
	public String createUpdateTareaProgramada(String id, String idRecurso,LocalDateTime fechaIni,String intervalo, 
			                                  String unidad,String tipoRecurso,String idAplicacion) {		
		TareaProgramada tareaProgramada = new TareaProgramada();
		if (id != null) {
			tareaProgramada.setId(Long.valueOf(id));
		}
		String url = "";
		if(tipoRecurso.equals("CAPA")) {
			url = obtenerDetalleCapa(idRecurso).getUrlRecurso();	
		}else {
			url = obtenerCategoria(idRecurso).getUrlRecurso();
		}
		tareaProgramada.setTipoRecurso(tipoRecurso);
		tareaProgramada.setIdRecurso(Long.valueOf(idRecurso));
		tareaProgramada.setFechaIni(java.sql.Timestamp.valueOf(fechaIni));						
		tareaProgramada.setUrlRecursoWS(url);
		tareaProgramada.setIntervalo(Integer.valueOf(intervalo));
		tareaProgramada.setUnidad(unidad);
		tareaProgramada.setActivo(true);
		tareaProgramada.setIdAplicacion(Long.valueOf(idAplicacion));
		
		boolean respuesta;
		try {
			respuesta = servicioTareasProgramadas.tareaProgramadaGuardar(tareaProgramada);
		} catch (Exception e) {
			respuesta = false;
		}
		
		if(respuesta) {
		    if (id == null) {
			    return "Se ha agregado una nueva tarea programada";
		    } else {
			    return "La tarea programada ha sido actualizada con exito";
		    }
		}else {
			return "Error en el proceso";
		}
		
		//return "No esta en funcionamiento";
	}
	

	/*  Método encargado de verificar si una URL ya se encuentra en alguna tarea programada existente*/
	public String mostrarBotonesURL(String tipoRecurso,String idRecurso) {		
		List<TareaProgramada> listaTareas = null;	
		String url = "";
		
		if(tipoRecurso.equals("CAPA")) {
			url = obtenerDetalleCapa(idRecurso).getUrlRecurso();
		}else {
			url = obtenerCategoria(idRecurso).getUrlRecurso();	
		}	
		
		if ((url == null) || (url.equals(""))){
			return "ninguno";
		}else {
		    try {
		    	listaTareas = servicioTareasProgramadas.findByActivo(true);			
		    	for (TareaProgramada tarea : listaTareas) {
		    		if (tarea.getUrlRecursoWS().equals(url)) {
		    			return "detener";
		    		}
	    		}
	    	} catch (Exception e) {
	    		return "ninguno";
	    	}
		    return "ejecutar";
        }		
	}	
			
	
	/** Método encargado de correr manualmente una tarea la cual permitira
    actualizar los marcadores correspondientes a una categoria */	
	public String detenerTarea(String tipoRecurso,String idRecurso){					
		List<TareaProgramada> listaTareas = null;		
		String url = "";
		if(tipoRecurso.equals("CAPA")) {
			url = obtenerDetalleCapa(idRecurso).getUrlRecurso();
		}else {
			url = obtenerCategoria(idRecurso).getUrlRecurso();	
		}
				
		try {
			listaTareas = servicioTareasProgramadas.findByActivo(true);	
			
			if(!listaTareas.isEmpty() && listaTareas!=null){
			    for (TareaProgramada tarea : listaTareas) {
				    if (tarea.getUrlRecursoWS().equals(url)) {
					    Scheduler scheduler = new StdSchedulerFactory().getScheduler();	
					
					    TriggerKey triggerKey = null;
					    if(tipoRecurso.equals("CAPA")) {
					    	triggerKey = TriggerKey.triggerKey("triggerCapa_"+idRecurso,"groupCapa_"+idRecurso);
						}else {
							triggerKey = TriggerKey.triggerKey("triggerCateg_"+idRecurso,"groupCateg_"+idRecurso);							
						}					    
					    scheduler.unscheduleJob(triggerKey);
					    
					    boolean res = servicioTareasProgramadas.tareaProgramadaBorrar(tarea.getId());	
		        	    return "La tarea ha sido detenida con éxito";
				    }
			    }
			} else {
				return "No ha sido posible detener la tarea";	
			}			
			return "No ha sido posible detener la tarea";
		} catch (Exception e) {
			return "No ha sido posible detener la tarea";
		}		

		//return "No esta en funcionamiento";		 		 		 
	}	
	

    // _______________________________Notificaciones Push____________________________________	
	
    /** Método encargado de guardar en base de datos una notificacion Push. */
	public String createUpdateNotificacionPush(String id,String titulo,String cuerpo,String destinatario) {
		
		NotificacionPush notificacionPush = new NotificacionPush();	
		
		if (id != null) {
			notificacionPush.setId(Long.valueOf(id));
		}
		notificacionPush.setTitulo(titulo);
		notificacionPush.setCuerpo(cuerpo);
		notificacionPush.setFecha(new Date()); 
		notificacionPush.setDestinatario(destinatario);
		
		boolean respuesta;				
		try {
			respuesta = servicioNotificacionesPush.notificacionPushGuardar(notificacionPush);			
		} catch (Exception e) {
			respuesta = false;
		}
		
		if(respuesta) {
		    if (id == null) {
			    return "Se ha agregado una nueva notificacion Push a la base de datos";
		    } else {
			    return "La notificacion Push ha sido actualizada con exito";
		    }
		}else {
			return "Error en el proceso";
		}
	}

	
    /** Método encargado de actualizar la información de una tarea programada. */
	public List<NotificacionPush> listarNotificacionesPush() {
		
		List<NotificacionPush> listaNotificaciones = null;
		try {
			listaNotificaciones = servicioNotificacionesPush.notificacionPushObtenerTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return listaNotificaciones;
	}	
	
	// _______________Administrar Recomendaciones_________________________________
	
	/**
	 * Método encargado de devolvernos una recomendacion correspondiente a una aplicacion
	 */
	public Recomendacion obtenerDetalleRecomendacion(String idAplicacion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Recomendacion> listEntity = new HttpEntity<Recomendacion>(headers);

		String recomendacionesUrl = getServerEndpoint() + "/api/recomendacion/aplicacion/" + idAplicacion;
		Recomendacion recomendacion = null;
		try {
			HttpEntity<Recomendacion> entityRecomendacion 
			    = restTemplate.exchange(recomendacionesUrl,HttpMethod.GET,listEntity,Recomendacion.class);
			recomendacion = entityRecomendacion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}

		return recomendacion;
	}

	/**
	 * Método encargado de crear y editar recomendaciones para cada aplicacion.
	 */
	public String createUpdateRecomendacion(String id, String nombre, String descripcion, String idAplicacion) {

		HttpEntity<String> respuesta = null;
		String respuestafINAL = null;
		String url = getServerEndpoint() + "/api/recomendacion/" + idAplicacion;
		RestTemplate restTemplate = new RestTemplate();
		try {
			Recomendacion recomendacion = new Recomendacion();
			if(!id.equals("")) {
			    recomendacion.setId(Long.parseLong(id));
			}
			recomendacion.setDescripcion(descripcion);
			recomendacion.setNombre(nombre);
			recomendacion.setTipo("Bienvenida");
			recomendacion.setActivo(true);
			recomendacion.setIdAplicacion(idAplicacion);
										
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", getTokenAcceso());
                       
            HttpEntity<Recomendacion> entity = new HttpEntity<Recomendacion>(recomendacion,headers); 
			
			if (id.equals("")) {
				respuesta = restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
			} else {
				respuesta = restTemplate.exchange(url,HttpMethod.PUT,entity,String.class);
			}
			respuestafINAL = respuesta.getBody();
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			respuestafINAL = "Error en el Proceso";
		}
		return respuestafINAL;
	}
	
	// _______________Administrar Iconos por Estado_________________________________	
	
	/** Método encargado de colocar las rutas de los iconos por estado a las capas 
        de avistamiento. */
	public Capa capasColocarRutasIconosEstado(Capa capa) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<IconoEstado[]> listEntity = new HttpEntity<IconoEstado[]>(headers);

		String url = getServerEndpoint() + "/api/avistamiento/iconoEstado/capa/" + capa.getId();
		IconoEstado[] listaIconoEstado = null;
		try {
			HttpEntity<IconoEstado[]> listEntityIco 
			    = restTemplate.exchange(url,HttpMethod.GET,listEntity,IconoEstado[].class);
			listaIconoEstado = listEntityIco.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		
		for (IconoEstado iconoEstado : Arrays.asList(listaIconoEstado)) {           
			if(iconoEstado.getIdEstado() == 2) {
				capa.setRutaIconoPendiente(iconoEstado.getIcono().getRutaLogo());						
			}else {
				if(iconoEstado.getIdEstado() == 0) {
					capa.setRutaIconoRechazado(iconoEstado.getIcono().getRutaLogo());			
				}else {
					if(iconoEstado.getIdEstado() == 1) {
						capa.setRutaIconoAprobado(iconoEstado.getIcono().getRutaLogo());		
					}	
				}
			}
		}
		return capa;
	}
	
	/** Método encargado de colocar las rutas de los iconos por estado a las categorias 
	    de avistamiento. */
	public Categoria categoriasColocarRutasIconosEstado(Categoria categoria) {	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<IconoEstado[]> listEntity = new HttpEntity<IconoEstado[]>(headers);

		String url = getServerEndpoint() + "/api/avistamiento/iconoEstado/categoria/" + categoria.getId();
		IconoEstado[] listaIconoEstado = null;
		try {
			HttpEntity<IconoEstado[]> listEntityIco 
			    = restTemplate.exchange(url,HttpMethod.GET,listEntity,IconoEstado[].class);
			listaIconoEstado = listEntityIco.getBody();			
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}		
		
		for (IconoEstado iconoEstado : Arrays.asList(listaIconoEstado)) {             
			if(iconoEstado.getIdEstado() == 2) {
				categoria.setRutaIconoPendiente(iconoEstado.getIcono().getRutaLogo());						
			}else {
				if(iconoEstado.getIdEstado() == 0) {
					categoria.setRutaIconoRechazado(iconoEstado.getIcono().getRutaLogo());			
				}else {
					if(iconoEstado.getIdEstado() == 1) {
						categoria.setRutaIconoAprobado(iconoEstado.getIcono().getRutaLogo());		
					}	
				}
			}
		}
		return categoria;
	}
	
	// ___________________Administracion de Actividades____________________________

	/** Método encargado de devolvernos un listado de las actividades de huellas
	 */
	public List<ActividadHuella> obtenerActividadesByIdCapa(String idCapa) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ActividadHuellaPackage> listEntity = new HttpEntity<ActividadHuellaPackage>(headers);

		String listaActividadesUrl = getServerEndpoint() + "/api/huellas/encuesta-hidrica/actividades?idCapa=" + idCapa;
		ActividadHuellaPackage listaActividades = null;     
		try {
			HttpEntity<ActividadHuellaPackage> listEntityActividad   
			    = restTemplate.exchange(listaActividadesUrl,HttpMethod.GET,listEntity,ActividadHuellaPackage.class);
			listaActividades = listEntityActividad.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return listaActividades.getResponses();
	}
	
	
	/** Método encargado de consultar una Actividad de Huella con base en un identificador */
	public ActividadHuella obtenerActividad(String idActividadHuella) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ActividadHuellaPackage> entity = new HttpEntity<ActividadHuellaPackage>(headers);

		String actividadUrl = getServerEndpoint() + "/api/huellas/encuesta-hidrica/actividad?id=" + idActividadHuella;
		HttpEntity<ActividadHuellaPackage> entityActividad = null;
		try {
			entityActividad = restTemplate.exchange(actividadUrl,HttpMethod.GET,entity,ActividadHuellaPackage.class);
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return entityActividad.getBody().getResponses().get(0);		
	}
	
	
	/**
	 * Método encargado de crear o editar las actividades del aplicativo huellas
	 */
	public String createUpdateActividad(String id,String nombre,String descripcion,
			                            String orden,boolean decision,String idCapa) {	
		
		RestTemplate restTemplate = new RestTemplate();

		String actividad = "{" + "\"id\":\"" + id + "\"," + 
		                         "\"nombre\":\"" + nombre + "\"," + 
				                 "\"descripcion\":\"" + descripcion + "\"," + 
				                 "\"orden\":" + orden + "," + 
				                 "\"decision\":" + decision + "," + 
		                         "\"idCapa\":\"" + idCapa + "\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<String> entity = new HttpEntity<String>(actividad,headers);
		
		String url = getServerEndpoint() + "/api/huellas/encuesta-hidrica/actividad";
		HttpEntity<ActividadHuellaPackage> respuesta = null;
		try {
		    if (id != null) {
			    respuesta = restTemplate.exchange(url,HttpMethod.PUT, entity,ActividadHuellaPackage.class);
		    } else {
			    respuesta = restTemplate.exchange(url,HttpMethod.POST,entity,ActividadHuellaPackage.class);
		    }
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
				
		if (respuesta.getBody().getTransaccion().getStatus().equals("SUCCESS")) {
		    if (id == null) {
			    return "Se ha agregado una nueva actividad";
		    } else {
			    return "La actividad ha sido actualizada con éxito";
		    }
		}else {
			return "Error en el proceso";
		}

	}	
	
	
	/** Método encargado de eliminar un actividad con base en un identificador */
	public String deleteActividad(String idActividadHuella) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<ActividadHuella> entity = new HttpEntity<ActividadHuella>(headers);

		String actividadUrl = getServerEndpoint() + "/api/huellas/encuesta-hidrica/actividad?id=" + idActividadHuella;
		HttpEntity<ActividadHuellaPackage> respuesta = null;
		try {
			respuesta = restTemplate.exchange(actividadUrl,HttpMethod.DELETE,entity,ActividadHuellaPackage.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		
		if (respuesta.getBody().getTransaccion().getStatus().equals("SUCCESS")) {
			return "La actividad ha sido eliminada con éxito";
		}else {
			return "Error en el proceso";
		}
	}
	
	
	public List<Integer> obtenerOrdenesActividadesByIdCapa(String idCapa) {		
		List<ActividadHuella> listActividades = obtenerActividadesByIdCapa(idCapa);
		List<Integer> listOrden = new ArrayList<Integer>();
		for (ActividadHuella act : listActividades) {
			listOrden.add(Integer.parseInt(act.getOrden()));
	    }
		return listOrden;
	}
	
	// ___________________Administracion de Preguntas de huella____________________________

	/** Método encargado de devolvernos un listado de las preguntas de huellas
	 */
	public List<PreguntaHuella> obtenerPreguntasHuella(String idActividad) {
		/*RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<PreguntaHuella[]> listEntity = new HttpEntity<PreguntaHuella[]>(headers);

		String listaPreguntasHuellaUrl = getServerEndpoint() + "/api/formulario/" + idActividad;
		PreguntaHuella[] listaPreguntasHuella = null;
		try {
			HttpEntity<PreguntaHuella[]> listEntityPreguntasHuella 
			    = restTemplate.exchange(listaPreguntasHuellaUrl,HttpMethod.GET,listEntity,PreguntaHuella[].class);
			listaPreguntasHuella = listEntityPreguntasHuella.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaPreguntasHuella);*/
		
		TipoRespuestaHuella b = new TipoRespuestaHuella(2L,"Numerico","Numerico",null);
		PreguntaHuella rr = new PreguntaHuella(1L,"prueba","prueba","1",1L,b);		
		List<PreguntaHuella> uu = new ArrayList<PreguntaHuella>();
		uu.add(rr);
		return uu;
	}
	
	
	/** Método encargado de consultar una pregunta de Huella con base en un identificador */
	public PreguntaHuella obtenerPreguntaHuella(String idPreguntaHuella) {
		/*RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<PreguntaHuella> entity = new HttpEntity<PreguntaHuella>(headers);

		String preguntaHuellaUrl = getServerEndpoint() + "/api/formulario/" + idPreguntaHuella;
		HttpEntity<PreguntaHuella> entityPreguntaHuella = null;
		try {
			entityPreguntaHuella = restTemplate.exchange(preguntaHuellaUrl,HttpMethod.GET,entity,PreguntaHuella.class);

		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return entityPreguntaHuella.getBody();*/
				
		TipoRespuestaHuella b = new TipoRespuestaHuella(2L,"Numerico","Numerico",null);
		return new PreguntaHuella(1L,"prueba","prueba","1",1L,b);
	}
	
	
	/**
	 * Método encargado de crear o editar las preguntas del aplicativo huellas
	 */
	public String createUpdatePreguntaHuella(String id, String nombre, String descripcion, 
			                                 String idActividad, String idTipoRespuesta) {
		String url = getServerEndpoint() + "/api/categoria";

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("nombre",nombre);
		params.add("descripcion",descripcion);
		params.add("idActividad",idActividad);
		params.add("idTipoRespuesta",idTipoRespuesta);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		
		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (id == null) {
				respuesta = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
			} else {
				params.add("id", Long.valueOf(id));
				respuesta = restTemplate.exchange(url,HttpMethod.PUT,requestEntity,String.class);
			}
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}	
	
	
	/** Método encargado de eliminar una pregunta con base en un identificador */
	public String deletePreguntaHuella(String idPreguntaHuella) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<PreguntaHuella> entity = new HttpEntity<PreguntaHuella>(headers);

		String preguntaHuellaUrl = getServerEndpoint() + "/api/formulario/" + idPreguntaHuella;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(preguntaHuellaUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();   
	}
	
	
	public List<Integer> obtenerOrdenesPreguntasByActividad(String idActividad) {		
		List<PreguntaHuella> listPreguntas = obtenerPreguntasHuella(idActividad);
		List<Integer> listOrden = new ArrayList<Integer>();
		for (PreguntaHuella preg : listPreguntas) {
			listOrden.add(Integer.parseInt(preg.getOrden()));
	    }
		return listOrden;
	}	
	
	
	public List<TipoRespuestaHuella> obtenerTiposRespuestaHuella() {			
		List<TipoRespuestaHuella> listTipos = new ArrayList<TipoRespuestaHuella>();		
		TipoRespuestaHuella a = new TipoRespuestaHuella(1L,"Booleano","Booleano",null);
		listTipos.add(a);
		TipoRespuestaHuella b = new TipoRespuestaHuella(2L,"Numerico","Numerico",null);
		listTipos.add(b);
		return listTipos;
	}	
	
	
	// __________________Administracion de Auditorias_________________________

	/**
	 * Método encargado de devolvernos un listado de las auditorias
	 */
	public List<Auditoria> obtenerAuditorias() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Auditoria[]> listEntity = new HttpEntity<Auditoria[]>(headers);

		String listaAuditoriasUrl = getServerEndpoint() + "/api/auditoria";
		Auditoria[] listaAuditorias = null;
		try {
			HttpEntity<Auditoria[]> listEntityAuditoria 
			    = restTemplate.exchange(listaAuditoriasUrl,HttpMethod.GET,listEntity,Auditoria[].class);
			listaAuditorias = listEntityAuditoria.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaAuditorias);
	}
	

	/** Método encargado de consultar una auditoria con base en un identificador */
	public Auditoria obtenerAuditoria(String idAuditoria) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Auditoria> entity = new HttpEntity<Auditoria>(headers);

		String auditoriaUrl = getServerEndpoint() + "/api/auditoria/" + idAuditoria;
		Auditoria auditoria = null;
		try {
			HttpEntity<Auditoria> entityAuditoria 
			    = restTemplate.exchange(auditoriaUrl,HttpMethod.GET,entity,Auditoria.class);
			auditoria = entityAuditoria.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return auditoria;
	}
	
	
    /** Método encargado de actualizar la información de una auditoria. */
	public String createUpdateAuditoria(Long idObjeto,String descripcion,int accion) {
		
		// accion: Adición=1, Edición=2, Borrar=3, Imprimir=4, Consulta=5	
		
		/*boolean puedeAuditar = true;
		Objeto objeto = obtenerDetalleObjeto(idObjeto.toString());
		
		if(accion==1) {
			puedeAuditar = objeto.getAuditarAdicion();
		}else {
			if(accion==2) {
				puedeAuditar = objeto.getAuditarEdicion();
			}else {
				if(accion==3) {
					puedeAuditar = objeto.getAuditarBorrar();
				}else {
					if(accion==4) {
						puedeAuditar = objeto.getAuditarImprimir();
					}else {
						puedeAuditar = objeto.getAuditarConsulta();
					}
				}
			}
		}
		
		if(puedeAuditar){
		    Auditoria auditoria = new Auditoria();		
		    String url = getServerEndpoint() + "/api/auditoria";

		    auditoria.setIdObjeto(idObjeto);
		    auditoria.setIdUsuario(CentroControlContextHolder.getIdUsuarioSesion());
		    auditoria.setFecha(new Date());
		    auditoria.setDescripcion(descripcion);
				
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", getTokenAcceso());
		    HttpEntity<Auditoria> entity = new HttpEntity<Auditoria>(auditoria,headers);
		
		    Auditoria respuesta = null;
		    try {
			    RestTemplate restTemplate = new RestTemplate();
			    respuesta = restTemplate.postForObject(url,entity,Auditoria.class);
		    } catch (Exception ex) {
			    LOGGER.error("Error durante el proceso: "+ex.getMessage());
		        return "Error en el proceso";
		    }			
		    return "Se ha agregado una nueva auditoria";
		}*/
		return "Esta auditoria no está permitida";
	}
	

	/** Método encargado de eliminar una auditoria con base en un identificador */
	public String deleteAuditoria(String idAuditoria) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Auditoria> entity = new HttpEntity<Auditoria>(headers);

		String auditoriaUrl = getServerEndpoint() + "/api/auditoria/" + idAuditoria;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(auditoriaUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}
	
	
	// __________________Administracion de Especie_________________________

	/**
	 * Método encargado de devolvernos un listado de especies
	 */
	public List<Especie> obtenerEspeciesPorIdCategoria(String idCategoria) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Especie[]> listEntity = new HttpEntity<Especie[]>(headers);

		String listaEspeciesUrl = getServerEndpoint() + "/api/avistamiento/especie/list/" + idCategoria;
		Especie[] listaEspecies = null;
		try {
			HttpEntity<Especie[]> listEntityAfectacion 
			    = restTemplate.exchange(listaEspeciesUrl,HttpMethod.GET,listEntity,Especie[].class);
			listaEspecies = listEntityAfectacion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaEspecies);
	}
	

	/** Método encargado de consultar una especie con base en un identificador */
	public Especie obtenerEspecie(String idEspecie) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Especie> entity = new HttpEntity<Especie>(headers);

		String especieUrl = getServerEndpoint() + "/api/avistamiento/especie/" + idEspecie;
		Especie especie = null;
		try {
			HttpEntity<Especie> entityEspecie 
			    = restTemplate.exchange(especieUrl,HttpMethod.GET,entity,Especie.class);
			especie = entityEspecie.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return especie;
	}
	
	
	/**
	 * Método encargado de crear o editar las especies
	 */
	public String createUpdateEspecie(String id, String nombre, MultipartFile icono, String idCategoria) {
		String url = getServerEndpoint() + "/api/avistamiento/especie";
		MultipartInputStreamFileResource iconoFile = null;			
		try {
			iconoFile = new MultipartInputStreamFileResource(icono.getInputStream(), icono.getOriginalFilename());
		} catch (Exception ex) {}

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("icono", iconoFile);
		params.add("nombre", nombre); 
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (id == null) {
				params.add("idCategoria", Long.valueOf(idCategoria));
				respuesta = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
			} else {
				params.add("id", Long.valueOf(id));
				respuesta = restTemplate.exchange(url,HttpMethod.PUT,requestEntity,String.class);
			}
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}		
		String respuestaString = respuesta.getBody();		
		return respuestaString;
	}
	

	/** Método encargado de eliminar una afectacion con base en un identificador */
	public String deleteEspecie(String idEspecie) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<Especie> entity = new HttpEntity<Especie>(headers);

		String especieUrl = getServerEndpoint() + "/api/avistamiento/especie/" + idEspecie;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(especieUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}	
	
	
	// __________Administracion de Recomendacion de medicion_________________

	/**
	 * Método encargado de devolvernos un listado de recomendacion
	 */
	public List<RecomendacionAire> obtenerTodasRecomendaciones() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<RecomendacionAire[]> listEntity = new HttpEntity<RecomendacionAire[]>(headers);

		String listaRecomendacionesUrl = getServerEndpoint() + "/api/entorno/recomendacion/list";
		RecomendacionAire[] listaRecomendaciones = null;
		try {
			HttpEntity<RecomendacionAire[]> listEntityRecomendacion 
			    = restTemplate.exchange(listaRecomendacionesUrl,HttpMethod.GET,listEntity,RecomendacionAire[].class);
			listaRecomendaciones = listEntityRecomendacion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return Arrays.asList(listaRecomendaciones);
	}
	

	/** Método encargado de consultar una recomendacion con base en un identificador */
	public RecomendacionAire obtenerRecomendacion(String idRecomendacion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<RecomendacionAire> entity = new HttpEntity<RecomendacionAire>(headers);

		String recomendacionUrl = getServerEndpoint() + "/api/entorno/recomendacionPorId/" + idRecomendacion;
		RecomendacionAire recomendacion = null;
		try {
			HttpEntity<RecomendacionAire> entityRecomendacion 
			    = restTemplate.exchange(recomendacionUrl,HttpMethod.GET,entity,RecomendacionAire.class);
			recomendacion = entityRecomendacion.getBody();
		} catch (Exception ex) {
			System.out.print("Error: " + ex);
			return null;
		}
		return recomendacion;
	}
	
	
	/**
	 * Método encargado de crear o editar las recomendacion
	 */
	public String createUpdateRecomendacion(String id,String texto,MultipartFile icono,String codigo) {
		String url = getServerEndpoint() + "/api/entorno/recomendacion";
		MultipartInputStreamFileResource iconoFile = null;			
		try {
			iconoFile = new MultipartInputStreamFileResource(icono.getInputStream(), icono.getOriginalFilename());
		} catch (Exception ex) {}

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("icono",iconoFile);
		params.add("texto",texto); 
		params.add("codigo",codigo); 
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", getTokenAcceso());

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity 
		    = new HttpEntity<LinkedMultiValueMap<String, Object>>(params, headers);
		HttpEntity<String> respuesta = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (id == null) {
				respuesta = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
			} else {
				params.add("id", Long.valueOf(id));
				respuesta = restTemplate.exchange(url,HttpMethod.PUT,requestEntity,String.class);
			}
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}		
		String respuestaString = respuesta.getBody();		
		return respuestaString;
	}
	

	/** Método encargado de eliminar una recomendacion con base en un identificador */
	public String deleteRecomendacion(String idRecomendacion) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", getTokenAcceso());
		HttpEntity<RecomendacionAire> entity = new HttpEntity<RecomendacionAire>(headers);

		String recomendacionUrl = getServerEndpoint() + "/api/entorno/recomendacion/" + idRecomendacion;
		HttpEntity<String> respuesta = null;
		try {
			respuesta = restTemplate.exchange(recomendacionUrl,HttpMethod.DELETE,entity,String.class);
		} catch (Exception ex) {
			LOGGER.error("Error durante el proceso: "+ex.getMessage());
			return "Error en el proceso";
		}
		return respuesta.getBody();
	}	


	
/*
{ 
"contrasena": "AdA2017@",
"nombreFuenteRegistro": "AD", 
"username": "area247@ada.co"
}
*/

}