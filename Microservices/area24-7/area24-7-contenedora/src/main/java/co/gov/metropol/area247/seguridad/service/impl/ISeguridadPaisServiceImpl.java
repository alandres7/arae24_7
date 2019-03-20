package co.gov.metropol.area247.seguridad.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.dao.ISeguridadPaisRepository;
import co.gov.metropol.area247.seguridad.mapper.ISeguridadMapper;
import co.gov.metropol.area247.seguridad.model.Pais;
import co.gov.metropol.area247.seguridad.model.dto.PaisDto;
import co.gov.metropol.area247.seguridad.service.ISeguridadPaisService;

@Service
public class ISeguridadPaisServiceImpl implements ISeguridadPaisService {
	
	private Pais pais;
	
	@Autowired
	@Qualifier("paisMapper")
	ISeguridadMapper<Pais, PaisDto> paisMapper;
	
	@Autowired
	ISeguridadPaisRepository paisDao;
	
	@Override
	public List<PaisDto> getPaises() {
		List<PaisDto> listaPais = new ArrayList<>();
		List<Pais> paises = (List<Pais>) paisDao.findAll();
		for (Pais pais : paises) {
			PaisDto dto = paisMapper.entityToModel(pais);
			listaPais.add(dto);
		}
		return listaPais;
	}

}
