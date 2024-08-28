package com.ddyy.springbasic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ddyy.springbasic.entity.SampleUserEntity;

@Repository
public interface SampleUserRepository 
extends JpaRepository<SampleUserEntity, String> {

    // Query Method:
    // repository의 메서드 선언 시에 지정된 패턴에 따라 메서드명을 작성하면 JPA가 SQL을 만들어주는 방법 (오타가 제일 중요)
    // - findBy: 필드명을 기준으로 모든 컬럼을 조회할 때 사용, findBy 뒤에 필드명을 붙여서 작성, 필드명의 첫글자는 대문자이어야 함
    List<SampleUserEntity> findByName(String name);        // SELECT * FROM sample_user WHERE name; 와 같음. 0개에서 전체 n개까지 반환될 수 있으니 반환 타입은 리스트 * 조회하는 결과가 없다면 빈 리스트(null 아님)
    SampleUserEntity findByTelNumber(String telNumber);       // SELECT * FROM sample_user WHERE tel_number = ?; 와 같음. UNIQUE가 제약 조건이 있어 0개에서 1개까지 반환될 수 있 * 조회하는 결과가 없다면 null

    // - And / Or: and 연산 혹은 or 연산에 사용됨, 필드와 필드 사이에 사용
    // And Or 우선순위 주의
    List<SampleUserEntity> findByNameAndAddress(String name, String address);          // SELECT * FROM sample_user WHERE name = ? AND address = ?; 와 같음. 0개에서 전체 n개까지 반환될 수 있으니 반환 타입은 리스트

    // - Like, NotLike, StartingWith, EndingWith, Containing: Like 연산에 대하여 사용, 필드 뒤에 사용
    List<SampleUserEntity> findByAddressContaining(String address);        // SELECT * FROM sample_user WHERE address LIKE '%부산%'; 과 같음. Like 연산은 반환 타입이 무조건 List

    // - OrderBy: 정렬을 사용할 필드를 지정하여 Desc, Asc 지정
    List<SampleUserEntity> findByNameOrderByTelNumberDesc(String name);     // SELECT * FROM sample_user WHERE name = ? ORDER BY tel_number DESC; 와 같음
    
    // - existsBy: 조건에 해당하는 레코드가 존재하는지 여부 확인 시 사용
    boolean existsByName(String name);

    // - countBy: 조건에 해당하는 레코드의 개수 확인 시 사용
    int countByName(String name);

    // @Query:
    // - 쿼리 메서드의 한계를 극복하기 위해 사용하는 방식
    // - 쿼리 메서드가 사용할 수 없는 복잡한 쿼리를 직접 작성하는 방법

    // - JPQL (Java Persistence Query Language)
    // - 표준 SQL과 매우 흡사하지만 Entity 클래스와 Entity 필드로 쿼리를 작성하는 방법
    @Query(value = "SELECT u FROM user u WHERE u.name = ?1 AND  u.address = ?2")        // 별칭 필수
    List<SampleUserEntity> getJpql(String name, String address);

    @Query(value = "SELECT u FROM user u WHERE u.name = :name AND  u.address = :address")        // 별칭 필수
    List<SampleUserEntity> getJpql2(
        @Param("name") String name,
        @Param("address") String address
    );

    // Native SQL:
    // - 현재 RDBMS의 SQL 문법을 그대로 사용하는 방법
    // - @Query nativeQuery 속성을 반드시 true로 지정
    @Query(value =
    "SELECT * " +
    "FROM sample_user " +
    "WHERE name = :name " +
    "AND address = :address"
    , nativeQuery = true)
    List<SampleUserEntity> getNativeSql(           // ServiceImplement에서 실행
        @Param("name") String name,
        @Param("address") String address
    );
    
}
