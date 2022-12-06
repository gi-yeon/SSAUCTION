# Dockerfile

# nginx 이미지를 사용합니다. 뒤에 tag가 없으면 latest 를 사용합니다.
FROM nginx

# root 에 빌드 결과가 들어갈 폴더를 생성
RUN mkdir -p /var/www

# host pc의 현재경로의 dist 폴더를 workdir 의 빌드 결과가 들어갈 폴더로 복사
ADD ./dist /var/www

# 80 포트 오픈
EXPOSE 80

# container 실행 시 자동으로 실행할 command. nginx 시작함
CMD ["nginx", "-g", "daemon off;"]
