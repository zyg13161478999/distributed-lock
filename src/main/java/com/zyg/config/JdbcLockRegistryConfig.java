package com.zyg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.util.UUID;

/**
 * @author .gang
 * @date 2023/5/11
 */
@Configuration
public class JdbcLockRegistryConfig {
    @Value("${server.port}")
    private Integer port;

    @Autowired
    @Bean
    public LockRegistry getDefaultLockRegistry(DataSource dataSource) throws Exception {
        DefaultLockRepository lockRepository = new DefaultLockRepository(dataSource,
                 UUID.randomUUID().toString());
        //设置分布式锁的过期时间,单位为毫秒,默认为10秒
        lockRepository.setTimeToLive(10);
        //调用方法对锁的变量进行初始化
        lockRepository.afterPropertiesSet();
        return new JdbcLockRegistry(lockRepository);
    }


}