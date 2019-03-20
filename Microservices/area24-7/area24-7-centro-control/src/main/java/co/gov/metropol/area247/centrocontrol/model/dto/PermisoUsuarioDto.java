package co.gov.metropol.area247.centrocontrol.model.dto;

public class PermisoUsuarioDto {
	
	private Long id;
    private Long idUsuario;
    private Long idObjeto;
    private boolean puedeAdicionar;
    private boolean puedeEditar;
    private boolean puedeBorrar;
    private boolean puedeImprimir;
    private boolean puedeConsultar;
    private String nombreObjeto;
    
    
	/**
	 * @param id PermisoUsuario
	 * @param idUsuario PermisoUsuario
	 * @param idObjeto PermisoUsuario
	 * @param puedeAdicionar PermisoUsuario
	 * @param puedeEditar PermisoUsuario
	 * @param puedeBorrar PermisoUsuario
	 * @param puedeImprimir PermisoUsuario
	 * @param puedeConsultar PermisoUsuario
	 * @param nombreObjeto Objeto
	 */
	public PermisoUsuarioDto(Long id,Long idUsuario, Long idObjeto, boolean puedeAdicionar, boolean puedeEditar,
			boolean puedeBorrar, boolean puedeImprimir, boolean puedeConsultar, String nombreObjeto) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.idObjeto = idObjeto;
		this.puedeAdicionar = puedeAdicionar;
		this.puedeEditar = puedeEditar;
		this.puedeBorrar = puedeBorrar;
		this.puedeImprimir = puedeImprimir;
		this.puedeConsultar = puedeConsultar;
		this.nombreObjeto = nombreObjeto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdObjeto() {
		return idObjeto;
	}
	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}
	public boolean isPuedeAdicionar() {
		return puedeAdicionar;
	}
	public void setPuedeAdicionar(boolean puedeAdicionar) {
		this.puedeAdicionar = puedeAdicionar;
	}
	public boolean isPuedeEditar() {
		return puedeEditar;
	}
	public void setPuedeEditar(boolean puedeEditar) {
		this.puedeEditar = puedeEditar;
	}
	public boolean isPuedeBorrar() {
		return puedeBorrar;
	}
	public void setPuedeBorrar(boolean puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}
	public boolean isPuedeImprimir() {
		return puedeImprimir;
	}
	public void setPuedeImprimir(boolean puedeImprimir) {
		this.puedeImprimir = puedeImprimir;
	}
	public boolean isPuedeConsultar() {
		return puedeConsultar;
	}
	public void setPuedeConsultar(boolean puedeConsultar) {
		this.puedeConsultar = puedeConsultar;
	}
	public String getNombreObjeto() {
		return nombreObjeto;
	}
	public void setNombreObjeto(String nombreObjeto) {
		this.nombreObjeto = nombreObjeto;
	}
    
}
