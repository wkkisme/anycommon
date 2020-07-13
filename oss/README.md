##使用方式：

1 SpringbootApplication类中要加入扫描：cache包 
##示例：@SpringBootApplication(scanBasePackages = {"com.anycommon.cache","com.anycommon.oss"})

2 oss的配置根据项目环境走的，如果有自定义的配置，则需要加配置文件 oss-自定义.properties文件能被springboot读取到即可

3 无需任何配置自带controller ，com.anycommon.oss.controller.CommonOssController 里有几个公共的方法
可供使用
