package filter;

import manager.UserManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DengrongGuan on 2016/1/14.
 */
public class AuthFilter implements Filter{
    UserManager userManager = UserManager.getInstance();

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter auth");
//        System.out.println(servletRequest.getParameter("token"));
//        servletRequest.setAttribute("userId",1);
        String token = servletRequest.getParameter("token");
        int userId = userManager.getUserIdByKey(token);
        if(userId == 0){
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
