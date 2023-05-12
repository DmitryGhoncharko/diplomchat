package by.webproj.carshowroom.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // set cache directives
        httpServletResponse.setHeader(HTTPCacheHeader.CACHE_CONTROL.getName(), "no-cache, no-store");
        httpServletResponse.setDateHeader(HTTPCacheHeader.EXPIRES.getName(), 0L);

        filterChain.doFilter(servletRequest, new AddExpiresHeaderResponse((HttpServletResponse) servletResponse));
    }
}
