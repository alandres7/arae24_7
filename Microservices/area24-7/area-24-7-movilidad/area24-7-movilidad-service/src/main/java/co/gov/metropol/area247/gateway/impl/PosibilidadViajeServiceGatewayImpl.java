package co.gov.metropol.area247.gateway.impl;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.gov.metropol.area247.gateway.IPosibilidadViajeServiceGateway;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.services.rest.opt.PosibilidadViajeWSDTO;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.ServiceUtils;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.constantes.Constantes.TipoParametro;

/**
 * Esta clase clase obtiene información de la API expuesta por OTP
 * (OpenTripPlanner).
 * 
 * @version 1.0
 *
 */
@Service
public class PosibilidadViajeServiceGatewayImpl implements IPosibilidadViajeServiceGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(PosibilidadViajeServiceGatewayImpl.class);

	private static final String PATRON_FECHA = "MM-dd-yyyy";
	private static final String PATRON_HORA = "hh:mmaa";
	
	@Autowired
	private ITipoParametroService tipoParametroService;

	@Override
	public PosibilidadViajeWSDTO consultarPosiblesViajes(Date fecha, Double latitudOrigen, Double longitudOrigen,
			Double latitudDestino, Double longitudDestino, String modosTransporte) {

		String urlBase = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routeOTPService");
		String urlParametros = "?fromPlace={origen}&toPlace={destino}&time={hora}&date={fecha}&mode={modoTransporte}&maxWalkDistance={distanciaMaxCaminando}&arriveBy=false&wheelchair=false&locale=es";
		String url = urlBase + urlParametros;

		Map<String, String> variables = new HashMap<>(5);
		variables.put("origen", latitudOrigen.toString() + Constantes.COMA + longitudOrigen.toString());
		variables.put("destino", latitudDestino.toString() + Constantes.COMA + longitudDestino.toString());
		variables.put("hora", formatearFecha(fecha, PATRON_HORA));
		variables.put("fecha", formatearFecha(fecha, PATRON_FECHA));
		variables.put("modoTransporte", modosTransporte);

		PosibilidadViajeWSDTO wsdto = null;
		
		
		try {

			String distanciaMaxCaminando = tipoParametroService.obtenerValorCadena(TipoParametro.DISTANCIA_MAX_CAMINANDO);
			variables.put("distanciaMaxCaminando", distanciaMaxCaminando);
			
			RestTemplate accesoHttp = new RestTemplate();
			accesoHttp.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			String respuesta = accesoHttp.getForObject(url, String.class, variables);
			wsdto = (PosibilidadViajeWSDTO) ServiceUtils.stringResponseToObject(respuesta, PosibilidadViajeWSDTO.class);

			if (!Utils.isNull(wsdto)) {
				if (Utils.isNull(wsdto.getError())) {
					wsdto.setCodigo(HttpStatus.OK.value());
					wsdto.setMensaje(PropertiesManager.obtenerCadena(Constantes.Recursos.MENSAJES,
							 "posible.viaje.consulta.otp.exito"));
				} else {
					wsdto.setCodigo(HttpStatus.NOT_FOUND.value());
					wsdto.setMensaje(PropertiesManager.obtenerCadena(Constantes.Recursos.MENSAJES,
						 "posible.viaje.consulta.otp.no.encontro.viajes"));
				}
			}
			
		} catch (Exception e) {
			wsdto = new PosibilidadViajeWSDTO();
			wsdto.setCodigo(HttpStatus.CONFLICT.value());
			wsdto.setMensaje(PropertiesManager.obtenerCadena(Constantes.Recursos.MENSAJES,
					 "posible.viaje.consulta.otp.servidor.error.interno"));
			LOGGER.error("Error en el servidor OTP : ", e.getMessage());
		}
		
		return wsdto;

	}

	private static String formatearFecha(Date fecha, String patron) {
		SimpleDateFormat formato = new SimpleDateFormat(patron);
		return formato.format(fecha);
	}

}
