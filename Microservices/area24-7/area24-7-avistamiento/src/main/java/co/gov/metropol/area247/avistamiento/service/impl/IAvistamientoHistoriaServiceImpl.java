package co.gov.metropol.area247.avistamiento.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.avistamiento.dao.IAvistamientoHistoriaJDBCRepository;
import co.gov.metropol.area247.avistamiento.dao.IAvistamientoHistoriaRepository;
import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.Historia;
import co.gov.metropol.area247.avistamiento.model.dto.HistoriaDto;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoAvistamientoService;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoHistoriaService;

@Service
public class IAvistamientoHistoriaServiceImpl implements IAvistamientoHistoriaService {

	@Autowired
	IAvistamientoHistoriaRepository historiaDao;

	@Autowired
	IAvistamientoHistoriaJDBCRepository historiaJDBCDao;

	@Autowired
	IAvistamientoAvistamientoService avistamientoService;

	@Override
	public boolean historiaCrear(Historia historia, Long idAvistamiento) {
		try {
			if (idAvistamiento != null) {
				Avistamiento avistamiento = avistamientoService.avistamientoPorId(idAvistamiento);
				if (avistamiento != null) {
					historia.setAvistamiento(avistamiento);
				} else {
					return false;
				}
			}
			historiaDao.save(historia);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean historiaActualizar(Historia historia) {
		return historiaCrear(historia, null);
	}

	@Override
	public Historia historiaPorId(Long idHistoria) {
		return historiaDao.findOne(idHistoria);
	}

	@Override
	public HistoriaDto historiaDtoPorId(Long idHistoria) {
		HistoriaDto historiaDto = historiaJDBCDao.obtenerHistoriaPorId(idHistoria);
		return historiaDto;
	}

	@Override
	public boolean historiaEliminar(Long idHistoria) {
		try {
			Historia historia = historiaDao.findOne(idHistoria);
			if (historia != null) {
				historiaDao.delete(historia);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<HistoriaDto> obtenerHistoriaPorParametros(Long idAvistamiento, Long idUsuario, Integer estado) {
		try {
			List<HistoriaDto> historiasDto = historiaJDBCDao.obtenerHistoriaPorParametros(idAvistamiento, idUsuario,
					estado);
			return historiasDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<HistoriaDto> historiaObtenerPorIdAvistamiento(Long idAvistamiento, String nickname) {
		try {
			List<HistoriaDto> historiasDto = historiaJDBCDao.historiaObtenerPorIdAvistamiento(idAvistamiento, nickname);
			return historiasDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<HistoriaDto> getHistoriasPorIdAvistamiento(Long idAvistamiento) {
		try {
			List<HistoriaDto> historiasDto = historiaJDBCDao.getHistoriasPorIdAvistamiento(idAvistamiento);
			return historiasDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
