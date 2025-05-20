# EC2 (Amazon Elastic Compute Cloud)

## 개요
- EC2는 AWS에서 제공하는 가상 서버 (Virtual Machine) 서비스이다.
- 사용자는 EC2 인스턴스를 생성하여 웹 서버, 애플리케이션 서버, 실습 서버 등으로 사용할 수 있다.
- EC2는 IaaS(Infrastructure as a Service)에 속한다.

## 주요 개념

| 구성 요소 | 설명 |
|------------|------|
| AMI (Amazon Machine Image) | EC2 인스턴스에 설치할 OS 및 소프트웨어 환경 |
| Instance Type | CPU, 메모리, 네트워크 성능 사양 (예: `t2.micro`, `m5.large`) |
| Key Pair | SSH 접속용 인증 파일 (.pem), 비밀번호 대신 사용 |
| Security Group | 방화벽 역할, 허용된 포트만 접근 가능 |
| Elastic IP | 고정 퍼블릭 IP 주소 (선택 사항) |
| User Data | 인스턴스 시작 시 실행할 스크립트 (부트스트랩용) |
| EBS (Elastic Block Store) | EC2의 영구 스토리지 디스크 |

## 인바운드 접근을 위한 필수 조건
- 보안 그룹에서 포트 22 (SSH) 열려 있어야 함
- 퍼블릭 IP 주소 또는 Elastic IP가 부여되어 있어야 함
- 서브넷에 인터넷 게이트웨이(IGW) 연결되어 있어야 외부에서 접근 가능

## EC2 실습 예시

```bash
# 키 파일 권한 설정
chmod 400 my-key.pem

# EC2 SSH 접속
ssh -i my-key.pem ec2-user@<퍼블릭 IP>
```

## AWS Associate 시험 자주 출제 포인트

### EC2 인스턴스 상태
- running: 정상 실행 중
- stopped: 꺼진 상태, EBS 요금만 과금됨
- terminated: 삭제됨, 복구 불가

### 예약 인스턴스 vs 온디맨드 vs 스팟

| 유형 | 설명 |
|------|------|
| 온디맨드 | 일반 요금, 유연한 사용 |
| 예약 인스턴스 | 1~3년 약정, 최대 75% 할인 |
| 스팟 인스턴스 | 남는 용량을 저렴하게 제공, 중단 가능성 있음 |

### EBS 볼륨
- 인스턴스와 별도로 존재하는 스토리지
- 인스턴스를 중지해도 데이터 유지
- 스냅샷으로 백업 가능
- 루트 볼륨 외에 추가 볼륨 연결 가능

### 스케일링 및 가용성 관련
- Auto Scaling: 수요에 따라 인스턴스 수 자동 조절
- Elastic Load Balancer (ELB): 여러 인스턴스에 트래픽 분산
- Placement Group: 고성능 워크로드 위한 인스턴스 배치 설정

### EC2 시작 시 사용자 데이터 (User Data)
- 인스턴스 시작 시 한 번만 실행되는 스크립트
- 주로 초기 설정이나 소프트웨어 설치에 사용

```bash
#!/bin/bash
yum update -y
yum install -y httpd
systemctl start httpd
```

## 요약
- EC2는 AWS에서 제공하는 가상 서버 서비스
- 실습, 웹 서비스, 백엔드 서버 등 다양한 용도로 사용 가능
- 시험에서는 보안 그룹, EBS, 인스턴스 상태, Auto Scaling, 스팟 인스턴스 등이 자주 출제됨
