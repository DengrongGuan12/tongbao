package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by DengrongGuan on 2016/1/14.
 */
public class AuthFilter implements Filter{

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter auth");
    }

    public void destroy() {

    }
}
