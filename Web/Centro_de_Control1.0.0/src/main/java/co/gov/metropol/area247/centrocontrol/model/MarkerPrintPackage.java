package co.gov.metropol.area247.centrocontrol.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkerPrintPackage {

	private List<MarkerPoint> listaMarcadores;
	private List<MarkerPolygon> listaPoligonos;
	
	
	public List<MarkerPoint> getListaMarcadores() {
		return listaMarcadores;
	}
	
	public void setListaMarcadores(List<MarkerPoint> listaMarcadores) {
		this.listaMarcadores = listaMarcadores;
	}
	
	public List<MarkerPolygon> getListaPoligonos() {
		return listaPoligonos;
	}
	
	public void setListaPoligonos(List<MarkerPolygon> listaPoligonos) {
		this.listaPoligonos = listaPoligonos;
	}

}