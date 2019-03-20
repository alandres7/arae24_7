package co.gov.metropol.area247.core.gateway.posconsumo.dto;

import java.io.Serializable;

/**
 * @author ageo.fuentes@ada.co
 */
public class PosconsumoPoint implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3132967040118351858L;
    private String categoria_residuo;
    private String ciudad;
    private String correo_electr_nico;
    private String departamento;
    private String direcci_n_punto_de_recolecci_n;
    private String horario;
    private Point latitud;
    private String nombre_programa_posconsumo;
    private String nombre_punto_de_recolecci_n;
    private String nombre_residuo;
    private String pa_s;
    private String persona_contacto;
    private String tipo_residuo;

    public String getCategoria_residuo() {
        return categoria_residuo;
    }

    public void setCategoria_residuo(String categoria_residuo) {
        this.categoria_residuo = categoria_residuo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreo_electr_nico() {
        return correo_electr_nico;
    }

    public void setCorreo_electr_nico(String correo_electr_nico) {
        this.correo_electr_nico = correo_electr_nico;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDirecci_n_punto_de_recolecci_n() {
        return direcci_n_punto_de_recolecci_n;
    }

    public void setDirecci_n_punto_de_recolecci_n(String direcci_n_punto_de_recolecci_n) {
        this.direcci_n_punto_de_recolecci_n = direcci_n_punto_de_recolecci_n;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Point getLatitud() {
        return latitud;
    }

    public void setLatitud(Point latitud) {
        this.latitud = latitud;
    }

    public String getNombre_programa_posconsumo() {
        return nombre_programa_posconsumo;
    }

    public void setNombre_programa_posconsumo(String nombre_programa_posconsumo) {
        this.nombre_programa_posconsumo = nombre_programa_posconsumo;
    }

    public String getNombre_punto_de_recolecci_n() {
        return nombre_punto_de_recolecci_n;
    }

    public void setNombre_punto_de_recolecci_n(String nombre_punto_de_recolecci_n) {
        this.nombre_punto_de_recolecci_n = nombre_punto_de_recolecci_n;
    }

    public String getNombre_residuo() {
        return nombre_residuo;
    }

    public void setNombre_residuo(String nombre_residuo) {
        this.nombre_residuo = nombre_residuo;
    }

    public String getPa_s() {
        return pa_s;
    }

    public void setPa_s(String pa_s) {
        this.pa_s = pa_s;
    }

    public String getPersona_contacto() {
        return persona_contacto;
    }

    public void setPersona_contacto(String persona_contacto) {
        this.persona_contacto = persona_contacto;
    }

    public String getTipo_residuo() {
        return tipo_residuo;
    }

    public void setTipo_residuo(String tipo_residuo) {
        this.tipo_residuo = tipo_residuo;
    }

    public PosconsumoPoint(String categoria_residuo, String ciudad, String correo_electr_nico, String departamento, String direcci_n_punto_de_recolecci_n, String horario, Point latitud, String nombre_programa_posconsumo, String nombre_punto_de_recolecci_n, String nombre_residuo, String pa_s, String persona_contacto, String tipo_residuo) {
        this.categoria_residuo = categoria_residuo;
        this.ciudad = ciudad;
        this.correo_electr_nico = correo_electr_nico;
        this.departamento = departamento;
        this.direcci_n_punto_de_recolecci_n = direcci_n_punto_de_recolecci_n;
        this.horario = horario;
        this.latitud = latitud;
        this.nombre_programa_posconsumo = nombre_programa_posconsumo;
        this.nombre_punto_de_recolecci_n = nombre_punto_de_recolecci_n;
        this.nombre_residuo = nombre_residuo;
        this.pa_s = pa_s;
        this.persona_contacto = persona_contacto;
        this.tipo_residuo = tipo_residuo;
    }

    public PosconsumoPoint() {
    }

}
