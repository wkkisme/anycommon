##使用方式：

1 SpringbootApplication类中要加入扫描：cache包 
##示例：@SpringBootApplication(scanBasePackages = {"com.anycommon.cache","com.anycommon.oss"})

2 在配置application.properties中加入当前的redis的配置，目前测试环境的的为standalone模式，
线上会采用cluster模式；
##示例： platform.redis.type=standalone

3 使用则直接 
@Resource
private RedisService redisService;