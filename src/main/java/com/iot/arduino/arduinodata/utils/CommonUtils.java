package com.iot.arduino.arduinodata.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class CommonUtils {

    @Value("${pst.time.zone}")
    String pstZone;

    /**
     * @return date
     */
    public Date getCurrentDate() {

        return getCurrentDate(pstZone);
    }

    public Date getCurrentDate(String zone) {

        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(zone));
        return Date.from(zdt.toInstant());
    }
}
