package co.gov.metropol.area247.contenedora.service.impl;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import co.gov.metropol.area247.contenedora.dao.IContenedoraIconoRepository;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;

@Service
public class IContenedoraIconoServiceImpl implements IContenedoraIconoService {

	@Autowired
	IContenedoraIconoRepository iconoDao;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
    private Environment env;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraIconoServiceImpl.class);
	
	@Override
	public Long iconoCrear(MultipartFile imagen, Long idIcono) {
		
		String rutaServidor = env.getProperty("iconos.server.url");
		String rutaCarpetaImagenes = env.getProperty("iconos.folder.url");
		
		if(!imagen.isEmpty()) {
			try{								
				if(idIcono == null) {					
					String nombreImagen = imagen.getOriginalFilename();						
					String[] partes = nombreImagen.split("\\.");
					String extencion = partes[partes.length-1];					
					
					Icono icono = new Icono();	
					icono.setNombre("Sin Archivo");
					icono.setRutaLogo("Sin Archivo");	
					iconoDao.save(icono);
					
					Long idNuevoIcono = icono.getId();
					
					byte [] bytes = imagen.getBytes();
					Path path = Paths.get(rutaCarpetaImagenes + "icono_" + idNuevoIcono + "." + extencion);
					Files.write(path, bytes);
					
					icono.setNombre("icono_" + idNuevoIcono + "." + extencion);
					icono.setRutaLogo(rutaServidor + icono.getId());										
					iconoDao.save(icono);	
					return icono.getId();
				} else {
					iconoActualizar(imagen,idIcono);
				}													
			}catch(Exception e) {
				LOGGER.error("Error durante el proceso: "+e.getMessage());
				return null;
			}
		}else {
			return null;
		}
		return null;
	}

	@Override
	public Icono iconoObtenerPorId(Long iconoId) {
		return iconoDao.findOne(iconoId);
	}

	@Override
	public Icono iconoObtenerPorNombre(String iconoNombre) {
		return iconoDao.findByNombre(iconoNombre);
	}

	@Override
	public List<Icono> iconoObtenerTodos() {
		return (List<Icono>) iconoDao.findAll();
	}

	@Override
	public Long iconoActualizar(MultipartFile icono, Long idIcono) {
		if(!icono.isEmpty()) {
			try{
				String rutaServidor = env.getProperty("iconos.server.url");
				String rutaCarpetaImagenes = env.getProperty("iconos.folder.url");
												
				Icono iconoAuxiliar = iconoObtenerPorId(idIcono); 
				
				if(iconoAuxiliar==null) {
					return null;
				} else {					
					File file = new File(rutaCarpetaImagenes + iconoAuxiliar.getNombre());
                    file.delete();
                                        
                    String nombreImagen = icono.getOriginalFilename();						
					String[] partes = nombreImagen.split("\\.");
					String extencion = partes[partes.length-1];					
					
					byte [] bytes = icono.getBytes();
					Path path = Paths.get(rutaCarpetaImagenes + "icono_" + iconoAuxiliar.getId() + "." + extencion);
					Files.write(path, bytes);
					
					iconoAuxiliar.setNombre("icono_" + iconoAuxiliar.getId() + "." + extencion);
					iconoAuxiliar.setRutaLogo(rutaServidor + iconoAuxiliar.getId());										
					iconoDao.save(iconoAuxiliar);
				}
				return iconoAuxiliar.getId();
			}catch(Exception e) {
				return null;
			}
		}else {
			return null;
		}
	}

	@Override
	public boolean iconoEliminar(Long iconoId) {
		try {
			String rutaCarpetaImagenes = env.getProperty("iconos.folder.url");
			File file = new File(rutaCarpetaImagenes + iconoObtenerPorId(iconoId).getNombre());
            file.delete();
            
			iconoDao.delete(iconoId);
			return true;
		}catch(Exception e) {
			return false;
		}
	}


}
