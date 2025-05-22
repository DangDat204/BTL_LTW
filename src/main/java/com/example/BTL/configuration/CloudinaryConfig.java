package com.example.BTL.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dsiazxjsx",
                "api_key", "562259658892389",
                "api_secret", "zAS051IaqcLSEppRoUKXta3C0dQ",
                "secure", true
        ));
    }
}
