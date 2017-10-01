package com.athena.service.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
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
        String query = null;
        try {
            query = java.net.URLDecoder.decode(this.cleanQuery(request.getQueryString()), "utf-8");
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
            response.setHeader("Links", links);
        } catch (UnsupportedEncodingException e) {
            throw new MissingServletRequestPartException("search term");
        }

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
