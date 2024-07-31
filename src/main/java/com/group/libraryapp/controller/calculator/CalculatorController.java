package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //주어진 class를 controller(API의 입구)로 등록한다
public class CalculatorController {

//    @GetMapping("/add") // 아래 함수를 HTTP Method가 GET이고 HTTP path가 /add인 API로 지정한다
//    public int addTwoNumbers(
//            @RequestParam int num1, //@RequestParam : 주어지는 쿼리를 함수 파라미터에 넣는다
//            @RequestParam int num2
//    ){
//        return num1 + num2;
//    }

    @GetMapping("/add") // 아래 함수를 HTTP Method가 GET이고 HTTP path가 /add인 API로 지정한다
    public int addTwoNumbers(CalculatorAddRequest request){
        return request.getNum1() + request.getNum2();
    }

    @PostMapping("/multiply")
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNum1() * request.getNum2();
    }

}
