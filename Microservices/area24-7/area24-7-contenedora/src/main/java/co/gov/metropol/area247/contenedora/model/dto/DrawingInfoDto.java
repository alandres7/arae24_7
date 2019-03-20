package co.gov.metropol.area247.contenedora.model.dto;

import com.google.gson.JsonObject;

public class DrawingInfoDto {
	
	public DrawingInfoDto(){
		
	}
	
	private JsonObject renderer;

	public JsonObject getRenderer() {
		return renderer;
	}

	public void setRenderer(JsonObject renderer) {
		this.renderer = renderer;
	}
	
}
