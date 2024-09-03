package com.ddyy.springbasic.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

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
public class WebSecurityConfig {

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


    }
}
