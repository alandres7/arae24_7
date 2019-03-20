package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.gov.metropol.area247.centrocontrol.carga.service.ICamaraService;
import co.gov.metropol.area247.centrocontrol.carga.service.IVehiculoService;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.common.Area247WebRequest;
import co.gov.metropol.area247.centrocontrol.common.FormatUtils;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.HuellaCarbonoDto;
import co.gov.metropol.area247.centrocontrol.dto.VehiculoDto;
import co.gov.metropol.area247.centrocontrol.model.Camara;
import co.gov.metropol.area247.centrocontrol.model.TipoCarroceriaVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoClaseVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoCombustibleVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoMarcaVehiculo;
import co.gov.metropol.area247.centrocontrol.model.Vehiculo;
import co.gov.metropol.area247.centrocontrol.service.CentroControlExchange;

@Controller
@RequestMapping("/archivos/reporte/huellacarbono")
public class ReporteHuellaController extends BaseController {
	
	private HuellaCarbonoDto formulario = new HuellaCarbonoDto();
	private VehiculoDto vehiculoDto = new VehiculoDto();
	private List<TipoClaseVehiculo> claseList = new ArrayList<>();
	private List<TipoCarroceriaVehiculo> carroceriaList = new ArrayList<>();
	private List<TipoMarcaVehiculo> marcaList = new ArrayList<>();
	private List<TipoCombustibleVehiculo> combustibleList = new ArrayList<>();
	private List<Camara> camaraList = new ArrayList<>();
	private List<Vehiculo> vehiculosList = new ArrayList<>();
	
	@Value("${area247.huellacarbono.maxrows}")
	private Integer maxRows;
	
	@Autowired
	private CentroControlExchange centroControlExchange;

	@Autowired
	private ICamaraService camaraService;

