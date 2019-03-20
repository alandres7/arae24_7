package co.gov.metropol.area247.core.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.gov.metropol.area247.core.domain.Icono;
import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.jdbc.util.Utils;

public class AplicacionRowMapper implements RowMapper<Aplicacion>{

	@Override
	public Aplicacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.setId(rs.getLong("ID_APLICACION"));
		aplicacion.setNombre(rs.getString("NOMBRE_APLICACION"));
		aplicacion.setDescripcion(rs.getString("DESCRIPCION_APLICACION"));
		aplicacion.setCodigoColor(rs.getString("CODIGO_COLOR"));
		aplicacion.setCodigoToggle(rs.getString("CODIGO_TOGGLE"));
		aplicacion.setActivo(Utils.isActive(rs.getString("ACTIVO")) ? Boolean.TRUE : Boolean.FALSE);
		aplicacion.setIcono(new Icono());
		aplicacion.getIcono().setId(rs.getLong("ID_ICONO"));
		aplicacion.getIcono().setNombre(rs.getString("NOMBRE_ICONO"));
		aplicacion.getIcono().setRutaLogo(rs.getString("RUTA_ICONO"));
		return aplicacion;
	}

}
