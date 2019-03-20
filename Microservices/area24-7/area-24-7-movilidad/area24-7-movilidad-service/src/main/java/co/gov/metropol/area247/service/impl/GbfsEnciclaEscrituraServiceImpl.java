package co.gov.metropol.area247.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import co.gov.metropol.area247.gateway.IEnciclaServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.service.IGbfsEscrituraService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.services.rest.encicla.EnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.EstacionEnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.ZonaCiudadEnciclaWSDTO;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.constantes.Constantes.TipoParametro;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service("gbfsEnciclaEscrituraService")
public class GbfsEnciclaEscrituraServiceImpl implements IGbfsEscrituraService {

	private static final String GBFS = "gbfs";
	private static final String INFORMACION_SISTEMA = "system_information";
	private static final String INFORMACION_ESTACIONES = "station_information";
	private static final String ESTADO_ESTACIONES = "station_status";
	private static final String REGIONES = "system_regions";
	private static final String EXT_JSON = ".json";

	@Autowired
	private IEnciclaServiceGateway enciclaServiceGateway;

	@Autowired
	private ITipoParametroService tipoParametroService;

	private EnciclaWSDTO respuestaEnciclaWSDTO;

	private List<SystemRegion> systemRegions = new ArrayList<>();
	private List<StationInformation> stationsInfo = new ArrayList<>();
	private List<StationStatus> stationsStatus = new ArrayList<>();

