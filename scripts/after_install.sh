#!/bin/bash
set -e

# Docker Compose를 사용해 이미지 빌드
cd /home/ec2-user/app
docker-compose build
