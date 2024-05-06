package com.eminyidle.tour.repository;

import com.eminyidle.tour.dto.Country;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CountryNodeRepository extends Neo4jRepository<Country,String> {
}
