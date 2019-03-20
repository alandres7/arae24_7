package co.gov.metropol.area247.avistamiento.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.avistamiento.dao.IAvistamientoEspecieRepository;
import co.gov.metropol.area247.avistamiento.model.Especie;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoEspecieService;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;

@Service
public class IAvistamientoEspecieServiceImpl implements IAvistamientoEspecieService {

	@Autowired
	IAvistamientoEspecieRepository especieDao;
	
	@Autowired
	IContenedoraCategoriaService categoriaService;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	

	@Override
	public boolean especieCrear(Especie especie, Long idCategoria) {
		try {
			if (idCategoria != null) {
				Categoria categoria = categoriaService.categoriaObtenerPorId(idCategoria);
				if (categoria != null) {
					especie.setIdCategoria(idCategoria);
				} else {
					return false;
				}
			}
			especieDao.save(especie);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean especieActualizar(Especie especie) {
		return especieCrear(especie, null);
	}

	@Override
	public List<Especie> especiePorIdCategoria(Long idCategoria) {
		try {
			List<Especie> especies = especieDao.findByIdCategoria(idCategoria);			
			if (especies != null) {
				for (Especie especie : especies) {
					if (especie.getIcono() == null) {
						especie.setIcono(new Icono());
					}else {
						especie.getIcono().setRutaLogo(urlIconos + especie.getIcono().getId());
					}
				}				
			}			
			return especies;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Especie especiePorId(Long id) {
		try {
			Especie especie = especieDao.findOne(id);
			if (especie.getIcono() == null) {
				especie.setIcono(new Icono());
			}else {
				especie.getIcono().setRutaLogo(urlIconos + especie.getIcono().getId());
			}
			return especie;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean especieEliminar(Long idEspecie) {
		try {
			Especie especie = especieDao.findOne(idEspecie);
			if (especie != null) {
				iconoService.iconoEliminar(especie.getIcono().getId());
				especieDao.delete(especie);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}



}
