package lib.sixzeroseven.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @EnableCaching 注解配置启用缓存，自动配置配置文件的配置信息进行条件注入缓存所需实例
 * @author WJoe
 *
 */
@MapperScan(value = "lib.sixzeroseven.admin.mapper")
@SpringBootApplication
// @EnableCaching
public class BootstrapApplication  {

	
	public static void main(String[] args) {
		SpringApplication.run(BootstrapApplication.class, args);
	}
}
