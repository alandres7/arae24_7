package co.gov.metropol.area247.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ShapePoint;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.IGtfsEscrituraService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.util.web.Coordenada;

@RunWith(MockitoJUnitRunner.class)
public class GtfsLineaEscrituraServiceImplTest {
	
	LineaMetroDTO lineaA;
	List<EstacionLineaMetroDTO> estaciones;
	HorarioLineaMetroDTO horario;
	FrecuenciaLineaMetroDTO frecuencia;
	
	@InjectMocks
	private IGtfsEscrituraService lineaGtfsEscrituraService = new GtfsLineaEscrituraServiceImpl();
	
	@Mock
	private ILineaMetroService lineaMetroService;
	
	@Mock
	private IEstacionLineaMetroService estacionLineaMetroService;

	@Before
	public void prepare() {

		lineaA = new LineaMetroDTO();
		lineaA.setId(1l);
		lineaA.setCodigo("LA");
		lineaA.setDescripcion("Linea A");
		lineaA.setActivo('S');
		lineaA.setEnabled(true);
		lineaA.setModoLinea(ModoRecorrido.METRO);
		lineaA.setCoordenadas(GeometryUtil.obtenerLineString(Lists.newArrayList(
				new Coordenada(6.15290203194, -75.6262557128), new Coordenada(6.16163422618, -75.6091586596),
				new Coordenada(6.27116706409, -75.5699964766), new Coordenada(6.27116706409, -75.5699964766),
				new Coordenada(6.16163422618, -75.6091586596), new Coordenada(6.15290203194, -75.6262557128))));
		
		EstacionLineaMetroDTO estacionNiquia1 = new EstacionLineaMetroDTO();
		estacionNiquia1.setCodigo("ACE");
		estacionNiquia1.setDescripcion("Acevedo");
		estacionNiquia1.setLatitud(6.300145);
		estacionNiquia1.setLongitud(-75.558536);
		estacionNiquia1.setLineaDTO(lineaA);
		
		EstacionLineaMetroDTO estacionNiquia2 = new EstacionLineaMetroDTO();
		estacionNiquia2.setCodigo("ACE");
		estacionNiquia2.setDescripcion("Acevedo");
		estacionNiquia2.setLatitud(6.300145);
		estacionNiquia2.setLongitud(-75.558536);
		estacionNiquia2.setLineaDTO(new LineaMetroDTO().setIdLinea(2l));
		
		estaciones = new ArrayList<>();
		estaciones.add(estacionNiquia1);
		estaciones.add(estacionNiquia2);
		
		horario = new HorarioLineaMetroDTO();
		horario.setActivo("S");
		horario.setHoraInicioOperacion("04:16:00");
		horario.setHoraFinOperacion("23:22:03");

		frecuencia.setFrecuenciaMinimaValleDiurno(13.0);  // Vehiculos salientes por hora desde el origen
		frecuencia.setIntervaloMaximoValleDiurno(5.0);    // Minutos que le lleva un vehiculo a otro
		frecuencia.setIntervaloMaximoValleNocturno(16.0); // Minutos en los que se demora el vehiculo en realizar el recorrido

	}
	
	@Test
	public void testObtenerRutas() {
		
		List<LineaMetroDTO> lineasDTO = new ArrayList<LineaMetroDTO>();
		lineasDTO.add(lineaA);
		when(lineaMetroService.findAllActivas()).thenReturn(lineasDTO);
		Collection<Route> rutas = lineaGtfsEscrituraService.obtenerRutas();
		
		assertNotNull(rutas);
		assertTrue(rutas.size() > 0);
		Route lineaA = new ArrayList<Route>(rutas).get(0);
		// Campos obligatorios del archivo routes.txt y para OpenTripPlanner
		assertEquals(lineaA.getId().getId() ,this.lineaA.getCodigo());
		assertEquals(lineaA.getShortName()  ,this.lineaA.getCodigo());
		assertEquals(lineaA.getLongName()   ,this.lineaA.getDescripcion());
		assertEquals(lineaA.getDesc()   	,this.lineaA.getDescripcion());
		assertEquals(lineaA.getType()   	,this.lineaA.getModoLinea().indice());
				
	} 
	
	@Test
	public void testObtenerCoordenadasRuta() {
		
		List<LineaMetroDTO> lineasDTO = new ArrayList<LineaMetroDTO>();
		lineasDTO.add(lineaA);
		when(lineaMetroService.findAllActivas()).thenReturn(lineasDTO);
		Collection<ShapePoint> coordenadas = lineaGtfsEscrituraService.obtenerCoordenadas();
		assertNotNull(coordenadas);
		assertTrue(coordenadas.size() == 6);
		
	}
	
	@Test
	public void testObtenerParaderos() {
		
//		when(estacionLineaMetroService.findByActivo(Constantes.ACTIVO)).thenReturn(estaciones);
//		Set<Stop> paraderos = lineaGtfsEscrituraService.obtenerParaderos();
//		assertNotNull(paraderos);
//		assertTrue(paraderos.size()==1);
		
	}
	
}
