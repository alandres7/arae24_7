package co.gov.metropol.area247.core.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.core.service.AplicacionService;
import co.gov.metropol.area247.core.service.CapaService;
import co.gov.metropol.area247.core.service.CategoriaService;

@RequestMapping(value = "/api")
@RestController
public class BaseRestController {
	
	@Autowired
	private AplicacionService aplicacionService;

	@Autowired
	private CapaService capaService;
	
	public AplicacionService getAplicacionService() {
		return aplicacionService;
	}

	public CapaService getCapaService() {
		return capaService;
	}

}
