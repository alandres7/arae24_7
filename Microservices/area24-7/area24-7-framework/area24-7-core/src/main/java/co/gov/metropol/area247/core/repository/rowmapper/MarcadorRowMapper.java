package co.gov.metropol.area247.core.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.domain.marker.dto.Point;

/**
 * Devuelve el objeto actual como una fila, en este caso devolvera {@link MarkerPoint}
 * Marcadores donde devuelve los siguientes parametros (1) id del marcador, (2)latitud, (3)longitud y  (4) un enlance del icono 
 * La Razon que lo hacemos de esta forma es por que nos ayuda con el performance de la aplicacion, ya que al utilizar
 * los nombres de los parametros de la consulta es lento cuando se cargan 50000 marcadores. 
 * 
 * @author mario.silva@ada.co
 *
 */
public class MarcadorRowMapper implements RowMapper<MarkerPoint> {

	@Override
	public MarkerPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
		MarkerPoint markerPoint = new MarkerPoint();
		markerPoint.setIdMarker(rs.getLong(1));
		markerPoint.setPoint(new Point(rs.getDouble(3), rs.getDouble(2)));
		markerPoint.setRutaWebIcono(rs.getString(4));
		return markerPoint;
	}

}
