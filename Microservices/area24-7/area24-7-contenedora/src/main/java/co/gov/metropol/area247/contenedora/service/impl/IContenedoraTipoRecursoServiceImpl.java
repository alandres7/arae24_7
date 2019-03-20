package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraTipoRecursoRepository;
import co.gov.metropol.area247.contenedora.model.TipoRecurso;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoRecursoService;

@Service
public class IContenedoraTipoRecursoServiceImpl implements IContenedoraTipoRecursoService {

	@Autowired
	IContenedoraTipoRecursoRepository tipoRecursoDao;
	
	@Override
	public List<TipoRecurso> tipoRecursoListarTodos() {
		return (List<TipoRecurso>) tipoRecursoDao.findAll();
	}

	@Override
	public TipoRecurso tipoRecursoObtenerPorId(Long idTipoRecurso) {
		return tipoRecursoDao.findOne(idTipoRecurso);
	}

	@Override
	public TipoRecurso tipoRecursoObtenerPorNombre(String nombreTipoRecurso) {
		return tipoRecursoDao.findByNombre(nombreTipoRecurso);
	}

	@Override
	public boolean tipoRecursoCrear(TipoRecurso tipoRecurso) {
		try{
			tipoRecursoDao.save(tipoRecurso);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean tipoRecursoEliminar(Long idTipoRecurso) {
		try{
			tipoRecursoDao.delete(idTipoRecurso);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean tipoRecursoActualizar(TipoRecurso tipoRecurso) {
		try{
			tipoRecursoDao.save(tipoRecurso);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
