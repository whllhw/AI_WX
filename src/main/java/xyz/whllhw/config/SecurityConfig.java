package xyz.whllhw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new SecurityInterceptor();
    }

    /**
     * 添加拦截器，
     * 添加不需要登录的界面。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(getMyInterceptor()).excludePathPatterns(
                "/swagger-ui.html",
                "/webjars/springfox-swagger-ui/**",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/configuration/**",
                "/index.html",
                "/user/login",
                "/user/reg");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置了可以被跨域访问的路径和可以被哪些主机跨域访问
        registry.addMapping("/**").allowedOrigins("*");
    }

    /**
     * 在这里添加静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
