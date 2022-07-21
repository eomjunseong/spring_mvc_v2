package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout","/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("[LOGIN DOFILTER 진입] {}", requestURI);
            if(isLoginCheckPath(requestURI)){ //True면 체크해야되는 경로
                log.info("[LOGIN DOFILTER 인증시작] {}", requestURI);
                HttpSession session = httpRequest.getSession();
                //쿠키에 sessionId가 있는 지 없는지 체크
                if(session==null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                    //없다
                    log.info("[LOGIN DOFILTER 인증 실패 유저] {}", requestURI);
                    //처리후 이동을 위해
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    return;//-->finally 항상 호출
                }
                log.info("[LOGIN DOFILTER 인증 해야하는 유저] {}", requestURI);
                //있다 .
                //유효성 검사는 ?
            }

            chain.doFilter(request, response);
        }catch (Exception e){
            throw e; //톰캣까지 예외를 보냄
        }finally{
            log.info("[LOGIN DOFILTER 인증 종료] {}", requestURI);
        }
    }
    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
        //1) 일치 한다 ->true-> false
        //2) 일치 X -->false --> true
        //3) True면 체크해야되는 경로
    }

}
