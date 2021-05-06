# blog
springboot集成的一个博客

springboot版本：2.2.0.RELEASE

elasticsearch版本要求：与springboot的保持一致 否则会报错；我使用的6.8.3

jdk环境要求：1.8

使用springboot集成了elasticsearch

注意配置问题：

cluster-nodes 通过tcp方式连接端口一定是9300

代码中一些不同的技术：Vue、axios、bootstrap

springboot中接收不同参数的写法：
@Param、@PathVariable("id")、@RequestBody等


V0.1 springboot集成的一个博客

V0.2 集成了layui并通过此技术实现后台的RBAC功能

注意：使用了thymeleaf模板需要在方法中注意，如果是模板的话不能使用@RestController和@ResponseBody如果是接口或者返回数据的话必须的有@ResponseBody

