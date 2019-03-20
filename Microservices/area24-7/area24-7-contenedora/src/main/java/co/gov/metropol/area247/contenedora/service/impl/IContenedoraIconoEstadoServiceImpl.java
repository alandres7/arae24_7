package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.dao.IContenedoraIconoEstadoRepository;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.IconoEstado;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;

@Service
public class IContenedoraIconoEstadoServiceImpl implements IContenedoraIconoEstadoService {
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraIconoEstadoServiceImpl.class);

	@Autowired
	private IContenedoraIconoEstadoRepository iconoEstadoDao;
	
	@Autowired
	private IContenedoraIconoService iconoService;
	
	
	@Override
	public boolean iconoEstadoRegistrar(String nivelCapa,Long idCapaCategoria,
			                            int idEstado,MultipartFile icono){
        Long idCapa = null;
        Long idCategoria = null;
        IconoEstado	iconoEstado = null;

        if (nivelCapa.equals("CAPA")) {
		    iconoEstado = iconoEstadoPorCapaEstado(idCapaCategoria,idEstado);
		    if (iconoEstado != null) {
			    return iconoEstadoActualizar(iconoEstado,icono);
		    }
	    } else {
		    iconoEstado = iconoEstadoPorCategoriaEstado(idCapaCategoria,idEstado);
		    if (iconoEstado != null) {
			    return iconoEstadoActualizar(iconoEstado,icono);
		    }
	    }

        if (nivelCapa.equals("CAPA")) {
            idCapa = idCapaCategoria;
        } else {
            idCategoria = idCapaCategoria;
        }

        iconoEstado = new IconoEstado();
        iconoEstado.setIdCapa(idCapa);
        iconoEstado.setIdCategoria(idCategoria);
        iconoEstado.setIdEstado(idEstado);	
        
        if (icono != null && !icono.isEmpty()) {
            Long idIconoMarcador = iconoService.iconoCrear(icono, null);
            if (idIconoMarcador != null) {
                iconoEstado.setIcono(iconoService.iconoObtenerPorId(idIconoMarcador));
            }
        }
        
        try {
			iconoEstadoDao.save(iconoEstado);
			return true;
		} catch (Exception e) {
			return false;
		}
    }
	
	
	@Override
	public boolean iconoEstadoActualizar(IconoEstado iconoEstado, MultipartFile icono){
		if (icono != null && !icono.isEmpty()) {
			
		    Long idIcono = iconoService.iconoCrear(icono,iconoEstado.getIcono().getId());
			if (idIcono != null) {
				iconoEstado.setIcono(iconoService.iconoObtenerPorId(idIcono));
			}
			
			try {
				iconoEstadoDao.save(iconoEstado);
				return true;
			} catch (Exception e) {
				return false;
			}
	    }else {
	    	return false;	
	    }	
               
    }
	
	
	@Override
	public List<IconoEstado> iconoEstadoPorIdCapa(Long idCapa) {
		List<IconoEstado> iconoEstadoList = iconoEstadoDao.findByIdCapa(idCapa);
		if (iconoEstadoList.isEmpty()) {
			LOGGER.info("No se encuentran iconos por estado asociados a la capa: " + idCapa);
		}else {
			for (IconoEstado iconoEstado : iconoEstadoList) {
				iconoEstado.getIcono().setRutaLogo(urlIconos + iconoEstado.getIcono().getId());
			}
		}		
		return iconoEstadoList;
	}
	
	@Override
	public IconoEstado iconoEstadoPorCapaEstado(Long idCapa, int estado) {
		List<IconoEstado> listIconoEstado = iconoEstadoDao.findByIdCapa(idCapa);
		for (IconoEstado iconoEstado : listIconoEstado) {
			if (iconoEstado.getIdEstado() == estado) {
				return iconoEstado;
			}
		}
		return null;
	}
	
	@Override
	public List<IconoEstado> iconoEstadoPorIdCategoria(Long idCategoria) {
		
		List<IconoEstado> iconoEstadoList = iconoEstadoDao.findByIdCategoria(idCategoria);
		if (iconoEstadoList.isEmpty()) {
			LOGGER.info("No se encuentran iconos por estado asociados a la categoria: " + idCategoria);
		}else {
			for (IconoEstado iconoEstado : iconoEstadoList) {
				iconoEstado.getIcono().setRutaLogo(urlIconos + iconoEstado.getIcono().getId());
			}
		}		
		return iconoEstadoList;
	}
	
	@Override
	public IconoEstado iconoEstadoPorCategoriaEstado(Long idCategoria, int estado) {
		List<IconoEstado> listIconoEstado = iconoEstadoDao.findByIdCategoria(idCategoria);
		for (IconoEstado iconoEstado : listIconoEstado) {
			if (iconoEstado.getIdEstado() == estado) {
				return iconoEstado;
			}
		}
		return null;
	}
	
	@Override
	public boolean iconoEstadoEliminarByCategoria(Long idCategoria, int estado) {
		try {
			List<IconoEstado> listIconoEstado = iconoEstadoDao.findByIdCategoria(idCategoria);
			for (IconoEstado iconoEstado : listIconoEstado) {
				if (iconoEstado.getIdEstado() == estado) {
					Icono icono = iconoEstado.getIcono();
					if(icono!=null) {
					    iconoService.iconoEliminar(icono.getId());
					}										
					iconoEstadoDao.delete(iconoEstado);
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean iconoEstadoEliminarByCapa(Long idCapa, int estado) {
		try {
			List<IconoEstado> listIconoEstado = iconoEstadoDao.findByIdCapa(idCapa);
			for (IconoEstado iconoEstado : listIconoEstado) {
				if (iconoEstado.getIdEstado() == estado) {
					Icono icono = iconoEstado.getIcono();
					if(icono!=null) {
					    iconoService.iconoEliminar(icono.getId());
					}										
					iconoEstadoDao.delete(iconoEstado);
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