	private String urlFilesGbfs;
	private String urlHostCloudGbfs;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGbfsEscrituraService#escribirGBFS()
	 */
	@Override
	public void escribirGBFS() {

		try {

			respuestaEnciclaWSDTO = enciclaServiceGateway.consultarEstatusEncicla();
			urlFilesGbfs = tipoParametroService.obtenerValorCadena(TipoParametro.URL_FILES_GBFS_ENCICLA);
			urlHostCloudGbfs = PropertiesManager.obtenerCadena(Recursos.ROUTE_SERVICES, "bootApi.gateway.routeGbfsEnciclaHostCloude");

			crearJsonGbfs();
			crearJsonSistemaInformacion();
			crearJsonEstacionesInformacion();
			crearJsonEstadoEstaciones();
			crearJsonRegionesSistema();

		} catch (Exception e) {
			String msg = "Error al crear los archivos GBFS";
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);
		}

	}

	@Override
	public Gson crearJsonGbfs() {

		Feed gbfsFeed = new Feed(GBFS, 
				urlHostCloudGbfs.concat(GBFS).concat(EXT_JSON));
		Feed systemInformationFeed = new Feed(INFORMACION_SISTEMA,
				urlHostCloudGbfs.concat(INFORMACION_SISTEMA).concat(EXT_JSON));
		Feed stationInformationFeed = new Feed(INFORMACION_ESTACIONES,
				urlHostCloudGbfs.concat(INFORMACION_ESTACIONES).concat(EXT_JSON));
		Feed stationStatusFeed = new Feed(ESTADO_ESTACIONES, 
				urlHostCloudGbfs.concat(ESTADO_ESTACIONES).concat(EXT_JSON));
		Feed systemRegionsFeed = new Feed(REGIONES, 
				urlHostCloudGbfs.concat(REGIONES).concat(EXT_JSON));

		List<Feed> feeds = new ArrayList<>();
		feeds.add(gbfsFeed);
		feeds.add(systemInformationFeed);
		feeds.add(stationInformationFeed);
		feeds.add(stationStatusFeed);
		feeds.add(systemRegionsFeed);
		
		Lang lang = new Lang(feeds);

		GbfsData data = new GbfsData(lang);
		GbfsFeed feed = new GbfsFeed(data);
		feed.setLastUpdated(Instant.now().getEpochSecond());
		feed.setTtl(0l);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String strJson = gson.toJson(feed);

		crearArchivo(strJson, GBFS.concat(EXT_JSON));

		return gson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGbfsEscrituraService#
	 * crearJsonSistemaInformacion()
	 */
	@Override
	public Gson crearJsonSistemaInformacion() {

		SystemInformation sysInfo = new SystemInformation();
		sysInfo.setSystemId(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "encicla.info.sistema.nombre"));
		sysInfo.setLanguage(Constantes.LANGUAJE);
		sysInfo.setName(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "encicla.info.sistema.descripcion"));
		sysInfo.setUrl(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "encicla.info.sistema.url"));
		sysInfo.setPurchaseUrl(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "encicla.info.sistema.url"));
		sysInfo.setTimezone(Constantes.TIME_ZONE);
		sysInfo.setPhoneNumber(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "encicla.info.sistema.telefono"));

		SystemInformationData sysInfoData = new SystemInformationData();
		sysInfoData.setLastUpdated(Instant.now().getEpochSecond());
		sysInfoData.setTtl(0l);
		sysInfoData.setData(sysInfo);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String strJson = gson.toJson(sysInfoData);

		crearArchivo(strJson, INFORMACION_SISTEMA.concat(EXT_JSON));

		return gson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGbfsEscrituraService#
	 * crearJsonEstacionesInformacion()
	 */
	@Override
	public Gson crearJsonEstacionesInformacion() {

		for (ZonaCiudadEnciclaWSDTO zonaWSDTO : respuestaEnciclaWSDTO.getStations()) {
			// Obtenemos la informacion de las zonas
			String regionId = zonaWSDTO.getId();
			SystemRegion sysReg = new SystemRegion();
			sysReg.setRegionId(regionId);
			sysReg.setName(zonaWSDTO.getName().concat(" (" + zonaWSDTO.getDesc() + ") "));
			systemRegions.add(sysReg);

			for (EstacionEnciclaWSDTO estacionEnciclaWSDTO : zonaWSDTO.getItems()) {
				// Obtenemos la informacion de las estaciones
				StationInformation stationInfo = new StationInformation();
				stationInfo.setStationId(estacionEnciclaWSDTO.getId());
				stationInfo.setName(estacionEnciclaWSDTO.getName());
				stationInfo.setRegionId(regionId);
				stationInfo.setLat(estacionEnciclaWSDTO.getLat());
				stationInfo.setLon(estacionEnciclaWSDTO.getLon());
				stationInfo.setAddress(estacionEnciclaWSDTO.getAddress());
				stationInfo.setCapacity(estacionEnciclaWSDTO.getCapacity().intValue());
				this.stationsInfo.add(stationInfo);
				// Obtenemos la informacion del estado de cada estacion
				StationStatus stationStatus = new StationStatus();
				stationStatus.setInstalled(1);
				stationStatus.setRenting(1);
				stationStatus.setReturning(1);
				stationStatus.setStationId(estacionEnciclaWSDTO.getId());
				stationStatus.setNumBikesAvailable(estacionEnciclaWSDTO.getBikes());
				stationStatus.setNumDocksAvailable(
						estacionEnciclaWSDTO.getPlaces() == null ? 0 : estacionEnciclaWSDTO.getPlaces());
				stationStatus.setLastReported(Instant.now().getEpochSecond());
				this.stationsStatus.add(stationStatus);
			}
		}

		StationInformationData data = new StationInformationData();
		data.setStations(stationsInfo);
		StationInformationFeed feed = new StationInformationFeed();
		feed.setLastUpdated(Instant.now().getEpochSecond());
		feed.setTtl(0l);
		feed.setData(data);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String strJson = gson.toJson(feed);

		crearArchivo(strJson, INFORMACION_ESTACIONES.concat(EXT_JSON));

		return gson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGbfsEscrituraService#
	 * crearJsonEstadoEstaciones()
	 */
	@Override
	public Gson crearJsonEstadoEstaciones() {

		StationStatusData data = new StationStatusData();
		data.setStations(stationsStatus);
		StationStatusFeed feed = new StationStatusFeed();
		feed.setLastUpdated(Instant.now().getEpochSecond());
		feed.setTtl(0l);
		feed.setData(data);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String strJson = gson.toJson(feed);

		crearArchivo(strJson, ESTADO_ESTACIONES.concat(EXT_JSON));

		return gson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGbfsEscrituraService#
	 * crearJsonRegionesSistema()
	 */
	@Override
	public Gson crearJsonRegionesSistema() {

		SystemRegionData data = new SystemRegionData(systemRegions);
		SystemRegionFeed feed = new SystemRegionFeed(data, Instant.now().getEpochSecond(), 0l);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String strJson = gson.toJson(feed);

		crearArchivo(strJson, REGIONES.concat(EXT_JSON));

		return gson;

	}

	private void crearArchivo(String strJson, String nombreArchivo) {

		try {
			
			File archivo = new File(urlFilesGbfs.concat(nombreArchivo));
			
			if (!archivo.exists()) {
				archivo.createNewFile();
			}
			
			Writer writer = new FileWriter(archivo);
			writer.write(strJson);
			writer.close();

		} catch (FileNotFoundException e) {
			String msg = MessageFormat.format(
					"No se encontro el archivo {0} que debe ser configurada en los parametros de BD con el id = 3",
					nombreArchivo);
			throw new Area247Exception(msg, e);
		} catch (Exception e) {
			String msg = MessageFormat.format("Ocurrió un error al crear el archivo {0}", nombreArchivo);
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);
		}

	}

	class BaseFeed {

		@SerializedName("last_updated")
		private Long lastUpdated;
		private Long ttl;

		public void setLastUpdated(Long lastUpdate) {
			this.lastUpdated = lastUpdate;
		}

		public Long getLastUpdated() {
			return lastUpdated;
		}

		public void setTtl(Long ttl) {
			this.ttl = ttl;
		}

		public Long getTtl() {
			return ttl;
		}

	}

	class Feed {

		private String name;
		private String url;

		public Feed(String name, String url) {
			this.name = name;
			this.url = url;
		}

		public String getName() {
			return name;
		}

		public String getUrl() {
			return url;
		}
	}

	class Lang {

		private List<Feed> feeds;

		public Lang(List<Feed> feeds) {
			this.feeds = feeds;
		}

		public List<Feed> getFeeds() {
			return feeds;
		}
	}

	class GbfsData {

		private Lang es;

		public GbfsData(Lang es) {
			this.es = es;
		}

		public Lang getEn() {
			return es;
		}
	}

	class GbfsFeed extends BaseFeed {

		private GbfsData data;

		public GbfsFeed(GbfsData data) {
			this.data = data;
		}

		public GbfsData getData() {
			return data;
		}

	}

	class StationInformation {

		@SerializedName("station_id")
		private String stationId;
		private String name;
		@SerializedName("short_name")
		private String shortName;
		private String lat;
		private String lon;
		@SerializedName("region_id")
		private String regionId;
		private String address;
		private Integer capacity;

		public String getStationId() {
			return stationId;
		}

		public String getName() {
			return name;
		}

		public String getShortName() {
			return shortName;
		}

		public String getLat() {
			return lat;
		}

		public String getLon() {
			return lon;
		}

		public String getRegionId() {
			return regionId;
		}

		public Integer getCapacity() {
			return capacity;
		}

		public void setStationId(String stationId) {
			this.stationId = stationId;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public void setLon(String lon) {
			this.lon = lon;
		}

		public void setRegionId(String regionId) {
			this.regionId = regionId;
		}

		public void setCapacity(Integer capacity) {
			this.capacity = capacity;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

	}

	class StationInformationData {

		private List<StationInformation> stations;

		public void setStations(List<StationInformation> stations) {
			this.stations = stations;
		}

		public List<StationInformation> getStations() {
			return stations;
		}
	}

	class StationInformationFeed extends BaseFeed {

		private StationInformationData data;

		public void setData(StationInformationData data) {
			this.data = data;
		}

		public StationInformationData getData() {
			return data;
		}
	}

	class SystemInformation {

		@SerializedName("system_id")
		private String systemId;
		private String language;
		private String name;
		private String url;
		@SerializedName("purchase_url")
		private String purchaseUrl;
		private String timezone;
		@SerializedName("phone_number")
		private String phoneNumber;

		public String getSystemId() {
			return systemId;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getPurchaseUrl() {
			return purchaseUrl;
		}

		public void setPurchaseUrl(String purchaseUrl) {
			this.purchaseUrl = purchaseUrl;
		}

		public String getTimezone() {
			return timezone;
		}

		public void setTimezone(String timezone) {
			this.timezone = timezone;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

	}

	class SystemInformationData extends BaseFeed {

		private SystemInformation data;

		public SystemInformation getData() {
			return data;
		}

		public void setData(SystemInformation data) {
			this.data = data;
		}

	}

	class SystemRegion {

		@SerializedName("region_id")
		private String regionId;
		private String name;

		public String getRegionId() {
			return regionId;
		}

		public void setRegionId(String regionId) {
			this.regionId = regionId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	class SystemRegionData {

		private List<SystemRegion> regions;

		public SystemRegionData(List<SystemRegion> regions) {
			this.regions = regions;
		}

		public List<SystemRegion> getRegions() {
			return regions;
		}

		public void setRegions(List<SystemRegion> regions) {
			this.regions = regions;
		}
	}

	class SystemRegionFeed extends BaseFeed {

		private SystemRegionData data;

		public SystemRegionFeed(SystemRegionData data, Long lastUpdated, Long ttl) {
			this.data = data;
			super.lastUpdated = lastUpdated;
			super.ttl = ttl;
		}

		public SystemRegionData getData() {
			return data;
		}

		public void setData(SystemRegionData data) {
			this.data = data;
		}
	}

	class StationStatus {

		@SerializedName("station_id")
		private String stationId;
		@SerializedName("num_bikes_available")
		private Long numBikesAvailable;
		@SerializedName("num_bikes_disabled")
		private Long numBikesDisabled;
		@SerializedName("num_docks_available")
		private Long numDocksAvailable;
		@SerializedName("is_installed")
		private int isInstalled;
		@SerializedName("is_renting")
		private int isRenting;
		@SerializedName("is_returning")
		private int isReturning;
		@SerializedName("last_reported")
		private Long lastReported;

		public String getStationId() {
			return stationId;
		}

		public void setStationId(String stationId) {
			this.stationId = stationId;
		}

		public Long getNumBikesAvailable() {
			return numBikesAvailable;
		}

		public void setNumBikesAvailable(Long numBikesAvailable) {
			this.numBikesAvailable = numBikesAvailable;
		}

		public Long getNumBikesDisabled() {
			return numBikesDisabled;
		}

		public void setNumBikesDisabled(Long numBikesDisabled) {
			this.numBikesDisabled = numBikesDisabled;
		}

		public Long getNumDocksAvailable() {
			return numDocksAvailable;
		}

		public void setNumDocksAvailable(Long numDocksAvailable) {
			this.numDocksAvailable = numDocksAvailable;
		}

		public int isInstalled() {
			return isInstalled;
		}

		public void setInstalled(int isInstalled) {
			this.isInstalled = isInstalled;
		}

		public int isRenting() {
			return isRenting;
		}

		public void setRenting(int isRenting) {
			this.isRenting = isRenting;
		}

		public int isReturning() {
			return isReturning;
		}

		public void setReturning(int isReturning) {
			this.isReturning = isReturning;
		}

		public Long getLastReported() {
			return lastReported;
		}

		public void setLastReported(Long lastReported) {
			this.lastReported = lastReported;
		}

	}

	class StationStatusData {

		private List<StationStatus> stations;

		public List<StationStatus> getStations() {
			return stations;
		}

		public void setStations(List<StationStatus> stations) {
			this.stations = stations;
		}

	}

	class StationStatusFeed extends BaseFeed {

		private StationStatusData data;

		public StationStatusData getData() {
			return data;
		}

		public void setData(StationStatusData data) {
			this.data = data;
		}

	}

}
