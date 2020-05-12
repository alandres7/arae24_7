package co.gov.metropol.area247.covid19.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.covid19.dao.IMarkerDao;
import co.gov.metropol.area247.covid19.entity.Marker;
import co.gov.metropol.area247.covid19.svc.IMarkerSvc;

@Service
public class MarkerSvcImpl implements IMarkerSvc {
	
	@Autowired
	private IMarkerDao markerDao;
	
	@Override
	public Marker guardarMarker( Marker marker ) {
		return markerDao.save(marker);
	}
	

}
