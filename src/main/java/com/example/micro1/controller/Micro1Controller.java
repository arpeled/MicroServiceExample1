package com.example.micro1.controller;
import com.example.micro1.service.Micro1Service;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("micro1")
public class Micro1Controller {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Micro1Service service;
    @GetMapping("event")
    public String getEventFromQueue(@RequestParam String msg){
        log.info(msg);
        return msg;
    }
    @PostMapping("name")
    public String getMicroserviceName(@RequestParam String msg)
    {
        String micro2Response = "";
        try {
            micro2Response = restTemplate.postForObject("http://localhost:8081/micro2/name", null, String.class);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

        String micro3Response ="";
        try {
            micro3Response = restTemplate.postForObject("http://localhost:8082/micro3/name", null, String.class);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        service.sendEmailQueue(msg);
        return service.getMicroserviceName() + " : " + micro2Response + " : " + micro3Response;
    }
}