package co.gov.metropol.area247.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.repository.RutaRepository;
import co.gov.metropol.area247.repository.domain.support.enums.ClasificacionInformacion;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.service.IPuntoTarjetaCivicaService;
import co.gov.metropol.area247.service.IRutaService;
import co.gov.metropol.area247.util.web.Coordenada;

@RunWith(MockitoJUnitRunner.class)
public class RutaServiceImplTest {

	protected static final double ORIGEN_LONGITUD = 6.249435170811115;
	protected static final double ORIGEN_LATITUD = -75.56619644165039;
	protected static final double RADIO = 2.0;

	@Mock
	private ILineaMetroService lineaMetroService;
	
	@Mock
	private IEstacionLineaMetroService estacionLineaMetroService;
	
	@Mock
	private IPuntoTarjetaCivicaService tarjetaCivicaService;
	
	@Mock
	private RutaRepository rutaRepository;

	@InjectMocks
	private IRutaService service = new RutaServiceImpl();

	@Before
	public void prepare() {
	}

	@Test
	public void obtenerRutasCercanasTest() {

		Coordenada coordenada = new Coordenada(ORIGEN_LATITUD, ORIGEN_LONGITUD, RADIO);

		List<ModoRecorrido> modosTransporte = Lists
				.newArrayList(new ModoRecorrido[] { ModoRecorrido.METRO_CABLE, ModoRecorrido.METRO });
		List<Integer> modosTransporteInt = Arrays
				.asList(new Integer[] { ModoRecorrido.METRO_CABLE.indice(), ModoRecorrido.METRO.indice() });

		when(lineaMetroService.obtenerlineasCercanas(coordenada, modosTransporte)).thenReturn(Lists.newArrayList());
		when(estacionLineaMetroService.obtenerEstacionesCercanas(coordenada, Lists.newArrayList()))
				.thenReturn(Lists.newArrayList());
		when(tarjetaCivicaService.obtenerEstacionesCercanas(ORIGEN_LATITUD, ORIGEN_LONGITUD, RADIO))
				.thenReturn(Lists.newArrayList());
		when(rutaRepository.rutaCercana(ORIGEN_LATITUD, ORIGEN_LONGITUD, RADIO)).thenReturn(Lists.newArrayList());
		when(rutaRepository.rutaCercana(ORIGEN_LATITUD, ORIGEN_LONGITUD, RADIO, Lists.newArrayList()))
				.thenReturn(Lists.newArrayList());

		Map<ClasificacionInformacion, List<Object>> informacion = service
				.obtenerInformacionTransporteCercano(coordenada, modosTransporteInt);
		
		assertNotNull(informacion);

	}

}
