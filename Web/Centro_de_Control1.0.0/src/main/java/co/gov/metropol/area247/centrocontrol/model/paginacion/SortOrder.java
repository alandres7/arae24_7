package co.gov.metropol.area247.centrocontrol.model.paginacion;

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
public enum SortOrder {
    /**
     * The asc.
     */
    ASC("ASC"),
    /**
     * The desc.
     */
    DESC("DESC");

    /**
     * The value.
     */
    private final String value;

    /**
     * Instantiates a new sort order.
     *
     * @param v the v
     */
    SortOrder(String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the sort order
     */
    public static SortOrder fromValue(String v) {
        for (SortOrder c : SortOrder.values()) {
            if (c.name().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return value;
    }
}