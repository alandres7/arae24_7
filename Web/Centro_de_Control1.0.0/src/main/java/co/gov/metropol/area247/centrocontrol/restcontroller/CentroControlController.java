package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.util.DateTime;
import com.google.gson.Gson;

import co.gov.metropol.area247.centrocontrol.dto.LoginExternoDto;
import co.gov.metropol.area247.centrocontrol.model.ActividadHuella;
import co.gov.metropol.area247.centrocontrol.model.Afectacion;
import co.gov.metropol.area247.centrocontrol.model.Aplicacion;
import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;
import co.gov.metropol.area247.centrocontrol.model.AutoridadCompetente;
import co.gov.metropol.area247.centrocontrol.model.Avistamiento;
import co.gov.metropol.area247.centrocontrol.model.Capa;
import co.gov.metropol.area247.centrocontrol.model.CapaSubmenu;
import co.gov.metropol.area247.centrocontrol.model.Categoria;
import co.gov.metropol.area247.centrocontrol.model.CategoriaSubmenu;
import co.gov.metropol.area247.centrocontrol.model.ComentarioAvist;
import co.gov.metropol.area247.centrocontrol.model.ComentarioVigia;
import co.gov.metropol.area247.centrocontrol.model.Especie;
import co.gov.metropol.area247.centrocontrol.model.EstadisticaAvistamiento;
import co.gov.metropol.area247.centrocontrol.model.EstadisticaReporteAvist;
import co.gov.metropol.area247.centrocontrol.model.EstadisticaVigia;
import co.gov.metropol.area247.centrocontrol.model.Formulario;
import co.gov.metropol.area247.centrocontrol.model.Historia;
import co.gov.metropol.area247.centrocontrol.model.IconoEstado;
import co.gov.metropol.area247.centrocontrol.model.MarkerPrintPackage;
import co.gov.metropol.area247.centrocontrol.model.Medicion;
import co.gov.metropol.area247.centrocontrol.model.Mensaje;
import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.model.ObjetoWrapper;
import co.gov.metropol.area247.centrocontrol.model.OpcionPregunta;
import co.gov.metropol.area247.centrocontrol.model.PermisoRolWrapper;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuarioVista;
import co.gov.metropol.area247.centrocontrol.model.PermisoWrapper;
import co.gov.metropol.area247.centrocontrol.model.Pregunta;
import co.gov.metropol.area247.centrocontrol.model.PreguntaHuella;
import co.gov.metropol.area247.centrocontrol.model.RecomendacionAire;
import co.gov.metropol.area247.centrocontrol.model.RecursoVigia;
import co.gov.metropol.area247.centrocontrol.model.ReporteVigia;
import co.gov.metropol.area247.centrocontrol.model.RespuestaLogin;
import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.model.Usuario;
import co.gov.metropol.area247.centrocontrol.model.paginacion.AppUtil;
import co.gov.metropol.area247.centrocontrol.model.paginacion.DataTableRequest;
import co.gov.metropol.area247.centrocontrol.model.paginacion.DataTableResults;
import co.gov.metropol.area247.centrocontrol.model.paginacion.PaginationCriteria;
import co.gov.metropol.area247.centrocontrol.security.context.CentroControlContextHolder;
import co.gov.metropol.area247.centrocontrol.security.service.SecurityService;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadLoginExternoService;
import co.gov.metropol.area247.centrocontrol.service.CentroControlExchange;

@Controller
public class CentroControlController {

	private static final String CAPA_DEFAULT = null;
	private static final String KEY_TITLE = "title";
	private static final String KEY_MENU_ITEMS_LIST = "menuItemsList";
	private static final String KEY_LOGIN_ERROR = "loginError"; ///
	private static final String KEY_LOGIN_ERROR_MESSAGE = "loginErrorMessage"; ///
	private static final String KEY_LISTA_MARCADORES = "paqueteImpresion"; ///

	private static final String VAL_TITLE_LOGIN = "Login"; ///
	private static final String VAL_TITLE_INICIO = "Inicio"; ///

	private static final String KEY_KML_BORDER = "kmlBorder"; ///
	private static final String VAL_KML_BORDER_URLMAPKML = "https://bitbucket.org/luisyepes/kmls/raw/5563efa9d9dc5b8a642848f203817b18f9b881b5/Meteorologico2.kml";
	// private static final String VAL_KML_BORDER_URLMAPKML = "http://localhost:9097/assets/kml/aburraValleyBorders.kml";

	private static final String VAL_DESPLIEGUE_BLOCK = "block"; ///
	private static final String VAL_DESPLIEGUE_NONE = "none";

	private static final String RSRC_LOGIN = "area247/login";
	private static final String RSRC_INICIO = "area247/inicio";

	@Autowired // Crea el objeto automaticamente y lo destruye cuando termina la
				// session o transaccion (Inyectar dependencias)
	private CentroControlExchange centroControlExchange;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private ISeguridadLoginExternoService loginExternoService;

	@Value("${area247.password}")
	private String psswrdContenedora;

	@Value("${area247.fuente.registro}")
	private String fuenteRegistroContenedora;

	@Value("${area247.reporte.vehiculos.url}")
	private String reporteVehiculosUrl;

	@Value("${area247.reporte.fotodetecciones.url}")
	private String reporteFotodeteccionesUrl;

	@Value("${area247.reporte.mapafotodetecciones.url}")
	private String reporteMapaUrl;

	// _________________________________________Administrar
	// Login__________________________________________

	/**
	 * Método encargado de darnos acceso a la página de loguin del aplicativo
	 */
	@RequestMapping("/login")
	public String intro(Model model) {
		model.addAttribute(KEY_TITLE, VAL_TITLE_LOGIN);
		model.addAttribute(KEY_LOGIN_ERROR, Boolean.FALSE);
		model.addAttribute(KEY_LOGIN_ERROR_MESSAGE, "");
		return RSRC_LOGIN;
	}

