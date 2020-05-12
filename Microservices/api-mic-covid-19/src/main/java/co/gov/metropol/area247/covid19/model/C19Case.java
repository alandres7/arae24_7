package co.gov.metropol.area247.covid19.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

@JsonIgnoreProperties(ignoreUnknown = true)
public class C19Case {

	private String id_de_caso;
	@JsonIgnore
	private String fecha_de_diagn_stico;
	private String ciudad_de_ubicaci_n;
	private String departamento;
	private String atenci_n;
	@JsonIgnore
	private String edad;
	@JsonIgnore
	private String sexo;
	private String tipo;
	private String pa_s_de_procedencia;
	@JsonIgnore
	private String fecha_recuperado;
	@JsonIgnore
	private String fecha_de_muerte;
	@JsonIgnore
	private String fis;
	
	
	public C19Case() {}

	public C19Case(String id_de_caso, String fecha_de_diagn_stico, String ciudad_de_ubicaci_n, String departamento,
			String atenci_n, String edad, String sexo, String tipo, String pa_s_de_procedencia,
			String fecha_recuperado, String fecha_de_muerte) {
		this.id_de_caso = id_de_caso;
		this.fecha_de_diagn_stico = fecha_de_diagn_stico;
		this.ciudad_de_ubicaci_n = ciudad_de_ubicaci_n;
		this.departamento = departamento;
		this.atenci_n = atenci_n;
		this.edad = edad;
		this.sexo = sexo;
		this.tipo = tipo;
		this.pa_s_de_procedencia = pa_s_de_procedencia;
		this.fecha_recuperado = fecha_recuperado;
		this.fecha_de_muerte = fecha_de_muerte;
	}

	public String getId_de_caso() {
		return id_de_caso;
	}

	public void setId_de_caso(String id_de_caso) {
		this.id_de_caso = id_de_caso;
	}

	public String getFecha_de_diagn_stico() {
		return fecha_de_diagn_stico;
	}

	public void setFecha_de_diagn_stico(String fecha_de_diagn_stico) {
		this.fecha_de_diagn_stico = fecha_de_diagn_stico;
	}

	public String getCiudad_de_ubicaci_n() {
		return ciudad_de_ubicaci_n;
	}

	public void setCiudad_de_ubicaci_n(String ciudad_de_ubicaci_n) {
		this.ciudad_de_ubicaci_n = ciudad_de_ubicaci_n;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getAtenci_n() {
		return atenci_n;
	}

	public void setAtenci_n(String atenci_n) {
		this.atenci_n = atenci_n;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPa_s_de_procedencia() {
		return pa_s_de_procedencia;
	}

	public void setPa_s_de_procedencia(String pa_s_de_procedencia) {
		this.pa_s_de_procedencia = pa_s_de_procedencia;
	}

	public String getFecha_recuperado() {
		return fecha_recuperado;
	}

	public void setFecha_recuperado(String fecha_recuperado) {
		this.fecha_recuperado = fecha_recuperado;
	}

	public String getFecha_de_muerte() {
		return fecha_de_muerte;
	}

	public void setFecha_de_muerte(String fecha_de_muerte) {
		this.fecha_de_muerte = fecha_de_muerte;
	}

	public String getFis() {
		return fis;
	}

	public void setFis(String fis) {
		this.fis = fis;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
