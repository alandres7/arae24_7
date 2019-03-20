package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.gov.metropol.area247.centrocontrol.carga.service.ICamaraService;
import co.gov.metropol.area247.centrocontrol.carga.service.ICargaService;
import co.gov.metropol.area247.centrocontrol.carga.service.ICirculacionVehiculoService;
import co.gov.metropol.area247.centrocontrol.carga.service.IFormatoCargaService;
import co.gov.metropol.area247.centrocontrol.carga.service.ILogCargaService;
import co.gov.metropol.area247.centrocontrol.carga.service.IVehiculoService;
import co.gov.metropol.area247.centrocontrol.common.Archivo;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.common.Area247WebRequest;
import co.gov.metropol.area247.centrocontrol.common.SecurityUtil;
import co.gov.metropol.area247.centrocontrol.constantes.KeyViewConstants;
import co.gov.metropol.area247.centrocontrol.constantes.ValueViewConstants;
import co.gov.metropol.area247.centrocontrol.constantes.ViewConstants;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorLineaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ValidarArchivoDto;
import co.gov.metropol.area247.centrocontrol.service.CentroControlExchange;

/**
 * 
 * Controladora de cargarInfo.html 
 * para la carga de archivos de vehiculos del RUNT 
 * y de camaras de las secretarias de transito
 * 
 */
