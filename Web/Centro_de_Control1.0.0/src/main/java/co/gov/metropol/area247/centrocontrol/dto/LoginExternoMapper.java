package co.gov.metropol.area247.centrocontrol.dto;

import co.gov.metropol.area247.centrocontrol.model.LoginExterno;

/**
 * 
 * @author ageofuentes
 * <h3>
 * Ayudante para la conversión entre los tipos entity 
 * y DTO 
 * </h3>
 *
 */
public class LoginExternoMapper {
	
	public static LoginExterno getEntidad(LoginExternoDto dto){
		LoginExterno entity = new LoginExterno();
		entity.setId(dto.getId());
		entity.setToken(dto.getToken());
		entity.setUsername(dto.getUsername());
		entity.setNombre(dto.getNombre());
		entity.setDescripcion(dto.getDescripcion());
		entity.setFechaAcceso(dto.getFechaAcceso());
		return entity;
	}
	
	public static LoginExternoDto getDTO(LoginExterno entity){
		LoginExternoDto dto = new LoginExternoDto();
		dto.setId(entity.getId());
		dto.setToken(entity.getToken());
		dto.setUsername(entity.getUsername());
		dto.setNombre(entity.getNombre());
		dto.setDescripcion(entity.getDescripcion());
		dto.setFechaAcceso(entity.getFechaAcceso());
		return dto;
	}
	
}
