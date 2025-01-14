# Microservices avec Java, Spring Boot, Spring Cloud, Docker, Kubernetes, Helm et Sécurité des Microservices

Ce projet explore la conception, le développement, et la gestion des applications basées sur les microservices. Il couvre des concepts fondamentaux et des outils avancés pour construire des systèmes distribués et robustes, en mettant l'accent sur les technologies Java, Spring Boot, Spring Cloud, Docker, et Kubernetes.

## Points Clés Abordés :

1. **l'Architecture Microservices**

2. **Développement de Microservices avec Java et Spring**

   - Utilisation de Spring Boot pour créer des microservices prêts pour la production.
   - Intégration de Spring Cloud pour gérer la configuration, la découverte de services, et plus encore.

3. **Documentation des API**

   - Rédaction et génération de documentation claire et standardisée avec OpenAPI Specification et Swagger.

4. **Gestion des Conteneurs avec Docker**

   - Création d'images Docker et déploiement dans des conteneurs.
   - Utilisation de Docker Compose pour exécuter un ensemble de microservices.

5. **Patterns Clés pour Microservices**

   - **Service Discovery et Registration** : Implémentation avec Spring Eureka.
   - **Routing et Cross-Cutting Concerns** : Gestion via Spring Cloud Gateway.
   - **Résilience et Tolérance aux pannes** : Utilisation de Resilience4j.

6. **Observabilité et Monitoring**

   - Implémentation de la supervision et des métriques avec Prometheus, Loki, Promtail, Tempo, et Grafana.

7. **Sécurisation des Microservices**

   - Authentification et autorisation avec OAuth2, OpenID Connect et Spring Security.

8. **Microservices Événementiels**

   - Construction de systèmes basés sur des événements avec RabbitMQ, Kafka, et Spring Cloud Stream.

9. **Orchestration de Conteneurs avec Kubernetes**

    - Déploiement de microservices dans un cluster Kubernetes via Google Kubernetes Engine (GKE).
    - Gestion avancée des déploiements avec Helm.

