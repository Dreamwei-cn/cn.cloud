package cn.cloud.ZconfiService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableConfigServer
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableEurekaClient

public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
