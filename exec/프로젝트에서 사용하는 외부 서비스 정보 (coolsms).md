## 프로젝트에서 사용하는 외부 서비스 정보 (coolsms)



#### 0. 개요

- Java Spring (Gradle)을 활용해 API 통신으로 문자메시지 발송
- 일반 문자메시지(SMS) 건당 20원



#### 1. 가입

- coolsms API를 사용하기 위해 회원가입 필수
- 대쉬보드에서 충전금액 관리
- 발송내역 및 성공/실패 여부 조회 가능
- 날짜별 및 기간별 사용량 통계 확인 가능



#### 2. 활용 (API 연동)

- coolsms에서 제공하는 Java controller 코드를 수정하여 사용

  ```java
  // 단일 메시지 발송 코드 예시 (coolsms 제공)
  // import 패키지 부분 생략
  
  @PostMapping("/send-one")
  public SingleMessageSentResponse sendOne() {
      Message message = new Message();
      // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
      message.setFrom("발신번호 입력");
      message.setTo("수신번호 입력");
      message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다.");
  
      SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
      System.out.println(response);
  
      return response;
  }
  ```

  ```java
  // 실제 구현한 코드
  // import 패키지 부분 생략
  
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
  ```

- coolsms에서 전화번호 인증 절차를 완료해야 해당 발신번호로 SMS 전송 가능
  (발신번호가 미인증인 경우 SMS발송 불가)

- 발신번호는 인증된 번호를 고정으로 사용하고, 수신번호는 유저 입력값을 삽입

- 텍스트란에는 안내문구와 함께 인증번호로 사용할 6자리 난수를 파라미터로 함께 전송



#### 3. 관리 및 기타사항

- 충전금액 부족시 SMS 전송 실패 (자동충전 가능)
- 유효한 전화번호 양식이 아닌 경우 발송 제한