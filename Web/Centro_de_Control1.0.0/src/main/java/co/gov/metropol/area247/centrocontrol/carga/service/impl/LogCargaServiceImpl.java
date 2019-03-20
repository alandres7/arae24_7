package co.gov.metropol.area247.centrocontrol.carga.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.carga.service.IJdbcService;
import co.gov.metropol.area247.centrocontrol.carga.service.ILogCargaService;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.model.FormatoCarga;
import co.gov.metropol.area247.centrocontrol.model.LogCarga;

@Service
public class LogCargaServiceImpl implements ILogCargaService {
	private static Logger logger = LoggerFactory.getLogger(LogCargaServiceImpl.class);

	@Autowired
	private IJdbcService jdbc;
	
	@Override
	public List<LogCarga> consultarLogCarga(int maxRows, String usuario) {
		List<LogCarga> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = jdbc.open();
			ps = conn.prepareStatement("SELECT L.S_USUARIO, L.D_FECHA, F.S_NOMBRE "
					+ " FROM T247CAR_LOG_CARGA L JOIN T247CAR_FORMATO_CARGA F ON F.ID = L.ID_FORMATO "
					+ " WHERE L.S_USUARIO=? ORDER BY L.D_FECHA DESC");
			ps.setString(1, usuario);
			rs = ps.executeQuery();
			int i=1;
			while (rs.next() && i <= maxRows) {
				list.add(build(rs));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Area247Exception("Ocurrió un error al consultar el log de carga.");
		} finally {
			jdbc.close(ps, conn);
		}

		return list;
	}

	private LogCarga build(ResultSet rs) throws SQLException {
		LogCarga log = new LogCarga();
		FormatoCarga formatoCarga = new FormatoCarga();
		log.setUsuario(rs.getString(1));
		log.setFecha(rs.getDate(2));
		formatoCarga.setNombre(rs.getString(3));
		log.setFormatoCarga(formatoCarga);
		return log;
	}

	@Override
	public void saveLog(CargaArchivoDto cargaArchivoDTO, String usuario) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = jdbc.open();
			ps = conn.prepareStatement("INSERT INTO T247CAR_LOG_CARGA (ID,D_FECHA,S_USUARIO,ID_FORMATO) VALUES (HIBERNATE_SEQUENCE.NEXTVAL,?,?,?)");
			ps.setDate(1, new java.sql.Date((new Date()).getTime()));
			ps.setString(2, usuario);
			ps.setLong(3, cargaArchivoDTO.getTipoArchivo().getId());
			ps.execute();

		} catch (Exception e) {
			logger.error(String.format("Error al guardar log de carga %s.", usuario), e);
			throw new Area247Exception(String.format("Error al guardar log de carga %s.", usuario), e);
		} finally {
			jdbc.close(ps, conn);
		}
	}
	
}
