package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraTipoMultimediaRepository;
import co.gov.metropol.area247.contenedora.model.TipoMultimedia;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoMultimediaService;

@Service
public class IContenedoraTipoMultimediaServiceImpl implements IContenedoraTipoMultimediaService {

	@Autowired
	IContenedoraTipoMultimediaRepository tipoMultimediaDao;
	
	@Override
	public List<TipoMultimedia> tipoMultimediaObtenerTodos() {
		return (List<TipoMultimedia>) tipoMultimediaDao.findAll();
	}

	@Override
	public TipoMultimedia tipoMultimediaObtenerPorId(Long idTipoMultimedia) {
		return tipoMultimediaDao.findOne(idTipoMultimedia);
	}

	@Override
	public List<TipoMultimedia> tipoMultimediaObtenerPorTipo(String tipoMultimedia) {
		return tipoMultimediaDao.findByTipo(tipoMultimedia);
	}

	@Override
	public TipoMultimedia tipoMultimediaObtenerPorSubtipoMultimedia(String subtipoMultimedia) {
		return tipoMultimediaDao.findBySubtipo(subtipoMultimedia);
	}

	@Override
	public boolean tipoMultimediaCrear(TipoMultimedia tipoMultimedia) {
		try {
			tipoMultimediaDao.save(tipoMultimedia);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
