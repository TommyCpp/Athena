package com.athena.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tommy on 2017/5/16.
 */
@Service
public class PageableHeaderService {

    /**
     * Sets header.
     *
     * @param page     the page
     * @param request  the request
     * @param response the response
     * @throws MissingServletRequestPartException the missing servlet request part exception
     */
    public void setHeader(Page page, HttpServletRequest request, HttpServletResponse response) throws MissingServletRequestPartException {
        response.setHeader("X-Total-Count", Long.toString(page.getTotalElements()));
        String url = "";
        if (request.getQueryString() == null) {
            throw new MissingServletRequestPartException("search term");
        }
        if (request.getQueryString().contains("page") || request.getQueryString().contains("last_cursor")) {

        }

        response.setHeader("Links", "");
    }

    private Map<String, String> getQueryMap(String queryString) {
        Map<String, String> result = new HashMap<>();
        String[] keyValues = queryString.split("&");
        for (String keyValue : keyValues) {
            int divider = keyValue.indexOf("=");
            String key = keyValue.substring(0, divider);
            String value = keyValue.substring(divider + 1);
            if (result.containsKey(key)) {
                result.put(key, result.get(key) + "," + value);
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    private String getQuery(Map<String, String> map) {
        String result = "";
        boolean isFirst = true;
        for (String key :
                map.keySet()) {
            if (isFirst) {
                result += key + "=" + map.get(key);
                isFirst = false;
            } else {
                result += "&" + key + "=" + map.get(key);
            }
        }
        return result;
    }
}
