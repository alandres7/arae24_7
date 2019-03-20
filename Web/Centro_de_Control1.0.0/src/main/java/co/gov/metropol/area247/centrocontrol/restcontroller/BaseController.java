package co.gov.metropol.area247.centrocontrol.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

public abstract class BaseController {

    public static final String WEB_PARAM_SUCCESS_MESSAGE = "successMsg";
    public static final String WEB_PARAM_INFO_MESSAGE = "infoMsg";
    public static final String WEB_PARAM_ERROR_MESSAGE = "errorMsg";
    public static final String WEB_PARAM_WARNING_MESSAGE = "warningMsg";
    public static final String WEB_PARAM_ERROR_FIELDS = "errorFields";

    @Autowired
    protected MessageSource messageSource;

    public String getMessage(String key) {
        return getMessage(key, null, Locale.getDefault());
    }

    public String getMessage(String key, Object[] args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

    public String getMessage(String key, String arg) {
        return this.messageSource.getMessage(key, new Object[]{arg}, Locale.getDefault());
    }

    public String getMessage(String key, Object[] args, Locale locale) {
        return this.messageSource.getMessage(key, args, locale);
    }

}
