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

