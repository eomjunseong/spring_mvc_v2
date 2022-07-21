package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("[DOFILTER]로그 필터 !!!");

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try {
            log.info("[DOFILTER]로그 필터 : requestURI [{}][{}]", uuid, requestURI);

            //chain.doFilter();
            //다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출한다.
            //만약 이 로직을 호출하지 않으면 다음 단계로 진행되지 않는다

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("[DOFILTER]로그 필터 finally: [{}][{}]", uuid, requestURI);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[INIT] 로그 필터 INIT !!!");
    }

    @Override
    public void destroy() {
        log.info("[DESTORY] 로그 필터 DESTORY");
    }
}
