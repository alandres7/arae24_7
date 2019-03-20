package co.gov.metropol.area247.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.repository.LineaMetroRepository;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@RunWith(MockitoJUnitRunner.class)
public class LineaMetroServiceImplTest {

	@InjectMocks
	private ILineaMetroService service = new LineaMetroServiceImpl();
	
	@Mock
	private LineaMetroRepository repository;
	
	@Before
	public void prepare() {
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void obtenerlineasCercanasTestExcepcionArgumentoIlegal(){ 
		service.obtenerlineasCercanas(null, null);
	}
	
	@Test(expected = Area247Exception.class)
	public void obtenerlineasCercanasTestExcepcionArea247(){ 
		when(repository.lineaCercana(anyDouble(), anyDouble(), anyFloat(), anyObject())).thenThrow(Exception.class);
		when(repository.lineaCercana(anyDouble(), anyDouble(), anyFloat())).thenThrow(Exception.class);
		Coordenada coordenada = new Coordenada(RutaServiceImplTest.ORIGEN_LATITUD, RutaServiceImplTest.ORIGEN_LONGITUD, Float.valueOf("2"));
		List<ModoRecorrido> modosTransporte = Lists
				.newArrayList(new ModoRecorrido[] { ModoRecorrido.METRO_CABLE, ModoRecorrido.METRO });
		service.obtenerlineasCercanas(coordenada, modosTransporte);
	}
	
	@Test
	public void obtenerlineasCercanasTest(){ 
		
		Coordenada coordenada = new Coordenada(RutaServiceImplTest.ORIGEN_LATITUD, RutaServiceImplTest.ORIGEN_LONGITUD, Float.valueOf("2"));
		List<ModoRecorrido> modosTransporte = Lists
				.newArrayList(new ModoRecorrido[] { ModoRecorrido.METRO_CABLE, ModoRecorrido.METRO });
		
		when(repository.lineaCercana(anyDouble(), anyDouble(), anyFloat())).thenReturn(Lists.newArrayList());		
		List<LineaMetroDTO> lineasCercanas = service.obtenerlineasCercanas(coordenada, modosTransporte);
		assertNotNull(lineasCercanas);
		
	}
	
}
