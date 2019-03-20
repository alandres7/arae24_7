package co.gov.metropol.area247.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.gov.metropol.area247.gateway.IEnciclaServiceGateway;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.model.mapper.EstacionEnciclaMapper;
import co.gov.metropol.area247.repository.DareviaZonaRepository;
import co.gov.metropol.area247.repository.TareviaEstacionEnciclaRepository;
import co.gov.metropol.area247.repository.domain.DareviaZona;
import co.gov.metropol.area247.repository.domain.TareviaEstacionEncicla;
import co.gov.metropol.area247.service.IEnciclaService;
import co.gov.metropol.area247.services.rest.encicla.EnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.EstacionEnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.ZonaCiudadEnciclaWSDTO;

@RunWith(MockitoJUnitRunner.class)
public class EnciclaServiceImplTest {
	
	@InjectMocks
	private IEnciclaService enciclaService = new EnciclaServiceImpl();
	
	@Mock
	private IEnciclaServiceGateway enciclaServiceGateway;
	
	@Mock
	private DareviaZonaRepository zonaRepository;
	
	@Mock
	private EstacionEnciclaMapper mapper;
	
	@Mock
	private TareviaEstacionEnciclaRepository EstacionRepository;

	@Before
	public void prepare() {
		
	}
	
	@Test
	public void cargarEstacionesEnciclaTest() {
		
		EnciclaWSDTO enciclaWSDTO = new EnciclaWSDTO();
		enciclaWSDTO.setDate(Calendar.getInstance().getTimeInMillis() / 1000);
		
		ZonaCiudadEnciclaWSDTO zonaCiudadEnciclaWSDTO1 = new ZonaCiudadEnciclaWSDTO();
		zonaCiudadEnciclaWSDTO1.setId("1");
		zonaCiudadEnciclaWSDTO1.setName("ZONA1");
		zonaCiudadEnciclaWSDTO1.setDesc("Descripcion Zona 1");
		
		EstacionEnciclaWSDTO estacionEnciclaWSDTO1 = new EstacionEnciclaWSDTO();
		estacionEnciclaWSDTO1.setId("1");
		estacionEnciclaWSDTO1.setName("ESTACION1");
		estacionEnciclaWSDTO1.setDescription("Descripcion Estacion 1");
		estacionEnciclaWSDTO1.setAddress("Cll 1 no 2 - 3");
		estacionEnciclaWSDTO1.setLat("1.1");
		estacionEnciclaWSDTO1.setLon("2.1");
		estacionEnciclaWSDTO1.setCapacity(10l);
		estacionEnciclaWSDTO1.setType("TIPO");
		estacionEnciclaWSDTO1.setBikes(9l);
		estacionEnciclaWSDTO1.setPlaces(3l);
		List<EstacionEnciclaWSDTO> estaciones = new ArrayList<>();
		estaciones.add(estacionEnciclaWSDTO1);
		
		zonaCiudadEnciclaWSDTO1.setItems(estaciones);
		List<ZonaCiudadEnciclaWSDTO> zonas = new ArrayList<>();
		zonas.add(zonaCiudadEnciclaWSDTO1);
		
		enciclaWSDTO.setStations(zonas);
		
		DareviaZona dareviaZona = new DareviaZona();
		dareviaZona.setId(1l);
		dareviaZona.setIdZona(1l);
		dareviaZona.setNombreZona("ZONA1");
		dareviaZona.setDescripcionZona("Descripcion Zona 1");
		dareviaZona.setEnabled(true);
		
		TareviaEstacionEncicla tareviaEstacionEncicla = new TareviaEstacionEncicla();
		tareviaEstacionEncicla.setId(1l);
		tareviaEstacionEncicla.setNombreEstacionEncicla("ESTACION1");
		tareviaEstacionEncicla.setDescripcionEstacionEncicla("Estacion 1");
		tareviaEstacionEncicla.setLatitudEstacionEncila(6.300753007248462);
		tareviaEstacionEncicla.setLongitudEstacionEncila(-75.55823564529419);
		
		TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO = new TareviaEstacionEnciclaDTO();
		tareviaEstacionEnciclaDTO.setId(1l);
		tareviaEstacionEnciclaDTO.setNombreEstacionEncicla("ESTACION1");
		tareviaEstacionEnciclaDTO.setDescripcionEstacionEncicla("Estacion 1");
		tareviaEstacionEnciclaDTO.setLatitudEstacionEncila(6.300753007248462);
		tareviaEstacionEncicla.setLongitudEstacionEncila(-75.55823564529419);
		
		// Para procesar 
		when(enciclaServiceGateway.consultarEstatusEncicla()).thenReturn(enciclaWSDTO);
		when(zonaRepository.findByIdZona(1l)).thenReturn(null);
		when(mapper.toDareviaZona(anyObject())).thenReturn(dareviaZona);
		// Para procesar 
		when(EstacionRepository.findByIdEstacionEncicla(1l)).thenReturn(null);
		when(mapper.toTareviaEstacionEnciclaDTO(tareviaEstacionEncicla)).thenReturn(tareviaEstacionEnciclaDTO);
		
		enciclaService.cargarEstacionesEncicla();
		verify(mapper).toDareviaZona(anyObject());
		verify(mapper).toTareviaEstacionEstacion(anyObject());
	}
	
}
