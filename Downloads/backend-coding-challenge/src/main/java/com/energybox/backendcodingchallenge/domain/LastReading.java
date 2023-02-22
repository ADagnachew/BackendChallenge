package com.energybox.backendcodingchallenge.domain;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Node
public class LastReading {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime  timestamp;
    private double value;
}
