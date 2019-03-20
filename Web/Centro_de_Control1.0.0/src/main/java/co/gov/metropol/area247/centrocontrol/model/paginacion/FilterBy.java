package co.gov.metropol.area247.centrocontrol.model.paginacion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-datatable
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/04/18
 * Time: 17.57
 * To change this template use File | Settings | File Templates.
 */
public class FilterBy {
    /**
     * The map of sorts.
     */
    private Map<String, String> mapOfFilters;


    /**
     * The global search.
     */
    private boolean globalSearch;

    /**
     * Instantiates a new sort by.
     */
    public FilterBy() {
        if (null == mapOfFilters) {
            mapOfFilters = new HashMap<String, String>();
        }
    }

    /**
     * Gets the map of filters.
     *
     * @return the mapOfFilters
     */
    public Map<String, String> getMapOfFilters() {
        return mapOfFilters;
    }

    /**
     * Sets the map of filters.
     *
     * @param mapOfFilters the mapOfFilters to set
     */
    public void setMapOfFilters(Map<String, String> mapOfFilters) {
        this.mapOfFilters = mapOfFilters;
    }

    /**
     * Adds the sort.
     *
     * @param filterColumn the filter column
     * @param filterValue  the filter value
     */
    public void addFilter(String filterColumn, String filterValue) {
        mapOfFilters.put(filterColumn, filterValue);
    }

    /**
     * Checks if is global search.
     *
     * @return the globalSearch
     */
    public boolean isGlobalSearch() {
        return globalSearch;
    }

    /**
     * Sets the global search.
     *
     * @param globalSearch the globalSearch to set
     */
    public void setGlobalSearch(boolean globalSearch) {
        this.globalSearch = globalSearch;
    }

}