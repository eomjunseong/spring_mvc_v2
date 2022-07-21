package hello.login.web.interceptor;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //URI 꺼내두고, ex) ?redirectUrl=---
        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute("logId");
        log.info("로그 값 확인 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!{}",logId);
        log.info("쿠키에 사용자 있어야하는 영역, 검사 시작 ",requestURI);

        HttpSession session = request.getSession(false); // 새로생성하진 않고 있으면 가져온다.

        if(session==null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            log.info("쿠키에 사용자 없으면 로그인 시킴 ");

            response.sendRedirect("/login?redirectURL="+requestURI);
            return false;
        }

        log.info("쿠키 SessionID가 있는 상태(유효성검증은 안하고 마무리 상태) ");


        return true;
    }


}
