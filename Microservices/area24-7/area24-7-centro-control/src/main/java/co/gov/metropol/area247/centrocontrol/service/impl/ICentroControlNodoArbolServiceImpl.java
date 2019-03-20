package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlNodoArbolRepository;
import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlNodoArbolService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import co.gov.metropol.area247.contenedora.model.IconosVigia;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconosVigiaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMultimediaService;

@Service
public class ICentroControlNodoArbolServiceImpl implements ICentroControlNodoArbolService {

	@Autowired
	ICentroControlNodoArbolRepository nodoArbolRepository;
	
	@Autowired
	ICentroControlObjetoService objetoService;
	
	@Autowired
	IContenedoraMultimediaService multimediaService;
	
	@Autowired
	IContenedoraIconosVigiaService iconosVigiaService;
	
	@Value("${media.server.url}")
	String urlMultimedia;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlNodoArbolServiceImpl.class);
	
	@Override
	public List<NodoArbolDto> nodoArbolDtoObtenerPorIdPadre(Long idPadre) {
		List<NodoArbolDto> nodosArbol = nodoArbolRepository.nodosArbolDtoPorIdPadre(idPadre);
		nodosArbol.forEach(nodoArbol->{
			if(nodoArbol.getFormatoMedia().equals("Imagen")) {
				if(nodoArbol.getIdMultimediaImagen() != null) {
				    nodoArbol.setUrlMediaImagen(urlMultimedia + nodoArbol.getIdMultimediaImagen());
				    nodoArbol.setUrlMediaSeleccionada(urlMultimedia + nodoArbol.getIdMultimediaImagen());				    
				}			
			} else {
				nodoArbol.setUrlMediaSeleccionada(nodoArbol.getUrlMediaVideoAudio());
			}
			if(nodoArbolRepository.cantidadNodosHijos(nodoArbol.getId())==0) {
				nodoArbol.setTieneHijos(false);
			}else {
				nodoArbol.setTieneHijos(true);
			}
			
			IconosVigia iconosVigia = iconosVigiaService.iconosVigiaObtenerPorIdNodoArbol(nodoArbol.getId());
			if(iconosVigia != null) {
				nodoArbol.setIdIconoVigiaPen(iconosVigia.getIdIconoVigiaPen());
				nodoArbol.setUrIconoVigiaPen(urlIconos + iconosVigia.getIdIconoVigiaPen());				
				nodoArbol.setIdIconoVigiaApr(iconosVigia.getIdIconoVigiaApr());
				nodoArbol.setUrIconoVigiaApr(urlIconos + iconosVigia.getIdIconoVigiaApr());				
				nodoArbol.setIdIconoVigiaRec(iconosVigia.getIdIconoVigiaRec());
				nodoArbol.setUrIconoVigiaRec(urlIconos + iconosVigia.getIdIconoVigiaRec());				
				nodoArbol.setIdIconoVigiaWin(iconosVigia.getIdIconoVigiaWin());
				nodoArbol.setUrIconoVigiaWin(urlIconos + iconosVigia.getIdIconoVigiaWin());							
			}									
		});
		if(nodosArbol.isEmpty()){
			LOGGER.info("No se encuentran nodos asociados al nodo con id " + idPadre);
		}
		return nodosArbol;
	}

	@Override
	public List<NodoArbolDto> nodoRaizArbolDtoObtenerPorIdArbol(Long idArbol) {
		List<NodoArbolDto> nodosRaizArbol = new ArrayList<NodoArbolDto>();
		try{
			nodosRaizArbol = nodoArbolRepository.nodosRaizDtoPorIdArbol(idArbol);
			if(nodosRaizArbol.isEmpty()){
				LOGGER.info("No se encuentran nodos asociados a la aplicación con id " + idArbol);
			}else {
				nodosRaizArbol.forEach(nodoRaizArbol->{
					if(nodoRaizArbol.getFormatoMedia().equals("Imagen")) {
						if(nodoRaizArbol.getIdMultimediaImagen() != null) {
						    nodoRaizArbol.setUrlMediaImagen(urlMultimedia + nodoRaizArbol.getIdMultimediaImagen());
						    nodoRaizArbol.setUrlMediaSeleccionada(urlMultimedia + nodoRaizArbol.getIdMultimediaImagen());				    
						}			
					} else {
						nodoRaizArbol.setUrlMediaSeleccionada(nodoRaizArbol.getUrlMediaVideoAudio());
					}
					if(nodoArbolRepository.cantidadNodosHijos(nodoRaizArbol.getId())==0) {
						nodoRaizArbol.setTieneHijos(false);
					}else {
						nodoRaizArbol.setTieneHijos(true);
					}
					
					IconosVigia iconosVigia = iconosVigiaService.iconosVigiaObtenerPorIdNodoArbol(nodoRaizArbol.getId());
					if(iconosVigia != null) {
						nodoRaizArbol.setIdIconoVigiaPen(iconosVigia.getIdIconoVigiaPen());
						nodoRaizArbol.setUrIconoVigiaPen(urlIconos + iconosVigia.getIdIconoVigiaPen());				
						nodoRaizArbol.setIdIconoVigiaApr(iconosVigia.getIdIconoVigiaApr());
						nodoRaizArbol.setUrIconoVigiaApr(urlIconos + iconosVigia.getIdIconoVigiaApr());				
						nodoRaizArbol.setIdIconoVigiaRec(iconosVigia.getIdIconoVigiaRec());
						nodoRaizArbol.setUrIconoVigiaRec(urlIconos + iconosVigia.getIdIconoVigiaRec());				
						nodoRaizArbol.setIdIconoVigiaWin(iconosVigia.getIdIconoVigiaWin());
						nodoRaizArbol.setUrIconoVigiaWin(urlIconos + iconosVigia.getIdIconoVigiaWin());							
					}
				});
			}
		}catch(Exception e){
			LOGGER.error("Intento de obtención de nodos para arbol no existente; " + e.getMessage());
		}
		return nodosRaizArbol;
	}
	
	@Override
	public NodoArbol nodoArbolObtenerPorId(Long idNodoArbol) {
		NodoArbol nodoArbol = nodoArbolRepository.findOne(idNodoArbol);
		if (nodoArbol==null) {
			LOGGER.info("No se encuentra un nodo asociado al id " + idNodoArbol);
		}
		return nodoArbol;
	}

	@Override
	public NodoArbolDto nodoArbolDtoObtenerPorId(Long idNodoArbol) {
		NodoArbolDto nodoArbol = nodoArbolRepository.nodoArbolDtoPorId(idNodoArbol);
		if (nodoArbol==null) {
			LOGGER.info("No se encuentra un nodo asociado al id " + idNodoArbol);
		}else {
			if(nodoArbol.getFormatoMedia().equals("Imagen")) {
				if(nodoArbol.getIdMultimediaImagen() != null) {
				    nodoArbol.setUrlMediaImagen(urlMultimedia + nodoArbol.getIdMultimediaImagen());
				    nodoArbol.setUrlMediaSeleccionada(urlMultimedia + nodoArbol.getIdMultimediaImagen());				    
				}			
			} else {
				nodoArbol.setUrlMediaSeleccionada(nodoArbol.getUrlMediaVideoAudio());
			}
			if(nodoArbolRepository.cantidadNodosHijos(nodoArbol.getId())==0) {
				nodoArbol.setTieneHijos(false);
			}else {
				nodoArbol.setTieneHijos(true);
			}
			
			IconosVigia iconosVigia = iconosVigiaService.iconosVigiaObtenerPorIdNodoArbol(nodoArbol.getId());
			if(iconosVigia != null) {
				nodoArbol.setIdIconoVigiaPen(iconosVigia.getIdIconoVigiaPen());
				nodoArbol.setUrIconoVigiaPen(urlIconos + iconosVigia.getIdIconoVigiaPen());				
				nodoArbol.setIdIconoVigiaApr(iconosVigia.getIdIconoVigiaApr());
				nodoArbol.setUrIconoVigiaApr(urlIconos + iconosVigia.getIdIconoVigiaApr());				
				nodoArbol.setIdIconoVigiaRec(iconosVigia.getIdIconoVigiaRec());
				nodoArbol.setUrIconoVigiaRec(urlIconos + iconosVigia.getIdIconoVigiaRec());				
				nodoArbol.setIdIconoVigiaWin(iconosVigia.getIdIconoVigiaWin());
				nodoArbol.setUrIconoVigiaWin(urlIconos + iconosVigia.getIdIconoVigiaWin());							
			}
		}
		return nodoArbol;
	}

	@Override
	public boolean nodoArbolGuardar(NodoArbol nodoArbol) {
		try{
			NodoArbol nodoArbolAux = nodoArbolRepository.save(nodoArbol); 
			LOGGER.info("Nodo creado exitosamente con id " + nodoArbolAux.getId());
			return true;
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean nodoArbolEliminar(Long idNodoArbol) {
		try{
			NodoArbol nodoArbol = nodoArbolRepository.findOne(idNodoArbol);	
			if(nodoArbol!=null) {
				if(nodoArbol.getIdMultimedia()!=null) {
			        multimediaService.multimediaEliminar(nodoArbol.getIdMultimedia());
				}
			    nodoArbolRepository.delete(idNodoArbol);
			    LOGGER.info(String.format("eliminación de nodo con id %s exitosa", idNodoArbol));
			    return true;
			}else {
				LOGGER.error("no existe ningun nodo con id: " + idNodoArbol);
				return false;
			}
		}catch(Exception e){
			LOGGER.error("Error en intento de eliminación de nodo con id: " + idNodoArbol + " ; " + e.getMessage());
			return false;
		}
	}
	
	@Override
	public Integer cantidadNodosHijos(Long idPadre) {
		return nodoArbolRepository.cantidadNodosHijos(idPadre);
	}

}
