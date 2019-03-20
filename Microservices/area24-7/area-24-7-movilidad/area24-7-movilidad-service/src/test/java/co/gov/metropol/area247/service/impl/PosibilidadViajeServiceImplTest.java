package co.gov.metropol.area247.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.gateway.IPosibilidadViajeServiceGateway;
import co.gov.metropol.area247.repository.custom.RutaRepositoryCustom;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.services.rest.opt.CalleWSDTO;
import co.gov.metropol.area247.services.rest.opt.EstacionWSDTO;
import co.gov.metropol.area247.services.rest.opt.ItinerarioWSDTO;
import co.gov.metropol.area247.services.rest.opt.PlanWSDTO;
import co.gov.metropol.area247.services.rest.opt.PosibilidadViajeWSDTO;
import co.gov.metropol.area247.services.rest.opt.TrayectoWSDTO;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@RunWith(MockitoJUnitRunner.class)
public class PosibilidadViajeServiceImplTest {

	private static final double ORIGEN_LONGITUD = 6.249435170811115;
	private static final double ORIGEN_LATITUD = -75.56619644165039;
	private static final double DESTINO_LONGITUD = 6.285780495170542;
	private static final double DESTINO_LATITUD = -75.55469512939453;
	private static final Date FECHA = Calendar.getInstance().getTime();

	private Coordenada origen;
	private Coordenada destino;
	private PosibilidadViajeWSDTO wsdto;
	private PlanWSDTO planWSDTO;
	private List<ItinerarioWSDTO> itinerariosWSDTO;
	private List<TrayectoWSDTO> trayectosWSDTO;

	@InjectMocks
	private PosibilidadViajeServiceImpl posibilidadViajeService = new PosibilidadViajeServiceImpl();

	@Mock
	private IPosibilidadViajeServiceGateway posibilidadViajeGateway;
	
	@Mock
	private RutaRepositoryCustom rutaRepositoryCustom;

	@Before
	public void prepare() {

		origen = new Coordenada(ORIGEN_LATITUD, ORIGEN_LONGITUD);
		destino = new Coordenada(DESTINO_LATITUD, DESTINO_LONGITUD);

		wsdto = new PosibilidadViajeWSDTO();
		planWSDTO = new PlanWSDTO();
		itinerariosWSDTO = new ArrayList<>();
		trayectosWSDTO = new ArrayList<>();

		// Camino 1
		ItinerarioWSDTO camino1 = crearCamino1();
		ItinerarioWSDTO camino2 = crearCamino2();
		ItinerarioWSDTO camino3 = crearCamino3();

		camino1.setTrayectos(trayectosWSDTO);
		itinerariosWSDTO.add(0, camino1);
		itinerariosWSDTO.add(1, camino2);
		itinerariosWSDTO.add(2, camino3);

		planWSDTO.setItinerarios(itinerariosWSDTO);

		wsdto.setCodigo(HttpStatus.OK.value());
		wsdto.setMensaje("Mensaje de exito");
		;
		wsdto.setError(null);
		wsdto.setPlan(planWSDTO);

		planWSDTO.setItinerarios(itinerariosWSDTO);
	}


	@Test
	public void testToValoresEnteros() {
//		int[] valoresEnteros = PosibilidadViajeServiceImpl.toValoresEnteros("1", "2", "3");
//		assertArrayEquals(new int[] { 1, 2, 3 }, valoresEnteros);
	}

