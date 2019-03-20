package co.gov.metropol.area247.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.mapper.EstacionLineaMetroMapper;
import co.gov.metropol.area247.repository.EstacionLineaMetroRepository;
import co.gov.metropol.area247.repository.domain.EstacionMetro;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@RunWith(MockitoJUnitRunner.class)
public class EstacionLineaMetroServiceImplTest {

	private static final Long ID = 1l;
	private static final String CODIGO = "NIQ";
	private static final String DESCRIPCION = "Niquia";
	private static final Double LATITUD = 6.337804;
	private static final Double LONGITUD = -75.544299;

	private static final long ID_NO_ENCONTRADO = 100;

	@InjectMocks
	private IEstacionLineaMetroService estacionLineaMetroService = new EstacionLineaMetroServiceImpl();

	@Mock
	private EstacionLineaMetroMapper estacionLineaMetroMapper;

	@Mock
	private EstacionLineaMetroRepository estacionLineaMetroRepository;

	private EstacionMetro estacion;
	private EstacionMetro estacionNoEncontrada;
	private EstacionLineaMetroDTO estacionDTO;
	private EstacionLineaMetroDTO estacionDTONoMapeada;

	@Before
	public void prepare() throws Area247Exception {
		
		estacionNoEncontrada = null;
		estacionDTONoMapeada = null;
		
		estacion = new EstacionMetro();
		estacion.setId(ID);
		estacion.setCodigo(CODIGO);
		estacion.setDescripcion(DESCRIPCION);
		estacion.setLatitud(LATITUD);
		estacion.setLongitud(LONGITUD);

		estacionDTO = new EstacionLineaMetroDTO();
		estacionDTO.setId(ID);
		estacionDTO.setCodigo(CODIGO);
		estacionDTO.setDescripcion(DESCRIPCION);
		estacionDTO.setLatitud(LATITUD);
		estacionDTO.setLongitud(LONGITUD);

	}

	@Test
	public void findByEstacionLineaIdTest() {
		EstacionLineaMetroDTO estacionDTOAux;

		when(estacionLineaMetroRepository.findByIdEstacion(null)).thenReturn(estacionNoEncontrada);
		when(estacionLineaMetroRepository.findOne(null)).thenReturn(estacionNoEncontrada);
		estacionDTONoMapeada = estacionLineaMetroMapper.toEstacionLineaMetroDTO(estacionNoEncontrada);
		when(estacionLineaMetroMapper.toEstacionLineaMetroDTO(estacionNoEncontrada)).thenReturn(estacionDTONoMapeada);
		
		estacionDTOAux = estacionLineaMetroService.findByEstacionLineaId(null);
		assertEquals(estacionDTOAux, null);

		when(estacionLineaMetroRepository.findByIdEstacion(ID)).thenReturn(estacion);
		when(estacionLineaMetroMapper.toEstacionLineaMetroDTO(estacion)).thenReturn(estacionDTO);
		estacionDTOAux = estacionLineaMetroService.findByEstacionLineaId(ID);
		assertEquals(estacionDTOAux.getId(), ID);
		assertEquals(estacionDTOAux.getDescripcion(), DESCRIPCION);
		assertEquals(estacionDTOAux.getLatitud(), LATITUD);
		assertEquals(estacionDTOAux.getLongitud(), LONGITUD);

		when(estacionLineaMetroRepository.findByIdEstacion(ID_NO_ENCONTRADO)).thenReturn(estacionNoEncontrada);
		when(estacionLineaMetroMapper.toEstacionLineaMetroDTO(estacionNoEncontrada)).thenReturn(estacionDTONoMapeada);
		estacionDTOAux = estacionLineaMetroService.findByEstacionLineaId(ID_NO_ENCONTRADO);
		assertEquals(estacionDTOAux, null);

	}
	
	@Test
	public void saveEstacionMetroTest() {
//		when(estacionLineaMetroMapper.toEstacionMetro(estacionDTONoMapeada)).thenReturn(estacionNoEncontrada);
	}

}
