package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.exception.AppException;
import com.energybox.backendcodingchallenge.exception.BadRequestException;
import com.energybox.backendcodingchallenge.service.GatewayService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping( value = "/gateways" )
public class GatewayController {

    private final GatewayService gatewayService;

    @Autowired
    public GatewayController( GatewayService gatewayService ) {
        this.gatewayService = gatewayService;
    }

    @ApiOperation( value = "get all gateways", response = Gateway.class )
    @GetMapping(params = "paged=true")
    public ResponseEntity<?> getAllGateways(Pageable pageable){
        try {
            return ResponseEntity.ok(gatewayService.getAllGateways(pageable));
        }catch (AppException e){
            log.error("exception happen when trying to retrieve the gateways: " + e.getMessage());
            return ResponseEntity.badRequest().body("Something went wrong " + e.getMessage());
        }
    }
    @ApiOperation( value = "create a gateway", response = Gateway.class )
    @PostMapping()
    public ResponseEntity<?> createGateway(@RequestBody Gateway gateway){
        try {
            return ResponseEntity.ok(gatewayService.createGateway(gateway));
        }catch (BadRequestException e){
            log.error("error creating Gateway: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error Creating Gateway " + e.getMessage());
        }
    }

    @ApiOperation( value = "find sensors assigned to a given gateway", response = Gateway.class )
    @GetMapping("/{gatewayId}/sensors")
    public ResponseEntity<?> getAssignedSensors(@PathVariable Long gatewayId){
        try {
            return ResponseEntity.ok(gatewayService.getSensorsAssignedToGateway(gatewayId));
        }catch (AppException e){
            log.error("error getting Gateway by Id: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error retrieving the Gateway by Id " + e.getMessage());
        }
    }

    @ApiOperation( value = "assign sensor to a given gateway", response = Gateway.class )
    @PostMapping("/{gatewayId}/sensors/{sensorId}")
    public ResponseEntity<?> assignSensorToGateway(@PathVariable Long gatewayId, @PathVariable Long sensorId){
        try {
            return ResponseEntity.ok(gatewayService.assignSensorToGateWay(gatewayId,sensorId));
        }catch (BadRequestException e){
            log.error("error assigning sensors to the gateway: " + e.getMessage());
            return ResponseEntity.badRequest().body("something went wrong during assigning Sensor to Gateway " + e.getMessage());
        }
    }

    @ApiOperation( value = "Gateway connected with sensors of type ELECTRICITY", response = Gateway.class )
    @GetMapping("/sensors/electricity")
    public ResponseEntity<?> getGatewaysConnectedWithSensorTypeElectrical(){
        try {
            return ResponseEntity.ok(gatewayService.getGatewaysWithElectricalSensor());
        }catch (AppException e){
            log.error("Exception happened when getting Gateways connected with Electrical Sensor " + e.getMessage());
            return ResponseEntity.badRequest().body("something went wrong when getting Gateways connected with Electrical Sensor " + e.getMessage());
        }
    }
}
