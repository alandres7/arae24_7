package co.gov.metropol.area247.centrocontrol.carga.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.carga.helper.ErrorCampoArchivoHelper;
import co.gov.metropol.area247.centrocontrol.carga.helper.FormatoVehiculoEnum;
import co.gov.metropol.area247.centrocontrol.carga.service.IJdbcService;
import co.gov.metropol.area247.centrocontrol.carga.service.IVehiculoService;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.common.FormatUtils;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.HuellaCarbonoDto;
import co.gov.metropol.area247.centrocontrol.model.Fotodeteccion;
import co.gov.metropol.area247.centrocontrol.model.TipoCarroceriaVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoClaseVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoCombustibleVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoMarcaVehiculo;
import co.gov.metropol.area247.centrocontrol.model.Vehiculo;
import co.gov.metropol.area247.centrocontrol.repository.FotodeteccionRepository;
import co.gov.metropol.area247.centrocontrol.repository.TipoCarroceriaVehiculoRepository;
import co.gov.metropol.area247.centrocontrol.repository.TipoClaseVehiculoRepository;
import co.gov.metropol.area247.centrocontrol.repository.TipoCombustibleVehiculoRepository;
import co.gov.metropol.area247.centrocontrol.repository.TipoMarcaVehiculoRepository;
import co.gov.metropol.area247.centrocontrol.repository.VehiculoRepository;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

	private static Logger LOGGER = LoggerFactory.getLogger(VehiculoServiceImpl.class);

	@Autowired
	private TipoCarroceriaVehiculoRepository repositoryCarroceria;

	@Autowired
	private TipoClaseVehiculoRepository repositoryClase;

	@Autowired
	private TipoCombustibleVehiculoRepository repositoryCombustible;

	@Autowired
	private TipoMarcaVehiculoRepository repositoryMarca;

	@Autowired
	private VehiculoRepository repositoryVehiculo;

	@Autowired
	private FotodeteccionRepository repositoryFotodeteccion;
	
	@Autowired
	private IJdbcService jdbc;

	private static final String SQL = "INSERT INTO T247CAR_VEHICULO ("
			+ "ID,S_NRO_PLACA,N_CAP_TONELADAS,N_MODELO,S_DESC_CARROCERIA,S_DESC_CLASE,S_DESC_COMBUTIBLE,S_DESC_MARCA"
			+ ") VALUES (HIBERNATE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,?)";

	@Override
	public List<ErrorCampoArchivoDto> validarLinea(ErrorCampoArchivoHelper eah) {

		List<ErrorCampoArchivoDto> el = new ArrayList<>();

		if (eah.formatoInvalido(FormatoVehiculoEnum.values().length)) {
			el.add(new ErrorCampoArchivoDto("La línea no tiene todos los campos"));
			return el;
		}

		el.add(eah.errorAlfanumerico(FormatoVehiculoEnum.S_NRO_PLACA, 11, true));
		el.add(eah.errorDecimal(FormatoVehiculoEnum.N_CAP_TONELADAS, 8, false));
		el.add(eah.errorAlfanumerico(FormatoVehiculoEnum.S_DESC_CARROCERIA, 100, false));
		el.add(eah.errorAlfanumerico(FormatoVehiculoEnum.S_DESC_CLASE, 100, false));
		el.add(eah.errorAlfanumerico(FormatoVehiculoEnum.S_DESC_COMBUTIBLE, 100, false));
		el.add(eah.errorAlfanumerico(FormatoVehiculoEnum.S_DESC_MARCA, 100, false));
		el.add(eah.errorNumerico(FormatoVehiculoEnum.N_MODELO, 4, false));

		return el;
	}

	
	@Override
	public void procesarArchivo(int chunkSize, CargaArchivoDto cargaArchivoDTO, int lineas, String separadorCsv,
			int lineasEncabezado) {
		Connection conn = null;
		PreparedStatement ps = null;

		int registro = 0;
		try {
			conn = jdbc.open();
			ps = conn.prepareStatement(SQL);

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(cargaArchivoDTO.getArchivo().getInputStream()));

			for (int i = 1; i <= lineasEncabezado; i++) {
				bufferedReader.readLine(); // Saltarse lineas de encabezado
			}

			String linea;
			while ((linea = bufferedReader.readLine()) != null) {

				Vehiculo vehiculo = parseVehiculo(linea, separadorCsv);

				int col = 1;
				ps.setString(col++, vehiculo.getPlaca());
				ps.setBigDecimal(col++, vehiculo.getToneladas());
				ps.setLong(col++, vehiculo.getModelo());
				ps.setString(col++, vehiculo.getCarroceria());
				ps.setString(col++, vehiculo.getClase());
				ps.setString(col++, vehiculo.getCombustible());
				ps.setString(col++, vehiculo.getMarca());

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
			throw new Area247Exception("La línea " + registro + " ocasionó un error al guardar en la base de datos.");
		} finally {
			jdbc.close(ps, conn);
		}
	}

	private Vehiculo parseVehiculo(String line, String separadorCsv) {
		Vehiculo vehiculo = new Vehiculo();

		String[] fields = line.split(separadorCsv, -1);

		vehiculo.setPlaca(fields[FormatoVehiculoEnum.S_NRO_PLACA.ordinal()]);
		vehiculo.setToneladas(FormatUtils.toBigDecimal(fields[FormatoVehiculoEnum.N_CAP_TONELADAS.ordinal()]));
		vehiculo.setCarroceria(fields[FormatoVehiculoEnum.S_DESC_CARROCERIA.ordinal()]);
		vehiculo.setClase(fields[FormatoVehiculoEnum.S_DESC_CLASE.ordinal()]);
		vehiculo.setCombustible(fields[FormatoVehiculoEnum.S_DESC_COMBUTIBLE.ordinal()]);
		vehiculo.setMarca(fields[FormatoVehiculoEnum.S_DESC_MARCA.ordinal()]);
		vehiculo.setModelo(FormatUtils.toLong(fields[FormatoVehiculoEnum.N_MODELO.ordinal()]));

		return vehiculo;
	}

	@Override
	public void eliminarDuplicados() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = jdbc.open();
			ps = conn.prepareStatement(
					"delete from T247CAR_VEHICULO a where id < (select max(id) from T247CAR_VEHICULO b where a.S_NRO_PLACA = b.S_NRO_PLACA)");
			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Area247Exception("Ocurrió un error al eliminar duplicados en la base de datos.");
		} finally {
			jdbc.close(ps, conn);
		}

	}

	@Override
	public List<TipoCarroceriaVehiculo> consultarCarrocerias() {
		return (List<TipoCarroceriaVehiculo>) repositoryCarroceria.findAll();
	}

	@Override
	public List<TipoClaseVehiculo> consultarClases() {
		return (List<TipoClaseVehiculo>) repositoryClase.findAll();
	}

	@Override
	public List<TipoCombustibleVehiculo> consultarCombustibles() {
		return (List<TipoCombustibleVehiculo>) repositoryCombustible.findAll();
	}

	@Override
	public List<TipoMarcaVehiculo> consultarMarcas() {
		return (List<TipoMarcaVehiculo>) repositoryMarca.findAll();
	}

	@Override
	public List<Vehiculo> consultarVehiculos(HuellaCarbonoDto dto, int maxRows) {

		return repositoryVehiculo.consultarVehiculos(dto.getPlaca(), dto.getClase(), dto.getCarroceria(),
				dto.getMarca(), FormatUtils.toLong(dto.getModelo()), dto.getCombustible(),
				FormatUtils.toBigDecimal(dto.getCapacidad()), new PageRequest(0, maxRows));
	}

	@Override
	public Vehiculo findByPlaca(String placa) {
		return repositoryVehiculo.findByPlaca(placa);
	}

	@Override
	public List<Fotodeteccion> consultarDetecciones(String placa, String fecha) {
		return repositoryFotodeteccion.findByPlacaAndFecha(placa, fecha);
	}

	@Override
	public BigDecimal distanciaRecorrida(String placa, String fecha) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = jdbc.open();

			ps = conn.prepareStatement("SELECT DISTANCIA_MINIMA(?,?) FROM DUAL");

			ps.setString(1, placa);
			ps.setDate(2, new java.sql.Date(FormatUtils.toDate(fecha, "yyyy/MM/dd").getTime()));

			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getBigDecimal(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(rs, ps, conn);
		}

		return new BigDecimal(0);
	}
    
	@Override
	public Double porcentajeHuellaCarbono(String placa, String fecha) {
		Double porcentaje = 0D;

		BigDecimal co2 = calcularHuellaCarbono(placa, fecha);
		BigDecimal sumatoria = calcularHuellaCarbono(null, fecha);

		if (sumatoria.doubleValue() > 0) {
			porcentaje = (co2.divide(sumatoria).multiply(new BigDecimal(100))).doubleValue();
		}
		return porcentaje;
	}

	private BigDecimal calcularHuellaCarbono(String placa, String fecha) {
		String sql = "  SELECT " + "        SUM(" + "            NVL("
				+ "                DISTANCIA_MINIMA(CV.S_PLACA, CV.D_FECHA_PASO) * F.N_FACTOR * (V.N_CAP_TONELADAS / NULLIF(V.N_MODELO, 0))"
				+ "            , 0)" + "        )" + "    FROM T247CAR_CIRCULACION_VEHICULO CV"
				+ "    JOIN T247CAR_VEHICULO V ON V.S_NRO_PLACA = CV.S_PLACA"
				+ "    JOIN T247CAR_FACTOR_EMISION F ON F.S_DESC_COMBUTIBLE = V.S_DESC_COMBUTIBLE"
				+ "    WHERE TO_CHAR(CV.D_FECHA_PASO, 'YYYY/MM/DD') = ?" + "";
		if (null != placa) {
			sql += "    AND CV.S_PLACA=?";
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = jdbc.open();

			ps = conn.prepareStatement(sql);

			ps.setDate(1, new java.sql.Date(FormatUtils.toDate(fecha, "yyyy/MM/dd").getTime()));
			if (null != placa) {
				ps.setString(2, placa);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getBigDecimal(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(rs, ps, conn);
		}

		return new BigDecimal(0);
	}

	@Override
	public Double porcentajeCarroceria(String placa, String fecha) {
		// TODO: Falta la definicion de este indicador
		return 0D;
	}

	@Override
	public int contarVehiculosDuplicados() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = jdbc.open();
			ps = conn.prepareStatement(
					"select count(id) from T247CAR_VEHICULO a where id < (select max(id) from T247CAR_VEHICULO b where a.S_NRO_PLACA = b.S_NRO_PLACA)");
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Area247Exception("Ocurrió un error al contar los duplicados en la base de datos.");
		} finally {
			jdbc.close(ps, conn);
		}

		return 0;
	}
}