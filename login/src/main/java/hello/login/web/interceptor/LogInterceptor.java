package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        //LogInterceptor 도 싱클톤 처럼 사용되기 때문에, 멤버변수를 사용하지 않고,
        //request에 담아두고 afterCompletion에서, request.getAttribute(LOG_ID) 를 통해 사용한다.
        request.setAttribute(LOG_ID,uuid); //공유하기위해

        //@RequestMapping: HandlerMethod
        //정적 리소스: ResourceHttpRequestHandler
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("[log INTERCEPTOR] REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true; //되야 handler 실제 호출
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[log INTERCEPTOR] postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);
        log.info("[log INTERCEPTOR] RESPONSE [{}][{}]", logId, requestURI);

        if (ex != null) {
            log.error("[log INTERCEPTOR] afterCompletion error!!", ex);
        }
    }
}
