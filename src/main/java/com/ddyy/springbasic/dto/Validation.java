package com.ddyy.springbasic.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// client request body 데이터의 유효성 검사
// - spring-boot-starter-validation 라이브러리 사용
// - 스프링 프레임워크에서 제공하는 유효성 검사 인터페이스 라이브러리
// - 클라이언트가 서버 측에 데이터를 전송할 때 유효셩을 검사하고 정확한 데이터만 받을 수 있도록 도움을 줌
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Validation {

    // @NotNull: null을 허용하지 않음
    @NotNull
    private String notNull;
    
    // @NotEmpty: 문자열 타입에서 null과 빈문자열("")을 허용하지 않음
    @NotEmpty
    private String notEmpty;
    
    // @NotBlank: 문자열 타입에서 null, 빈문자열(""), 공백문자열("  ")을 허용하지 않음
    @NotBlank
    private String notBlank;
    
    // client로부터 데이터를 받을 때는 기본형 데이터 타입을 쓰지 않는 게 좋음
    @NotNull
    // @NotBlank -> 쓰면 안 됨 문자열만 검사할 수 있음
    private Integer number;

    // @Length(min=?, max=?): 문자열 타입에서 길이의 최소, 최대를 지정
    @Length(min = 4)
    @NotNull
    private String length;
}
