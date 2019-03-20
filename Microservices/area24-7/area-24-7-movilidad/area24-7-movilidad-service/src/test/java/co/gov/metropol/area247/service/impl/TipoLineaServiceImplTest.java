package co.gov.metropol.area247.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.gov.metropol.area247.model.TipoLineaDTO;
import co.gov.metropol.area247.model.mapper.TipoLineaMapper;
import co.gov.metropol.area247.repository.TipoLineaRepository;
import co.gov.metropol.area247.repository.domain.TipoLinea;
import co.gov.metropol.area247.service.ITipoLineaService;

@RunWith(MockitoJUnitRunner.class)
public class TipoLineaServiceImplTest {
	
	@InjectMocks
	private ITipoLineaService tipoLineaService = new TipoLineaServiceImpl();
	
	@Mock
	private TipoLineaRepository tipoLineaRepository;
	
	@Mock
	private TipoLineaMapper mapper;
	
	private static final Long ID = 1l;
	private static final String NOMBRE = "METRO";
	private static final String DESCRIPCION = "Metro de Medellin";

	private static final long ID_NO_ENCONTRADO = 100;
	
	private TipoLinea tipoLinea;
	private TipoLineaDTO tipoLineaDTO;
	private TipoLinea tipoLineaNoEncontrada;
	private TipoLineaDTO tipoLineaDTONoEncontrada;
	
	@Before
	public void prepare() {
		tipoLineaNoEncontrada = null;
		tipoLineaDTONoEncontrada = null;
		
		tipoLinea = new TipoLinea();
		tipoLinea.setId(ID);
		tipoLinea.setNombre(NOMBRE);
		tipoLinea.setDescripcion(DESCRIPCION);
		
		tipoLineaDTO = new TipoLineaDTO();
		tipoLineaDTO.setId(ID);
		tipoLineaDTO.setNombre(NOMBRE);
		tipoLineaDTO.setDescripcion(DESCRIPCION);
	}

	@Test
	public void testFindByTipoLineaId() {
		final Long idNull = null;
		TipoLineaDTO dto;
		dto = tipoLineaService.findById(idNull);
		assertNull(dto);
		
		when(tipoLineaRepository.findById(ID_NO_ENCONTRADO)).thenReturn(tipoLineaNoEncontrada);
		when(mapper.toTipoLineaDTO(tipoLineaNoEncontrada)).thenReturn(tipoLineaDTONoEncontrada);
		dto = tipoLineaService.findById(ID_NO_ENCONTRADO);
		assertNull(dto);
		
		when(tipoLineaRepository.findById(ID)).thenReturn(tipoLinea);
		when(mapper.toTipoLineaDTO(tipoLinea)).thenReturn(tipoLineaDTO);
		dto = tipoLineaService.findById(ID);
		assertNotNull(dto);
		assertEquals(dto, tipoLineaDTO);
	}

}
