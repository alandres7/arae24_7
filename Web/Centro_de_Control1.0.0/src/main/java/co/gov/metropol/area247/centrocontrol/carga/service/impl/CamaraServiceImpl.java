package co.gov.metropol.area247.centrocontrol.carga.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.carga.helper.ErrorCampoArchivoHelper;
import co.gov.metropol.area247.centrocontrol.carga.helper.FormatoCamaraEnum;
import co.gov.metropol.area247.centrocontrol.carga.helper.FormatoVehiculoEnum;
import co.gov.metropol.area247.centrocontrol.carga.service.ICamaraService;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.common.FormatUtils;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;
import co.gov.metropol.area247.centrocontrol.model.Camara;
import co.gov.metropol.area247.centrocontrol.repository.CamaraRepository;

@Service
public class CamaraServiceImpl implements ICamaraService {

	private static Logger LOGGER = LoggerFactory.getLogger(CamaraServiceImpl.class);

	@Autowired
	private CamaraRepository repository;

	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;

	@Value("${spring.datasource.driver-class-name}")
	private String springDatasourceDriver;

	@Value("${spring.datasource.username}")
	private String springDatasourceUsername;

	@Value("${spring.datasource.password}")
	private String springDatasourcePassword;

	private static final String SQL = " INSERT INTO T247CAR_INFORMACION_CAMARAS(" + "ID_CAMARA," + "ID_CAMARA_INFRACCION_TEC,"
			+ "S_DESCRIPCION_CAMARA," + "S_CODIGO_CAMARA," + "S_VIGENTE," + "D_HORA_INICIO," + "D_HORA_FIN,"
			+ "S_LONGITUD," + "S_LATITUD," + "S_ALIAS_CAMARA," + "S_IP_CAMARA," + "ID_TIPO_CAMARA,"
			+ "S_VALIDA_GPS," + "ID_ZONA," +  "ID_TIPO_VIA," + "N_NUMERO_VIA," + "ID_LETRA," + "ID_CARDINALIDAD_VIA,"
			+ "ID_TIPO_VIA_CRUCE," + "N_NUMERO_VIA_CRUCE," + "ID_LETRA_VIA_CRUCE,"  + "ID_CARDINALIDAD_VIA_CRUCE," 
			+  "ID_SECRETARIA," +  "ID_CIUDAD" + ") VALUES (HIBERNATE_SEQUENCE.NEXTVAL, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public List<ErrorCampoArchivoDto> validarLinea(ErrorCampoArchivoHelper eah, String formatoFecha) {

		List<ErrorCampoArchivoDto> el = new ArrayList<>();

		if (eah.formatoInvalido(FormatoVehiculoEnum.values().length)) {
			el.add(new ErrorCampoArchivoDto("La línea no tiene todos los campos"));
			return el;
		}

