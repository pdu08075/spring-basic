package com.ddyy.springbasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddyy.springbasic.dto.Validation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        @RequestParam("name") String name,
        @RequestParam(name = "age", required=false) Integer age     // 'required=false(필수 입력 안 해도 된다는 뜻)'에 대해 빈 값에는 자동으로 null이 들어가게 되는데, 데이터 타입이 int 등과 같이 참조형이 아닐 경우 null이 들어가지 못하므로 참조형 변수인 Integer로 변경
    ) {
        return "이름 : " + name + " 나이 : " + age;
    }

    // @PathVariable():
    // 모든 HTTP 메서드에서 URL의 특정 패턴에 따라 데이터를 추출
    // GET http://localhost:4000/request-data/path_variable/*/*
    @GetMapping({
        "/path-variable/{var}/{str}",
        "/path-variable/{var}/",
        "/path-variable/{var}"
    })
    public String pathVariable(
        @PathVariable(name = "var") String var,
        @PathVariable(name = "str", required = false) String str
    ) {
        return "읽은 경로 변수 : " + var + ", " + str;
    }

    // ! 주의
    // 경로 변수를 사용하여 URL 패턴을 작성할 때
    // 겹치는 패턴이 존재하는지 잘 확인해야 함
    @GetMapping("/path-variable/other")
    public String otherPathVariable () {
        return "other 메서드 호출";
    }
    
    @GetMapping("/path-variable/{var}/another")
    public String anotherPathVariable1 (
        @PathVariable("var") String var
    ) {
        return "another1";
    }
    
    @GetMapping("/path-variable/another/{var}")
    public String anotherPathVariable2 (
        @PathVariable("var") String var
    ) {
        return "another2";
    }
    
    //@ RequestBody() :
    // - POST, PUT, PATCH처럼 Request Body로 데이터를 전송하는 메서드에서 데이터를 읽기 위한 방법
    @PostMapping("/request-body")
    public String requestBody(
        // @RequestBody String requestBody      // Dto 하기 전에 작성함. 일단 주석 처리
        @RequestBody SampleDto requestBody
    ) {

        return "Request Body data: " + requestBody.getName() + ", " + requestBody.getAge();
    }

    @PostMapping("/validation")
    public String validation(
        @RequestBody @Valid Validation requestBody
    ) {
        return "정상";
    }

}
// DTO (Data Transfer object)
// - 데이터를 서로 다른 계층 간에 전송하기 위한 객체. 목적 자체가 데이터 전송
// - 캡슐화가 되어 있음, 비즈니스 로직은 포함하지 않고 private 필드와 생성자, getter, setter만 존재
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class SampleDto {
    private String name;
    private int age;
}