@Controller
@RequestMapping("/cargarInfo")
public class CargaArchivoController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(CargaArchivoController.class);

	private static final int TIPO_ARCHIVO_VEHICULO = 1;
	private static final int TIPO_ARCHIVO_CAMARAS = 2; 
	private static final int TIPO_ARCHIVO_CIRCULACION = 3; 
		
	@Value("${area247.carga.maxrows}")
	private Integer maxRows;

	@Value("${area247.carga.chunk}")
	private Integer chunkSize;

	@Value("${area247.carga.dateformat}")
	private String formatoFecha;

	@Value("${area247.carga.csv}")
	private String separadorCsv;

	@Value("${area247.carga.skip}")
	private Integer lineasEncabezado;

	@Value("${area247.carga.log}")
	private String rutaLogCarga;
	
	@Autowired
	private CentroControlExchange centroControlExchange;

	@Autowired
	private IFormatoCargaService formatoCargaService;

	@Autowired
	private ICargaService cargaService;

	@Autowired
	private ILogCargaService logCargaService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private ICamaraService camaraService;
	
	@Autowired
	private ICirculacionVehiculoService circulacionService;

	/**
	 * Consultas al cargar la vista (log de cargas y tipos de archivo)
	 * 
	 * @param model
	 * @param area247WebRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model, Area247WebRequest area247WebRequest) {

		model.addAttribute("archivosList",
				logCargaService.consultarLogCarga(maxRows, SecurityUtil.obtenerUsuarioLogueado()));

		model.addAttribute("tipoArchivoList", formatoCargaService.findAll());

		model.addAttribute("cargaArchivoModel", new CargaArchivoDto());

		model.addAttribute("title", "Subir Archivo");
		model.addAttribute("menuItemsList", centroControlExchange.obtenerMenus());

		return "area247/carga/cargarInfo";
	}

	/**
	 * Accion del boton Cargar
	 * 
	 * @param cargaArchivoModel
	 * @param result
	 * @param model
	 * @param area247WebRequest
	 * @return
	 */	
	@RequestMapping(value = "/form", method = RequestMethod.POST, params = "action=cargar")
	public String cargar(CargaArchivoDto cargaArchivoModel, BindingResult result, Model model,
			Area247WebRequest area247WebRequest) {
		try {
			
			LOGGER.info("Validando...");
			ValidarArchivoDto validarArchivoDto = cargaService.validarArchivo(cargaArchivoModel,
					cargaArchivoModel.getTipoArchivo().getId(), separadorCsv, lineasEncabezado, formatoFecha);
			List<ErrorLineaArchivoDto> errores = validarArchivoDto.getErrores();

			int duplicados = 0;

			if (null != errores && !errores.isEmpty()) {
				model.addAttribute("erroresCarga", errores);
				generarLogTexto(errores);
				String error = "La estructura del registro no es válida o existen campos obligatorios sin información. Hay " + errores.size() + " líneas con errores.";
				if (!cargaArchivoModel.getArchivo().getOriginalFilename().toLowerCase().endsWith("txt") 
						&& !cargaArchivoModel.getArchivo().getOriginalFilename().toLowerCase().endsWith("csv")) {
					error += " Suba un txt o csv correcto.";
				}
				throw new Area247Exception(error);
			}
			
			switch(cargaArchivoModel.getTipoArchivo().getId().intValue()) {
				case TIPO_ARCHIVO_VEHICULO:{
					LOGGER.info("Procesando vehículos...");
					vehiculoService.procesarArchivo(chunkSize, cargaArchivoModel, validarArchivoDto.getLineas(), separadorCsv, lineasEncabezado);
					duplicados = vehiculoService.contarVehiculosDuplicados();
					vehiculoService.eliminarDuplicados();
				}break;
				case TIPO_ARCHIVO_CAMARAS:{
					LOGGER.info("Procesando cámaras...");
					camaraService.procesarArchivo(chunkSize, cargaArchivoModel, validarArchivoDto.getLineas(), separadorCsv, lineasEncabezado, formatoFecha);
					duplicados = camaraService.contarDuplicados();
					camaraService.eliminarDuplicados();
				}break;
				case TIPO_ARCHIVO_CIRCULACION:{
					LOGGER.info("Procesando circulación...");
					circulacionService.procesarArchivoCirculacion(chunkSize, cargaArchivoModel, validarArchivoDto.getLineas(), separadorCsv, lineasEncabezado);
				}break;
				default:{
					
				}break;
			}
			
			logCargaService.saveLog(cargaArchivoModel, SecurityUtil.obtenerUsuarioLogueado());
			
			String exito = "El archivo fue subido exitosamente.";
			if (duplicados > 0) {
				exito += " Algunos registros fueron sobre escritos (" + duplicados + " registros).";
			}
			generarLogTexto(exito);
			
			model.addAttribute(WEB_PARAM_SUCCESS_MESSAGE, exito);

		} catch (Exception e) {
			model.addAttribute(WEB_PARAM_WARNING_MESSAGE, e.getMessage());
			model.addAttribute(WEB_PARAM_ERROR_FIELDS, result.getAllErrors());
		}

		return get(model, area247WebRequest);
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar(CargaArchivoDto cargaArchivoModel, BindingResult result, Model model, Area247WebRequest area247WebRequest) {
		model.addAttribute(KeyViewConstants.MENU_ITEMS_LIST, centroControlExchange.obtenerMenus());
		model.addAttribute(KeyViewConstants.TITLE, ValueViewConstants.TITLE_INICIO);
		model.addAttribute(KeyViewConstants.KML_BORDER,ValueViewConstants.KML_BORDER_URLMAPKML);
		model.addAttribute(KeyViewConstants.CARGAR_ARCHIVO_MODEL, new CargaArchivoDto());
		
		return ViewConstants.INICIO;
	}

		
	/**
	 * Accion del boton Descargar log
	 * 
	 * @param response
	 * @param cargaArchivoModel
	 * @param result
	 * @param model
	 * @param area247WebRequest
	 * @return
	 * @throws IOException
	 */	
	@RequestMapping(value = "/form", method = RequestMethod.POST, params = "log=log")
	private String crearLog(HttpServletResponse response, CargaArchivoDto cargaArchivoModel, BindingResult result,
			Model model, Area247WebRequest area247WebRequest) throws IOException {
		Archivo archivo = new Archivo(rutaLogCarga);
		String FILE_PATH = archivo.getRuta();
		File file = new File(FILE_PATH);
		if (file.exists()) {
			InputStream in = new FileInputStream(file);
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			response.setHeader("Content-Length", String.valueOf(file.length()));
			FileCopyUtils.copy(in, response.getOutputStream());
			archivo.borrarArchivo();
		}
		return get(model, area247WebRequest);
	}

	private void generarLogTexto(List<ErrorLineaArchivoDto> errores) {
		Archivo log = new Archivo(rutaLogCarga);

		try {
			log.abrirArchivo(true);
			for (int i = 0; i < errores.size(); i++) {
				String linea = "";
				String mensajeError = "";
				String nombre = "";
				String valor = "";

				int tamEror = errores.get(i).getErroresLinea().size();
				for (int y = 0; y < tamEror; y++) {
					mensajeError = errores.get(i).getErroresLinea().get(y).getMensajeError();
					nombre = errores.get(i).getErroresLinea().get(y).getNombre();
					valor = errores.get(i).getErroresLinea().get(y).getValor();

					linea = "Linea " + errores.get(i).getLinea() + " - " + mensajeError + " - " + nombre + " - " + valor;
					log.escribirArchivo(linea);
				}

			}
			log.cerrarArchivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generarLogTexto(String exito) {
		Archivo log = new Archivo(rutaLogCarga);

		try {
			log.abrirArchivo(true);
			log.escribirArchivo(exito);
			log.cerrarArchivo();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}
