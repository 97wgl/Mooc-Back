package com.xgxfd.moocback.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {





    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //registry.addInterceptor(passportInterceptor);
        //registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
                                                       // .excludePathPatterns("/static/**");

    }

   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       *//*registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");*//*
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
       // registry.addResourceHandler("/jquery/**").addResourceLocations("classpath:/static/jquery");
        //registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js");
        //registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui");

        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //访问路径 资源位置对应
    }*/

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true).maxAge(3600);

    }

}
