LOAD CSV WITH HEADERS FROM 'country_info_final_updated.csv' AS row
MERGE (c:COUNTRY {countryCode: row.country_code, countryName: row.country_name_kor});