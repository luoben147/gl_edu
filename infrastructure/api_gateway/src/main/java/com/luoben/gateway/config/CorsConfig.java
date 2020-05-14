package com.luoben.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 处理跨域
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        //1.初始化cors配置对象 添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了; * 代表所有域名都可以跨域访问
        config.addAllowedOrigin("*");
        //2) 是否允许携带Cookie信息
        //config.setAllowCredentials(true);
        //3) 允许的请求方式  *代表所有的请求方法 GET POST PUT DELETE...
        config.addAllowedMethod("*");
        //4）允许的头信息  *代表允许携带任何头信息
        config.addAllowedHeader("*");
        //5）此次预检有效时长,有效时长内无需预检直接跨域访问
        //config.setMaxAge(3600L);

        //2.初始化cors配置源对象，添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}

