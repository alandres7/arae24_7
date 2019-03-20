package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findByUsernameEquals(String username);

    List<Usuario> findByEnabledEquals(boolean enabled);

}