	@Test
	public void testEstandarizarTipoRutas() {
		String tiposEstadarizados = null;
		// No debe haber excepciones a valores nulos
		tiposEstadarizados = PosibilidadViajeServiceImpl.estandarizarTipoRutas(null);
		assertNull(tiposEstadarizados);
		tiposEstadarizados = PosibilidadViajeServiceImpl.estandarizarTipoRutas(ModoRecorrido.TRANSPORTE_PUBLICO,
				ModoRecorrido.CAMINAR);
		assertEquals("TRANSIT,WALK",
				ModoRecorrido.TRANSPORTE_PUBLICO.getModoOTP() + Constantes.COMA + ModoRecorrido.CAMINAR.getModoOTP());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRecomendarOtraOpcionViajeExcepciones() {
		// Ningun argumento debe ser nulo
		posibilidadViajeService.recomendarOtraOpcionViaje(null, null, null);
	}

	@Test
	public void testRecomendarOtraOpcionViaje() {
		when(posibilidadViajeGateway.consultarPosiblesViajes(anyObject(), anyDouble(), anyDouble(), anyDouble(),
				anyDouble(), anyString())).thenReturn(wsdto);
		PosibilidadViajeWSDTO viajeWsdto = posibilidadViajeService.recomendarOtraOpcionViaje(origen, destino, FECHA);
		assertNotNull(viajeWsdto);
		assertEquals(HttpStatus.SEE_OTHER.value(), viajeWsdto.getCodigo());
		assertEquals("metro", viajeWsdto.getMensaje());
	}

//	@Test(expected = NullPointerException.class)
//	public void testAjustarPosibilidadViajeWSDTOExcepciones() {
//		posibilidadViajeService.ajustarPosibilidadViajeWSDTO(null);
//	}

	@Test
	public void testAjustarPosibilidadViajeWSDTO() {
//		PosibilidadViajeWSDTO wsdtoAux = posibilidadViajeService.ajustarPosibilidadViajeWSDTO(wsdto);
//		assertNotNull(wsdtoAux);
//		assertEquals(HttpStatus.OK.value(), wsdto.getCodigo());
//		assertTrue("Deben existir recorridos", !wsdto.getPlan().getItinerarios().isEmpty());
//		// Deben haber 2 caminos, el 1ro con menor distancia y el 2do con menor
//		// tiempo
//		assertTrue(wsdto.getPlan().getItinerarios().size() == 2);
//		// validamos que el primero cumpla con la menor distancia
//		assertTrue(wsdto.getPlan().getItinerarios().get(0).getDistanciaViaje() < wsdto.getPlan().getItinerarios().get(1)
//				.getDistanciaViaje());
//		// validamos que el segundo cumpla con el menor tiempo
//		assertTrue(wsdto.getPlan().getItinerarios().get(1).getTiempoViaje() < wsdto.getPlan().getItinerarios().get(0)
//				.getTiempoViaje());
	}

	@Test(expected = Area247Exception.class)
	public void testConsultarPosiblesViajesExcepciones() {
		posibilidadViajeService.consultarPosiblesViajes(null, null, null, null);
	}
	
	public void testCalcularTaritaItinerario() {}
	
	@Test
	public void testAgruparModosRecorridoParaTafifas() {
		
		
		TrayectoWSDTO t1, t2, t3, t4, t5, t6;
		t1 = new TrayectoWSDTO();
		t1.setModo("WALK");
		t1.setDistancia(50.0);
		t2 = new TrayectoWSDTO();
		t2.setModo("FERRY");
		t3 = new TrayectoWSDTO();
		t3.setModo("RAIL");
		t4 = new TrayectoWSDTO();
		t4.setModo("SUBWAY");
		t5 = new TrayectoWSDTO();
		t5.setModo("BUS");
		
		
		List<TrayectoWSDTO> trayectosWSDTO = Lists.newArrayList(t1, t2, t1, t3, t1, t4, t1, t5, t1, t2);
		List<List<Long>> agrupado = posibilidadViajeService.agruparModosRecorridoParaTafifas(trayectosWSDTO);
		assertTrue(agrupado.size() == 3);
		
	}

	public void testConsultarPosiblesViajes() {
		when(posibilidadViajeGateway.consultarPosiblesViajes(anyObject(), anyDouble(), anyDouble(), anyDouble(),
				anyDouble(), anyString())).thenReturn(wsdto);
//		PosibilidadViajeWSDTO viajeWsdto = posibilidadViajeService.consultarPosiblesViajes(origen, destino, FECHA,
//				"9,0");
//		assertNotNull(viajeWsdto);
	}

	private ItinerarioWSDTO crearCamino1() {

		// Camino 1
		ItinerarioWSDTO camino1 = new ItinerarioWSDTO();
		camino1.setDistanciaViaje(900.0);
		camino1.setTiempoViaje(2000L);
		camino1.setTiempoCaminando(695L);
		camino1.setHoraSalidaOrigen(new Date());
		camino1.setHoraLLegadaDestino(new Date());
		// Trayecto 1 - Camino 1
		TrayectoWSDTO trayecto1 = new TrayectoWSDTO();
		trayecto1.setHoraSalida(new Date(1513692670000L));
		trayecto1.setHoraLlegada(new Date(1513692873000L));
		trayecto1.setDistancia(255.36400000000003);
		trayecto1.setModo("WALK");
		trayecto1.setDuracion(203L);
		// Punto de inicio del trayecto
		EstacionWSDTO desde = new EstacionWSDTO();
		desde.setNombre("Origin");
		desde.setLatitud(6.24841);
		desde.setLongitud(-75.57066);
		desde.setTipoVertice("NORMAL");
		trayecto1.setDesde(desde);
		// Punto final del trayecto
		EstacionWSDTO hasta = new EstacionWSDTO();
		hasta.setNombre("San Antonio");
		hasta.setId("1:SAA");
		hasta.setLatitud(6.247132);
		hasta.setLongitud(-75.569828);
		hasta.setTipoVertice("TRANSIT");
		trayecto1.setHasta(hasta);
		// Calle 1 - Trayecto 1 - Camino 1
		CalleWSDTO calle1 = new CalleWSDTO();
		calle1.setDistancia(88.776);
		calle1.setNombre("Carrera 52A;La Alhambra");
		calle1.setOrientacionRelativa("DEPART");
		calle1.setOrientacionAbsoluta("SOUTH");
		calle1.setLongitud(-75.57074571289799);
		calle1.setLatitud(6.248441459337089);
		// Calle 2 - Trayecto 1 - Camino 1
		CalleWSDTO calle2 = new CalleWSDTO();
		calle2.setDistancia(145.853);
		calle2.setNombre("Calle 46;Maturna");
		calle2.setOrientacionRelativa("LEFT");
		calle2.setOrientacionAbsoluta("EAST");
		calle2.setLongitud(-75.5710239);
		calle2.setLatitud(6.2476925);

		List<CalleWSDTO> calles = new ArrayList<>();
		calles.add(calle1);
		calles.add(calle2);
		trayecto1.setCalles(calles);
		trayectosWSDTO.add(trayecto1);

		// Trayecto 2 - Camino 1
		TrayectoWSDTO trayecto2 = new TrayectoWSDTO();
		trayecto2.setHoraSalida(new Date(1513692874000L));
		trayecto2.setHoraLlegada(new Date(1513693449000L));
		trayecto2.setDistancia(5172.16);
		trayecto2.setModo("RAIL");
		trayecto2.setDuracion(575L);
		// Punto de inicio del trayecto
		EstacionWSDTO desde2 = new EstacionWSDTO();
		desde2.setNombre("San Antonio");
		desde2.setId("1:SAA");
		desde2.setLatitud(6.247132);
		desde2.setLongitud(-75.569828);
		desde2.setTipoVertice("TRANSIT");
		trayecto2.setDesde(desde2);
		// Punto final del trayecto
		EstacionWSDTO hasta2 = new EstacionWSDTO();
		hasta2.setNombre("Tricentenario");
		hasta2.setId("1:TRI");
		hasta2.setLatitud(6.290361);
		hasta2.setLongitud(-75.564716);
		hasta2.setTipoVertice("TRANSIT");
		trayecto2.setHasta(hasta2);

		trayectosWSDTO.add(trayecto2);

		camino1.setTrayectos(trayectosWSDTO);

		return camino1;
	}

	private ItinerarioWSDTO crearCamino2() {

		// Camino 1
		ItinerarioWSDTO camino2 = new ItinerarioWSDTO();
		camino2.setDistanciaViaje(1000.0);
		camino2.setTiempoViaje(1000L);
		camino2.setTiempoCaminando(695L);
		camino2.setHoraSalidaOrigen(new Date());
		camino2.setHoraLLegadaDestino(new Date());
		// Trayecto 1 - Camino 1
		TrayectoWSDTO trayecto1 = new TrayectoWSDTO();
		trayecto1.setHoraSalida(new Date(1513692670000L));
		trayecto1.setHoraLlegada(new Date(1513692873000L));
		trayecto1.setDistancia(255.36400000000003);
		trayecto1.setModo("WALK");
		trayecto1.setDuracion(203L);
		// Punto de inicio del trayecto
		EstacionWSDTO desde = new EstacionWSDTO();
		desde.setNombre("Origin");
		desde.setLatitud(6.24841);
		desde.setLongitud(-75.57066);
		desde.setTipoVertice("NORMAL");
		trayecto1.setDesde(desde);
		// Punto final del trayecto
		EstacionWSDTO hasta = new EstacionWSDTO();
		hasta.setNombre("San Antonio");
		hasta.setId("1:SAA");
		hasta.setLatitud(6.247132);
		hasta.setLongitud(-75.569828);
		hasta.setTipoVertice("TRANSIT");
		trayecto1.setHasta(hasta);
		// Calle 1 - Trayecto 1 - Camino 1
		CalleWSDTO calle1 = new CalleWSDTO();
		calle1.setDistancia(88.776);
		calle1.setNombre("Carrera 52A;La Alhambra");
		calle1.setOrientacionRelativa("DEPART");
		calle1.setOrientacionAbsoluta("SOUTH");
		calle1.setLongitud(-75.57074571289799);
		calle1.setLatitud(6.248441459337089);
		// Calle 2 - Trayecto 1 - Camino 1
		CalleWSDTO calle2 = new CalleWSDTO();
		calle2.setDistancia(145.853);
		calle2.setNombre("Calle 46;Maturna");
		calle2.setOrientacionRelativa("LEFT");
		calle2.setOrientacionAbsoluta("EAST");
		calle2.setLongitud(-75.5710239);
		calle2.setLatitud(6.2476925);

		List<CalleWSDTO> calles = new ArrayList<>();
		calles.add(calle1);
		calles.add(calle2);
		trayecto1.setCalles(calles);
		List<TrayectoWSDTO> trayectosWSDTO = new ArrayList<>();
		trayectosWSDTO.add(trayecto1);

		// Trayecto 2 - Camino 1
		TrayectoWSDTO trayecto2 = new TrayectoWSDTO();
		trayecto2.setHoraSalida(new Date(1513692874000L));
		trayecto2.setHoraLlegada(new Date(1513693449000L));
		trayecto2.setDistancia(5172.16);
		trayecto2.setModo("RAIL");
		trayecto2.setDuracion(575L);
		// Punto de inicio del trayecto
		EstacionWSDTO desde2 = new EstacionWSDTO();
		desde2.setNombre("San Antonio");
		desde2.setId("1:SAA");
		desde2.setLatitud(6.247132);
		desde2.setLongitud(-75.569828);
		desde2.setTipoVertice("TRANSIT");
		trayecto2.setDesde(desde2);
		// Punto final del trayecto
		EstacionWSDTO hasta2 = new EstacionWSDTO();
		hasta2.setNombre("Tricentenario");
		hasta2.setId("1:TRI");
		hasta2.setLatitud(6.290361);
		hasta2.setLongitud(-75.564716);
		hasta2.setTipoVertice("TRANSIT");
		trayecto2.setHasta(hasta2);

		trayectosWSDTO.add(trayecto2);

		camino2.setTrayectos(trayectosWSDTO);

		return camino2;
	}

	private ItinerarioWSDTO crearCamino3() {

		// Camino 1
		ItinerarioWSDTO camino2 = new ItinerarioWSDTO();
		camino2.setDistanciaViaje(5000.0);
		camino2.setTiempoViaje(5000L);
		camino2.setTiempoCaminando(695L);
		camino2.setHoraSalidaOrigen(new Date());
		camino2.setHoraLLegadaDestino(new Date());
		// Trayecto 1 - Camino 1
		TrayectoWSDTO trayecto1 = new TrayectoWSDTO();
		trayecto1.setHoraSalida(new Date(1513692670000L));
		trayecto1.setHoraLlegada(new Date(1513692873000L));
		trayecto1.setDistancia(255.36400000000003);
		trayecto1.setModo("WALK");
		trayecto1.setDuracion(203L);
		// Punto de inicio del trayecto
		EstacionWSDTO desde = new EstacionWSDTO();
		desde.setNombre("Origin");
		desde.setLatitud(6.24841);
		desde.setLongitud(-75.57066);
		desde.setTipoVertice("NORMAL");
		trayecto1.setDesde(desde);
		// Punto final del trayecto
		EstacionWSDTO hasta = new EstacionWSDTO();
		hasta.setNombre("San Antonio");
		hasta.setId("1:SAA");
		hasta.setLatitud(6.247132);
		hasta.setLongitud(-75.569828);
		hasta.setTipoVertice("TRANSIT");
		trayecto1.setHasta(hasta);
		// Calle 1 - Trayecto 1 - Camino 1
		CalleWSDTO calle1 = new CalleWSDTO();
		calle1.setDistancia(88.776);
		calle1.setNombre("Carrera 52A;La Alhambra");
		calle1.setOrientacionRelativa("DEPART");
		calle1.setOrientacionAbsoluta("SOUTH");
		calle1.setLongitud(-75.57074571289799);
		calle1.setLatitud(6.248441459337089);
		// Calle 2 - Trayecto 1 - Camino 1
		CalleWSDTO calle2 = new CalleWSDTO();
		calle2.setDistancia(145.853);
		calle2.setNombre("Calle 46;Maturna");
		calle2.setOrientacionRelativa("LEFT");
		calle2.setOrientacionAbsoluta("EAST");
		calle2.setLongitud(-75.5710239);
		calle2.setLatitud(6.2476925);

		List<CalleWSDTO> calles = new ArrayList<>();
		calles.add(calle1);
		calles.add(calle2);
		trayecto1.setCalles(calles);
		List<TrayectoWSDTO> trayectosWSDTO = new ArrayList<>();
		trayectosWSDTO.add(trayecto1);

		// Trayecto 2 - Camino 1
		TrayectoWSDTO trayecto2 = new TrayectoWSDTO();
		trayecto2.setHoraSalida(new Date(1513692874000L));
		trayecto2.setHoraLlegada(new Date(1513693449000L));
		trayecto2.setDistancia(5172.16);
		trayecto2.setModo("RAIL");
		trayecto2.setDuracion(575L);
		// Punto de inicio del trayecto
		EstacionWSDTO desde2 = new EstacionWSDTO();
		desde2.setNombre("San Antonio");
		desde2.setId("1:SAA");
		desde2.setLatitud(6.247132);
		desde2.setLongitud(-75.569828);
		desde2.setTipoVertice("TRANSIT");
		trayecto2.setDesde(desde2);
		// Punto final del trayecto
		EstacionWSDTO hasta2 = new EstacionWSDTO();
		hasta2.setNombre("Tricentenario");
		hasta2.setId("1:TRI");
		hasta2.setLatitud(6.290361);
		hasta2.setLongitud(-75.564716);
		hasta2.setTipoVertice("TRANSIT");
		trayecto2.setHasta(hasta2);

		trayectosWSDTO.add(trayecto2);

		camino2.setTrayectos(trayectosWSDTO);

		return camino2;
	}
	
	@Test
	public void testFijarCoordenadasRuta() {
		
		TrayectoWSDTO trayecto = new TrayectoWSDTO();
		trayecto.setCodigoRuta("L1");
		trayecto.setModo("BUS");
		EstacionWSDTO desde = new EstacionWSDTO();
		desde.setLatitud(6.263875286743809);
		desde.setLongitud(-75.56768033439862);
		EstacionWSDTO hasta = new EstacionWSDTO();
		hasta.setLatitud(6.230712430388882);
		hasta.setLongitud(-75.60908086072205);
		trayecto.setDesde(desde);
		trayecto.setHasta(hasta);

		Coordinate punto1 = new Coordinate(6.231011, -75.609466);
		Coordinate punto2 = new Coordinate(6.230984, -75.605646);
		Coordinate punto3 = new Coordinate(6.230888, -75.602358);
		Coordinate punto4 = new Coordinate(6.231406, -75.577773);
		Coordinate punto5 = new Coordinate(6.245734, -75.575225);
		Coordinate punto6 = new Coordinate(6.264366, -75.567489);

		Coordinate[] trazo = new Coordinate[] { punto1, punto2, punto3, punto4, punto5, punto6 };
		
		when(rutaRepositoryCustom.obtenerRutaEntreCoordenadas(trayecto.getCodigoRuta(),
				trayecto.getDesde().getLatitud(), trayecto.getDesde().getLongitud(),
				trayecto.getHasta().getLatitud(), trayecto.getHasta().getLongitud(), "M")).thenReturn(trazo);
		posibilidadViajeService.fijarCoordenadasRuta(trayecto);
		assertEquals(punto6, trayecto.getCoordenadas().get(0));
		
	}
}