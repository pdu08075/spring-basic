package com.ddyy.springbasic.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ddyy.springbasic.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// Spring Web 보안 설정
// @Configurable:
// - Spring Bean으로 등록되지 않은 클래스에서 @Autowired를 사용할 수 있도록 하는 어노테이션
@Configurable
// @Configuraion:
// - 생성자나 '메서드'가 호출 시에 Spring Bean으로 등록되게 하는 어노테이션
@Configuration
//@EnableWebSecurity
// - Web Security 설정을 지원하는 어노테이션
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity security) throws Exception {

        // Class::method
        // - 메소드 참조, 특정 클래스의 메서드를 참조할 때 사용
        // - 매개변수로 메서드를 전달할 때 자주 사용

        security
            // Basic 인증 방식에 대한 설정
            // Basic 인증 방식 미사용으로 지정
            .httpBasic(HttpBasicConfigurer::disable)
            // Session:
            // 웹 애플리케이션에서 클라이언트에 대한 정보를 유지하기 위한 기술
            // 서버측에서 클라이언트 정보를 유지하는 방법
            // Session 유지 방식에 대한 설정
            // Session 유지를 하지 않겠다고 지정
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // CSRF (Cross-Site Request Forgery)
            // - 클라이언트가 자신의 의도와는 무관하게 공격행위를 하는 것
            // CSRF 취약점에 대한 대비 설정
            // CSRF 취약점에 대한 대비를 하지 않겠다고 지정
            .csrf(CsrfConfigurer::disable)

            // CORS (Cross Origin Resource Sharing)
            // - 서로 다른 출처 간의 데이터 공유에 대한 정책
            // - 출처 =  프로토콜, IP 주소, 포트

            // CORS 정책 설정
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 요청 URL의 패턴에 따라 인증이 필요한 작업인지 인가가 필요한 작업인지 지정하는 설정
            // - 모든 클라이언트가 접근할 수 있도록 허용
            // - 인증된 모든 클라이언트가 접근할 수 있도록 허용
            // - 인증된 클라이언트 중 특정 권한을 가진 클라이언트만 접근할 수 있도록 허용
            .authorizeHttpRequests(request -> request
                // requestMatchers(): URL 패턴, HTTP 메소드 + URL 패턴, HTTP 메소드 마다 접근 허용 방식을 지정하는 메서드
                // permitAll(): 모든 클라이언트가 접근할 수 있도록 지정
                // hasRole(권한): 특정 권한을 가진 클라이언트만 접근할 수 있도록 지정
                // authenticated(): 인증된 모든 클라이언트가 접근할 수 있도록 지정
                .requestMatchers("/anyone/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").authenticated()
                .requestMatchers(HttpMethod.GET).authenticated()
                .requestMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")       // 윗 둘의 코드들 처럼 패턴만 작성해도 되고, HTTPMethod만 작성해도 되고, 이 줄 처럼 둘 다 작성해도 됨
                // anyRequest(): requestMatchers로 지정한 메서드 혹은 URL이 아닌 모든 유형은 반드시 인증하도록 지정
                .anyRequest().authenticated()
                )
                // jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 등록
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);      // (지금 넣을 거, 이전 거)

        return security.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");    // 모든 출처에 대해 허락
        configuration.addAllowedHeader("*");   // 헤더의 모든 값들
        configuration.addAllowedMethod("*");

        // 위 세 줄이 정책들. 어떤 url에 대해 정책을 적용시킬 건지 의미

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 패턴에 대해 지정

        return source;

        // 이렇게 작성된 정책들의 집합을 다시 위의 .cors 안에 작성
    }
}
