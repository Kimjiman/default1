# Kubernetes 학습 로드맵

> 목표: CKA 대비
> 환경: Windows 11 + WSL2 + Docker Desktop
> 기간: 4-6주 (하루 1~2시간 기준)

---

## 전체 흐름

```
1단계: 핵심 개념        — K8s가 뭔지 이해
2단계: 로컬 실습 환경   — minikube 설치 및 기본 조작
3단계: 워크로드         — Pod / Deployment / Service
4단계: 설정 & 스토리지  — ConfigMap / Secret / PVC
5단계: 운영 기능        — Probe / HPA / Resource
6단계: 현재 프로젝트 배포 — basic-arch K8s로 올리기
7단계: Observability    — metrics-server / Prometheus / Grafana
```

---

## 1단계: 핵심 개념 이해 (1주차)

### K8s가 필요한 이유

```
Docker Compose: 단일 서버, 수동 배포, 장애 시 직접 복구
Kubernetes:     여러 서버(클러스터), 자동 배포, 자동 복구, 자동 확장
```

### 구성요소

```
Cluster
└── Node (서버 1대 = VM or 물리 서버)
    └── Pod (컨테이너 묶음, 배포 최소 단위)
        └── Container (실제 앱)
```

| 구성요소 | 역할 |
|----------|------|
| **Pod** | 컨테이너 1개 이상을 묶은 배포 단위 |
| **Node** | Pod가 실행되는 서버 |
| **Cluster** | Node 묶음 전체 |
| **Control Plane** | 클러스터 두뇌 (API Server, Scheduler, etcd) |
| **kubectl** | 클러스터 조작 CLI |

### 반드시 이해할 오브젝트

| 오브젝트 | 한 줄 설명 |
|----------|-----------|
| **Pod** | 배포 최소 단위, 직접 만들지 않음 |
| **Deployment** | Pod 개수 유지, 롤링 업데이트 |
| **ReplicaSet** | Pod 복제본 수 보장 (Deployment가 관리) |
| **Service** | Pod에 안정적인 네트워크 주소 부여 |
| **ConfigMap** | 환경변수/설정 파일 (평문) |
| **Secret** | 환경변수/설정 파일 (base64 인코딩) |
| **Namespace** | 리소스 논리적 격리 공간 |
| **Ingress** | 외부 HTTP 트래픽 → 내부 Service 라우팅 |
| **PersistentVolume (PV)** | 실제 저장소 |
| **PersistentVolumeClaim (PVC)** | Pod가 PV를 요청하는 방법 |
| **HPA** | CPU/메모리 기준 Pod 자동 수평 확장 |

---

## 2단계: 로컬 실습 환경 (1주차)

### 설치

```bash
# minikube 및 kubectl 설치 (더 많은 기능 실습 가능, 권장)
# minikube
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64

# 설치
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# 설치 확인
minikube version

# 자동완성 설정
echo 'source <(minikube completion bash)' >> ~/.bashrc
source ~/.bashrc

# kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"

# 설치
sudo install kubectl /usr/local/bin/kubectl

# 설치 확인
kubectl version --client

# 자동완성 설정
echo 'source <(kubectl completion bash)' >> ~/.bashrc
source ~/.bashrc
```

### minikube 기본 명령어

```bash
minikube start                    # 클러스터 시작
minikube stop                     # 클러스터 중지
minikube delete                   # 클러스터 삭제
minikube status                   # 상태 확인
minikube dashboard                # 웹 대시보드 오픈
minikube tunnel                   # LoadBalancer 로컬 접근용
```

### kubectl 기본 명령어

