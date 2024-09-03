#!/bin/bash
set -e

# Docker 컨테이너 중지 및 제거
docker-compose -f /home/ec2-user/app/docker-compose.yml down