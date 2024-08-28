package com.ddyy.springbasic.entity;

// import jakarta.persistence.Column;       // 테이블의 속성명과 서버의 변수명이 다를 경우 @Column을 사용하여 지정
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sample_user")
@Table(name = "sample_user")
public class SampleUserEntity {

    @Id
    private String userId;
    private String password;
    private String name;
    private String address;
    private String telNumber;
}
