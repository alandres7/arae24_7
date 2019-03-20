package co.gov.metropol.area247.centrocontrol.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class CirculacionVehiculoWSDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "circulacion")
	private ArrayList<CirculacionDTO> lstCircualcionVechiculo;
	
	public CirculacionVehiculoWSDTO(@JsonProperty(value = "circulacion") ArrayList<CirculacionDTO> circulacion) {
		this.lstCircualcionVechiculo = circulacion;
	}

	public ArrayList<CirculacionDTO> getLstCircualcionVechiculo() {
		return lstCircualcionVechiculo;
	}

	public void setLstCircualcionVechiculo(ArrayList<CirculacionDTO> lstCircualcionVechiculo) {
		this.lstCircualcionVechiculo = lstCircualcionVechiculo;
	}

	
}
