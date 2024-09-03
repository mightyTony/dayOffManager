#!/bin/bash
# 서비스 검증
curl -f http://localhost:8080/healthcheck || exit 1
