package co.gov.metropol.area247.contenedora.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.contenedora.gateway.DatosBaseGateway;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Coordenada;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.model.Subcategoria;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDto;
import co.gov.metropol.area247.contenedora.model.dto.DrawingInfoDto;
import co.gov.metropol.area247.contenedora.model.enums.TiposRecurso;
import co.gov.metropol.area247.contenedora.service.IContenedoraAplicacionService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCoordenadaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.service.IContenedoraSubcategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;
import co.gov.metropol.area247.core.gateway.geo.dto.Feature;
import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;
import co.gov.metropol.area247.core.util.GeometryUtil;

@Component
public class Actualizador {
	
	private JSONObject objetoServicio;

	private Categoria categoria;

	@Autowired
	private DatosBaseGateway gateway;

	@Autowired
	private IContenedoraCategoriaService categoriaService;

	@Autowired
	private IContenedoraSubcategoriaService subcategoriaService;

	@Autowired
	private IContenedoraCoordenadaService coordenadaService;

	@Autowired
	private IContenedoraVentanaInformacionService ventanaInformacionService;

	@Autowired
	private IContenedoraMarcadorService marcadorService;

	@Autowired
	private IContenedoraAplicacionService aplicacionService;

	// Icono[] iconos = new Icono[2];

	public List<Marcador> actualizarMarcadores(Long tipoRecurso, Long idRecurso, Subcategoria subCategoria,
			JsonArray pJsonArray, boolean poligono) {

		List<Feature> features = gateway.obtenerInformacionCapa(tipoRecurso, idRecurso, null, subCategoria);

		List<Marcador> marcadores = new ArrayList<Marcador>();
		int contador = 0;

		for (Feature iteradorFeature : features) {

			JsonObject pJsonObject2 = null;
			// if(p..get("value").getAsString()){}

			String jsonObjectNombre = iteradorFeature.getAttributes().get(subCategoria.getAliasNombre()).getAsString();

			for (Iterator<JsonElement> iterator = pJsonArray.iterator(); iterator.hasNext();) {
				JsonObject p = (JsonObject) iterator.next();
				if (p.getAsJsonObject().get("value").getAsString().equals(jsonObjectNombre)) {
					pJsonObject2 = p.getAsJsonObject();
					break;
				}
			}

			Marcador marcador = marcadorService.obtenerMarcadorPorSubCategoriaCodigo(subCategoria.getId(),
					iteradorFeature.getAttributes().get(subCategoria.getAliasId()).getAsInt());
			if (marcador != null) {
//				for (Coordenada iteradorCoordenada : marcador.getCoordenadas()) {
//					if (coordenadaService.coordenadaEliminar(iteradorCoordenada.getId())) {
//						System.out.println("coordenada eliminada");
//					}
//				}
			} else {
				marcador = new Marcador();
				System.out.println("No existe");
			}
			List<Coordenada> coordenadas = new ArrayList<Coordenada>();
			int numeroCoordenadas;

			try {
				numeroCoordenadas = iteradorFeature.getGeometry().getRings().get(0).size();
			} catch (Exception e) {
				numeroCoordenadas = 0;
			}

			if (numeroCoordenadas > 1 & poligono) {
				marcador.setPoligono(true);

			} else {
				marcador.setPoligono(false);
				try {
					if (iteradorFeature.getGeometry().getX() != 0 && iteradorFeature.getGeometry().getY() != 0) {

						Coordenada coordenada = new Coordenada();
						coordenada.setMarcador(marcador);
						coordenada.setCoordenadaPunto(GeometryUtil.obtenerPunto(iteradorFeature.getGeometry().getX(),
								iteradorFeature.getGeometry().getY()));
						if (coordenadaService.coordeanadaCrear(coordenada)) {
							coordenadas.add(coordenada);
						}

					}

				} catch (Exception e) {

					for (int i = 0; i < iteradorFeature.getGeometry().getRings().size(); i++) {
						List<CoordenadaDto> coordenadasDto = new ArrayList<CoordenadaDto>();
						Coordenada coordenada = new Coordenada();

						for (int j = 0; j < iteradorFeature.getGeometry().getRings().get(i).size(); j++) {
							CoordenadaDto coordenadaDto = new CoordenadaDto();
							coordenadaDto.setLatitud(iteradorFeature.getGeometry().getRings().get(i).get(j)[0]);
							coordenadaDto.setLongitud(iteradorFeature.getGeometry().getRings().get(i).get(j)[1]);
							coordenadasDto.add(coordenadaDto);
						}

						if (coordenadasDto.size() > 1) {
							Polygon polygon = GeometryUtil.obtenerPuntosPolygon(coordenadasDto);
							// coordenada.setCoordenadaPolygon(GeometryUtil.obtenerPuntosPolygon(coordenadasDto));
							coordenada.setCoordenadaPunto(polygon.getCentroid());
							coordenada.setMarcador(marcador);
							if (coordenadaService.coordeanadaCrear(coordenada)) {
								coordenadas.add(coordenada);
							}

						}
					}

				}

			}
			if (coordenadas.size() > 0) {
				AplicacionDto aplicacionDto = new AplicacionDto();

//				marcador.setCoordenadas(coordenadas);
				marcador.setCodigo(iteradorFeature.getAttributes().get(subCategoria.getAliasId()).getAsInt());
				marcador.setNombre(
						iteradorFeature.getAttributes().get(subCategoria.getAliasNombreItem()).getAsString());
				marcador.setDescripcion(
						iteradorFeature.getAttributes().get(subCategoria.getAliasDescripcionItem()).getAsString());
				// marcador.setTipoRecurso(TiposRecurso.SUBCATEGORIA);
				JsonObject jsonObjectSimbol = pJsonObject2.get("symbol").getAsJsonObject();
				marcador.setColorFondo(jsonObjectSimbol.get("color").getAsJsonArray().toString());
				marcador.setColorBorde(
						jsonObjectSimbol.get("outline").getAsJsonObject().get("color").getAsJsonArray().toString());
				VentanaInformacion ventanaInformacion = new VentanaInformacion();
				ventanaInformacion.setNombre(
						iteradorFeature.getAttributes().get(subCategoria.getAliasNombreItem()).getAsString());
				ventanaInformacion.setIcono(subCategoria.getIcono());
				ventanaInformacion.setColor(aplicacionDto.getCodigoColor());
				if (ventanaInformacionService.ventanaInformacionCrear(ventanaInformacion) != null) {
					marcador.setIcono(subCategoria.getIconoMarcador());
					marcador.setVentanaInformacion(ventanaInformacion);
					if (marcadorService.marcadorCrear(marcador)) {
						marcadores.add(marcador);
					} else {
						ventanaInformacionService.ventanaInformacionEliminar(ventanaInformacion.getId());
//						for (Coordenada iteradorCoordenada : marcador.getCoordenadas()) {
//							coordenadaService.coordenadaEliminar(iteradorCoordenada.getId());
//						}
						// return null;
					}
				} else {
					// return null;
				}
				contador += 1;
				System.out.println(" registros procesados: " + contador + " de " + features.size());

			}
		}
		return marcadores;
	}

