#!/bin/bash
set -e

# 이전 배포 파일 삭제
if [ -d "/home/ec2-user/app" ]; then
  sudo rm -rf /home/ec2-user/app/*
else
  sudo mkdir -p /home/ec2-user/app
fi

# Docker Compose 파일이 있는지 확인하고 Docker 컨테이너 정지 및 네트워크, 볼륨 제거
if [ -f "/home/ec2-user/app/docker-compose.yml" ]; then
  cd /home/ec2-user/app
  docker-compose down -v
  docker system prune -a -f
else
  echo "docker-compose.yml 파일이 없습니다. 컨테이너 정지 및 제거를 건너뜁니다."
fi

# 스크립트 파일에 실행 권한 부여
if [ -d "/home/ec2-user/app/scripts" ]; then
  chmod +x /home/ec2-user/app/scripts/*.sh
else
  echo "scripts 디렉토리가 없습니다. 실행 권한 부여를 건너뜁니다."
fi
