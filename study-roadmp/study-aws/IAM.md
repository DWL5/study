# AWS IAM

AWS IAM(Identity and Access Management)은 AWS 리소스에 대한 인증(Authentication)과 인가(Authorization)를 제어하는 핵심 서비스입니다.

---

## IAM의 주요 구성 요소

### 사용자 (User)
- 실제 사람이나 애플리케이션에 고정된 자격 증명을 제공
- Access Key, Secret Key, 콘솔 로그인 비밀번호 사용

### 그룹 (Group)
- IAM 사용자들의 집합
- 정책을 한 번에 적용하기 위한 관리 단위

### 역할 (Role)
- 임시 권한을 부여하기 위한 객체
- 직접 로그인하거나 키를 갖지 않음
- 다른 사용자, 서비스, 계정이 `AssumeRole`하여 사용

### 정책 (Policy)
- JSON 형식으로 작성된 권한 문서
- 어떤 사용자/역할이 어떤 리소스에서 어떤 액션을 허용/거부하는지 정의
- 종류:
  - AWS 관리형 정책 (Amazon 제공)
  - 고객 관리형 정책 (사용자 정의)
  - 인라인 정책 (특정 객체에 직접 연결)

---

## 인증 vs 인가

| 개념       | 설명                           |
|------------|--------------------------------|
| 인증       | 누가 접근하는가 (로그인 등)     |
| 인가       | 무엇을 할 수 있는가 (정책 등)  |

---

## AssumeRole (역할 맡기)

- 다른 IAM Role을 임시로 "맡아서" 그 권한을 사용하는 행위
- AWS STS(Security Token Service)를 통해 임시 보안 자격 증명 발급
- API: `sts:AssumeRole`
- 자격 증명 유효 시간: 기본 1시간, 최대 12시간까지 가능 (IRSA 제외)

### Trust Policy 예시
```json
{
  "Effect": "Allow",
  "Principal": {
    "AWS": "arn:aws:iam::123456789012:user/someUser"
  },
  "Action": "sts:AssumeRole"
}
```

---

## IAM Role은 왜 Assume해야 할까?

- IAM Role은 사용자 계정처럼 고정된 자격 증명이 없습니다.
- 대신 AWS는 **STS(Security Token Service)**를 통해 **임시 보안 자격 증명**을 발급합니다.
- 이 과정을 `AssumeRole`이라고 부릅니다.

---

## 누가 어떻게 AssumeRole을 하는가?

| 주체 | 사용 방식 | 내부 동작 |
|------|-----------|-----------|
| IAM 사용자 | `sts:AssumeRole` API 호출 | 임시 자격 증명 발급 |
| EC2 / Lambda 등 AWS 서비스 | IAM Role 연결만 해도 사용 가능 | 자동으로 AssumeRole 수행 |
| SSO / OIDC / SAML 사용자 | 로그인 시 Role 지정 | AssumeRoleWithWebIdentity 또는 WithSAML 호출 |

---

## Trust Policy가 중요한 이유

IAM Role은 **누가 Assume할 수 있는지 명시**해야 합니다.

예시:

```json
{
  "Effect": "Allow",
  "Principal": {
    "AWS": "arn:aws:iam::111122223333:user/someUser"
  },
  "Action": "sts:AssumeRole"
}
```

---

## 실무 예시

### 예: EC2가 S3에 접근

- EC2 인스턴스에 IAM Role을 연결하면
- AWS 내부적으로 자동 AssumeRole 수행
- 해당 Role에 따라 S3에 접근 가능

### 예: 사용자 A가 관리자 Role을 사용할 때

- `userA`는 기본 권한 없음
- `sts:AssumeRole` API로 `AdminRole`을 맡음
- 임시로 관리자 권한 획득

---

## 요약

- IAM Role은 **그 자체로는 아무것도 못함**
- 반드시 누군가가 **AssumeRole**해야 사용할 수 있음
- EC2, Lambda 등은 AWS가 대신 AssumeRole 호출
- 직접 호출할 땐 `sts:AssumeRole` 또는 `AssumeRoleWithWebIdentity` 등 API 사용

