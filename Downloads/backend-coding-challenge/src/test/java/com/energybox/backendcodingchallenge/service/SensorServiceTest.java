package com.energybox.backendcodingchallenge.service;

import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.enums.SensorType;
import com.energybox.backendcodingchallenge.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @InjectMocks
    SensorService sensorService;

    @Mock
    SensorRepository sensorRepository;

//    @BeforeEach
//    public void init(){
//        MockitoAnnotations.openMocks(this);
//    }


    @Test
    void itShouldCreateSensor() {
        //Given
        Sensor sensor = new Sensor(1L, "sensor1", Set.of(SensorType.TEMPERATURE, SensorType.ELECTRICITY), null);

        //When
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        Sensor created = sensorService.createSensor(sensor);

        //Then
        assertEquals(sensor, created);
        Mockito.verify(sensorRepository, Mockito.times(1)).save(sensor);

    }

    @Test
    void itShouldGetAllSensors() {
        //Given
        Pageable pageable = PageRequest.of(0, 20);
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor(1L, "sensor1", Set.of(SensorType.TEMPERATURE, SensorType.ELECTRICITY), null));
        Page<Sensor> page = new PageImpl<>(sensors);

        //When
        when(sensorRepository.findAll(pageable)).thenReturn(page);
        Page<Sensor> response = sensorService.getAllSensors(pageable);
        //Then
        assertEquals(page, response);
        assertEquals(1, response.stream().collect(Collectors.toList()).size());

    }

    @Test
    void itShouldGetSensorsBySensorType() {
        //Given
        SensorType sensorType = SensorType.ELECTRICITY;
        List<Sensor> sensors = Arrays.asList(
                new Sensor(1L, "sensor1", Set.of(SensorType.TEMPERATURE, SensorType.ELECTRICITY), null),
                new Sensor(2L, "sensor2", Set.of(SensorType.TEMPERATURE), null),
                new Sensor(3L, "sensor3", Set.of(SensorType.HUMIDITY), null));

        //When
        when(sensorRepository.findAll()).thenReturn(sensors);
        List<Sensor> response = sensorService.getSensorsBySensorType(sensorType);

        //Then
        assertEquals(1, response.size());
        assertTrue(response.contains(sensors.get(0)));

    }

}