	public List<Marcador> actualizarMarcadores(Long tipoRecurso, Long idRecurso, Categoria categoria,
			JsonArray pJsonArray, boolean poligono) {

		List<Feature> features = gateway.obtenerInformacionCapa(tipoRecurso, idRecurso, categoria, null);

		List<Marcador> marcadores = new ArrayList<Marcador>();

		for (Feature iteradorFeature : features) {
			String jsonObjectNombre = iteradorFeature.getAttributes().get(categoria.getAliasNombre()).getAsString();
			JsonObject pJsonObject2 = null;
			for (Iterator<JsonElement> iterator = pJsonArray.iterator(); iterator.hasNext();) {
				JsonObject p = (JsonObject) iterator.next();
				try {
					if (p.getAsJsonObject().get("value").getAsString().contentEquals(categoria.getNombre())) {
						pJsonObject2 = p.getAsJsonObject();
						break;
					}
				} catch (Exception e) {
					try {
						pJsonObject2 = p.getAsJsonObject();
					} catch (Exception e2) {

					}

					break;
				}

			}

			if (pJsonObject2 != null) {
				Marcador marcador = marcadorService.obtenerMarcadorPorNombre(
						iteradorFeature.getAttributes().get(categoria.getAliasNombre()).getAsString());
				// si no envuentra el marcador por el nombre intenta buscarlo por el
				// identificador del objeto
				if (marcador == null) {
					marcador = marcadorService.obtenerMarcadorPorCategoriaCodigo(categoria.getId(),
							iteradorFeature.getAttributes().get(categoria.getAliasTipo()).getAsInt());
				}

				if (marcador != null) {
//					for (Coordenada iteradorCoordenada : marcador.getCoordenadas()) {
//						if (coordenadaService.coordenadaEliminar(iteradorCoordenada.getId())) {
//							System.out.println("coordenada eliminada");
//						}
//					}
				} else {
					marcador = new Marcador();
					System.out.println("No existe");
					// marcador.setNombre(
					// iteradorFeature.getAttributes().get(categoria.getAliasNombreItem()).getAsString());
					// marcadorService.marcadorCrear(marcador);
				}
				List<Coordenada> coordenadas = new ArrayList<Coordenada>();
				int numeroCoordenadas;

				try {
					numeroCoordenadas = iteradorFeature.getGeometry().getRings().get(0).size();
				} catch (Exception e) {
					numeroCoordenadas = 0;
				}

				if (numeroCoordenadas > 1 & poligono) {
					marcador.setPoligono(true);
					for (int i = 0; i < iteradorFeature.getGeometry().getRings().size(); i++) {
						List<CoordenadaDto> coordenadasDto = new ArrayList<CoordenadaDto>();
						Coordenada coordenada = new Coordenada();

						for (int j = 0; j < iteradorFeature.getGeometry().getRings().get(i).size(); j++) {
							CoordenadaDto coordenadaDto = new CoordenadaDto();
							coordenadaDto.setLatitud(iteradorFeature.getGeometry().getRings().get(i).get(j)[0]);
							coordenadaDto.setLongitud(iteradorFeature.getGeometry().getRings().get(i).get(j)[1]);
							coordenadasDto.add(coordenadaDto);
						}

						if (coordenadasDto.size() > 1) {
							Polygon polygon = GeometryUtil.obtenerPuntosPolygon(coordenadasDto);
							coordenada.setCoordenadaPolygon(GeometryUtil.obtenerPuntosPolygon(coordenadasDto));
							coordenada.setCoordenadaPunto(polygon.getCentroid());
							coordenada.setMarcador(marcador);
							if (coordenadaService.coordeanadaCrear(coordenada)) {
								coordenadas.add(coordenada);
							}

						}
					}

				} else {
					marcador.setPoligono(false);
					try {
						if (iteradorFeature.getGeometry().getX() != 0 && iteradorFeature.getGeometry().getY() != 0) {

							Coordenada coordenada = new Coordenada();
							coordenada.setMarcador(marcador);
							coordenada.setCoordenadaPunto(GeometryUtil.obtenerPunto(
									iteradorFeature.getGeometry().getX(), iteradorFeature.getGeometry().getY()));
							if (coordenadaService.coordeanadaCrear(coordenada)) {
								coordenadas.add(coordenada);
							}

						}
					} catch (Exception e) {

						for (int i = 0; i < iteradorFeature.getGeometry().getRings().size(); i++) {
							List<CoordenadaDto> coordenadasDto = new ArrayList<CoordenadaDto>();
							Coordenada coordenada = new Coordenada();

							for (int j = 0; j < iteradorFeature.getGeometry().getRings().get(i).size(); j++) {
								CoordenadaDto coordenadaDto = new CoordenadaDto();
								coordenadaDto.setLatitud(iteradorFeature.getGeometry().getRings().get(i).get(j)[0]);
								coordenadaDto.setLongitud(iteradorFeature.getGeometry().getRings().get(i).get(j)[1]);
								coordenadasDto.add(coordenadaDto);
							}

							if (coordenadasDto.size() > 1) {
								Polygon polygon = GeometryUtil.obtenerPuntosPolygon(coordenadasDto);
								// coordenada.setCoordenadaPolygon(GeometryUtil.obtenerPuntosPolygon(coordenadasDto));
								coordenada.setCoordenadaPunto(polygon.getCentroid());
								coordenada.setMarcador(marcador);
								if (coordenadaService.coordeanadaCrear(coordenada)) {
									coordenadas.add(coordenada);
								}

							}
						}

					}
				}

				if (coordenadas.size() > 0) {
					AplicacionDto aplicacionDto = aplicacionService
							.obtenerAplicacionDtoPorIdCategoria(categoria.getId());

//					marcador.setCoordenadas(coordenadas);
					marcador.setNombre(
							iteradorFeature.getAttributes().get(categoria.getAliasNombre()).getAsString());
					marcador.setDescripcion(
							iteradorFeature.getAttributes().get(categoria.getAliasDescripcion()).getAsString());
					marcador.setCodigo(iteradorFeature.getAttributes().get(categoria.getAliasTipo()).getAsInt());
					JsonObject jsonObjectSimbol = pJsonObject2.get("symbol").getAsJsonObject();
					marcador.setColorFondo(jsonObjectSimbol.get("color").getAsJsonArray().toString());
					marcador.setColorBorde(
							jsonObjectSimbol.get("outline").getAsJsonObject().get("color").getAsJsonArray().toString());

					VentanaInformacion ventanaInformacion = new VentanaInformacion();
					ventanaInformacion.setNombre(
							iteradorFeature.getAttributes().get(categoria.getAliasNombre()).getAsString());
					ventanaInformacion.setIcono(categoria.getIcono());
					ventanaInformacion.setColor(aplicacionDto.getCodigoColor());
					if (ventanaInformacionService.ventanaInformacionCrear(ventanaInformacion) != null) {
						marcador.setIcono(categoria.getIconoMarcador());
						marcador.setVentanaInformacion(ventanaInformacion);
						if (marcadorService.marcadorCrear(marcador)) {
							marcadores.add(marcador);
						} else {
							ventanaInformacionService.ventanaInformacionEliminar(ventanaInformacion.getId());
//							for (Coordenada iteradorCoordenada : marcador.getCoordenadas()) {
//								coordenadaService.coordenadaEliminar(iteradorCoordenada.getId());
//							}
							// return null;
						}
					} else {
						// return null;
					}
				}

			}

		}
		return marcadores;
	}

