package co.gov.metropol.area247.huellas.svc;

import java.util.List;

import co.gov.metropol.area247.huellas.model.PreguntaConsumoDto;

public interface IHuellasPreguntaConsumoSvc {
	public abstract List<PreguntaConsumoDto> getPreguntasConsumo(Long idTipoHuella) throws Exception;

	public abstract void crearPreguntaConsumo(PreguntaConsumoDto pregunta) throws Exception;
}
