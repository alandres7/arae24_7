package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.UsuarioMapper;
import co.gov.metropol.area247.repository.UsuarioRepository;

public abstract class AbstractUsuarioService {

	@Autowired
	protected UsuarioMapper mapper;
	
	@Autowired
	protected UsuarioRepository repository;
}
