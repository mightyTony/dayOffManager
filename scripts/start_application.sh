#!/bin/bash
set -e

# Docker Compose를 사용해 애플리케이션 시작
cd /home/ec2-user/app
docker-compose up -d
