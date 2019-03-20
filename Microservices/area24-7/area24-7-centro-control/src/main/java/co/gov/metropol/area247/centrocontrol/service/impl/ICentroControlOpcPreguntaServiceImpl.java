package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlOpcPreguntaRepository;
import co.gov.metropol.area247.centrocontrol.dao.ICentroControlPreguntaRepository;
import co.gov.metropol.area247.centrocontrol.model.OpcPregunta;
import co.gov.metropol.area247.centrocontrol.model.Pregunta;
import co.gov.metropol.area247.centrocontrol.model.dto.OpcPreguntaDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlOpcPreguntaService;

@Service
public class ICentroControlOpcPreguntaServiceImpl implements ICentroControlOpcPreguntaService {

	@Autowired
	private ICentroControlOpcPreguntaRepository opcPreguntaDao;

	@Autowired
	private ICentroControlPreguntaRepository preguntaDao;

	private OpcPregunta opcPreguntaAux;

	@Override
	public Boolean guardarOpcPregunta(OpcPreguntaDto opcsPregunta) {
		opcPreguntaAux = new OpcPregunta();
		this.getOpcPregunta(opcsPregunta);
		try {
			opcPreguntaDao.save(opcPreguntaAux);
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public Boolean editarOpcPregunta(OpcPreguntaDto opcsPregunta) {
		try {
			opcPreguntaAux = opcPreguntaDao.findById(opcsPregunta.getId());
			if (opcsPregunta.getPreguntaId() != null) {
				if (opcsPregunta.getClave() != null && !opcsPregunta.getClave().equals(opcPreguntaAux.getClave())
						&& (getOpcPregunta(opcsPregunta.getClave(), opcsPregunta.getPreguntaId()))) {
					return Boolean.FALSE;
				}
				opcPreguntaAux.setIdPregunta(preguntaDao.findById(opcsPregunta.getPreguntaId()));
			}
			if (opcsPregunta.getClave() != null) {
				opcPreguntaAux.setClave(opcsPregunta.getClave());
			}
			if (opcsPregunta.getValor() != null) {
				opcPreguntaAux.setValor(opcsPregunta.getValor());
			}
			opcPreguntaDao.save(opcPreguntaAux);
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public List<OpcPreguntaDto> listarOpcsPregunta(Long preguntaId) {
		return opcPreguntaDao.getOpcPregunta(preguntaId);
	}

	private void getOpcPregunta(OpcPreguntaDto dto) {
		Pregunta pregunta = preguntaDao.findById(dto.getPreguntaId());
		opcPreguntaAux.setIdPregunta(pregunta);
		opcPreguntaAux.setClave(dto.getClave());
		opcPreguntaAux.setValor(dto.getValor());
	}

	@Override
	public Boolean getOpcPregunta(String clave, Long idPregunta) {
		List<OpcPreguntaDto> Listdto = opcPreguntaDao.getOpcPregunta(clave, idPregunta);
		if (Listdto.isEmpty()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

	@Override
	public OpcPregunta getById(Long idOpcPregunta) {
		return opcPreguntaDao.findById(idOpcPregunta);
	}

	public OpcPreguntaDto getDtoById(Long idOpcPregunta) {
		try {
			opcPreguntaAux = opcPreguntaDao.findById(idOpcPregunta);
			return new OpcPreguntaDto(opcPreguntaAux.getId(), opcPreguntaAux.getIdPregunta().getId(),
					opcPreguntaAux.getClave(), opcPreguntaAux.getValor());
		} catch (Exception e) {
			return null;
		}

	}

}
