package co.gov.metropol.area247.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import co.gov.metropol.area247.gateway.IGtpcServiceGateway;
import co.gov.metropol.area247.gateway.impl.GtpcServiceGatewayImpl;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes;

@RunWith(SpringRunner.class)
@ContextConfiguration
@TestPropertySource("/routeServices.properties")
public class GtpcTaskTest {

	@InjectMocks
	private IGtpcServiceGateway gtpcServiceGateway = new GtpcServiceGatewayImpl();

	@Test
	public void verificarConfiguracionRutasWSGtpcTest() {

		String uri = null;

		uri = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES, "bootApi.gateway.routeGtpcService");
		assertNotNull(uri);
		assertFalse(uri.isEmpty());

	}

	@Test
	public void verificarRespuestaGeneradaWSGtpcTest() {
//		RutaGtpcWSDTO rutaGtpcWSDTO = gtpcServiceGateway.cargarInformacionRutas();
//		assertNotNull(rutaGtpcWSDTO);
	}
	
	

}