```bash
# 조회
kubectl get pods                          # Pod 목록
kubectl get pods -n kube-system           # 특정 네임스페이스
kubectl get all                           # 모든 리소스
kubectl get pods -o wide                  # 상세 (IP, Node 포함)
kubectl get pods --watch                  # 실시간 감시

# 상세 정보
kubectl describe pod <pod-name>           # Pod 상세 정보
kubectl describe deployment <name>

# 로그
kubectl logs <pod-name>                   # 로그 출력
kubectl logs <pod-name> -f                # 실시간 로그
kubectl logs <pod-name> --previous        # 이전 컨테이너 로그 (재시작 후)

# 실행
kubectl exec -it <pod-name> -- /bin/bash  # 컨테이너 접속
kubectl apply -f <file.yaml>              # 매니페스트 적용
kubectl delete -f <file.yaml>             # 매니페스트 삭제
kubectl delete pod <pod-name>             # Pod 강제 삭제

# 포트 포워딩 (로컬 테스트용)
kubectl port-forward pod/<pod-name> 8080:8080
kubectl port-forward svc/<service-name> 8080:8080
```

---

## 3단계: 워크로드 (2주차)

### Pod

```yaml
# pod.yaml — 직접 생성은 거의 안 함, 개념 이해용
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
  containers:
    - name: app
      image: nginx:latest
      ports:
        - containerPort: 80
```

```bash
kubectl apply -f pod.yaml
kubectl get pods
kubectl delete pod my-pod
```

### Deployment

```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  replicas: 3                          # Pod 3개 유지
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
        - name: app
          image: nginx:1.25
          ports:
            - containerPort: 80
          resources:
            requests:
              cpu: "100m"
              memory: "128Mi"
            limits:
              cpu: "500m"
              memory: "256Mi"
```

```bash
# 롤링 업데이트
kubectl set image deployment/my-app app=nginx:1.26
kubectl rollout status deployment/my-app

# 롤백
kubectl rollout undo deployment/my-app
kubectl rollout history deployment/my-app
```

### Service 종류

```
ClusterIP    — 클러스터 내부에서만 접근 (기본값, Pod 간 통신)
NodePort     — 노드 IP:포트로 외부 접근 (로컬 테스트용)
LoadBalancer — 클라우드 로드밸런서 (minikube tunnel 필요)
```

```yaml
# service.yaml
apiVersion: v1
kind: Service
metadata:
  name: my-app-service
spec:
  type: ClusterIP
  selector:
    app: my-app                        # Deployment 라벨과 일치해야 함
  ports:
    - port: 80                         # Service 포트
      targetPort: 80                   # 컨테이너 포트
```

---

## 4단계: 설정 & 스토리지 (2~3주차)

### ConfigMap

```yaml
# configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  SPRING_PROFILES_ACTIVE: "dev"
  SERVER_PORT: "8080"
  DB_HOST: "postgres-service"
```

```yaml
# Deployment에서 사용
envFrom:
  - configMapRef:
      name: app-config
```

### Secret

```bash
# base64 인코딩
echo -n "mypassword" | base64      # bXlwYXNzd29yZA==
```

```yaml
# secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: app-secret
type: Opaque
data:
  DB_PASSWORD: bXlwYXNzd29yZA==    # base64 인코딩된 값
  JWT_SECRET: <base64-encoded>
```

```yaml
# Deployment에서 사용
envFrom:
  - secretRef:
      name: app-secret
```

### PersistentVolume / PVC (DB 데이터 영속화)

```yaml
# pvc.yaml — PostgreSQL 데이터 저장용
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: postgres-pvc
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi
```

```yaml
# Deployment volumes 섹션
volumes:
  - name: postgres-data
    persistentVolumeClaim:
      claimName: postgres-pvc
volumeMounts:
  - mountPath: /var/lib/postgresql/data
    name: postgres-data
```

---

## 5단계: 운영 기능 (3주차)

### Health Check (Probe)

