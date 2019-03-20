package co.gov.metropol.area247.services.rest.encicla;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.annotations.Loggable;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class CicloRutaWSDTO {
	@JsonProperty(value = "features")
	@Loggable
	private FeatureWSDTO features;
	
	public CicloRutaWSDTO() {
		super();
	}
	
	public CicloRutaWSDTO(@JsonProperty(value = "features") FeatureWSDTO features){
		this.features = features;
	}

	public FeatureWSDTO getFeatures() {
		return features;
	}

	public void setFeatures(FeatureWSDTO features) {
		this.features = features;
	}
	
	
}
