package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.enums.SensorType;
import com.energybox.backendcodingchallenge.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SensorService sensorService;

    @Test
    void itShouldGetAllSensors() throws Exception{
        //Given
        Pageable pageable = PageRequest.of(0, 20);
        List<Sensor> sensors = Arrays.asList(
                new Sensor(1L, "sensor1", Set.of(SensorType.TEMPERATURE, SensorType.ELECTRICITY), null),
                new Sensor(2L, "sensor2", Set.of(SensorType.TEMPERATURE), null),
                new Sensor(3L, "sensor3", Set.of(SensorType.HUMIDITY), null));
        Page<Sensor> page = new PageImpl<>(sensors);


        //Then
        mockMvc.perform(get("/sensors")
                        .param("paged", "true"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(page)));
    }

    @Test
    void itShouldCreateSensor()  throws Exception{
        //Given
        Sensor sensor = new Sensor(1L, "sensor1", Set.of(SensorType.TEMPERATURE, SensorType.ELECTRICITY), null);
        String body = new ObjectMapper().writeValueAsString(sensor);

        //When
        Mockito.when(sensorService.createSensor(sensor)).thenReturn(sensor);

        //Then
        mockMvc.perform(post("/sensors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(sensor)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(sensor)));
    }

    @Test
    void itShouldGetSensorsBySensorType() {
        //Given
        //When
        //Then

    }
}