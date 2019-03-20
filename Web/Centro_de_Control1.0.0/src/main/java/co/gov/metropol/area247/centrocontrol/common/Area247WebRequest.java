package co.gov.metropol.area247.centrocontrol.common;

import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by andres on 6/6/17.
 */
public class Area247WebRequest {

    private NativeWebRequest nativeWebRequest;

    private boolean ajaxRequest = false;

    public NativeWebRequest getNativeWebRequest() {
        return nativeWebRequest;
    }

    public Area247WebRequest setNativeWebRequest(NativeWebRequest nativeWebRequest) {
        this.nativeWebRequest = nativeWebRequest;
        return this;
    }

    public boolean isAjaxRequest() {
        return ajaxRequest;
    }

    public Area247WebRequest setAjaxRequest(boolean ajaxRequest) {
        this.ajaxRequest = ajaxRequest;
        return this;
    }
}
