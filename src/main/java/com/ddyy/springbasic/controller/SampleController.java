package com.ddyy.springbasic.controller;

// import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddyy.springbasic.dto.PostSample1RequestDto;
import com.ddyy.springbasic.service.SampleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController     // JSON만 받을 예정
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;
    
    @PostMapping("")
    public ResponseEntity<String> postSample1(      // 직접 조작하려 할 때 반환 타입을 ResponseEntity로 지정. 이는 제너릭 필수. 여기 작성한 ResponseEntity ~ 를 service 단에서 그대로 작성하면 됨
        @RequestBody @Valid PostSample1RequestDto requestBody)       // POST, PUT 등으로 정보를 받아올 떄 requestBody 사용, 문법은 '@RequestBody 타입 매개변수명' / @Valid 작성하면 Dto에서 미리 지정한 유효성 검사 실행
        {
            ResponseEntity<String> response = sampleService.postSample1(requestBody);
            return response;
    }

    @DeleteMapping("/{sampleId}")
    public ResponseEntity<String> deleteSample1 (
        @PathVariable ("sampleId") String sampaleId
    ) {
        ResponseEntity<String> response = sampleService.deleteSample1(sampaleId);
        return response;
    }

    @GetMapping("")
    public ResponseEntity<String> queryMethod() {
        ResponseEntity<String> response = sampleService.queryString();
        return response;
    }

    @GetMapping("/jwt/{name}")
    public String getJwt(
        @PathVariable("name") String name
    ) {
        String response = sampleService.getJwt(name);
        return response;
    }

}
