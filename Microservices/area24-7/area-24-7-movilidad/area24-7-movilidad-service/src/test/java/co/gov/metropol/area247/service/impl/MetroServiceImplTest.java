package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.gov.metropol.area247.gateway.IMetroServiceGateway;
import co.gov.metropol.area247.gateway.impl.MetroServiceGatewayImpl;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.service.IMetroService;
import co.gov.metropol.area247.services.rest.metro.LineaMetroWSDTO;

@RunWith(MockitoJUnitRunner.class)
public class MetroServiceImplTest {

	@InjectMocks
	private IMetroServiceGateway metroServiceGateway = new MetroServiceGatewayImpl();
	
	@Mock
	private IMetroService metroService;
	
	@Mock
	private  ILineaMetroService lineaMetroService;
	
	@Mock
	private IEstacionLineaMetroService estacionLineaMetroService;
	
	@Mock
	private List<LineaMetroWSDTO> lineasWSDTO;
	
	
	@Test
	public void cargarDatosTest() {
//		 metroService.cargarDatosMetro();	 
	}
	
	@Test
	public void procesarLineasTest() {
//		Mockito.doThrow(new NullPointerException()).when(metroService).procesarLineas(lineasWSDTO);
	}
}
