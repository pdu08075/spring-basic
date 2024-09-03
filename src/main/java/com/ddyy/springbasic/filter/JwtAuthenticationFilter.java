package com.ddyy.springbasic.filter;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ddyy.springbasic.entity.SampleUserEntity;
import com.ddyy.springbasic.provider.JwtProvider;
import com.ddyy.springbasic.repository.SampleUserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// filter:
// - 서버로직과 서블릿 사이에서 http request에 대한 사전 검사 작업을 수행하는 영역
// - filter에서 걸러진 request는 서블릿까지 도달하지 못하고 reject 됨
@Component
@RequiredArgsConstructor
// OncePerRequestFilter 라는 추상클래스를 확장 구현하여 filter 클래스로 생성
public class JwtAuthenticationFilter extends OncePerRequestFilter{      // OncePerRequestFilter를 확장 구현 할 시 반드시 doFilterInternal를 작성해야 함          // JwtAuthenticationFilter 이해 필수!!!!!

    private final JwtProvider JwtProvider;    // validate를 호출하고 싶으면 인스턴스가 필요해서 의존성 주입

    private final SampleUserRepository sampleUserRepository;

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

            // 3. 데이터베이스에 존재하는 유저인지 확인 // 상단에 final ~ repoeitory 작성
            // SampleUserEntity userEntity = sampleUserRepository.findByUserId(subject);
            // if (userEntity == null) {
            //     filterChain.doFilter(request, response);
            //     return;
            // }

            // 4. 접근주체의 권한 리스트 지정
            List<GrantedAuthority> roles = AuthorityUtils.NO_AUTHORITIES;
            if (subject.equals("qwer1234")) {
                roles = new ArrayList<>();      // 새로 만들 떄 이렇게 넣어줘야 함
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));      // 소괄호 안에는 권한의 이름을 작성하는데, 이는 무조건 'ROLE_'로 시작해야 함
            }

            // 5. principal에 대한 정보를 controller로 전달하기 위해 context에 저장

            // 5.1 인증된 사용자 객체를 생성
            // UsernamePasswordAuthenticationToken(사용자정보, 비밀번호, 권한);     
            AbstractAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(subject, null, roles);       // 비밀번호는 사용하지 않아서 null로 작성

            // 5.2 인증 정보에 상세 리퀘스트를 등록'
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 5.3 빈 security context 생성
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

            // 5.4 security context 생성한 인증 정보 토큰을 등록
            securityContext.setAuthentication(authenticationToken);

            // 5.5 생성한 security context를 등록
            SecurityContextHolder.setContext(securityContext);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // 6. 다음 필터에 request와 response를 전달
        filterChain.doFilter(request, response);

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
