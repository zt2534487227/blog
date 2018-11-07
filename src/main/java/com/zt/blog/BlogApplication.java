package com.zt.blog;

import com.zt.blog.interceptor.OpenApiSignInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan("com.zt.blog.dao")
public class BlogApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BlogApplication.class);
	}

	@Configuration
	public static class WebMvcConfig implements WebMvcConfigurer {
		@Override
		//开放接口签名拦截器
		public void addInterceptors(InterceptorRegistry registry) {
			OpenApiSignInterceptor interceptor=new OpenApiSignInterceptor();
			interceptor.setLoginUrl("/api/user/");
			registry.addInterceptor(interceptor).addPathPatterns("/api/**");
		}
	}

}
