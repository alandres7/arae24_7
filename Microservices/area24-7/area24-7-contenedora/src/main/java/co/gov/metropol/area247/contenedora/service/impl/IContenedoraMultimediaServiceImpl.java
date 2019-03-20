package co.gov.metropol.area247.contenedora.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.dao.IContenedoraMultimediaRepository;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.contenedora.model.TipoMultimedia;
import co.gov.metropol.area247.contenedora.service.IContenedoraMultimediaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoMultimediaService;
import co.gov.metropol.area247.vigias.model.Vigia;

@Service
public class IContenedoraMultimediaServiceImpl implements IContenedoraMultimediaService {

	@Autowired
	IContenedoraMultimediaRepository multimediaDao;

	@Autowired
	IContenedoraTipoMultimediaService tipoMultimediaService;

	@Autowired
	private Environment env;

	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraMultimediaServiceImpl.class);

	@Override
	public Multimedia multimediaCrear(Long idMultimedia, MultipartFile multimedia) {

		String rutaServidor = env.getProperty("media.server.url");
		String rutaCarpetaMultimedia = env.getProperty("media.folder.url");

		if (!multimedia.isEmpty()) {
			if(idMultimedia==null){
			    try{													
			        String nombreImagen = multimedia.getOriginalFilename();						
			    	String[] partes = nombreImagen.split("\\.");
			    	String extension = partes[partes.length-1];					
			    		
			    	Multimedia multimediaDB = new Multimedia();	
			    	multimediaDB.setNombre("Sin Archivo");
			    	multimediaDB.setRuta("Sin Archivo");
			    	multimediaDao.save(multimediaDB);
			    		
			    	Long idNuevaMulti = multimediaDB.getId();
			    		
			    	byte [] bytes = multimedia.getBytes();
			    	Path path = Paths.get(rutaCarpetaMultimedia + "multimedia_" + idNuevaMulti + "." + extension);
			    	Files.write(path, bytes);
			    		
			    	multimediaDB.setNombre("multimedia_" + idNuevaMulti + "." + extension);
			    	multimediaDB.setRuta(rutaServidor + multimediaDB.getId());
			    	multimediaDB.setTipoMultimedia(tipoMultimediaService
			    	    .tipoMultimediaObtenerPorSubtipoMultimedia(FilenameUtils.getExtension(nombreImagen)));
			    	multimediaDao.save(multimediaDB);	
			    	return multimediaDB;													
			    }catch(Exception e) {
			    	LOGGER.error("No fué posible el registro del multimedia ; " + e.getMessage());
			    	return null;
			    }
			}else{
				return multimediaActualizar(idMultimedia,multimedia);
			}
		} else {
			return null;
		}
	}
	
	@Override
	public Multimedia multimediaCrear(Long idMultimedia, MultipartFile multimedia, Vigia vigia) {

		String rutaServidor = env.getProperty("media.server.url");
		String rutaCarpetaMultimedia = env.getProperty("media.folder.url");

		if (!multimedia.isEmpty()) {
			if(idMultimedia==null){
			    try{													
			        String nombreImagen = multimedia.getOriginalFilename();						
			    	String[] partes = nombreImagen.split("\\.");
			    	String extension = partes[partes.length-1];					
			    		
			    	Multimedia multimediaDB = new Multimedia();	
			    	
			    	multimediaDB.setVigia(vigia );
			    	multimediaDB.setNombre("Sin Archivo");
			    	multimediaDB.setRuta("Sin Archivo");
			    	multimediaDao.save(multimediaDB);
			    		
			    	Long idNuevaMulti = multimediaDB.getId();
			    		
			    	byte [] bytes = multimedia.getBytes();
			    	Path path = Paths.get(rutaCarpetaMultimedia + "multimedia_" + idNuevaMulti + "." + extension);
			    	Files.write(path, bytes);
			    		
			    	multimediaDB.setNombre("multimedia_" + idNuevaMulti + "." + extension);
			    	multimediaDB.setRuta(rutaServidor + multimediaDB.getId());
			    	multimediaDB.setTipoMultimedia(tipoMultimediaService
			    	    .tipoMultimediaObtenerPorSubtipoMultimedia(FilenameUtils.getExtension(nombreImagen)));
			    	multimediaDao.save(multimediaDB);	
			    	return multimediaDB;													
			    }catch(Exception e) {
			    	LOGGER.error("No fué posible el registro del multimedia ; " + e.getMessage());
			    	return null;
			    }
			}else{
				return multimediaActualizar(idMultimedia,multimedia);
			}
		} else {
			return null;
		}
	}
	
	@Override
	public Multimedia multimediaActualizar(Long idMultimedia, MultipartFile multimedia) {

		String rutaServidor = env.getProperty("media.server.url");
		String rutaCarpetaMultimedia = env.getProperty("media.folder.url");

		if (!multimedia.isEmpty()) {
			try{												
				Multimedia multimediaAuxiliar = multimediaDao.findOne(idMultimedia); 
				
				if(multimediaAuxiliar==null) {
					return null;
				} else {					
					File file = new File(rutaCarpetaMultimedia + multimediaAuxiliar.getNombre());
                    file.delete();
                                        
                    String nombreImagen = multimedia.getOriginalFilename();						
					String[] partes = nombreImagen.split("\\.");
					String extencion = partes[partes.length-1];					
					
					byte [] bytes = multimedia.getBytes();
					Path path = Paths.get(rutaCarpetaMultimedia + "multimedia_" + multimediaAuxiliar.getId() + "." + extencion);
					Files.write(path, bytes);
					
					multimediaAuxiliar.setNombre("multimedia_" + multimediaAuxiliar.getId() + "." + extencion);
					multimediaAuxiliar.setRuta(rutaServidor + multimediaAuxiliar.getId());										
					multimediaDao.save(multimediaAuxiliar);
				}
				return multimediaAuxiliar;
			}catch(Exception e) {
				LOGGER.error("No fué posible la actualizacion de la multimedia ; " + e.getMessage());
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Multimedia multimediaObtenerPorId(Long idMultimedia) {
		return multimediaDao.findOne(idMultimedia);
	}

	@Override
	public List<Multimedia> multimediaObtenerPorIdTipoMultimedia(Long idTipoMultimedia) {
		return multimediaDao.findByTipoMultimedia(tipoMultimediaService.tipoMultimediaObtenerPorId(idTipoMultimedia));
	}

	@Override
	public List<Multimedia> multimediaObtenerTodos() {
		return (List<Multimedia>) multimediaDao.findAll();
	}

	@Override
	public Multimedia multimediaObtenerPorNombre(String nombreMultimedia) {
		return multimediaDao.findByNombre(nombreMultimedia);
	}

	public TipoMultimedia obtenerTipoMultimedia(MultipartFile multimedia) {
		for (TipoMultimedia tiposMultimedia : tipoMultimediaService.tipoMultimediaObtenerTodos()) {
			String[] contentType = multimedia.getContentType().split("/");
			if (contentType[1].equals(tiposMultimedia.getSubtipo())) {
				return tipoMultimediaService.tipoMultimediaObtenerPorSubtipoMultimedia(contentType[1]);
			}
		}
		return null;
	}
	
	@Override
	public boolean multimediaEliminar(Long multimediaId) {
		try {
			String rutaCarpetaImagenes = env.getProperty("media.folder.url");
			File file = new File(rutaCarpetaImagenes + multimediaObtenerPorId(multimediaId).getNombre());
            file.delete();
            
			multimediaDao.delete(multimediaId);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
