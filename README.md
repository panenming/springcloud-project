架构图
架构图见 paltform.png

各模块部署
nacos: https://nacos.io/zh-cn/docs/deployment.html
xxl-job: https://www.xuxueli.com/xxl-job/#%E4%BA%8C%E3%80%81%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8

中间件版本依赖
Mysql： 版本 5.7.28       8.0+版本会有很多jar和管理工具存在问题
redis： 3.0+
nacos： 1.1.4
xxl-job： 2.1.1
redssion：3.11.6 

工程打包
mvn clean -U package -P dev  -Dmaven.test.skip=true

工程部署运行
java -jar xx.jar

问题总结
1. 显示jar包未引入的webflux，idea直接删除应用后重新倒入
2. nacos集群配置一定要配置局域网ip

3. 网关配置：
动态路由：
{
    "refreshGatewayRoute":true,
    "routeList":[
        {
            "id":"select_user",
            "predicates":[
                {
                    "name":"Path",
                    "args":{
                        "_genkey_0":"/testFeignSelectUser"
                    }
                }
            ],
            "filters":[

            ],
            "uri":"lb://pay",
            "order":0
        }
    ]
}
限流
[
    {
        "resource": "/select_user",
        "limitApp": "default",
        "grade": 1,
        "count": 5,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
resource：资源名
limitApp：流控针对的调用来源，default不区分来源
grade：限流阈值类型(0-根据并发数量来限流 1-根据QPS来进行流量控制)
count：限流阈值
strategy：调用关系限流策略
controlBehavior：流量控制效果(直接拒绝、WarmUP、匀速排队)
clusterMode：是否集群模式

4. redisson文档
https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95
5. xxl-job文档
https://www.xuxueli.com/xxl-job/#%E3%80%8A%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E8%B0%83%E5%BA%A6%E5%B9%B3%E5%8F%B0XXL-JOB%E3%80%8B
6. nacos文档
https://nacos.io/zh-cn/docs/what-is-nacos.html
7. sentinel文档
https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D