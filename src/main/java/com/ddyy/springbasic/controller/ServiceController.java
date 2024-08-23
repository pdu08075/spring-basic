package com.ddyy.springbasic.controller;

import java.util.Date;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddyy.springbasic.service.BasicService;

// import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/service")
// @AllArgsConstructor     // contoller 생성자를 따로 작성하지 않아도 자동 생성되기에 'private BasicService basicService;'만 쓰고 @Autowired와 public ServiceController(BasicService basicService) {this.basicService = basicService;}를 작성할 필요가 없음
@RequiredArgsConstructor
public class ServiceController {

    // 의존성 주입(DI):
    // - 해당 모듈에 필요한 의존성을 외부(클래스의 인스턴스를 생성하는 위치)에서 주입
    // - 생성자를 통한 의존성 주입, setter 메서드를 통한 의존성 주입, 필드를 통한 의존성 주입
    // - spring framework에서 권장하고 있는 의존성 주입 방법은 생성자를 통한 의존성 주입(의존성이 주입되지 않은 상태가 존재할 수 없기 때문)
    // - spring framework에서 IoC을 통해 의존성을 주입할 땐 주입할 인스턴스의 클래스가 Spring bean으로 등록되어 있어야 함 (annotension componenet가 걸려있어야 된다는 의미)
    // private BasicService basicService;

    // @Autowired: 등록된 Spring Bean을 제어 역전을 통해 의존성 주입을 할 수 있도록 하는 어노테이션
    // 1. 필드 객체
    // @Autowired
    // private BasicService basicService;

    // 2. setter 메서드
    // private BasicService basicService;

    // @Autowired      // 지우면 500 에러(nullpointException) 발생 - 의존성을 만들어 놓았지만 주입을 하지 않아서 basicService가 null로 잡히기 때문
    // public void setBasicService(BasicService basicService) {
    //     this.basicService = basicService;
    // }

    // 3. 생성자
    private final BasicService basicService;        // *추상화에 의존하도록 만들어줘야 된다는 점 주의*

    // 생성자를 통한 의존성 주입 방식에는 @AutoWired를 걸어줄 필요가 없음 (springboot 2.7 버전 이상은 지워도 정상 작동)
    // @Autowired
    // public ServiceController(BasicService basicService) {
    //     this.basicService = basicService;
    // }

    private Date today;

    @GetMapping("")
    public ResponseEntity<String> getService() {        // <Strings> import 안 해도 됨
        return basicService.getService();
    }

}