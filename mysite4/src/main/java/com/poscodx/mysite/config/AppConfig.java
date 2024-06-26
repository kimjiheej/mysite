package com.poscodx.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.poscodx.mysite.config.app.DBConfig;
import com.poscodx.mysite.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan({"com.poscodx.mysite.service", "com.poscodx.mysite.repository", "com.poscodx.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
	
	  // Configuration : 이 클래스가 설정 파일이다 
	  // EnableAspectJAutoProxy : 스프링에서의 관점 지향 프로그래밍이다 
	  // EnableTransactionManageement : 트랜잭션을 그대로 하도록 
	  // ComponentScan :  스프링이 지정된 패키지에서 컴포넌트(빈)를 자동으로 검색하고 인스턴스화하도록 합니다.
	 // 전체적으로 이 AppConfig 클래스는 스프링 애플리케이션에서 AOP, 트랜잭션 관리, 컴포넌트 스캔, 데이터베이스 설정 등을 구성합니다.
}