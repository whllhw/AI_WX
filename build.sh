#!/bin/bash
git reset --hard HEAD
docker_name=ai_wx
db_name=db
start=`git log -1 --format="%H"`
if [ $1 == $start ]
then
echo "look like pulled,skip this time."
exit 0
fi
git pull origin master
sed -i "s/localhost/$db_name/g" src/main/resources/application.yml
mvn clean package
cp target/*.jar ../images/
if [ $(docker ps | grep $docker_name | awk '{print $1}') ]
then
docker stop $docker_name
fi
cd ../images
docker build -t $docker_name .
#docker run --rm -d --name ims-test -v /home/caddy/log:/var/log/ims -p 127.0.0.1:10000:10000 ims-test
