package co.gov.metropol.area247.avistamiento.model.enums;

public enum Colores {
	
	PENDIENTE("#9D9D9C"), 
	APROBADO("#00751C"), 
	RECHAZADO("#E6332A");
	
	private final String color;
	
	Colores(String color){
		this.color = color;
	}

	public String getColor() {
		return color;
	}
	
	

}
