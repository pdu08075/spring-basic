package com.ddyy.springbasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// http://localhost:4000/request-data/**
@RequestMapping("/request-data")
public class RequestDataController {
    // - GET, DELETE 처럼 Request Body가 존재하지 않고 URL로 데이터를 전송하는 메서드에서 Query String 방식으로 데이터를 가져오기 위해 사용하는 어노테이션

    // GET http://localhost:4000/request-data/request-param
    @GetMapping("/request-param")
    // http://localhost:4000/request-data/request-param?name=홍길동&age=30
    public String requestParam(
        // @RequestParam(name = "name") String name,
        @RequestParam() String name,
        @RequestParam(name = "age", required=false) Integer age     // 'required=false(필수 입력 안 해도 된다는 뜻)'에 대해 빈 값에는 자동으로 null이 들어가게 되는데, 데이터 타입이 int 등과 같이 참조형이 아닐 경우 null이 들어가지 못하므로 참조형 변수인 Integer로 변경
    ) {
        return "이름 : " + name + " 나이 : " + age;
    }
}
