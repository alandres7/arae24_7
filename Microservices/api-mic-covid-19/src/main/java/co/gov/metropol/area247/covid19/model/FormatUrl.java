package co.gov.metropol.area247.covid19.model;

import java.io.Serializable;

public class FormatUrl implements Serializable {
	
	private String schemaAuthority;
	private String resourceId;
	private String conditions;
	
	public FormatUrl() {
		// TODO Auto-generated constructor stub
	}
	public FormatUrl(String schemaAuthority, String resourceId, String conditions) {
		this.schemaAuthority = schemaAuthority;
		this.resourceId = resourceId;
		this.conditions = conditions;
	}	
	public FormatUrl(String schemaAuthority, String resourceId) {
		this.schemaAuthority = schemaAuthority;
		this.resourceId = resourceId;
	}
	public String getSchemaAuthority() {
		return schemaAuthority;
	}
	public void setSchemaAuthority(String schemaAuthority) {
		this.schemaAuthority = schemaAuthority;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}
