package co.gov.metropol.area247.contenedora.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.dao.IContenedoraIconosVigiaRepository;
import co.gov.metropol.area247.contenedora.model.IconosVigia;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconosVigiaService;

@Service
public class IContenedoraIconosVigiaServiceImpl implements IContenedoraIconosVigiaService {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraIconosVigiaServiceImpl.class);
	
	@Autowired
	private IContenedoraIconosVigiaRepository iconosVigiaDao;
	
	@Autowired
	private IContenedoraIconoService iconoService;
	
	
	
	@Override
	public boolean iconosVigiasGuardar(Long idNodoArbol, MultipartFile iconoVigiaPen, MultipartFile iconoVigiaApr,
			MultipartFile iconoVigiaRec, MultipartFile iconoVigiaWin) {

		if ((iconosVigiaDao.findByIdNodoArbol(idNodoArbol)) == null) {

			IconosVigia iconosVigia = new IconosVigia();
			iconosVigia.setIdNodoArbol(idNodoArbol);

			if (iconoVigiaPen != null && !iconoVigiaPen.isEmpty()) {
				Long idIconoVigiaPen = iconoService.iconoCrear(iconoVigiaPen, null);
				if (idIconoVigiaPen != null) {
					iconosVigia.setIdIconoVigiaPen(idIconoVigiaPen);
				}
			}

			if (iconoVigiaApr != null && !iconoVigiaApr.isEmpty()) {
				Long idIconoVigiaApr = iconoService.iconoCrear(iconoVigiaApr, null);
				if (idIconoVigiaApr != null) {
					iconosVigia.setIdIconoVigiaApr(idIconoVigiaApr);
				}
			}

			if (iconoVigiaRec != null && !iconoVigiaRec.isEmpty()) {
				Long idIconoVigiaRec = iconoService.iconoCrear(iconoVigiaRec, null);
				if (idIconoVigiaRec != null) {
					iconosVigia.setIdIconoVigiaRec(idIconoVigiaRec);
				}
			}

			if (iconoVigiaWin != null && !iconoVigiaWin.isEmpty()) {
				Long idIconoVigiaWin = iconoService.iconoCrear(iconoVigiaWin, null);
				if (idIconoVigiaWin != null) {
					iconosVigia.setIdIconoVigiaWin(idIconoVigiaWin);
				}
			}

			try {
				iconosVigiaDao.save(iconosVigia);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return iconosVigiasActualizar(idNodoArbol, iconoVigiaPen, iconoVigiaApr, iconoVigiaRec, iconoVigiaWin);
		}
	}
	
	@Override
	public boolean iconosVigiasActualizar(Long idNodoArbol,MultipartFile iconoVigiaPen,
			MultipartFile iconoVigiaApr,MultipartFile iconoVigiaRec,MultipartFile iconoVigiaWin){
		
		IconosVigia iconosVigia = iconosVigiaDao.findByIdNodoArbol(idNodoArbol);			
		if(iconosVigia != null) { 
			
		    if (iconoVigiaPen != null && !iconoVigiaPen.isEmpty()) {
			    if(iconosVigia.getIdIconoVigiaPen()==null) {
			    	iconosVigia.setIdIconoVigiaPen(iconoService.iconoCrear(iconoVigiaPen,null));
			    }else {
			    	iconosVigia.setIdIconoVigiaPen(iconoService.iconoCrear(iconoVigiaPen,iconosVigia.getIdIconoVigiaPen()));
			    }
		    }
		    
		    if (iconoVigiaApr != null && !iconoVigiaApr.isEmpty()) {
			    if(iconosVigia.getIdIconoVigiaApr()==null) {
			    	iconosVigia.setIdIconoVigiaApr(iconoService.iconoCrear(iconoVigiaApr,null));
			    }else {
			    	iconosVigia.setIdIconoVigiaApr(iconoService.iconoCrear(iconoVigiaApr,iconosVigia.getIdIconoVigiaApr()));
			    }
		    }		    

		    if (iconoVigiaRec != null && !iconoVigiaRec.isEmpty()) {
			    if(iconosVigia.getIdIconoVigiaRec()==null) {
			    	iconosVigia.setIdIconoVigiaRec(iconoService.iconoCrear(iconoVigiaRec,null));
			    }else {
			    	iconosVigia.setIdIconoVigiaRec(iconoService.iconoCrear(iconoVigiaRec,iconosVigia.getIdIconoVigiaRec()));
			    }
		    }
		    
		    if (iconoVigiaWin != null && !iconoVigiaWin.isEmpty()) {
			    if(iconosVigia.getIdIconoVigiaWin()==null) {
			    	iconosVigia.setIdIconoVigiaWin(iconoService.iconoCrear(iconoVigiaWin,null));
			    }else {
			    	iconosVigia.setIdIconoVigiaWin(iconoService.iconoCrear(iconoVigiaWin,iconosVigia.getIdIconoVigiaWin()));
			    }
		    }		    
		    
            try {
        	    iconosVigiaDao.save(iconosVigia);
			    return true;
		    } catch (Exception e) {
			    return false;
		    }
		}else {
			return false;
		}
    }
		
	@Override
	public IconosVigia iconosVigiaObtenerPorIdNodoArbol(Long idNodoArbol){
		
		return iconosVigiaDao.findByIdNodoArbol(idNodoArbol);	
	}
	

}
