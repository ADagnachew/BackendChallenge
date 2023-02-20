package com.energybox.backendcodingchallenge.repository;

import com.energybox.backendcodingchallenge.domain.Sensor;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends Neo4jRepository<Sensor, Long> {

}
