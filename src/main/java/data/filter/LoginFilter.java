package data.filter;

import data.exception.UnauthorizedException;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter{

    @Autowired
    JwtProvider jwtProvider;
    // 로그인 검증에서 제외할 URI들
    private static final String[] whitelist = {
            "/",
            "/login/*",
            "/favicon.ico",
            "/test/*",
            "/comment/purchase/*",
            "/purchases",
            "/categories",
            "/college",
            "/college/*"
    }; // 추가 필요

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        /*
        * 검증 절차 (header)
        * 1-1. access token 이 있는 경우 -> 유효성 체크 -> 통과시 실행, 통과하지 못할시 1-2로
        * 1-2. access token 이 없는 경우 -> 엑세스 토큰이 없다는 애러 리턴
        */

        String accessToken = httpRequest.getHeader("Authorization");

        if(isLoginCheckPath(requestURI)) { // 검증해야하는 URI인 경우
            if (accessToken != null) { // 엑세스 토큰이 null이 아니면
                if (!jwtProvider.isValidToken(accessToken)) { // 유효하지 않은 토큰의 경우
                    throw new UnauthorizedException("유효하지 않은 토큰");
                }
            } else { // 엑세스 토큰이 없는 경우
                throw new UnauthorizedException("토큰 정보 없음");
            }
        }
        chain.doFilter(request, response);
    }

    /* 화이트리스트는 인증 체크를 하지 않음 */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