		el.add(eah.errorNumerico(FormatoCamaraEnum.IDCAMARAINFRACCIONTEC, 10, true));
		el.add(eah.errorAlfanumerico(FormatoCamaraEnum.DESCCAMARA, 200, true));
		el.add(eah.errorAlfanumerico(FormatoCamaraEnum.CODCAMARA, 50, true));
		el.add(eah.errorSoN(FormatoCamaraEnum.VIGENTE));
		el.add(eah.errorFecha(FormatoCamaraEnum.HORAINICIO, formatoFecha, false));
		el.add(eah.errorFecha(FormatoCamaraEnum.HORAFIN, formatoFecha, false));
		el.add(eah.errorDecimal(FormatoCamaraEnum.LONGITUD, 54, true));
		el.add(eah.errorDecimal(FormatoCamaraEnum.LATITUD, 54, true));
		el.add(eah.errorAlfanumerico(FormatoCamaraEnum.ALIASCAMARA, 100, false));
		el.add(eah.errorAlfanumerico(FormatoCamaraEnum.IPCAMARA, 15, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDTIPOCAMARA, 10, true));
		el.add(eah.errorSoN(FormatoCamaraEnum.VALIDAGPS));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDZONA, 10, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDTIPOVIA, 10, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.NUMEROVIA, 4, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDLETRA, 10, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDCARDINALVIA, 10, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDTIPOVIACRUCE, 10, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.NUMEROVIACRUCE, 4, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDLETRAVIACRUCE, 10, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDCARDINALVIACRUCE, 10, false));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDSECRETARIA, 10, true));
		el.add(eah.errorNumerico(FormatoCamaraEnum.IDCIUDAD, 10, true));

		return el;
	}

	@Override
	public void procesarArchivo(int chunkSize, CargaArchivoDto cargaArchivoDTO, int lineas, String separadorCsv,
			int lineasEncabezado, String formatoFecha) {
		Connection conn = null;
		PreparedStatement ps = null;
		int registro = 0;

		try {
			conn = getDBConnection();
			ps = conn.prepareStatement(SQL);

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(cargaArchivoDTO.getArchivo().getInputStream()));

			for (int i = 1; i <= lineasEncabezado; i++) {
				bufferedReader.readLine(); // Saltarse lineas de encabezado
			}

			String linea;
			while ((linea = bufferedReader.readLine()) != null) {

				Camara camara = parseCamara(linea, separadorCsv, formatoFecha);

				int col = 1;

				ps.setLong(col++, camara.getInfraccionTecId());
				ps.setString(col++, camara.getDescripcionCamara());
				ps.setString(col++, camara.getCodigoCamara());
				ps.setString(col++, camara.getVigente());
				ps.setDate(col++, new java.sql.Date(camara.getHoraInicio().getTime()));
				ps.setDate(col++, new java.sql.Date(camara.getHoraFin().getTime()));
				ps.setBigDecimal(col++, camara.getLongitud());
				ps.setBigDecimal(col++, camara.getLatitud());
				ps.setString(col++, camara.getAliasCamara());
				ps.setString(col++, camara.getIpCamara());
				ps.setLong(col++, camara.getTipoCamara());
				ps.setString(col++, camara.getValidaGps());
				ps.setLong(col++, camara.getIdZona());				
				ps.setLong(col++, camara.getIdTipoVia());
				ps.setLong(col++, camara.getNumeroVia());
				ps.setLong(col++, camara.getIdLetra());
				ps.setLong(col++, camara.getIdCardinalidad());
				ps.setLong(col++, camara.getIdTipoViaCruce());
				ps.setLong(col++, camara.getIdNumeroViaCruce());
				ps.setLong(col++, camara.getLetraViaCruceId());
				ps.setLong(col++, camara.getIdCardinalidadViaCruce());
				ps.setLong(col++, camara.getIdSecretaria());
				ps.setLong(col++, camara.getIdCiudad());

				ps.addBatch();

				registro++;

				if (registro % chunkSize == 0) {
					LOGGER.info("...commit de registro " + registro + ".");
					ps.executeBatch();
				}
			}

			LOGGER.info("...commit de registro " + registro + ".");
			ps.executeBatch();

			LOGGER.info("Procesados " + cargaArchivoDTO.getArchivo().getSize() + " bytes.");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Area247Exception("La línea " + (registro)+ " ocasionó un error al guardar en la base de datos.");
		} finally {
			close(ps, conn);
		}
	}

	private Camara parseCamara(String line, String separadorCsv, String formatoFecha) {
		Camara camara = new Camara();

		String[] fields = line.split(separadorCsv, -1);

		camara.setInfraccionTecId(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDCAMARAINFRACCIONTEC.ordinal()]));
		camara.setDescripcionCamara(fields[FormatoCamaraEnum.DESCCAMARA.ordinal()]);
		camara.setCodigoCamara(fields[FormatoCamaraEnum.CODCAMARA.ordinal()]);
		camara.setVigente(fields[FormatoCamaraEnum.VIGENTE.ordinal()]);
		camara.setHoraInicio(FormatUtils.toDate(fields[FormatoCamaraEnum.HORAINICIO.ordinal()], formatoFecha));
		camara.setHoraFin(FormatUtils.toDate(fields[FormatoCamaraEnum.HORAFIN.ordinal()], formatoFecha));
		camara.setLongitud(FormatUtils.toBigDecimal(fields[FormatoCamaraEnum.LONGITUD.ordinal()]));
		camara.setLatitud(FormatUtils.toBigDecimal(fields[FormatoCamaraEnum.LATITUD.ordinal()]));
		camara.setAliasCamara(fields[FormatoCamaraEnum.ALIASCAMARA.ordinal()]);
		camara.setIpCamara(fields[FormatoCamaraEnum.IPCAMARA.ordinal()]);
		camara.setTipoCamara(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDTIPOCAMARA.ordinal()]));
		camara.setValidaGps(fields[FormatoCamaraEnum.VALIDAGPS.ordinal()]);
		camara.setIdZona(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDZONA.ordinal()]));
		camara.setIdTipoVia(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDTIPOVIA.ordinal()]));
		camara.setNumeroVia(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.NUMEROVIA.ordinal()]));;
		camara.setIdLetra(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDLETRA.ordinal()]));
		camara.setIdCardinalidad(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDCARDINALVIA.ordinal()]));
		camara.setIdTipoViaCruce(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDTIPOVIACRUCE.ordinal()]));
		camara.setIdNumeroViaCruce(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.NUMEROVIACRUCE.ordinal()]));
		camara.setLetraViaCruceId(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDLETRAVIACRUCE.ordinal()]));
		camara.setIdCardinalidadViaCruce(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDCARDINALVIACRUCE.ordinal()]));
		camara.setIdSecretaria(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDSECRETARIA.ordinal()]));
		camara.setIdCiudad(FormatUtils.toLongAvoidingNulls(fields[FormatoCamaraEnum.IDCIUDAD.ordinal()]));
		
		return camara;
	}

	private Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(springDatasourceDriver);
			dbConnection = DriverManager.getConnection(springDatasourceUrl, springDatasourceUsername,
					springDatasourcePassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConnection;
	}

	private void close(PreparedStatement ps, Connection conn) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void eliminarDuplicados() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getDBConnection();
			ps = conn.prepareStatement(
					"delete from T247CAR_INFORMACION_CAMARA a where id < (select max(id) from T247CAR_INFORMACION_CAMARA b where a.S_CODIGO_CAMARA = b.S_CODIGO_CAMARA and a.s_descripcion_secretaria=b.s_descripcion_secretaria)");
			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Area247Exception("Ocurrió un error al eliminar duplicados en la base de datos.");
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public List<Camara> findAll() {
		return (List<Camara>) repository.findAll();
	}

	@Override
	public int contarDuplicados() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = getDBConnection();
			ps = conn.prepareStatement(
					"select count(id) from T247CAR_INFORMACION_CAMARA a where id < (select max(id) from T247CAR_INFORMACION_CAMARA b where a.S_CODIGO_CAMARA = b.S_CODIGO_CAMARA and a.s_descripcion_secretaria=b.s_descripcion_secretaria)");
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Area247Exception("Ocurrió un error al contar los duplicados en la base de datos.");
		} finally {
			close(ps, conn);
		}

		return 0;
	}
}
