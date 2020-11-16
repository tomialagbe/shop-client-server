package com.tomi.shop.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            servletResponse.setHeader("Access-Control-Allow-Headers", "*");
            servletResponse.setHeader("Access-Control-Allow-Methods", "*");
            if (servletRequest.getMethod().toLowerCase().equals("options")) {
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
