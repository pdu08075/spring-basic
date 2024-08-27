package com.ddyy.springbasic.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ddyy.springbasic.dto.PostSample1RequestDto;
import com.ddyy.springbasic.service.SampleService;

@Service
public class SampleServiceImplement implements SampleService {

    @Override
    public ResponseEntity<String> postSample1(PostSample1RequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body("성공");            // ResponseEntity의 상태가 실패일 경우 처리할 내용은 .status()에 작성 (201번 에러(=HttpStatus.CREATED)), 성공일 경우 처리할 내용은 .body()에 작성
    }
}
