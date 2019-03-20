package co.gov.metropol.area247.centrocontrol.model.paginacion;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import co.gov.metropol.area247.centrocontrol.model.Avistamiento;
import co.gov.metropol.area247.centrocontrol.model.MarkerPrintPackage;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-datatable
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/04/18
 * Time: 18.00
 * To change this template use File | Settings | File Templates.
 */
public class DataTableResults<T> {

    /**
     * The list of data objects.
     */
    @SerializedName("data")
    List<T> listOfDataObjects;
    /**
     * The draw.
     */
    private String draw;
    /**
     * The records filtered.
     */
    private String recordsFiltered;
    /**
     * The records total.
     */
    private String recordsTotal;
    
    
    private MarkerPrintPackage markerPrintPackage;
  

    public String getJson() {
        return new Gson().toJson(this);
    }

    /**
     * Gets the draw.
     *
     * @return the draw
     */
    public String getDraw() {
        return draw;
    }

    /**
     * Sets the draw.
     *
     * @param draw the draw to set
     */
    public void setDraw(String draw) {
        this.draw = draw;
    }

    /**
     * Gets the records filtered.
     *
     * @return the recordsFiltered
     */
    public String getRecordsFiltered() {
        return recordsFiltered;
    }

    /**
     * Sets the records filtered.
     *
     * @param recordsFiltered the recordsFiltered to set
     */
    public void setRecordsFiltered(String recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    /**
     * Gets the records total.
     *
     * @return the recordsTotal
     */
    public String getRecordsTotal() {
        return recordsTotal;
    }

    /**
     * Sets the records total.
     *
     * @param recordsTotal the recordsTotal to set
     */
    public void setRecordsTotal(String recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    /**
     * Gets the list of data objects.
     *
     * @return the listOfDataObjects
     */
    public List<T> getListOfDataObjects() {
        return listOfDataObjects;
    }

    /**
     * Sets the list of data objects.
     *
     * @param listOfDataObjects the listOfDataObjects to set
     */
    public void setListOfDataObjects(List<T> listOfDataObjects) {
        this.listOfDataObjects = listOfDataObjects;
    }
    
    

	public MarkerPrintPackage getMarkerPrintPackage() {
		return markerPrintPackage;
	}

	public void setMarkerPrintPackage(MarkerPrintPackage markerPrintPackage) {
		this.markerPrintPackage = markerPrintPackage;
	}
    
}