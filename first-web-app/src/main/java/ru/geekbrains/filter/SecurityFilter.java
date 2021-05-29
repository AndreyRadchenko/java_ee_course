package ru.geekbrains.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin")
public class SecurityFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Security Filter initialized");
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if(request.getParameter("username") != null) {
            filterConfig.getServletContext().getRequestDispatcher("/http-servlet").forward(request, response);
            chain.doFilter(request, response);
        } else {
            filterConfig.getServletContext().getRequestDispatcher("/access_denied").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
