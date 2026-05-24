package apd.apigateway.controller;

import apd.apigateway.exception.ServiceUnavailableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/user")
    public Mono<Void> userFallback() {
        throw new ServiceUnavailableException("User Service is temporarily unavailable.");
    }

    @RequestMapping("/fallback/employee")
    public Mono<Void> employeeFallback() {
        throw new ServiceUnavailableException("Employee Service is temporarily unavailable.");
    }

    @RequestMapping("/fallback/elearning")
    public Mono<Void> elearningFallback() {
        throw new ServiceUnavailableException("E-Learning Service is temporarily unavailable.");
    }

    @RequestMapping("/fallback/file")
    public Mono<Void> fileFallback() {
        throw new ServiceUnavailableException("File Service is temporarily unavailable.");
    }
}