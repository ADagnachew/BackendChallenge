package com.energybox.backendcodingchallenge.service;

import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.enums.SensorType;
import com.energybox.backendcodingchallenge.repository.GatewayRepository;
import com.energybox.backendcodingchallenge.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final GatewayRepository gatewayRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, GatewayRepository gatewayRepository) {
        this.sensorRepository = sensorRepository;
        this.gatewayRepository = gatewayRepository;
    }

    public Sensor createSensor(Sensor sensor){
        Sensor sensor1 = sensorRepository.save(sensor);
        log.info("New Sensor Created with Id: " + sensor1.getId());
        return sensor1;
    }

    public Page<Sensor> getAllSensors(Pageable pageable){
        Page<Sensor> sensors =  sensorRepository.findAll(pageable);
        log.info("Number of Sensors retrieved = " + sensors.toList().size());
        return sensors;
    }

    public List<Sensor> getSensorsBySensorType(SensorType type){
       List<Sensor> sensors = sensorRepository.findAll().stream()
               .filter(sensor -> sensor.getSensorTypes().contains(type))
               .collect(Collectors.toList());

       log.info("number of sensors that has type " + type + " are " + sensors.size());

       return sensors;
    }

}
