# Kubernetes (k8s) Installation

System required: 
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)
- [istio](https://istio.io/docs/setup/kubernetes/install/kubernetes/)
- helm (Optional)

## Arsitektur

- Deployment

![istio configuration](imgs/arch.svg)

## Setup minikube

Kubernate on local, mengunakan minikube. pertama kita harus buat dulu vm untuk minikubenya dengan spec seperti berikut:

```bash
minikube --memory 4096 --cpus 2 --insecure-registry tabeldata.ip-dynamic.com:8087 start
```

Penjelasan

- `memory`, ramnya disarankan lebih besar dari 4gb
- `cpus`, cpu core disarankan lebih besar dari 2
- `insecure-registry`, docker private registry yang kita gunakan adalah `tabeldata.ip-dynamic.com:8087`

Berikut hasilnya:

```bash
😄  minikube v1.0.0 on darwin (amd64)
🤹  Downloading Kubernetes v1.14.0 images in the background ...
🔥  Creating virtualbox VM (CPUs=2, Memory=4096MB, Disk=20000MB) ...
📶  "minikube" IP address is 192.168.99.127
🐳  Configuring Docker as the container runtime ...
🐳  Version of container runtime is 18.06.2-ce
⌛  Waiting for image downloads to complete ...
✨  Preparing Kubernetes environment ...
🚜  Pulling images required by Kubernetes v1.14.0 ...
🚀  Launching Kubernetes v1.14.0 using kubeadm ... 
⌛  Waiting for pods: apiserver proxy etcd scheduler controller dns
🔑  Configuring cluster permissions ...
🤔  Verifying component health .....
💗  kubectl is now configured to use "minikube"
🏄  Done! Thank you for using minikube!
```

## Installing istio.io

Pertama download dulu, [istio.io lastest version](https://istio.io/docs/setup/kubernetes/download/), kemudian gunakan script berikut:

```bash
for i in install/kubernetes/helm/istio-init/files/crd*yaml; do kubectl apply -f $i; done
```

Setelah itu, kita active-kan istionya dengan menggunakan perintah berikut:

```bash
kubectl apply -f path-to-istio/install/kubernetes/istio-demo-auth.yaml
```

## Springboot running into pods

```bash
kubectl label namespace default istio-injection=enabled

## apply pods and services
kubectl apply -f kubernetes/kubernetes.postgres-service.yaml
kubectl apply -f kubernetes/kubernetes.auth-service.yaml

## apply proxy
kubectl apply -f kubernetes/istio.gateway.yaml
kubectl apply -f kubernetes/istio.virtualservice.yaml
```

check status is running:

```bash
watch kubectl get pods -A
```

![watch pods creating](imgs/watch-pods-creating.png)

## Testing

Setelah run, kita bisa testing dengan perintah berikut:

Untuk request token

```bash
curl -X POST \
  'http://<minikube ip>:31380/oauth/token?username=user&password=password&grant_type=password' \
  -H 'Authorization: Basic Y2xpZW50LXdlYjoxMjM0NTY=' \
  -H 'cache-control: no-cache'
```

Untuk request api:

```bash
curl -X GET \
  http://<minikube ip>:31380/api/example \
  -H 'Authorization: Bearer [access-token]' \
  -H 'cache-control: no-cache'
```

## Visualization mesh service

```bash
kubectl -n istio-system port-forward $(kubectl -n istio-system get pod -l app=kiali -o jsonpath='{.items[0].metadata.name}') 20001:20001
```

visit [http://localhost:20001/kiali/console](http://localhost:20001/kiali/console)

Berikut adalah graph flow service mesh:

![graph](imgs/graph-service-mesh.png)

## Istio Dashboard 

```bash
kubectl -n istio-system port-forward $(kubectl -n istio-system get pod -l app=grafana -o jsonpath='{.items[0].metadata.name}') 3000:3000 &
```

visit [http://localhost:3000/dashboard/db/istio-mesh-dashboard](http://localhost:3000/dashboard/db/istio-mesh-dashboard)

![graph](imgs/istio-dashboard.png)
