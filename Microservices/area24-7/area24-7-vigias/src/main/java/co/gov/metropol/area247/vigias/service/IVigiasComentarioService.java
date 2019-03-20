package co.gov.metropol.area247.vigias.service;

import java.util.List;

import co.gov.metropol.area247.vigias.model.ComentarioVigia;
import co.gov.metropol.area247.vigias.model.Vigia;
import co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto;
import co.gov.metropol.area247.vigias.model.dto.VigiaDto;


public interface IVigiasComentarioService {
	
	boolean comentarioVigiaCrear(ComentarioVigia comentarioVigia); 
	boolean comentarioVigiaActalizar(ComentarioVigia comentarioVigia);
	boolean comentarioVigiaEliminar(Long idComentarioVigia);
	ComentarioVigiaDto comentarioVigiaDtoConsultarPorId(Long idComentarioVigia);
	List<ComentarioVigiaDto> comentarioVigiaDtoObtenerPorReporteVigia(Long idReporteVigia);
}
