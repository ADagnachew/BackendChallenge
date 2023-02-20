package com.energybox.backendcodingchallenge.domain;

import com.energybox.backendcodingchallenge.enums.SensorType;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Node("Sensor")
public class Sensor {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Property(name = "types")
    private Set<SensorType> sensorTypes;

    @Relationship(type = "CONNECTED_TO", direction = Relationship.Direction.OUTGOING)
    private Gateway gateway;

}
