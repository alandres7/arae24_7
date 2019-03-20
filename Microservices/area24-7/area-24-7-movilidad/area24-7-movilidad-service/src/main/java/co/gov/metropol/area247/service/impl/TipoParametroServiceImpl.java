package co.gov.metropol.area247.service.impl;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.TipoParametroDTO;
import co.gov.metropol.area247.model.mapper.TipoParametroMapper;
import co.gov.metropol.area247.repository.TipoParametroRepository;
import co.gov.metropol.area247.repository.domain.TipoParametro;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoParametroServiceImpl implements ITipoParametroService {

	@Autowired
	private TipoParametroRepository repository;

	@Autowired
	private TipoParametroMapper mapper;

	@Override
	public TipoParametroDTO findById(Long idParametro) {
		try {
			TipoParametro tipoParametro = repository.findOne(idParametro);
			return mapper.toTipoParametroDTO(tipoParametro);
		} catch (Exception e) {
			String msg = MessageFormat.format("Error al consultar el tipo de parametro por el id {0} ", idParametro);
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);
		}
	}

	@Override
	public Long obtenerValorEntero(Long idParametro) {
		try {
			TipoParametroDTO dto = findById(idParametro);
			return Long.valueOf(dto.getValor());
		} catch (Exception e) {
			String msg = MessageFormat.format("Error al convertir el valor del parametro {0} a valor entero ",
					idParametro);
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);
		}
	}

	@Override
	public String obtenerValorCadena(Long idParametro) {
		try {
			TipoParametroDTO dto = findById(idParametro);
			return dto.getValor();
		} catch (Exception e) {
			String msg = MessageFormat.format("El parametro con el id {0} no esta configurado en base de datos",
					idParametro);
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);
		}
	}
	
	

}
