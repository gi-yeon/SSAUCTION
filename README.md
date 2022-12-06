
# ssauction (개인 화상 경매 서비스)

## ssauction 소개


## ✨ 주요 기능

---

- 서비스 설명 : 거래를 통해 판매자 구매자 모두 만족하는 금액으로 거래 가능한 곳
- 주요 기능 :
  - webRTC를 통한 실시간 입찰 기능
  - timer를 이용하여 마지막 입찰 후 timer가 종료될 경우 해당 금액으로 낙찰


### 🖥️ 개발 환경

---

🖱**Backend**

- IntelliJ
- spring boot 2.7.1
- spring-boot-jpa
- Spring Security
- Java 11
- AWS EC2
- mysql

🖱**Frontend**

- Visual Studio Code
- Vue.js 3
- element-ui

🖱**Web RTC**

- openvidu 2.22.0


🖱**CI/CD**

- aws ec2
- docker
- nginx
- jenkins


### ✨Jenkins를 이용한 CI/CD 구축 및 SSL 인증서 적용

---

Jenkins의 item을 이용하여 자동 배포를 구축하였습니다. 
프론트엔드인 Vue.js는 Nginx와 함께 docker image로 빌드하여 배포하였고, 백엔드 및 mysql, openvidu 또한 docker container로 배포하였습니다. 그리고 Nginx와 letsencrypt를 이용하여 ssl 인증서를 적용하였고, 프론트엔드는 443(https)로 프록시로 분기시켰고 백엔드는 /ssauction 경로로 프록시를 걸어줬습니다.
