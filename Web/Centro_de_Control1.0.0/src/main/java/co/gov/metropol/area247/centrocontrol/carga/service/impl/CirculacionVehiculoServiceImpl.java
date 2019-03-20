package co.gov.metropol.area247.centrocontrol.carga.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.carga.gateway.ICirculacionVehiculoGateway;
import co.gov.metropol.area247.centrocontrol.carga.helper.ErrorCampoArchivoHelper;
import co.gov.metropol.area247.centrocontrol.carga.helper.FormatoCirculacionEnum;
import co.gov.metropol.area247.centrocontrol.carga.helper.FormatoCirculacionVehiculoEnum;
import co.gov.metropol.area247.centrocontrol.carga.service.ICirculacionVehiculoService;
import co.gov.metropol.area247.centrocontrol.common.Archivo;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.common.FormatUtils;
import co.gov.metropol.area247.centrocontrol.common.StringUtils;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.CirculacionDTO;
import co.gov.metropol.area247.centrocontrol.dto.CirculacionVehiculoDto;
import co.gov.metropol.area247.centrocontrol.dto.CirculacionVehiculoWSDTO;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorLineaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ValidarArchivoDto;
import co.gov.metropol.area247.centrocontrol.model.Vehiculo;
import co.gov.metropol.area247.centrocontrol.repository.SecretariaRepository;
import co.gov.metropol.area247.centrocontrol.repository.VehiculoRepository;

@Service
public class CirculacionVehiculoServiceImpl implements ICirculacionVehiculoService {

	private static Logger LOGGER = LoggerFactory.getLogger(CirculacionVehiculoServiceImpl.class);
	private static final int ERRORES_MAX = 30; // TODO: parametrizar numero maximo de errores a procesar por carga

	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;

	@Value("${spring.datasource.driver-class-name}")
	private String springDatasourceDriver;

	@Value("${spring.datasource.username}")
	private String springDatasourceUsername;

	@Value("${spring.datasource.password}")
	private String springDatasourcePassword;

	@Value("${area247.carga.bytesmax}")
	private int bytesMax;

	@Value("${area247.carga.path}")
	private String path;

	@Value("${area247.carga.path.errores}")
	private String pathErrores;

	@Value("${area247.carga.chunk}")
	private Integer chunkSize;

	@Value("${area247.carga.dateformat}")
	private String formatoFecha;

	@Value("${area247.carga.circulacion.separador}")
	private String separadorCsv;

	@Autowired
	private SecretariaRepository secretariaRepository;

	@Autowired
	private VehiculoRepository repositoryVehiculo;
	
	@Autowired
	private ICirculacionVehiculoGateway circulacionVehiculoGateway;

