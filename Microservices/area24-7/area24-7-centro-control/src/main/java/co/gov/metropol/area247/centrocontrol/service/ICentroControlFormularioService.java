package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Formulario;

public interface ICentroControlFormularioService {
	
	List<Formulario> formularioObtenerTodos();
	Formulario formularioObtenerPorNombre(String nombreFormulario);
	Formulario formularioObtenerPorId(Long idFormulario);
	boolean formularioGuardar(Formulario formulario);
	boolean formularioEliminar(Long idFormulario);	
}
