package com.swb.springcloud.imguploadget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ImgUploadGetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgUploadGetApplication.class, args);
    }

}
