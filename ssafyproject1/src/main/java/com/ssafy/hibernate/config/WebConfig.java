package com.ssafy.hibernate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.hibernate.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	private static final String[] EXCLUDE_PATHS = {
			"/users/social",
			"/users/social/signup",
			"/swagger-ui/index.html",
			"/v3/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**"
	};

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 실제환경에서는 패턴추가 다해줘야함.
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
											.excludePathPatterns(EXCLUDE_PATHS);
	}


}
