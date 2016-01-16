package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dengrong on 2016/1/16.
 */
public class AdminAuthFilter implements Filter{

    String[] excludedPages;

    public void init(FilterConfig filterConfig) throws ServletException {
        String exc = filterConfig.getInitParameter("excludedPages");
        if(exc != null){
            excludedPages = exc.split(";");
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("admin filter");
        boolean isExculded = false;
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        for(String page:excludedPages){
            if(httpServletRequest.getServletPath().equals(page)){
                isExculded = true;
                break;
            }
        }
        if(!isExculded){
            HttpSession session = httpServletRequest.getSession();
            Object o = session.getAttribute("type");
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            if(o != null){
                int type = (Byte)o;
                if(type == 2){
                    filterChain.doFilter(servletRequest,servletResponse);
                }else{
                    httpServletResponse.sendRedirect("/tongbao/admin/login");
                    return;
                }
            }else{
                httpServletResponse.sendRedirect("/tongbao/admin/login");
                return;
            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }


//        doFilter(servletRequest,servletResponse,filterChain);
    }

    public void destroy() {

    }
}
