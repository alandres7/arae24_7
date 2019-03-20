package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.UsuarioDTO;

public interface IUsuarioService {

	List<UsuarioDTO> findAll();

	List<UsuarioDTO> findByEnabled(boolean enabled);

    UsuarioDTO find(Long userId);

    void save(UsuarioDTO userModel);

    void update(UsuarioDTO userModel);

    void delete(Long userId);

    void delete(Long[] userIds);
}
