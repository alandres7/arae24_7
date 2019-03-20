package co.gov.metropol.area247.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.service.IShellScriptService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.util.constantes.Constantes.TipoParametro;
import co.gov.metropol.area247.util.ex.Area247Exception;

/**
 * Esta clase crear el Shell Script que actualiza el OpenTripPlanner, detiene
 * los servicios de XAMPP y OpenTripPlanner, elimina el GTFS que se encuentre en
 * la carpeta del OPT copia el GTFS General(Archivo que contiene la informacion
 * de Metro y GTPC) y pega en la carpeta del OTP y levanta nuevamente los
 * servicios de XAMPP y OpenTripPlanner.
 */
@Service("shActualizaOTPService")
public class ShActualizaOTPServiceImpl implements IShellScriptService {

	@Autowired
	private ITipoParametroService tipoParametroService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.gov.metropol.area247.service.IShellScriptService#crearShellScript()
	 */
	@Override
	public File crearShellScript() {

		try {

			String nombreJarOTP = tipoParametroService.obtenerValorCadena(TipoParametro.NAME_JAR_OTP);
			String nombreGtfsGeneral = tipoParametroService.obtenerValorCadena(TipoParametro.NAME_GTFS_GENERAL);
			String urlDirectorioOTP = tipoParametroService.obtenerValorCadena(TipoParametro.URL_FOLDER_OTP);
			String urlDirectorioGtfsGeneral = tipoParametroService
					.obtenerValorCadena(TipoParametro.URL_FOLDER_GTFS_GENERAL);

			// Creamos el archivo .sh, esta ruta debe estar parametrizada
			String ruta = "/opt/shell.sh";
			StringBuilder contenido = new StringBuilder("#!/bin/sh\r\n");
			// Matamos el servicio de OpenTripPlanner
			contenido.append("sudo kill $(ps -aef | grep -v grep | grep '").append(nombreJarOTP)
					.append("' | awk '{print $2}')\r\n")
					// Matamos el servicio de xampp
					.append("sudo /opt/lampp/lampp stop \r\n")
					// Eliminamos el GTFS de la carpeta del OTP
					.append("sudo find ").append(urlDirectorioOTP).append(" -name \"*.zip\" -type f -delete \r\n")
					// Movemos el GTFS a la carpeta de OTP
					.append("sudo mv ").append(urlDirectorioGtfsGeneral).append(nombreGtfsGeneral).append(" ")
					.append(urlDirectorioOTP).append("\r\n")
					// Iniciamos nuevamente los servicios de XAMPP y
					// OpenTripPlanner
					.append("sudo /opt/lampp/lampp start \r\n").append("sudo java -Xmx2G -jar ").append(urlDirectorioOTP).append(nombreJarOTP)
					.append(" --build . --inMemory \r\n");

			File file = new File(ruta);
			// Si el archivo no existe es creado
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contenido.toString());
			bw.close();
			return file;
		} catch (Exception e) {
			throw new Area247Exception(e.getMessage(), e);
		}
	}

}
