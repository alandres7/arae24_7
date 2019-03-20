package co.gov.metropol.area247.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import co.gov.metropol.area247.service.IGbfsEscrituraService;

@RunWith(MockitoJUnitRunner.class)
public class GbfsEnciclaEscrituraServiceImplTest {

	@InjectMocks
	private IGbfsEscrituraService gbfsEscrituraService = new GbfsEnciclaEscrituraServiceImpl();
	
	@Before
	public void prepare() {
		
	}
	
	@Test
	public void crearJsonSistemaInformacionTest() {
		
		gbfsEscrituraService.crearJsonSistemaInformacion();
		
	}
}
