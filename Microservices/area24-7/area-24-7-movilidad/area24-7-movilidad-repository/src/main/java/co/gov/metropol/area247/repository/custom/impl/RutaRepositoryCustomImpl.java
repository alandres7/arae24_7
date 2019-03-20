package co.gov.metropol.area247.repository.custom.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.geotools.data.oracle.sdo.GeometryConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import co.gov.metropol.area247.repository.custom.RutaRepositoryCustom;
import co.gov.metropol.area247.repository.domain.LineaMetro;
import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;
import oracle.jdbc.OracleConnection;
import oracle.sql.STRUCT;

@Repository
public class RutaRepositoryCustomImpl implements RutaRepositoryCustom {
	
	private static final String MSG_EXCEPCION = "Error al obtener datos en la capa de datos";

	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;

	@Value("${spring.datasource.driver-class-name}")
	private String springDatasourceDriver;

	@Value("${spring.datasource.username}")
	private String springDatasourceUsername;

	@Value("${spring.datasource.password}")
	private String springDatasourcePassword;
	
	@SuppressWarnings("resource")
	@Override
	public Coordinate[] obtenerRutaEntreCoordenadas(String codigo, double latitudO, double longitudO, double latitudD,
			double longitudD, String tipoRuta) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDBConnection();

			if (null != conn) {
				ps = conn.prepareStatement(
						"SELECT pkg_util_rutas.ft_ruta_entre_coordenadas(?, ?, ?, ?, ?, ?) FROM dual");

				ps.setString(1, codigo);
				ps.setDouble(2, latitudO);
				ps.setDouble(3, longitudO);
				ps.setDouble(4, latitudD);
				ps.setDouble(5, longitudD);
				ps.setString(6, tipoRuta);

				rs = ps.executeQuery();

				if (!Utils.isNull(rs)) {
					conn = conn.unwrap(OracleConnection.class);
					GeometryConverter gc = new GeometryConverter((OracleConnection) conn);

					while (rs.next()) {
						Geometry geo = gc.asGeometry((STRUCT) rs.getObject(1));
						return Utils.isNull(geo) ? null : geo.getCoordinates();
					}
				}
			}

		} catch (Exception e) {
			throw new Area247Exception(MSG_EXCEPCION, e.getCause());
		} finally {
			close(rs, ps, conn);
		}

		return new Coordinate[0];

	}
	
	@SuppressWarnings("resource")
	public List<Ruta> findByCodigoOrDescripcion(String parametro) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDBConnection();
			
			if (conn != null) {
			
			ps = conn.prepareStatement(
					"SELECT RU.ID,RU.S_CODIGO_RUTA,RU.S_DESCRIPCION,RU.S_COORDENADAS,RU.S_PRIMER_PUNTO,RU.S_ULTIMO_PUNTO,RU.S_ACTIVO,RU.ID_TIPO_RUTA,RU.N_TIEMPO_ESTIMADO_RUTA,RU.ID_MODO_RUTA FROM MOVILIDAD.T247VIA_RUTA RU WHERE UPPER(RU.S_CODIGO_RUTA) LIKE (?) OR UPPER(TRANSLATE(RU.S_DESCRIPCION,'"
							+ Constantes.ACENTOS + "','" + Constantes.SIN_ACENTOS + "')) LIKE TRANSLATE((?),'"
							+ Constantes.ACENTOS + "','" + Constantes.SIN_ACENTOS + "')");
			final String parametroFinal = "%" + parametro + "%";
			ps.setString(1, parametroFinal);
			ps.setString(2, parametroFinal);
			rs = ps.executeQuery();
			List<Ruta> rutas = new ArrayList<>();

			if (!Utils.isNull(rs)) {
				conn = conn.unwrap(OracleConnection.class);
				GeometryConverter gc = new GeometryConverter((OracleConnection) conn);

				while (rs.next()) {
					Ruta ruta = new Ruta();
					ruta.setId(rs.getLong(1));
					ruta.setCodigo(rs.getString(2));
					ruta.setDescripcion(rs.getString(3));
					Geometry geoCoordenadas = gc.asGeometry((STRUCT) rs.getObject(4));
					ruta.setCoordenadas(new GeometryFactory(null, 8307).createLineString(geoCoordenadas.getCoordinates()));
					Geometry geoPrimerPunto = gc.asGeometry((STRUCT) rs.getObject(5));
					ruta.setPrimerPunto(geoPrimerPunto.getCentroid());
					Geometry geoUltimoPunto = gc.asGeometry((STRUCT) rs.getObject(6));
					ruta.setUltimoPunto(geoUltimoPunto.getCentroid());
					ruta.setRutaActiva(rs.getString(7));
					ruta.setIdTipoRuta(rs.getLong(8));
					ruta.setTiempoEstimado(rs.getLong(9));
					ruta.setIdModoRuta(ModoRecorrido.valueOf(rs.getInt(10)));
					
					rutas.add(ruta);
				}
			}

			return rutas;
			} 
			
		return new ArrayList<>(); 

		} catch (Exception e) {
			throw new Area247Exception(MSG_EXCEPCION);
		} finally {
			close(rs, ps, conn);
		}

	}
	
	@Override
	public List<Object> findInfoLineasAndRutasByCodigoOrDescripcion(String parametro) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();

		try {

			List<Object> rutasConcretas = new ArrayList<>();

				conn = getDBConnection();

				sql.append("SELECT * FROM (");
				
				sql.append("SELECT ").append("RU.ID ID, RU.S_CODIGO_RUTA CODIGO, RU.S_DESCRIPCION, 'R' TIPO ")
						.append("FROM ").append("MOVILIDAD.T247VIA_RUTA RU ").append("WHERE ")
						.append("UPPER(RU.S_CODIGO_RUTA) LIKE UPPER(?) ").append("OR ")
						.append("UPPER(TRANSLATE(RU.S_DESCRIPCION,'" + Constantes.ACENTOS + "', '"
								+ Constantes.SIN_ACENTOS + "')) LIKE UPPER(?)");

				sql.append("UNION ALL ");

				sql.append("SELECT ").append("LM.ID ID, LM.S_CODIGO_LINEA CODIGO, LM.S_DESCRIPCION, 'L' TIPO ")
						.append("FROM ").append("MOVILIDAD.T247VIA_LINEA_METRO LM ").append("WHERE ")
						.append("UPPER(LM.S_CODIGO_LINEA) LIKE UPPER(?) ").append("OR ")
						.append("UPPER(TRANSLATE(LM.S_DESCRIPCION,'" + Constantes.ACENTOS + "', '"
								+ Constantes.SIN_ACENTOS + "')) LIKE UPPER(?)");
				
				sql.append(") WHERE ROWNUM < 11");

				ps = conn.prepareStatement(sql.toString());

				parametro = StringUtils.stripAccents(parametro);
				parametro = "%" + parametro + "%";
				
				ps.setString(1, parametro);
				ps.setString(2, parametro);
				ps.setString(3, parametro);
				ps.setString(4, parametro);

				rs = ps.executeQuery();

				if (!Utils.isNull(rs)) {

					while (rs.next()) {
						
						char tipo = rs.getString(4).charAt(0);
						
						if (tipo == 'L') {
							
							LineaMetro lineaMetro = new LineaMetro();
							lineaMetro.setId(rs.getLong(1));
							lineaMetro.setCodigo(rs.getString(2));
							lineaMetro.setDescripcion(rs.getString(3));
							rutasConcretas.add(lineaMetro);
							
						} else if (tipo == 'R') {
							
							Ruta ruta = new Ruta();
							ruta.setId(rs.getLong(1));
							ruta.setCodigo(rs.getString(2));
							ruta.setDescripcion(rs.getString(3));
							rutasConcretas.add(ruta);
							
						}
					}
				}
				
			return rutasConcretas;

		} catch (Exception e) {
			throw new Area247Exception("Error al obtener datos en la capa de datos");
		} finally {
			close(rs, ps, conn);
		}
	}

	private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			throw new Area247Exception(MSG_EXCEPCION);
		}
		close(ps, conn);
	}

	private void close(PreparedStatement ps, Connection conn) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			throw new Area247Exception(MSG_EXCEPCION);
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			throw new Area247Exception(MSG_EXCEPCION);
		}
	}

	private Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(springDatasourceDriver);
			dbConnection = DriverManager.getConnection(springDatasourceUrl, springDatasourceUsername,
					springDatasourcePassword);
		} catch (Exception e) {
			throw new Area247Exception(MSG_EXCEPCION);
		}
		return dbConnection;
	}

	
}
