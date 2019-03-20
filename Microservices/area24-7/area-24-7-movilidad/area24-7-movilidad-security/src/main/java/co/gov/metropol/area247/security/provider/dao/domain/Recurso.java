package co.gov.metropol.area247.security.provider.dao.domain;

public class Recurso {
	
	private String codigo;
	
	private String url;

	public String getCodigo() {
		return codigo;
	}

	public Recurso setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Recurso setUrl(String url) {
		this.url = url;
		return this;
	}
	
}
