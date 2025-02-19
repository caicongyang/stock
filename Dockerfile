# 第一阶段：构建基础依赖
FROM maven:3.8.4-openjdk-8 AS base-builder

# 设置工作目录
WORKDIR /build

# 配置Maven设置
COPY settings.xml /root/.m2/settings.xml

# 克隆并构建基础项目
RUN git clone https://github.com/caicongyang/springbootDemo.git && \
    cd springbootDemo && \
    mvn clean install -DskipTests -Dmaven.test.skip=true

# 第二阶段：构建当前项目
FROM maven:3.8.4-openjdk-8 AS builder

# 设置工作目录
WORKDIR /app

# 复制基础项目的Maven仓库
COPY --from=base-builder /root/.m2 /root/.m2
COPY settings.xml /root/.m2/settings.xml

# 复制当前项目文件
COPY pom.xml .
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# 第三阶段：运行应用
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 从builder阶段复制构建好的jar文件
COPY --from=builder /app/target/*.jar app.jar

# 设置时区为上海
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 暴露端口
EXPOSE 8888

# 设置启动命令
ENTRYPOINT ["java","-jar","/app/app.jar"] 