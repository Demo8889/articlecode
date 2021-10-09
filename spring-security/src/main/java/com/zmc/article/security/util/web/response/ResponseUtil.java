package com.zmc.article.security.util.web.response;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {

    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String CHARSET = ";charset=";

    private ResponseUtil() {
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static HttpServletResponse setContentType(HttpServletResponse response, String contentType) {
        if (response == null) {
            response = getResponse();
        }
        response.setContentType(contentType);
        return response;
    }


    public static HttpServletResponse setContentTypeDefaultCharset(String contentType) {
        String charset = "UTF-8";
        HttpServletResponse response = setContentType(null, contentType + CHARSET + charset);
        response.setCharacterEncoding(charset);
        return response;
    }


    public static HttpServletResponse write(HttpServletResponse response, String text) {
        if (response == null) {
            response = getResponse();
        }
        if (text == null) {
            text = "";
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static HttpServletResponse writeJson(String text) {
        return write(setContentTypeDefaultCharset(ResponseUtil.CONTENT_TYPE_JSON), text);
    }

}
