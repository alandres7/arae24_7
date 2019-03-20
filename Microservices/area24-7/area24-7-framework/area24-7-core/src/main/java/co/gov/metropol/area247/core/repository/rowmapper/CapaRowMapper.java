package co.gov.metropol.area247.core.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.gov.metropol.area247.core.domain.Icono;
import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.jdbc.util.Utils;

public class CapaRowMapper implements RowMapper<Capa>{

	@Override
	public Capa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Capa capa = new Capa();
		capa.setId(rs.getLong("ID_CAPA"));
		capa.setNombre(rs.getString("NOMBRE_CAPA"));
		capa.setNombreTipoCapa(rs.getString("NOMBRE_TIPO_CAPA"));
		//capa.setRutaIconoCapa(iconoServerUrl + rs.getString("ID_ICONO_CAPA"));
		capa.setContieneHistoria(Utils.isActive(rs.getString("FLAG")) ? Boolean.TRUE : Boolean.FALSE);
		capa.setAplicacion(new Aplicacion());
		capa.getAplicacion().setId(rs.getLong("ID_APLICACION"));
		capa.getAplicacion().setNombre(rs.getString("NOMBRE"));
		capa.getAplicacion().setDescripcion(rs.getString("DESCRIPCION_APLICACION"));
		capa.getAplicacion().setActivo(Utils.isActive(rs.getString("ACTIVO")) ? Boolean.TRUE : Boolean.FALSE);
		capa.getAplicacion().setCodigoColor(rs.getString("CODIGO_COLOR"));
		capa.getAplicacion().setCodigoToggle(rs.getString("CODIGO_TOGGLE"));
		capa.getAplicacion().setRadioAccion(rs.getInt("RADIO_ACCION"));
		capa.getAplicacion().setIcono(new Icono());
		//capa.getAplicacion().getIcono().setRutaLogo(iconoServerUrl + rs.getString("ID_ICONO_APLICACION"));
		return capa;
	}

}
