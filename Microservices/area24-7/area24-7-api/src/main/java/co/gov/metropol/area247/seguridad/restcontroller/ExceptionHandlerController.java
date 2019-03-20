package co.gov.metropol.area247.seguridad.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	//Registro:Si el género enviado es superior a un dígito, si no se envía la fuenteRegistro, si se envía un json ilegible
	//Login: Si no se envía el username
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("error: \n");
		ex.printStackTrace();
		return new ResponseEntity<Object>("Bad Request. La solicitud contiene errores, revisar y reintentar", HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(MultipartException.class)
	    public ResponseEntity<Object> handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
		 return new ResponseEntity<Object>("Bad Request. La solicitud contiene errores, revisar y reintentar", HttpStatus.BAD_REQUEST);
	 }

	
}
