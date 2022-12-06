package com.ssafy.ssauction.web.controller;
import com.ssafy.ssauction.web.dto.HelloResponseDto;
import com.ssafy.ssauction.web.dto.Hello.HelloPostRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public HelloResponseDto hello(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name,amount);
    }

    @PostMapping("/hello")
    public String helloPost(@RequestBody HelloPostRequestDto requestDto){
        System.out.println(requestDto.toString());
        return requestDto.toString();
    }
}

