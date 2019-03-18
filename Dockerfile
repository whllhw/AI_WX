FROM openjdk:8u181-jre-alpine3.8
# 设置时区
RUN  echo 'http://mirrors.ustc.edu.cn/alpine/v3.5/main' > /etc/apk/repositories \
    && echo 'http://mirrors.ustc.edu.cn/alpine/v3.5/community' >>/etc/apk/repositories \
&& apk update && apk add --no-cache tzdata && apk add --update --no-cache ttf-dejavu \
&& ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone
COPY *.jar /
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "*.jar"]
