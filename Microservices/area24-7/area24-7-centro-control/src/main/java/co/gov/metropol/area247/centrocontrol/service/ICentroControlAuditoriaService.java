package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Auditoria;

public interface ICentroControlAuditoriaService {

	List<Auditoria> auditoriaObtenerTodas()throws Exception;

	Auditoria auditoriaObtenerPorId(Long id)throws Exception;

	Auditoria auditoriaGuardar(Auditoria auditoria)throws Exception;

	boolean auditoriaBorrar(Long id);
}
