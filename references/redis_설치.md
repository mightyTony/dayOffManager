# [DB] 맥북 Redis 설치, 사용 가이드 

# 1. 맥에 redis 설치

```
* Homebrew 설치 가정 하에 

brew install redis
```
## 1-1. 설치된 redis 버전 확인
```markdown
redis-server --version
```

# 2. Redis 사용법
## 2-1-1. Redis 서버 실행

- launchd 사용 하여 백그라운드에서 실행
```markdown
redis-server
```
## 2-2. redis 클라이언트로 접속
redis-cli를 사용하여 Redis 서버에 연결하고 Redis 명령을 보내고 그 결과를 명령줄로 받을 수 있다.
```markdown
redis-cli
```

## 2-3. Redis 재실행
```markdown
brew services restart redis
```
## 2-4. 실행중인 Redis 중지
```markdown
brew services stop redis
```
## 2-5. 설치한 Redis 삭제
```markdown
brew uninstall redis
```

# 3. 레디스 기본 명령어
## 3-1. 데이터 저장
```markdown
set KEY VALUE

기존에 존재하는 key에 새로운 값을 set 하면 해당 key의 value가 수정 됩니다.
```

## 3-2. 데이터 조회
```markdown
get KEY / 해당 Key에 해당하는 값을 가져올 수 있다.
```
## 3-3. 키 조회
```markdown
keys PATTERN // keys PATTERN 명령어를 사용하여 저장된 key 들을 조회할 수 있다.
패턴으로 조회 할 수 있기 때문에 위와 같이 *을 사용하면 모든 key 들을 조회할 수 있다.
```
## 3-4. 데이터 삭제
```markdown
del KEY / key와 해당 key의 value를 삭제 할 수 있다.
```
## 3-5. 여러개를 한번에 삭제하고 싶다면
```markdown
del KEY1 KEY2
```
## 3-6. 전체 삭제
```markdown
flushall / 모든 키, 값 삭제 할 수 있다.
```
## 3-7. Key Name 수정
```markdown
rename KEY NEWKEY
```
## 3-8. 현재 데이터베이스 키 갯수 확인 
```markdown
dbsize
```
## 4. 레디스 cli 접속
redis cli -h <호스트> -p <포트>