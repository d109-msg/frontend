package com.ssafy.msg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.msg.user.interceptor.UserInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	
	private final UserInterceptor userInterceptor;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
				HttpMethod.PATCH.name()).allowedHeaders("*")
				.maxAge(1800);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/error/**")
				.excludePathPatterns("/user/sign-in/**", "/user/sign-up/**", "/user/token/**", "/user/password/reset/**");
	}
}
