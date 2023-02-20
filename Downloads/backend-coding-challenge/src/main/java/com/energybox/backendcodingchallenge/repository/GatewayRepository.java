package com.energybox.backendcodingchallenge.repository;

import com.energybox.backendcodingchallenge.domain.Gateway;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayRepository extends Neo4jRepository<Gateway, Long> {

}
