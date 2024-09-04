package com.ddyy.springbasic.service;

import com.ddyy.springbasic.dto.PostUserRequestDto;

public interface AuthService {
    String signUp(PostUserRequestDto dto);
}
