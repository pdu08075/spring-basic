package com.ddyy.springbasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ddyy.springbasic.entity.SampleTable1Entity;

// Respository 레이어:
// - 데이터베이스 작업을 수행하는 영역

// @Repository: 해당 클래스를 Sping Bean으로 등록하는 어노테이션, @Component와 동일
// - interface에 @Repository를 적용한 이유
// - JPA를 사용하면 JpaRepositiry 인터페이스를 상속받은 인터페이스는 JPA가 구현체를 자동으로 생성
@Repository
public interface SampleTable1Repository
// JpaRepository<T, ID> 인터페이스
// - JPA 기반의 Repository를 구현하는 주요 인터페이스
// - 기본 CRUD 기능 및 정렬 기능 등을 포함
// - JPA 기반의 Repository를 생성할 땐 필수로 상속해야 함
// - 매개타입 T: 해당 Repository가 어떤 Entity의 Repository인지 지정
// - 매개타입 ID: 해당 Repository가 사용하는 Entity의 기본키 타입 지정
extends JpaRepository<SampleTable1Entity, String> {
    
}
