package com.iot.arduino.arduinodata.service;

import com.iot.arduino.arduinodata.utils.HttpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DateTimeServices {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    HttpUtils httpUtils;

    @Value("${weather.hourly.api}")
    private String hourlyApi;


}
