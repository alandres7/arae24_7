package co.gov.metropol.area247.huellas.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.huellas.dao.IHuellasPreguntaConsumoDao;
import co.gov.metropol.area247.huellas.entity.PreguntaConsumo;
import co.gov.metropol.area247.huellas.mapper.IHuellasMapper;
import co.gov.metropol.area247.huellas.model.PreguntaConsumoDto;
import co.gov.metropol.area247.huellas.svc.IHuellasPreguntaConsumoSvc;

@Service("preguntaConsumoSvc")
public class HuellasPreguntaConsumoSvcImpl implements IHuellasPreguntaConsumoSvc {
	
	private PreguntaConsumo pregunta;
	
	@Autowired
	@Qualifier("preguntaConsumoMapper")
	IHuellasMapper<PreguntaConsumo, PreguntaConsumoDto> preguntaMapper;
	
	@Autowired
	@Qualifier("preguntaConsumoDao")
	IHuellasPreguntaConsumoDao preguntaDao;

	@Override
	public List<PreguntaConsumoDto> getPreguntasConsumo(Long idTipoHuella) throws Exception{
//		List<PreguntaConsumoDto> preguntas = null;
		return null;
	}

	@Override
	public void crearPreguntaConsumo(PreguntaConsumoDto pregunta) throws Exception{
		// TODO Auto-generated method stub
		
	}

}
