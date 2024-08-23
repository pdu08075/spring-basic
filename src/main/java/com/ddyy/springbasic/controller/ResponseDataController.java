package com.ddyy.springbasic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
// HTTP * localhost:4000/response-data/**
@RequestMapping("/response-data")
public class ResponseDataController {

    // HTTP GET localhost:4000/response-data/{이름}
    @GetMapping("/{name}")
    public ResponseDto responseBodyMethod(
        @PathVariable("name") String name
    ) {
        ResponseDto response = new ResponseDto(name, new Date());
        return response;
    }
    
    // ResponseEntity: 
    // - Response의 header, status, code, status message, data를 직접 조작할 수 있는 클래스
    // - 반환 타입으로 ResponseEntity 타입으로 지정
    //   ResponseEntity 클래스는 제너릭으로 response body 데이터의 타입을 전달해야 함
    @GetMapping("/response-entity/{name}")
    public ResponseEntity<ResponseDto> responseEntity(
        @PathVariable("name") String name
    ) {
        ResponseDto response = new ResponseDto(name, new Date());
        // return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        // ResponseEntity의 상태코드와 메세지를 'status(HttpStatus.BAD_REQUEST)'(400 반환)'으로 지정하고 .body(response)로 무엇을 내보낼지 작성
    }
    

}

// name 문자열 데이터
// date 날짜 데이터
// {
//     "name": "홍길동",
//     "date": "2024-08-23~~~"
// }
@Getter
@AllArgsConstructor
class ResponseDto {
    private String name;
    private Date date;
}