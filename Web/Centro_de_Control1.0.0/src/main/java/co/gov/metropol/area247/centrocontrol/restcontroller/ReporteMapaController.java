package co.gov.metropol.area247.centrocontrol.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.gov.metropol.area247.centrocontrol.common.Area247WebRequest;
import co.gov.metropol.area247.centrocontrol.service.CentroControlExchange;

@Controller
@RequestMapping("/archivos/reporte/mapafotodetecciones")
public class ReporteMapaController extends BaseController {
	
	@Value("${area247.reporte.powerbi.groupId}")
	private String groupId;
	
	@Value("${area247.reporte.powerbi.mapaFotodeteccionesId}")
	private String reportId;

	@Autowired
	private CentroControlExchange centroControlExchange;
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model, Area247WebRequest area247WebRequest){
		
		model.addAttribute("reporteNombre", "Mapa Circulación");
		model.addAttribute("groupId", groupId);
		model.addAttribute("reportId", reportId);
		
		model.addAttribute("title", "Mapa Circulación");
		
		model.addAttribute("menuItemsList",centroControlExchange.obtenerMenus());
		
		return "area247/carga/powerbi";
	}
	

}
