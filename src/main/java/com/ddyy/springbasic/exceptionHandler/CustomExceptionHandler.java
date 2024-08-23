package com.ddyy.springbasic.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice: RestController에서 발생하는 특정 상황에 대해 처리하는 클래스로 지정
@RestControllerAdvice
public class CustomExceptionHandler {
    
    // @ExceptionHandler: 지정한 예외에 대해 직접 컨트롤할 수 있도록 하는 어노테이션
    // @ExceptionHandler(value={예외클래스, ...}) 형태로 작성해서 예외 배열(예외 클래스)를 받음
    @ExceptionHandler(value={MethodArgumentNotValidException.class})        // class 정의 자체를 넘겨줘야 돼서 '.class' 작성해야 함
    public ResponseEntity<String> customException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 입력입니다.");
    }


    // postman에서 validation의 body(row-JSON)을 모두 지워보니 'HttpMessageNotReadableException'라는 이름의 에러가 발생해 아래와 같이 예외 처리 연습
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<String> notReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("요청 데이터를 읽을 수 없습니다.");
    }

}