	public boolean actualizarData(Long tipoRecurso, Long idRecurso, boolean poligono) {
		for (TiposRecurso tiposRecursoIterador : TiposRecurso.values()) {         //TiposRecurso: Capa,Categoria,Subcategoria
			if (tiposRecursoIterador.getValor() == tipoRecurso) {
				if (tiposRecursoIterador.equals(TiposRecurso.CATEGORIA)) {
					try {
						Categoria categoria = categoriaService.categoriaObtenerPorId(idRecurso);
						DrawingInfoDto drawingInfo = gateway.obtenerInformacionCategoria(categoria.getUrlRecurso());
						JsonObject pJsonObject = drawingInfo.getRenderer().getAsJsonObject();
						JsonArray pJsonArray = new JsonArray();
						try {
							pJsonArray = pJsonObject.get("uniqueValueInfos").getAsJsonArray();
						} catch (Exception e) {
							pJsonArray.add(pJsonObject);
						}
						JsonObject pJsonObject2 = null;
						boolean actualizar = false;
						if (pJsonArray.size() > 1) {
							for (int i = 0; i < pJsonArray.size(); i++) {
								pJsonObject2 = pJsonArray.get(i).getAsJsonObject();
								System.out.print("registro " + i + " --->" + pJsonObject2.get("value").getAsString());
								if (categoria.getNombre().contains(pJsonObject2.get("value").getAsString())) {
									System.out.print("comparo bien");
									actualizar = true;
									break;
								}
								;

							}
						} else if (pJsonArray.size() == 1) {
							actualizar = true;
						}

						if (actualizar) {
							categoria.setMarcadores(
									actualizarMarcadores(tipoRecurso, idRecurso, categoria, pJsonArray, poligono));
							categoriaService.categoriaActualizar(categoria);
						}

						return true;
					} catch (Exception e) {
						return false;
					}
				} else {
					if (tiposRecursoIterador.equals(TiposRecurso.SUBCATEGORIA)) {
						try {
							Subcategoria subcategoria = subcategoriaService.subcategoriaObtenerPorId(idRecurso);

							// cambiar a subcategoria relación

							DrawingInfoDto drawingInfo = gateway
									.obtenerInformacionCategoria(subcategoria.getUrlRecurso());
							JsonObject pJsonObject = drawingInfo.getRenderer().getAsJsonObject();
							JsonArray pJsonArray = pJsonObject.get("uniqueValueInfos").getAsJsonArray();

							subcategoria.setMarcadores(
									actualizarMarcadores(tipoRecurso, idRecurso, subcategoria, pJsonArray, poligono));
							subcategoriaService.subcategoriaActualizar(subcategoria);
							return true;
						} catch (Exception e) {
							return false;
						}
					}
				}
			}
		}
		return false;
	}
	
	private JSONArray obtenerSubLayers(Long idRecurso) {
		categoria = categoriaService.categoriaObtenerPorId(idRecurso);
		objetoServicio = gateway.obtenerInformacionSubLayers(categoria.getUrlRecurso());
		return objetoServicio.getJSONArray("subLayers");
	}
	
}
