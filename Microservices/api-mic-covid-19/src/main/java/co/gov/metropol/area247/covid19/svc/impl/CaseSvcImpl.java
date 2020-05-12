package co.gov.metropol.area247.covid19.svc.impl;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.socrata.exceptions.SodaError;

import co.gov.metropol.area247.covid19.entity.Layer;
import co.gov.metropol.area247.covid19.entity.Marker;
import co.gov.metropol.area247.covid19.model.C19Case;
import co.gov.metropol.area247.covid19.model.FormatUrl;
import co.gov.metropol.area247.covid19.providers.MarkersProvider;
import co.gov.metropol.area247.covid19.svc.ICaseSvc;
import co.gov.metropol.area247.covid19.svc.ILayerSvc;
import co.gov.metropol.area247.covid19.svc.IMarkerSvc;
import co.gov.metropol.area247.covid19.svc.ITownSvc;
import co.gov.metropol.area247.covid19.util.EMunicipio;

@Service
public class CaseSvcImpl implements ICaseSvc {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CaseSvcImpl.class);

	private final static String MEDELLIN = "MEDELLIN";
	private final static String BELLO = "BELLO";
	private final static String ENVIGADO = "ENVIGADO";
	private final static String LA_ESTRELLA = "LA ESTRELLA";
	private final static String COPACABANA = "COPACABANA";
	private final static String SABANETA = "SABANETA";
	private final static String ITAGUI = "ITAGUI";
	private final static String GIRARDOTA = "GIRARDOTA";
	private final static String CALDAS = "CALDAS";
	private final static String BARBOSA = "BARBOSA";
	private final static String CONDITIONS_CAMEL_CASE = "ciudad_de_ubicaci_n IN ('Barbosa','Bello',"
														+ "'Caldas','Copacabana','Envigado','Girardota',"
														+ "'Itagui','La Estrella','Medellin','Sabaneta') "
														+ "AND departamento='Antioquia'";
	private final static String CONDITIONS_ACCENT_CAMEL_CASE = "ciudad_de_ubicaci_n IN ('Barbosa','Bello',"
																+ "'Caldas','Copacabana','Envigado','Girardota',"
																+ "'Itagüí','La Estrella','Medellín','Sabaneta') "
																+ "AND departamento='Antioquia'";
	 
	
	private String numCasos = "";

	@Value("${covid19.layer}")
	private String covid19Layer;
	
	@Value("${url.iconos}")
	private String urlIconos;

	@Autowired
	private ILayerSvc layerSvc;

	@Autowired
	private MarkersProvider c19CasesProvider;

	@Autowired
	private ITownSvc townSvc;
	
	@Autowired
	private IMarkerSvc markerSvc;
	

	@Override
	public boolean updateCasosCovid() throws Exception{
		try {
		layerSvc.borrarMarkersXId(Long.parseLong(covid19Layer));
		Layer layer = layerSvc.getById(Long.parseLong(covid19Layer));
		List<C19Case> c19Cases = getCasosCovid(layer.getUrlRecurso());
		layer.getCategorias().forEach(categoria ->{
		townSvc.getTowns().forEach(town -> {
			long numCases = 0L;
			Marker marcador = new Marker();
			switch (town.getNombre()) {
			case MEDELLIN:
				numCases = 
					c19Cases.stream()
								.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
															.equalsIgnoreCase(EMunicipio.MEDELLIN.getNameDatosAbiertos())
										&& (categoria.getAliasNombre()
														.equalsIgnoreCase(c19Case.getAtenci_n())))
									.count();
				System.out.println(categoria.getNombre()+":MEDELLIN:"+numCases);
				break;
			case BELLO:
				numCases = 
					c19Cases.stream()
								.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
															.equalsIgnoreCase(EMunicipio.BELLO.getNameDatosAbiertos())
										&& (categoria.getAliasNombre()
														.equalsIgnoreCase(c19Case.getAtenci_n())))
									.mapToLong(filter -> 1L)
										.sum();
				System.out.println(categoria.getNombre()+":BELLO:"+numCases);
				break;
			case ENVIGADO:
				numCases = 
					c19Cases.stream()
								.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
															.equalsIgnoreCase(EMunicipio.ENVIGADO.getNameDatosAbiertos())
										&& (categoria.getAliasNombre()
														.equalsIgnoreCase(c19Case.getAtenci_n())))
									.mapToLong(filter -> 1L)
										.sum();
				System.out.println(categoria.getNombre()+":ENVIGADO:"+numCases);
				break;
			case LA_ESTRELLA:
				numCases = 
					c19Cases.stream()
								.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
															.equalsIgnoreCase(EMunicipio.LA_ESTRELLA.getNameDatosAbiertos())
										&& (categoria.getAliasNombre()
														.equalsIgnoreCase(c19Case.getAtenci_n())))
									.count();

				System.out.println(categoria.getNombre()+":LA ESTRELLA:"+numCases);
				break;
			case COPACABANA:
				numCases = 
				c19Cases.stream()
							.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
														.equalsIgnoreCase(EMunicipio.COPACABANA.getNameDatosAbiertos())
									&& (categoria.getAliasNombre()
													.equalsIgnoreCase(c19Case.getAtenci_n())))
								.count();
				System.out.println(categoria.getNombre()+":COPACABANA:"+numCases);
				break;
			case SABANETA:
				numCases = 
				c19Cases.stream()
							.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
														.equalsIgnoreCase(EMunicipio.SABANETA.getNameDatosAbiertos())
									&& (categoria.getAliasNombre()
													.equalsIgnoreCase(c19Case.getAtenci_n())))
								.count();	
				System.out.println(categoria.getNombre()+":SABANETA:"+numCases);
				break;
			case BARBOSA:
				numCases = 
				c19Cases.stream()
							.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
														.equalsIgnoreCase(EMunicipio.BARBOSA.getNameDatosAbiertos())
									&& (categoria.getAliasNombre()
													.equalsIgnoreCase(c19Case.getAtenci_n())))
								.count();
				System.out.println(categoria.getNombre()+":BARBOSA:"+numCases);	
				break;
			case ITAGUI:
				numCases = 
				c19Cases.stream()
							.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
														.equalsIgnoreCase(EMunicipio.ITAGUI.getNameDatosAbiertos())
									&& (categoria.getAliasNombre()
													.equalsIgnoreCase(c19Case.getAtenci_n())))
								.count();	
				System.out.println(categoria.getNombre()+":ITAGUI:"+numCases);
				break;
			case GIRARDOTA:
				numCases = 
				c19Cases.stream()
							.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
														.equalsIgnoreCase(EMunicipio.GIRARDOTA.getNameDatosAbiertos())
									&& (categoria.getAliasNombre()
													.equalsIgnoreCase(c19Case.getAtenci_n())))
								.count();	
				System.out.println(categoria.getNombre()+":GIRARDOTA:"+numCases);
				break;
			case CALDAS:
				numCases = 
				c19Cases.stream()
							.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n()
														.equalsIgnoreCase(EMunicipio.CALDAS.getNameDatosAbiertos())
									&& (categoria.getAliasNombre()
													.equalsIgnoreCase(c19Case.getAtenci_n())))
								.count();	
				System.out.println(categoria.getNombre()+":CALDAS:"+numCases);
				break;
			}
			marcador.setCategoria(categoria);
			marcador.setNombre(categoria.getNombre() + " (" + numCases + ")");
			marcador.setDescripcion("");
			marcador.setDireccion("");
			marcador.setNombreMunicipio(town.getNombre());
			marcador.setPoligono(Boolean.FALSE);
			marcador.setCoordenadaPunto(town.getZeroPoint());
			marcador.setRutaImagen(urlIconos + categoria.getIconoMarcador().getId());
			marcador.setRutaImagen2(urlIconos + categoria.getIconoMarcador().getId());
			marcador.setIcono(categoria.getIconoMarcador());
			marcador.setColorFondo("#a3238e");
			marcador = markerSvc.guardarMarker(marcador);
			LOGGER.info("Se ha creado el marcador con ID: "+marcador.getId()+" Para la categoría "+ categoria.getNombre());
		});
		});
		}catch (Exception e) {
			LOGGER.error("Error actualizando la capa de COVID-19", e);
			return Boolean.FALSE;
		}
		
		

		return Boolean.TRUE;
	}

	@Override
	public List<C19Case> getCasosCovid(String urlRecurso) {
		try {
			return getCovid19Case(urlRecurso);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (SodaError e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	@Override
	public String getNumCasos() {
		try {
			layerSvc.borrarMarkersXId(Long.parseLong(covid19Layer));
			Layer layer = layerSvc.getById(Long.parseLong(covid19Layer));
			List<C19Case> c19Cases = getCasosCovid(layer.getUrlRecurso());
			layer.getCategorias().forEach(categoria ->{
			townSvc.getTowns().forEach(town -> {
				long numCases = 0L;
				switch (town.getNombre()) {
				case MEDELLIN:
					numCases = 
						c19Cases.stream()
									.filter(c19Case -> c19Case.getCiudad_de_ubicaci_n().equals(EMunicipio.MEDELLIN.getNameDatosAbiertos())
														&& (categoria.getAliasNombre().equals(c19Case.getAtenci_n())))
										.count();
					PrintStream p = Objects.requireNonNull(System.out);
//					c19Cases.stream().forEach(c19Case -> p.println( c19Case ) );
					c19Cases.stream()
								.filter(c19Case -> (c19Case.getCiudad_de_ubicaci_n()
															.equals(EMunicipio.MEDELLIN.getNameDatosAbiertos())
																	&& (categoria.getAliasNombre().equals(c19Case.getAtenci_n()))))
									.collect(Collectors.toList())
										.stream()
											.forEach(c19Case -> p.println( c19Case ) );
					System.out.println("Ciudad ENUM: "+EMunicipio.MEDELLIN.getNameDatosAbiertos());
					c19Cases.forEach(c19Case -> {
						if(c19Case.getCiudad_de_ubicaci_n().equals("Medellín")){
							System.out.println("Ciudad Covid19: "+ c19Case.getCiudad_de_ubicaci_n());
							System.out.println("Categoría Alias Nombre: "+categoria.getAliasNombre());
							System.out.println("Atención: "+c19Case.getAtenci_n());
						}
						if(c19Case.getCiudad_de_ubicaci_n().equals(EMunicipio.MEDELLIN.getNameDatosAbiertos())
														&& (categoria.getAliasNombre().equals(c19Case.getAtenci_n()))) {
							System.out.println(c19Case);
						}
					});
//					System.out.println(categoria.getNombre()+":MEDELLIN:"+numCases);
					numCasos += categoria.getNombre()+":MEDELLIN:"+numCases+";";
					break;
					
				}
			});
			});
			return numCasos;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
		
	}

	private List<C19Case> getCovid19Case(String url)
			throws UnsupportedEncodingException, SodaError, InterruptedException {
		List<C19Case> c19Cases = new ArrayList<>();
		FormatUrl urlFormateada = new FormatUrl(extractSchemaAuthority(url), extractResourceId(url));
		c19Cases = c19CasesProvider.getPayloadCasosCovid(urlFormateada.getSchemaAuthority(),
															urlFormateada.getResourceId());
		if(c19Cases.isEmpty()) {
			c19Cases = c19CasesProvider.getPayloadCasosCovid(urlFormateada.getSchemaAuthority(),
																urlFormateada.getResourceId(), 
																CONDITIONS_ACCENT_CAMEL_CASE);
		}
		if(c19Cases.isEmpty()) {
			c19Cases = c19CasesProvider.getPayloadCasosCovid(urlFormateada.getSchemaAuthority(),
																urlFormateada.getResourceId(), 
																CONDITIONS_CAMEL_CASE);
		}

		return c19Cases;
	}

	private String extractSchemaAuthority(String url) {
		String parts2[] = url.split("/");
		StringBuilder schemaAuthority = new StringBuilder(parts2[0]);
		schemaAuthority.append("//").append(parts2[2]).append("/");
		return schemaAuthority.toString();
	}

	private String extractResourceId(String url) {
		String parts[] = url.split("\\.");
		String parts2[] = parts[3].split("/");
		return parts2[2];
	}

}
