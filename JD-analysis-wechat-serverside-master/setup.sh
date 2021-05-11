#!/bin/sh
echo '======= Start building and runing SpringBoot Application =======\n'

# 创建日志文件
cd /root/log
FILENAME="$(date "+%Y%m%d%H%M%S").log"
touch "${FILENAME}"


# 如果已有SpringBoot项目正在运行，则杀死已有进程
if
jps|grep jar
then
	echo 'kill $(jps|grep jar)'
	kill -9 $(jps|grep jar)
	sleep 2s
fi

# 切换目录
cd /root/JD-analysis-wechat-serverside || echo 'cd fail'

# 清理项目
mvn clean

# 构建项目
mvn install -Dmaven.test.skip=true

#切换目录
cd target
nohup java -jar wechat-serverside-0.0.1-SNAPSHOT.jar > /root/log/${FILENAME} 2>&1 &

# 防止终端无限等待
sleep 10s
'successfully deploy SpringBoot Application'>0
