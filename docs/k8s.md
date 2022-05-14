1. 集群中启动一个pod
    ```text
    kubectl run busybox --rm=true --image=busybox:1.28.3 --restart=Never --tty -i 
    ```
2. 启动容器并进入交互式命令
   ```text
   docker run -it image:tag /bin/bash
   ```
3. 删除已经停止的容器
   ```text
   docker rm $(docker ps -a -f "status=exited" -q)
   ```
4. 构建docker镜像
   ```text
   docker build -t jenkins:1.5 -f Dockerfile-jenkins .
   ```
5. 启动容器
   ```text
   docker run -it --name jenkins jenkins:1.0 /bin/sh
   
   docker run -p 8080:8080 --name jenkins \
   --privileged=true -d \
   -v /opt/jenkins/data:/opt/jenkins/data \
   -v /var/lib/docker:/var/lib/docker \
   jenkins:1.5 /usr/sbin/init
   ```
6. docker安装包：https://download.docker.com/linux/static/stable/x86_64/


