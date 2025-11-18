package com.iot.arduino.arduinodata.repository;

import com.iot.arduino.arduinodata.entity.HourlyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface HourlyDataRepository extends JpaRepository<HourlyData, Long> {

    public Optional<HourlyData> findTopByOrderByUpdatedTimeDesc();

    public Optional<HourlyData> findTopByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Date from, Date to);

}
