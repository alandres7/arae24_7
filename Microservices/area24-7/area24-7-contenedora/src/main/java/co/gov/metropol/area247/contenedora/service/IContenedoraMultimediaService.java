package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.vigias.model.Vigia;

public interface IContenedoraMultimediaService {

	Multimedia multimediaCrear(Long idMultimedia, MultipartFile multimedia);
	Multimedia multimediaActualizar(Long idMultimedia, MultipartFile multimedia);
	Multimedia multimediaObtenerPorId(Long idMultimedia);
	Multimedia multimediaObtenerPorNombre(String nombreMultimedia);
	List<Multimedia> multimediaObtenerPorIdTipoMultimedia(Long idTipoMultimedia);
	List<Multimedia> multimediaObtenerTodos();
	boolean multimediaEliminar(Long multimediaId);
	Multimedia multimediaCrear(Long idMultimedia, MultipartFile multimedia, Vigia vigia);


}