	@Autowired
	private IVehiculoService vehiculoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model){
		
		init(model);
				
		model.addAttribute("claseList", claseList);
		model.addAttribute("carroceriaList", carroceriaList);
		model.addAttribute("marcaList", marcaList);
		model.addAttribute("combustibleList", combustibleList);
		
		model.addAttribute("camaraList", camaraList);
				
		model.addAttribute("huellacarbonoModel", formulario);	
		model.addAttribute("vehiculoDto", vehiculoDto);
		
		model.addAttribute("reporteNombre", "Huella de Carbono");
		
		model.addAttribute("title", "Reporte de Huella de Carbono");
		
		model.addAttribute("menuItemsList",centroControlExchange.obtenerMenus());
		
		return "area247/carga/huellacarbono";
	}
	
	private void init(Model model) {
		try {
			if(claseList.isEmpty()) {
				claseList = vehiculoService.consultarClases();			
			}
			if(marcaList.isEmpty()) {
				marcaList = vehiculoService.consultarMarcas();			
			}
			if(carroceriaList.isEmpty()) {
				carroceriaList = vehiculoService.consultarCarrocerias();			
			}
			if(combustibleList.isEmpty()) {
				combustibleList = vehiculoService.consultarCombustibles();			
			}
			if(camaraList.isEmpty()) {
				camaraList = camaraService.findAll();			
			}							
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(WEB_PARAM_WARNING_MESSAGE, e.getMessage());			
		}
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST, params="action=filtrar")
	public String filtrar(
			@RequestParam Long claseId,
			@RequestParam Long carroceriaId,
			@RequestParam Long marcaId,
			@RequestParam String modelo,
			@RequestParam Long combustibleId,
			@RequestParam String capacidad,
			@RequestParam String placa,
			@RequestParam String fecha,
			@RequestParam String fechaHasta,
			CargaArchivoDto cargaArchivoModel, BindingResult errors, Model model, Area247WebRequest area247WebRequest) {
		
		try {
			setearFormulario(
					claseId,
					carroceriaId,
					marcaId,
					modelo,
					combustibleId,
					capacidad,
					placa,
					fecha,
					fechaHasta
					);
			model.addAttribute("huellacarbonoModel", formulario);
			
			vehiculosList = vehiculoService.consultarVehiculos(formulario, maxRows);
			model.addAttribute("vehiculosList", vehiculosList);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(WEB_PARAM_WARNING_MESSAGE, e.getMessage());
			model.addAttribute(WEB_PARAM_ERROR_FIELDS, errors.getAllErrors());			
		}
		
		return get(model);
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST, params="action=detalle")
	public String detalle(
			@RequestParam String filaplaca,			
			CargaArchivoDto cargaArchivoModel, BindingResult errors, Model model, Area247WebRequest area247WebRequest) {
		try {
			model.addAttribute("huellacarbonoModel", formulario);
			model.addAttribute("vehiculosList", vehiculosList);

			Vehiculo vehiculo = vehiculoService.findByPlaca(filaplaca);	
			if(null==vehiculo) {
				throw new Area247Exception("No existe un vehículo con placa "+filaplaca);
			}
				
			vehiculoDto = new VehiculoDto();			
			vehiculoDto.setVehiculo(vehiculo);			
			model.addAttribute("vehiculoDto", vehiculoDto);		
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(WEB_PARAM_WARNING_MESSAGE, e.getMessage());
			model.addAttribute(WEB_PARAM_ERROR_FIELDS, errors.getAllErrors());			
		}
		
		return get(model);
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST, params="action=estadisticas")
	public String estadisticas(
			@RequestParam String placa,
			@RequestParam String fecha,
			@RequestParam String fechaHasta,
			CargaArchivoDto cargaArchivoModel, BindingResult errors, Model model, Area247WebRequest area247WebRequest) {
		try {
			
//			formulario.setPlaca(FormatUtils.getVal(vehiculoDto.getVehiculo().getPlaca()));
			formulario.setPlaca(FormatUtils.getVal(placa));
			formulario.setFecha(FormatUtils.getDateVal(fecha));
			formulario.setFechaHasta(FormatUtils.getDateVal(fechaHasta));
			
			Vehiculo vehiculo = validarEstadisticas();
			
			model.addAttribute("huellacarbonoModel", formulario);
			model.addAttribute("vehiculosList", vehiculosList);

			BigDecimal distanciaRecorrida = vehiculoService.distanciaRecorrida(formulario.getPlaca(), fecha);
			
			vehiculoDto = new VehiculoDto();			
			vehiculoDto.setVehiculo(vehiculo);
			vehiculoDto.setDetecciones(vehiculoService.consultarDetecciones(formulario.getPlaca(), fecha));
			vehiculoDto.setPorcentajeCarroceria(vehiculoService.porcentajeCarroceria(formulario.getPlaca(), fecha)+" %");			
			vehiculoDto.setDistanciaRecorrida((new DecimalFormat("#0.00")).format(distanciaRecorrida)+" kms");
			if(distanciaRecorrida.doubleValue() > 0) {
				Double huellaCarbono = vehiculoService.porcentajeHuellaCarbono(formulario.getPlaca(), fecha);
				vehiculoDto.setPorcentajeHuellaCarbono((new DecimalFormat("#0.00")).format(huellaCarbono)+" %");				
			}
			
			model.addAttribute("vehiculoDto", vehiculoDto);		
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(WEB_PARAM_WARNING_MESSAGE, e.getMessage());
			model.addAttribute(WEB_PARAM_ERROR_FIELDS, errors.getAllErrors());			
		}
		
		return get(model);
	}

	private Vehiculo validarEstadisticas() {
		
		if(null==formulario.getPlaca() || "".equals(formulario.getPlaca().trim())) {
//			throw new Area247Exception("Debe seleccionar una placa para consultar sus estadísticas.");
			throw new Area247Exception("La placa es requerida");
		}
		
		Vehiculo vehiculo = vehiculoService.findByPlaca(formulario.getPlaca());	
		if(null==vehiculo) {
			throw new Area247Exception("No existe un vehículo con placa "+formulario.getPlaca());
		}
							
		Date hoy = new Date();
		
		Date fecha = FormatUtils.toDate(formulario.getFecha());
		if(null==fecha) {
			throw new Area247Exception("La Fecha Desde es requerida");
		}
		if(FormatUtils.fromDateIsAfterToDateWithoutTime(fecha, hoy)) {
			throw new Area247Exception("La Fecha Desde no puede ser posterior a la actual.");
		}
		
		Date fechaHasta = FormatUtils.toDate(formulario.getFechaHasta());
		if(null!=fechaHasta && FormatUtils.fromDateIsAfterToDateWithoutTime(fecha, fechaHasta)) {
			throw new Area247Exception("La Fecha Desde no puede ser posterior a la Fecha Hasta.");
		}
		
		return vehiculo;
	}
	
	private void setearFormulario(
			Long clase,
			Long carroceria,
			Long marca,
			String modelo,
			Long combustible,
			String capacidad,
			String placa,
			String fecha,
			String fechaHasta
			) {

		formulario.setClase(null);
		if(clase > 0) {
			for (TipoClaseVehiculo item : claseList) {
				if(item.getId().longValue() == clase.longValue()) {
					formulario.setClase(item.getNombre());
					break;
				}
			}					
		}
		
		formulario.setCarroceria(null);
		if(carroceria > 0) {
			for (TipoCarroceriaVehiculo item : carroceriaList) {
				if(item.getId().longValue() == carroceria.longValue()) {
					formulario.setCarroceria(item.getNombre());
					break;
				}
			}					
		}
		
		formulario.setMarca(null);
		if(marca > 0) {
			for (TipoMarcaVehiculo item : marcaList) {
				if(item.getId().longValue() == marca.longValue()) {
					formulario.setMarca(item.getNombre());
					break;
				}
			}					
		}
		
		formulario.setModelo(FormatUtils.getVal(modelo));
		
		formulario.setCombustible(null);
		if(combustible > 0) {
			for (TipoCombustibleVehiculo item : combustibleList) {
				if(item.getId().longValue() == combustible.longValue()) {
					formulario.setCombustible(item.getNombre());
					break;
				}
			}					
		}
		
		formulario.setCapacidad(FormatUtils.getNumericVal(capacidad));
		formulario.setPlaca(FormatUtils.getVal(placa));
		formulario.setFecha(FormatUtils.getDateVal(fecha));
		formulario.setFechaHasta(FormatUtils.getDateVal(fechaHasta));
	}
		
}
