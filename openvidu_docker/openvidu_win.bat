rem Docker Desktop이 미리 실행되어 있어야 합니다.

docker run -p 4443:4443 --rm -e OPENVIDU_SECRET=MY_SECRET openvidu/openvidu-server-kms:2.22.0