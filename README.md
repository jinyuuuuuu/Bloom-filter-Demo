###简介

这个非常简单的Demo主要使用了Redis实现的Token认证登录（原始项目来自[3]），并且使用BloomFilter解决了Redis的缓存穿透问题，主要用的是Google的Hashing方法与Funnel，具体方法还请看代码

###演示方式

 1.按照实体类建表之后插入数据首先调用addAllDataToBloomFilter接口将数据库所有数据（以nickname为键）放入布隆过滤器
 
 2.为接口加上@Authorization注释就可以让拦截器拦截该接口
 
 3.由于将原项目的Springboot版本改为了2.1.1，对RedisTemplate的初始化配置与序列化方式我都放在了RedisConfig中，通过在Config中Bean注入再用@Resource或者@Autowired注解来找到相应的RedisTemplate
 
 （不知道是不是这么理解的，要是不对可以告诉我一下）
 
###可能会遇到的问题：

**java.lang.ClassNotFoundException: org.jboss.jandex.IndexView**

原因是缺少`org.jboss:jandex:1.1.0Final`依赖，可能需要您手动在`pom.xml`中依赖中添加以下内容：

```
<dependency>
    <groupId>org.jboss</groupId>
    <artifactId>jandex</artifactId>
    <version>1.1.0.Final</version>
</dependency>
```
**第一次使用GITHUB上传代码，我也不知会不会，可能在MAVEN配置里会出现<artifactId>不匹配的问题，要是会的话把项目名改一下就好了**


[1]:http://www.scienjus.com/restful-token-authorization/
[2]:https://github.com/ScienJus/spring-authorization-manager
[3]:githubhttps://github.com/ScienJus/spring-restful-authorization
