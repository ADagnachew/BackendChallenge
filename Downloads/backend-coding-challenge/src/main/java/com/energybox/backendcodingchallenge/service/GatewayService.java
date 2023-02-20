package com.energybox.backendcodingchallenge.service;

import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.enums.SensorType;
import com.energybox.backendcodingchallenge.repository.GatewayRepository;
import com.energybox.backendcodingchallenge.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GatewayService {

    @Lazy
    private final GatewayRepository gatewayRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public GatewayService(GatewayRepository gatewayRepository, SensorRepository sensorRepository) {
        this.gatewayRepository = gatewayRepository;
        this.sensorRepository = sensorRepository;
    }

    public Gateway createGateway(Gateway gateway){
        Gateway gateway1 =  gatewayRepository.save(gateway);
        log.info("new Gateway Created with Id: "+ gateway1.getId());
        return gateway1;
    }
    public Page<Gateway> getAllGateways(Pageable pageable){
        Page<Gateway> gateways =  gatewayRepository.findAll(pageable);
        log.info("number of gateways retrieved = " + gateways.toList().size());
        return gateways;
    }

    public List<Sensor> getSensorsAssignedToGateway(Long gatewayId){
        Optional<Gateway> gateway = gatewayRepository.findById(gatewayId);
        List<Sensor> sensors = new ArrayList<>();
        if(gateway.isPresent())
            sensors = gateway.get().getSensors();

        log.info("List of sensors assigned to gateway with ID: " + gatewayId + " are " + sensors.toString());
        return sensors;
    }
    public Gateway assignSensorToGateWay(Long gatewayId,Long sensorId){
        Optional<Sensor>  sensor= sensorRepository.findById(sensorId);
        Optional<Gateway> gateway = gatewayRepository.findById(gatewayId);

        if(sensor.isPresent() && gateway.isPresent()){
            Gateway gateway1 = gateway.get();
            gateway1.assignSensor(sensor.get());
            log.info("Sensor with ID: " + sensorId + " is assigned to Gateway with Id " + gatewayId);
            return gatewayRepository.save(gateway1);
        }
        else {
            return null;
        }
    }

    public List<Gateway> getGatewaysWithElectricalSensor(){
        List<Gateway> gateways= gatewayRepository.findAll();
        List<Gateway> result = new ArrayList<>();

        for(Gateway gateway : gateways){
            List<Sensor> sensors = gateway.getSensors();
            for(Sensor sensor : sensors){
                if(sensor.getSensorTypes().contains(SensorType.ELECTRICITY))
                    result.add(gateway);
            }
        }
      return result;
    }
}
