package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar los diversos mensajes de advertencia o información que se usaran en los aplicativos del Area 24/7 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MensajeItem {
	
	private Long  id;	
    private String codigo;   
    private String mensaje;
    private String uso;
    private String aplicacion;
    

	public MensajeItem(Long id, String codigo, String mensaje, String uso, String aplicacion) {
    	this.id = id;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.uso = uso;
        this.aplicacion = aplicacion;
    }
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public String getUso() {
		return uso;
	}


	public void setUso(String uso) {
		this.uso = uso;
	}


	public String getAplicacion() {
		return aplicacion;
	}


	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
       

    
}