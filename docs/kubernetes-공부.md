- 2026-02-26 minikube, kubectl 설치완료


## 쿠버네티스란?

- 컨테이너를 여러 서버에 걸쳐 자동으로 배포/관리해주는 시스템
"이 앱 3개 띄워줘, 하나 죽으면 자동으로 되살려줘" 이런 걸 해줌
---
- minikube → K8s 클러스터 자체를 간단하게 만들어주는 것
- kubectl → 그 클러스터에 명령 내리는 도구
--- 
- Pod → 실제 컨테이너가 실행되는 단위 (Docker 컨테이너와 유사)
- Deployment → Pod를 관리하는 것 (몇 개 띄울지, 죽으면 재시작 등)
- ReplicaSet → Deployment가 Pod 개수 맞추려고 만든 것
- Service → 외부에서 접근할 수 있게 포트 열어주는 것
- Node → Pod들이 실제로 올라가는 서버

1. nginx 배포
```bash
kubectl create deployment nginx --image=nginx
kubectl expose deployment nginx --type=NodePort --port=80
minikube service nginx --url
```
2. 리소스 확인
```bash
kubectl get all
```
3. 자동 복구
```bash
# 터미널 1 - 실시간 모니터링
kubectl get pods -w
# 터미널 2 - pod 강제 종료
kubectl delete pod [pod명]
```
4. 스케일 아웃
```bash
kubectl scale deployment nginx --replicas=3
```

### 자동 복구
- replicas=1 이면 pod 죽어도 자동으로 새 pod 재시작
- replicas=3 이면 하나 죽어도 나머지 2개가 트래픽 받음 → 진짜 무중단

### Rolling Update
- Deployment 수정하면 pod 하나씩 순차 교체
- 교체 중에도 나머지 pod가 살아있어서 무중단 배포 가능

---


### 앞으로 공부 방향

1. yaml 파일로 배포해보기
2. Basic-Arch (Spring Boot) K8s에 올려보기
3. Rolling Update 실습
4. Kafka 공부 (Redis pub/sub 전환)
5. AWS EKS or OCI OKE 실제 배포