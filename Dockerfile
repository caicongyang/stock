# 第一阶段：构建应用
FROM maven:3.8.4-openjdk-8 AS builder

# 设置工作目录
WORKDIR /app

# 复制pom.xml
COPY pom.xml .

# 复制源代码
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests

# 第二阶段：运行应用
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 从builder阶段复制构建好的jar文件
COPY --from=builder /app/target/*.jar app.jar

# 设置时区为上海
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 暴露端口（根据你的应用配置修改）
EXPOSE 8888

# 设置启动命令
ENTRYPOINT ["java","-jar","/app/app.jar"] 