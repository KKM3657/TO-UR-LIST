package com.eminyidle.tour.dto;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Node
public class City {
    @Id @GeneratedValue
    private String id;
//    @Relationship(type = "IN")
//    @Property(readOnly = true)
    private String countryCode;
    @Property
    private String cityName;

    @Relationship(type = "IN",direction = Relationship.Direction.OUTGOING)
    private Country country;

    public City(String cityName, String countryCode){
        setCityName(cityName);
        setCountryCode(countryCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(countryCode, city.countryCode) && Objects.equals(cityName, city.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, cityName);
    }
}
