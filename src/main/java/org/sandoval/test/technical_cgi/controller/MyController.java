package org.sandoval.test.technical_cgi.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyController {

  @Autowired
  private RestTemplate restTemplate;

  private static final String EXTERNAL_SERVICE_URL = "http://test-service/api/data";

  @GetMapping("/getData")
  @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
  public String fetchData() {
    return restTemplate.getForObject(EXTERNAL_SERVICE_URL, String.class);
  }


  public String fallbackMethod(Exception e) {
    return "Fallback: El servicio externo no está disponible en este momento.";
  }
}