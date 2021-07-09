package com.anshu.platform.s3connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class S3ConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3ConnectorApplication.class, args);
	}

}
