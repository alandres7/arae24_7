package co.gov.metropol.area247.huellas.rest.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.huellas.rest.response.msg.HuellasMsgs;
import co.gov.metropol.area247.huellas.rest.response.msg.Transaccion;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class HuellasResponse<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5427074165182853757L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Transacción")
	private Transaccion transaccion;
	
	@JsonProperty
	@ApiModelProperty(value = "Listado de objetos")
	private List<T> responses;
	
	public List<T> getResponses() {
		return responses;
	}
	public void setResponses(List<T> responses) {
		this.responses = responses;
	}
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}
	
	public void setResponse(T response) {
		responses.add(response);
	}
	
	public HuellasResponse() {
		transaccion = new Transaccion();
		responses = new ArrayList<>();
	}
	
	public void configResponse(HuellasMsgs msgs, String complemento, String personalMsg, Exception...exs) {
		transaccion.setCode(msgs.getCode());
		transaccion.setStatus(msgs.getStatus());
		if ("ERROR".equals(msgs.getStatus())) {
			Exception e = exs[0];
			StringBuilder failMsg = new StringBuilder(String.format(msgs.getDescripcion(),complemento))
					.append("%%ERROR:").append(e.getMessage());
			Optional<Throwable> causaPpalOpt = Stream.iterate(e, Throwable::getCause)
					.filter(element -> element.getCause() == null)
					.findFirst();
			Throwable causaPpal = causaPpalOpt.isPresent()?causaPpalOpt.get():new Throwable("No Determinado");
			failMsg.append("%%CAUSA:").append(causaPpal.toString());
			transaccion.setDescripcion(failMsg.toString());
		} else {
			if(personalMsg != null && !"".equals(personalMsg.trim())) {
				transaccion.setDescripcion(personalMsg);
			}
			else{
				transaccion.setDescripcion(String.format(msgs.getDescripcion(),complemento));
			}
		}
		setTransaccion(transaccion);
	}
	
}
