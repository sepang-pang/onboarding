# onboarding
바로인턴 9기 백엔드 개발 온보딩 과제

---

🔗 LINK : ~http://3.34.153.147:8080/swagger-ui/index.html~

---

# 📌 요구사항
- [X] Junit를 이용한 테스트 코드 작성법 이해
- [X] Spring Security를 이용한 Filter에 대한 이해
- [X] JWT와 구체적인 알고리즘의 이해
- [X] PR 날려보기
- [X] 리뷰 바탕으로 개선하기
- [X] EC2에 배포해보기

### 🔐 Spring Security 기본 이해
- [X] Filter란 무엇인가?(with Interceptor, AOP)
- [X] Spring Security란?

### 🔑 JWT 기본 이해
- [X] JWT란 무엇인가요?

### 🔄 토큰 발행과 유효성 확인
- [X] Access / Refresh Token 발행과 검증에 관한 테스트 시나리오 작성하기

### ⚙ 유닛 테스트 작성
- [X] JUnit를 이용한 JWT Unit 테스트 코드 작성해보기

### 🚀 백엔드 배포하기
- [X] 백엔드 유닛 테스트 완성하기

### 🖥 로직 작성
- [X] 백엔드 로직을 Spring Boot로

### 📝 회원가입 기능
API 요구사항
```java
PATH : /signup

Reqeust Message

{
    "username" : "JIN HO",
    "password" : "12341234",
    "nickname" : "Mentos"
}

Response Message

{
    "username": "JIN HO",
    "nickname": "Mentos",
    "authorities": [
        {
            "authorityName": "ROLE_USER"
        }
    ]
}

```

요구사항 구현

```java
PATH : /signup

Reqeust Message

{
    "username" : "system0000",
    "password" : "sysytem0000!",
    "nickname" : "system"
}

Response Message

{
    "username": "system0000",
    "nickname": "system",
    "authorities": [
        {
            "authorityName": "ROLE_USER"
        }
    ]
}
```

### 🔑 로그인 기능
API 요구사항
```java
PATH : /sign

Request Message

{
    "username" : "JIN HO",
    "password" : "12341234"
}

Response Message

{
    "token": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL"
}

```

요구사항 구현
```java
PATH : /sign

Request Message

{
    "username" : "system0000",
    "password" : "sysytem0000!"
}

Response Message

{
    "token": "Bearer eyJhbGciOiJIUzI1NiJ...."
}

```

### 🚀 배포해보기
- [X] AWS EC2 에 배포하기
- public IPv4 : `3.34.153.147`

### 🌐 API 접근과 검증
- [X] Swagger UI 로 접속 가능하게 하기

### 🤖 AI-assisted programming
- [X] AI 에게 코드리뷰 받아보기
- Tool : CodeRabbit-Ai

### 🔄 Refactoring
- [X] 피드백 받아서 코드 개선하기

### 🎯 마무리
- [X] AWS EC2 재배포하기

### 🏁 최종
- [X] 과제 제출
