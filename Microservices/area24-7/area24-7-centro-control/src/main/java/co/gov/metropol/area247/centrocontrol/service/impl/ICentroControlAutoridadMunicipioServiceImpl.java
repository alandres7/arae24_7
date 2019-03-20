package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlArbolDecisionRepository;
import co.gov.metropol.area247.centrocontrol.dao.ICentroControlAutoridadMunicipioRepository;
import co.gov.metropol.area247.centrocontrol.dao.ICentroControlNodoArbolRepository;
import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;
import co.gov.metropol.area247.centrocontrol.model.AutoridadMunicipio;
import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.dto.AutoridadMunicipioDto;
import co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlArbolDecisionService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAutoridadMunicipioService;

@Service
public class ICentroControlAutoridadMunicipioServiceImpl implements ICentroControlAutoridadMunicipioService {

	@Autowired
	ICentroControlAutoridadMunicipioRepository autoridadMunicipioRepository;
	
	@Autowired
	ICentroControlNodoArbolRepository nodoArbolRepository;

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlAutoridadMunicipioServiceImpl.class);
	
	@Override
	public List<AutoridadMunicipioDto> autoridadMunicipioPorIdNodo(Long idNodo){
		List<AutoridadMunicipioDto> autoridadesMunicipioDto = new ArrayList<AutoridadMunicipioDto>();
		autoridadesMunicipioDto = autoridadMunicipioRepository.autoridadMunicipioPorIdNodo(idNodo);
		if(autoridadesMunicipioDto.isEmpty()){
			LOGGER.info("No se encuentran registros asociados al nodo con id " + idNodo);
		}
		return autoridadesMunicipioDto;		
	}
	
	@Override
	public AutoridadMunicipioDto autoridadMunicipioPorIdNodoAndMunicipio(Long idNodo, String municipio){
		AutoridadMunicipioDto autoridadMunicipioDto = 
				autoridadMunicipioRepository.autoridadMunicipioPorIdNodoAndMunicipio(idNodo,municipio);
		if(autoridadMunicipioDto == null){
			LOGGER.info("No se encuentran registros asociados al nodo con id " + idNodo);
		}
		return autoridadMunicipioDto;		
	}

	@Override
	public boolean autoridadMunicipioGuardar(AutoridadMunicipio autoridadMunicipio) {
		try{
			autoridadMunicipioRepository.save(autoridadMunicipio);
			LOGGER.info("registro guardado exitosamente con id " + autoridadMunicipio.getId());
			return true;
		}catch(Exception e){
			LOGGER.error("Error en intento de guardar el registro ; " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean autoridadMunicipioEliminarByIdNodoArbol(Long idNodoArbol) {
		try{
			List<AutoridadMunicipio> autoridadesMunicipio = autoridadMunicipioRepository.findByIdNodoArbol(idNodoArbol);			
			for (AutoridadMunicipio autoriMuni : autoridadesMunicipio) {
				autoridadMunicipioRepository.delete(autoriMuni);
			}
			LOGGER.info(String.format("registros borrados exitosamente"));
			return true;
		}catch(Exception e){
			LOGGER.error("Error en intento de eliminación de los registros" + e.getMessage());
			return false;
		}
	}

}
