package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraTipoCapaRepository;
import co.gov.metropol.area247.contenedora.model.TipoCapa;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoCapaService;

@Service
public class IContenedoraTipoCapaServiceImpl implements IContenedoraTipoCapaService {

	@Autowired
	private IContenedoraTipoCapaRepository tipoCapaDao;
	
	@Override
	public TipoCapa tipoCapaObtenerPorNombre(String nombreTipoCapa) {
		return tipoCapaDao.findByNombre(nombreTipoCapa);
	}

	@Override
	public TipoCapa tipoCapaObtenerPorId(Long idTipoCapa) {
		return tipoCapaDao.findOne(idTipoCapa);
	}

	@Override
	public List<TipoCapa> tipoCapaObtenerTodas() {
		return (List<TipoCapa>) tipoCapaDao.findAll();
	}

}
