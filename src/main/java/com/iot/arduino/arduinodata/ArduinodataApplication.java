package com.iot.arduino.arduinodata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArduinodataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArduinodataApplication.class, args);
    }

}

