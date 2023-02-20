package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.enums.SensorType;
import com.energybox.backendcodingchallenge.exception.AppException;
import com.energybox.backendcodingchallenge.exception.BadRequestException;
import com.energybox.backendcodingchallenge.service.SensorService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sensors")
public class SensorController {

    SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @ApiOperation( value = "get all Sensors", response = Sensor.class )
    @GetMapping(params = "paged=true")
    public ResponseEntity<?> getAllSensors(Pageable pageable){
        try {
           return ResponseEntity.ok(sensorService.getAllSensors(pageable));
        }catch (AppException e){
            log.error("exception happen when trying to retrieve the Sensors: " + e.getMessage());
            return ResponseEntity.badRequest().body("Something went wrong " + e.getMessage());
        }
    }

    @ApiOperation( value = "Create a Sensors", response = Gateway.class )
    @PostMapping()
    public ResponseEntity<?> createSensor(@RequestBody Sensor sensor){
        try {
            return ResponseEntity.ok(sensorService.createSensor(sensor));
        }catch (BadRequestException e){
            log.error("error creating sensor: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error Creating Sensor " + e.getMessage());
        }
    }

    @ApiOperation( value = "find Sensors by Sensor type", response = Gateway.class )
    @GetMapping("/{type}")
    public ResponseEntity<?> getSensorsBySensorType(@PathVariable SensorType type){
        try {
           return ResponseEntity.ok(sensorService.getSensorsBySensorType(type));
        }catch (Exception e){
            log.error("error getting sensor by sensorType: " + e.getMessage());
            return ResponseEntity.badRequest().body("something went wrong: " + e.getMessage());
        }
    }

}
