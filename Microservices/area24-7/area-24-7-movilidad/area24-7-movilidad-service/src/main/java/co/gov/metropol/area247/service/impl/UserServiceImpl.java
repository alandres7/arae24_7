package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.UsuarioDTO;
import co.gov.metropol.area247.repository.domain.Usuario;
import co.gov.metropol.area247.service.IUsuarioService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractUsuarioService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class UserServiceImpl extends AbstractUsuarioService implements IUsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UsuarioDTO findByUsername(String username) {
        try {
            return mapper.toUsuarioDTO(repository.findByUsernameEquals(username));
        } catch (Exception e) {
            LOGGER.error("An error occurred while trying to find user by username.", username, e);
            throw new Area247Exception(String.format("An error occurred while trying to validate if the client %s exists.", username), e);
        }
    }

	@Override
	public List<UsuarioDTO> findAll() {
		try {
            return mapper.mapToUsuarioDTO((List<Usuario>) repository.findAll());
        } catch (Exception e) {
            LOGGER.error("An error occurred while trying to find the all users.", e);
            throw new Area247Exception("An error occurred while trying to find the all users.", e);
        }
	}

	@Override
	public List<UsuarioDTO> findByEnabled(boolean enabled) {
		try {
            return mapper.mapToUsuarioDTO(repository.findByEnabledEquals(enabled));
        } catch (Exception e) {
            LOGGER.error("An error occurred while trying to find the all the enabled users.", e);
            throw new Area247Exception("An error occurred while trying to find the all enabled users.", e);
        }
	}

	@Override
	public UsuarioDTO find(Long userId) {
		try {
			return mapper.toUsuarioDTO(repository.findOne(userId));
		} catch (Exception e) {
			LOGGER.error("An error occurred while trying to find the user {}.", userId , e);
            throw new Area247Exception(String.format("An error occurred while trying to find the user %s.", userId), e);
		}
	}

	@Override
	public void save(UsuarioDTO userDTO) {
		try {
			Usuario user = mapper.toUsuario(userDTO);
			repository.save(user);
			userDTO.setId(user.getId());
		} catch (Exception e) {
			LOGGER.error("An error occurred while trying to save the user {}.", userDTO, e);
            throw new Area247Exception(String.format("An error occurred while trying to save the user %s.", userDTO), e);
		}
	}

	@Override
	public void update(UsuarioDTO userDTO) {
		try {
			Usuario user = mapper.toUsuario(userDTO);
			repository.save(user);
		} catch (Exception e) {
			LOGGER.error("An error occurred while trying to update the user {}.", userDTO, e);
            throw new Area247Exception(String.format("An error occurred while trying to update the user %s.", userDTO), e);
		}
	}

	@Override
	public void delete(Long userId) {
		try {
			repository.delete(userId);
		} catch (Exception e) {
			LOGGER.error("An error occurred while trying to delete the user {}.", userId, e);
			throw new Area247Exception(String.format("An error occurred while trying to delete the user %s.", userId), e);
		}

	}

	@Override
	public void delete(Long[] userIds) {
		for (Long userId : userIds) {
			try {
				delete(userId);
			} catch (Exception e) {
				LOGGER.error("An error occurred while trying to delete the user {}.", userId, e);
				throw new Area247Exception(String.format("An error occurred while trying to delete the user %s.", userId), e);
			}
		}
	}
}
