#!/bin/bash
# 필요한 도구 설치
sudo yum update -y
sudo yum install -y docker
sudo systemctl start docker
sudo systemctl enable docker

# Docker Compose 설치
if [ ! -f /usr/local/bin/docker-compose ]; then
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
fi

## 실행 중인 모든 도커 컨테이너 정지 및 제거
#docker-compose down
#docker rm $(docker ps -a -q)
#
## 사용하지 않는 도커 이미지 삭제
#docker rmi $(docker images -aq) --force


