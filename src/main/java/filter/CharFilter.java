package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by I322233 on 1/11/2016.
 */

public class CharFilter implements Filter {
    private String config;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig.getInitParameter("chars");

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--------------------------------filter-----------------");
        HttpServletRequest req;
        req = (HttpServletRequest) servletRequest;

        HttpServletResponse res;
        res = (HttpServletResponse) servletResponse;
        req.setCharacterEncoding("utf-8");
        res.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest, servletResponse);

    }

    public void destroy() {
        this.config = null;
    }
}
