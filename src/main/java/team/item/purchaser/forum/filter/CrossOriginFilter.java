package team.item.purchaser.forum.filter;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CrossOriginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, DELETE, GET, OPTIONS, PATCH, HEAD");
        response.setHeader("Access-Control-Allow-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request, response);
    }
}
