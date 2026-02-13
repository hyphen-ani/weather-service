package org.anirudh.spring.weatherserver.repository;

import org.anirudh.spring.weatherserver.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findByState(String state);
}
