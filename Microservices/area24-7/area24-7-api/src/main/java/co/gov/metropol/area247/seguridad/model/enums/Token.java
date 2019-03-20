package co.gov.metropol.area247.seguridad.model.enums;

public enum Token {

	TIEMPO_EXPIRACION("86400000000"), //10 días en milisegundos .. 1000 dias por ahora
	SECRETO("AMVA-AREA24-7"),
	PREFIJO_TOKEN("Bearer"),
	HEADER("Authorization");
	
	private final String valor;

	Token(String valor)
	{
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
}
