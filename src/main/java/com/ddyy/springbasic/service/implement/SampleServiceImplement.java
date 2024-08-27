package com.ddyy.springbasic.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ddyy.springbasic.dto.PostSample1RequestDto;
import com.ddyy.springbasic.entity.SampleTable1Entity;
import com.ddyy.springbasic.repository.SampleTable1Repository;
import com.ddyy.springbasic.service.SampleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleServiceImplement implements SampleService {

    private final SampleTable1Repository sampleTable1Repository;

    @Override
    public ResponseEntity<String> postSample1(PostSample1RequestDto dto) {

        String sampleId = dto.getSampleId();
        Integer sampleColumn = dto.getSampleColumn();

        // SELECT (SQL : SELECT)
        // 1. repository를 이용하여 조회 (findAll, findById)
        // SampleTable1Entity existEntity = sampleTable1Repository.findById(sampleId).get();       // 조회 작업 수행   * Entity 받는 방식(실제로 잘 안 쓰는 방식)
        // 2. repository를 이용하여 조회 (existsById)
        boolean isExisted = sampleTable1Repository.existsById(sampleId);     // 괄호 안의 값의 존재 여부 따진 후 있으면 true, 없으면 false를 전달
        if (isExisted) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 기본키입니다.");

        // CREATE (SQL : INSERT)
        // 1. Entity 클래스의 인스턴스 생성
        SampleTable1Entity entity = new SampleTable1Entity(sampleId, sampleColumn);

        // 2. 생성한 인스턴스를 repository를 이용하여 저장
        // save(): 저장
        // - 만약에 Primary key가 동일한 레코드가 존재하지 않으면 레코드 생성
        // - 동일한 레코드가 존재하면 수정      * 원치 않는 수정이 발생할 수 있으니 주의!
        sampleTable1Repository.save(entity);        // save() 메서드는 선언하지 않아도 jk repository 에서 제공

        return ResponseEntity.status(HttpStatus.CREATED).body("성공");            // ResponseEntity의 상태가 실패일 경우 처리할 내용은 .status()에 작성 (201번 에러(=HttpStatus.CREATED)), 성공일 경우 처리할 내용은 .body()에 작성
    }
}
