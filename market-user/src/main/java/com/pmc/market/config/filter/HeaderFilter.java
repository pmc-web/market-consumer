package com.pmc.market.config.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class HeaderFilter implements Filter {

    private List<String> excludeUrls;

    public void setExcludeUrls(){
        this.excludeUrls.add("");
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getServletPath();
        if (!excludeUrls.contains(path)) {
            final HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader(
                    "Access-Control-Allow-Headers",
                    "X-Requested-With, Content-Type, Authorization, X-XSRF-token"
            );
            res.setHeader("Access-Control-Allow-Credentials", "false");
        }
        chain.doFilter(request, response);
    }

}