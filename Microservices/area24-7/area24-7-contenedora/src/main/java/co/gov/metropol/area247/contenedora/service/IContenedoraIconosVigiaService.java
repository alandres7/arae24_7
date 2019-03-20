package co.gov.metropol.area247.contenedora.service;

import org.springframework.web.multipart.MultipartFile;
import co.gov.metropol.area247.contenedora.model.IconosVigia;


public interface IContenedoraIconosVigiaService {
	
	boolean iconosVigiasGuardar(Long idNodoArbol,MultipartFile iconoVigiaPen,
			MultipartFile iconoVigiaApr,MultipartFile iconoVigiaRec,MultipartFile iconoVigiaWin);
	
	boolean iconosVigiasActualizar(Long idNodoArbol,MultipartFile iconoVigiaPen,
			MultipartFile iconoVigiaApr,MultipartFile iconoVigiaRec,MultipartFile iconoVigiaWin);

	IconosVigia iconosVigiaObtenerPorIdNodoArbol(Long idNodoArbol);
	
}
