package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.mapper.ViajesLineaMapper;
import co.gov.metropol.area247.repository.ViajesLineaRepository;
import co.gov.metropol.area247.repository.domain.ViajesLinea;
import co.gov.metropol.area247.service.IViajesLineaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class ViajesLineaServiceImpl implements IViajesLineaService {

	@Autowired
	private ViajesLineaRepository repository;

	@Autowired
	private ViajesLineaMapper mapper;
	
	@Override
	public List<InfoViajesLineaDTO> findByIdLinea(Long idLinea) {
		try {
			List<ViajesLinea> viajesLinea = repository.findByIdLinea(idLinea);
			return mapper.mapToInfoViajesLineaDTO(viajesLinea);
		} catch (Exception e) {
			throw new Area247Exception("Error al obtener los viajes de una linea", e.getCause());
		}
	}

	@Override
	public void procesarInfoViajesLinea(List<InfoViajesLineaDTO> infosViajesLineaDTO, LineaMetroDTO LineaDTO) {
		if (Utils.isNotEmpty(infosViajesLineaDTO)) {
			boolean hayElementosPorBorrar = true;
			// Obtenemos la informacion existente de viajes de la Linea
			List<InfoViajesLineaDTO> viajesLineaLista = findByIdLinea(LineaDTO.getId());
			// recorremos la informacion actual para guardarla
			for (int i = 0; i < infosViajesLineaDTO.size(); i++) {
				try {
					InfoViajesLineaDTO infoViajesDTO = infosViajesLineaDTO.get(i);
					infoViajesDTO.setLineaDTO(LineaDTO);
					// Tomamos la informacion ya existente y la reemplazamos por la actual
					InfoViajesLineaDTO infoViajeAux = viajesLineaLista.get(i);
					// Utilizando el id con el cual ya esta almacenado
					infoViajesDTO.setId(infoViajeAux.getId());
				} catch (IndexOutOfBoundsException e) {
					hayElementosPorBorrar = false;
				}
			}
			// En caso de que haya mas informacion persistida que la informacion actual
			// se deben borrar estos elementos para que no queden como info basura
			if (hayElementosPorBorrar) {
				List<InfoViajesLineaDTO> itemsABorrar = viajesLineaLista.subList(infosViajesLineaDTO.size()-1,
						viajesLineaLista.size() - 1);
				deleteAll(itemsABorrar);
			}
			// Y por ultimo persistimos los actuales
			saveAll(infosViajesLineaDTO);
		}
	}

	@Override
	public void delete(Long id) {
		try {
			repository.delete(id);
		} catch (Exception e) {
			throw new Area247Exception("Error al eliminar la informacion de los viajes de la linea", e.getCause());
		}
	}

	@Override
	public void deleteAll(List<InfoViajesLineaDTO> infosViajesLineas) {
		if (Utils.isNotEmpty(infosViajesLineas)) {
			for (InfoViajesLineaDTO infoViajesLineaDTO : infosViajesLineas) {
				delete(infoViajesLineaDTO.getId());
			}
		}
	}

	@Override
	public void save(InfoViajesLineaDTO infoViajesLineaDTO) {
		try {
			ViajesLinea viajesLinea = mapper.toViajesLinea(infoViajesLineaDTO);
			repository.save(viajesLinea);
			// TODO convertir en entidad a ViajesLinea y descomentar
			//infoViajesLineaDTO.setId(viajesLinea.getId());
		} catch (Exception e) {
			throw new Area247Exception(String.format("Error al tratar de guardar la informacion de los viajes de la Linea %s.", infoViajesLineaDTO), e);
		}
	}

	@Override
	public void update(InfoViajesLineaDTO infoViajesLineaDTO) {
		try {
			ViajesLinea viajesLinea = mapper.toViajesLinea(infoViajesLineaDTO);
			repository.save(viajesLinea);
		} catch (Exception e) {
			throw new Area247Exception(String.format("Error al tratar de actualizar la informacion de los viajes de la Linea %s.", infoViajesLineaDTO),
					e);
		}
	}

	@Override
	public void saveAll(List<InfoViajesLineaDTO> infosViajesLinea) {
		if (Utils.isNotEmpty(infosViajesLinea)) {
			for (InfoViajesLineaDTO infoViajesLineaDTO : infosViajesLinea) {
				if (null == infoViajesLineaDTO.getId()) {
					save(infoViajesLineaDTO);
				} else {
					update(infoViajesLineaDTO);
				}
			}
		}
	}

}