```yaml
livenessProbe:      # 실패 시 컨테이너 재시작
  httpGet:
    path: /actuator/health
    port: 8080
  initialDelaySeconds: 60    # 앱 기동 대기
  periodSeconds: 10
  failureThreshold: 3

readinessProbe:     # 실패 시 Service에서 제외 (트래픽 차단)
  httpGet:
    path: /actuator/health/readiness
    port: 8080
  initialDelaySeconds: 30
  periodSeconds: 5
```

> Spring Boot Actuator 의존성 추가 필요: `spring-boot-starter-actuator`

### HPA (수평 자동 확장)

```yaml
# hpa.yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: my-app-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: my-app
  minReplicas: 1
  maxReplicas: 5
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70     # CPU 70% 초과 시 스케일 아웃
```

```bash
# HPA 동작 확인
kubectl get hpa
kubectl top pods                    # metrics-server 설치 필요
```

### Ingress

```yaml
# ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: my-app-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
    - host: my-app.local
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: my-app-service
                port:
                  number: 80
```

```bash
# minikube ingress 활성화
minikube addons enable ingress
```

---

## 6단계: 현재 프로젝트(basic-arch) K8s 배포 (4주차)

### docker-compose → K8s 전환 목표

```
docker-compose.yml                  K8s 매니페스트
─────────────────────────────────────────────────────
postgres 서비스            →   postgres-deployment.yaml + postgres-pvc.yaml
redis 서비스               →   redis-deployment.yaml
Spring Boot 앱             →   app-deployment.yaml
포트 노출                  →   service.yaml (각 서비스)
환경변수                   →   configmap.yaml + secret.yaml
외부 접근                  →   ingress.yaml
```

### 디렉토리 구조

```
k8s/
├── configmap.yaml
├── secret.yaml
├── postgres/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── pvc.yaml
├── redis/
│   ├── deployment.yaml
│   └── service.yaml
└── app/
    ├── deployment.yaml
    ├── service.yaml
    ├── ingress.yaml
    └── hpa.yaml
```

### 배포 순서

```bash
# 1. 설정 먼저
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml

# 2. 인프라 (DB, Redis)
kubectl apply -f k8s/postgres/
kubectl apply -f k8s/redis/

# 3. 앱
kubectl apply -f k8s/app/

# 전체 한번에
kubectl apply -f k8s/ --recursive

# 상태 확인
kubectl get all
```

---

## 7단계: Observability (5~6주차)

### K8s 기본 모니터링

```bash
# metrics-server 설치 (minikube)
minikube addons enable metrics-server

# 리소스 사용량 확인
kubectl top nodes
kubectl top pods
kubectl top pods --sort-by=cpu

# 이벤트 확인
kubectl get events --sort-by=.metadata.creationTimestamp
```

### Prometheus + Grafana (kube-prometheus-stack)

```bash
# Helm 설치
winget install Helm.Helm

# kube-prometheus-stack 설치 (Prometheus + Grafana + AlertManager 한번에)
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install monitoring prometheus-community/kube-prometheus-stack \
  --namespace monitoring \
  --create-namespace

# 설치 확인
kubectl get pods -n monitoring

# Grafana 접근
kubectl port-forward svc/monitoring-grafana 3000:80 -n monitoring
# 브라우저: http://localhost:3000  (admin / prom-operator)
```

### 주요 Prometheus 메트릭

| 메트릭 | 설명 |
|--------|------|
| `container_cpu_usage_seconds_total` | 컨테이너 CPU 사용량 |
| `container_memory_usage_bytes` | 컨테이너 메모리 사용량 |
| `kube_pod_status_phase` | Pod 상태 (Running/Pending/Failed) |
| `kube_deployment_spec_replicas` | 목표 복제본 수 |
| `kube_deployment_status_replicas_available` | 실제 가용 복제본 수 |

### Spring Boot Actuator → Prometheus 연동

```yaml
# build.gradle 추가
implementation 'io.micrometer:micrometer-registry-prometheus'
```

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health, readiness, prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

```bash
# 메트릭 확인
curl http://localhost:8085/actuator/prometheus
```

