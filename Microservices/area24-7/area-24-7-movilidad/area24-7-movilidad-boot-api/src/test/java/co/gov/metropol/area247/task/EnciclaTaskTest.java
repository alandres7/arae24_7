package co.gov.metropol.area247.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import co.gov.metropol.area247.gateway.IEnciclaServiceGateway;
import co.gov.metropol.area247.gateway.impl.EnciclaServiceGatewayImpl;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes;

@RunWith(SpringRunner.class)
@ContextConfiguration
@TestPropertySource("/routeServices.properties")
public class EnciclaTaskTest {

	@InjectMocks
	private IEnciclaServiceGateway enciclaServiceGateway = new EnciclaServiceGatewayImpl();
	

	@Test
	public void verificarConfiguracionRutasWSEnciclaTest() {
		
		String uri = null;
		
		// Ruta de WS para consultar el estatus de encicla
		uri = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routeEnciclaStatusService");
		assertNotNull(uri);
		assertFalse(uri.isEmpty());
		// Ruta de WS para consultar las ciclorutas
		
		uri = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routeEnciclaConsultarCicloRutas");
		assertNotNull(uri);
		assertFalse(uri.isEmpty());
		
	}
	
	@Test
	public void verificarRespuestaGeneradaWSEnciclaTest() { 
//		EnciclaWSDTO wsdto = enciclaServiceGateway.consultarEstatusEncicla();
//		assertNotNull(wsdto);
//		assertNotNull(wsdto.getDate());
//		assertNotNull(wsdto.getStations());
//		assertFalse(wsdto.getStations().isEmpty());
	}
		

}
