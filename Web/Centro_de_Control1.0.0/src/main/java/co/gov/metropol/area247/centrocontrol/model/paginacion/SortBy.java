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
 * Time: 17.55
 * To change this template use File | Settings | File Templates.
 */
public class SortBy {

    /**
     * The map of sorts.
     */
    private Map<String, SortOrder> mapOfSorts;

    /**
     * Instantiates a new sort by.
     */
    public SortBy() {
        if (null == mapOfSorts) {
            mapOfSorts = new HashMap<String, SortOrder>();
        }
    }

    /**
     * Gets the sort bys.
     *
     * @return the sortBys
     */
    public Map<String, SortOrder> getSortBys() {
        return mapOfSorts;
    }

    /**
     * Adds the sort.
     *
     * @param sortBy the sort by
     */
    public void addSort(String sortBy) {
        mapOfSorts.put(sortBy, SortOrder.ASC);
    }

    /**
     * Adds the sort.
     *
     * @param sortBy    the sort by
     * @param sortOrder the sort order
     */
    public void addSort(String sortBy, SortOrder sortOrder) {
        mapOfSorts.put(sortBy, sortOrder);
    }

}