package com.ddyy.springbasic.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddyy.springbasic.dto.PostUserRequestDto;
import com.ddyy.springbasic.dto.SignInRequestDto;
import com.ddyy.springbasic.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("")
    public String getAuth(
        // @AuthenticationPrincipal:
        // 인증 필터에서 security context에 등록된 해당 request의 접근 주체의 정보를 가져오는 어노테이션
        // 인증이 성공적으로 완료된 접근 주체일 경우 authentication token의 principal 값을 가져옴
        // 인증에 실패한 접근 주체일 경우 anonymousUser를 반환함, 단 해당 url 패턴이 permitAll일 때만 가져옴 
        @AuthenticationPrincipal String principal
    ) {
    return principal;
    }

    @PostMapping("/sign-up")
    public String signUp(
        @RequestBody @Valid PostUserRequestDto requestBody
    ) {
        String response = authService.signUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    public String signIn(
        @RequestBody @Valid SignInRequestDto requestBody
    ) {
        String response = authService.signIn(requestBody);
        return response;
    }
}
