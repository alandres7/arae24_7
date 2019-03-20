package co.gov.metropol.area247.contenedora.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraSubcategoriaRepository;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Subcategoria;
import co.gov.metropol.area247.contenedora.model.dto.SubcategoriaDtoSinListas;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraSubcategoriaService;

@Service
public class IContenedoraSubcategoriaServiceImpl implements IContenedoraSubcategoriaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraSubcategoriaServiceImpl.class);
	
	@Autowired
	IContenedoraSubcategoriaRepository subcategoriaDao;
	
	@Autowired
	IContenedoraCategoriaService categoriaService;
	
	@Override
	public boolean subcategoriaCrear(Subcategoria subcategoria, Long idCategoria) {
		try
		{
			subcategoriaDao.save(subcategoria);
			LOGGER.info("Se ha guardado la subcategoria id: "+subcategoria.getId());
			Categoria categoria = categoriaService.categoriaObtenerPorId(idCategoria);
			//categoria.getSubcategorias().add(subcategoria);
			if(categoriaService.categoriaActualizar(categoria)) {
				return true;
			}else{
				return false;
			}
		}catch(Exception e) {
			LOGGER.error("Ha ocurrido error guardando la subcategoria id: "+subcategoria.getId()+" , mensaje: "+ e.getMessage() +" , causa: "+ e.getCause());
			return false;
		}
	}

	@Override
	public Subcategoria subcategoriaObtenerPorId(Long idSubcategoria) {
		return subcategoriaDao.findOne(idSubcategoria);
	}

	@Override
	public Subcategoria subcategoriaObtenerPorNombre(String nombreCategoria) {
		return subcategoriaDao.findByNombre(nombreCategoria);
	}

	@Override
	public List<Subcategoria> subcategoriaObtenerTodas() {
		return (List<Subcategoria>) subcategoriaDao.findAll();
	}

	@Override
	public boolean subcategoriaActualizar(Subcategoria subcategoria) {
		try
		{
			subcategoriaDao.save(subcategoria);
			LOGGER.info("Se ha guardado la subcategoria id: "+subcategoria.getId());
			return true;
		}catch(Exception e) {
			LOGGER.error("Ha ocurrido error guardando la subcategoria id: "+subcategoria.getId()+" , mensaje: "+ e.getMessage() +" , causa: "+ e.getCause());
			return false;
		}
	}

	@Override
	public boolean subcategoriaEliminar(Long idSubcategoria) {
		try {
			subcategoriaDao.delete(idSubcategoria);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<Subcategoria> subcategoriaObtenerPorIdCategoria(Long idCategoria) {
		return new ArrayList<>();
	}

	@Override
	public SubcategoriaDtoSinListas subcategoriaDtoSinListasObtenerPorId(Long idSubCategoria) throws Exception {
		
		SubcategoriaDtoSinListas subcategoria = subcategoriaDao.subcategoriaDtoSinListasObtenerPorId(idSubCategoria);
		if(subcategoria==null){
			LOGGER.info("Subcategoria no encontrada con id: "+idSubCategoria);
		}
		return subcategoria;
	}

	@Override
	public List<SubcategoriaDtoSinListas> subcategoriaDtoSinListasObtenerTodos() throws Exception {
		List<SubcategoriaDtoSinListas> subcategorias = subcategoriaDao
				.subcategoriaDtoSinListasObtenerTodos();
		if(subcategorias.isEmpty()){
			LOGGER.info("Subcategorias no encontradas");
		}
		return subcategorias;
	}

	@Override
	public List<SubcategoriaDtoSinListas> subcategoriaDtoSinListasObtenerPorIdCategoria(Long idCategoria)
			throws Exception {
		List<Subcategoria> subcategorias = new ArrayList<>();
		if(subcategorias.isEmpty()) {
			LOGGER.info("No se encuentran subcategorias asociadas a la categoria con id: "+idCategoria);
		}
		List<SubcategoriaDtoSinListas> subcategoriasSinListas = new ArrayList<SubcategoriaDtoSinListas>();
		subcategorias.forEach(subcategoria->{
			SubcategoriaDtoSinListas subcategoriaAuxiliarSinListas 
				= new SubcategoriaDtoSinListas(subcategoria);
			subcategoriasSinListas.add(subcategoriaAuxiliarSinListas);
		});
		return subcategoriasSinListas;
	}

	@Override
	public Long subcategoriaObtenerCantidadMarcadores(Long idSubcategoria) throws Exception {
			Long cantidadMarcadores = subcategoriaDao.subcategoriaContarMarcadores(idSubcategoria);
			LOGGER.info("Se encuentran "+cantidadMarcadores+" "
					+ "marcadores para la subcategoria con id: "+cantidadMarcadores);
			return cantidadMarcadores;
	}
	

}
