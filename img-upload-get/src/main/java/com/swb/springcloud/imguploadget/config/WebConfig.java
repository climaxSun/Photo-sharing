package com.swb.springcloud.imguploadget.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Value("${avatarUrl}")
    private String avatarUrl;

    @Value("${flowerUrl}")
    private String flowerUrl;

    @Value("${articleUrl}")
    private String articleUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射图片保存地址
        registry.addResourceHandler("/userAvatar/**").
                addResourceLocations("file:"+avatarUrl);
        registry.addResourceHandler("/flowers/**").
                addResourceLocations("file:"+flowerUrl);
        registry.addResourceHandler("/article/**").
                addResourceLocations("file:"+articleUrl);
    }
}
