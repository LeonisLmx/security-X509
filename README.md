<h1>本项目共有2个服务</h1>
1.server服务 充当服务端角色

2.client服务 充当客户端角色

<h1>运行步骤：</h1>
1.进入到当前目录下，使用指令 sh build.sh

2.指令中出现的2次停顿，均输入 "是" 再回车

3.指令运行完成以后，当前目录下会出现keys文件夹

4.将 keys 文件夹中的 server.keystore 和 server.truststore 拷贝至 server 项目 application.yml 同目录下

5.运行 ServerApplication.java

6.将该项目 keys 文件夹中的 client.keystore 和 client.truststore 拷贝至 client 项目 application.properties 同目录下

7.运行 ClientApplication.java

8.本地访问 http://127.0.0.1:8881/test 即可。