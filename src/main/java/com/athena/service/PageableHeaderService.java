package com.athena.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        String url = String.valueOf(request.getRequestURL());
        String links = "";
        if (request.getQueryString() == null) {
            throw new MissingServletRequestPartException("search term");
        }
        if (request.getQueryString().contains("page") || request.getQueryString().contains("last_cursor")) {
//            If the page contains both page and last_cursor, we will use the page
            String query = this.cleanQuery(request.getQueryString());
            Integer pageNumber = page.getNumber();
            if (!page.isLast()) {
                // If not the last page
                links += "<" + url + "?page=" + (pageNumber + 1) + "&" + query + ">; rel=\"next\",";
            }
            if (!page.isFirst()) {
                // If not the first page
                links += "<" + url + "?page=" + (pageNumber - 1) + "&" + query + ">; rel=\"previous\",";
            }
            links += "<" + url + "?page=" + String.valueOf(page.getTotalPages() - 1) + "&" + query + ">; rel=\"last\",";
            links += "<" + url + "?page=" + String.valueOf(0) + "&" + query + ">; rel=\"first\"";

        }
        response.setHeader("Links", links);
    }

    private String cleanQuery(String rawQuery) {
        String[] keyValues = rawQuery.split("&");
        String result = "";
        for (String keyValue : keyValues) {
            int divider = keyValue.indexOf("=");
            String key = keyValue.substring(0, divider);
            if (!Objects.equals(key, "page") && !Objects.equals(key, "last_cursor")) {
                result += result.equals("") ? keyValue : "&" + keyValue;
            }
        }
        return result;
    }

}
