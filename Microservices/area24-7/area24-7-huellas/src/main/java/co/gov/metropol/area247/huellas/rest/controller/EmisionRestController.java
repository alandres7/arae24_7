package co.gov.metropol.area247.huellas.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.huellas.model.EmisionDto;
import co.gov.metropol.area247.huellas.model.Recorrido;
import co.gov.metropol.area247.huellas.rest.request.TipoTransporte;
import co.gov.metropol.area247.huellas.rest.response.HuellasResponse;
import co.gov.metropol.area247.huellas.rest.response.msg.HuellasMsgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Huellas")
@RestController
@RequestMapping("/api/huellas/carbono")
public class EmisionRestController {
	
	private static final String NOMBRE_ENTIDAD = "Emision(es)";
	
	private static final double MIL_D = 1000d;
	private static final double CIEN_D = 100d;

	private static final String EMPTY = "";
	
	private HuellasResponse<EmisionDto> emisionResponse;
	
	@ResponseBody
	@ApiOperation(value="/emision", notes="Obtener la emisión en (kg) CO2 y (g) PM2.5 por medio de transporte, "
			+ "Teniendo como medios de transporte ALIMENTADOR, AUTO, BUS, A PIE, METRO, BICI, TRANVIA, METRO_PLUS, METRO_CABLE, ENCICLA.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value= {@ApiResponse(code=404,message="Not provided")})
	@PostMapping(value="/emision")
	public ResponseEntity<HuellasResponse<EmisionDto>> calculoEmision(@RequestBody List<Recorrido>  recorridos){
		
		double kmAuto = recorridos.stream()
				.filter(recorrido -> recorrido.getTipoTransporte()
						.equals(TipoTransporte.AUTO
								.getNombre()))
				.findAny()
				.orElse(new Recorrido())
				.getKilometraje();
		double kmBus = recorridos.stream()
				.filter(recorrido -> recorrido.getTipoTransporte()
						.equals(TipoTransporte.BUS.getNombre()) || 
						recorrido.getTipoTransporte()
						.equals(TipoTransporte.ALIMENTADOR.getNombre()))
				.mapToDouble(recorrido->recorrido.getKilometraje()).sum();
		double kmMetro = recorridos.stream()
				.filter(recorrido -> recorrido.getTipoTransporte()
						.equals(TipoTransporte.METRO.getNombre()) || 
						recorrido.getTipoTransporte()
						.equals(TipoTransporte.METRO_CABLE.getNombre()) ||
						recorrido.getTipoTransporte()
						.equals(TipoTransporte.METRO_PLUS.getNombre()) ||
						recorrido.getTipoTransporte()
						.equals(TipoTransporte.TRANVIA.getNombre()))
				.mapToDouble(recorrido->recorrido.getKilometraje()).sum();
		
		double factorEmisionCO2Bus = TipoTransporte.BUS.getFactorEmisionCO2();
		double factorEmisionCO2Metro = TipoTransporte.METRO.getFactorEmisionCO2();
		double factorEmisionPM2p5Bus = TipoTransporte.BUS.getFactorEmisionPM2_5();
		double factorEmisionCO2Auto = TipoTransporte.AUTO.getFactorEmisionCO2();
		int numPasajerosBus = TipoTransporte.BUS.getCantPasajeros();
		int numPasajerosMetro = TipoTransporte.METRO.getCantPasajeros();
		int numPasajetosAuto = TipoTransporte.AUTO.getCantPasajeros();
		
		
		EmisionDto emision = new EmisionDto();
		double emisionCO2TransPublico = (double)Math.round(((kmBus * factorEmisionCO2Bus/numPasajerosBus + 
						kmMetro * factorEmisionCO2Metro/numPasajerosMetro)/MIL_D)*CIEN_D)/CIEN_D;
		double emisionPM2_5TransPublico = (double)Math.round((kmBus * factorEmisionPM2p5Bus/numPasajerosBus)*CIEN_D)/CIEN_D;
		double emisionCO2Auto = (double)Math.round(((kmAuto*factorEmisionCO2Auto/numPasajetosAuto)/MIL_D)*CIEN_D)/CIEN_D;
		emision.setEmisionCO2(emisionCO2TransPublico);
		emision.setEmisionPM2_5(emisionPM2_5TransPublico);
		emision.setEmisionCO2Autos(emisionCO2Auto);
		emisionResponse = new HuellasResponse<>();
		emisionResponse.configResponse(HuellasMsgs.SUCCESS, NOMBRE_ENTIDAD, EMPTY);
		List<EmisionDto> emisiones = new ArrayList<>();
		emisiones.add(emision);
		emisionResponse.setResponses(emisiones);
		return new ResponseEntity<HuellasResponse<EmisionDto>>(emisionResponse, HttpStatus.OK);
		
	}
	
	
	
	
}
