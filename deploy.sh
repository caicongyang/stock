#!/bin/bash

# 设置变量
IMAGE_NAME="stock"
VERSION="latest"
DOCKER_HUB_USERNAME="caicongyang"  # 替换为你的Docker Hub用户名
CONTAINER_NAME="stock"
PORT=8888

# 构建Docker镜像
echo "开始构建Docker镜像..."
docker build -t $IMAGE_NAME:$VERSION .

# 标记镜像
echo "标记Docker镜像..."
docker tag $IMAGE_NAME:$VERSION $DOCKER_HUB_USERNAME/$IMAGE_NAME:$VERSION

# 推送到Docker Hub
echo "推送镜像到Docker Hub..."
docker push $DOCKER_HUB_USERNAME/$IMAGE_NAME:$VERSION

# 停止并删除旧容器（如果存在）
echo "清理旧容器..."
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

# 运行新容器
echo "启动新容器..."
docker run -d \
  --name $CONTAINER_NAME \
  -p $PORT:$PORT \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3333/stock \
  -e SPRING_DATASOURCE_USERNAME=your-username \
  -e SPRING_DATASOURCE_PASSWORD=your-password \
  $DOCKER_HUB_USERNAME/$IMAGE_NAME:$VERSION

# 检查容器状态
echo "检查容器状态..."
docker ps | grep $CONTAINER_NAME

echo "部署完成！" 