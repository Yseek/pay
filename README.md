# 💳 Pay 프로젝트

![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

AI 기반 결제 사기 탐지 시스템을 포함한 **결제 백엔드**를 직접 구현하는 프로젝트입니다.

> ⚠️ **현재 열심히 개발 중입니다.**  
> 코드가 빠르게 바뀌고 있으며, 구현되지 않은 기능도 있습니다.

## 🎯 프로젝트 목표

- 실제 운영을 염두에 둔 **결제 시스템 아키텍처 설계**
- **AI 기반 사기 탐지 로직**을 백엔드에 통합
- **확장성**, **보안성**, **모듈화**를 고려한 백엔드 설계 및 구현


## 📚 API 문서화

Swagger를 사용하여 API 문서를 자동으로 생성하고 관리합니다.

### API 문서 확인하기

```
http://localhost:8082/swagger-ui/index.html
```

### 주요 기능

- API 엔드포인트 자동 문서화
- 요청/응답 모델 스키마 자동 생성
- JWT 인증 테스트 지원
- 인터랙티브 API 테스트 환경 제공

### 문서화된 API

- `User API`: 사용자 관련 엔드포인트
  - 사용자 프로필 조회
  - 계정 삭제

## 🛠️ 사용 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 21 |
| Framework | Spring Boot 3.2.5, Spring Security |
| 아키텍처 | MSA (Microservice Architecture) |
| 메시징 | Apache Kafka |
| 데이터베이스 | MySQL + JPA (Hibernate) |
| 인증/인가 | JWT (Access/Refresh Token), Redis 기반 세션 관리 |
| 통신 방식 | REST + gRPC 혼합 구조 |
| 인프라 | Docker, DevContainer, Redis |


## 🔁 서비스 간 통신 구조

```plaintext

🧾 [회원가입]

auth-service
    ├─> gRPC 호출 ─────────────> user-service
    │                           └─ 회원 정보 저장
    └─> Kafka 메시지 (user.signup) ──> point-service
                                └─ 가입 축하 포인트 지급


🔐 [로그인]

auth-service
    ├─> 사용자 인증 성공
    ├─> AccessToken + RefreshToken 생성
    └─> RefreshToken 저장 ────> Redis (key: RT:{email})


🔁 [AccessToken 재발급]

auth-service
    ├─> 클라이언트로부터 RefreshToken 전달받음
    ├─> Redis 에서 RT:{email} 확인 및 유효성 검증
    ├─> 새 AccessToken + 새 RefreshToken 발급
    └─> Redis 덮어쓰기 저장 (RT:{email})


🛒 [결제 요청]

payment-service
    ├─> 결제 요청 수신
    ├─> Kafka 메시지 (payment.pay) ──> point-service
    │                                 └─ 포인트 차감 처리
    └─> 결제 완료 응답 반환


🗑️ [회원탈퇴]

user-service
    ├─> gRPC 호출 ─────────────> auth-service            
    │                           └─ 인증 정보 삭제
    └─> Kafka 메시지 (user.del) ──────> point-service
                                └─ 포인트 기록 삭제

```
