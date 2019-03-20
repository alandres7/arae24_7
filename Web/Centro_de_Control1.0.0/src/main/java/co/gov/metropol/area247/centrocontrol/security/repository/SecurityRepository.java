package co.gov.metropol.area247.centrocontrol.security.repository;

import java.sql.SQLException;
import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.model.Usuario;



public interface SecurityRepository {
	
	public List<Rol> obtenerRoles() throws SQLException;
	
	public Usuario obtenerUsuarioPorUsername(String username,String password) throws SQLException;

}
