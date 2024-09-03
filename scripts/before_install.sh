#!/bin/bash
## 필요한 도구 설치
#sudo yum update -y
#sudo yum install -y docker
#sudo systemctl start docker
#sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
#sudo chmod +x /usr/local/bin/docker-compose

# 실행 중인 도커 컨테이너 이미지 정지, 삭제
docker-compose down
docker rmi $(docker images -a -q)