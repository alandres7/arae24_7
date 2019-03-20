package co.gov.metropol.area247.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.ConDireccionFavoritaDTO;
import co.gov.metropol.area247.repository.domain.ConDireccionFavorita;
import co.gov.metropol.area247.service.IConDireccionFavoritaService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractConDireccionFavoritaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class ConDireccionFavoritaServiceImpl extends AbstractConDireccionFavoritaService
		implements IConDireccionFavoritaService {

	@Override
	public ConDireccionFavoritaDTO findByNombreAndIdUsuario(String nombre, Long idUsuario) {
		try {
			return mapper.toConDireccionFavoritaDTO(repository.findByNombreAndUsuario(nombre, idUsuario));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar la ubicaion por el nombre", nombre), e);
			throw new Area247Exception(String.format("Error al consultar la ubicaion por el nombre", nombre), e);
		}
	}

	@Override
	public ConDireccionFavoritaDTO saveUbicacionFavorita(String nombre, String descripcion, double latitud,
			double longitud, Long idUsuario, Long idCategoria) {
		ConDireccionFavorita direccion = new ConDireccionFavorita();
		direccion.setDescripcion(descripcion);
		direccion.setEnabled(true);
		direccion.setIdCoordenada(1L);
		direccion.setCoordenada(GeometryUtil.obtenerPunto(latitud, longitud));
		direccion.setIdUsuario(idUsuario);
		direccion.setNombre(nombre);
		direccion.setIdCategoria(idCategoria);
		return mapper.toConDireccionFavoritaDTO(repository.save(direccion));
	}

	@Override
	public boolean isCondireccionFavoritaExist(Long id) {
		return repository.exists(id);
	}

	@Override
	public ConDireccionFavoritaDTO findById(Long id) {
		return mapper.toConDireccionFavoritaDTO(repository.findOne(id));
	}

	@Override
	public void updateDireccionFavorita(ConDireccionFavoritaDTO direccion) {
		ConDireccionFavorita dir = repository.findOne(direccion.getId());
		dir.setDescripcion(direccion.getDescripcion());
		dir.setEnabled(true);
		dir.setIdCoordenada(direccion.getIdCoordenada());
		dir.setIdUsuario(direccion.getIdUsuario());
		dir.setNombre(direccion.getNombre());
		dir.setIdCategoria(direccion.getCategoriaDTO().getId());

		repository.save(dir);
	}

	@Override
	public void deleteDireccionFavoritaById(Long id) {
		ConDireccionFavorita dir = repository.findOne(id);
		repository.delete(dir);
	}

	@Override
	public List<ConDireccionFavoritaDTO> findByIdUsuario(Long idUsuario) {
		List<ConDireccionFavoritaDTO> direcciones = new ArrayList<>();
		repository.findByIdUsuario(idUsuario).iterator().forEachRemaining(direccion -> {
					ConDireccionFavoritaDTO dto = mapper.toConDireccionFavoritaDTO(direccion); 
					if (!Utils.isNull(dto.getCategoriaDTO())) {
						dto.setCategoriaDTO(conCatUbicacionFavoritaService.findById(direccion.getIdCategoria()));
					}
					direcciones.add(dto);
				});
		return direcciones;
	}

	@Override
	public Long countByIdUsuario(Long idUsuario) {
		try {
			if (!Utils.isNull(idUsuario))
				return repository.countByIdUsuario(idUsuario);
			return 0L;
		} catch (Exception e) {
			throw new Area247Exception(
					"Error al consultar la cantidad de direcciones favoritas almacenadas por el usuario ", e);
		}
		
	}

}
