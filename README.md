# 💳 Pay 프로젝트

AI 기반 결제 사기 탐지 시스템을 포함한 **결제 백엔드**를 직접 구현하는 프로젝트입니다.

> ⚠️ **현재 열심히 개발 중입니다.**  
> 코드가 빠르게 바뀌고 있으며, 구현되지 않은 기능도 있습니다.

## 🎯 프로젝트 목표

- 실제 운영을 염두에 둔 **결제 시스템 아키텍처 설계**
- **AI 기반 사기 탐지 로직**을 백엔드에 통합
- **확장성**, **보안성**, **모듈화**를 고려한 백엔드 설계 및 구현


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


🗑️ [회원탈퇴]

user-service
    ├─> gRPC 호출 ─────────────> auth-service            
    │                           └─ 인증 정보 삭제
    └─> Kafka 메시지 (user.del) ──────> point-service
                                └─ 포인트 기록 삭제

```
