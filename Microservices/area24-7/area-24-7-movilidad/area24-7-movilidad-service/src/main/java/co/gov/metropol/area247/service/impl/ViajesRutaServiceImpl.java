package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.InfoViajesRutaDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.mapper.ViajesRutaMapper;
import co.gov.metropol.area247.repository.ViajesRutaRepository;
import co.gov.metropol.area247.repository.domain.ViajesRuta;
import co.gov.metropol.area247.service.IViajesRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class ViajesRutaServiceImpl implements IViajesRutaService {

	@Autowired
	private ViajesRutaRepository repository;

	@Autowired
	private ViajesRutaMapper mapper;

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IViajesRutaService#findByIdRuta(java.lang.Long)
	 */
	@Override
	public List<InfoViajesRutaDTO> findByIdRuta(Long idRuta) {
		try {
			return mapper.mapToInfoViajesRutaDTO(repository.findByIdRuta(idRuta));
		} catch (Exception e) {
			throw new Area247Exception("Error al obtener los viajes de una ruta", e.getCause());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IViajesRutaService#procesarInfoViajesRuta(java.util.List, co.gov.metropol.area247.model.RutaMetroDTO)
	 */
	@Override
	public void procesarInfoViajesRuta(List<InfoViajesRutaDTO> infosViajesRutaDTO, RutaMetroDTO rutaDTO) {

		if (Utils.isNotEmpty(infosViajesRutaDTO)) {
			boolean hayElementosPorBorrar = true;
			// Obtenemos la informacion existente de viajes de la ruta
			List<InfoViajesRutaDTO> viajesRutaLista = findByIdRuta(rutaDTO.getId());
			// recorremos la informacion actual para guardarla
			for (int i = 0; i < infosViajesRutaDTO.size(); i++) {
				try {
					InfoViajesRutaDTO infoViajesDTO = infosViajesRutaDTO.get(i);
					infoViajesDTO.setRutaDTO(rutaDTO);
					// Tomamos la informacion ya existente y la reemplazamos por la actual
					InfoViajesRutaDTO infoViajeAux = viajesRutaLista.get(i);
					// Utilizando el id con el cual ya esta almacenado
					infoViajesDTO.setId(infoViajeAux.getId());
				} catch (IndexOutOfBoundsException e) {
					hayElementosPorBorrar = false;
				}
			}
			// En caso de que haya mas informacion persistida que la informacion actual
			// se deben borrar estos elementos para que no queden como info basura
			if (hayElementosPorBorrar) {
				List<InfoViajesRutaDTO> itemsABorrar = viajesRutaLista.subList(infosViajesRutaDTO.size()-1,
						viajesRutaLista.size() - 1);
				deleteAll(itemsABorrar);
			}
			// Y por ultimo persistimos los actuales
			saveAll(infosViajesRutaDTO);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IViajesRutaService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		try {
			repository.delete(id);
		} catch (Exception e) {
			throw new Area247Exception("Error al eliminar la informacion de los viajes de la ruta", e.getCause());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IViajesRutaService#delete(java.util.List)
	 */
	@Override
	public void deleteAll(List<InfoViajesRutaDTO> infosViajesRuta) {
		if (Utils.isNotEmpty(infosViajesRuta)) {
			for (InfoViajesRutaDTO infoViajesRutaDTO : infosViajesRuta) {
				delete(infoViajesRutaDTO.getId());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IViajesRutaService#save(co.gov.metropol.area247.model.InfoViajesRutaDTO)
	 */
	@Override
	public void save(InfoViajesRutaDTO infoViajesRutaDTO) {
		try {
			ViajesRuta viajesRuta = mapper.toViajesRuta(infoViajesRutaDTO);
			repository.save(viajesRuta);
			// TODO convertir en entidad a ViajesRuta y descomentar
			//infoViajesRutaDTO.setId(viajesRuta.getId());
		} catch (Exception e) {
			throw new Area247Exception(String.format("Error al tratar de guardar la informacion de los viajes de la ruta %s.", infoViajesRutaDTO), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IViajesRutaService#update(co.gov.metropol.area247.model.InfoViajesRutaDTO)
	 */
	@Override
	public void update(InfoViajesRutaDTO infoViajesRutaDTO) {
		try {
			ViajesRuta viajesRuta = mapper.toViajesRuta(infoViajesRutaDTO);
			repository.save(viajesRuta);
		} catch (Exception e) {
			throw new Area247Exception(String.format("Error al tratar de actualizar la informacion de los viajes de la ruta %s.", infoViajesRutaDTO),
					e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IViajesRutaService#saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<InfoViajesRutaDTO> infosViajesRuta) {
		if (Utils.isNotEmpty(infosViajesRuta)) {
			for (InfoViajesRutaDTO infoViajesRutaDTO : infosViajesRuta) {
				if (null == infoViajesRutaDTO.getId()) {
					save(infoViajesRutaDTO);
				} else {
					update(infoViajesRutaDTO);
				}
			}
		}
	}
}