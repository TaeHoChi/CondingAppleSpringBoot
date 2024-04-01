//package com.apple.shop;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//// Rest API를 활용해서 코드 어딘가에서 에러가 나면
//// 여기서 집어 넣는다.
//@ControllerAdvice
//public class MyExcecptionHandler {
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<String> handler1(){
//        return ResponseEntity.status(400).body("에러");
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handler(){
//        return ResponseEntity.status(400).body("에러");
//    }
//
//}
