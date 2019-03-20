package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCategoriaRepository;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Especie;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.dto.CategoriaDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraArbolDecisionService;
import co.gov.metropol.area247.contenedora.service.IContenedoraAvistamientoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraEspecieService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;

@Service
public class IContenedoraCategoriaServiceImpl implements IContenedoraCategoriaService {
	
	@Autowired
	private IContenedoraCategoriaRepository categoriaDao;
	
	@Autowired
	private IContenedoraCapaService capaService;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
	IContenedoraMarcadorService marcadorService;
	
	@Autowired
	IContenedoraIconoEstadoService iconoEstadoService;
	
	@Autowired
	IContenedoraAvistamientoService avistamientoService;
	
	@Autowired
	IContenedoraEspecieService especieService; 
	
	@Autowired
	IContenedoraArbolDecisionService arbolDecisionService;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	private final Logger LOGGER = LoggerFactory.getLogger(IContenedoraCategoriaServiceImpl.class);
	
	@Override
	public boolean categoriaCrear(Categoria categoria, Long idCapa) {
		try{
			Capa capa = capaService.capaGetById(idCapa); 
			categoria.setCapa(capa);
			categoriaDao.save(categoria);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public Categoria categoriaObtenerPorId(Long idCategoria) {		
		return categoriaDao.findOne(idCategoria);
		
	}

	@Override
	public Categoria categoriaObtenerPorNombre(String nombreCategoria) {
		return categoriaDao.findByNombre(nombreCategoria);
	}

	@Override
	public boolean categoriaActualizar(Categoria categoria) {
		try {
			categoriaDao.save(categoria);
			LOGGER.info("Categoria guardada exitosamente con id " + categoria.getId());
			return true;
		}catch(Exception e) {
			LOGGER.error("No se ha podido actualizar la categoría ; " + e); 
			return false;
		}
	}

	@Override
	public boolean categoriaEliminar(Long idCategoria) {
		try {
			CategoriaDto categoria = categoriaDao.categoriaDtoObtenerPorId(idCategoria);
			
			Icono icono = categoria.getIcono();
			if(icono!=null) {
			    iconoService.iconoEliminar(icono.getId());
			}				
			Icono iconoMar = categoria.getIconoMarcador();
			if(iconoMar!=null) {
			    iconoService.iconoEliminar(iconoMar.getId());
			}			
			iconoEstadoService.iconoEstadoEliminarByCategoria(idCategoria,0);
			iconoEstadoService.iconoEstadoEliminarByCategoria(idCategoria,1);
			iconoEstadoService.iconoEstadoEliminarByCategoria(idCategoria,2);
			
			List<Especie> especies = especieService.especiePorIdCategoria(idCategoria);			
			for (Especie especie : especies) {
				especieService.especieEliminar(especie.getId());
			}										
			marcadorService.eliminarMarcadoresPorIdCategoria(idCategoria);	
			
			arbolDecisionService.arbolEliminarCategorias(idCategoria);
			
			categoriaDao.delete(idCategoria);
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean categoriaCrear(CategoriaDto categoria, Long idCapa) {
		try{
			Categoria categoriaAuxiliar = new Categoria();
			categoriaAuxiliar.setNombre(categoria.getNombre());
			categoriaAuxiliar.setDescripcion(categoria.getDescripcion());
			categoriaCrear(categoriaAuxiliar, idCapa);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean categoriaCrear(Categoria categoria) {
		try{
			categoriaDao.save(categoria);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean categoriaCrear(CategoriaDto categoria) {
		try{
			Categoria categoriaAuxiliar = new Categoria();
			categoriaAuxiliar.setNombre(categoria.getNombre());
			categoriaAuxiliar.setDescripcion(categoria.getDescripcion());
			if(categoriaCrear(categoriaAuxiliar)) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public List<String> categoriaDtoObtenerNombres() throws Exception {
		List<String> listaNombres = categoriaDao.categoriaDtoObtenerNombres();
		return listaNombres;
	}

	@Override
	public CategoriaDto categoriaDtoObtenerPorId(Long idCategoria) throws Exception {
		CategoriaDto categoria = categoriaDao.categoriaDtoObtenerPorId(idCategoria);
		if(categoria==null){
			LOGGER.info("No se encuentra la categoria con id: "+idCategoria);
		}else {
			if (categoria.getIcono() == null) {
				categoria.setIcono(new Icono());
			}else {
				categoria.getIcono().setRutaLogo(urlIconos + categoria.getIcono().getId());
			}
			if (categoria.getIconoMarcador() == null) {
				categoria.setIconoMarcador(new Icono());
			}else {
				categoria.getIconoMarcador().setRutaLogo(urlIconos + categoria.getIconoMarcador().getId());
			}
		}
		return categoria;
	}

	@Override
	public List<CategoriaDto> categoriaDtoObtenerPorIdCapa(Long idCapa) throws Exception {
		try {
		    List<CategoriaDto> categorias = categoriaDao.categoriaDtoObtenerPorIdCapa(idCapa);
		    if(categorias.isEmpty()) {
			    LOGGER.info("No se encuentran categorias por el id de capa: "+idCapa);
		    }else {
		    	for (CategoriaDto categoriaDto : categorias) {
					if (categoriaDto.getIcono() == null) {
						categoriaDto.setIcono(new Icono());
					}else {
						categoriaDto.getIcono().setRutaLogo(urlIconos + categoriaDto.getIcono().getId());
					}
					if (categoriaDto.getIconoMarcador() == null) {
						categoriaDto.setIconoMarcador(new Icono());
					}else {
						categoriaDto.getIconoMarcador().setRutaLogo(urlIconos + categoriaDto.getIconoMarcador().getId());
					}
				}
		    }
		    return categorias;
		}catch (Exception e) {
			return null;
		}		
	}
	
	@Override
	public List<String> categoriaDtoObtenerUrls() throws Exception {
		List<String> listaUrls = categoriaDao.categoriaDtoObtenerUrls();
		return listaUrls;
	}	
	
}
