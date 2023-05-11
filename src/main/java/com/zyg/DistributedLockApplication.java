package com.zyg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author zhangyg
 *
 */
@EnableCaching
@SpringBootApplication
public class DistributedLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedLockApplication.class, args);
	}


}
