package com.energybox.backendcodingchallenge.domain;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Node("Gateway")
public class Gateway {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Relationship(type = "CONNECTED_TO", direction = Relationship.Direction.INCOMING)
    private List<Sensor> sensors;

    public void assignSensor(Sensor sensor){
        sensors.add(sensor);
    }
}
