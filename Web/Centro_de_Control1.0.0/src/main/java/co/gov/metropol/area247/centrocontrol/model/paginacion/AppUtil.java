package co.gov.metropol.area247.centrocontrol.model.paginacion;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-datatable
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/04/18
 * Time: 17.52
 * To change this template use File | Settings | File Templates.
 */
public class AppUtil {

    /**
     * Checks if is collection empty.
     *
     * @param collection the collection
     * @return true, if is collection empty
     */
    private static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks if is object empty.
     *
     * @param object the object
     * @return true, if is object empty
     */
    public static boolean isObjectEmpty(Object object) {
        if (object == null) return true;
        else if (object instanceof String) {
            return ((String) object).trim().length() == 0;
        } else if (object instanceof Collection) {
            return isCollectionEmpty((Collection<?>) object);
        }
        return false;
    }

    /**
     * Gets the bean to json string.
     *
     * @param beanClass the bean class
     * @return the bean to json string
     */
    public static String getBeanToJsonString(Object beanClass) {
        return new Gson().toJson(beanClass);
    }

    /**
     * Gets the bean to json string.
     *
     * @param beanClasses the bean classes
     * @return the bean to json string
     */
    public static String getBeanToJsonString(Object... beanClasses) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object beanClass : beanClasses) {
            stringBuilder.append(getBeanToJsonString(beanClass));
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    /**
     * Builds the paginated query.
     *
     * @param baseQuery          the base query
     * @param paginationCriteria the pagination criteria
     * @return the string
     */
    public static String buildPaginatedQuery(String baseQuery, PaginationCriteria paginationCriteria) {
        //String queryTemplate = "SELECT FILTERED_ORDERD_RESULTS.* FROM (SELECT BASEINFO.* FROM ( %s ) BASEINFO %s  %s ) FILTERED_ORDERD_RESULTS LIMIT %d, %d";
        StringBuilder sb = new StringBuilder("SELECT FILTERED_ORDERD_RESULTS.* FROM (SELECT BASEINFO.* FROM ( #BASE_QUERY# ) BASEINFO #WHERE_CLAUSE#  #ORDER_CLASUE# ) FILTERED_ORDERD_RESULTS LIMIT #PAGE_NUMBER#, #PAGE_SIZE#");
        String finalQuery = null;
        if (!AppUtil.isObjectEmpty(paginationCriteria)) {
            finalQuery = sb.toString().replaceAll("#BASE_QUERY#", baseQuery)
                    .replaceAll("#WHERE_CLAUSE#", ((AppUtil.isObjectEmpty(paginationCriteria.getFilterByClause())) ? "" : " WHERE ") + paginationCriteria.getFilterByClause())
                    .replaceAll("#ORDER_CLASUE#", paginationCriteria.getOrderByClause())
                    .replaceAll("#PAGE_NUMBER#", paginationCriteria.getPageNumber().toString())
                    .replaceAll("#PAGE_SIZE#", paginationCriteria.getPageSize().toString());
        }
        return (null == finalQuery) ? baseQuery : finalQuery;
    }

    /**
     * Concatenate.
     *
     * @param listOfItems the list of items
     * @param separator   the separator
     * @return the string
     */
    public String concatenate(List<String> listOfItems, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> stit = listOfItems.iterator();

        while (stit.hasNext()) {
            sb.append(stit.next());
            if (stit.hasNext()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }
}