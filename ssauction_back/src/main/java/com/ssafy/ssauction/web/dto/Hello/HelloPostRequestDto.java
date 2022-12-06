package com.ssafy.ssauction.web.dto.Hello;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HelloPostRequestDto {
    String name;
    int age;
    int userno;



    String desc;

    @Builder
    public HelloPostRequestDto(String name, int age, int userno, String desc) {
        this.name = name;
        this.age = age;
        this.userno = userno;
        this.desc = desc;
    }
    @Override
    public String toString() {
        return "HelloPostRequestDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", userno=" + userno +
                ", desc='" + desc + '\'' +
                '}';
    }


}

