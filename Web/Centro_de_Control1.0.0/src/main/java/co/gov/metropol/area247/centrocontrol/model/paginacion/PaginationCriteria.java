package co.gov.metropol.area247.centrocontrol.model.paginacion;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-datatable
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/04/18
 * Time: 17.56
 * To change this template use File | Settings | File Templates.
 */
public class PaginationCriteria {
    /**
     * The Constant PAGE_NO.
     */
    public static final String PAGE_NO = "start";
    /**
     * The Constant PAGE_SIZE.
     */
    public static final String PAGE_SIZE = "length";
    /**
     * The Constant DRAW.
     */
    public static final String DRAW = "draw";
    /**
     * The Constant BLANK.
     */
    private static final String BLANK = "";
    /**
     * The Constant SPACE.
     */
    private static final String SPACE = " ";
    /**
     * The Constant LIKE_PREFIX.
     */
    private static final String LIKE = " LIKE ";
    
    private static final String UPPER = " UPPER";
    
    private static final String JOKER = "%";
    
    private static final String QUOTATION_MARK = "'";
    /**
     * The Constant LIKE_SUFFIX.
     */
//    private static final String LIKE_SUFFIX = "%";
    /**
     * The Constant AND.
     */
    private static final String AND = " AND ";
    /**
     * The Constant OR.
     */
    private static final String OR = " OR ";
    /**
     * The Constant ORDER_BY.
     */
    private static final String ORDER_BY = " ORDER BY ";
    private static final String BRKT_OPN = " ( ";
    private static final String BRKT_CLS = " ) ";
    /**
     * The Constant COMMA.
     */
    private static final String COMMA = " , ";
    /**
     * The page number.
     */
    private Integer pageNumber;
    /**
     * The page size.
     */
    private Integer pageSize;
    /**
     * The total records.
     */
    private Integer totalRecords;
    /**
     * The sort by.
     */
    private SortBy sortBy;
    /**
     * The filter by.
     */
    private FilterBy filterBy;

    /**
     * Gets the page number.
     *
     * @return the pageNumber
     */
    public Integer getPageNumber() {
        return (null == pageNumber) ? 0 : pageNumber;
    }

    /**
     * Sets the page number.
     *
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets the page size.
     *
     * @return the pageSize
     */
    public Integer getPageSize() {
        return (null == pageSize) ? 10 : pageSize;
    }

    /**
     * Sets the page size.
     *
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets the total records.
     *
     * @return the totalRecords
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     * Sets the total records.
     *
     * @param totalRecords the totalRecords to set
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * Gets the sort by.
     *
     * @return the sortBy
     */
    public SortBy getSortBy() {
        return sortBy;
    }

    /**
     * Sets the sort by.
     *
     * @param sortBy the sortBy to set
     */
    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Gets the filter by.
     *
     * @return the filterBy
     */
    public FilterBy getFilterBy() {
        return filterBy;
    }

    /**
     * Sets the filter by.
     *
     * @param filterBy the filterBy to set
     */
    public void setFilterBy(FilterBy filterBy) {
        this.filterBy = filterBy;
    }

    /**
     * Checks if is filter by empty.
     *
     * @return true, if is filter by empty
     */
    public boolean isFilterByEmpty() {
        return null == filterBy || null == filterBy.getMapOfFilters() || filterBy.getMapOfFilters().size() == 0;
    }

    /**
     * Checks if is sort by empty.
     *
     * @return true, if is sort by empty
     */
    public boolean isSortByEmpty() {
        return null == sortBy || null == sortBy.getSortBys() || sortBy.getSortBys().size() == 0;
    }

    /**
     * Gets the filter by clause.
     *
     * @return the filter by clause
     */
    public String getFilterByClause() {

        StringBuilder fbsb = null;

        if (!isFilterByEmpty()) {
            Iterator<Map.Entry<String, String>> fbit = filterBy.getMapOfFilters().entrySet().iterator();

            while (fbit.hasNext()) {

                Map.Entry<String, String> pair = fbit.next();

                if (!pair.getKey().equals("")) {
                    if (null == fbsb) {
                        fbsb = new StringBuilder();
                        fbsb.append(BRKT_OPN);                    
                        fbsb.append(SPACE)
                                .append(BRKT_OPN)
                                .append(UPPER)
                                .append(BRKT_OPN)
                                .append(pair.getKey())
                                .append(BRKT_CLS)
                        		.append(SPACE)
                                .append(LIKE)
                                .append(UPPER)
                                .append(BRKT_OPN)
                                .append(QUOTATION_MARK)
                                .append(JOKER)
                                .append(pair.getValue())
                                .append(JOKER)
                                .append(QUOTATION_MARK)
                                .append(BRKT_CLS)
                                .append(BRKT_CLS);                   
                    } else {                    
                        fbsb.append(filterBy.isGlobalSearch() ? OR : AND)
                                .append(BRKT_OPN)
                                .append(UPPER)
                                .append(BRKT_OPN)
                                .append(pair.getKey())
                                .append(BRKT_CLS)
                        		.append(SPACE)
                                .append(LIKE)
                                .append(UPPER)
                                .append(BRKT_OPN)
                                .append(QUOTATION_MARK)
                                .append(JOKER)
                                .append(pair.getValue())
                                .append(JOKER)
                                .append(QUOTATION_MARK)
                                .append(BRKT_CLS)
                                .append(BRKT_CLS);                    
                    }
                }
            }
            fbsb.append(BRKT_CLS);
        }

        return (null == fbsb) ? BLANK : fbsb.toString();
    }

    /**
     * Gets the order by clause.
     *
     * @return the order by clause
     */
    public String getOrderByClause() {

        StringBuilder sbsb = null;

        if (!isSortByEmpty()) {
            Iterator<Map.Entry<String, SortOrder>> sbit = sortBy.getSortBys().entrySet().iterator();

            while (sbit.hasNext()) {
                Map.Entry<String, SortOrder> pair = sbit.next();
                if (null == sbsb) {
                    sbsb = new StringBuilder();
                    sbsb.append(ORDER_BY).append(pair.getKey()).append(SPACE).append(pair.getValue());
                } else {
                    sbsb.append(COMMA).append(pair.getKey()).append(SPACE).append(pair.getValue());
                }
            }
        }

        return (null == sbsb) ? BLANK : sbsb.toString();
    }


}