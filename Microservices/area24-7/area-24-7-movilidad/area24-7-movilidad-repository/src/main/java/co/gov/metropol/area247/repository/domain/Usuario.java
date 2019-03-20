package co.gov.metropol.area247.repository.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "TSIMAUD_USUARIOS", schema = "MOVILIDAD")
public class Usuario extends Entities {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "S_NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "S_NOMBRE_DE_USUARIO", nullable = false, unique = true, length = 80)
    private String username;

    @Column(name = "S_CONTRASENA", nullable = false, length = 80)
    private String password;

    @Column(name = "S_CORREO", nullable = false, length = 180)
    private String correo;

    @Column(name = "B_CUENTA_NO_EXPRIRADA", nullable = false)
    private boolean cuentaNoExpirada;

    @Column(name = "B_CUENTA_NO_BLOQUEADA", nullable = false)
    private boolean cuentaNoBloqueada;

    @Column(name = "B_CREDENCIALES_NO_EXPIRADAS", nullable = false)
    private boolean credencialesNoExpiradas;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "D_ULTIMO_INGERSO")
    private Date ultimoIngreso;

    @ManyToMany
    @JoinTable(name = "TSIMAUD_ROLES_POR_USUARIOS")
    private Set<Autorizacion> autorizaciones = new HashSet<>();

    @Override
    public Usuario withId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public Usuario withEnabled(boolean enabled) {
        super.setEnabled(enabled);
        return this;
    }

    public String getNombre() {
		return nombre;
	}

	public Usuario setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public Usuario setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Usuario setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getCorreo() {
		return correo;
	}

	public Usuario setCorreo(String correo) {
		this.correo = correo;
		return this;
	}

	public boolean isCuentaNoExpirada() {
		return cuentaNoExpirada;
	}

	public Usuario setCuentaNoExpirada(boolean cuentaNoExpirada) {
		this.cuentaNoExpirada = cuentaNoExpirada;
		return this;
	}

	public boolean isCuentaNoBloqueada() {
		return cuentaNoBloqueada;
	}

	public Usuario setCuentaNoBloqueada(boolean cuentaNoBloqueada) {
		this.cuentaNoBloqueada = cuentaNoBloqueada;
		return this;
	}

	public boolean isCredencialesNoExpiradas() {
		return credencialesNoExpiradas;
	}

	public Usuario setCredencialesNoExpiradas(boolean credencialesNoExpiradas) {
		this.credencialesNoExpiradas = credencialesNoExpiradas;
		return this;
	}

	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}

	public Usuario setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
		return this;
	}

	public Set<Autorizacion> getAutorizaciones() {
		return autorizaciones;
	}

	public Usuario setAutorizaciones(Set<Autorizacion> autorizaciones) {
		this.autorizaciones = autorizaciones;
		return this;
	}
}
