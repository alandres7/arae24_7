package co.gov.metropol.area247.covid19.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.covid19.dao.ITownDao;
import co.gov.metropol.area247.covid19.entity.Town;
import co.gov.metropol.area247.covid19.svc.ITownSvc;

@Service
public class TownSvcImpl implements ITownSvc {
	
	@Autowired
	private ITownDao townDao;
	
	@Override
	public List<Town> getTowns() {
		return townDao.findByValleAburra(Boolean.TRUE);
	}

}
