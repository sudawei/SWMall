package com.swmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//开启事务管理
@EnableTransactionManagement
//与dao层的@Mapper二选一写上即可(主要作用是扫包)
@MapperScan("com.swmall.dao")
public class SwmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwmallApplication.class, args);
	}
}
