package com.eminyidle.tour.repository.maria;

import com.eminyidle.tour.dto.City;
import com.eminyidle.tour.dto.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryCityRepository extends JpaRepository<CityEntity,Integer> {
    List<CityEntity> findAllByCountryCode(String countryCode);

    Optional<CityEntity> findByCountryCodeAndCityNameKor(String countryCode, String cityNameKor);
}
