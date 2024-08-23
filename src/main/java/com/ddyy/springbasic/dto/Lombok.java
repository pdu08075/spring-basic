package com.ddyy.springbasic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
// import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Lombok 라이브러리
// - getter 메서드, setter 메서드, 생성자, toString 등 자주 사용되지만 반복적인 작업을 자동으로 생성하는 라이브러리
// - 프로젝트가 Lombok 라이브러리 의존성을 가지고 있어야 함
// - 대부분의 IDE에서 Lombok 어노테이션을 읽을 수 없기 때문에 Lombok 추가 확장이 필요함

// @Getter: getter 메서드 생성
// @Setter: setter 메서드 생성
// @toString: toString 메서드 생성 (필드 이름과 값을 문자열로 표현)
// @NoArgsConstructor: 빈 생성자 생성
// @AllArgsConstructor: 모든 멤버 변수를 초기화하는 생성자 생성 
// @RequiredArgsConstructor: 필수 멤버 변수를 초기화하는 생성자 생성
// @Data: @Getter, @Setter, @RequiredArgsConstructor 등을 하나로 합친 어노테이션
@Getter
@Setter
@AllArgsConstructor
// @NoArgsConstructor
@RequiredArgsConstructor
public class Lombok {

    final private String field1;
    final private String field2;
    private String field3;
    // 기본형 boolean일 경우 getter 메서드 이름은 isXXX()
    private boolean field4;
    // 참조형 Boolean일 경우 getter 메서드 이름은 getXXX()
    private Boolean field5;
}
