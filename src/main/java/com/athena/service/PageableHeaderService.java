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

    public void setHeader(Page page, HttpServletRequest request, HttpServletResponse response) throws MissingServletRequestPartException {
        response.setHeader("X-Total-Count", Long.toString(page.getTotalElements()));
        String url = "";
        if(request.getQueryString() == null){
            throw new MissingServletRequestPartException("search term");
        }
        if (request.getQueryString().contains("page") || request.getQueryString().contains("last_cursor")) {

        }

        response.setHeader("Links", "");
    }

    public Map<String,String> getQueryMap(String queryString){
        Map<String, String> result = new HashMap<>();
        String[] keyValues = queryString.split("&");
        for(String keyValue:keyValues){
            int divider = keyValue.indexOf("=");
            result.put(keyValue.substring(0, divider), keyValue.substring(divider));
        }
        return result;
    }
}
