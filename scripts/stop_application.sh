#!/bin/bash
set -e

# 스크립트 실행 디렉토리 변경
cd /home/ec2-user/app

# Docker 컨테이너 중지 및 제거
docker-compose down
