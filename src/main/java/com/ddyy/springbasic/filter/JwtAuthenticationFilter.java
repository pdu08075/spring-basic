package com.ddyy.springbasic.filter;

import java.io.IOException;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ddyy.springbasic.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// filter:
// - 서버로직과 서블릿 사이에서 http request에 대한 사전 검사 작업을 수행하는 영역
// - filter에서 걸러진 request는 서블릿까지 도달하지 못하고 reject 됨

// OncePerRequestFilter 라는 추상클래스를 확장 구현하여 filter 클래스로 생성
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{      // OncePerRequestFilter를 확장 구현 할 시 반드시 doFilterInternal를 작성해야 함

    private final JwtProvider JwtProvider;    // validate를 호출하고 싶으면 인스턴스가 필요해서 의존성 주입

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {

            // 1. request 객체에서token 가져오기 // 아래 'parseBearerToken' 다 작성한 이 곳에 이어서 작성
            String token = parseBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 2. token 검증
            String subject = JwtProvider.validate(token);
            if (subject == null) {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
    
    // 1. request 객체 header 중 'Authorization' 필드의 값을 가져옴
    // 2. 'Authorization' 필드 값이 Bearer 형식인지 확인
    // 3. 'Authorization' 필드 값에서 토큰 추출
    private String parseBearerToken(HttpServletRequest request) {

        // 1. request 객체 header 중 'Authorization' 필드의 값을 가져옴
        String authorization = request.getHeader("Authorization");
        
        // 2. 'Authorization' 필드 값이 Bearer 형식인지 확인
        // 'Authorization' 필드 값의 존재 여부      // 외부에서 값을 받아오면 항상 존재 여부부터 확인
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;
        
        // 문자열이 "Bearer "로 시작하는지 여부
        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        // 3. 'Authorization' 필드 값에서 토큰 추출
        // Bearer qwdhukasjdfhajkd
        String token = authorization.substring(7);
        return token;
    }
}
