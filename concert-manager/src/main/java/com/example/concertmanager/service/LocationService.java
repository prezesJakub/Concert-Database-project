package com.example.concertmanager.service;

import com.example.concertmanager.entity.City;
import com.example.concertmanager.entity.Country;
import com.example.concertmanager.repository.CityRepository;
import com.example.concertmanager.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public LocationService(CountryRepository countryRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    public Country findOrCreateCountry(String name) {
        return countryRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> countryRepository.save(new Country(name)));
    }

    public City findOrCreateCity(String name, String countryName) {
        Country country = findOrCreateCountry(countryName);
        return cityRepository.findByNameIgnoreCaseAndCountry(name, country)
                .orElseGet(() -> cityRepository.save(new City(name, country)));
    }
}
