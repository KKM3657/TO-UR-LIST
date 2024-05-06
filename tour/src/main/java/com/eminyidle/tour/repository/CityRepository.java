package com.eminyidle.tour.repository;

import com.eminyidle.tour.dto.City;
import com.eminyidle.tour.dto.Country;
import com.eminyidle.tour.dto.TourDetail;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends Neo4jRepository<City,String> {

    @Query("match(c:City {cityName: $cityName})-[]->(:Country {countryCode: $countryCode}) return c")
    Optional<City> findCity(@Param("cityName") String cityName,@Param("countryCode") String countryCode);

    @Query("MATCH (t:Tour {tourId: $tourId})-[:TO]->(c:City) " +
            "RETURN collect(DISTINCT c) AS cityList")
    List<City> findCitiesByTourId(String tourId);

    @Query("MATCH (c:Country {countryCode: $countryCode}) RETURN c")
    Optional<Country> findCountryNodeByCountryCode(String countryCode);
}