	/**
	 * Método encargado de darnos acceso a la página de inicio del aplicativo
	 */
	@RequestMapping("/inicio")
	public String inicio(Model model) {
		System.out.println("hello");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_TITLE, VAL_TITLE_INICIO);
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model = agregarNotificacionesVista(model);
		return RSRC_INICIO;
	}

	/**
	 * Método encargado de darnos acceso a la página de inicio del aplicativo
	 */
	@RequestMapping(value = "/inicio/{opcion}", method = RequestMethod.GET)
	public String inicio(@PathVariable(value = "opcion") String opcion, Model model) {
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(opcion));
		model.addAttribute(KEY_TITLE, VAL_TITLE_INICIO);
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model = agregarNotificacionesVista(model);
		return RSRC_INICIO;
	}

	/**
	 * Método encargado de darnos acceso a la página de inicio del aplicativo
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "j_username", required = false) String username,
			@RequestParam(value = "j_password", required = false) String password, Model model)
			throws FileNotFoundException, IOException {

		// XXXXXXXXXXXXXXXXXXXXX Spring Security XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		try {
			if (securityService.authenticate(username, password)) {
				// if (true) {
				LoginExternoDto dtoTemporal = new LoginExternoDto();
				dtoTemporal.setUsername(username);
				dtoTemporal.setNombre(username);
				dtoTemporal.setDescripcion("Acceso Directorio Activo: " + username + " - login Externo: " + username);
				dtoTemporal.setFechaAcceso(new Date());
				
				RespuestaLogin respuestaLogin = null;
				String mensajeCreacionUser = null;

				respuestaLogin = centroControlExchange.loguinUsuario(username,psswrdContenedora,fuenteRegistroContenedora);
				
				if (respuestaLogin == null) {
					mensajeCreacionUser = centroControlExchange.createUpdateUsuario(
							null,username,psswrdContenedora,fuenteRegistroContenedora,true,"2");					
					respuestaLogin = centroControlExchange.loguinUsuario(username,psswrdContenedora,fuenteRegistroContenedora);
				}
				
				dtoTemporal.setToken(respuestaLogin.getToken());
				loginExternoService.registrarLoginExterno(dtoTemporal);
				System.out.print("Usuario: " + username + "  TokenAcceso: " + respuestaLogin.getToken());
				
				model.addAttribute("mensajeCreacionUser", mensajeCreacionUser);
				model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
				model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
				model.addAttribute(KEY_TITLE, VAL_TITLE_INICIO);
				model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

				model = agregarNotificacionesVista(model);
				return RSRC_INICIO;
			} else {
				String msgErrorLogin = "Usuario o contraseña incorrectos";
				model.addAttribute(KEY_TITLE, VAL_TITLE_LOGIN);
				model.addAttribute(KEY_LOGIN_ERROR, Boolean.TRUE);
				model.addAttribute(KEY_LOGIN_ERROR_MESSAGE, msgErrorLogin);
				return RSRC_LOGIN;
			}
		} catch (Exception e) {
			String msgExLogin = "Error en el Proceso";
			System.out.println("Error >>> " + e);
			model.addAttribute(KEY_TITLE, VAL_TITLE_LOGIN);
			model.addAttribute(KEY_LOGIN_ERROR, true);
			model.addAttribute(KEY_LOGIN_ERROR_MESSAGE, msgExLogin);
			return RSRC_LOGIN;
		}
	}

	/**
	 * Método encargado de validar el registro de un usuario
	 */
	@RequestMapping(value = "/validar-registro", method = RequestMethod.GET)
	public String validarRegistro(@RequestParam(value = "user") String idUsuario,
			@RequestParam(value = "token") String token, Model model) throws FileNotFoundException, IOException {

		String mensaje = "";
		if (centroControlExchange.validarRegistro(idUsuario, token)) {
			mensaje = "Su cuenta ha sido activada exitosamente";
		} else {
			mensaje = "Su cuenta no pudo ser activada";
		}
		model.addAttribute("mensaje", mensaje);
		model.addAttribute(KEY_TITLE, "Validacion registro");
		return "area247/validar-registro";
	}

	public Model agregarNotificacionesVista(Model model) {
		/*
		 * Long idUsuario = CentroControlContextHolder.getIdUsuarioSesion();
		 * System.out.println("El id del usuario es: " + idUsuario);
		 */

		Aplicacion aplicacion = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaIni = "2000-01-01";
		String fechaFin = dateFormat.format(new Date()); 

		int cantVigPen = centroControlExchange.getCantidadVigiaByParametros(fechaIni,fechaFin,null,null,"PENDIENTE",false);

		model.addAttribute("newDenun", cantVigPen);				
		model.addAttribute("showNewDenun", true);

		aplicacion = centroControlExchange.obtenerDetalleAplicacion("2");
		if (aplicacion != null) {
			model.addAttribute("rutaIconoVigia", aplicacion.getIcono().getRutaLogo());
			model.addAttribute("colorFondoVigia", aplicacion.getCodigoColor());
			model.addAttribute("nombreVigia", aplicacion.getNombre());
		}
				
		String idApp = "3", listIdTipoCapa = "5,8"; // 5,8 : Subcapas,Avistamiento
		List<CapaSubmenu> capas = centroControlExchange.getCapasByIdAplTipoCapa(idApp, listIdTipoCapa);
		String filtroCapasEspadis = centroControlExchange.getFiltroPorCapasEstadisticas(capas);
		String filtroCategEspadis = centroControlExchange.getFiltroPorCategEstadisticas(capas);		
		int cantAvistPen = centroControlExchange.getTotalAvistamientoEstadisticas(
				fechaIni,fechaFin,filtroCapasEspadis,filtroCategEspadis,true);
		
		model.addAttribute("newAvist", cantAvistPen);				
		model.addAttribute("showNewAvist", true);

		aplicacion = centroControlExchange.obtenerDetalleAplicacion("3");
		if (aplicacion != null) {
			model.addAttribute("rutaIconoAvistamiento", aplicacion.getIcono().getRutaLogo());
			model.addAttribute("colorFondoAvistamiento", aplicacion.getCodigoColor());
			model.addAttribute("nombreAvistamiento", aplicacion.getNombre());
		}
		return model;
	}

	public Model prepararPermisosUsuario(Model model, String idObjetoVista) {
		/*
		 * Long idUsuario = CentroControlContextHolder.getIdUsuarioSesion();
		 * PermisoUsuarioVista permisoUsuario =
		 * centroControlExchange.obtenerPermisoObjetoPorUsuario(idUsuario,idObjetoVista)
		 * ;
		 * 
		 * model.addAttribute("puedeAdicionar",permisoUsuario.getPuedeAdicionar());
		 * model.addAttribute("puedeEditar",permisoUsuario.getPuedeEditar());
		 * model.addAttribute("puedeBorrar",permisoUsuario.getPuedeBorrar());
		 * model.addAttribute("puedeConsultar",permisoUsuario.getPuedeConsultar());
		 */

		model.addAttribute("puedeAdicionar", true);
		model.addAttribute("puedeEditar", true);
		model.addAttribute("puedeBorrar", true);
		model.addAttribute("puedeConsultar", true);

		return model;
	}

	/**
	 * Método encargado de redirigirnos a la pagina de Login, en caso de que el
	 * usuario sea incorrecto
	 */
	@RequestMapping("/loginError")
	public String loginError(Model model) {
		model.addAttribute(KEY_TITLE, VAL_TITLE_LOGIN);
		model.addAttribute(KEY_LOGIN_ERROR, Boolean.TRUE);
		model.addAttribute(KEY_LOGIN_ERROR_MESSAGE, "Error en el Proceso de Logueo");
		return RSRC_LOGIN;
	}

	/**
	 * Método encargado de redirigirnos a la pagina de Login, en caso de que nos
	 * deslogiemos
	 */
	@RequestMapping("/logout")
	public String logout(Model model) {
		SecurityContextHolder.clearContext();
		model.addAttribute(KEY_TITLE, VAL_TITLE_LOGIN);
		model.addAttribute(KEY_LOGIN_ERROR, true);
		model.addAttribute(KEY_LOGIN_ERROR_MESSAGE, "Usuario deslogueado");
		return RSRC_LOGIN;
	}

	// ______________________________________________Administrar
	// Aplicaciones____________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * aplicaciones, editarlas y crear nuevas
	 */
	@RequestMapping("/adminAplicaciones")
	public String adminAplicaciones(Model model) {
		String keyMensajeFormulario = "mensajeFormulario1";
		String keyDespliegeModalAplicacion = "despliegeModalAplicacion";
		String valTitle = "Administrar Aplicaciones";
		String espacio = " ";
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute(keyMensajeFormulario, espacio);
		model.addAttribute(keyDespliegeModalAplicacion, VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, valTitle);
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("nombresList", centroControlExchange.obtenerListNombreAplicaciones());
		model.addAttribute("aplicacionesList", centroControlExchange.obtenerAplicaciones());
		model = agregarNotificacionesVista(model);
		model = prepararPermisosUsuario(model, "1");
		return "area247/adminAplicaciones/adminAplicaciones";
	}

	/**
	 * Método encargado de consultar una aplicacion con base en un identificador
	 */
	@RequestMapping(value = "/getAplicacion/{idAplicacion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Aplicacion getAplicacion(@PathVariable(value = "idAplicacion") String idAplicacion) {
		return centroControlExchange.obtenerDetalleAplicacion(idAplicacion);
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de aplicaciones al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/updateAplicacion", method = RequestMethod.POST)
	public String createUpdateAplicacion(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "activo", required = false) boolean activo,
			@RequestParam(name = "codigoColor", required = false) String codigoColor,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "recomendacion", required = false) String recomendacion,
			@RequestParam(name = "idRecomendacion", required = false) String idRecomendacion,
			@RequestParam(name = "radioAccion", required = false) String radioAccion,
			@RequestParam(name = "icono", required = false) MultipartFile icono, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.updateAplicacion(id, nombre, activo, codigoColor, descripcion,
				recomendacion, idRecomendacion, radioAccion, icono);
		model.addAttribute("validacionEdicion", "");
		String msgFormulario = "mensajeFormulario1";

		String keyDespliegueModalApp = "despliegeModalAplicacion";
		String valTitle = "Administrar Aplicaciones";
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute(msgFormulario, respuesta);
		model.addAttribute(keyDespliegueModalApp, VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, valTitle);
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("nombresList", centroControlExchange.obtenerListNombreAplicaciones());
		model.addAttribute("aplicacionesList", centroControlExchange.obtenerAplicaciones());
		model = agregarNotificacionesVista(model);
		model = prepararPermisosUsuario(model, "1");
		return "area247/adminAplicaciones/adminAplicaciones";
	}

	// ___________________Administracion de Capas_____________________________

	/** Método encargado de consultar una capa con base en un identificador */
	@RequestMapping(value = "/getCapa/{idCapa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Capa getCapa(@PathVariable(value = "idCapa") String idCapa) {
		Capa capa = centroControlExchange.obtenerDetalleCapa(idCapa);
		return capa;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de capas al respectivo servicio web encargado de procesarlas y luego
	 * retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateCapa", method = RequestMethod.POST)
	public String createUpdateCapa(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "activo", required = false) boolean activo,
			@RequestParam(name = "favorito", required = false) boolean favorito,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "iconoMar", required = false) MultipartFile iconoMar,
			@RequestParam(name = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@RequestParam(name = "tipo") String tipoCapa, 
			@RequestParam(value = "urlRecurso", required = false) String urlRecurso,
			@RequestParam(value = "aliasNombre", required = false) String aliasNombre,
			@RequestParam(value = "aliasMunicipio", required = false) String aliasMunicipio,
			@RequestParam(value = "aliasDescripcion", required = false) String aliasDescripcion,
			@RequestParam(value = "aliasCategoria", required = false) String aliasCategoria,
			@RequestParam(value = "aliasImagen", required = false) String aliasImagen,
			@RequestParam(value = "aliasDireccion", required = false) String aliasDireccion,
			@RequestParam(name = "fichaCaracterizacion", required = false) boolean fichaCaracterizacion,
			@RequestParam(name = "poligono", required = false) boolean poligono,
			@RequestParam(name = "tieneHistoria", required = false) boolean tieneHistoria, 
			Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateCapa(id,nombre,activo,favorito,descripcion,iconoMar,
				icono,idAplicacionDeCapa,tipoCapa,urlRecurso,aliasNombre,aliasMunicipio,aliasDescripcion,
				aliasCategoria,aliasImagen,aliasDireccion,fichaCaracterizacion,poligono,tieneHistoria);
		model.addAttribute("mensajeFormulario2",respuesta);
		model = prepararParametrosVistaCapa(model, idAplicacionDeCapa);
		model = agregarNotificacionesVista(model);

		return "area247/adminCapas/adminCapas";
	}

	/**
	 * Método encargado de devolvernos un listado de las capas correspondientes a
	 * una aplicacion
	 */
	@RequestMapping(value = "/getCapasByIdAplicacion/{idAplicacion}", 
			        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCapasByIdAplicacion(
			@PathVariable(value="idAplicacion") String idAplicacion, 
			Model model) {
		model.addAttribute("mensajeFormulario2", " ");
		model = prepararParametrosVistaCapa(model, idAplicacion);
		model = agregarNotificacionesVista(model);

		return "area247/adminCapas/adminCapas";
	}

	/**
	 * Método encargado de eliminar una capa con base en un identificador.
	 */
	@RequestMapping(value = "/deleteCapa/{idAplicacionDeCapa}/{idCapa}", 
			        method = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteCapa(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapa") String idCapa, 
			Model model) {
		String respuesta = centroControlExchange.deleteCapa(idCapa);
		model.addAttribute("mensajeFormulario2", respuesta);
		model = prepararParametrosVistaCapa(model, idAplicacionDeCapa);
		model = agregarNotificacionesVista(model);

		return "area247/adminCapas/adminCapas";
	}

	/**
	 * Método encargado de ejecutar una tarea programada que se ejecutara cada
	 * cierto tiempo la cual permitira actualizar los marcadores correspondientes a
	 * una capa
	 */
	@RequestMapping(value = "/programTaskCapa", method = RequestMethod.POST)
	public String programarTareaCapa(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre") String nombre,
			@RequestParam(name = "idAplicacionDeCapa") String idAplicacionDeCapa,

			@RequestParam(name = "horaIni", required = false) String horaIni,
			@RequestParam(name = "intervalo", required = false) String intervalo,
			@RequestParam(name = "unidad", required = false) String unidad, 
			Model model) throws FileNotFoundException, IOException {
		LocalDateTime fechaIni = null;
		String respuesta = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			fechaIni = LocalDateTime.parse(horaIni, formatter);
			respuesta = centroControlExchange.programarTarea(id, "CAPA", fechaIni, intervalo, unidad, nombre,
					idAplicacionDeCapa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("mensajeFormulario2", respuesta);
		model = prepararParametrosVistaCapa(model, idAplicacionDeCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminCapas/adminCapas";
	}

	/**
	 * Método encargado de correr manualmente una tarea la cual permitira actualizar
	 * los marcadores correspondientes a una capa
	 */
	@RequestMapping(value = "/runTaskCapa/{idAplicacionDeCapa}/{idCapa}", 
			        method = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public String correrTareaCapa(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapa") String idCapa, 
			Model model) {

		String respuesta = centroControlExchange.correrTarea("CAPA", idCapa, idAplicacionDeCapa);
		model.addAttribute("mensajeFormulario2", respuesta);
		model = prepararParametrosVistaCapa(model, idAplicacionDeCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminCapas/adminCapas";
	}

	/**
	 * Método encargado de detener una tarea la cual permitira actualizar los
	 * marcadores correspondientes a una capa
	 */
	@RequestMapping(value = "/stopTaskCapa/{idAplicacionDeCapa}/{idCapa}", 
			        method = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public String detenerTareaCapa(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapa") String idCapa, 
			Model model) {

		String respuesta = centroControlExchange.detenerTarea("CAPA", idCapa);
		model.addAttribute("mensajeFormulario2", respuesta);
		model = prepararParametrosVistaCapa(model, idAplicacionDeCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminCapas/adminCapas";
	}

	public Model prepararParametrosVistaCapa(Model model, String idAplicacionDeCapa) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");

		model.addAttribute("despliegeModalCapas", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Capas");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("capaList", centroControlExchange.getCapasByIdAplicacion(idAplicacionDeCapa));
		model.addAttribute("tipoCapaList", centroControlExchange.obtenerTipoCapasPorAplicacion(idAplicacionDeCapa));
		model.addAttribute("idAplicacionDeCapa", idAplicacionDeCapa);
		model.addAttribute("nombresList", centroControlExchange.obtenerListNombreCapas());
		model.addAttribute("urlsList", centroControlExchange.obtenerListUrlsCapas());

		Aplicacion aplicacion = centroControlExchange.obtenerDetalleAplicacion(idAplicacionDeCapa);
		model.addAttribute("rutaMostrada", " / " + aplicacion.getNombre());
		model.addAttribute("colorAplicacion", aplicacion.getCodigoColor());

		if (idAplicacionDeCapa.equals("3") || idAplicacionDeCapa.equals("8")) {
			model.addAttribute("avistamiento", VAL_DESPLIEGUE_BLOCK);
			model.addAttribute("noavistamiento", VAL_DESPLIEGUE_NONE);
		} else {
			model.addAttribute("avistamiento", VAL_DESPLIEGUE_NONE);
			model.addAttribute("noavistamiento", VAL_DESPLIEGUE_BLOCK);
		}

		if (idAplicacionDeCapa.equals("4")) {
			model.addAttribute("ordenamiento", VAL_DESPLIEGUE_BLOCK);
		} else {
			model.addAttribute("ordenamiento", VAL_DESPLIEGUE_NONE);
		}

		if ((idAplicacionDeCapa.equals("5")) || (idAplicacionDeCapa.equals("4")) || (idAplicacionDeCapa.equals("6"))) {
			model.addAttribute("despliegueUrl", VAL_DESPLIEGUE_BLOCK);
		} else {
			model.addAttribute("despliegueUrl", VAL_DESPLIEGUE_NONE);
		}
		
		if (idAplicacionDeCapa.equals("5")) {
			model.addAttribute("mostrarIconMarker", VAL_DESPLIEGUE_NONE);			
		} else {
			model.addAttribute("mostrarIconMarker", VAL_DESPLIEGUE_BLOCK);
		}
		
		model = prepararPermisosUsuario(model, "2");

		return model;
	}

	// _____________________________Administracion de Categorias___________________________________

	/**
	 * Método encargado de consultar una categoria con base en un identificador
	 */
	@RequestMapping(value = "/getCategoria/{idCategoria}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Categoria getCategoria(@PathVariable(value = "idCategoria") String idCategoria) {
		Categoria categoria = centroControlExchange.obtenerCategoria(idCategoria);
		return categoria;
	}

	/**
	 * Método encargado de devolvernos un listado de las categorias correspondientes
	 * a una capa
	 */
	@RequestMapping(value = "/getCategoriasListByIdCapa/{idCapa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Categoria> getListCategoriasByIdCapa(@PathVariable(value = "idCapa") String idCapa) {
		List<Categoria> categorias = centroControlExchange.getCategoriasByIdCapa(idCapa);
		return categorias;
	}

	/**
	 * Método encargado de devolvernos un listado de las categorias correspondientes
	 * a una capa y a un tipo de categoria
	 */
	@RequestMapping(value = "/getCategoriasByIdTipoCateg/{idCapa}/{tiposCateg}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CategoriaSubmenu> getAutoridadesList(@PathVariable(value = "idCapa") String idCapa,
			@PathVariable(value = "tiposCateg") String listaTipoCateg) {
		List<CategoriaSubmenu> listCateg = centroControlExchange.getCategoriasByIdTipoCateg(idCapa, listaTipoCateg);
		return listCateg;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de categorias al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateCategoria", method = RequestMethod.POST)
	public String createUpdateCategoria(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "icono", required = false) MultipartFile icono,
			@RequestParam(name = "iconoMar", required = false) MultipartFile iconoMar,
			@RequestParam(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@RequestParam(value = "idCapaDeCategoria") String idCapaDeCategoria,
			@RequestParam(value = "urlRecurso", required = false) String urlRecurso,
			@RequestParam(value = "aliasNombre", required = false) String aliasNombre,
			@RequestParam(value = "aliasMunicipio", required = false) String aliasMunicipio,
			@RequestParam(value = "aliasDescripcion", required = false) String aliasDescripcion,
			@RequestParam(value = "aliasImagen", required = false) String aliasImagen,
			@RequestParam(value = "aliasDireccion", required = false) String aliasDireccion,
			@RequestParam(name = "tipo") String tipoCategoria,
			@RequestParam(name = "fichaCaracterizacion", required = false) boolean fichaCaracterizacion,
			@RequestParam(name = "respuestaFichaCarac", required = false) String respuestaFichaCarac,
			@RequestParam(name = "poligono", required = false) boolean poligono,
			@RequestParam(name = "msgOrdenamiento", required = false) boolean msgOrdenamiento,
			@RequestParam(name = "tieneHistoria", required = false) boolean tieneHistoria,

			@RequestParam(name = "iconoPen", required = false) MultipartFile iconoPen,
			@RequestParam(name = "iconoApr", required = false) MultipartFile iconoApr,
			@RequestParam(name = "iconoRec", required = false) MultipartFile iconoRec, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateCategoria(id, nombre, descripcion, icono, iconoMar,
				idCapaDeCategoria, urlRecurso, aliasNombre, aliasMunicipio, aliasDescripcion, aliasImagen, aliasDireccion, 
				tipoCategoria, fichaCaracterizacion, respuestaFichaCarac, poligono, msgOrdenamiento, tieneHistoria, 
				iconoPen, iconoApr, iconoRec);
		model.addAttribute("mensajeFormulario3", respuesta);
		model = prepararParametrosVistaCategoria(model, idAplicacionDeCapa, idCapaDeCategoria);
		model = agregarNotificacionesVista(model);
		return "area247/adminCategorias/adminCategorias";
	}

	/**
	 * Método encargado de devolvernos un listado de las categorias correspondientes
	 * a una capa
	 */
	@RequestMapping(value = "/getCategoriasByIdCapa/{idAplicacionDeCapa}/{idCapa}", 
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCategoriasByIdCapa(
			@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapa") String idCapa, 
			Model model) {
		model.addAttribute("mensajeFormulario3", " ");
		model = prepararParametrosVistaCategoria(model, idAplicacionDeCapa, idCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminCategorias/adminCategorias";
	}

	/**
	 * Método encargado de eliminar una categoria con base en un identificador.
	 */
	@RequestMapping(value = "/deleteCategoria/{idAplicacionDeCapa}/{idCapaDeCategoria}/{idCategoria}", 
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteCategoria(
			@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapaDeCategoria") String idCapaDeCategoria,
			@PathVariable(value = "idCategoria") String idCategoria, 
			Model model) {
		String respuesta = centroControlExchange.deleteCategoria(idCategoria);
		model.addAttribute("mensajeFormulario3", respuesta);
		model = prepararParametrosVistaCategoria(model, idAplicacionDeCapa, idCapaDeCategoria);
		model = agregarNotificacionesVista(model);
		return "area247/adminCategorias/adminCategorias";
	}

	/**
	 * Método encargado de ejecutar una tarea programada que se ejecutara cada
	 * cierto tiempo la cual permitira actualizar los marcadores correspondientes a
	 * una categoria
	 */
	@RequestMapping(value = "/programTaskCateg", method = RequestMethod.POST)
	public String programarTarea(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre") String nombre,
			@RequestParam(name = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@RequestParam(name = "idCapaDeCategoria") String idCapaDeCategoria,

			@RequestParam(name = "horaIni", required = false) String horaIni,
			@RequestParam(name = "intervalo", required = false) String intervalo,
			@RequestParam(name = "unidad", required = false) String unidad, Model model)
			throws FileNotFoundException, IOException {
		LocalDateTime fechaIni = null;
		String respuesta = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			fechaIni = LocalDateTime.parse(horaIni, formatter);
			respuesta = centroControlExchange.programarTarea(id, "CATEGORIA", fechaIni, intervalo, unidad, nombre,
					idAplicacionDeCapa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("mensajeFormulario3", respuesta);
		model = prepararParametrosVistaCategoria(model, idAplicacionDeCapa, idCapaDeCategoria);
		model = agregarNotificacionesVista(model);
		return "area247/adminCategorias/adminCategorias";
	}

	/**
	 * Método encargado de correr manualmente una tarea la cual permitira actualizar
	 * los marcadores correspondientes a una categoria
	 */
	@RequestMapping(value = "/runTaskCateg/{idAplicacionDeCapa}/{idCapaDeCategoria}/{idCategoria}", 
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String correrTarea(
			@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapaDeCategoria") String idCapaDeCategoria,
			@PathVariable(value = "idCategoria") String idCategoria, 
			Model model) {
		String respuesta = centroControlExchange.correrTarea("CATEGORIA", idCategoria, idAplicacionDeCapa);
		model.addAttribute("mensajeFormulario3", respuesta);
		model = prepararParametrosVistaCategoria(model, idAplicacionDeCapa, idCapaDeCategoria);
		model = agregarNotificacionesVista(model);
		return "area247/adminCategorias/adminCategorias";
	}

	/**
	 * Método encargado de detener una tarea la cual permitira actualizar los
	 * marcadores correspondientes a una categoria
	 */
	@RequestMapping(value = "/stopTaskCateg/{idAplicacionDeCapa}/{idCapaDeCategoria}/{idCategoria}", 
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String detenerTarea(
			@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapaDeCategoria") String idCapaDeCategoria,
			@PathVariable(value = "idCategoria") String idCategoria, 
			Model model) {
		String respuesta = centroControlExchange.detenerTarea("CATEGORIA", idCategoria);
		model.addAttribute("mensajeFormulario3", respuesta);
		model = prepararParametrosVistaCategoria(model, idAplicacionDeCapa, idCapaDeCategoria);
		model = agregarNotificacionesVista(model);
		return "area247/adminCategorias/adminCategorias";
	}

	Model prepararParametrosVistaCategoria(Model model, String idAplicacionDeCapa, String idCapaDeCategoria) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");

		model.addAttribute("despliegeModalCateg", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Categorias");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("categoriaList", centroControlExchange.getCategoriasByIdCapa(idCapaDeCategoria));
		model.addAttribute("tipoCapaList", centroControlExchange.obtenerTipoCapasParaCategorias(idAplicacionDeCapa));
		model.addAttribute("idAplicacionDeCapa", idAplicacionDeCapa);
		model.addAttribute("idCapaDeCategoria", idCapaDeCategoria);
		model.addAttribute("nombresList", centroControlExchange.obtenerListNombreCategorias());
		model.addAttribute("urlsList", centroControlExchange.obtenerListUrlsCategorias());

		model.addAttribute("rutaMostrada",
				" / " + (centroControlExchange.obtenerDetalleAplicacion(idAplicacionDeCapa)).getNombre() + " / "
						+ (centroControlExchange.obtenerDetalleCapa(idCapaDeCategoria)).getNombre());

		if (idAplicacionDeCapa.equals("4")) {
			model.addAttribute("ordenamiento", VAL_DESPLIEGUE_BLOCK);
		} else {
			model.addAttribute("ordenamiento", VAL_DESPLIEGUE_NONE);
		}

		if (idAplicacionDeCapa.equals("3") || idAplicacionDeCapa.equals("8")) {
			model.addAttribute("avistamiento", VAL_DESPLIEGUE_BLOCK);
			model.addAttribute("noavistamiento", VAL_DESPLIEGUE_NONE);
		} else {
			model.addAttribute("avistamiento", VAL_DESPLIEGUE_NONE);
			model.addAttribute("noavistamiento", VAL_DESPLIEGUE_BLOCK);
		}

		if (idAplicacionDeCapa.equals("4")) {
			model.addAttribute("despliegueUrl", VAL_DESPLIEGUE_BLOCK);
		} else {
			model.addAttribute("despliegueUrl", VAL_DESPLIEGUE_NONE);
		}

		model = prepararPermisosUsuario(model, "3");

		return model;
	}

	// ____________________________________Administrar
	// Roles___________________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar los
	 * roles, editarlos y crear nuevos
	 */
	@RequestMapping("/adminRoles")
	public String adminRoles(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormularioRol", " ");
		model.addAttribute("despliegeModalRoles", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Roles");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("rolList", centroControlExchange.obtenerRoles());
		model = agregarNotificacionesVista(model);
		model = prepararPermisosUsuario(model, "10");
		return "area247/adminRoles/adminRoles";
	}

	/** Método encargado de consultar un rol con base en un identificador */
	@RequestMapping(value = "/getRol/{idRol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Rol getRol(@PathVariable(value = "idRol") String idRol) {
		Rol rol = centroControlExchange.obtenerDetalleRol(idRol);
		return rol;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de roles al respectivo servicio web encargado de procesarlos y luego
	 * retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateRol", method = RequestMethod.POST)
	public String createUpdateRol(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "activo", required = false) String activo, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateRol(id, nombre, descripcion, activo);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("mensajeFormularioRol", respuesta);

		model.addAttribute("despliegeModalRoles", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Roles");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("rolList", centroControlExchange.obtenerRoles());
		model = agregarNotificacionesVista(model);
		model = prepararPermisosUsuario(model, "10");
		return "area247/adminRoles/adminRoles";
	}

	/**
	 * Método encargado de asignar los permisos de un rol sobre cada objeto.
	 */
	@RequestMapping(value = "/adminPermisosRol/{nombre}/{idRol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String adminPermisosRol(@PathVariable(value = "idRol") String idRol,
			@PathVariable(value = "nombre") String nombre, Model model) {

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("despliegeModalPermisosRoles", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Permisos de Rol");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("idRol", idRol);
		model.addAttribute("nombre", nombre);
		model.addAttribute("permisoWrapper", obtenerPermisosPorRol1(idRol));
		model = agregarNotificacionesVista(model);
		return "area247/adminRoles/adminPermisosRoles";
	}

	@ModelAttribute
	public PermisoRolWrapper obtenerPermisosPorRol1(String idRol) {
		PermisoRolWrapper permisoRolWrapper = new PermisoRolWrapper();
		permisoRolWrapper.setPermisosRol(centroControlExchange.obtenerPermisosPorRol(idRol));
		return permisoRolWrapper;
	}

	/**
	 * Método encargado de editar los permisos que cada rol tiene sobre cada objeto.
	 */
	@RequestMapping(value = "/updatePermisosRol", method = RequestMethod.POST)
	public String updatePermisosRol(@ModelAttribute PermisoRolWrapper permisoRolWrapper, Model model) {
		String respuesta = centroControlExchange.updatePermisosRol(permisoRolWrapper.getPermisosRol());

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormularioRol", respuesta);
		model.addAttribute("despliegeModalRoles", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Roles");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("rolList", centroControlExchange.obtenerRoles());
		model = agregarNotificacionesVista(model);
		return "area247/adminRoles/adminRoles";
		// return "redirect:/adminRoles";
	}

	// ________________________________Administrar
	// Usuarios____________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar los
	 * usuarios y editarlos
	 */
	@RequestMapping("/adminUsuarios")
	public String adminUsuarios(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormularioUsuario", " ");
		model.addAttribute("despliegeModalUsuarios", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Usuarios");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("usuarioList", centroControlExchange.obtenerUsuarios());
		model.addAttribute("rolList", centroControlExchange.obtenerRoles());
		model = agregarNotificacionesVista(model);
		model = prepararPermisosUsuario(model, "9");
		return "area247/adminUsuarios/adminUsuarios";
	}

	/** Método encargado de consultar un usuario con base en un identificador */
	@RequestMapping(value = "/getUsuario/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Usuario getUsuario(@PathVariable(value = "idUsuario") String idUsuario) {
		Usuario usuario = centroControlExchange.obtenerDetalleUsuario(idUsuario);
		return usuario;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de edición de
	 * usuarios al respectivo servicio web encargado de procesarlos y luego
	 * retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/updateUsuario", method = RequestMethod.POST)
	public String createUpdateUsuario(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "activo", required = false) boolean activo,
			@RequestParam(name = "genero", required = false) String genero,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "apellido", required = false) String apellido,
			@RequestParam(name = "nickname", required = false) String nickname,
			@RequestParam(name = "rol", required = false) String idRol, Model model)
			throws FileNotFoundException, IOException {
		String respuesta = centroControlExchange.createUpdateUsuario(
				id,username,psswrdContenedora,fuenteRegistroContenedora,activo,idRol);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormularioUsuario", respuesta);
		model.addAttribute("despliegeModalUsuarios", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Usuarios");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("usuarioList", centroControlExchange.obtenerUsuarios());
		model.addAttribute("rolList", centroControlExchange.obtenerRoles());
		model = agregarNotificacionesVista(model);
		model = prepararPermisosUsuario(model, "9");
		return "area247/adminUsuarios/adminUsuarios";
	}

	/**
	 * Método encargado de asignar los permisos sobre cada objeto a un usuario
	 * determinado.
	 */
	@RequestMapping(value = "/adminPermisosUsuario/{username}/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String adminPermisosUsuario(@PathVariable(value = "idUsuario") String idUsuario,
			@PathVariable(value = "username") String username, Model model) {

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("despliegeModalPermisosUsuarios", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Permisos de Usuario");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("idUsuario", idUsuario);
		model.addAttribute("username", username);
		model.addAttribute("permisoWrapper", obtenerPermisosPorUsuario1(idUsuario));
		model = agregarNotificacionesVista(model);
		return "area247/adminUsuarios/adminPermisosUsuarios";
	}

	@ModelAttribute
	public PermisoWrapper obtenerPermisosPorUsuario1(String idUsuario) {
		PermisoWrapper permisoWrapper = new PermisoWrapper();
		permisoWrapper.setPermisosUsuario(centroControlExchange.obtenerPermisosPorUsuario(idUsuario));
		return permisoWrapper;
	}

	/**
	 * Método encargado de editar los permisos que cada usuario tiene sobre cada
	 * objeto.
	 */
	@RequestMapping(value = "/updatePermisosUsuario", method = RequestMethod.POST)
	public String updatePermisosUsuario(@ModelAttribute PermisoWrapper permisoWrapper, Model model) {
		String respuesta = centroControlExchange.updatePermisosUsuario(permisoWrapper.getPermisosUsuario());
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormularioUsuario", respuesta);
		model.addAttribute("despliegeModalUsuarios", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Usuarios");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("usuarioList", centroControlExchange.obtenerUsuarios());
		model = agregarNotificacionesVista(model);
		return "area247/adminUsuarios/adminUsuarios";
		// return "redirect:/adminUsuarios";
	}

	// _____________________Administrar Arboles de
	// Decision____________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar los
	 * arboles de decision, editarlos y crear nuevos
	 */
	@RequestMapping("/adminArboles")
	public String adminArboles(Model model) {
		model.addAttribute("mensajeFormularioArbol", " ");
		model = prepararParametrosVistaArbol(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminArboles/adminArboles";
	}

	/**
	 * Método encargado de consultar un arbol de decision con base en un
	 * identificador
	 */
	@RequestMapping(value = "/getArbol/{idArbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArbolDecision getArbol(@PathVariable(value = "idArbol") String idArbol) {
		ArbolDecision arbol = centroControlExchange.obtenerDetalleArbol(idArbol);
		return arbol;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de arboles de decision al respectivo servicio web encargado de
	 * procesarlos y luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateArbol", method = RequestMethod.POST)
	public String createUpdateArbol(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "capa", required = false) String idCapa,
			@RequestParam(name = "categoria", required = false) String idCategoria,
			@RequestParam(name = "activo", required = false) Boolean activo, Model model)
			throws FileNotFoundException, IOException {
		String respuesta = centroControlExchange.createUpdateArbol(id, nombre, descripcion, idCapa, idCategoria,
				activo);
		model.addAttribute("mensajeFormularioArbol", respuesta);
		model = prepararParametrosVistaArbol(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminArboles/adminArboles";
	}

	/**
	 * Método encargado de de eliminar un arbol de decision con base en un
	 * identificador
	 */
	@RequestMapping(value = "/deleteArbol/{idArbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteArbol(@PathVariable(value = "idArbol") String idArbol, Model model) {
		String respuesta = centroControlExchange.deleteArbol(idArbol);
		model.addAttribute("mensajeFormularioArbol", respuesta);
		model = prepararParametrosVistaArbol(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminArboles/adminArboles";
	}

	Model prepararParametrosVistaArbol(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("despliegeModalArboles", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar arboles de decision");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("arbolList", centroControlExchange.obtenerArbolesDecision());
		List<CapaSubmenu> capaList = centroControlExchange.getCapasforArboles();
		model.addAttribute("capaList", capaList);
		model.addAttribute("idCapaSelect", capaList.get(0).getId());
		model = prepararPermisosUsuario(model, "4");
		return model;
	}

	// ________________________Administrar Nodos de Arboles de
	// Decision____________________________

	/**
	 * Método encargado de devolvernos un listado de nodos raiz de un arbol de
	 * decisión
	 */
	@RequestMapping(value = "/adminNodosRaizArbol/{idArbol}", method = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public String adminNodosRaizArbol(@PathVariable(value = "idArbol") String idArbol, Model model) {

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("mensajeFormularioNodo", " ");
		model.addAttribute("despliegeModalNodos", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Nodos");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("idArbol", idArbol);

		List<NodoArbol> ListNodos = centroControlExchange.obtenerNodosRaizArbol(idArbol);
		model.addAttribute("nodoList", ListNodos);
		if (ListNodos != null) {
			model.addAttribute("ordenesList", centroControlExchange.obtenerListOrdenesVista(ListNodos));
		}
		List<String> rutaNodos = new ArrayList<String>();
		rutaNodos.add((centroControlExchange.obtenerDetalleArbol(idArbol)).getNombre().replace("/", "-"));
		model.addAttribute("rutaNodos", rutaNodos);
		model.addAttribute("rutaMostrada",
				" / " + (centroControlExchange.obtenerDetalleArbol(idArbol)).getNombre().replace("/", "-"));

		model.addAttribute("enlaceRetorno", "/adminArboles");
		model = agregarNotificacionesVista(model);
		model.addAttribute("autoridadCompetenteList",centroControlExchange.obtenerAutoridades());
		model = prepararPermisosUsuario(model, "8");
		return "area247/adminArboles/adminNodosHijos";
	}

	/** Método encargado de consultar un nodo con base en un identificador */
	@RequestMapping(value = "/getNodo/{idNodo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody NodoArbol getNodo(@PathVariable(value = "idNodo") String idNodo) {
		NodoArbol nodo = centroControlExchange.obtenerDetalleNodo(idNodo);
		return nodo;
	}

	/**
	 * Método encargado de devolvernos un listado de nodos hijos de un nodo padre
	 */
	@RequestMapping(value = "/obtenerNodosByPadre/{rutaNodos}/{idArbol}/{idPadre}", method = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerNodosByPadre(@PathVariable(value = "idArbol") String idArbol,
			@PathVariable(value = "rutaNodos") List<String> rutaNodos, @PathVariable(value = "idPadre") String idPadre,
			Model model) {
		model.addAttribute("mensajeFormularioNodo", " ");

		model = prepararParametrosVistaNodoArbol(model, idArbol, rutaNodos, idPadre, true);
		model = agregarNotificacionesVista(model);
		return "area247/adminArboles/adminNodosHijos";
	}

	/*
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de nodos al respectivo servicio web encargado de procesarlos y luego
	 * retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateNodoArbol", method = RequestMethod.POST)
	public String createUpdateNodoArbol(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "alias", required = false) String alias,
			@RequestParam(name = "instrucciones", required = false) String instrucciones,
			@RequestParam(name = "instruccionesDetalladas", required = false) String instruccionesDetalladas,
			@RequestParam(name = "orden", required = false) String orden,
			@RequestParam(name = "formatoMedia", required = false) String formatoMedia,
			@RequestParam(name = "multimedia", required = false) MultipartFile multimedia,
			@RequestParam(name = "urlMediaVideoAudio", required = false) String urlMediaVideoAudio,
			@RequestParam(name = "idArbol", required = false) String idArbol,
			@RequestParam(name = "idPadre", required = false) String idPadre,
			@RequestParam(name = "rutaNodos", required = false) List<String> rutaNodos,
			@RequestParam(name = "flagHijosDropdown") boolean flagHijosDropdown,
			@RequestParam(name = "pedirMultimedia", required = false) String pedirMultimedia,
			@RequestParam(name = "autoridadCompetente", required = false) String idAutoridadCompetente,			
			@RequestParam(name = "iconoVigiaApr", required = false) MultipartFile iconoVigiaApr,
			@RequestParam(name = "iconoVigiaPen", required = false) MultipartFile iconoVigiaPen,
			@RequestParam(name = "iconoVigiaRec", required = false) MultipartFile iconoVigiaRec,
			@RequestParam(name = "iconoVigiaWin", required = false) MultipartFile iconoVigiaWin,
			Model model) throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateNodoArbol(id,nombre,descripcion,alias,
				instrucciones,instruccionesDetalladas,orden,formatoMedia,multimedia,urlMediaVideoAudio,
				idPadre,idArbol,rutaNodos,flagHijosDropdown,pedirMultimedia,idAutoridadCompetente,
				iconoVigiaApr,iconoVigiaPen,iconoVigiaRec,iconoVigiaWin);
		model.addAttribute("mensajeFormularioNodo", respuesta);
		model = prepararParametrosVistaNodoArbol(model, idArbol, rutaNodos, idPadre, false);
		model = agregarNotificacionesVista(model);
		return "area247/adminArboles/adminNodosHijos";
	}

	/** Método encargado de de eliminar un Nodo con base en un id */
	@RequestMapping(value = "/deleteNodo/{rutaNodos}/{idArbol}/{idPadre}/{idNodo}", method = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteNodo(@PathVariable(value = "rutaNodos") List<String> rutaNodos,
			@PathVariable(value = "idArbol") String idArbol, @PathVariable(value = "idPadre") String idPadre,
			@PathVariable(value = "idNodo") String idNodo, Model model) {				
		String respuesta = centroControlExchange.deleteNodoArbol(idNodo);
		model.addAttribute("mensajeFormularioNodo", respuesta);
		model = prepararParametrosVistaNodoArbol(model, idArbol, rutaNodos, idPadre, false);
		model = agregarNotificacionesVista(model);
		return "area247/adminArboles/adminNodosHijos";
	}

	Model prepararParametrosVistaNodoArbol(Model model, String idArbol, List<String> rutaNodos, String idPadre,
			boolean paginaSig) {
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("despliegeModalNodos", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Nodos");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("idArbol", idArbol);
		model.addAttribute("idPadre", idPadre);
		List<NodoArbol> listNodos = null;

		if (!idPadre.equals("null")) {
			listNodos = centroControlExchange.obtenerNodosByPadre(idPadre);
			NodoArbol nodoPadre = centroControlExchange.obtenerDetalleNodo(idPadre);
			
			if (paginaSig) {
				rutaNodos.add(nodoPadre.getNombre().replace("/", "-"));
			}
			model.addAttribute("rutaNodos", rutaNodos);
			model.addAttribute("rutaMostrada", getRutaMostrada(rutaNodos));
			
			if(nodoPadre.isFlagHijosDropdown()) {
				model.addAttribute("mostrarMultimedia","none");
			}else {
				model.addAttribute("mostrarMultimedia","table-cell");
			}
			
			Long idAbuelo = nodoPadre.getIdPadre();
			model.addAttribute("enlaceRetorno",
					"/obtenerNodosByPadre/" + getRutaAnterior(rutaNodos) + "/" + idArbol + "/" + idAbuelo);
		} else {
			listNodos = centroControlExchange.obtenerNodosRaizArbol(idArbol);

			List<String> rutaNodosRaiz = new ArrayList<String>();
			String nombreArbol = (centroControlExchange.obtenerDetalleArbol(idArbol)).getNombre().replace("/", "-");
			rutaNodosRaiz.add(nombreArbol);
			model.addAttribute("rutaNodos", rutaNodosRaiz);
			model.addAttribute("rutaMostrada", " / " + nombreArbol);			
			model.addAttribute("mostrarMultimedia","table-cell");

			model.addAttribute("enlaceRetorno", "/adminArboles");
		}

		model.addAttribute("nodoList", listNodos);
		if (listNodos != null) {
			model.addAttribute("ordenesList", centroControlExchange.obtenerListOrdenesVista(listNodos));
		}
		model.addAttribute("autoridadCompetenteList",centroControlExchange.obtenerAutoridades());

		model = prepararPermisosUsuario(model, "8");
		return model;
	}

	/**
	 * Método encargado de retornar la ruta de navegación de nodos de la vista
	 * anterior.
	 */
	private List<String> getRutaAnterior(List<String> rutaNodos) {
		List<String> rutaAnterior = new ArrayList<String>();
		for (String nodo : rutaNodos) {
			nodo = nodo.replace("[", "");
			nodo = nodo.replace("]", "");
			rutaAnterior.add(nodo);
		}
		if (rutaAnterior.size() > 2) {
			rutaAnterior.remove(rutaAnterior.size() - 1);
			rutaAnterior.remove(rutaAnterior.size() - 1);
		}
		;
		return rutaAnterior;
	}

	/**
	 * Método encargado de retornar la ruta de navegación de nodos de la vista
	 * actual.
	 */
	private String getRutaMostrada(List<String> rutaNodos) {
		String rutaMostrada = "";
		for (String nodo : rutaNodos) {
			nodo = nodo.replace("[", "");
			nodo = nodo.replace("]", "");
			rutaMostrada = rutaMostrada + " / " + nodo;
		}
		return rutaMostrada;
	}

	// ___________________________Administracion de
	// Mensajes___________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar los
	 * mensajes, editarlos y crear nuevos
	 */
	@RequestMapping("/adminMensajes")
	public String adminMensajes(Model model) {
		model.addAttribute("mensajeFormMensaje", " ");
		model = prepararParametrosVistaMensaje(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminMensajes/adminMensajes";
	}

	/** Método encargado de consultar un mensaje con base en un identificador */
	@RequestMapping(value = "/getMensaje/{idMensaje}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Mensaje getMensaje(@PathVariable(value = "idMensaje") String idMensaje) {
		Mensaje mensaje = centroControlExchange.obtenerMensaje(idMensaje);
		return mensaje;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de mensajes al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateMensaje", method = RequestMethod.POST)
	public String createUpdateMensaje(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "contenido", required = false) String contenido, 
			@RequestParam(name = "titulo", required = false) String titulo, 	
			@RequestParam(name = "nombreIdentificador", required = false) String nombreIdentificador, 
			@RequestParam(name = "uso", required = false) String uso, 
			@RequestParam(name = "idAplicacion", required = false) String idAplicacion, 
			Model model) throws FileNotFoundException, IOException {
		String respuesta = centroControlExchange.createUpdateMensaje(id,contenido,titulo,nombreIdentificador,uso,idAplicacion);
		model.addAttribute("mensajeFormMensaje", respuesta);
		model = prepararParametrosVistaMensaje(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminMensajes/adminMensajes";
	}

	/** Método encargado de eliminar un mensaje con base en un identificador. */
	@RequestMapping(value = "/deleteMensaje/{idMensaje}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteMensaje(@PathVariable(value = "idMensaje") String idMensaje, Model model) {		
		String respuesta = centroControlExchange.deleteMensaje(idMensaje);	
		model.addAttribute("mensajeFormMensaje", respuesta);
		model = prepararParametrosVistaMensaje(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminMensajes/adminMensajes";
	}
	
	
	public Model prepararParametrosVistaMensaje(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");		
		model.addAttribute("despliegeModalMensajes", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("aplicacionList", centroControlExchange.obtenerAplicaciones());
		model.addAttribute("mensajeList", centroControlExchange.obtenerMensajes());
		model.addAttribute(KEY_TITLE, "Administrar Mensajes");
		return model;
	}	
	
	
	/**
	 * Método encargado de realizar el envió de mensajes Push a los celulares
	 */
	@RequestMapping(value = "/mensajePushEnviado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void mensajePushEnviado(@RequestParam("message") String message,
			@RequestParam("recipients") String recipients) throws FileNotFoundException, IOException {

	    centroControlExchange.enviarMensajePush(recipients, null, "Nuevo Mensaje", message);

		/*String usuario = "blackgersain";
		centroControlExchange.enviarMensajePush("Personal",usuario,"Prueba","Prueba");*/
	}

	@RequestMapping(value = "/listMensajesPush", method = RequestMethod.GET)
	public String listMensajesPush(Model model) {
		model.addAttribute("despliegeModalNotificaciones", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("notificacionesPushList", centroControlExchange.listarNotificacionesPush());
		return "fragments/enviarMensajePush :: modalNotificaHisto";
	}

	// _______________________Administrar Formulario_________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar los
	 * formularios, editarlos y crear nuevos
	 */
	@RequestMapping("/adminFormularios")
	public String adminFormularios(Model model) {
		model.addAttribute("mensajeFormFormulario", " ");
		model = prepararParametrosVistaFormulario(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminFormularios/adminFormularios";
	}

	/** Método encargado de obtener un listado de formularios */
	@RequestMapping(value = "/getFormulariosList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Formulario> getFormulariosList() {
		List<Formulario> formulariosList = centroControlExchange.obtenerFormularios();
		return formulariosList;
	}

	/** Método encargado de consultar un formulario con base en un identificador */
	@RequestMapping(value = "/getFormulario/{idFormulario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Formulario getFormulario(@PathVariable(value = "idFormulario") String idFormulario) {
		Formulario formulario = centroControlExchange.obtenerFormulario(idFormulario);
		return formulario;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de Formularios al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateFormulario", method = RequestMethod.POST)
	public String createUpdateFormulario(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "tipoMultimedia", required = false) String tipoMultimedia, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateFormulario(id, nombre, descripcion, tipoMultimedia);
		model.addAttribute("mensajeFormFormulario", respuesta);
		model = prepararParametrosVistaFormulario(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminFormularios/adminFormularios";
	}

	/** Método encargado de eliminar un formulario con base en un identificador */
	@RequestMapping(value = "/deleteFormulario/{idFormulario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteFormulario(@PathVariable(value = "idFormulario") String idFormulario, Model model) {
		String respuesta = centroControlExchange.deleteFormulario(idFormulario);
		model.addAttribute("mensajeFormFormulario", respuesta);
		model = prepararParametrosVistaFormulario(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminFormularios/adminFormularios";
	}

	public Model prepararParametrosVistaFormulario(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("despliegeModalFormularios", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Formularios");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("formularioList", centroControlExchange.obtenerFormularios());
		model = prepararPermisosUsuario(model, "5");
		return model;
	}

	// ______________________________Administrar
	// Pregunta____________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * preguntas, editarlas y crear nuevas.
	 */
	@RequestMapping(value = "/obtenerPreguntasbyIdFormulario/{idFormulario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerPreguntasbyIdFormulario(@PathVariable(value = "idFormulario") String idFormulario,
			Model model) {
		model.addAttribute("mensajeFormPregunta", " ");
		model = prepararParametrosVistaPregunta(model, idFormulario);
		model = agregarNotificacionesVista(model);
		return "area247/adminPreguntas/adminPreguntas";
	}

	/** Método encargado de consultar una pregunta con base en un identificador */
	@RequestMapping(value = "/getPregunta/{idPregunta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Pregunta getPregunta(@PathVariable(value = "idPregunta") String idPregunta) {
		Pregunta pregunta = centroControlExchange.obtenerPregunta(idPregunta);
		return pregunta;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de preguntas al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdatePregunta", method = RequestMethod.POST)
	public String createUpdatePregunta(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "orden", required = false) String orden,
			@RequestParam(name = "tipoPregunta", required = false) String tipoPregunta,
			@RequestParam(name = "idFormulario", required = false) String idFormulario, Model model)
			throws FileNotFoundException, IOException {
		String respuesta = centroControlExchange.createUpdatePregunta(id, descripcion, orden, tipoPregunta,
				idFormulario);
		model.addAttribute("mensajeFormPregunta", respuesta);
		model = prepararParametrosVistaPregunta(model, idFormulario);
		model = agregarNotificacionesVista(model);
		return "area247/adminPreguntas/adminPreguntas";
	}

	/** Método encargado de deliminar una Pregunta con base en un identificador */
	@RequestMapping(value = "/deletePregunta/{idFormulario}/{idPregunta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deletePregunta(@PathVariable(value = "idPregunta") String idPregunta,
			@PathVariable(value = "idFormulario") String idFormulario, Model model) {
		String respuesta = centroControlExchange.deletePregunta(idPregunta);
		model.addAttribute("mensajeFormPregunta", respuesta);
		model = prepararParametrosVistaPregunta(model, idFormulario);
		model = agregarNotificacionesVista(model);
		return "area247/adminPreguntas/adminPreguntas";
	}

	public Model prepararParametrosVistaPregunta(Model model, String idFormulario) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");

		model.addAttribute("despliegeModalPreguntas", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Preguntas");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("idFormulario", idFormulario);
		model.addAttribute("preguntaList", centroControlExchange.obtenerPreguntasbyIdFormulario(idFormulario));
		model.addAttribute("ordenesList", centroControlExchange.obtenerOrdenesPreguntasByFormulario(idFormulario));
		model.addAttribute("rutaMostrada", " / " + (centroControlExchange.obtenerFormulario(idFormulario)).getNombre());
		model = prepararPermisosUsuario(model, "6");
		return model;
	}

	// ______________________________Administrar Opciones de
	// Pregunta____________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * opciones de preguntas, editarlas y crear nuevas.
	 */
	@RequestMapping(value = "/obtenerOpcionesbyIdPregunta/{idFormulario}/{idPregunta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerOpcionesbyIdPregunta(@PathVariable(value = "idFormulario") String idFormulario,
			@PathVariable(value = "idPregunta") String idPregunta, Model model) {
		model.addAttribute("mensajeFormOpcion", " ");
		model = prepararParametrosVistaOpcionesPregunta(model, idFormulario, idPregunta);
		model = agregarNotificacionesVista(model);
		return "area247/adminPreguntas/adminOpcionesPregunta";
	}

	/**
	 * Método encargado de consultar una opcion de pregunta con base en un
	 * identificador
	 */
	@RequestMapping(value = "/getOpcion/{idOpcion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody OpcionPregunta getOpcion(@PathVariable(value = "idOpcion") String idOpcion) {
		OpcionPregunta opcionPregunta = centroControlExchange.obtenerOpcion(idOpcion);
		return opcionPregunta;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de opciones al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateOpcion", method = RequestMethod.POST)
	public String createUpdateOpcion(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "clave", required = false) String clave,
			@RequestParam(name = "valor", required = false) String valor,
			@RequestParam(name = "idPregunta", required = false) String idPregunta,
			@RequestParam(name = "idFormulario", required = false) String idFormulario, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateOpciones(id, clave, valor, idPregunta);
		model.addAttribute("mensajeFormOpcion", respuesta);
		model = prepararParametrosVistaOpcionesPregunta(model, idFormulario, idPregunta);
		model = agregarNotificacionesVista(model);
		return "area247/adminPreguntas/adminOpcionesPregunta";
	}

	/** Método encargado de deliminar una Pregunta con base en un identificador */
	@RequestMapping(value = "/deletePregunta/{idFormulario}/{idPregunta}/{idOpcion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deletePregunta(@PathVariable(value = "idOpcion") String idOpcion,
			@PathVariable(value = "idFormulario") String idFormulario,
			@PathVariable(value = "idPregunta") String idPregunta, Model model) {
		String respuesta = centroControlExchange.deleteOpcion(idOpcion);
		model.addAttribute("mensajeFormOpcion", respuesta);
		model = prepararParametrosVistaOpcionesPregunta(model, idFormulario, idPregunta);
		model = agregarNotificacionesVista(model);
		return "area247/adminPreguntas/adminOpcionesPregunta";
	}

	public Model prepararParametrosVistaOpcionesPregunta(Model model, String idFormulario, String idPregunta) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("despliegeModalOpciones", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Opciones");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		List<OpcionPregunta> listOpciones = centroControlExchange.obtenerOpcionesbyIdPregunta(idPregunta);
		model.addAttribute("opcionList", listOpciones);
		if (listOpciones != null) {
			model.addAttribute("clavesList", centroControlExchange.obtenerListClavesVista(listOpciones));
		}
		model.addAttribute("idFormulario", idFormulario);
		model.addAttribute("idPregunta", idPregunta);
		model.addAttribute("rutaMostrada", " / " + (centroControlExchange.obtenerFormulario(idFormulario)).getNombre()
				+ " / " + (centroControlExchange.obtenerPregunta(idPregunta)).getDescripcion());

		model = prepararPermisosUsuario(model, "7");
		return model;
	}

	// ___________________________Administracion de
	// Mediciones________________________________________

	/**
	 * Método encargado de devolvernos un listado de las mediciones correspondientes
	 * a una capa
	 */
	@RequestMapping(value = "/getMedicionesByIdCapa/{idAplicacionDeCapa}/{idCapa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerMedicionesByIdCapa(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapa") String idCapa, Model model) {
		model.addAttribute("mensajeFormMedicion", " ");
		model = prepararParametrosVistaMedicion(model, idAplicacionDeCapa, idCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminMediciones/adminMediciones";
	}

	/** Método encargado de consultar una medicion con base en un identificador */
	@RequestMapping(value = "/getMedicion/{idMedicion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Medicion getMedicion(@PathVariable(value = "idMedicion") String idMedicion) {
		Medicion medicion = centroControlExchange.obtenerDetalleMedicion(idMedicion);
		return medicion;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de capas al respectivo servicio web encargado de procesarlas y luego
	 * retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateMedicion", method = RequestMethod.POST)
	public String createUpdateMedicion(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "color", required = false) String color,
			@RequestParam(name = "significado", required = false) String significado,
			@RequestParam(name = "recomendacion", required = false) String recomendacion,
			@RequestParam(name = "escalaInicial", required = false) String escalaInicial,
			@RequestParam(name = "escalaFinal", required = false) String escalaFinal,
			@RequestParam(name = "idCapaDeMedicion", required = false) String idCapa,
			@RequestParam(name = "idAplicacionDeCapa", required = false) String idAplicacionDeCapa,
			@RequestParam(name = "icono", required = false) MultipartFile icono, Model model)
			throws FileNotFoundException, IOException {
		String respuesta = centroControlExchange.createUpdateMedicion(id, nombre, descripcion, color, significado,
				recomendacion, escalaInicial, escalaFinal, idCapa, icono);
		model.addAttribute("mensajeFormMedicion",respuesta);
		model = prepararParametrosVistaMedicion(model, idAplicacionDeCapa, idCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminMediciones/adminMediciones";
	}

	/** Método encargado de consultar una medicion con base en un identificador */
	@RequestMapping(value = "/verificarsolapamiento/{idCapa}/{escalaInicial}/{escalaFinal}/{idMedicion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean verificarSolapamiento(@PathVariable(value = "idMedicion") Long idMedicion,
			@PathVariable(value = "idCapa") Long idCapa, @PathVariable(value = "escalaInicial") String escalaInicial,
			@PathVariable(value = "escalaFinal") String escalaFinal) {
		return centroControlExchange.verificarSolapamiento(idMedicion, idCapa, escalaInicial, escalaFinal);
	}

	/**
	 * Método encargado de eliminar una medicion con base en un identificador.
	 */
	@RequestMapping(value = "/deleteMedicion/{idAplicacionDeCapa}/{idCapaDeMedicion}/{idMedicion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteMedicion(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapaDeMedicion") String idCapaDeMedicion,
			@PathVariable(value = "idMedicion") String idMedicion, Model model) {
		String respuesta = centroControlExchange.deleteMedicion(idMedicion);
		model.addAttribute("mensajeFormMedicion", respuesta);
		model = prepararParametrosVistaMedicion(model, idAplicacionDeCapa, idCapaDeMedicion);
		model = agregarNotificacionesVista(model);
		return "area247/adminMediciones/adminMediciones";
	}

	public Model prepararParametrosVistaMedicion(Model model, String idAplicacionDeCapa, String idCapa) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("despliegeModalMedicion", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Mediciones");
		model.addAttribute("nombresList", centroControlExchange.obtenerListNombreMediciones());
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("medicionList", centroControlExchange.getMedicionesByIdCapa(idCapa));
		model.addAttribute("idAplicacionDeCapa", idAplicacionDeCapa);
		model.addAttribute("idCapaDeMedicion", idCapa);
		model.addAttribute("rutaMostrada",
				" / " + (centroControlExchange.obtenerDetalleAplicacion(idAplicacionDeCapa)).getNombre() + " / "
						+ (centroControlExchange.obtenerDetalleCapa(idCapa)).getNombre());
		prepararPermisosUsuario(model,"11");
		return model;
	}

	// _______________________Administrar
	// Autoridad__________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * autoridades, editarlos y crear nuevos
	 */
	@RequestMapping("/adminAutoridades")
	public String adminAutoridad(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormAutoridad", " ");
		model.addAttribute("despliegeModalAutoridades", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Autoridades");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("autoridadList", centroControlExchange.obtenerAutoridades());
		model = agregarNotificacionesVista(model);
		prepararPermisosUsuario(model,"12");
		return "area247/adminAutoridades/adminAutoridades";
	}

	/** Método encargado de devolvernos un listado de los autoridades */
	@RequestMapping(value = "/getAutoridadesList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AutoridadCompetente> getAutoridadesList() {
		List<AutoridadCompetente> autoridadesList = centroControlExchange.obtenerAutoridades();
		return autoridadesList;
	}

	/** Método encargado de consultar una autoridad con base en un identificador */
	@RequestMapping(value = "/getAutoridad/{idAutoridad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AutoridadCompetente getAutoridad(@PathVariable(value = "idAutoridad") String idAutoridad) {
		AutoridadCompetente autoridad = centroControlExchange.obtenerAutoridad(idAutoridad);
		return autoridad;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de autoridades al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateAutoridad", method = RequestMethod.POST)
	public String createUpdateAutoridad(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "direccion", required = false) String direccion,
			@RequestParam(name = "telefono", required = false) String telefono,
			@RequestParam(name = "municipio", required = false) String municipio,
			@RequestParam(name = "correo", required = false) String correo,
			@RequestParam(name = "horario", required = false) String horario, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateAutoridad(id, nombre, direccion, telefono, municipio,
				correo, horario);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("mensajeFormAutoridad", respuesta);

		model.addAttribute("despliegeModalAutoridades", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Autoridades");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("autoridadList", centroControlExchange.obtenerAutoridades());
		model = agregarNotificacionesVista(model);
		prepararPermisosUsuario(model,"12");
		return "area247/adminAutoridades/adminAutoridades";
	}

	/** Método encargado de eliminar una autoridad con base en un identificador */
	@RequestMapping(value = "/deleteAutoridad/{idAutoridad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteAutoridad(@PathVariable(value = "idAutoridad") String idAutoridad, Model model) {

		String respuesta = centroControlExchange.deleteAutoridad(idAutoridad);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormAutoridad", respuesta);
		model.addAttribute("despliegeModalAutoridades", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Formularios");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("autoridadList", centroControlExchange.obtenerAutoridades());
		model = agregarNotificacionesVista(model);
		prepararPermisosUsuario(model,"12");
		return "area247/adminAutoridades/adminAutoridades";
	}

	// __________________________Administrar Avistamientos___________________________

	/*
	 * Método encargado de redireccionarnos a la vista en la cual podemos
	 * administrar los avistamientos
	 */
	@RequestMapping("/adminAvistamientos")
	public String adminAvistamientos(Model model) {
		model.addAttribute("avistamientoCargado", "none");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());

		String idApp = "3";
		String listIdTipoCapa = "5,8"; // 5,8 : Subcapas,Avistamiento
		String colorApp = centroControlExchange.obtenerDetalleAplicacion(idApp).getCodigoColor();

		List<CapaSubmenu> capas = centroControlExchange.getCapasByIdAplTipoCapa(idApp, listIdTipoCapa);
		model.addAttribute("capasNavTabs", capas);
		
		String filtroCapasEspadis = centroControlExchange.getFiltroPorCapasEstadisticas(capas);
		String filtroCategEspadis = centroControlExchange.getFiltroPorCategEstadisticas(capas);		
		model.addAttribute("filtroCapasEspadis",filtroCapasEspadis);
		model.addAttribute("filtroCategEspadis",filtroCategEspadis);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String fechaIni = dateFormat.format(date); //String fechaIni = "2018-11-01";
		String fechaFin = dateFormat.format(date); //String fechaFin = "2018-11-30";
		Long idCapaSel = capas.get(0).getId(); // Long idCapaSel = capas.get(1).getId();

		model.addAttribute("fechaIni", fechaIni);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("idCapaSel", idCapaSel);
		model.addAttribute("idGrafEst","usuario");

		model.addAttribute("idApp", idApp);
		model.addAttribute("listIdTipoCapa", listIdTipoCapa);
		model.addAttribute("colorApp", colorApp);
		model.addAttribute("numColorApp", colorApp.replace("#", ""));		

		EstadisticaAvistamiento cantidades = centroControlExchange.getCantidadesAvistamientoEstadosbyFechaCapa("" + idCapaSel, null,
				fechaIni, fechaFin, false, false);
		model.addAttribute("numApr", cantidades.getCantidadAprobados());
		model.addAttribute("numPen", cantidades.getCantidadPendientes());
		model.addAttribute("numRec", cantidades.getCantidadRechazados());

		model.addAttribute("totalAvist",
				centroControlExchange.getTotalAvistamientoEstadisticas(fechaIni,fechaFin,filtroCapasEspadis,filtroCategEspadis,false));

		model.addAttribute(KEY_TITLE, "Admin Avistamiento");
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		return "area247/adminAvistamientos/adminAvistamientos";
	}
	

	@RequestMapping("/adminFotografias")
	public String adminFotografias(Model model) {
		model.addAttribute("avistamientoCargado", "none");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());

		String idApp = "8";
		String listIdTipoCapa = "5,8"; // 5,8 : Subcapas,Avistamiento
		String colorApp = centroControlExchange.obtenerDetalleAplicacion(idApp).getCodigoColor();

		List<CapaSubmenu> capas = centroControlExchange.getCapasByIdAplTipoCapa(idApp, listIdTipoCapa);
		model.addAttribute("capasNavTabs", capas);
		
		String filtroCapasEspadis = centroControlExchange.getFiltroPorCapasEstadisticas(capas);
		String filtroCategEspadis = centroControlExchange.getFiltroPorCategEstadisticas(capas);		
		model.addAttribute("filtroCapasEspadis",filtroCapasEspadis);
		model.addAttribute("filtroCategEspadis",filtroCategEspadis);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String fechaIni = dateFormat.format(date);
		String fechaFin = dateFormat.format(date);
		Long idCapaSel = capas.get(0).getId();
		model.addAttribute("fechaIni", fechaIni);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("idCapaSel", idCapaSel);
		model.addAttribute("idGrafEst","usuario");

		model.addAttribute("idApp", idApp);
		model.addAttribute("listIdTipoCapa", listIdTipoCapa);
		model.addAttribute("colorApp", colorApp);
		model.addAttribute("numColorApp", colorApp.replace("#", ""));

		EstadisticaAvistamiento cantidades = centroControlExchange.getCantidadesAvistamientoEstadosbyFechaCapa("" + idCapaSel, null,
				fechaIni, fechaFin, false, false);
		model.addAttribute("numApr", cantidades.getCantidadAprobados());
		model.addAttribute("numPen", cantidades.getCantidadPendientes());
		model.addAttribute("numRec", cantidades.getCantidadRechazados());

		model.addAttribute("totalAvist",
				centroControlExchange.getTotalAvistamientoEstadisticas(fechaIni,fechaFin,filtroCapasEspadis,filtroCategEspadis,false));

		model.addAttribute(KEY_TITLE, "Admin Fotografias");
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		return "area247/adminAvistamientos/adminAvistamientos";
	}

	/**
	 * Método encargado de conparan las fechas con las cuales buscamos avistamientos
	 */
	@RequestMapping(value = "/compararFechas/{fechaIni}/{fechaFin}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean compararFechas(@PathVariable(value = "fechaIni") String fechaIni,
			@PathVariable(value = "fechaFin") String fechaFin) {
		Date fechaIniDate = null;
		Date fechaFinDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			fechaIniDate = format.parse(fechaIni);
			fechaFinDate = format.parse(fechaFin);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (fechaIniDate.compareTo(fechaFinDate) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "/getAvistamientosPaginated/{fechaIni}/{fechaFin}/{idApp}/{idCapa}/{idCategoria}"
			              + "/{estadosList}/{soloComHis}/{conComenDeHis}", 
			        method = RequestMethod.GET)
	@ResponseBody
	public String obtenerAvistamientosPaginated(@PathVariable(value = "fechaIni") String fechaIni,
			@PathVariable(value = "fechaFin") String fechaFin, @PathVariable(value = "idApp") String idApp,
			@PathVariable(value = "idCapa") String idCapa, @PathVariable(value = "idCategoria") String idCategoria,
			@PathVariable(value = "estadosList") String estadosList, 
			@PathVariable(value = "soloComHis") boolean soloComHis, 
			@PathVariable(value = "conComenDeHis") boolean conComenDeHis,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		DataTableRequest<Avistamiento> dataTableInRQ = new DataTableRequest<Avistamiento>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

		String whereClause = ((AppUtil.isObjectEmpty(pagination.getFilterByClause())) ? "" : " WHERE ")
				+ pagination.getFilterByClause();
		String orderClause = pagination.getOrderByClause();
		String pageSize = "" + pagination.getPageSize();
		String pageNumber = "" + pagination.getPageNumber();

		List<Avistamiento> listAvistamientos = centroControlExchange.obtenerAvistamientosPaginadosPorFechaCapa(idCapa,
				idCategoria, idApp, fechaIni, fechaFin, whereClause, orderClause, 
				pageSize, pageNumber, estadosList,soloComHis,conComenDeHis);

		DataTableResults<Avistamiento> dataTableResult = new DataTableResults<Avistamiento>();
		dataTableResult
				.setMarkerPrintPackage(centroControlExchange.obtenerMardadoresListAvistamientos(listAvistamientos));
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(listAvistamientos);

		if (!AppUtil.isObjectEmpty(listAvistamientos)) {
			dataTableResult.setRecordsTotal("" + centroControlExchange
					.cantidadAvistamientosPaginadosPorFechaCapa(idCapa, idCategoria, fechaIni, fechaFin, 
							estadosList,soloComHis,conComenDeHis));

			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered("" + dataTableResult.getRecordsTotal());
			} else {
				dataTableResult.setRecordsFiltered(
						"" + centroControlExchange.cantidadAvisFiltradosPaginadosPorFechaCapa(idCapa, idCategoria,
								fechaIni, fechaFin, whereClause, estadosList,soloComHis,conComenDeHis));
			}
		}

		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "/refrescarTablaAvistamientos/{numColorApp}", method = RequestMethod.GET)
	public String refrescarTablaAvistamientos(@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		model.addAttribute("numColorApp", numColorApp);
		return "area247/adminAvistamientos/pantallasAvistamiento :: tableAvistamientos";
	}

	/**
	 * Método encargado de consultar una avitamiento con base en un identificador
	 */
	@RequestMapping(value = "/getAvistamientoVista/{idAvistamiento}/{numColorApp}", method = RequestMethod.GET)
	public String getAvistamientoVista(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		model.addAttribute("numColorApp", numColorApp);
		model = prepararParametrosDetalleAvistamiento(idAvistamiento, model);
		model.addAttribute("avistamientoCargado", "block");
		return "area247/adminAvistamientos/pantallasAvistamiento :: quarter-section2";
	}
	
	@RequestMapping(value = "/getAvistamientoVistaMini/{idAvistamiento}/{numColorApp}", method = RequestMethod.GET)
	public String getAvistamientoVistaMini(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		model.addAttribute("numColorApp", numColorApp);
		model = prepararParametrosDetalleAvistamiento(idAvistamiento, model);
		model.addAttribute("avistamientoCargado", "block");
		return "area247/adminAvistamientos/pantallasAvistamiento :: quarter-section2-mini";
	}
	

	/** Método encargado de borrar la vista de los avistamientos */
	@RequestMapping(value = "/borrarAvistamientoVista/{numColorApp}", method = RequestMethod.GET)
	public String borrarAvistamientoVista(@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		model.addAttribute("numColorApp", numColorApp);
		model.addAttribute("avistamientoCargado", "none");
		return "area247/adminAvistamientos/pantallasAvistamiento :: quarter-section2";
	}

	/**
	 * Método encargado de consultar el marcador de un avitamiento para luego ser
	 * impreso sobre un mapoa
	 */
	@RequestMapping(value = "/getMarcadorAvistamiento/{idAvistamiento}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MarkerPrintPackage getMarkerPrintPackage(
			@PathVariable(value = "idAvistamiento") String idAvistamiento) {
		Avistamiento avistamiento = centroControlExchange.obtenerAvistamiento(idAvistamiento);
		List<Avistamiento> listAvistamientos = new ArrayList<Avistamiento>();
		listAvistamientos.add(avistamiento);
		return centroControlExchange.obtenerMardadoresListAvistamientos(listAvistamientos);
	}

	/** Método encargado de aprobar un avistamiento */
	@RequestMapping(value = "/aprobarAvistamiento/{idAvistamiento}/{numColorApp}", method = RequestMethod.GET)
	public String aprobarAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		centroControlExchange.cambiarEstadoAvistamiento(idAvistamiento, "aprobado");
		notificarUsuario(idAvistamiento, "aprobado"); // Notificar Usuario
		model.addAttribute("numColorApp", numColorApp);
		model = prepararParametrosDetalleAvistamiento(idAvistamiento, model);

		return "area247/adminAvistamientos/pantallasAvistamiento :: quarter-section2";
	}

	/** Método encargado de rechazar un avistamiento */
	@RequestMapping(value = "/rechazarAvistamiento/{idAvistamiento}/{numColorApp}", method = RequestMethod.GET)
	public String rechazarAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		centroControlExchange.cambiarEstadoAvistamiento(idAvistamiento, "rechazado");
		notificarUsuario(idAvistamiento, "rechazado"); // Notificar Usuario
		model.addAttribute("numColorApp", numColorApp);
		model = prepararParametrosDetalleAvistamiento(idAvistamiento, model);
		return "area247/adminAvistamientos/pantallasAvistamiento :: quarter-section2";
	}

	public void notificarUsuario(String idAvistamiento, String estado) {
		String userNameAvist = centroControlExchange.obtenerAvistamiento(idAvistamiento).getUsername();
		if (estado.equals("aprobado")) {
			centroControlExchange.enviarMensajePush("Personal", userNameAvist, "Avistamiento aprobado",
					"Le informamos que uno de sus avistamientos ha sido aprobado");
		} else {
			if (estado.equals("rechazado")) {
				centroControlExchange.enviarMensajePush("Personal", userNameAvist, "Avistamiento rechazado",
						"Le informamos que uno de sus avistamientos ha sido rechazado");
			}
		}
	}

	/** Método encargado de editar un avistamiento */
	@RequestMapping(value = "/editarAvistamiento/{idAvistamiento}/{nombreComun}"
			+ "/{descripcion}/{nombreCientifico}/{tipoEspecie}/{numColorApp}", method = RequestMethod.GET)
	public String editarAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "nombreComun") String nombreComun,
			@PathVariable(value = "descripcion") String descripcion,
			@PathVariable(value = "nombreCientifico") String nombreCientifico,
			@PathVariable(value = "tipoEspecie") String tipoEspecie,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		centroControlExchange.actualizarAvistamiento(idAvistamiento, nombreComun, descripcion, nombreCientifico,
				tipoEspecie);
		model.addAttribute("numColorApp", numColorApp);
		model = prepararParametrosDetalleAvistamiento(idAvistamiento, model);

		return "area247/adminAvistamientos/pantallasAvistamiento :: quarter-section2";
	}

	/** Método encargado de editar un avistamiento */
	@RequestMapping(value = "/confirmarAprobacionAvistamiento/{idAvistamiento}/{nombreComun}"
			+ "/{descripcion}/{nombreCientifico}/{tipoEspecie}/{numColorApp}", method = RequestMethod.GET)
	public String confirmarAprobacionAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "nombreComun") String nombreComun,
			@PathVariable(value = "descripcion") String descripcion,
			@PathVariable(value = "nombreCientifico") String nombreCientifico,
			@PathVariable(value = "tipoEspecie") String tipoEspecie,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		centroControlExchange.actualizarAvistamiento(idAvistamiento, nombreComun, descripcion, nombreCientifico,
				tipoEspecie);
		centroControlExchange.cambiarEstadoAvistamiento(idAvistamiento, "aprobado");
		notificarUsuario(idAvistamiento, "aprobado"); // Notificar Usuario
		model.addAttribute("numColorApp", numColorApp);
		model = prepararParametrosDetalleAvistamiento(idAvistamiento, model);

		return "area247/adminAvistamientos/pantallasAvistamiento :: quarter-section2";
	}

	public Model prepararParametrosDetalleAvistamiento(String idAvistamiento, Model model) {
		Avistamiento avistamiento = centroControlExchange.obtenerAvistamiento(idAvistamiento);
		model.addAttribute("id", avistamiento.getId());		
		model.addAttribute("fechaCreacion", avistamiento.getFechaCreacion());
		model.addAttribute("colorEstado", avistamiento.getColorEstado());
		model.addAttribute("username", avistamiento.getUsername());
		model.addAttribute("tieneHistoria", avistamiento.isTieneHistoria());
		
		if (avistamiento.getNombreComun() != null) {
			model.addAttribute("nombre", avistamiento.getNombreComun());
		} else {
			model.addAttribute("nombre","Sin nombre");
		}
		
		if (avistamiento.getNombreCientifico() != null) {
			model.addAttribute("nombreCientifico", avistamiento.getNombreCientifico());
		} else {
			model.addAttribute("nombreCientifico","Sin nombre");
		}
		
		if (avistamiento.getDescripcion() != null) {
			model.addAttribute("descripcion", avistamiento.getDescripcion());
		} else {
			model.addAttribute("descripcion","Sin descripción");
		}

		//model.addAttribute("nombreEstado", avistamiento.getNombreEstado());
		/*if ((avistamiento.getIdCategoria() != null) && (avistamiento.getIdCategoria() != 0)) {
			Categoria categoria = centroControlExchange.obtenerCategoria("" + avistamiento.getIdCategoria());
			model.addAttribute("tipoAvistamiento", categoria.getNombre());
		} else {
			Capa capa = centroControlExchange.obtenerDetalleCapa("" + avistamiento.getIdCapa());
			model.addAttribute("tipoAvistamiento", capa.getNombre());
		}
		if (avistamiento.getIdEspecie() != null) {
			Especie especie = centroControlExchange.obtenerEspecie("" + avistamiento.getIdEspecie());
			model.addAttribute("tipoEspecieTexto", especie.getNombre());
		} else {
			model.addAttribute("tipoEspecieTexto", "Sin Especie");
		}*/

		if (!avistamiento.getEstadoPublicacion().equals("2")) {
			model.addAttribute("aproRecAvist", "none");
		} else {
			model.addAttribute("aproRecAvist", "block");
		}

		if (avistamiento.getRutaMultimedia() != null) {
			model.addAttribute("rutaImagen", avistamiento.getRutaMultimedia());
		} else {
			model.addAttribute("rutaImagen", "assets/images/amenazalogo.jpg");
		}

		model.addAttribute("rutaIconoMarcador", avistamiento.getRutaIcono());

		return model;
	}

	/** Método encargado de editar un avistamiento por id */
	@RequestMapping(value = "/visualizarEditarAvistamiento/{idAvistamiento}/{numColorApp}", method = RequestMethod.GET)
	public String visualizarEditarAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		Avistamiento avistamiento = centroControlExchange.obtenerAvistamiento(idAvistamiento);
		model.addAttribute("idAvistEdit", avistamiento.getId());
		model.addAttribute("nombreEdit", avistamiento.getNombreComun());
		model.addAttribute("descripEdit", avistamiento.getDescripcion());
		model.addAttribute("nomCienEdit", avistamiento.getNombreCientifico());

		if ((avistamiento.getIdCategoria() != null) && (avistamiento.getIdCategoria() != 0)) {
			Categoria categoria = centroControlExchange.obtenerCategoria("" + avistamiento.getIdCategoria());
			model.addAttribute("tipoAvistEdit", categoria.getNombre());
		} else {
			Capa capa = centroControlExchange.obtenerDetalleCapa("" + avistamiento.getIdCapa());
			model.addAttribute("tipoAvistEdit", capa.getNombre());
		}

		model.addAttribute("numColorApp", numColorApp);
		model.addAttribute("despliegeModalEditAvist", "block");
		return "area247/adminAvistamientos/pantallasAvistamiento :: modalEditAvist";
	}

	/** Método encargado de editar un avistamiento por id */
	@RequestMapping(value = "/visualizarAprobarAvistamiento/{idAvistamiento}/{numColorApp}", method = RequestMethod.GET)
	public String visualizarAprobarAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			@PathVariable(value = "numColorApp") String numColorApp, Model model) {
		Avistamiento avistamiento = centroControlExchange.obtenerAvistamiento(idAvistamiento);
		model.addAttribute("idAvistApr", avistamiento.getId());
		model.addAttribute("nombreApr", avistamiento.getNombreComun());
		model.addAttribute("descripApr", avistamiento.getDescripcion());
		model.addAttribute("nomCienApr", avistamiento.getNombreCientifico());

		List<Especie> listEspecies = null;

		if ((avistamiento.getIdCategoria() != null) && (avistamiento.getIdCategoria() != 0)) {
			Categoria categoria = centroControlExchange.obtenerCategoria("" + avistamiento.getIdCategoria());
			model.addAttribute("tipoAvistApr", categoria.getNombre());
			listEspecies = centroControlExchange.obtenerEspeciesPorIdCategoria("" + avistamiento.getIdCategoria());
		} else {
			Capa capa = centroControlExchange.obtenerDetalleCapa("" + avistamiento.getIdCapa());
			model.addAttribute("tipoAvistApr", capa.getNombre());
		}

		if (listEspecies != null) {
			model.addAttribute("listEspecieApr", listEspecies);
			model.addAttribute("showSelectApr", "block");
			model.addAttribute("numColorApp", numColorApp);
		} else {
			model.addAttribute("showSelectApr", "none");
		}

		model.addAttribute("despliegeModalAproAvist", "block");
		return "area247/adminAvistamientos/pantallasAvistamiento :: modalAproAvist";
	}

	/**
	 * Método encargado de consultar el marcador de un avitamiento para luego ser
	 * impreso sobre un mapoa
	 */
	@RequestMapping(value = "/getCantidadesEstadosbyFechaCapa/{fechaIni}/{fechaFin}/{capa}/{categoria}"
			              + "/{soloComHis}/{conComenDeHis}", 
			        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EstadisticaAvistamiento getCantidadesEstadosbyFechaCapa(
			@PathVariable(value = "fechaIni") String fechaIni, @PathVariable(value = "fechaFin") String fechaFin,
			@PathVariable(value = "capa") String capa, @PathVariable(value = "categoria") String categoria,
			@PathVariable(value = "soloComHis") boolean soloComHis, 
			@PathVariable(value = "conComenDeHis") boolean conComenDeHis, Model model) {
		return centroControlExchange.getCantidadesAvistamientoEstadosbyFechaCapa(capa, categoria, fechaIni, fechaFin,
				                                                     soloComHis, conComenDeHis);
	}
	
	
	@RequestMapping(value = "/getCantidadTotalAvistamientos/{fechaIni}/{fechaFin}/{filtroCapa}/{filtroCateg}", 
			        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int getTotalAvistamientos(@PathVariable(value = "fechaIni") String fechaIni,
			                                       @PathVariable(value = "fechaFin") String fechaFin,
			                                       @PathVariable(value = "filtroCapa") String filtroCapa,
			                                       @PathVariable(value = "filtroCateg") String filtroCateg) {
		return centroControlExchange.getTotalAvistamientoEstadisticas(fechaIni,fechaFin,filtroCapa,filtroCateg,false);
	}
	
	
	/**
	 * Método encargado de consultar las estadisticas de avistamientos por usuario o
	 * por municipio
	 */
	@RequestMapping(value = "/getMatrizDatosPantalla/{fechaIni}/{fechaFin}/{filtroCapa}/{filtroCateg}/{pestana}", 
			        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object[][] getEstadisticasByUsuario(@PathVariable(value = "fechaIni") String fechaIni,
			                                                 @PathVariable(value = "fechaFin") String fechaFin, 
			                                                 @PathVariable(value = "filtroCapa") String filtroCapa,
			                                                 @PathVariable(value = "filtroCateg") String filtroCateg,
			                                                 @PathVariable(value = "pestana") String pestana) {		
		if (pestana.equals("usuario")) {			
			return convertEstadistUserToMatrix(centroControlExchange.getEstadisticasAvistUsuario(fechaIni,fechaFin,filtroCapa,filtroCateg));
		} else {
			if (pestana.equals("municipio")) {				
				return convertEstadistMuniToMatrix(centroControlExchange.getEstadisticasAvistMunicipio(fechaIni,fechaFin,filtroCapa,filtroCateg));
			}
		}
		return null;
	}
	

	public Object[][] convertEstadistUserToMatrix(List<EstadisticaReporteAvist> listDatosUsuario) {
		Object[][] datosGraficoUsuario = new Object[listDatosUsuario.size()][4];		
		int count = 0;	
		//Object[] leyendas = {"Usuario","Aprobado","Pendiente","Rechazado"};							
		//datosGraficoUsuario[count]= leyendas;
		for (EstadisticaReporteAvist datoUsuario : listDatosUsuario) {			
			Object[] columnas = {datoUsuario.getNombre(),datoUsuario.getCantidadAvistamientoAprobado(),
			                                             datoUsuario.getCantidadAvistamientoPendiente(),
			                                             datoUsuario.getCantidadAvistamientoRechazado()};
			datosGraficoUsuario[count] = columnas;
			count++;
		}
		return datosGraficoUsuario;
	}
	
	
	public Object[][] convertEstadistMuniToMatrix(List<EstadisticaReporteAvist> listDatosMunicipio) {
		Object[][] datosGraficoMunicipio = new Object[listDatosMunicipio.size()][4];		
		int count = 0;	
		for (EstadisticaReporteAvist datoMunicipio : listDatosMunicipio) {			
			Object[] columnas = {datoMunicipio.getNombre(),datoMunicipio.getCantidadAvistamientoAprobado(),
			                                               datoMunicipio.getCantidadAvistamientoPendiente(),
			                                               datoMunicipio.getCantidadAvistamientoRechazado()};
			datosGraficoMunicipio[count] = columnas;
			count++;
		}
		return datosGraficoMunicipio;
	}
	

	/**
	 * Método encargado de consultar un listado de historias con base en un
	 * identificador de avistamiento
	 */
	@RequestMapping(value = "/getHistoriasByAvistamiento/{idAvistamiento}", method = RequestMethod.GET)
	public String getHistoriasByAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			Model model) {
		List<Historia> listHistoria = centroControlExchange.obtenerHistoriasbyIdAvistamiento(idAvistamiento);
		model.addAttribute("historiaList", listHistoria);
		model.addAttribute("despliegeModalHistorias", "block");

		return "area247/adminAvistamientos/pantallasAvistamiento :: modalHistorias";
	}

	/**
	 * Método encargado de consultar un listado de historias con base en un
	 * identificador de avistamiento
	 */
	@RequestMapping(value = "/getComentariosByHistoria/{idHistoria}", method = RequestMethod.GET)
	public String getComentariosByHistoria(@PathVariable(value = "idHistoria") String idHistoria, Model model) {
		model.addAttribute("comenHistoList", centroControlExchange.obtenerComentariosbyIdHistoria(idHistoria));
		model.addAttribute("despliegeModalComenHisto", "block");
		return "area247/adminAvistamientos/pantallasAvistamiento :: modalComenHisto";
	}

	/**
	 * Método encargado de consultar un listado de comentarios con base en un
	 * identificador de avistamiento
	 */
	@RequestMapping(value = "/getComentariosByAvistamiento/{idAvistamiento}", method = RequestMethod.GET)
	public String getComentariosByAvistamiento(@PathVariable(value = "idAvistamiento") String idAvistamiento,
			Model model) {
		List<ComentarioAvist> listComentarioAvist = centroControlExchange
				.obtenerComentariosbyIdAvistamiento(idAvistamiento);
		model.addAttribute("comenAvistList", listComentarioAvist);
		model.addAttribute("despliegeModalComenAvist", "block");
		return "area247/adminAvistamientos/pantallasAvistamiento :: modalComenAvist";
	}

	/** Método encargado de cambiar el estado de un comentario de un avistamiento */
	@RequestMapping(value = "/cambiarEstadoComenAvistamiento/{idComentario}/{estado}", method = RequestMethod.GET)
	public @ResponseBody String cambiarEstadoComenAvistamiento(
			@PathVariable(value = "idComentario") String idComentario, @PathVariable(value = "estado") String estado) {
		return centroControlExchange.cambiarEstadoComenAvistamiento(idComentario, estado);
	}

	/** Método encargado de cambiar el estado de una historia */
	@RequestMapping(value = "/cambiarEstadoHistoria/{idHistoria}/{estado}", method = RequestMethod.GET)
	public @ResponseBody String cambiarEstadoHistoria(@PathVariable(value = "idHistoria") String idHistoria,
			@PathVariable(value = "estado") String estado) {
		return centroControlExchange.cambiarEstadoHistoria(idHistoria, estado);
	}

	/** Método encargado de cambiar el estado de un comentario de una historia */
	@RequestMapping(value = "/cambiarEstadoComenHistoria/{idComentario}/{estado}", method = RequestMethod.GET)
	public @ResponseBody String cambiarEstadoComenHistoria(@PathVariable(value = "idComentario") String idComentario,
			@PathVariable(value = "estado") String estado) {
		return centroControlExchange.cambiarEstadoComenHistoria(idComentario, estado);
	}

	// _______________________Administrar Vigias______________________

	/*
	 * Método encargado de redireccionarnos a la vista en la cual podemos
	 * administrar los reportes de vigias
	 */
	@RequestMapping("/adminVigias")
	public String adminVigias(Model model) {
		model.addAttribute("vigiaCargado", "none");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());

		String idApp = "2";
		String listIdTipoCapa = "5,2"; // 5,2 : Subcapas,Mapa
		String colorApp = centroControlExchange.obtenerDetalleAplicacion(idApp).getCodigoColor();

		List<CapaSubmenu> capas = centroControlExchange.getCapasByIdAplTipoCapa(idApp, listIdTipoCapa);
		model.addAttribute("capasNavTabs", capas);
		
		String filtroCapasEspadis = centroControlExchange.getFiltroPorCapasEstadisticas(capas);
		String filtroCategEspadis = centroControlExchange.getFiltroPorCategEstadisticas(capas);		
		model.addAttribute("filtroCapasEspadis",filtroCapasEspadis);
		model.addAttribute("filtroCategEspadis",filtroCategEspadis);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String fechaIni = dateFormat.format(date); //String fechaIni = "2018-11-01";
		String fechaFin = dateFormat.format(date); //String fechaFin = "2018-11-30";
		Long idCapaSel = capas.get(0).getId(); // Long idCapaSel = capas.get(1).getId();

		model.addAttribute("fechaIni", fechaIni);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("idCapaSel", idCapaSel);
		model.addAttribute("idGrafEst","usuario");

		model.addAttribute("idApp", idApp);
		model.addAttribute("listIdTipoCapa", listIdTipoCapa);
		model.addAttribute("colorApp", colorApp);
		model.addAttribute("numColorApp", colorApp.replace("#", ""));		

		EstadisticaVigia cantidades = centroControlExchange.getCantidadesVigiaEstadosbyFechaCapa("" + idCapaSel, null,
				fechaIni, fechaFin, false);
		model.addAttribute("numApr", cantidades.getCantidadAprobados());
		model.addAttribute("numPen", cantidades.getCantidadPendientes());
		model.addAttribute("numRec", cantidades.getCantidadRechazados());

		model.addAttribute("totalVigias",
				centroControlExchange.getCantidadVigiaByParametros(fechaIni,fechaFin,null,null,null,false));

		model.addAttribute(KEY_TITLE, "Admin Vigia");
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		return "area247/adminVigias/adminVigias";
	}
	
	

	@RequestMapping(value = "/getVigiasPaginated/{fechaIni}/{fechaFin}/{idApp}/{idCapa}/{idCategoria}"
			              + "/{estadosList}/{comenPen}", 
			        method = RequestMethod.GET)
	@ResponseBody
	public String obtenerVigiasPaginated(
			@PathVariable(value = "fechaIni") String fechaIni,
			@PathVariable(value = "fechaFin") String fechaFin, 
			@PathVariable(value = "idApp") String idApp,
			@PathVariable(value = "idCapa") String idCapa, 
			@PathVariable(value = "idCategoria") String idCategoria,
			@PathVariable(value = "estadosList") String estadosList, 
			@PathVariable(value = "comenPen") boolean comenPen,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		
		DataTableRequest<ReporteVigia> dataTableInRQ = new DataTableRequest<ReporteVigia>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

		String whereClause = ((AppUtil.isObjectEmpty(pagination.getFilterByClause())) ? "" : " WHERE ")
				+ pagination.getFilterByClause();
		String orderClause = pagination.getOrderByClause();
		String pageSize = "" + pagination.getPageSize();
		String pageNumber = "" + pagination.getPageNumber();

		List<ReporteVigia> listVigias = centroControlExchange.obtenerVigiasPaginadosPorParametros(idCapa,
				idCategoria, idApp, fechaIni, fechaFin, whereClause, orderClause, 
				pageSize, pageNumber, estadosList,comenPen);

		DataTableResults<ReporteVigia> dataTableResult = new DataTableResults<ReporteVigia>();
		dataTableResult
				.setMarkerPrintPackage(centroControlExchange.obtenerMardadoresListVigias(listVigias));
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(listVigias);

		if (!AppUtil.isObjectEmpty(listVigias)) {
			dataTableResult.setRecordsTotal("" + centroControlExchange
					.cantidadVigiasPaginatedPorParametros(idCapa, idCategoria, fechaIni, fechaFin, 
							estadosList,comenPen));

			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered("" + dataTableResult.getRecordsTotal());
			} else {
				dataTableResult.setRecordsFiltered(
						"" + centroControlExchange.cantidadVigiasPaginatedFiltradosPorParametros(idCapa, idCategoria,
								fechaIni, fechaFin, whereClause, estadosList,comenPen));
			}
		}
		return new Gson().toJson(dataTableResult);
		//return null;
	}
	
	
	
	
	
	
	
	
	

	// _______________________Administrar Recurso_______________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar los
	 * Recursos, editarlos y crear nuevos
	 */
	@RequestMapping("/adminRecursos")
	public String adminRecurso(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormRecurso", " ");
		model.addAttribute("despliegeModalRecursos", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Recursos");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("recursoList", centroControlExchange.obtenerRecursos());
		model = agregarNotificacionesVista(model);
		return "area247/adminRecursos/adminRecursos";
	}

	/** Método encargado de consultar un recurso con base en un identificador */
	@RequestMapping(value = "/getRecurso/{idRecurso}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RecursoVigia getRecurso(@PathVariable(value = "idRecurso") String idRecurso) {
		RecursoVigia recurso = centroControlExchange.obtenerRecurso(idRecurso);
		return recurso;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de recursos al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateRecurso", method = RequestMethod.POST)
	public String createUpdateRecurso(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateRecurso(id, nombre, descripcion);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("mensajeFormRecurso", respuesta);

		model.addAttribute("despliegeModalRecursos", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Recursos");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("recursoList", centroControlExchange.obtenerRecursos());
		model = agregarNotificacionesVista(model);
		return "area247/adminRecursos/adminRecursos";
	}

	/** Método encargado de eliminar un recurso con base en un identificador */
	@RequestMapping(value = "/deleteRecurso/{idRecurso}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteRecurso(@PathVariable(value = "idRecurso") String idRecurso, Model model) {

		String respuesta = centroControlExchange.deleteRecurso(idRecurso);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormRecurso", respuesta);
		model.addAttribute("despliegeModalRecursos", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Recursos");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("recursoList", centroControlExchange.obtenerRecursos());
		model = agregarNotificacionesVista(model);
		return "area247/adminRecursos/adminRecursos";
	}

	// _______________________Administrar
	// Afectacion__________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * Afectaciones, editarlas y crear nuevas
	 */
	@RequestMapping("/adminAfectaciones")
	public String adminAfectacion(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormAfectacion", " ");
		model.addAttribute("despliegeModalAfectaciones", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Afectaciones");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("afectacionList", centroControlExchange.obtenerAfectaciones());
		model.addAttribute("recursoList", centroControlExchange.obtenerRecursos());
		model.addAttribute("autoridadList", centroControlExchange.obtenerAutoridades());
		model = agregarNotificacionesVista(model);
		return "area247/adminAfectaciones/adminAfectaciones";
	}

	/** Método encargado de consultar una afectacion con base en un identificador */
	@RequestMapping(value = "/getAfectacion/{idAfectacion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Afectacion getAfectacion(@PathVariable(value = "idAfectacion") String idAfectacion) {
		Afectacion afectacion = centroControlExchange.obtenerAfectacion(idAfectacion);
		return afectacion;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de afectaciones al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateAfectacion", method = RequestMethod.POST)
	public String createUpdateAfectacion(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "recurso", required = false) String recurso,
			@RequestParam(name = "autoridad", required = false) String autoridad, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateAfectacion(id, nombre, descripcion, recurso, autoridad);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("mensajeFormAfectacion", respuesta);

		model.addAttribute("despliegeModalAfectaciones", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Afectaciones");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("afectacionList", centroControlExchange.obtenerAfectaciones());
		model.addAttribute("recursoList", centroControlExchange.obtenerRecursos());
		model.addAttribute("autoridadList", centroControlExchange.obtenerAutoridades());
		model = agregarNotificacionesVista(model);
		return "area247/adminAfectaciones/adminAfectaciones";
	}

	/** Método encargado de eliminar una afectacion con base en un identificador */
	@RequestMapping(value = "/deleteAfectacion/{idAfectacion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteAfectacion(@PathVariable(value = "idAfectacion") String idAfectacion, Model model) {

		String respuesta = centroControlExchange.deleteAfectacion(idAfectacion);

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeFormAfectacion", respuesta);
		model.addAttribute("despliegeModalAfectaciones", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Afectaciones");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("afectacionList", centroControlExchange.obtenerAfectaciones());
		model.addAttribute("recursoList", centroControlExchange.obtenerRecursos());
		model.addAttribute("autoridadList", centroControlExchange.obtenerAutoridades());
		model = agregarNotificacionesVista(model);
		return "area247/adminAfectaciones/adminAfectaciones";
	}

	// _______________________________Tareas
	// Programadas____________________________________

	/** Método encargado de consultar una afectacion con base en un identificador */
	@RequestMapping(value = "/mostrarBotonesURL/{tipoRecurso}/{idCapa}", method = RequestMethod.GET)
	public @ResponseBody String mostrarBotonesURL(@PathVariable(value = "tipoRecurso") String tipoRecurso,
			@PathVariable(value = "idCapa") String idCapa) {
		String respuesta = centroControlExchange.mostrarBotonesURL(tipoRecurso, idCapa);
		return respuesta;
	}

	// _______________________Administrar
	// Actividad__________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * actividades, editarlas y crear nuevas
	 */
	@RequestMapping(value = "/getActividadesByIdCapa/{idAplicacionDeCapa}/{idCapa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String adminActividades(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapa") String idCapa, Model model) {
		model.addAttribute("mensajeFormActividad", " ");
		model = prepararParametrosVistaActividad(model, idAplicacionDeCapa, idCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminActividades/adminActividades";
	}

	/** Método encargado de consultar una Actividad con base en un identificador */
	@RequestMapping(value = "/getActividad/{idActividad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ActividadHuella getActividad(@PathVariable(value = "idActividad") String idActividad) {
		ActividadHuella actividad = centroControlExchange.obtenerActividad(idActividad);
		return actividad;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de Actividades al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateActividad", method = RequestMethod.POST)
	public String createUpdateActividad(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "orden", required = false) String orden,
			@RequestParam(name = "decision", required = false) boolean decision,
			@RequestParam(name = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@RequestParam(name = "idCapaDeActividad") String idCapa, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateActividad(id, nombre, descripcion, orden, decision,
				idCapa);
		model.addAttribute("mensajeFormActividad", respuesta);
		model = prepararParametrosVistaActividad(model, idAplicacionDeCapa, idCapa);
		model = agregarNotificacionesVista(model);
		return "area247/adminActividades/adminActividades";
	}

	/** Método encargado de eliminar una actividad con base en un identificador */
	@RequestMapping(value = "/deleteActividad/{idAplicacionDeCapa}/{idCapaDeActividad}/{idActividad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteActividad(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapaDeActividad") String idCapaDeActividad,
			@PathVariable(value = "idActividad") String idActividad, Model model) {
		String respuesta = centroControlExchange.deleteActividad(idActividad);
		model.addAttribute("mensajeFormActividad", respuesta);
		model = prepararParametrosVistaActividad(model, idAplicacionDeCapa, idCapaDeActividad);
		model = agregarNotificacionesVista(model);
		return "area247/adminActividades/adminActividades";
	}

	public Model prepararParametrosVistaActividad(Model model, String idAplicacionDeCapa, String idCapa) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("despliegeModalActividades", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Actividades");
		model.addAttribute("idAplicacionDeCapa", idAplicacionDeCapa);
		model.addAttribute("idCapaDeActividad", idCapa);
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("actividadList", centroControlExchange.obtenerActividadesByIdCapa(idCapa));
		model.addAttribute("ordenesList", centroControlExchange.obtenerOrdenesActividadesByIdCapa(idCapa));
		return model;
	}

	// _______________________Administrar Preguntas de
	// huella__________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * preguntas de huellas, editarlas y crear nuevas.
	 */
	@RequestMapping(value = "/obtenerPreguntasbyIdActividad/{idActividad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerPreguntasbyIdActividad(@PathVariable(value = "idActividad") String idActividad, Model model) {
		model.addAttribute("mensajeFormPregunta", " ");
		model = prepararParametrosVistaPreguntaHuella(idActividad, model);
		model = agregarNotificacionesVista(model);
		return "area247/adminActividades/adminPreguntasHuella";
	}

	/** Método encargado de consultar una Actividad con base en un identificador */
	@RequestMapping(value = "/getPreguntaHuella/{idPreguntaHuella}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PreguntaHuella getPreguntaHuella(
			@PathVariable(value = "idPreguntaHuella") String idPreguntaHuella) {
		PreguntaHuella preguntaHuella = centroControlExchange.obtenerPreguntaHuella(idPreguntaHuella);
		return preguntaHuella;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de Actividades al respectivo servicio web encargado de procesarlos y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdatePreguntaHuella", method = RequestMethod.POST)
	public String createUpdatePreguntaHuella(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "idActividad", required = false) String idActividad,
			@RequestParam(name = "idTipoRespuesta", required = false) String idTipoRespuesta, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdatePreguntaHuella(id, nombre, descripcion, idActividad,
				idTipoRespuesta);
		model.addAttribute("mensajeFormPregunta", respuesta);
		model = prepararParametrosVistaPreguntaHuella(idActividad, model);
		model = agregarNotificacionesVista(model);
		return "area247/adminActividades/adminPreguntasHuella";
	}

	/** Método encargado de eliminar una actividad con base en un identificador */
	@RequestMapping(value = "/deletePreguntaHuella/{idPreguntaHuella}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deletePreguntaHuella(@PathVariable(value = "idActividad") String idActividad,
			@PathVariable(value = "idPreguntaHuella") String idPreguntaHuella, Model model) {
		String respuesta = centroControlExchange.deletePreguntaHuella(idPreguntaHuella);
		model.addAttribute("mensajeFormPregunta", respuesta);
		model = prepararParametrosVistaPreguntaHuella(idActividad, model);
		model = agregarNotificacionesVista(model);
		return "area247/adminActividades/adminPreguntasHuella";
	}

	public Model prepararParametrosVistaPreguntaHuella(String idActividad, Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");
		model.addAttribute("despliegeModalPreguntas", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Preguntas");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("preguntaList", centroControlExchange.obtenerPreguntasHuella(idActividad));
		model.addAttribute("ordenesList", centroControlExchange.obtenerOrdenesPreguntasByActividad(idActividad));
		model.addAttribute("tiposRespuestaHuellaList", centroControlExchange.obtenerTiposRespuestaHuella());
		model.addAttribute("rutaMostrada", " / " + (centroControlExchange.obtenerActividad(idActividad).getNombre()));
		return model;
	}

	// _______________________________Reportes___________________________________

	/**
	 * Método encargado de darnos acceso a la vista en la cual podremos visualizar
	 * distintos reportes
	 */
	@RequestMapping("/reportes")
	public String reportes(Model model) {
		model.addAttribute(KEY_TITLE, "Reportes");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());

		model.addAttribute("reporteVehiculosUrl", reporteVehiculosUrl);
		model.addAttribute("reporteFotodeteccionesUrl", reporteFotodeteccionesUrl);
		model.addAttribute("reporteMapaUrl", reporteMapaUrl);

		return "area247/carga/visualizarReportes";
	}

	// _______________________________Reportes___________________________________

	/**
	 * Método encargado de asignar los permisos de un rol sobre cada objeto.
	 */
	@RequestMapping(value = "/adminAuditorias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String adminAuditoria(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeAuditorias", "");
		model.addAttribute("despliegeModalAuditorias", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Auditorias");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("objetoWrapper", obtenerObjetos1());
		model = agregarNotificacionesVista(model);
		return "area247/adminAuditorias/adminAuditorias";
	}

	@ModelAttribute
	public ObjetoWrapper obtenerObjetos1() {
		ObjetoWrapper objetoWrapper = new ObjetoWrapper();
		objetoWrapper.setObjetos(centroControlExchange.obtenerObjetos());
		return objetoWrapper;
	}

	/**
	 * Método encargado de editar los permisos que cada rol tiene sobre cada objeto.
	 */
	@RequestMapping(value = "/updateAuditorias", method = RequestMethod.POST)
	public String updateAuditorias(@ModelAttribute ObjetoWrapper objetoWrapper, Model model) {
		String respuesta = centroControlExchange.updateAuditorias(objetoWrapper.getObjetos());

		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);

		model.addAttribute("mensajeAuditorias", respuesta);
		model.addAttribute("despliegeModalAuditorias", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, "Administrar Auditorias");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("objetoWrapper", obtenerObjetos1());
		model = agregarNotificacionesVista(model);
		return "area247/adminAuditorias/adminAuditorias";
	}

	// _______________________Administrar Especies__________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * Especies, editarlas y crear nuevas
	 */
	@RequestMapping("/getEspeciesByIdCategoria/{idAplicacionDeCapa}/{idCapaDeCategoria}/{idCategoria}")
	public String adminEspecie(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapaDeCategoria") String idCapaDeCategoria,
			@PathVariable(value = "idCategoria") String idCategoria, Model model) {
		model.addAttribute("mensajeFormEspecie", " ");
		model = prepararParametrosVistaEspecie(model, idAplicacionDeCapa, idCapaDeCategoria, idCategoria);
		model = agregarNotificacionesVista(model);
		return "area247/adminEspecies/adminEspecies";
	}

	/** Método encargado de consultar una especie con base en un identificador */
	@RequestMapping(value = "/getEspecie/{idEspecie}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Especie getEspecie(@PathVariable(value = "idEspecie") String idEspecie) {
		Especie especie = centroControlExchange.obtenerEspecie(idEspecie);
		return especie;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de especie al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateEspecie", method = RequestMethod.POST)
	public String createUpdateEspecie(@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "icono", required = false) MultipartFile icono,
			@RequestParam(name = "idCategoriaDeEspecie", required = false) String idCategoriaDeEspecie,
			@RequestParam(name = "idCapaDeCategoria", required = false) String idCapaDeCategoria,
			@RequestParam(name = "idAplicacionDeCapa", required = false) String idAplicacionDeCapa, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateEspecie(id, nombre, icono, idCategoriaDeEspecie);
		model.addAttribute("mensajeFormEspecie", respuesta);
		model = prepararParametrosVistaEspecie(model, idAplicacionDeCapa, idCapaDeCategoria, idCategoriaDeEspecie);
		model = agregarNotificacionesVista(model);
		return "area247/adminEspecies/adminEspecies";
	}

	/** Método encargado de eliminar una especie con base en un identificador */
	@RequestMapping(value = "/deleteEspecie/{idAplicacionDeCapa}/{idCapaDeCategoria}/{idCategoria}/{idEspecie}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteEspecie(@PathVariable(value = "idAplicacionDeCapa") String idAplicacionDeCapa,
			@PathVariable(value = "idCapaDeCategoria") String idCapaDeCategoria,
			@PathVariable(value = "idCategoria") String idCategoria,
			@PathVariable(value = "idEspecie") String idEspecie, Model model) {
		String respuesta = centroControlExchange.deleteEspecie(idEspecie);
		model.addAttribute("mensajeFormEspecie", respuesta);
		model = prepararParametrosVistaEspecie(model, idAplicacionDeCapa, idCapaDeCategoria, idCategoria);
		model = agregarNotificacionesVista(model);
		return "area247/adminEspecies/adminEspecies";
	}

	Model prepararParametrosVistaEspecie(Model model, String idAplicacionDeCapa, String idCapaDeCategoria,
			String idCategoriaDeEspecie) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");
		model.addAttribute("validacionCreacion", "");

		model.addAttribute("despliegeModalEspecie", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Especies");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("especieList", centroControlExchange.obtenerEspeciesPorIdCategoria(idCategoriaDeEspecie));
		model.addAttribute("idAplicacionDeCapa", idAplicacionDeCapa);
		model.addAttribute("idCapaDeCategoria", idCapaDeCategoria);
		model.addAttribute("idCategoriaDeEspecie", idCategoriaDeEspecie);

		model.addAttribute("rutaMostrada",
				" / " + (centroControlExchange.obtenerDetalleAplicacion(idAplicacionDeCapa)).getNombre() + " / "
						+ (centroControlExchange.obtenerDetalleCapa(idCapaDeCategoria)).getNombre() + " / "
						+ (centroControlExchange.obtenerCategoria(idCategoriaDeEspecie)).getNombre());
		model = prepararPermisosUsuario(model, "3");
		return model;
	}
	
	
	// _______________________Administrar Recomendaciones__________________________________

	/**
	 * Método encargado de redireccionarnos a la vista en la cual podemos listar las
	 * Recomendaciones, editarlas y crear nuevas
	 */
	@RequestMapping("/getRecomendacionesAire")
	public String adminRecomendacion(Model model) {
		model.addAttribute("mensajeFormRecomendacion", " ");
		model = prepararParametrosVistaRecomendacion(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminRecomendaciones/adminRecomendaciones";
	}

	/** Método encargado de consultar una recomendaciones con base en un identificador */
	@RequestMapping(value = "/getRecomendacion/{idRecomendacion}", method = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RecomendacionAire getRecomendacion(@PathVariable(value = "idRecomendacion") String idRecomendacion) {
		RecomendacionAire recomendacion = centroControlExchange.obtenerRecomendacion(idRecomendacion);
		return recomendacion;
	}

	/**
	 * Método encargado de enviar los datos recogidos del formulario de creación y
	 * edición de recomendacion al respectivo servicio web encargado de procesarlas y
	 * luego retornarnos un mensaje de confirmación o rechazo
	 */
	@RequestMapping(value = "/createUpdateRecomendacion", method = RequestMethod.POST)
	public String createUpdateRecomendacion(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(name = "texto", required = false) String texto,
			@RequestParam(name = "icono", required = false) MultipartFile icono,
			@RequestParam(name = "codigo", required = false) String codigo, Model model)
			throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.createUpdateRecomendacion(id,texto,icono,codigo);
		model.addAttribute("mensajeFormRecomendacion",respuesta);
		model = prepararParametrosVistaRecomendacion(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminRecomendaciones/adminRecomendaciones";
	}
	
	/** Método encargado de eliminar una especie con base en un identificador */
	@RequestMapping(value = "/deleteRecomendacion/{idRecomendacion}", 
			        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteRecomendacion(
			@PathVariable(value = "idRecomendacion") String idRecomendacion, Model model) {
		String respuesta = centroControlExchange.deleteRecomendacion(idRecomendacion);
		model.addAttribute("mensajeFormRecomendacion", respuesta);
		model = prepararParametrosVistaRecomendacion(model);
		model = agregarNotificacionesVista(model);
		return "area247/adminRecomendaciones/adminRecomendaciones";
	}
	
	
	public Model prepararParametrosVistaRecomendacion(Model model) {
		model.addAttribute(KEY_LISTA_MARCADORES, centroControlExchange.obtenerMardadoresbyIdCapa(CAPA_DEFAULT));
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("validacionEdicion", "");

		model.addAttribute("despliegeModalRecomendacion", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute("title", "Administrar Recomendaciones aire");
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("recomendacionList", centroControlExchange.obtenerTodasRecomendaciones());
		model = prepararPermisosUsuario(model, "3");
		return model;
	}	
	
	
	
	
	@RequestMapping("/adminRadios")
	public String adminRadio(Model model) {
		String valTitle = "Administrar Radios";
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("mensajeFormRadio"," ");
		model.addAttribute("despliegeModalAplicacion", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, valTitle);
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("aplicacionesList", centroControlExchange.obtenerAplicaciones());
		model = agregarNotificacionesVista(model);
		return "area247/adminAplicaciones/adminRadios";
	}
	
	
	@RequestMapping(value = "/updateRadios", method = RequestMethod.POST)
	public String createUpdateAplicacion(
			@RequestParam(value = "idAplicacion", required = false) String idAplicacion,
			@RequestParam(name = "radioAccion", required = false) String radioAccion,
			@RequestParam(name = "limInfRadio", required = false) String limInfRadio,
			@RequestParam(name = "limSupRadio", required = false) String limSupRadio,
			Model model) throws FileNotFoundException, IOException {

		String respuesta = centroControlExchange.updateRadioAplicacion(idAplicacion,radioAccion,limInfRadio,limSupRadio);
		
		String valTitle = "Administrar Radios";
		model.addAttribute(KEY_KML_BORDER, VAL_KML_BORDER_URLMAPKML);
		model.addAttribute("mensajeFormRadio",respuesta);
		model.addAttribute("despliegeModalAplicacion", VAL_DESPLIEGUE_BLOCK);
		model.addAttribute(KEY_TITLE, valTitle);
		model.addAttribute(KEY_MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute("aplicacionesList", centroControlExchange.obtenerAplicaciones());
		model = agregarNotificacionesVista(model);
		return "area247/adminAplicaciones/adminRadios";
	}	

	
}