---

## 면접 대비 핵심 질문 & 답변 포인트

| 질문 | 핵심 답변 |
|------|-----------|
| Pod와 Container 차이 | Pod는 배포 단위, 여러 컨테이너가 네트워크/볼륨 공유 |
| Deployment vs StatefulSet | Deployment: 무상태 앱. StatefulSet: DB처럼 순서/ID 필요한 상태 있는 앱 |
| Rolling Update 동작 | 새 Pod 띄우고 → 트래픽 전환 → 구 Pod 종료. 무중단 배포 |
| Liveness vs Readiness | Liveness: 죽었으면 재시작. Readiness: 준비 안 됐으면 트래픽 차단 |
| K8s 장애 대응 순서 | `kubectl get pods` → `kubectl describe` → `kubectl logs` |
| OOMKilled 원인 | memory limits 초과. resources.limits.memory 증가 또는 메모리 누수 수정 |
| Service의 역할 | Pod IP는 바뀌므로, 고정 ClusterIP + 라벨 셀렉터로 Pod 그룹에 접근 |

---

## 학습 체크리스트

### 1주차
- [ ] K8s 핵심 구성요소 설명할 수 있다
- [ ] minikube 설치 및 시작
- [ ] kubectl 기본 명령어 (get, describe, logs, apply) 사용

### 2주차
- [ ] Pod, Deployment, Service yaml 직접 작성
- [ ] 롤링 업데이트, 롤백 실습
- [ ] ConfigMap, Secret 적용

### 3주차
- [ ] Liveness/Readiness Probe 설정
- [ ] PVC로 데이터 영속화
- [ ] HPA 설정 및 동작 확인

### 4주차
- [ ] basic-arch 프로젝트 K8s 배포
- [ ] PostgreSQL StatefulSet + PVC
- [ ] Redis Deployment
- [ ] Spring Boot Actuator 연동

### 5~6주차
- [ ] metrics-server 설치 및 `kubectl top` 활용
- [ ] kube-prometheus-stack 설치
- [ ] Grafana 대시보드에서 Pod 메트릭 확인
- [ ] Spring Boot → Prometheus 메트릭 수집
- [ ] 면접 질문 5개 이상 답변 가능

---

## 참고 자료

### 강의 (무료)

| 자료 | 링크 | 특징 |
|------|------|------|
| 따배쿠 - 따라하며 배우는 쿠버네티스 (유튜브) | [YouTube 재생목록](https://www.youtube.com/watch?v=6n5obRKsCRQ&list=PLApuRlvrZKohaBHvXAOhUD-RxD0uQ3z0c) | 한국어 무료 입문 강의, 강추 |
| 따배쿠 학습 노트 (wikidocs) | [wikidocs.net/186107](https://wikidocs.net/186107) | 강의 내용 정리본, 복습용 |

### 문서 & 가이드

| 자료 | 링크 | 특징 |
|------|------|------|
| 쿠버네티스 안내서 (subicura) | [subicura.com/k8s](https://subicura.com/k8s/) | 한국어, 그림 설명 탁월, 강추 |
| 공식 문서 | [kubernetes.io/docs](https://kubernetes.io/docs/home/) | 가장 정확한 레퍼런스 |
| kubectl 치트시트 | [kubectl cheatsheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/) | 명령어 모음, 북마크 필수 |
| Helm 패키지 검색 | [artifacthub.io](https://artifacthub.io/) | Prometheus 등 설치 시 사용 |

### 실습 환경

| 자료 | 링크 | 특징 |
|------|------|------|
| Play with Kubernetes | [labs.play-with-k8s.com](https://labs.play-with-k8s.com/) | 설치 없이 브라우저에서 실습 |
| 따배쿠 실습 GitHub | [github.com/zieunx/ttabaeku](https://github.com/zieunx/ttabaeku) | 강의 실습 파일 모음 |
