package com.friends.easybud;

import com.friends.easybud.auth.dto.OauthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(OauthProperties.class)
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableJpaAuditing
@SpringBootApplication
public class EasybudApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasybudApplication.class, args);
    }

}