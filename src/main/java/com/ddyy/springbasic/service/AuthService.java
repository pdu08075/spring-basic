package com.ddyy.springbasic.service;

import com.ddyy.springbasic.dto.PostUserRequestDto;
import com.ddyy.springbasic.dto.SignInRequestDto;

public interface AuthService {
    String signUp(PostUserRequestDto dto);
    String signIp(SignInRequestDto dto);
}