	private static final String SQL = "INSERT INTO T247CAR_CIRCULACION_VEHICULO ("
			+ "ID,S_TOKEN_AUTENTICACION,D_FECHA,N_CODIGO_DIRECCION,S_CARRIL,D_FECHA_PASO,N_VELOCIDAD_MEDIDA,S_PLACA,D_FECHA_REGISTRO,N_FECHA_DIA,N_FECHA_HORA,ID_SECRETARIA"
			+ ") VALUES (HIBERNATE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQLWS = "INSERT INTO T247CAR_CIRCULACION_VEHICULOS ("
			+ "D_FECHA,N_CODIGO_DIRECCION,S_CARRIL,D_FECHA_PASO,N_VELOCIDAD_MEDIDA,S_PLACA,D_FECHA_REGISTRO,N_FECHA_DIA,N_FECHA_HORA,ID_SECRETARIA"
			+ ") VALUES (?,?,?,?,?,?,?,?,?,?)";


	@Override
	public void save(String token, CirculacionVehiculoDto circulacion) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getDBConnection();
			ps = conn.prepareStatement(SQL);

			Vehiculo vehiculo = repositoryVehiculo.findByPlaca(circulacion.getPlaca());

			if ((vehiculo != null) && (secretariaRepository.exists(circulacion.getIdSecretaria()))) {
				LOGGER.info("Entro a Guardar");
				int col = 1;
				ps.setString(col++, token);
				ps.setDate(col++, new java.sql.Date(circulacion.getFecha().getTime()));
				ps.setInt(col++, circulacion.getCodigoDireccion());
				ps.setString(col++, circulacion.getCarril());
				ps.setDate(col++, new java.sql.Date(circulacion.getFechaPaso().getTime()));
				ps.setInt(col++, circulacion.getVelocidadMedida());
				ps.setString(col++, circulacion.getPlaca());
				ps.setDate(col++, new java.sql.Date(circulacion.getFechaRegistro().getTime()));
				ps.setInt(col++, circulacion.getFechaDia());
				ps.setInt(col++, circulacion.getFechaHora());
				ps.setLong(col++, circulacion.getIdSecretaria());

				ps.addBatch();
			}

			ps.executeBatch();

		} catch (Exception e) {
			LOGGER.error("Error °°°°°°°°°°° " + e.getMessage());
			throw new Area247Exception("Ocurrio un error al guardar en la base de datos.");
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public void procesarArchivo() {

		File file = new File(path);
		try {
			if (file.exists()) {
				File[] ficheros = file.listFiles();
				for (int x = 0; x < ficheros.length; x++) {
					if (ficheros[x].getName().toLowerCase().endsWith("txt")) {

						FileReader archivo = new FileReader(path + ficheros[x].getName());
						ValidarArchivoDto validarArchivoDto = validarArchivo(archivo, separadorCsv, formatoFecha);
						archivo.close();
						List<ErrorLineaArchivoDto> errores = validarArchivoDto.getErrores();

						if (null != errores && !errores.isEmpty()) {
							LOGGER.info("Generarndo log Errores" + pathErrores + ficheros[x].getName());
							generarLogTexto(pathErrores + ficheros[x].getName(), errores);
						} else {
							FileReader archivoC = new FileReader(path + ficheros[x].getName());
							saveArchivo(chunkSize, archivoC, separadorCsv, formatoFecha);
							archivoC.close();
							ficheros[x].delete();
						}
					}

				}
			} else {
				LOGGER.info("No se encuentra la ruta.");
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrio un Error °°°°°°°°°°° " + e.getMessage());
		}

	}

	public ValidarArchivoDto validarArchivo(FileReader archivo, String separadorCsv, String formatoFecha)
			throws IOException {
		int lineas = 0;
		List<ErrorLineaArchivoDto> errores = new ArrayList<>();

		BufferedReader bufferedReader = new BufferedReader(archivo);

		String linea;
		while ((linea = bufferedReader.readLine()) != null) {
			lineas++;
			LOGGER.info("Validando la linea " + linea);
			ErrorLineaArchivoDto error = validarLinea(linea, lineas, separadorCsv, formatoFecha);
			if (null != error) {
				errores.add(error);
			}

			if (errores.size() > ERRORES_MAX) {
				List<ErrorCampoArchivoDto> el = new ArrayList<>();
				el.add(new ErrorCampoArchivoDto(
						"El archivo contiene muchos errores, por favor corríjalos y vuélvalo a cargar"));
				errores.add(new ErrorLineaArchivoDto(lineas, "", el));
				break;
			}
		}

		if (lineas == 0) {
			throw new Area247Exception("El archivo está vacío.");
		}

		ValidarArchivoDto dto = new ValidarArchivoDto();
		dto.setErrores(errores);
		dto.setLineas(lineas);
		return dto;
	}

	private ErrorLineaArchivoDto validarLinea(String linea, int numeroLinea, String separadorCsv, String formatoFecha) {

		List<ErrorCampoArchivoDto> el = new ArrayList<>();

		if (null == linea || "".equals(linea.trim())) {
			el.add(new ErrorCampoArchivoDto("La línea está en blanco"));
			return new ErrorLineaArchivoDto(numeroLinea, "", el);
		}

		ErrorCampoArchivoHelper eah = new ErrorCampoArchivoHelper(linea, separadorCsv);

		el = validarLinea(eah, formatoFecha);

		el.removeAll(Collections.singleton(null));

		if (el.isEmpty()) {
			return null;
		}

		return new ErrorLineaArchivoDto(numeroLinea, StringUtils.truncarString(eah.getId()), el);
	}

	
	private List<ErrorCampoArchivoDto> validarLinea(ErrorCampoArchivoHelper eah, String formatoFecha) {

		List<ErrorCampoArchivoDto> el = new ArrayList<>();

		if (eah.formatoInvalido(FormatoCirculacionVehiculoEnum.values().length)) {
			el.add(new ErrorCampoArchivoDto("La línea no tiene todos los campos"));
			return el;
		}

		el.add(eah.errorFecha(FormatoCirculacionVehiculoEnum.FECHA, formatoFecha, false));
		el.add(eah.errorNumerico(FormatoCirculacionVehiculoEnum.CODDIRECCION, 9, true));
		el.add(eah.errorAlfanumerico(FormatoCirculacionVehiculoEnum.CARRIL, 10, true));
		el.add(eah.errorFecha(FormatoCirculacionVehiculoEnum.FECHAPASO, formatoFecha, true));
		el.add(eah.errorNumerico(FormatoCirculacionVehiculoEnum.VELMEDIDA, 3, true));
		el.add(eah.errorAlfanumerico(FormatoCirculacionVehiculoEnum.PLACA, 7, true));
		el.add(eah.errorFecha(FormatoCirculacionVehiculoEnum.FEREGISTRO, formatoFecha, true));
		el.add(eah.errorNumerico(FormatoCirculacionVehiculoEnum.FECHADIA, 8, true));
		el.add(eah.errorNumerico(FormatoCirculacionVehiculoEnum.FECHAHORA, 10, true));
		el.add(eah.errorNumerico(FormatoCirculacionVehiculoEnum.IDSECRETARIA, 11, true));

		return el;
	}
	
	private void saveArchivo(int chunkSize, FileReader archivo, String separadorCsv, String formatoFecha) {
		Connection conn = null;
		PreparedStatement ps = null;

		int registro = 0;
		try {
			conn = getDBConnection();
			ps = conn.prepareStatement(SQL);

			BufferedReader bufferedReader2 = new BufferedReader(archivo);
			LOGGER.info("Leyendo Lineas");
			String linea;
			while ((linea = bufferedReader2.readLine()) != null) {
				LOGGER.info("Recorriendo linea");
				CirculacionVehiculoDto circulacion = parseCirculacionVehiculo(linea, separadorCsv, formatoFecha);
				LOGGER.info("Recorriendo linea");
				int col = 1;
				Vehiculo vehiculo = repositoryVehiculo.findByPlaca(circulacion.getPlaca());
				LOGGER.info("Antes de Guardar");
				if ((vehiculo != null) && (secretariaRepository.exists(circulacion.getIdSecretaria()))) {
					LOGGER.info("Entro a Guardar");
					ps.setString(col++, circulacion.getTokenAutenticacion());
					ps.setDate(col++, new java.sql.Date(circulacion.getFecha().getTime()));
					ps.setInt(col++, circulacion.getCodigoDireccion());
					ps.setString(col++, circulacion.getCarril());
					ps.setDate(col++, new java.sql.Date(circulacion.getFechaPaso().getTime()));
					ps.setInt(col++, circulacion.getVelocidadMedida());
					ps.setString(col++, circulacion.getPlaca());
					ps.setDate(col++, new java.sql.Date(circulacion.getFechaRegistro().getTime()));
					ps.setInt(col++, circulacion.getFechaDia());
					ps.setInt(col++, circulacion.getFechaHora());
					ps.setLong(col++, circulacion.getIdSecretaria());

					ps.addBatch();

					registro++;

					if (registro % chunkSize == 0) {
						LOGGER.info(".. commit de registro... " + registro + ".");
						ps.executeBatch();
					}
				}
			}

			LOGGER.info(".... commit de registro... " + registro + ".");
			ps.executeBatch();

		} catch (Exception e) {
			LOGGER.error("Error °°°°°°°°°°° " + e.getMessage());
			throw new Area247Exception("Ocurrio un error al guardar en la base de datos.");
		} finally {
			close(ps, conn);
		}
	}

	private CirculacionVehiculoDto parseCirculacionVehiculo(String linea, String separadorCsv, String formatoFecha) {
		CirculacionVehiculoDto circulacion = new CirculacionVehiculoDto();

		String[] fields = linea.split(separadorCsv, -1);

		circulacion.setTokenAutenticacion(fields[FormatoCirculacionVehiculoEnum.TOKENAUTENTICACION.ordinal()]);
		circulacion.setFecha(FormatUtils.toDate(fields[FormatoCirculacionVehiculoEnum.FECHA.ordinal()], formatoFecha));
		circulacion
				.setCodigoDireccion(Integer.parseInt((fields[FormatoCirculacionVehiculoEnum.CODDIRECCION.ordinal()])));
		circulacion.setCarril(fields[FormatoCirculacionVehiculoEnum.CARRIL.ordinal()]);
		circulacion.setFechaPaso(
				FormatUtils.toDate(fields[FormatoCirculacionVehiculoEnum.FECHAPASO.ordinal()], formatoFecha));
		circulacion.setVelocidadMedida(Integer.parseInt(fields[FormatoCirculacionVehiculoEnum.VELMEDIDA.ordinal()]));
		circulacion.setPlaca(fields[FormatoCirculacionVehiculoEnum.PLACA.ordinal()]);
		circulacion.setFechaRegistro(
				FormatUtils.toDate(fields[FormatoCirculacionVehiculoEnum.FEREGISTRO.ordinal()], formatoFecha));
		circulacion.setFechaDia(Integer.parseInt(fields[FormatoCirculacionVehiculoEnum.FECHADIA.ordinal()]));
		circulacion.setFechaHora(Integer.parseInt(fields[FormatoCirculacionVehiculoEnum.FECHAHORA.ordinal()]));
		circulacion.setIdSecretaria(FormatUtils.toLong(fields[FormatoCirculacionVehiculoEnum.IDSECRETARIA.ordinal()]));
		return circulacion;
	}

	private Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(springDatasourceDriver);
			dbConnection = DriverManager.getConnection(springDatasourceUrl, springDatasourceUsername,
					springDatasourcePassword);
		} catch (Exception e) {
			LOGGER.error("Error°°°° " + e.getMessage());
		}
		return dbConnection;
	}