![Prometheus](https://github.com/user-attachments/assets/cf21c79a-2cca-4b3f-a855-d74e3553f194)
![Jvm-MicroMeter](https://github.com/user-attachments/assets/a5918457-98d5-476a-8d1e-07fd7e3389af)
![Grafana-Loki-prometail](https://github.com/user-attachments/assets/3c0156ac-654d-4343-ab84-639864cdb423)
![Microservices](https://github.com/user-attachments/assets/1a15c7ce-c810-4fb6-973c-984f9d3a1182)
![KeyCloack-UI](https://github.com/user-attachments/assets/ae2fc2d7-9eb0-455d-9f0e-8925d0b2077a)
![DockerHub](https://github.com/user-attachments/assets/e7ff1be9-35c3-4daf-8dce-fc07508095aa)
![Docker-Desktop](https://github.com/user-attachments/assets/b683342c-d070-4b3a-a4e8-224b148ddddf)
![image](https://github.com/user-attachments/assets/1ca20596-bdb5-4d33-823e-4ab3166ea7d2)
![image](https://github.com/user-attachments/assets/2f6726c9-8441-4bd3-95a8-7ceabd983780)

 
Spring Cloud Stream équipe une application Spring Boot d'un destination binder (liaison de destination) qui s'intègre de manière transparente à un système de messagerie externe(kafka, RabbitMq..). Ce binder prend en charge l’établissement de canaux de communication entre les producteurs et les consommateurs de l’application et les entités du système de messagerie (comme les échanges et les files d’attente dans le cas de RabbitMQ). Ces canaux de communication, connus sous le nom de liaisons de destination, servent de connexions entre les applications et les brokers.

Une liaison de destination peut fonctionner soit comme un canal d’entrée, soit comme un canal de sortie. Par défaut, Spring Cloud Stream associe chaque liaison, à la fois en entrée et en sortie, à un échange au sein de RabbitMQ (plus précisément, un échange de type "topic"). De plus, pour chaque liaison d’entrée, il lie une file d’attente à l’échange associé. Cette file d’attente sert de source à partir de laquelle les consommateurs reçoivent et traitent les événements. Cette configuration fournit l'infrastructure nécessaire pour mettre en œuvre des architectures pilotées par les événements basées sur le modèle pub/sub.
Using RabbitMq :
![image](https://github.com/user-attachments/assets/fb57429f-5e34-4ec2-9132-945f2391a94b)
![image](https://github.com/user-attachments/assets/da637817-b288-4793-864c-79aa143ed1d0)

Using Kafka Apache :
![image](https://github.com/user-attachments/assets/cbd4b31a-ed0f-413a-9c39-0d7a4e50e2e2)
![image](https://github.com/user-attachments/assets/4c0417cb-d254-40f8-9b7a-e2fc0738f3b2)

Kubernetes/K8s Cluster:

C:\Workspaces\microservices\kubernetes>kubectl get deployments
NAME                       READY   UP-TO-DATE   AVAILABLE   AGE
accounts-deployment        2/2     2            2           12h
cards-deployment           1/1     1            1           12h
configserver-deployment    1/1     1            1           14h
eurekaserver-deployment    1/1     1            1           12h
gatewayserver-deployment   1/1     1            1           12h
keycloak                   1/1     1            1           12h
loans-deployment           1/1     1            1           12h

C:\Workspaces\microservices\kubernetes>kubectl apply -f 5_accounts.yaml
deployment.apps/accounts-deployment configured
service/accounts unchanged

C:\Workspaces\microservices\kubernetes>kubectl get replicaset
NAME                                  DESIRED   CURRENT   READY   AGE
accounts-deployment-54ddcf8687        0         0         0       12h
accounts-deployment-9b7cc65c5         2         2         2       12h
cards-deployment-86b48dd584           1         1         1       12h
configserver-deployment-7b6fcdb485    1         1         1       14h
configserver-deployment-97b8c767      0         0         0       14h
eurekaserver-deployment-6ccbf69b9f    0         0         0       12h
eurekaserver-deployment-746476dcff    1         1         1       12h
gatewayserver-deployment-85555d5f69   1         1         1       12h
keycloak-fd7788444                    1         1         1       12h
loans-deployment-7d5b94d597           1         1         1       12h

C:\Workspaces\microservices\kubernetes>kubectl delete pod accounts-deployment-9b7cc65c5-hgh6m
pod "accounts-deployment-9b7cc65c5-hgh6m" deleted

C:\Workspaces\microservices\kubernetes>kubectl get events --sort-by=.metadata.creationTimestamp
LAST SEEN   TYPE     REASON              OBJECT                                     MESSAGE
7m34s       Normal   Scheduled           pod/accounts-deployment-9b7cc65c5-6mpwn    Successfully assigned default/accounts-deployment-9b7cc65c5-6mpwn to docker-desktop
7m35s       Normal   SuccessfulCreate    replicaset/accounts-deployment-9b7cc65c5   Created pod: accounts-deployment-9b7cc65c5-6mpwn
7m35s       Normal   ScalingReplicaSet   deployment/accounts-deployment             Scaled up replica set accounts-deployment-9b7cc65c5 to 2 from 1
7m29s       Normal   Pulled              pod/accounts-deployment-9b7cc65c5-6mpwn    Container image "aarafahub/accounts:s12" already present on machine
7m29s       Normal   Created             pod/accounts-deployment-9b7cc65c5-6mpwn    Created container accounts
7m28s       Normal   Started             pod/accounts-deployment-9b7cc65c5-6mpwn    Started container accounts
2m39s       Normal   Killing             pod/accounts-deployment-9b7cc65c5-hgh6m    Stopping container accounts
2m39s       Normal   Scheduled           pod/accounts-deployment-9b7cc65c5-v9v6k    Successfully assigned default/accounts-deployment-9b7cc65c5-v9v6k to docker-desktop
2m39s       Normal   SuccessfulCreate    replicaset/accounts-deployment-9b7cc65c5   Created pod: accounts-deployment-9b7cc65c5-v9v6k
2m37s       Normal   Pulled              pod/accounts-deployment-9b7cc65c5-v9v6k    Container image "aarafahub/accounts:s12" already present on machine
2m37s       Normal   Created             pod/accounts-deployment-9b7cc65c5-v9v6k    Created container accounts
2m36s       Normal   Started             pod/accounts-deployment-9b7cc65c5-v9v6k    Started container accounts

C:\Workspaces\microservices\kubernetes>kubectl get pods
NAME                                        READY   STATUS    RESTARTS   AGE
accounts-deployment-9b7cc65c5-6mpwn         1/1     Running   0          8m54s
accounts-deployment-9b7cc65c5-v9v6k         1/1     Running   0          3m58s
cards-deployment-86b48dd584-7qjpv           1/1     Running   0          11h
configserver-deployment-7b6fcdb485-fqlvh    1/1     Running   0          13h
eurekaserver-deployment-746476dcff-rdb8k    1/1     Running   0          11h
gatewayserver-deployment-85555d5f69-jtt8j   1/1     Running   0          10h
keycloak-fd7788444-766k8                    1/1     Running   0          11h
loans-deployment-7d5b94d597-gpjj7           1/1     Running   0          11h

C:\Workspaces\microservices\kubernetes>kubectl get services
NAME            TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
accounts        LoadBalancer   10.103.71.165   localhost     8080:30860/TCP   12h
cards           LoadBalancer   10.111.58.69    localhost     9000:30648/TCP   12h
configserver    LoadBalancer   10.105.21.230   <pending>     8071:31565/TCP   14h
eurekaserver    LoadBalancer   10.97.155.248   localhost     8070:32514/TCP   12h
gatewayserver   LoadBalancer   10.98.246.199   localhost     8072:32471/TCP   12h
keycloak        LoadBalancer   10.108.174.47   <pending>     7080:30270/TCP   12h
kubernetes      ClusterIP      10.96.0.1       <none>        443/TCP          2d15h
loans           LoadBalancer   10.98.95.35     localhost     8090:31474/TCP   12h

![image](https://github.com/user-attachments/assets/b700873c-43e4-44ff-a028-da3cdc635e02)



