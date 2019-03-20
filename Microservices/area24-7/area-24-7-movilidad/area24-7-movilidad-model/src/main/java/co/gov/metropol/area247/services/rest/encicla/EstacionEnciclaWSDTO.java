package co.gov.metropol.area247.services.rest.encicla;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.annotations.Loggable;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class EstacionEnciclaWSDTO {
	
	@JsonProperty(value = "id")
	@Loggable
	private String id;
	
	@JsonProperty(value = "order")
	@Loggable
	private Long order;
	
	@JsonProperty(value = "name")
	@Loggable
	private String name;
	
	@JsonProperty(value = "address")
	@Loggable
	private String address;
	
	@JsonProperty(value = "description")
	@Loggable
	private String description;
	
	@JsonProperty(value = "lat")
	@Loggable
	private String lat;
	
	@JsonProperty(value = "lon")
	@Loggable
	private String lon;
	
	@JsonProperty(value = "type")
	@Loggable
	private String type;
	
	@JsonProperty(value = "capacity")
	@Loggable
	private Long capacity;
	
	@JsonProperty(value = "bikes")
	@Loggable
	private Long bikes;
	
	@JsonProperty(value = "places")
	@Loggable
	private Long places;
	
	@JsonProperty(value = "picture")
	@Loggable
	private String picture;

	public EstacionEnciclaWSDTO(){
		super();
	}
	
	public EstacionEnciclaWSDTO(@JsonProperty(value = "id") String id,
			@JsonProperty(value = "order") Long order, 
			@JsonProperty(value = "name") String name, 
			@JsonProperty(value = "address") String address, 
			@JsonProperty(value = "description") String description, 
			@JsonProperty(value = "lat") String lat, 
			@JsonProperty(value = "lon") String lon,
			@JsonProperty(value = "type") String type, 
			@JsonProperty(value = "capacity") Long capacity, 
			@JsonProperty(value = "bikes") Long bikes, 
			@JsonProperty(value = "places") Long places, 
			@JsonProperty(value = "picture")String picture){
		this.id = id;
		this.address = address;
		this.bikes = bikes;
		this.capacity = capacity;
		this.description = description;
		this.lat = lat;
		this.lon = lon;
		this.type = type;
		this.order = order;
		this.places = places;
		this.picture = picture;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}

	public Long getBikes() {
		return bikes;
	}

	public void setBikes(Long bikes) {
		this.bikes = bikes;
	}

	public Long getPlaces() {
		return places;
	}

	public void setPlaces(Long places) {
		this.places = places;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
