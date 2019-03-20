package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.OpcPregunta;
import co.gov.metropol.area247.centrocontrol.model.dto.OpcPreguntaDto;

public interface ICentroControlOpcPreguntaService {
	Boolean guardarOpcPregunta(OpcPreguntaDto opcsPregunta);
	Boolean editarOpcPregunta(OpcPreguntaDto opcsPregunta);
	List<OpcPreguntaDto> listarOpcsPregunta(Long preguntaId);
	Boolean getOpcPregunta(String clave, Long idPregunta);
	OpcPregunta getById(Long idOpcPregunta);
	OpcPreguntaDto getDtoById(Long idOpcPregunta);
}
