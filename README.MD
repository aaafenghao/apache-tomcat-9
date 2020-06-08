# 组件
 Server
 Service
 Connector
 Engine--Servlet
 Host
 Context
 Wrapper
# Tomcat启动:
1、生命周期
init
start
stop
destory
2、Tomcat初始化
StandardServer
    StandService
        Connector--http,arp
        Engine
3、Tomcat启动
StandardServer
    StandardService
        Connector--Http,arp
        Engine
            StandardHost
                HostConfig
                    StandardContext
                        ContextConfig
                            StandardWrapper
4、 一次请求
5、设计模式
   责任链模式
   门面模式