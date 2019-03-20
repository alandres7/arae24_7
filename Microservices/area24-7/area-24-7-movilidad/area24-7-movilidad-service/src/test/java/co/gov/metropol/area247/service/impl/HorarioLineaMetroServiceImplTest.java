package co.gov.metropol.area247.service.impl;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.mapper.HorarioLineaMetroMapper;
import co.gov.metropol.area247.repository.HorarioLineaMetroRepository;
import co.gov.metropol.area247.repository.domain.HorarioLinea;
import co.gov.metropol.area247.service.IHorarioLineaMetroService;

@RunWith(MockitoJUnitRunner.class)
public class HorarioLineaMetroServiceImplTest {

	@InjectMocks
	private IHorarioLineaMetroService service = new HorarioLineaMetroServiceImpl();

	@Mock
	private HorarioLineaMetroRepository horarioLineaMetroRepository;

	@Mock
	private HorarioLineaMetroMapper horarioLineaMetroMapper;

	private static final String HORA_HORARIO = "04:04:04";
	private static final String CODIGO_LINEA = "LA";
	private static final Long ID_LINEA = 1l;

	private List<HorarioLineaMetroDTO> horariosLineaMetroDTO;
	private List<HorarioLineaMetroDTO> horariosDTOAux;
	private List<HorarioLinea> horarioLineas;
	private LineaMetroDTO lineaDTO;

	@Before
	public void prepare() {

		horariosDTOAux = new ArrayList<>();
		horariosLineaMetroDTO = new ArrayList<>();
		horarioLineas = new ArrayList<>();

		lineaDTO = new LineaMetroDTO();
		lineaDTO.setId(ID_LINEA);
		lineaDTO.setCodigo(CODIGO_LINEA);
	}

	@Test
	public void testProcesarHorariosUpdate() {
		horarioLineas.clear();
		horarioLineas.addAll(cargarHorarios(1));

		horariosLineaMetroDTO.clear();
		horariosLineaMetroDTO.addAll(cargarHorariosDTO(3, false));
		when(horarioLineaMetroRepository.findByIdLinea(ID_LINEA)).thenReturn(horarioLineas);
		horariosDTOAux.addAll(cargarHorariosDTO(1, true));
		when(horarioLineaMetroMapper.mapToHorarioLineaMetroDTO(horarioLineas)).thenReturn(horariosDTOAux);

		for (int i = 0; i < horariosLineaMetroDTO.size(); i++) {
			if (i < horariosDTOAux.size()) {
				HorarioLinea horario = (HorarioLinea) new HorarioLinea().setId(horariosDTOAux.get(0).getId());
				when(horarioLineaMetroRepository.findOne(horariosDTOAux.get(0).getId())).thenReturn(horario);
				HorarioLineaMetroDTO horarioDto = new HorarioLineaMetroDTO().withId(horario.getId());
				when(horarioLineaMetroMapper.toHorarioLineaMetroDTO(horario)).thenReturn(horarioDto);
			} else {
				HorarioLinea horario = new HorarioLinea();
				when(horarioLineaMetroMapper.toHorarioLinea(horariosLineaMetroDTO.get(i))).thenReturn(horario);
				horario.setId(anyLong());
				when(horarioLineaMetroRepository.save(horario)).thenReturn(horario);
			}
		}
		service.procesarHorarios(horariosLineaMetroDTO);
	}

	@Test
	public void testProcesarHorariosDelete() {
		horarioLineas.clear();
		horarioLineas.addAll(cargarHorarios(3));

		horariosLineaMetroDTO.clear();
		horariosLineaMetroDTO.addAll(cargarHorariosDTO(1, false));
		when(horarioLineaMetroRepository.findByIdLinea(ID_LINEA)).thenReturn(horarioLineas);
		horariosDTOAux.addAll(cargarHorariosDTO(3, true));
		when(horarioLineaMetroMapper.mapToHorarioLineaMetroDTO(horarioLineas)).thenReturn(horariosDTOAux);

		for (int i = 0; i < horarioLineas.size(); i++) {
			if (i < horariosLineaMetroDTO.size()) {
				HorarioLineaMetroDTO horarioDto = horariosLineaMetroDTO.get(i);
				horarioDto.setId(horarioLineas.get(i).getId());
				HorarioLinea horario = (HorarioLinea) new HorarioLinea().setId(horarioDto.getId());
				when(horarioLineaMetroRepository.findOne(horarioDto.getId())).thenReturn(horario);
				when(horarioLineaMetroMapper.toHorarioLineaMetroDTO(horario)).thenReturn(horarioDto);
				when(horarioLineaMetroMapper.toHorarioLinea(horarioDto)).thenReturn(horario);
				when(horarioLineaMetroRepository.save(horario)).thenReturn(anyObject());
			}
		}
		service.procesarHorarios(horariosLineaMetroDTO);
	}

	private List<HorarioLineaMetroDTO> cargarHorariosDTO(int tam, boolean conId) {
		List<HorarioLineaMetroDTO> horariosDTOAux = new ArrayList<>();
		Long id = null;
		if (conId) {
			id = anyLong();
		}
		for (int i = 0; i < tam; i++) {
			HorarioLineaMetroDTO horarioDTO = new HorarioLineaMetroDTO();
			horarioDTO.setId(id);
			horarioDTO.setHoraInicioPicoPM(HORA_HORARIO);
			horarioDTO.setHoraFinPicoPM(HORA_HORARIO);
			horarioDTO.setHoraInicioOperacion(HORA_HORARIO);
			horarioDTO.setHoraFinOperacion(HORA_HORARIO);
			horarioDTO.setHoraInicioPicoAM(HORA_HORARIO);
			horarioDTO.setHoraFinPicoAM(HORA_HORARIO);
			horarioDTO.setLineaDTO(lineaDTO);
			horariosDTOAux.add(horarioDTO);
		}
		return horariosDTOAux;
	}

	private List<HorarioLinea> cargarHorarios(int tam) {
		List<HorarioLinea> horariosAux = new ArrayList<>();
		for (int i = 0; i < tam; i++) {
			HorarioLinea horario = new HorarioLinea();
			horario.setId(Long.valueOf(i));
			horario.setHoraInicioPicoPM(HORA_HORARIO);
			horario.setHoraFinPicoPM(HORA_HORARIO);
			horario.setHoraInicioOperacion(HORA_HORARIO);
			horario.setHoraFinOperacion(HORA_HORARIO);
			horario.setHoraInicioPicoAM(HORA_HORARIO);
			horario.setHoraFinPicoAM(HORA_HORARIO);
			horario.setIdLinea(ID_LINEA);
			horariosAux.add(horario);
		}
		return horariosAux;
	}

}
