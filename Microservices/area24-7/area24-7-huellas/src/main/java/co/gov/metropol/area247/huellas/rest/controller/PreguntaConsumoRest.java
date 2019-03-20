package co.gov.metropol.area247.huellas.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import co.gov.metropol.area247.huellas.model.PreguntaConsumoDto;
import co.gov.metropol.area247.huellas.rest.response.HuellasResponse;
import co.gov.metropol.area247.huellas.rest.response.msg.HuellasMsgs;
import co.gov.metropol.area247.huellas.rest.response.msg.Transaccion;
import co.gov.metropol.area247.huellas.svc.IHuellasPreguntaSvc;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@Api("Pregunta")
//@RestController
//@RequestMapping("/api")
public class PreguntaConsumoRest {
	
	private HuellasResponse<PreguntaConsumoDto> tipoConsumoResponse;
	private ResponseEntity<HuellasResponse<PreguntaConsumoDto>> response;
	
	@Autowired
	@Qualifier("preguntaConsumoSvc")
	IHuellasPreguntaSvc preguntaConsumoSvc;
	
	@ResponseBody
	@ApiOperation(value="/huellas/tipos-consumo/{idTipoHuella}", notes="Obtener las preguntas asociadas a un tipo de huella específico")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", 
					dataType = "string", paramType = "header")
			})
	@ApiResponses(value= {
			@ApiResponse(code=302,message="Se han encontrado las preguntas asociadas al tipo de huella" ),
			@ApiResponse(code=404,message="No se ha encontrado ninguna pregunta asociada al tipo de huella específico" )
	})
	@GetMapping("/huellas/preguntas-consumo/{idTipoHuella}")
	public ResponseEntity<HuellasResponse<PreguntaConsumoDto>> getTiposConsumo(@PathVariable("idTipoHuella") Long idTipoHuella){
		try {
			List<PreguntaConsumoDto> preguntas = preguntaConsumoSvc.getPreguntasConsumo(idTipoHuella);
			if(preguntas.isEmpty()) {
				configResponse(HuellasMsgs.EMPTY_LIST);
				response = new ResponseEntity<>(tipoConsumoResponse, HttpStatus.FOUND);
			}else {
				configResponse(HuellasMsgs.SUCCESS_LIST);
				tipoConsumoResponse.setResponses(preguntas);
				response = new ResponseEntity<>(tipoConsumoResponse, HttpStatus.FOUND);
			}
		}catch(Exception e) {
			configResponse(HuellasMsgs.FAILURE_LIST, e);
			response = new ResponseEntity<>(tipoConsumoResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ResponseBody
	@ApiOperation(value="/huellas/pregunta-consumo", notes="Crear una nueva pregunta")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", 
					dataType = "string", paramType = "header")
			})
	@ApiResponses(value= {
			@ApiResponse(code=201,message="Se ha creado la pregunta respectiva" ),
			@ApiResponse(code=500,message="No se ha podido crear la pregunta respectiva" )
	})
	@PostMapping("/huellas/pregunta-consumo")
	public ResponseEntity<HuellasResponse<PreguntaConsumoDto>> addPregunta(@RequestBody PreguntaConsumoDto pregunta) {
		try {
			    preguntaConsumoSvc.crearPreguntaConsumo(pregunta);
				configResponse(HuellasMsgs.SUCCESS_CREATE);
				response = new ResponseEntity<>(tipoConsumoResponse, HttpStatus.CREATED);
			
		} catch (Exception e) {
			configResponse(HuellasMsgs.FAILURE_CREATE, e);
			response = new ResponseEntity<>(tipoConsumoResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public void configResponse(HuellasMsgs msgs, Exception...exs) {
		tipoConsumoResponse = new HuellasResponse<>();
		Transaccion transaccion = new Transaccion();
		transaccion.setCode(msgs.getCode());
		transaccion.setStatus(msgs.getStatus());
		if ("ERROR".equals(msgs.getStatus())) {
			Exception e = exs[0];
			StringBuilder failMsg = new StringBuilder(msgs.getDescripcion())
					.append("%%ERROR:").append(e.getMessage());
			Optional<Throwable> causaPpalOpt = Stream.iterate(e, Throwable::getCause)
					.filter(element -> element.getCause() == null)
					.findFirst();
			Throwable causaPpal = causaPpalOpt.get();
			failMsg.append("%%CAUSA:").append(causaPpal.toString());
			transaccion.setDescripcion(failMsg.toString());
		} else {
			transaccion.setDescripcion(msgs.getDescripcion());
		}
		tipoConsumoResponse.setTransaccion(transaccion);
	}
			
}
