package com.ddyy.springbasic.entity;

import com.ddyy.springbasic.dto.PostUserRequestDto;

// import jakarta.persistence.Column;       // 테이블의 속성명과 서버의 변수명이 다를 경우 @Column을 사용하여 지정
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString       // 문자열로 반환
@Entity(name = "user")
@Table(name = "sample_user")
@Builder
public class SampleUserEntity {

    @Id
    private String userId;
    private String password;
    private String name;
    private String address;
    private String telNumber;

    public SampleUserEntity(PostUserRequestDto dto) {       // implement에서 생성자 (dto)를 못 받아서 직접 생성자
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.telNumber = dto.getTelNumber();


    }
}