	private void close(PreparedStatement ps, Connection conn) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error°°°°°°° " + e.getMessage());
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error°°°°°° " + e.getMessage());
		}
	}

	private void generarLogTexto(String ruta, List<ErrorLineaArchivoDto> errores) {
		Archivo log = new Archivo(ruta);

		try {
			log.abrirArchivo(true);
			for (int i = 0; i < errores.size(); i++) {
				String linea = "";
				String mensajeError = "";
				String nombre = "";
				String valor = "";

				int tamEror = errores.get(i).getErroresLinea().size();
				for (int y = 0; y < tamEror; y++) {
					mensajeError = errores.get(i).getErroresLinea().get(y).getMensajeError();
					nombre = errores.get(i).getErroresLinea().get(y).getNombre();
					valor = errores.get(i).getErroresLinea().get(y).getValor();

					linea = "Linea " + errores.get(i).getLinea() + " - " + mensajeError + " - " + nombre + " - "
							+ valor;
					log.escribirArchivo(linea);
				}

			}
			log.cerrarArchivo();
		} catch (Exception e) {
			LOGGER.error("Error°°°°°°°°°°°°°°° " + e.getMessage());
		}
	}

	@Override
	public void saveFile(CirculacionVehiculoDto circulacionVehiculo) {
		try {
			File tempFile = File.createTempFile("CargaCirculacion", null);
			BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
			for (CirculacionVehiculoDto circulacion : circulacionVehiculo) {
				String linea = circulacion.getTokenAutenticacion() + separadorCsv + circulacion.getFecha()
				        + separadorCsv + circulacion.getCodigoDireccion() + separadorCsv + circulacion.getCarril()
						+ separadorCsv + circulacion.getFechaPaso() + separadorCsv + circulacion.getVelocidadMedida()
						+ separadorCsv + circulacion.getPlaca() + separadorCsv + circulacion.getFechaRegistro()
						+ separadorCsv + circulacion.getFechaDia() + separadorCsv + circulacion.getFechaHora()
						+ separadorCsv + circulacion.getIdSecretaria();

				out.write(linea);
			}
			out.close();
			
			FileReader archivo = new FileReader(tempFile);
			ValidarArchivoDto validarArchivoDto = validarArchivo(archivo, separadorCsv, formatoFecha);
			archivo.close();
			List<ErrorLineaArchivoDto> errores = validarArchivoDto.getErrores();

			if (null != errores && !errores.isEmpty()) {
				LOGGER.info("Generarndo log Errores...." + pathErrores + tempFile.getName());
				generarLogTexto(pathErrores + tempFile.getName(), errores);
			} else {
				FileReader archivoC = new FileReader(tempFile);
				saveArchivo(chunkSize, archivoC, separadorCsv, formatoFecha);
				archivoC.close();
				tempFile.deleteOnExit();
			}
			
		} catch (IOException e) {
			LOGGER.error("Error °°°°°°°°°°°°°° " + e.getMessage());
		}

	}

	@Override
	public void cargarCirculacionVehiculo() {
		CirculacionVehiculoWSDTO circulacionVehiculoWSDTO = circulacionVehiculoGateway.cargarCiculacionVeiculos(); 
		if(circulacionVehiculoWSDTO!=null) {
			saveCirculacionVehiculoRest(circulacionVehiculoWSDTO.getLstCircualcionVechiculo());
		}
		
	}
	
	public void saveCirculacionVehiculoRest(ArrayList<CirculacionDTO> circulacion) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
			conn = getDBConnection();
			ps = conn.prepareStatement(SQLWS);
			
				LOGGER.info("Inicia guardado de circulación.");
				for (CirculacionDTO circulacionObj : circulacion) {
					int col = 1;
					Date date = new Date();
					ps.setDate(col++, new java.sql.Date(date.getTime()));
					ps.setInt(col++, circulacionObj.getCodigoDireccion());
					ps.setString(col++, circulacionObj.getCarril());
					Date dateFechaPaso = sdf.parse(circulacionObj.getFechaPaso());
					ps.setDate(col++, new java.sql.Date(dateFechaPaso.getTime()));
					ps.setInt(col++, circulacionObj.getVelocidadMedida());
					ps.setString(col++, circulacionObj.getPlaca());
					Date dateFechaReg = sdf.parse(circulacionObj.getFechaRegistro());
					ps.setDate(col++, new java.sql.Date(dateFechaReg.getTime()));
					ps.setInt(col++, circulacionObj.getFechaDia());
					ps.setInt(col++, circulacionObj.getFechaHora());
					ps.setLong(col++, circulacionObj.getIdSecretaria());

					ps.addBatch();
					col=0;
				}
				
				ps.executeBatch();
				LOGGER.info("Información de circulación registrada");
		} catch (Exception e) {
			LOGGER.error("Error de inserción de circulación " + e.getMessage());
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public void procesarArchivoCirculacion(int chunkSize, CargaArchivoDto cargaArchivoDTO, int lineas, String separadorCsv, int lineasEncabezado) {
		Connection conn = null;
		PreparedStatement ps = null;
		int registro = 0;
		try {
			conn = getDBConnection();
			ps = conn.prepareStatement(SQLWS);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cargaArchivoDTO.getArchivo().getInputStream()));

			for (int i = 1; i <= lineasEncabezado; i++) {
				bufferedReader.readLine(); 
			}

			String linea;
			while ((linea = bufferedReader.readLine()) != null) {

				CirculacionDTO circulacion = parseCirculacion(linea, separadorCsv);

				int col = 1;
				Date date = new Date();
				ps.setDate(col++, new java.sql.Date(date.getTime()));
				ps.setInt(col++, circulacion.getCodigoDireccion());
				ps.setString(col++, circulacion.getCarril());
				Date dateFechaPaso = sdf.parse(circulacion.getFechaPaso());
				ps.setDate(col++, new java.sql.Date(dateFechaPaso.getTime()));
				ps.setInt(col++, circulacion.getVelocidadMedida());
				ps.setString(col++, circulacion.getPlaca());
				Date dateFechaReg = sdf.parse(circulacion.getFechaRegistro());
				ps.setDate(col++, new java.sql.Date(dateFechaReg.getTime()));
				ps.setInt(col++, circulacion.getFechaDia());
				ps.setInt(col++, circulacion.getFechaHora());
				ps.setLong(col++, circulacion.getIdSecretaria());

				ps.addBatch();
				col=0;

				registro++;

			}

			LOGGER.info("...commit de registro " + registro + ".");
			ps.executeBatch();

			LOGGER.info("Procesados " + cargaArchivoDTO.getArchivo().getSize() + " bytes.");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Area247Exception("La línea " + registro + " ocasionó un error al guardar en la base de datos.");
		} finally {
			close(ps, conn);
		}		
	}
	
	private CirculacionDTO parseCirculacion(String line, String separadorCsv) {
		CirculacionDTO circulacion = new CirculacionDTO();
		String[] fields = line.split(separadorCsv, -1);
		
		circulacion.setCodigoDireccion(Integer.parseInt(fields[FormatoCirculacionEnum.CODDIRECCION.ordinal()]));
		circulacion.setCarril(fields[FormatoCirculacionEnum.CARRIL.ordinal()]);
		circulacion.setFechaPaso(fields[FormatoCirculacionEnum.FECHAPASO.ordinal()]);
		circulacion.setVelocidadMedida(Integer.parseInt(fields[FormatoCirculacionEnum.VELMEDIDA.ordinal()]));
		circulacion.setPlaca(fields[FormatoCirculacionEnum.PLACA.ordinal()]);
		circulacion.setFechaRegistro(fields[FormatoCirculacionEnum.FEREGISTRO.ordinal()]);
		circulacion.setFechaDia(Integer.parseInt(fields[FormatoCirculacionEnum.FECHADIA.ordinal()]));
		circulacion.setFechaHora(Integer.parseInt(fields[FormatoCirculacionEnum.FECHAHORA.ordinal()]));
		circulacion.setIdSecretaria(Long.valueOf(fields[FormatoCirculacionEnum.IDSECRETARIA.ordinal()]));
		
		return circulacion;
	}
	
	@Override
	public List<ErrorCampoArchivoDto> validarLineaArchivo(ErrorCampoArchivoHelper eah, String formatoFecha) {

		List<ErrorCampoArchivoDto> el = new ArrayList<>();

		if (eah.formatoInvalido(FormatoCirculacionEnum.values().length)) {
			el.add(new ErrorCampoArchivoDto("La línea no tiene todos los campos"));
			return el;
		}

		el.add(eah.errorNumerico(FormatoCirculacionEnum.CODDIRECCION, 9, true));
		el.add(eah.errorAlfanumerico(FormatoCirculacionEnum.CARRIL, 10, true));
		el.add(eah.errorFecha(FormatoCirculacionEnum.FECHAPASO, formatoFecha, true));
		el.add(eah.errorNumerico(FormatoCirculacionEnum.VELMEDIDA, 3, true));
		el.add(eah.errorAlfanumerico(FormatoCirculacionEnum.PLACA, 7, true));
		el.add(eah.errorFecha(FormatoCirculacionEnum.FEREGISTRO, formatoFecha, true));
		el.add(eah.errorNumerico(FormatoCirculacionEnum.FECHADIA, 8, true));
		el.add(eah.errorNumerico(FormatoCirculacionEnum.FECHAHORA, 10, true));
		el.add(eah.errorNumerico(FormatoCirculacionEnum.IDSECRETARIA, 11, true));

		return el;
	}

}
