package cn.edu.chd.sms.conf;

import cn.edu.chd.sms.interceptors.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
    @Bean //将组件注册在容器
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer configurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("page/login");
                registry.addViewController("/index.html").setViewName("page/login");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                List<String> patterns = new ArrayList<>();
                patterns.add("/page/login.html");
                patterns.add("/plugins/**");
                patterns.add("/user/login");
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
            }
        };
        return configurer;
    }
}
