package co.gov.metropol.area247.services.rest.encicla;

import java.util.List;

public class GeometryWSDTO {
		
	private List<List<float[]>> paths;
	
	public GeometryWSDTO(List<List<float[]>> paths) {
		this.paths = paths;
	}

	public List<List<float[]>> getPaths() {
		return paths;
	}

	public void setPaths(List<List<float[]>> paths) {
		this.paths = paths;
	}

	
	
	
}
