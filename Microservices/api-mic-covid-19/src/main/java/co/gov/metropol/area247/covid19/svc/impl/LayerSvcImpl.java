package co.gov.metropol.area247.covid19.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.covid19.dao.ILayerDao;
import co.gov.metropol.area247.covid19.dao.ILayerDaoJdbc;
import co.gov.metropol.area247.covid19.entity.Layer;
import co.gov.metropol.area247.covid19.svc.ILayerSvc;

@Service
public class LayerSvcImpl implements ILayerSvc {
	
	@Autowired
	private ILayerDao layerDao;
	
	@Autowired
	private ILayerDaoJdbc layerDaoJdbc;
	
	@Override
	public Layer getById(long id) {
		try {
			return layerDao.findOne(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Layer();
		}
		
	}
	
	@Override
	public Layer getByNombre(String nombre) {
		return layerDao.findByNombre(nombre);
	}
	
	@Override
	public void borrarMarkersXId(Long idCapa) {
		layerDaoJdbc.borrarMarcadoresXIdCapa(idCapa);		
	}

}
