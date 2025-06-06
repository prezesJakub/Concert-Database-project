package com.example.concertmanager.repository;

import com.example.concertmanager.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
