package com.iot.arduino.arduinodata.repository;

import com.iot.arduino.arduinodata.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ForecastDataRepository extends JpaRepository<Forecast, Long> {


// public List<Forecast> findTop2ByOrderByUpdatedTimeDesc();

    //public List<Forecast> findTop2ByOrderByStartTimeAsc();

    public List<Forecast> findTop2ByEndTimeGreaterThanEqualOrderByStartTimeAsc(Date date);


}
