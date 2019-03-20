package co.gov.metropol.area247.huellas.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.gov.metropol.area247.huellas.dao.IHuellasEncuestadoDao;
import co.gov.metropol.area247.huellas.entity.Encuestado;
import co.gov.metropol.area247.huellas.mapper.IHuellasMapper;
import co.gov.metropol.area247.huellas.model.EncuestadoDto;
import co.gov.metropol.area247.huellas.svc.IHuellasEncuestadoSvc;

@Service("encuestadoSvc")
public class HuellasEncuestadoSvcImpl implements IHuellasEncuestadoSvc {
	
	@Autowired
	@Qualifier("encuestadoDao")
	IHuellasEncuestadoDao encuestadoDao;
	
	@Autowired
	@Qualifier("encuestadoMapper")
	IHuellasMapper<Encuestado, EncuestadoDto> encuestadoMapper;
	
	private Encuestado encuestado;
	
	@Override
	@Transactional
	public EncuestadoDto registro(EncuestadoDto dto) {
		encuestado = encuestadoMapper.modelToEntity(dto);
		return encuestadoMapper.entityToModel
				(encuestadoDao.save(encuestado));
	}
	
	@Override
	public EncuestadoDto getEncuestado(Long dto) {
		return encuestadoMapper.
				entityToModel(encuestadoDao.findOne(dto));
	}
	
}
