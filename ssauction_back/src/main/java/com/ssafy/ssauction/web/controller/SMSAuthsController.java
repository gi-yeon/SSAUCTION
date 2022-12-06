package com.ssafy.ssauction.web.controller;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSAuthsController {

    final DefaultMessageService messageService;

    public SMSAuthsController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSDPSA7LRS9NHSM", "QGB5CCFBGQON2WT5LDXHBYUJRHSFQ8QG", "https://api.coolsms.co.kr");
    }

    /**
     * 단일 메시지 발송 예제
     */
//    @PostMapping("/users/sendSMS/{userPhoneNo}")
//    public SingleMessageSentResponse sendOne(@PathVariable String userPhoneNo) {
//            Message message = new Message();
//        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
//        Integer randCode = (int)(Math.random()*1000000);  // 6자리 난수 생성
//        message.setFrom("01047280165");
//        message.setTo(userPhoneNo);
//        message.setText("[Web 발신] SSAFY 7기 공통 프로젝트 A806 - 인증코드:" + randCode);
//
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//        System.out.println(response);
//
//        return response;
//    }

    @PostMapping("/users/sendSMS/{userPhoneNo}")
    public ResponseEntity<String> sendOne(@PathVariable String userPhoneNo) {
            Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        Integer randCode = (int)(Math.random()*1000000);  // 6자리 난수 생성
        message.setFrom("01047280165");
        message.setTo(userPhoneNo);
        message.setText("[Web 발신] SSAFY 7기 공통 프로젝트 A806 - 인증코드:" + randCode);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return new ResponseEntity<>(randCode.toString(), HttpStatus.OK);
    }
}