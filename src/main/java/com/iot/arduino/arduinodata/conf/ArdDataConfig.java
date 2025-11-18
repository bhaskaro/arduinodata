package com.iot.arduino.arduinodata.conf;

import com.iot.arduino.arduinodata.utils.HttpUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArdDataConfig {


    @Bean
    public HttpUtils getHttpUtils() {
        return new HttpUtils();
    }
}
