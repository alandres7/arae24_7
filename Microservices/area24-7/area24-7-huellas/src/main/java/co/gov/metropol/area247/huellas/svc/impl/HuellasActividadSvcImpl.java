package co.gov.metropol.area247.huellas.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.huellas.dao.IHuellasActividadDao;
import co.gov.metropol.area247.huellas.entity.Actividad;
import co.gov.metropol.area247.huellas.mapper.IHuellasMapper;
import co.gov.metropol.area247.huellas.model.ActividadDto;
import co.gov.metropol.area247.huellas.svc.IHuellasActividadSvc;

@Service
public class HuellasActividadSvcImpl implements IHuellasActividadSvc {
	
	@Autowired
	private IHuellasActividadDao actividadDao;
	
	@Autowired
	@Qualifier("actividadMapper")
	private IHuellasMapper<Actividad, ActividadDto> actividadMapper;
	
	@Override
	public List<ActividadDto> getActividadesXCapa(long idCapa) {
		return actividadDao.getActividadesXCapa(idCapa);
	}
	
	@Override
	public ActividadDto getActividadXId(long id) {
		return actividadMapper.entityToModel(actividadDao.findById(id));
	}
	
	@Override
	public boolean existActividad(long id) {
		return actividadDao.exists(id);
	}
	
	@Override
	public Boolean saveActividad(ActividadDto actividad) throws Exception {
		try {
			Actividad entity = actividadMapper.modelToEntity(actividad);
			entity = actividadDao.save(entity);
			return entity.getId() != 0L;
		} catch (Exception e) {
			throw new Exception("Operación: save/updateActividad(ActividadDto actividad) throws Exception", e);
		}
		
	}
	
	@Override
	public boolean deleteActividad(long id) throws Exception {
		try {
			actividadDao.delete(id);
			return true;
		} catch (Exception e) {
			throw new Exception("Operación: deleteActividad(long id) throws Exception", e);
		}
	}

}
